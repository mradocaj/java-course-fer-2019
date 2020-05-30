package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Sučelje koje modelira objekte koji mogu vratiti prijevod za neki ključ.
 * 
 * @author Maja Radočaj
 *
 */
public interface ILocalizationProvider {

	/**
	 * Metoda koja dodaje slušača.
	 * 
	 * @param l slušač
	 */
	void addLocalizationListener(ILocalizationListener l);
	
	/**
	 * Metoda koja uklanja slušača.
	 * 
	 * @param l slušač
	 */
	void removeLocalizationListener(ILocalizationListener l);
	
	/**
	 * Metoda koja vraća prijevod za dani ključ.
	 * 
	 * @param key ključ
	 * @return prijevod za dani ključ
	 */
	String getString(String key);
	
	/**
	 * Metoda koja vraća trenutni jezik.
	 * 
	 * @return trenutni jezik
	 */
	String getCurrentLanguage();
}
