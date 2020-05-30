package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.Rectangle;

/**
 * Razred koji pretstavlja visitora koji za objekte koje posjećuje računa
 * najmanji mogući pravokutnik u koji svi objekti stanu.
 * 
 * @author Maja Radočaj
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	/**
	 * X koordinata gornjeg lijevog kuta pravokutnika.
	 */
	private int x = Integer.MAX_VALUE;
	/**
	 * Y koordinara gornjeg lijevog kuta pravokutnika.
	 */
	private int y = Integer.MAX_VALUE;
	/**
	 * X koordinata donjeg desnog kuta pravokutnika.
	 */
	private int lowerX;
	/**
	 * Y koordinata donjeg desnog kuta pravokutnika.
	 */
	private int lowerY;
	
	@Override
	public void visit(Line line) {
		int x1 = Math.min(line.getX1(), line.getX2());
		int y1 = Math.min(line.getY1(), line.getY2());
		int lowerX1 = Math.max(line.getX1(), line.getX2());
		int lowerY1 = Math.max(line.getY1(), line.getY2());
		
		compareAndEdit(x1, y1, lowerX1, lowerY1);
	}

	@Override
	public void visit(Circle circle) {
		int radius = circle.getR();
		int x1 = circle.getX() - radius;
		int y1 = circle.getY() - radius;
		
		compareAndEdit(x1, y1, x1 + 2 * radius, y1 + 2 * radius);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		visit((Circle) filledCircle);
	}

	/**
	 * Pomoćna metoda koja za objekt uspoređuje i uređuje najmanji mogući pravokutnik.
	 * 
	 * @param x1 x koordinata gornjeg lijevog kuta za dani objekt
	 * @param y1 y koordinata gornjeg lijevog kuta za dani objekt
	 * @param lowerX1 x koordinata donjeg desnog kuta za dani objekt
	 * @param lowerY1 y koordinata donjeg desnog kuta za dani objekt
	 */
	private void compareAndEdit(int x1, int y1, int lowerX1, int lowerY1) {
		if(x > x1) x = x1;
		if(y > y1) y = y1;
		if(lowerX < lowerX1) lowerX = lowerX1;
		if(lowerY < lowerY1) lowerY = lowerY1;
	}
	
	/**
	 * Metoda koja vraća najmanji mogući pravokutnik.
	 * 
	 * @return najmanji mogući pravokutnik
	 */
	public Rectangle getBoundingBox() {
		return new Rectangle(x == Integer.MAX_VALUE ? 0 : x,
				y == Integer.MAX_VALUE ? 0 : y, lowerX - x, lowerY - y);		
	}

	@Override
	public void visit(FilledTriangle triangle) {
		int x1 = Math.min(triangle.getX1(), triangle.getX2());
		int y1 = Math.min(triangle.getY1(), triangle.getY2());
		int lowerX1 = Math.max(triangle.getX1(), triangle.getX2());
		int lowerY1 = Math.max(triangle.getY1(), triangle.getY2());
		
		compareAndEdit(x1, y1, lowerX1, lowerY1);
		
		int x2 = Math.min(triangle.getX1(), triangle.getX3());
		int y2 = Math.min(triangle.getY1(), triangle.getY3());
		int lowerX2 = Math.max(triangle.getX1(), triangle.getX3());
		int lowerY2 = Math.max(triangle.getY1(), triangle.getY3());
		
		compareAndEdit(x2, y2, lowerX2, lowerY2);
	}
}
