package hr.fer.zemris.java.hw17.jvdraw.objects;

import javax.swing.JPanel;

/**
 * Razred koji predstavlja nadrazred za sve uređivače objekata.
 * 
 * @author Maja Radočaj
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Metoda koja se poziva za provjeru korisnikovog unosa pri uređivanju objekta.
	 */
	public abstract void checkEditing();

	/**
	 * Metoda koja uređuje objekt,
	 */
	public abstract void acceptEditing();
}
