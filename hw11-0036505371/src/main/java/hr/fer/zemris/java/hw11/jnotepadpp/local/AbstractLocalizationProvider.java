package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Apstraktni razred koji predstavlja dobavljača prijevoda.
 * 
 * @author Maja Radočaj
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * Slušači.
	 */
	private List<ILocalizationListener> listeners;
	
	/**
	 * Javni konstuktor.
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}

	/**
	 * Metoda koja obaviještava sve slušače da je došlo do promjene jezika.
	 */
	public void fire() {
		for(ILocalizationListener listener : listeners) {
			listener.localizationChanged();
		}
	}
	
}
