package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Razred koji predstavlja dobavljača prijevoda za dane ključeve.
 * <p>Može se stvoriti samo jedan primjerak ovog razreda koji pohranjuje jezik.
 * 
 * @author Maja Radočaj
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * Jezik.
	 */
	private String language;
	/**
	 * Resursi.
	 */
	private ResourceBundle bundle;
	/**
	 * Primjerak razreda.
	 */
	private static final LocalizationProvider instance = new LocalizationProvider();

	/**
	 * Javni konstruktor.
	 */
	private LocalizationProvider() {
		language = "en";
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.languages.prijevodi",  
				Locale.forLanguageTag(language));
	}
	
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}
	
	/**
	 * Metoda koja vraća primjerak razreda.
	 * 
	 * @return primjerak razreda
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	/**
	 * Metoda koja postavlja jezik.
	 * 
	 * @param language jezik
	 * @throws NullPointerException ako je predani jezik <code>null</code>
	 */
	public void setLanguage(String language) {
		this.language = Objects.requireNonNull(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.languages.prijevodi",  
				Locale.forLanguageTag(language));
		fire();
	}
	
	/**
	 * Metoda koja vraća trenutni jezik.
	 * 
	 * @return trenutni jezik
	 */
	public String getLanguage() {
		return language;
	}

	
}
