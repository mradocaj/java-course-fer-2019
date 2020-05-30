package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Objects;

/**
 * Most koji služi kao dekorator za {@link ILocalizationProvider}.
 * 
 * @author Maja Radočaj
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * Zastavica spojenosti.
	 */
	private boolean connected;
	/**
	 * Dobavljač prijevoda.
	 */
	private ILocalizationProvider parent;
	/**
	 * Slušač.
	 */
	private ILocalizationListener listener;
	/**
	 * Jezik.
	 */
	private String language;
	
	/**
	 * Javni konstruktor.
	 * 
	 * @param parent dobavljač koji se dekorira
	 * @throws NullPointerException ako je predani argument <code>null</code>
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = Objects.requireNonNull(parent);
		language = parent.getCurrentLanguage();
		listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				language = parent.getCurrentLanguage();
				fire();
			}
		};
	}
	
	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}
	
	/**
	 * Metoda za spajanje sa slušačem.
	 */
	public void connect() {
		if(connected) return;
		parent.addLocalizationListener(listener);
		connected = true;
	}
	
	/**
	 * Metoda za otspajanje od slušača.
	 */
	public void disconnect() {
		parent.removeLocalizationListener(listener);
		connected = false;
	}

	
}
