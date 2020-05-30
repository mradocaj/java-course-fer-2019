package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * Razred koji modelira akciju koja može dinamički mijenjati jezik.
 * 
 * @author Maja Radočaj
 *
 */
public abstract class LocalizableAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	/**
	 * Dobavljač prijevoda.
	 */
	private ILocalizationProvider prov;
	/**
	 * Slušač.
	 */
	private ILocalizationListener listener;
	/**
	 * Ključ.
	 */
	private String key; 
	
	/**
	 * Javni konstuktor.
	 * 
	 * @param key ključ
	 * @param lp dobavljač
	 * @throws NullPointerException ako je neki od predanih argumenata <code>null</code>
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		prov = Objects.requireNonNull(lp);
		this.key = Objects.requireNonNull(key);
		setup();		
		listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				setup();
			}
		};
		prov.addLocalizationListener(listener);
	}

	/**
	 * Postavljanje stanja akcije s obzirom na trenutni jezik.
	 */
	private void setup() {
		String translation = prov.getString(key);
		putValue(Action.NAME, translation);
		putValue(Action.SHORT_DESCRIPTION, prov.getString(key + "_desc"));
	}
}
