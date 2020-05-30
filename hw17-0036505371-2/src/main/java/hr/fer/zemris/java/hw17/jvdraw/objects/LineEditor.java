package hr.fer.zemris.java.hw17.jvdraw.objects;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw17.jvdraw.components.JColorArea;

/**
 * Uređivač linija.
 * 
 * @author Maja Radočaj
 *
 */
public class LineEditor extends GeometricalObjectEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Linija.
	 */
	private Line line;
	/**
	 * Tekst za x koordinatu početne točke.
	 */
	private JTextArea startX;
	/**
	 * Tekst za y koordinatu početne točke.
	 */
	private JTextArea startY;
	/**
	 * Tekst za x koordinatu završne točke.
	 */
	private JTextArea endX;
	/**
	 * Tekst za y koordinatu završne točke.
	 */
	private JTextArea endY;
	/**
	 * Komponenta za odabir boja.
	 */
	private JColorArea colorChooser;
	/**
	 * X koordinata početne točke.
	 */
	private int x1;
	/**
	 * Y koordinata početne točke.
	 */
	private int y1;
	/**
	 * X koordinata završne točke.
	 */
	private int x2;
	/**
	 * Y koordinata završne točke.
	 */
	private int y2;
	/**
	 * Boja linije.
	 */
	private Color color = Color.BLACK;

	/**
	 * Javni konstruktor.
	 * 
	 * @param line linija
	 */
	public LineEditor(Line line) {
		this.line = line;
		
		startX = new JTextArea(Integer.toString(line.getX1()));
		startY = new JTextArea(Integer.toString(line.getY1()));
		endX = new JTextArea(Integer.toString(line.getX2()));
		endY = new JTextArea(Integer.toString(line.getY2()));
		colorChooser = new JColorArea(line.getColor());
		
		setLayout(new BorderLayout());
		JPanel left = new JPanel();
		JPanel right = new JPanel();
		
		left.setLayout(new GridLayout(5, 1));
		left.add(new JLabel("X coordinate of start point: "));
		left.add(new JLabel("Y coordinate of start point: "));
		left.add(new JLabel("X coordinate of end point: "));
		left.add(new JLabel("Y coordinate of end point: "));
		left.add(new JLabel("Color: "));
		
		right.setLayout(new GridLayout(5, 1));
		right.add(startX);
		right.add(startY);
		right.add(endX);
		right.add(endY);
		right.add(colorChooser);
		
		this.add(left, BorderLayout.WEST);
		this.add(right, BorderLayout.EAST);
	}

	@Override
	public void checkEditing() {
		try {
			x1 = Integer.parseInt(startX.getText());
			y1 = Integer.parseInt(startY.getText());
			x2 = Integer.parseInt(endX.getText());
			y2 = Integer.parseInt(endY.getText());
			color = colorChooser.getCurrentColor();
			
			/*
			 * Stavila sam da koordinate mogu biti i NEGATIVNE jer
			 * u tekstu zadaće piše da bounding box može bit negativan
			 */
			if(color == null) {	
				
				throw new RuntimeException("Fields not filled in properly.");
			}
			
		} catch(NumberFormatException ex) {
			throw new RuntimeException("Fields not filled in properly. "
					+ "Positive integer numbers expected for coordinates.");
		}
	}

	@Override
	public void acceptEditing() {
		line.setX1(x1);
		line.setX2(x2);
		line.setY1(y1);
		line.setY2(y2);
		line.setColor(color);
	}

}
