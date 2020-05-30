package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.Color;

/**
 * Razred koji modelira liniju kao grafički objekt.
 * <p>Svaka linija ima početnu točku, završnu točku te boju.
 * 
 * @author Maja Radočaj
 *
 */
public class Line extends GeometricalObject {

	/**
	 * X koordinata početne točke.
	 */
	private int x1;
	/**
	 * Y koordinata početne točke.
	 */
	private int y1;
	/**
	 * X koordinata završne točke.
	 */
	private int x2; 
	/**
	 * Y koordinata završne točke.
	 */
	private int y2;
	/**
	 * Boja linije.
	 */
	private Color color;

	/**
	 * Javni konstruktor.
	 * 
	 * @param x1 x koordinata početne točke
	 * @param y1 y koordinata početne točke
	 * @param x2 x koordinata završne točke
	 * @param y2 y koordinata završne točke
	 * @param color boja linije
	 */
	public Line(int x1, int y1, int x2, int y2, Color color) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = color;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	/**
	 * Getter za x koordinatu početne točke.
	 * 
	 * @return x koordinata početne točke
	 */
	public int getX1() {
		return x1;
	}

	/**
	 * Setter za x koordinatu početne točke.
	 * 
	 * @param x1 x koordinata početne točke
	 */
	public void setX1(int x1) {
		this.x1 = x1;
	}

	/**
	 * Getter za y koordinatu početne točke.
	 * 
	 * @return y koordinata početne točke
	 */
	public int getY1() {
		return y1;
	}

	/**
	 * Setter za y koordinatu početne točke.
	 * 
	 * @param x1 y koordinata početne točke
	 */
	public void setY1(int y1) {
		this.y1 = y1;
	}

	/**
	 * Getter za x koordinatu završne točke.
	 * 
	 * @return x koordinata završne točke
	 */
	public int getX2() {
		return x2;
	}

	/**
	 * Setter za x koordinatu završne točke.
	 * 
	 * @param x1 x koordinata završne točke
	 */
	public void setX2(int x2) {
		this.x2 = x2;
	}

	/**
	 * Getter za y koordinatu završne točke.
	 * 
	 * @return y koordinata završne točke
	 */
	public int getY2() {
		return y2;
	}

	/**
	 * Setter za y koordinatu završne točke.
	 * 
	 * @param x1 y koordinata završne točke
	 */
	public void setY2(int y2) {
		this.y2 = y2;
	}

	/**
	 * Getter za boju linije.
	 * 
	 * @return boja linije
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Setter za boju linije.
	 * 
	 * @param color boja linije
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}
	
	@Override
	public String toString() {
		return String.format("Line (%d,%d)-(%d,%d)", x1, y1, x2, y2);
	}
	
}
