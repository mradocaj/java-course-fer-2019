package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Objects;

import javax.swing.JLabel;

/**
 * Labela za prikaz trenutnih informacija o dokumentu sa dinamičkom izmjenom jezika.
 * 
 * @author Maja Radočaj
 *
 */
public class LJInfoLabel extends JLabel {

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
	 * Ključ za linije.
	 */
	private String key1; 
	/**
	 * Ključ za retke.
	 */
	private String key2; 
	/**
	 * Ključ za selekciju.
	 */
	private String key3; 
	
	/**
	 * Javni konstruktor.
	 * 
	 * @param key1 ključ za linije
	 * @param key2 ključ za retke
	 * @param key3 ključ za selekciju
	 * @param lp dobavljač prijevoda
	 * @throws NullPointerException ako je neki od predanih argumenata <code>null</code>
	 */
	public LJInfoLabel(String key1, String key2, String key3, ILocalizationProvider lp) {
		prov = Objects.requireNonNull(lp);
		this.key1 = Objects.requireNonNull(key1);
		this.key2 = Objects.requireNonNull(key2);
		this.key3 = Objects.requireNonNull(key3);
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
	 * Pomoćna metoda koja osvježava stanje labele.
	 */
	private void updateLabel() {
		String line = prov.getString(key1);
		String col = prov.getString(key2);
		String sel = prov.getString(key3);
		String current = getText();
		String[] parts = current.split(" ");
		
		if(parts.length == 3) {
			String lineNum = parts[0].split(":")[1];
			String colNum = parts[1].split(":")[1];
			String selNum = parts[2].split(":")[1];
			setText(String.format("%s:%s %s:%s %s:%s", line, lineNum, col, colNum, sel, selNum));
		} else {
			setText(String.format("%s:1 %s:0 %s:0", line, col, sel));
		}
	}
	
	/**
	 * Metoda koja postavlja vrijednosti labele na dane vrijednosti.
	 * 
	 * @param line linija
	 * @param col redak
	 * @param sel selekcija
	 */
	public void labelChange(int line, int col, int sel) {
		String lineString = prov.getString(key1);
		String colString = prov.getString(key2);
		String selString = prov.getString(key3);
		setText(String.format("%s:%d %s:%d %s:%d", lineString, line, colString, col, selString, sel));
	}
}
