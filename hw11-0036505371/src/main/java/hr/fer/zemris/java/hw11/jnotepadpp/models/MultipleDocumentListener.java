package hr.fer.zemris.java.hw11.jnotepadpp.models;

/**
 * Slušač za objekte koji implementiraju sučelje {@link MultipleDocumentModel}.
 * 
 * @author Maja Radočaj
 *
 */
public interface MultipleDocumentListener {
	/**
	 * Metoda koja se poziva kad se promijeni trenutni dokument.
	 * 
	 * @param previousModel dokument prije promjene
	 * @param currentModel dokument poslije promjene
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	
	/**
	 * Metoda koja se poziva kad se doda novi dokument.
	 * 
	 * @param model model koji se dodaje
	 */
	void documentAdded(SingleDocumentModel model);

	/**
	 * Metoda koja se poziva kad se uklanja dokument.
	 * 
	 * @param model model koji se uklanja
	 */
	void documentRemoved(SingleDocumentModel model);
}
