package hr.fer.zemris.java.tecaj_13.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;

/**
 * Formular za dodavanje komentara.
 * 
 * @author Maja Radočaj
 *
 */
public class CommentForm {

	/**
	 * EMail.
	 */
	private String email = "";
	/**
	 * Komentar.
	 */
	private String message = "";
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
	 * Metoda koja iz zahtjeva popunjava formular.
	 * 
	 * @param req zahtjev
	 */
	public void fillFromRequest(HttpServletRequest req) {
		this.email = prepare(req.getParameter("email"));
		this.message = prepare(req.getParameter("message"));
	}
	
	/**
	 * Metoda koja iz postojećeg komentara popunjava formular.
	 * 
	 * @param comment komentar
	 */
	public void fillFromComment(BlogComment comment) {
		this.email = prepare(comment.getUsersEMail());
		this.message = prepare(comment.getMessage());
	}

	/**
	 * Temeljem sadržaja ovog formulara puni svojstva predanog domenskog
	 * objekta. Metodu ne bi trebalo pozivati ako formular prethodno nije
	 * validiran i ako nije utvrđeno da nema pogrešaka.
	 * 
	 * @param r domenski objekt koji treba napuniti
	 */
	public void fillComment(BlogComment comment) {
		comment.setUsersEMail(email);
		comment.setMessage(this.message);
	}
	
	/**
	 * Metoda koja provjerava ispravnost formulara.
	 * Ako postoje greške, one se dodaju u mapu.
	 */
	public void validate() {
		errors.clear();
		
		if(this.email.isEmpty()) {
			errors.put("email", "Email je obavezan!");
		} else if(this.email.length() > 100) {
			errors.put("email", "Email ne smije biti dulji od 200 znakova!");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if(l < 3 || p == -1 || p == 0 || p == l - 1) {
				errors.put("email", "Email nije ispravnog formata.");
			}
		}
		
		if(this.message.isEmpty()) {
			errors.put("message", "Komentar je obavezan!");
		} else if(this.message.length() > 4096) {
			errors.put("message", "Komentar ne smije biti dulji od 50 znakova!");
		}
	}
	
	/**
	 * Metoda koja za dani string vraća taj string ili "" ako je predana <code>null</code>.
	 * @param s string
	 * @return string
	 */
	private String prepare(String s) {
		if(s == null) return "";
		return s.trim();
	}

	/**
	 * Getter za mapu greški.
	 * 
	 * @return greške
	 */
	public Map<String, String> getErrors() {
		return errors;
	}

	/**
	 * Setter za mapu greški.
	 * 
	 * @param errors greške
	 */
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
	
	/**
	 * Metoda koja postavlja novu grešku.
	 * 
	 * @param name ime greške
	 * @param value tekst greške
	 */
	public void setError(String name, String value) {
		this.errors.put(name, value);
	}

	/**
	 * Getter za email.
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter za email.
	 * 
	 * @param email email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter za poruku.
	 * 
	 * @return poruka
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter za poruku.
	 * 
	 * @param message poruka
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
