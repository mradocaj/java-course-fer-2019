package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.components.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;

/**
 * Alat za crtanje krugova.
 * 
 * @author Maja Radočaj
 *
 */
public class CircleTool implements Tool {

	/**
	 * Model za crtanje.
	 */
	private DrawingModel model;
	/**
	 * Dobavljač boje.
	 */
	private IColorProvider colorProvider;
	/**
	 * Komponenta po kojoj se crta.
	 */
	private JDrawingCanvas canvas;
	/**
	 * Krug.
	 */
	private Circle circle;
	/**
	 * Boolean varijabla koja nam govori radi li se o prvom ili drugom kliku miša.
	 */
	private boolean circleStart = true;
	/**
	 * Točka središta.
	 */
	private Point center;	
	/**
	 * Točka završetka.
	 */
	private Point end;
	
	/**
	 * Javni konstruktor.
	 * 
	 * @param model model
	 * @param colorProvider dobavljač boje
	 * @param canvas komponenta po kojoj se crta
	 */
	public CircleTool(DrawingModel model, IColorProvider colorProvider, JDrawingCanvas canvas) {
		this.model = model;
		this.colorProvider = colorProvider;
		this.canvas = canvas;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(circleStart) {
			center = e.getPoint();
			circleStart = false;
			
		} else {
			Point end = e.getPoint();
			int r = (int) Math.sqrt(Math.pow(center.x - end.x, 2) + Math.pow(center.y - end.y, 2));
			if(r != 0) {
				circle = new Circle(center.x, center.y, r, colorProvider.getCurrentColor());
				model.add(circle);
			}
			
			circleStart = true;
			center = null;
			end = null;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!circleStart) {
			end = e.getPoint();
			canvas.repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void paint(Graphics2D g2d) {
		if(end != null && center != null) {
			g2d.setColor(colorProvider.getCurrentColor());
			int r = (int) Math.sqrt(Math.pow(center.x - end.x, 2) + Math.pow(center.y - end.y, 2));
			g2d.drawOval(center.x - r, center.y - r, r * 2, r * 2);
		}
	}

}
