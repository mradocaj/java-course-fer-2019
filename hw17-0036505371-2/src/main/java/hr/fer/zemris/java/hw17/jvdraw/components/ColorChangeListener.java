package hr.fer.zemris.java.hw17.jvdraw.components;

import java.awt.Color;

/**
 * Sučelje koje modelira slušača za promjene boja.
 * 
 * @author Maja Radočaj
 *
 */
public interface ColorChangeListener {
	
	/**
	 * Metoda koja se poziva pri promjeni boje komponente.
	 * 
	 * @param source izvor promjene
	 * @param oldColor stara boja 
	 * @param newColor nova boja
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}