package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * Razred koji modelira alat za crtanje.
 * 
 * @author Maja Radočaj
 *
 */
public interface Tool {

	/**
	 * Metoda koja se poziva pri pritisku miša.
	 * 
	 * @param e događaj mišta
	 */
	public void mousePressed(MouseEvent e);

	/**
	 * Metoda koja se poziva pri otpuštanju gumba miša.
	 * 
	 * @param e događaj mišta
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * Metoda koja se poziva pri kliku gumba miša.
	 * 
	 * @param e događaj mišta
	 */
	public void mouseClicked(MouseEvent e);

	/**
	 * Metoda koja se poziva pri pomicanju miša.
	 * 
	 * @param e događaj mišta
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * Metoda koja se poziva pri pritisku tipke miša i njegovom micanju.
	 * 
	 * @param e događaj mišta
	 */
	public void mouseDragged(MouseEvent e);

	/**
	 * Metoda koja crta pomoću grafičke komponente.
	 * 
	 * @param g2d grafička komponenta
	 */
	public void paint(Graphics2D g2d);
}