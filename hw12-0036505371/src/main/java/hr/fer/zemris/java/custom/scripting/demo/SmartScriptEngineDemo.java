package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Program koji služi za demonstraciju rada metoda {@link SmartScriptEngine}.
 * Pokretanjem se izvode i ispisuju rezultati skripti osnovni.smscr, zbrajanje.smscr,
 * brojPoziva.smscr, fibonacci.smscr i fibonaccih.smscr.
 * 
 * @author Maja Radočaj
 *
 */
public class SmartScriptEngineDemo {

	/**
	 * Glavni program.
	 * 
	 * @param args argumenti naredbenog retka
	 * @throws IOException ako izvođenje skripti pođe po zlu
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("\n*-------------- osnovni.smscr ---------------*\n");
		demo1("./webroot/scripts/osnovni.smscr");
		System.out.println("\n*-------------- zbrajanje.smscr ---------------*\n");
		demo2("./webroot/scripts/zbrajanje.smscr");
		System.out.println("\n*------------- brojPoziva.smscr ----------------*\n");
		demo3("./webroot/scripts/brojPoziva.smscr");
		System.out.println("\n*------------- fibonacci.smscr ----------------*\n");
		demo1("./webroot/scripts/fibonacci.smscr");
		System.out.println("\n*-------------- fibonaccih.smscr ---------------*\n");
		demo1("./webroot/scripts/fibonaccih.smscr");
	}

	/**
	 * Prvi demo.
	 * 
	 * @param path put do skripte
	 * @throws IOException ako dođe do greške
	 */
	private static void demo1(String path) throws IOException {
		String documentBody = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);

		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		
		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}

	/**
	 * Drugi demo.
	 * 
	 * @param path put do skripte
	 * @throws IOException ako dođe do greške
	 */
	private static void demo2(String path) throws IOException {
		String documentBody = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);

		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		parameters.put("a", "4");
		parameters.put("b", "2");
		
		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}

	/**
	 * Treći demo.
	 * 
	 * @param path put do skripte
	 * @throws IOException ako dođe do greške
	 */
	private static void demo3(String path) throws IOException {
		String documentBody = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);

		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		persistentParameters.put("brojPoziva", "3");
		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters, cookies);
		
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), rc).execute();
		System.out.println("Vrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));
	}

}
