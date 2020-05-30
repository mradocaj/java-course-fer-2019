package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

/**
 * Razred koji pretstavlja koji iscrtava sve objekte koje posjećuje koristeći grafičku 
 * komponentu danu kroz konstruktor.
 * 
 * @author Maja Radočaj
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

	/**
	 * Grafička komponenta.
	 */
	private Graphics2D g2d;
	
	/**
	 * Javni konstruktor.
	 * 
	 * @param g2d grafička komponenta
	 */
	public GeometricalObjectPainter(Graphics2D g2d) {
		g2d.setStroke(new BasicStroke(4));
		this.g2d = g2d;
	}

	@Override
	public void visit(Line line) {
		g2d.setColor(line.getColor());
		g2d.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
	}

	@Override
	public void visit(Circle circle) {
		g2d.setColor(circle.getOutlineColor());
		int r = circle.getR();
		g2d.drawOval(circle.getX() - r, circle.getY() - r, r * 2, r * 2);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		g2d.setColor(filledCircle.getFillColor());
		int r = filledCircle.getR();
		g2d.fillOval(filledCircle.getX() - r, filledCircle.getY() - r, r * 2, r * 2);
		
		g2d.setColor(filledCircle.getOutlineColor());
		g2d.drawOval(filledCircle.getX() - r, filledCircle.getY() - r, r * 2, r * 2);
	}

	@Override
	public void visit(FilledTriangle triangle) {
		g2d.setColor(triangle.getFillColor());
		int[] xPoints = {triangle.getX1(), triangle.getX2(), triangle.getX3()};
		int[] yPoints = {triangle.getY1(), triangle.getY2(), triangle.getY3()};
		g2d.fillPolygon(xPoints, yPoints, 3);
		
		g2d.setColor(triangle.getOutlineColor());
		g2d.drawPolygon(xPoints, yPoints, 3);
	}

}
