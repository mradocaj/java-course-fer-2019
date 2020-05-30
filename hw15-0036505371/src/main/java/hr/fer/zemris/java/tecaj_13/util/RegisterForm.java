package hr.fer.zemris.java.tecaj_13.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Formular za registriranje novog korisnika.
 * 
 * @author Maja Radočaj
 *
 */
public class RegisterForm {

	/**
	 * Ime.
	 */
	private String firstName = "";
	/**
	 * Prezime.
	 */
	private String lastName = "";
	/**
	 * Email.
	 */
	private String email = "";
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
		this.firstName = prepare(req.getParameter("firstName"));
		this.lastName = prepare(req.getParameter("lastName"));
		this.email = prepare(req.getParameter("email"));
		this.nick = prepare(req.getParameter("nick"));
		this.passwordHash = Util.cryptPassword(prepare(req.getParameter("password")));
	}
	
	/**
	 * Metoda koja puni formular iz postojećeg korisnika.
	 * 
	 * @param user korisnik
	 */
	public void fillFromUser(BlogUser user) {
		this.firstName = prepare(user.getFirstName());
		this.lastName = prepare(user.getLastName());
		this.email = prepare(user.getEmail());
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
		user.setFirstName(this.firstName);
		user.setLastName(this.lastName);
		user.setEmail(this.email);
		user.setNick(this.nick);
		user.setPasswordHash(this.passwordHash);
	}
	
	/**
	 * Metoda koja provjerava ispravnost formulara.
	 * Ako postoje greške, one se dodaju u mapu.
	 */
	public void validate() {
		errors.clear();
		
		if(this.firstName.isEmpty()) {
			errors.put("firstName", "Ime je obavezno!");
		} else if(this.firstName.length() > 50) {
			errors.put("firstName", "Ime ne smije biti dulje od 50 znakova!");
		}
		
		if(this.lastName.isEmpty()) {
			errors.put("lastName", "Prezime je obavezno!");
		} else if(this.lastName.length() > 50) {
			errors.put("lastName", "Prezime ne smije biti dulje od 50 znakova!");
		}
		
		if(this.email.isEmpty()) {
			errors.put("email", "Email je obavezan!");
		} else if(this.email.length() > 200) {
			errors.put("email", "Email ne smije biti dulji od 200 znakova!");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if(l < 3 || p == -1 || p == 0 || p == l - 1) {
				errors.put("email", "Email nije ispravnog formata!");
			}
		}
		
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
	 * @param value poruka greške
	 */
	public void setError(String name, String value) {
		this.errors.put(name, value);
	}

	/**
	 * Getter za ime.
	 * 
	 * @return ime
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter za ime.
	 * 
	 * @param firstName ime
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter za prezime.
	 * 
	 * @return prezime
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter za prezime.
	 * 
	 * @param lastName prezime
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
}
