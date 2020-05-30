package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Razred koji modelira zahtjev koji se žalje serveru.
 * 
 * @author Maja Radočaj
 *
 */
public class RequestContext {	

	/**
	 * Izlazni tok podataka.
	 */
	private OutputStream outputStream;
	/**
	 * Charset.
	 */
	private Charset charset;
	/**
	 * Kodiranje teksta koji se koristi pri pisanju izlaznih podataka.
	 */
	private String encoding;
	/**
	 * Kod statusa.
	 */
	private int statusCode; 
	/**
	 * Tekst statusa.
	 */
	private String statusText;	
	/**
	 * Tip.
	 */
	private String mimeType;	
	/**
	 * Duljina zapisa.
	 */
	private Long contentLength; 
	/**
	 * Parametri.
	 */
	private Map<String,String> parameters;
	/**
	 * Imena parametra.
	 */
	private Set<String> parameterNames;
	/**
	 * Privremeni parametri.
	 */
	private Map<String,String> temporaryParameters;
	/**
	 * Stalni parametri.
	 */
	private Map<String,String> persistentParameters;
	/**
	 * Kolačići.
	 */
	private List<RCCookie> outputCookies;	
	/**
	 * Zastavica generiranja headera.
	 */
	private boolean headerGenerated;
	/**
	 * Dispatcher.
	 */
	private IDispatcher dispatcher;
	/**
	 * Identifikacijska oznaka sessiona.
	 */
	private String sid;
	/**
	 * Pretpostavljena kodna stranica.
	 */
	private final static String DEFAULT_ENCODING = "UTF-8";
	/**
	 * Pretpostavljeni tip.
	 */
	private final static String DEFAULT_MIME_TYPE = "text/html";
	/**
	 * Pretpostavljeni statusni kod.
	 */
	private final static int DEFAULT_STATUS_CODE = 200;
	/**
	 * Pretpostavljeni statusni tekst.
	 */
	private final static String DEFAULT_STATUS_TEXT = "OK";
	
	/**
	 * Javni konstruktor. Izlazni tok podataka ne smije biti <code>null</code>.
	 * 
	 * @param outputStream izlazni tok podataka
	 * @param parameters parametri
	 * @param persistentParameters stalni parametri
	 * @param outputCookies kolačići
	 * @param sid identifikacijski niz
	 * @throws NullPointerException ako je izlazni tok podataka <code>null</code>
	 */
	public RequestContext(OutputStream outputStream, Map<String,String> parameters, 
			Map<String,String> persistentParameters, List<RCCookie> outputCookies, String sid) {
		
		this.outputStream = Objects.requireNonNull(outputStream);
		this.parameters = parameters == null ? new HashMap<>() : parameters;
		this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
		this.outputCookies = outputCookies == null ? new ArrayList<>() : outputCookies;
		this.sid = sid;
		this.encoding = DEFAULT_ENCODING;
		this.statusCode = DEFAULT_STATUS_CODE;
		this.statusText = DEFAULT_STATUS_TEXT;
		this.mimeType = DEFAULT_MIME_TYPE;
		
		Set<String> parameterSet = new HashSet<>();
		parameters.forEach((k, v) -> parameterSet.add(k));
		parameterNames = Collections.unmodifiableSet(parameterSet);
		
		temporaryParameters = new HashMap<>();
	}
		
	/**
	 * Javni konstruktor.Izlazni tok podataka ne smije biti <code>null</code>.
	 * 
	 * @param outputStream izlazni tok podataka
	 * @param parameters parametri
	 * @param persistentParameters trajni parametri
	 * @param outputCookies kolačići
	 */
	public RequestContext(OutputStream outputStream, Map<String,String> parameters, 
			Map<String,String> persistentParameters, List<RCCookie> outputCookies) {
		
		this(outputStream, parameters, persistentParameters, outputCookies, "");
	}
	
	/**
	 * Javni konstruktor. Izlazni tok podataka ne smije biti <code>null</code>.
	 * 
	 * @param outputStream izlazni tok podataka
	 * @param parameters parametri
	 * @param persistentParameters stalni parametri
	 * @param outputCookies kolačići
	 * @param temporaryParameters privremeni parametri
	 * @param dispatcher dispatcher
	 * @param sid identifikacijski niz
	 * @throws NullPointerException ako je izlazni tok podataka ili dispatcher <code>null</code>
	 */
	public RequestContext(OutputStream outputStream, Map<String,String> parameters, 
			Map<String,String> persistentParameters, List<RCCookie> outputCookies,
			Map<String,String> temporaryParameters, IDispatcher dispatcher, String sid) {
		
		this(outputStream, parameters, persistentParameters, outputCookies, sid);
		this.dispatcher = Objects.requireNonNull(dispatcher);
		this.temporaryParameters = temporaryParameters == null ? new HashMap<>() : temporaryParameters;
	}
	
	/**
	 * Javni konstruktor. Izlazni tok podataka ne smije biti <code>null</code>.
	 * 
	 * @param outputStream izlazni tok podataka
	 * @param parameters parametri
	 * @param persistentParameters stalni parametri
	 * @param outputCookies kolačići
	 * @param temporaryParameters privremeni parametri
	 * @param dispatcher dispatcher
	 * @throws NullPointerException ako je izlazni tok podataka ili dispatcher <code>null</code>
	 */
	public RequestContext(OutputStream outputStream, Map<String,String> parameters, 
			Map<String,String> persistentParameters, List<RCCookie> outputCookies,
			Map<String,String> temporaryParameters, IDispatcher dispatcher) {
		
		this(outputStream, parameters, persistentParameters, outputCookies, 
				temporaryParameters, dispatcher, "");
	}
	
	/**
	 * Razred koji modelira jedan kolačić u zahtjevu.
	 * 
	 * @author Maja Radočaj
	 *
	 */
	public static class RCCookie {
		/**
		 * Ime kolačića.
		 */
		private String name;
		/**
		 * Vrijednost kolačića.
		 */
		private String value;
		/**
		 * Domena.
		 */
		private String domain;
		/**
		 * Putanja.
		 */
		private String path;
		/**
		 * Maksimalna starost.
		 */
		private Integer maxAge;
		
		/**
		 * Javni konstruktor.
		 * 
		 * @param name ime kolačića
		 * @param value vrijednost kolačića
		 * @param maxAge maksimalna starost
		 * @param domain domena
		 * @param path putanja
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}
		
		/**
		 * Getter koji vraća ime kolačića.
		 * 
		 * @return ime kolačića
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * Getter koji vraća vrijednost kolačića.
		 * 
		 * @return vrijednost kolačića
		 */
		public String getValue() {
			return value;
		}
		
		/**
		 * Getter koji vraća domenu kolačića.
		 * 
		 * @return domena kolačića
		 */
		public String getDomain() {
			return domain;
		}
		
		/**
		 * Getter koji vraća putanju kolačića.
		 * 
		 * @return putanja kolačića
		 */
		public String getPath() {
			return path;
		}
		
		/**
		 * Getter koji vraća maksimalnu starost kolačića.
		 * 
		 * @return maksimalna starost kolačića
		 */
		public Integer getMaxAge() {
			return maxAge;
		}	
	}

	/**
	 * Metoda koja postavlja kodnu stranicu na onu koja je predana.
	 * 
	 * @param encoding kodna stranica
	 * @throws RuntimeException ako je header generiran prije poziva ove metode
	 * @throws NullPointerException ako je predana kodna stranica {@link NullPointerException}
	 */
	public void setEncoding(String encoding) {
		checkIfHeaderGenerated();
		this.encoding = Objects.requireNonNull(encoding);
	}

	/**
	 * Metoda koja postavlja statusni kod na onaj koji je predan.
	 * 
	 * @param statusCode novi statusni kod
	 * @throws RuntimeException ako je header već generiran
	 */
	public void setStatusCode(int statusCode) {
		checkIfHeaderGenerated();
		this.statusCode = statusCode;
	}

	/**
	 * Metoda koja postavlja statusni tekst na onaj koji je predan.
	 * 
	 * @param statusText statusni tekst
	 * @throws RuntimeException ako je header već generiran
	 * @throws NullPointerException ako je statusni tekst <code>null</code>
	 */
	public void setStatusText(String statusText) {
		checkIfHeaderGenerated();
		this.statusText = Objects.requireNonNull(statusText);
	}

	/**
	 * Metoda koja postavlja mime tip na onaj koji je predan.
	 * 
	 * @param mimeType tip
	 * @throws RuntimeException ako je header već generiran
	 * @throws NullPointerException ako je tip <code>null</code>
	 */
	public void setMimeType(String mimeType) {
		checkIfHeaderGenerated();
		this.mimeType = Objects.requireNonNull(mimeType);
	}

	/**
	 * Metoda koja postavlja duljinu sadržaja na onu koja je predana.
	 * 
	 * @param contentLength duljina sadržaja
	 * @throws RuntimeException ako je header već generiran
	 * @throws NullPointerException ako je duljina <code>null</code>
	 */
	public void setContentLength(Long contentLength) {
		checkIfHeaderGenerated();
		this.contentLength = Objects.requireNonNull(contentLength);
	}

	/**
	 * Getter koji vraća parametre.
	 * 
	 * @return parametri
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}

	/**
	 * Getter koji vraća privremene parametre
	 * 
	 * @return privremeni parametri
	 */
	public Map<String, String> getTemporaryParameters() {
		return temporaryParameters;
	}

	/**
	 * Metoda koja postavlja privremene parametre.
	 * 
	 * @param temporaryParameters privremeni parametri
	 */
	public void setTemporaryParameters(Map<String, String> temporaryParameters) {
		this.temporaryParameters = temporaryParameters;
	}

	/**
	 * Metoda koja vraća trajne parametre.
	 * 
	 * @return trajne parametre
	 */
	public Map<String, String> getPersistentParameters() {
		return persistentParameters;
	}

	/**
	 * Metoda koja postavlja trajne parametre.
	 * 
	 * @param persistentParameters trajni parametri
	 */
	public void setPersistentParameters(Map<String, String> persistentParameters) {
		this.persistentParameters = persistentParameters;
	}
	
	/**
	 * Metoda koja vraća vrijednost iz mape parametara 
	 * (ili <code>null</code> ako vrijednost nije pohranjena)
	 * 
	 * @param name ime parametra
	 * @return vrijednost parametra ili <code>null</code> ako ne postoji parametar danog imena
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
	/**
	 * Metoda koja vraća imena svih parametara u nepromjenjivoj mapi.
	 * 
	 * @return imena svih parametara
	 */
	public Set<String> getParameterNames() {
		return parameterNames;
	}
	
	/**
	 * Metoda koja vraća vrijednost iz mape trajnih parametara 
	 * (ili <code>null</code> ako vrijednost nije pohranjena)
	 * 
	 * @param name ime trajnog parametra
	 * @return vrijednost trajnog parametra ili <code>null</code> ako ne postoji parametar danog imena
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Metoda koja vraća imena svih trajnih parametara u nepromjenjivoj mapi.
	 * 
	 * @return imena svih parametara
	 */
	public Set<String> getPersistentParameterNames() {
		Set<String> parameterSet = new HashSet<>();
		persistentParameters.forEach((k, v) -> parameterSet.add(k));
		return Collections.unmodifiableSet(parameterSet);
	}
	
	/**
	 * Metoda koja pohranjuje ime i vrijednost parametra u mapi trajnih parametara.
	 * 
	 * @param name ime parametra
	 * @param value vrijednost parametra
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}
	
	/**
	 * Metoda koja uklanja vrijednost parametra iz mape trajnih parametara.
	 * 
	 * @param name ime parametra kojeg treba ukloniti
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}
	
	/**
	 * Metoda koja vraća vrijednost iz mape privremenih parametara 
	 * (ili <code>null</code> ako vrijednost nije pohranjena)
	 * 
	 * @param name ime privremenog parametra
	 * @return vrijednost privremenog parametra ili <code>null</code> ako ne postoji parametar danog imena
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}
	
	/**
	 * Metoda koja vraća imena svih privremenih parametara u nepromjenjivoj mapi.
	 * 
	 * @return imena svih parametara
	 */
	public Set<String> getTemporaryParameterNames() {
		Set<String> parameterSet = new HashSet<>();
		temporaryParameters.forEach((k, v) -> parameterSet.add(k));
		return Collections.unmodifiableSet(parameterSet);
	}
	
	/**
	 * Metoda koja vraća identifikator trenutnog korisničkog sessiona.
	 * 
	 * @return identifikator
	 */
	public String getSessionID() {
		return sid;
	}
	
	/**
	 * Metoda koja pohranjuje ime i vrijednost parametra u mapi privremenih parametara.
	 * 
	 * @param name ime privremenog parametra
	 * @param value vrijednost privremenog parametra
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}
	
	/**
	 * Metoda koja uklanja vrijednost parametra iz mape privremenih parametara.
	 * 
	 * @param name ime parametra kojeg treba ukloniti
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Metoda koja piše sve dane podatke u izlazni tok podataka.
	 * 
	 * @param data podaci
	 * @return referencu na trenutni zahtjev
	 * @throws IOException ako dođe do greške pri pisanju
	 */
	public RequestContext write(byte[] data) throws IOException {
		checkHeader();
		outputStream.write(data);
		return this;
	}
	
	/**
	 * Metoda koja piše dane podatke duljine <code>len</code> 
	 * u izlazni tok podataka. Pisanje počinje od <code>offset</code>.
	 * 
	 * @param data podaci
	 * @param offset početni pomak pri pisanju
	 * @param len broj bajtova koji se zapisuje
	 * @return referencu na trenutni zahtjev
	 * @throws IOException ako dođe do greške pri pisanju
	 * @throws IndexOutOfBoundsException ako je <code>offset</code> ili <code>len</code> negativan 
	 * ili ako je <code>offset</code> + <code>len</code> veće od <code>data.length</code>
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		checkHeader();
		outputStream.write(data, offset, len);
		return this;
	}
	
	/**
	 * Metoda koja prima tekst, pretvara ga u bajtove pomoću pohranjene kodne stranice
	 * te zapisuje bajtove u izlazni tok podataka.
	 * 
	 * @param text tekst koji treba zapisati
	 * @return referenca na trenutni zahtjev
	 * @throws IOException ako dođe do greške pri pisanju
	 */
	public RequestContext write(String text) throws IOException {
		checkHeader();
		outputStream.write(text.getBytes(charset));
		return this;
	}
	
	/**
	 * Metoda koja dodaje predani kolačić.
	 * 
	 * @param cookie kolačić
	 * @throws RuntimeException ako je header već generiran
	 * @throws NullPointerException ako je predana vrijednost <code>null</code>
	 */
	public void addRCCookie(RCCookie cookie) {
		checkIfHeaderGenerated();
		outputCookies.add(Objects.requireNonNull(cookie));
	}
	
	/**
	 * Metoda koja vraća pohranjeni dispatcher.
	 * 
	 * @return dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * Pomoćna metoda koja provjerava je li generiran header.
	 * Ako je, baca se iznimka.
	 * 
	 * @throws RuntimeException ako je header generiran.
	 */
	private void checkIfHeaderGenerated() {
		if(headerGenerated) {
			throw new RuntimeException("Cannot change properties after header is generated.");
		}
	}
	
	/**
	 * Pomoćna metoda koja provjerava je li header generiran. Ako je, ništa se ne događa.
	 * Ako nije, generira se novi header na temelju trenutnih podataka.
	 * 
	 * @throws IOException ako dođe do greške pri pisanju
	 */
	private void checkHeader() throws IOException {
		if(!headerGenerated) {
			generateHeader();
		}
	}
	
	/**
	 * Pomoćna metoda za generiranje headera za trenutne podatke.
	 * 
	 * @throws IOException ako dođe do greške pri pisanju u izlazni tok podataka
	 */
	private void generateHeader() throws IOException {
		charset = Charset.forName(encoding);
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + 
				"Content-Type: " + mimeType);
		if(mimeType.startsWith("text/")) {
			sb.append("; charset=" + encoding);
		}
		sb.append("\r\n");
		if(contentLength != null) {
			sb.append("Content-Length: " + contentLength + "\r\n");
		}
		if(!outputCookies.isEmpty()) {
			outputCookies.forEach(rc -> {
				sb.append("Set-Cookie: " + rc.getName() + "=\"" + rc.getValue() + "\"");
				if(rc.getDomain() != null) {
					sb.append("; Domain=" + rc.getDomain());
				}
				if(rc.getPath() != null) {
					sb.append("; Path=" + rc.getPath());
				}
				if(rc.getMaxAge() != null) {
					sb.append("; Max-Age=" + rc.getMaxAge());
				}
				sb.append("\r\n");
			});
		}
		
		sb.append("\r\n");
		outputStream.write(sb.toString().getBytes(StandardCharsets.ISO_8859_1));
		headerGenerated = true;
	}
}
