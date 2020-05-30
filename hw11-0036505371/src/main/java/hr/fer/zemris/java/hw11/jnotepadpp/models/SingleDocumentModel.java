package hr.fer.zemris.java.hw11.jnotepadpp.models;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Sučelje koje modelira jedan dokument.
 * <p>Svaki dokument ima svoju putanju (koja može biti <code>null</code>) i tekstualnu komponentu.
 * 
 * @author Maja Radočaj
 *
 */
public interface SingleDocumentModel {
	/**
	 * Metoda koja vraća Swing komponentu za uređivanje teksta dokumenta.
	 * 
	 * @return komponenta za uređivanje teksta
	 */
	JTextArea getTextComponent();

	/**
	 * Metoda koja vraća putanju do dokumenta.
	 * 
	 * @return putanja do dokumenta.
	 */
	Path getFilePath();

	/**
	 * Metoda koja postavlja putanju do dokumenta.
	 * 
	 * @param path putanja do dokumenta
	 */
	void setFilePath(Path path);

	/**
	 * Metoda koja vraća status modifikacije trenutnog dokumenta.
	 * 
	 * @return status modifikacije trenutnog dokumenta 
	 * (<code>true</code> ako je dokument modificiran, <code>false</code> ako nije)
	 */
	boolean isModified();

	/**
	 * Metoda koja postavlja status modifikacije trenutnog dokumenta.
	 * 
	 * @param modified <code>true</code> ako želimo postaviti da je modificiran, <code>false</code> ako ne
	 */
	void setModified(boolean modified);

	/**
	 * Metoda koja dodaje slušača dokumenta.
	 * 
	 * @param l slušač dokumenta
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Metoda koja uklanja slušača dokumenta.
	 * 
	 * @param l slušač dokumenta
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
