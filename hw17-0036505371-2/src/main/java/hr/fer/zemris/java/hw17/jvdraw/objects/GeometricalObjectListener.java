package hr.fer.zemris.java.hw17.jvdraw.objects;

/**
 * Razred koji modelira slušača nad geometrijskim objektom.
 * 
 * @author Maja Radočaj
 *
 */
public interface GeometricalObjectListener {
	
	/**
	 * Metoda koja se poziva pri promjeni geometrijskog objekta.
	 * 
	 * @param o izmijenjeni geometrijski objekt
	 */
	public void geometricalObjectChanged(GeometricalObject o);
}
