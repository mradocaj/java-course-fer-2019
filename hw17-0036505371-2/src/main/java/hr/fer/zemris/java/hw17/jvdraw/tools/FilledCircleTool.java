package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.components.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;

/**
 * Alat za crtanje ispunjenih krugova.
 * 
 * @author Maja Radočaj
 *
 */
public class FilledCircleTool implements Tool {

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
	private FilledCircle circle;	
	/**
	 * Boolean varijabla koja nam govori radi li se o prvom ili drugom kliku miša.
	 */
	private boolean circleStart = true;
	/**
	 * Točka središta.
	 */
	private Point center;	
	/**
	 * Točka kraja.
	 */
	private Point end;
	
	/**
	 * Javni konstruktor.
	 * 
	 * @param model model
	 * @param outline dobavljač boje obruba
	 * @param fill dobavljač boje ispune
	 * @param canvas komponenta po kojoj se crta
	 */
	public FilledCircleTool(DrawingModel model, IColorProvider outline,
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
		if(circleStart) {
			center = e.getPoint();
			circleStart = false;
		} else {
			Point end = e.getPoint();
			int r = (int) Math.sqrt(Math.pow(center.x - end.x, 2) + Math.pow(center.y - end.y, 2));
			if(r != 0) {
				circle = new FilledCircle(center.x, center.y, r, outline.getCurrentColor(),
						fill.getCurrentColor());
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
			g2d.setColor(fill.getCurrentColor());
			int r = (int) Math.sqrt(Math.pow(center.x - end.x, 2) + Math.pow(center.y - end.y, 2));
			g2d.fillOval(center.x - r, center.y - r, r * 2, r * 2);
			g2d.setColor(outline.getCurrentColor());
			g2d.drawOval(center.x - r, center.y - r, r * 2, r * 2);
		}
	}

}
