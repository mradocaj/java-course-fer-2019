package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.components.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledTriangle;

/**
 * Alat za crtanje ispunjenih krugova.
 * 
 * @author Maja Radočaj
 *
 */
public class FilledTriangleTool implements Tool {

	/**
	 * Model.
	 */
	private DrawingModel model;
	/**
	 * Dobavljač boje obruba.
	 */
	private IColorProvider outline;
	/**
	 * Dobavljač boje ispune.
	 */
	private IColorProvider fill;
	/**
	 * Komponenta po kojoj se crta.
	 */
	private JDrawingCanvas canvas;
	/**
	 * Ispunjeni krug.
	 */
	private FilledTriangle triangle;	
	/**
	 * Boolean varijabla koja nam govori radi li se o prvom ili drugom kliku miša.
	 */
	private int pointCount = 0;
	/**
	 * Točka središta.
	 */
	private Point first;
	private Point second;
	private Point third;
	
	/**
	 * Javni konstruktor.
	 * 
	 * @param model model
	 * @param outline dobavljač boje obruba
	 * @param fill dobavljač boje ispune
	 * @param canvas komponenta po kojoj se crta
	 */
	public FilledTriangleTool(DrawingModel model, IColorProvider outline,
			IColorProvider fill, JDrawingCanvas canvas) {
		this.model = model;
		this.outline = outline;
		this.canvas = canvas;
		this.fill = fill;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(pointCount == 0) {
			first = e.getPoint();
			pointCount++;
		} else if(pointCount == 1) {
			second = e.getPoint();
			pointCount++;
		} else if(pointCount == 2) {
			third = e.getPoint();
			triangle = new FilledTriangle(first.x, first.y, 
					second.x, second.y, third.x, third.y, 
					outline.getCurrentColor(), fill.getCurrentColor());
			model.add(triangle);
			pointCount = 0;
			triangle = null;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
//		if(!circleStart) {
//			end = e.getPoint();
//			canvas.repaint();
//		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void paint(Graphics2D g2d) {
		if(triangle != null) {
//			g2d.setColor(fill.getCurrentColor());
//			int r = (int) Math.sqrt(Math.pow(center.x - end.x, 2) + Math.pow(center.y - end.y, 2));
//			g2d.fillOval(center.x - r, center.y - r, r * 2, r * 2);
//			g2d.setColor(outline.getCurrentColor());
//			g2d.drawOval(center.x - r, center.y - r, r * 2, r * 2);
			g2d.setColor(triangle.getFillColor());
			int[] xPoints = {triangle.getX1(), triangle.getX2(), triangle.getX3()};
			int[] yPoints = {triangle.getY1(), triangle.getY2(), triangle.getY3()};
			g2d.fillPolygon(xPoints, yPoints, 3);
			
			g2d.setColor(triangle.getOutlineColor());
			g2d.drawPolygon(xPoints, yPoints, 3);
		}
	}

}
