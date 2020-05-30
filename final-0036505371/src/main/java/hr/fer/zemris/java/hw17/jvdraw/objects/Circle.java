package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.Color;

/**
 * Razred koji modelira krug.
 * <p>Svaki krug ima središe, radijus i boju kružnice.
 * 
 * @author Maja Radočaj
 *
 */
public class Circle extends GeometricalObject {

	/**
	 * X komponenta središta.
	 */
	private int x;	
	/**
	 * Y komponenta središta.
	 */
	private int y;
	/**
	 * Polumjer.
	 */
	private int r;
	/**
	 * Boja kružnice.
	 */
	private Color outlineColor;
	
	/**
	 * Konstruktor.
	 * 
	 * @param x x komponenta središta
	 * @param y y komponenta središta
	 * @param r radijus
	 * @param color boja kružnice
	 */
	public Circle(int x, int y, int r, Color color) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.outlineColor = color;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	/**
	 * Getter za x komponentu središta.
	 * 
	 * @return x komponenta središta
	 */
	public int getX() {
		return x;
	}

	/**
	 * Setter za x komponentu središta.
	 * 
	 * @param x x komponenta središta
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Getter za y komponentu središta.
	 * 
	 * @return y komponenta središta
	 */
	public int getY() {
		return y;
	}

	/**
	 * Setter za y komponentu središta.
	 * 
	 * @param y y komponenta središta
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Getter za polumjer.
	 * 
	 * @return polumjer
	 */
	public int getR() {
		return r;
	}

	/**
	 * Setter za polumjer.
	 * 
	 * @param r polumjer
	 */
	public void setR(int r) {
		this.r = r;
	}

	/**
	 * Metoda koja vraća boju kružnice.
	 * 
	 * @return boja kružnice
	 */
	public Color getOutlineColor() {
		return outlineColor;
	}

	/**
	 * Metoda koja postavlja boju kružnice
	 * 
	 * @param color boja kružnice
	 */
	public void setOutlineColor(Color color) {
		this.outlineColor = color;
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}
	
	@Override
	public String toString() {
		return String.format("Circle (%d,%d), %d", x, y, r);
	}
}
