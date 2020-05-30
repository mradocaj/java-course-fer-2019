package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw17.jvdraw.components.JColorArea;

/**
 * Uređivač ispunjenih krugova.
 * 
 * @author Maja Radočaj
 *
 */
public class FilledCircleEditor extends GeometricalObjectEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Ispunjeni krug.
	 */
	private FilledCircle circle;
	/**
	 * Tekst za x komponentu središta.
	 */
	private JTextArea centerX;
	/**
	 * Tekst za y komponentu središta.
	 */
	private JTextArea centerY;
	/**
	 * Tekst za polumjer.
	 */
	private JTextArea radius;
	/**
	 * Komponenta za biranje boje obruba.
	 */
	private JColorArea outlineColorChooser;
	/**
	 * Komponenta za biranje boje ispune.
	 */
	private JColorArea fillColorChooser;
	/**
	 * X komponenta središta.
	 */
	private int x;
	/**
	 * Y komponenta središta.
	 */
	private int y;
	/**
	 * Polumjer.
	 */
	private int r;
	/**
	 * Boja obruba.
	 */
	private Color outlineColor = Color.BLACK;
	/**
	 * Boja ispune.
	 */
	private Color fillColor = Color.WHITE;
	
	/**
	 * Javni konstruktor.
	 * 
	 * @param circle ispunjeni krug
	 */
	public FilledCircleEditor(FilledCircle circle) {
		this.circle = circle;
		
		centerX = new JTextArea(Integer.toString(circle.getX()));
		centerY = new JTextArea(Integer.toString(circle.getY()));
		radius = new JTextArea(Integer.toString(circle.getR()));
		outlineColorChooser = new JColorArea(circle.getOutlineColor());
		fillColorChooser = new JColorArea(circle.getFillColor());
		
		setLayout(new BorderLayout());
		JPanel left = new JPanel();
		JPanel right = new JPanel();
		
		left.setLayout(new GridLayout(5, 1));
		left.add(new JLabel("X coordinate of center: "));
		left.add(new JLabel("Y coordinate of center: "));
		left.add(new JLabel("Circle radius: "));
		left.add(new JLabel("Outline color: "));
		left.add(new JLabel("Fill color: "));
		
		right.setLayout(new GridLayout(5, 1));
		right.add(centerX);
		right.add(centerY);
		right.add(radius);
		right.add(outlineColorChooser);
		right.add(fillColorChooser);
		
		this.add(left, BorderLayout.WEST);
		this.add(right, BorderLayout.EAST);
	}

	@Override
	public void checkEditing() {
		try {
			x = Integer.parseInt(centerX.getText());
			y = Integer.parseInt(centerY.getText());
			r = Integer.parseInt(radius.getText());
			outlineColor = outlineColorChooser.getCurrentColor();
			fillColor = fillColorChooser.getCurrentColor();
			
			/*
			 * Stavila sam da koordinate mogu biti i NEGATIVNE jer
			 * u tekstu zadaće piše da bounding box može bit negativan
			 */
			if(r < 0 || outlineColor == null|| fillColor == null) {
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
		circle.setOutlineColor(outlineColor);
		circle.setFillColor(fillColor);
	}

}
