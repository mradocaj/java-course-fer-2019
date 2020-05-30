package hr.fer.zemris.java.hw17.jvdraw.drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Supplier;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;

/**
 * Razred koji modelira komponentu po kojoj se može crtati.
 * 
 * @author Maja Radočaj
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Dobavljač trenutnog alata za crtanje.
	 */
	private Supplier<Tool> supplier;
	/**
	 * Model za crtanje.
	 */
	private DrawingModel model;

	/**
	 * Javni konstruktor.
	 * 
	 * @param supplier dobavljač
	 * @param model model
	 */
	public JDrawingCanvas(Supplier<Tool> supplier, DrawingModel model) {
		this.supplier = supplier;
		this.model = model;
		this.model.addDrawingModelListener(this);
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				supplier.get().mouseClicked(e);
			}
		});
		
		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				supplier.get().mouseMoved(e);
			}
		});
	}
	
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		Insets ins = getInsets();
		g2d.fillRect(ins.left, ins.top, 
				getWidth() - ins.right - ins.left, getHeight() - ins.top - ins.bottom);
		
		g2d.setStroke(new BasicStroke(4));
		GeometricalObjectPainter painter = new GeometricalObjectPainter(g2d);
		for(int i = 0; i < model.getSize(); i++) {
			model.getObject(i).accept(painter);
		}
		
		Tool tool = supplier.get();
		tool.paint(g2d);
	}

}
