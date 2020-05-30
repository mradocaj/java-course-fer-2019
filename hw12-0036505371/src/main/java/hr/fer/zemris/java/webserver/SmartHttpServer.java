package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

@SuppressWarnings("unused")

/**
 * Razred koji predstavlja implementaciju servera.
 * Server se pokreće uz jedan argument - putanju do konfiguracijske datoteke.
 * 
 * @author Maja Radočaj
 *
 */
public class SmartHttpServer {

	/**
	 * Adresa na kojoj server sluša.
	 */
	private String address;	
	/**
	 * Domena.
	 */
	private String domainName;
	/**
	 * Port na kojem server sluša.
	 */
	private int port;
	/**
	 * Broj dretvi.
	 */
	private int workerThreads;
	/**
	 * Broj sekundi nakon kojeg server posluživati.
	 */
	private int sessionTimeout;		
	/**
	 * Mime tipovi.
	 */
	private Map<String, String> mimeTypes = new HashMap<>();
	/**
	 * Dretva servera.
	 */
	private ServerThread serverThread; 
	/**
	 * Dretve.
	 */
	private ExecutorService threadPool;
	/**
	 * Čvor parsiranog dokumenta.
	 */
	private Path documentRoot;
	/**
	 * Boolean zastavica kraja rada.
	 */
	private boolean stop = false;
	/**
	 * Mapa radnika.
	 */
	private Map<String,IWebWorker> workersMap = new HashMap<>();
	/**
	 * Mapa sessiona.
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<>();
	/**
	 * Objekt za generiranje slučajnih brojeva.
	 */
	private Random sessionRandom = new Random();
	/**
	 * Adresa.
	 */
	private static final String ADDRESS = "server.address";
	/**
	 * Domena.
	 */
	private static final String DOMAIN_NAME = "server.domainName";
	/**
	 * Port.
	 */
	private static final String PORT = "server.port";
	/**
	 * Timeout.
	 */
	private static final String TIMEOUT = "session.timeout";
	/**
	 * Dretve.
	 */
	private static final String WORKER_THREADS = "server.workerThreads";
	/**
	 * Čvor.
	 */
	private static final String DOCUMENT_ROOT = "server.documentRoot";
	/**
	 * Konfiguracija.
	 */
	private static final String MIME_CONFIG = "server.mimeConfig";
	/**
	 * Radnici.
	 */
	private static final String WORKERS = "server.workers";
	
	
	/**
	 * Javni konstruktor.
	 * 
	 * @param configFileName putanja do konfiguracijske datoteke 
	 */
	public SmartHttpServer(String configFileName) {
		try {
			setUpProperties(configFileName);
			serverThread = new ServerThread();
		} catch(IOException | NumberFormatException ex) {
			System.err.println("Couldn't read configuration file. ");
			ex.printStackTrace();
		}
	}

	/**
	 * Glavni program.
	 * 
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Path to server properties file expected.");
			return;
		}
		SmartHttpServer server = new SmartHttpServer(args[0]);
		server.start();
	}

	/**
	 * Metoda koja pokreće rad servera.
	 */
	protected synchronized void start() {
		threadPool = Executors.newFixedThreadPool(workerThreads);
		if(!serverThread.isAlive()) {
			serverThread.start();
		}
		
		Thread removeExpiredThread = new Thread(() ->  {
			try {
				while(true) {
					Map<String, SmartHttpServer.SessionMapEntry> sessionsCopy = new HashMap<>(sessions);
					sessionsCopy.forEach((sid, entry) -> {
						if(entry.validUntil < System.currentTimeMillis() / 1000) {
							sessions.remove(sid);
						}
					});
				Thread.sleep(1000 * 300); 	//5 minuta sleepa
				}
			} catch(InterruptedException ex) {
				System.err.println(ex.getMessage());
			}
		});
		removeExpiredThread.setDaemon(true);
		removeExpiredThread.start();
	}

	/**
	 * Metoda koja zaustavlja rad servera.
	 */
	protected synchronized void stop() {
		stop = true;
		threadPool.shutdown();
	}

	/**
	 * Razred koji modelira dretvu servera.
	 * 
	 * @author Maja Radočaj
	 *
	 */
	protected class ServerThread extends Thread {
		
		@Override
		public void run() {
			try {
				ServerSocket serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress((InetAddress) null, port));
				
				while(!stop) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
				serverSocket.close();
			} catch(IOException ex) {
				System.err.println("Couldn't create socket with port " + port + ".");
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * Razred koji modelira jedan zapis u mapi sessiona.
	 * 
	 * @author Maja Radočaj
	 *
	 */
	private static class SessionMapEntry {
		/**
		 * Karakteristični indetifikacijski niz.
		 */
		String sid;
		/**
		 * Host.
		 */
		String host;
		/**
		 * Vrijeme do kada je zapis validan.
		 */
		long validUntil;
		/**
		 * Mapa.
		 */
		Map<String, String> map;
		
		/**
		 * Javni konstruktor.
		 * 
		 * @param sid karakteristični identifikacijski zapis
		 * @param host host
		 * @param validUntil vrijeme do kad je zapis validan
		 * @param map mapa
		 */
		public SessionMapEntry(String sid, String host, long validUntil, Map<String, String> map) {
			this.sid = sid;
			this.host = host;
			this.validUntil = validUntil;
			this.map = map;
		}
	}

	/**
	 * Razred koji modelira posao koji se treba obaviti kad se pošalje zahtjev serveru.
	 * 
	 * @author Maja Radočaj
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		/**
		 * Socket na kojem server sluša.
		 */
		private Socket csocket;
		/**
		 * Ulazni tok podataka.
		 */
		private PushbackInputStream istream;
		/**
		 * Izlazni tok podataka.
		 */
		private OutputStream ostream;
		/**
		 * Verzija.
		 */
		private String version;
		/**
		 * Metoda.
		 */
		private String method;
		/**
		 * Host.
		 */
		private String host;	
		/**
		 * Parametri.
		 */
		private Map<String, String> params = new HashMap<String, String>();
		/**
		 * Privremeni parametri.
		 */
		private Map<String, String> tempParams = new HashMap<String, String>();	
		/**
		 * Stalni parametri.
		 */
		private Map<String, String> permParams = new HashMap<String, String>();	
		/**
		 * Kolačići.
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/**
		 * Jedinstveni identifikacijski niz.
		 */
		private String SID;
		/**
		 * Kontekst zahtjeva.
		 */
		private RequestContext context;

		/**
		 * Javni konstruktor.
		 * 
		 * @param csocket socket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());	
				ostream = new BufferedOutputStream(csocket.getOutputStream());
				List<String> request = readRequest(istream);
				
				if(request == null || request.size() < 1) {
					sendError(ostream, 400, "Bad request");	
					return; 
				}
				String firstLine = request.get(0);
				
				String[] parts = firstLine.split(" ");
				if(parts.length != 3) {
					sendError(ostream, 400, "Bad request");
					return;		
				}
				
				method = parts[0].toUpperCase();
				if(!method.equals("GET")) {
					sendError(ostream, 400, "Method Not Allowed");
					return;
				}
				version = parts[2].toUpperCase();
				if(!version.equals("HTTP/1.1") && !version.equals("HTTP/1.0")) {
					sendError(ostream, 400, "HTTP Version Not Supported");
					return;
				}
				
				host = SmartHttpServer.this.domainName;
				for(String header : request) {
					if(header.startsWith("Host:")) {
						String headerParts[] = header.split(" ");
						host = headerParts[1].split(":")[0];
					}
				}
				
				checkSession(request);
				
				String[] requestedPathParts = parts[1].split("\\?");	
				String path = requestedPathParts[0];
				String paramString = requestedPathParts.length == 2 ? requestedPathParts[1] : "";
		
				parseParameters(paramString); 
				internalDispatchRequest(path, true);
				ostream.flush();
			} catch(Exception ex) {
				System.err.println("Error while creating input/output streams. " + ex.getMessage());
				ex.printStackTrace();
			} finally {
				try {
					csocket.close();
				} catch(IOException ex) {
					System.err.println("Cannot close socket. " + ex.getMessage());
				}
			}

		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}
		
		/**
		 * Metoda za obradu zahtjeva.
		 * 
		 * @param urlPath primljeni path u zahtjevu
		 * @param directCall zastavica koja nam govori radi li se o direktnom pozivu
		 * @throws Exception ako dođe do greške
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			if(context == null) {					
				context = new RequestContext(ostream, params, permParams, 
						outputCookies, tempParams, this, SID);
			}
			
			Path requestedPath = documentRoot.resolve(urlPath.substring(1));
			
			if(urlPath.startsWith("/private/")) {
				if(directCall) {
					sendError(ostream, 404, "Bad request");
					return;	
				}
			}
			if(urlPath.startsWith("/ext/")) {
				String className = "hr.fer.zemris.java.webserver.workers." 
						+ urlPath.replaceFirst("/ext/", "");	
				Class<?> referenceToClass = this.getClass().getClassLoader().
						loadClass(className);
				Object newObject = referenceToClass.getDeclaredConstructor().newInstance();
				IWebWorker iww = (IWebWorker)newObject;
				iww.processRequest(context);
				return;
			}
			if(workersMap.containsKey(urlPath)) {		
				workersMap.get(urlPath).processRequest(context);
				return;
			}
			
			if(!requestedPath.toAbsolutePath().startsWith(documentRoot.toAbsolutePath())) {
				sendError(ostream, 403, "Forbidden path");
				return;
			} 
			if(!Files.exists(requestedPath) || !Files.isReadable(requestedPath) 		
					|| !Files.isRegularFile(requestedPath)) {
				sendError(ostream, 404, "File isn't readable");
				return;
			}
			
			String extension = urlPath.substring(urlPath.lastIndexOf('.') + 1);	

			String name = SmartHttpServer.this.mimeTypes.get(extension);
			String mimeType = name == null ? "application/octet-stream" : name;

			if(extension.equals("smscr")) {
				executeScript(requestedPath);
				return;
			}

			context.setMimeType(mimeType);
			context.setStatusCode(200);
			serveFile(context, requestedPath);
		}
		
		/**
		 * Pomoćna metoda za omogućavanje podrške za kolačiće.
		 * 
		 * @param request headeri
		 */
		private synchronized void checkSession(List<String> request) {
			String sidCandidate = null;
			
			for(String headerLine : request) {
				if(!headerLine.startsWith("Cookie:")) continue;
				String line = headerLine.split(" ")[1];
				String[] cookies = line.split(";");
				for(String cookie : cookies) {
					String[] parts = cookie.split("=");
					if(parts[0].equals("sid")) {
						sidCandidate = parts[1].replace("\"", "");
					}
				}
			}
			
			if(sidCandidate != null) {
				SessionMapEntry entry = sessions.get(sidCandidate);
				if(entry == null || !entry.host.equals(host)) {
					sidCandidate = null;
				} else {
					if(entry.validUntil < System.currentTimeMillis() / 1000) {
						sessions.remove(sidCandidate);
						sidCandidate = null;
					} else {
						entry.validUntil = System.currentTimeMillis() / 1000 + sessionTimeout;
						permParams = entry.map;
					}
				}
			}
			
			if(sidCandidate == null) {
				StringBuilder buffer = new StringBuilder(20);
				for(int i = 0; i < 20; i++) {
					int rand = 'A' + (int) (sessionRandom.nextFloat() * ('Z' - 'A' + 1));
					buffer.append((char) rand);
				}
				sidCandidate = buffer.toString();
				
				SessionMapEntry entry = new SessionMapEntry(sidCandidate, host, 
						System.currentTimeMillis() / 1000 + sessionTimeout, new ConcurrentHashMap<>());
				sessions.put(sidCandidate, entry);
				RCCookie cookie = new RCCookie("sid", sidCandidate, null, host, "/");
				outputCookies.add(cookie);
				permParams = entry.map;
			}
		
			this.SID = sidCandidate;
		}

		/**
		 * Pomoćna metoda za parsiranje parametara.
		 * 
		 * @param paramString parametri u obliku jednog stringa
		 */
		private void parseParameters(String paramString) {
			if(paramString.length() == 0) return;
			String[] parameters = paramString.split("&");	
			
			for(String param : parameters) {
				String[] keyAndValue = param.split("=");
				if(keyAndValue.length != 2) continue;
				params.put(keyAndValue[0], keyAndValue[1]);
			}
		}

		/**
		 * Pomoćna metoda koja izvodi skriptu.
		 * 
		 * @param requestedPath putanja do skripte
		 * @throws IOException ako dođe do greške
		 */
		private void executeScript(Path requestedPath) throws IOException {
			byte[] bytes = Files.readAllBytes(requestedPath);
			String file = new String(bytes, StandardCharsets.UTF_8);
			SmartScriptParser parser = new SmartScriptParser(file);
			SmartScriptEngine en = new SmartScriptEngine(parser.getDocumentNode(), context);
			
			en.execute();
		}

		/**
		 * Pomoćna metoda koja ispisuje dokument u zahtjev <code>rc</code>.
		 * 
		 * @param rc kontekst
		 * @param requestedFile dokument koji treba ispisati
		 * @throws IOException ako dođe do greške pri čitanju ili pisanju
		 */
		private void serveFile(RequestContext rc, Path requestedFile) throws IOException {
			try(InputStream is = new BufferedInputStream(Files.newInputStream(requestedFile))) {
				byte[] buf = new byte[1024];
				
				while(true) {
					int r = is.read(buf);
					if(r < 1) break;
					rc.write(buf, 0, r);
				}
			}
		}
		
		private List<String> extractHeaders(String requestStr) {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for(String s : requestStr.split("\n")) {
				if(s.isEmpty())
					break;
				char c = s.charAt(0);
				if(c == 9 || c == 32) {
					currentLine += s;
				} else {
					if(currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if(!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		/**
		 * Pomoćna metoda za slanje odgovora.
		 * 
		 * @param cos izlazni tok podataka
		 * @param statusCode status
		 * @param statusText tekst statusa
		 * @throws IOException ako dođe do greške
		 */
		private void sendError(OutputStream cos, int statusCode, String statusText) throws IOException {

			cos.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + 
					"Server: simple java server\r\n" + 
					"Content-Type: text/plain;charset=UTF-8\r\n" + 
					"Content-Length: 0\r\n" + 
					"Connection: close\r\n" + 
					"\r\n").getBytes(StandardCharsets.US_ASCII));
			cos.flush();

		}

		/**
		 * Metoda koja čita bajtove iz predanog ulaznog toka podataka.
		 * 
		 * @param is ulazni tok podataka
		 * @return polje bajtova
		 * @throws IOException ako dođe do greške
		 */
		private byte[] readRequestBytes(InputStream is) throws IOException {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
	l:		while(true) {
				int b = is.read();
				if(b==-1) return null;
				if(b!=13) {
					bos.write(b);
				}
				switch(state) {
				case 0: 
					if(b==13) { state=1; } else if(b==10) state=4;
					break;
				case 1: 
					if(b==10) { state=2; } else state=0;
					break;
				case 2: 
					if(b==13) { state=3; } else state=0;
					break;
				case 3: 
					if(b==10) { break l; } else state=0;
					break;
				case 4: 
					if(b==10) { break l; } else state=0;
					break;
				}
			}
			return bos.toByteArray();
		}

		/**
		 * Pomoćna metoda koja iz ulaznog toka podataka stvara headere koje potom vraća.
		 * 
		 * @param cis ulazni tok podataka
		 * @return headere
		 * @throws IOException ako dođe do greške pri čitanju podataka
		 */
		private List<String> readRequest(InputStream cis) throws IOException {
			byte[] request = readRequestBytes(cis);
			if(request == null) {
				return null;
			}
			String requestStr = new String(request, StandardCharsets.US_ASCII);

			List<String> headers = extractHeaders(requestStr);
			return headers;
		}
		
	}

	/**
	 * Pomoćna metoda koja čita i pohranjuje karakteristike iz datoteke dane 
	 * putanjom <code>configFileName</code>.
	 * 
	 * @param configFileName putanja do datoteke sa pohranjenim karakteristikama servera
	 * @throws IOException ako dođe do greške
	 */
	private void setUpProperties(String configFileName) throws IOException {
		Properties properties = new Properties();
		try(InputStream is = new FileInputStream(configFileName)) {
			properties.load(is);
		}

		address = properties.getProperty(ADDRESS);	
		domainName = properties.getProperty(DOMAIN_NAME);
		port = Integer.parseInt(properties.getProperty(PORT));
		workerThreads = Integer.parseInt(properties.getProperty(WORKER_THREADS));
		sessionTimeout = Integer.parseInt(properties.getProperty(TIMEOUT));
		documentRoot = Paths.get(properties.getProperty(DOCUMENT_ROOT));

		Properties p = new Properties();
		try(InputStream is = new FileInputStream(properties.getProperty(MIME_CONFIG))) {
			p.load(is);
		}
		for(String name : p.stringPropertyNames()) {
			mimeTypes.put(name, p.getProperty(name));
		}
		
		Properties prop = new Properties();
		try(InputStream is = new FileInputStream(properties.getProperty(WORKERS))) {
			prop.load(is);
		}
		
		for(String name : prop.stringPropertyNames()) {		
			try {
				String path = name; 
				if(workersMap.containsKey(path)) {
					throw new IllegalArgumentException("Path already defined.");
				}
				String fqcn = prop.getProperty(path);
				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				Object newObject = referenceToClass.getDeclaredConstructor().newInstance();
				IWebWorker iww = (IWebWorker)newObject;
				workersMap.put(path, iww);
			} catch(Exception ex) {
				System.err.println("Problem with creating workers.");
				return;
			}
		}
	}
}
