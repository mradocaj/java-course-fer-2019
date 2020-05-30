package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.Color;

/**
 * Razred koji modelira krug.
 * <p>Svaki krug ima središe, radijus i boju kružnice.
 * 
 * @author Maja Radočaj
 *
 */
public class FilledTriangle extends GeometricalObject {

	/**
	 * X komponenta središta.
	 */
	private int x1;	
	/**
	 * Y komponenta središta.
	 */
	private int y1;
	
	private int x2;	
	private int y2;
	
	private int x3;	
	private int y3;

	private Color outlineColor;
	private Color fillColor;

	public FilledTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Color outlineColor, 
			Color fillColor) {
		
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.x3 = x3;
		this.y3 = y3;
		this.outlineColor = outlineColor;
		this.fillColor = fillColor;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public int getX3() {
		return x3;
	}

	public void setX3(int x3) {
		this.x3 = x3;
	}

	public int getY3() {
		return y3;
	}

	public void setY3(int y3) {
		this.y3 = y3;
	}

	public Color getOutlineColor() {
		return outlineColor;
	}

	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledTriangleEditor(this);
	}
	
	@Override
	public String toString() {
		return String.format("Filled triangle (%d,%d), (%d,%d), (%d,%d), #%s", x1, y1, x2, y2, x3, y3, 
				Integer.toHexString(fillColor.getRGB()).substring(2).toUpperCase());
	}
}
