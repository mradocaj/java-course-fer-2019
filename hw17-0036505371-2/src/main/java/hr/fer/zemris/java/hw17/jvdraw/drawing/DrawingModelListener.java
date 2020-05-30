package hr.fer.zemris.java.hw17.jvdraw.drawing;

/**
 * Sučelje koji modelira slušača promjena nad modelom za crtanje.
 * 
 * @author Maja Radočaj
 *
 */
public interface DrawingModelListener {
	
	/**
	 * Metoda koja se poziva pri dodavanju novog objekta u model.
	 * 
	 * @param source izvor promjene
	 * @param index0 indeks objekta
	 * @param index1 indeks objekta
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Metoda koja se poziva pri uklanjanju objekta iz modela.
	 * 
	 * @param source izvor promjene
	 * @param index0 indeks objekta
	 * @param index1 indeks objekta
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);
	
	/**
	 * Metoda koja se poziva pri izmjeni nekog objekta u modelu.
	 * 
	 * @param source izvor promjene
	 * @param index0 indeks objekta
	 * @param index1 indeks objekta
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
