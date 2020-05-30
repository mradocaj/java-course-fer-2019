package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Objects;

import javax.swing.JMenu;

/**
 * Izbornik sa mogućnošću dinamičke promjene jezika.
 * 
 * @author Maja Radočaj
 *
 */
public class LJMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	/**
	 * Provider koji dobavlja jezik.
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
	 * Javni konstruktor.
	 * 
	 * @param key ključ za prijevod
	 * @param lp dobavljač prijevoda
	 * @throws NullPointerException ako je <code>key</code> ili <code>lp</code> <code>null</code>
	 */
	public LJMenu(String key, ILocalizationProvider lp) {
		prov = Objects.requireNonNull(lp);
		this.key = Objects.requireNonNull(key);
		updateMenu();		
		listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				updateMenu();
			}
		};
		prov.addLocalizationListener(listener);
	}
	
	/**
	 * Pomoćna metoda koja postavlja prijevod izbornika.
	 */
	private void updateMenu() {
		String translation = prov.getString(key);
		setText(translation);
	}
}
