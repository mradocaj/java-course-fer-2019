package hr.fer.zemris.java.hw17.jvdraw.components;

import java.awt.Color;

/**
 * Sučelje koje modelira dobavljača boje.
 * 
 * @author Maja Radočaj
 *
 */
public interface IColorProvider {

	/**
	 * Metoda koja vraća trenutnu boju.
	 * 
	 * @return trenutna boja
	 */
	public Color getCurrentColor();

	/**
	 * Metoda za dodavanje slušača nad promjenama boja.
	 * 
	 * @param l slušač
	 * @throws NullPointerException u slučaju pokušaja dodavanja <code>null</code>
	 *                              vrijednosti
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * Metoda za uklanjanja slušača nad promjenama boja.
	 * 
	 * @param l slušač
	 */
	public void removeColorChangeListener(ColorChangeListener l);
}