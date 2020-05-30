package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.Color;

/**
 * Razred koji modelira ispunjeni krug.
 * 
 * @author Maja Radočaj
 *
 */
public class FilledCircle extends Circle {

	/**
	 * Boja ispune.
	 */
	private Color fillColor;

	/**
	 * Javni konstruktor.
	 * 
	 * @param x x komponenta središta
	 * @param y y komponenta središta
	 * @param r radijus
	 * @param outlineColor boja obruba
	 * @param fillColor boja ispune
	 */
	public FilledCircle(int x, int y, int r, Color outlineColor, Color fillColor) {
		super(x, y, r, outlineColor);
		this.fillColor = fillColor;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	/**
	 * Getter za boju ispune.
	 * 
	 * @return boja ispune
	 */
	public Color getFillColor() {
		return fillColor;
	}

	/**
	 * Setter za boju ispune.
	 * 
	 * @param fillColor boja ispune
	 */
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
	}

	@Override
	public String toString() {
		return String.format("Filled circle (%d,%d), %d, #%s", super.getX(), super.getY(), super.getR(),
				Integer.toHexString(fillColor.getRGB()).substring(2).toUpperCase());
	}
	
}
