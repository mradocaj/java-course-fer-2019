package hr.fer.zemris.java.hw11.jnotepadpp.models;

import java.nio.file.Path;

/**
 * Model koji može pohranjivati 0 ili više dokumenata.
 * Uvijek postoji trenutni dokument - onaj koji se prikazuje korisniku.
 * 
 * @author Maja Radočaj
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	/**
	 * Metoda koja stvara novi dokument.
	 * 
	 * @return novi dokument
	 */
	SingleDocumentModel createNewDocument();
	
	/**
	 * Metoda koja vraća trenutni dokument.
	 * 
	 * @return trenutni dokument
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 * Metoda koja učitava dokument sa putanje <code>path</code>.
	 * @param path putanja s koje treba učitati dokument
	 * 
	 * @return učitani dokument
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Metoda koja sprema dokument <code>model</code> na adresu <code>path</code>.
	 * 
	 * @param model dokument koji treba spremiti
	 * @param newPath putanja na koju treba spremiti novi dokument
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Metoda za zatvaranje odabranog dokumenta.
	 * 
	 * @param model dokument kojeg treba zatvoriti
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Metoda za dodavanje slušača dokumenta.
	 * 
	 * @param l slušač
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Metoda za uklanjanje slušača dokumenta.
	 * 
	 * @param l slušač
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Metoda koja vraća broj dokumenata pohranjenih u modelu.
	 * 
	 * @return broj dokumenata
	 */
	int getNumberOfDocuments();

	/**
	 * Metoda koja vraća dokument sa predanog indeksa.
	 * 
	 * @param index indeks
	 * @return dokument na danom indeksu
	 */
	SingleDocumentModel getDocument(int index);
}
