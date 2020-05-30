package hr.fer.zemris.java.hw11.jnotepadpp.models;

/**
 * Slušač za objekte koji implementiraju sučelje {@link SingleDocumentModel}.
 * 
 * @author Maja Radočaj
 *
 */
public interface SingleDocumentListener {
	/**
	 * Metoda koja se poziva kad se promijeni dokument.
	 * 
	 * @param model dokument čije stanje se promijenilo
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Metoda koja se poziva kad se promijeni put do dokumenta.
	 * 
	 * @param model dokument čiji path se promijenilo
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}