package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Sučelje koji modelira slušače lokalizacija.
 * 
 * @author Maja Radočaj
 *
 */
public interface ILocalizationListener {

	/**
	 * Metoda koja se poziva pri promjeni lokalizacije (jezika na koji se prevode ključevi).
	 */
	void localizationChanged();
}
