package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Objects;

import javax.swing.JLabel;

/**
 * Labela za prikaz duljine dokumenta sa dinamičkom izmjenom jezika.
 * 
 * @author Maja Radočaj
 *
 */
public class LJLengthLabel extends JLabel {

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
	 * @throws NullPointerException ako je neki od argumenata <code>null</code>
	 */
	public LJLengthLabel(String key, ILocalizationProvider lp) {
		this.prov = Objects.requireNonNull(lp);
		this.key = Objects.requireNonNull(key);
		updateLabel();		
		
		listener = new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				updateLabel();
			}
		};
		prov.addLocalizationListener(listener);
	}
	
	/**
	 * Metoda za ispis labele.
	 */
	private void updateLabel() {
		String translation = prov.getString(key);
		String current = getText();
		String[] parts = current.split(":");
		if(parts.length == 2) {
			setText(translation + ":" + parts[1]);
		} else {
			setText(translation + ":0");
		}
	}
	
	/**
	 * Metoda koja postavlja vrijednost na predanu.
	 * 
	 * @param number predana duljina koju treba ispisati
	 */
	public void labelChange(int number) {
		String translation = prov.getString(key);
		setText(translation + ":" + number);
	}
}
