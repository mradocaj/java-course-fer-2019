package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.components.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawing.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

/**
 * Alat za crtanje linija.
 * 
 * @author Maja Radočaj
 *
 */
public class LineTool implements Tool {

	/**
	 * Model.
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
	 * Linija.
	 */
	private Line line;
	/**
	 * Boolean varijabla koja nam govori radi li se o prvom ili drugom kliku miša.
	 */
	private boolean lineStart = true;
	/**
	 * Točka početka linije.
	 */
	private Point beginning;	
	/**
	 * Točka kraja linije.
	 */
	private Point end;
	
	/**
	 * Javni konstruktor.
	 * 
	 * @param model model
	 * @param colorProvider dobavljač boje
	 * @param canvas komponenta po kojoj se crta
	 */
	public LineTool(DrawingModel model, IColorProvider colorProvider, JDrawingCanvas canvas) {
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
		if(lineStart) {
			lineStart = false;
			beginning = e.getPoint();
	
		} else {
			end = e.getPoint();
			line = new Line(beginning.x, beginning.y, 
					end.x, end.y, colorProvider.getCurrentColor());
			model.add(line);
			lineStart = true;
			beginning = null;
			end = null;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!lineStart) {
			end = e.getPoint();
			canvas.repaint();	
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void paint(Graphics2D g2d) {
		if(beginning != null && end != null) {
			g2d.setColor(colorProvider.getCurrentColor());
			g2d.drawLine(beginning.x, beginning.y, end.x, end.y);
		}
	}
}
