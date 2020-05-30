package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Razred koji modelira jednostavni geometrijski objekt.
 * 
 * @author Maja Radočaj
 *
 */
public abstract class GeometricalObject {

	/**
	 * Slušači geometrijskog objekta.
	 */
	private List<GeometricalObjectListener> listeners = new ArrayList<>();

	/**
	 * Metoda za prihvaćanje visitora.
	 * 
	 * @param v visitor
	 */
	public abstract void accept(GeometricalObjectVisitor v);

	/**
	 * Metoda koja vraća uređivač geometrijskog objekta.
	 * 
	 * @return uređivač geometrijskog objekta
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	/**
	 * Metoda za dodavanje slušača geometrijskog objekta.
	 * 
	 * @param l slušač geometrijskog objekta
	 * @throws NullPointerException ako se pokuša dodati <code>null</code>
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	/**
	 * Metoda za uklanjanje slušača geometrijskog objekta.
	 * 
	 * @param l slušač geometrijskog objekta
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	}

}
