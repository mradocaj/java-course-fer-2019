package hr.fer.zemris.java.tecaj_13.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Formular za prijavu korisnika.
 * 
 * @author Maja Radočaj
 *
 */
public class LoginForm {

	/**
	 * Nadimak.
	 */
	private String nick = "";
	/**
	 * Hash lozinke.
	 */
	private String passwordHash = "";
	/**
	 * Greške.
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
		this.nick = prepare(req.getParameter("nick"));
		this.passwordHash = Util.cryptPassword(prepare(req.getParameter("password")));
	}
	
	/**
	 * Metoda koja puni formular iz postojećeg korisnika.
	 * 
	 * @param user korisnik
	 */
	public void fillFromUser(BlogUser user) {
		this.nick = prepare(user.getNick());
		this.passwordHash = prepare(user.getPasswordHash());
	}

	/**
	 * Temeljem sadržaja ovog formulara puni svojstva predanog domenskog
	 * objekta. Metodu ne bi trebalo pozivati ako formular prethodno nije
	 * validiran i ako nije utvrđeno da nema pogrešaka.
	 * 
	 * @param r domenski objekt koji treba napuniti
	 */
	public void fillUser(BlogUser user) {
		user.setNick(this.nick);
		user.setPasswordHash(this.passwordHash);
	}
	
	/**
	 * Metoda koja provjerava ispravnost formulara.
	 * Ako postoje greške, one se dodaju u mapu.
	 */
	public void validate() {
		errors.clear();
		
		if(this.nick.isEmpty()) {
			errors.put("nick", "Nadimak je obavezan!");
		} else if(this.nick.length() > 50) {
			errors.put("nick", "Nadimak ne smije biti dulji od 50 znakova!");
		}
		
		if(this.passwordHash.isEmpty()) {
			errors.put("password", "Lozinka je obavezna!");
		}
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

	/**
	 * Getter za nadimak.
	 * 
	 * @return nadimak
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Setter za nadimak.
	 * 
	 * @param nick nadimak
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Getter za hash lozinke.
	 * 
	 * @return hash lozinke
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Setter za hash lozinke.
	 * 
	 * @param passwordHash hash lozinke
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	/**
	 * Setter za lozinku.
	 * 
	 * @param password lozinka
	 */
	public void setPassword(String password) {
		this.passwordHash = Util.cryptPassword(password);
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
	 * Setter za grešku.
	 * 
	 * @param name ime greške
	 * @param value tekst greške
	 */
	public void setError(String name, String value) {
		this.errors.put(name, value);
	}
}
