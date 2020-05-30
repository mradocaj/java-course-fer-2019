package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw17.jvdraw.components.JColorArea;

/**
 * Uređivač krugova.
 * 
 * @author Maja Radočaj
 *
 */
public class CircleEditor extends GeometricalObjectEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Kružnica.
	 */
	private Circle circle;
	/**
	 * Tekst za x komponentu središta.
	 */
	private JTextArea centerX;
	/**
	 * Tekst za y komponentu središta.
	 */
	private JTextArea centerY;
	/**
	 * Tekst za radijus.
	 */
	private JTextArea radius;
	/**
	 * Komponenta za biranje boje.
	 */
	private JColorArea colorChooser;
	/**
	 * X komponenta.
	 */
	private int x;
	/**
	 * Y kompoenta.
	 */
	private int y;
	/**
	 * Radijus.
	 */
	private int r;
	/**
	 * Boja.
	 */
	private Color color = Color.BLACK;
	
	/**
	 * Javni konstruktor.
	 * 
	 * @param circle krug
	 */
	public CircleEditor(Circle circle) {
		this.circle = circle;
		
		centerX = new JTextArea(Integer.toString(circle.getX()));
		centerY = new JTextArea(Integer.toString(circle.getY()));
		radius = new JTextArea(Integer.toString(circle.getR()));
		colorChooser = new JColorArea(circle.getOutlineColor());
		
		setLayout(new BorderLayout());
		JPanel left = new JPanel();
		JPanel right = new JPanel();
		
		left.setLayout(new GridLayout(4, 1));
		left.add(new JLabel("X coordinate of center: "));
		left.add(new JLabel("Y coordinate of center: "));
		left.add(new JLabel("Circle radius: "));
		left.add(new JLabel("Outline color: "));
		
		right.setLayout(new GridLayout(4, 1));
		right.add(centerX);
		right.add(centerY);
		right.add(radius);
		right.add(colorChooser);
		
		this.add(left, BorderLayout.WEST);
		this.add(right, BorderLayout.EAST);
	}

	@Override
	public void checkEditing() {
		try {
			x = Integer.parseInt(centerX.getText());
			y = Integer.parseInt(centerY.getText());
			r = Integer.parseInt(radius.getText());
			color = colorChooser.getCurrentColor();
			
			/*
			 * Stavila sam da koordinate mogu biti i NEGATIVNE jer
			 * u tekstu zadaće piše da bounding box može bit negativan
			 */
			if(r < 0 || color == null) {
				throw new RuntimeException("Fields not filled in properly.");
			}
			
		} catch(NumberFormatException ex) {
			throw new RuntimeException("Fields not filled in properly. "
					+ "Positive integer numbers expected for coordinates.");
		}
	}

	@Override
	public void acceptEditing() {
		circle.setX(x);
		circle.setY(y);
		circle.setR(r);
		circle.setOutlineColor(color);
	}

}
