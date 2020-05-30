package hr.fer.zemris.java.tecaj_13.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

/**
 * Formular za stvaranje zapisa.
 * 
 * @author Maja Radočaj
 *
 */
public class EntryForm {

	/**
	 * Naslov.
	 */
	private String title;
	/**
	 * Tekst.
	 */
	private String text;
	/**
	 * Mapa greški.
	 */
	private Map<String, String> errors = new HashMap<>();
	
	/**
	 * Getter za pogrešku sa imenom name.
	 * 
	 * @param name ime greške
	 * @return grešku sa zadanim imenom
	 */
	public String getError(String name) {
		return errors.get(name);
	}
	
	/**
	 * Metoda za provjeru ima li greški u formularu.
	 * 
	 * @return <code>true</code> ako ima greški, <code>false</code> ako nema
	 */
	public boolean hasError() {
		return !errors.isEmpty();
	}
	
	/**
	 * Metoda koja provjerava ima li greške za imenom name.
	 * 
	 * @param name ime greške
	 * @return <code>true</code> ako ima, <code>false</code> ako ne
	 */
	public boolean hasError(String name) {
		return errors.containsKey(name);
	}
	
	/**
	 * Metoda koja puni formular iz zahtjeva.
	 * 
	 * @param req zahtjev
	 */
	public void fillFromRequest(HttpServletRequest req) {
		this.title = prepare(req.getParameter("title"));
		this.text = prepare(req.getParameter("text"));
	}
	
	/**
	 * Metoda koja puni formular iz postojećeg zapisa.
	 * 
	 * @param entry zapis
	 */
	public void fillFromEntry(BlogEntry entry) {
		this.title = prepare(entry.getTitle());
		this.text = prepare(entry.getText());
	}

	/**
	 * Temeljem sadržaja ovog formulara puni svojstva predanog domenskog
	 * objekta. Metodu ne bi trebalo pozivati ako formular prethodno nije
	 * validiran i ako nije utvrđeno da nema pogrešaka.
	 * 
	 * @param r domenski objekt koji treba napuniti
	 */
	public void fillEntry(BlogEntry entry) {
		entry.setTitle(this.title);
		entry.setText(this.text);
	}
	
	/**
	 * Metoda koja provjerava ispravnost formulara.
	 * Ako postoje greške, one se dodaju u mapu.
	 */
	public void validate() {
		errors.clear();
		
		if(this.title.isEmpty()) {
			errors.put("title", "Naslov je obavezan.");
		} else if(this.title.length() > 200) {
			errors.put("title", "Naslov ne smije biti duži od 200 znakova!");
		}
		
		if(this.text.length() > 4096) {
			errors.put("text", "Tekst ne smije biti duži od 4096 znakova!");
		}
	}
	
	/**
	 * Getter za naslov.
	 * 
	 * @return naslov
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter za naslov.
	 * 
	 * @param title naslov
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter za tekst.
	 * 
	 * @return tekst
	 */
	public String getText() {
		return text;
	}

	/**
	 * Setter za tekst.
	 * 
	 * @param text tekst
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Getter za greške.
	 * 
	 * @return greške
	 */
	public Map<String, String> getErrors() {
		return errors;
	}

	/**
	 * Setter za greške.
	 * 
	 * @param errors greške
	 */
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

	/**
	 * Metoda za obradu stringa.
	 * 
	 * @param s string 
	 * @return pripremljeni string
	 */
	private String prepare(String s) {
		if(s == null) return "";
		return s.trim();
	}
}
