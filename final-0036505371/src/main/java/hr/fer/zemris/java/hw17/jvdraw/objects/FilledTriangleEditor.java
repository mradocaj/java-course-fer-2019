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
public class FilledTriangleEditor extends GeometricalObjectEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Ispunjeni krug.
	 */
	private FilledTriangle triangle;
	/**
	 * Tekst za x komponentu središta.
	 */
	private JTextArea x1text;
	private JTextArea y1text;
	private JTextArea x2text;
	private JTextArea y2text;
	private JTextArea x3text;
	private JTextArea y3text;
	
	private JColorArea outlineColorChooser;
	/**
	 * Komponenta za biranje boje ispune.
	 */
	private JColorArea fillColorChooser;
	
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private int x3;
	private int y3;
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
	public FilledTriangleEditor(FilledTriangle triangle) {
		this.triangle = triangle;
		
		x1text = new JTextArea(Integer.toString(triangle.getX1()));
		y1text = new JTextArea(Integer.toString(triangle.getY1()));
		x2text = new JTextArea(Integer.toString(triangle.getX2()));
		y2text = new JTextArea(Integer.toString(triangle.getY2()));
		x3text = new JTextArea(Integer.toString(triangle.getX3()));
		y3text = new JTextArea(Integer.toString(triangle.getY3()));
		
		outlineColorChooser = new JColorArea(triangle.getOutlineColor());
		fillColorChooser = new JColorArea(triangle.getFillColor());
		
		setLayout(new BorderLayout());
		JPanel left = new JPanel();
		JPanel right = new JPanel();
		
		left.setLayout(new GridLayout(8, 1));
		left.add(new JLabel("x1: "));
		left.add(new JLabel("y1: "));
		left.add(new JLabel("x2: "));
		left.add(new JLabel("y2: "));
		left.add(new JLabel("x3: "));
		left.add(new JLabel("y3: "));
		left.add(new JLabel("Outline color: "));
		left.add(new JLabel("Fill color: "));
		
		right.setLayout(new GridLayout(8, 1));
		right.add(x1text);
		right.add(y1text);
		right.add(x2text);
		right.add(y2text);
		right.add(x3text);
		right.add(y3text);
	
		right.add(outlineColorChooser);
		right.add(fillColorChooser);
		
		this.add(left, BorderLayout.WEST);
		this.add(right, BorderLayout.EAST);
	}

	@Override
	public void checkEditing() {
		try {
			x1 = Integer.parseInt(x1text.getText());
			y1 = Integer.parseInt(y1text.getText());
			x2 = Integer.parseInt(x2text.getText());
			y2 = Integer.parseInt(y2text.getText());
			x3 = Integer.parseInt(x3text.getText());
			y3 = Integer.parseInt(y3text.getText());
			
			outlineColor = outlineColorChooser.getCurrentColor();
			fillColor = fillColorChooser.getCurrentColor();
			
			/*
			 * Stavila sam da koordinate mogu biti i NEGATIVNE jer
			 * u tekstu zadaće piše da bounding box može bit negativan
			 */
			if(outlineColor == null|| fillColor == null) {
				throw new RuntimeException("Fields not filled in properly.");
			}
			
		} catch(NumberFormatException ex) {
			throw new RuntimeException("Fields not filled in properly. "
					+ "Positive integer numbers expected for coordinates.");
		}
	}

	@Override
	public void acceptEditing() {
		triangle.setX1(x1);
		triangle.setY1(y1);
		triangle.setX2(x2);
		triangle.setY2(y2);
		triangle.setX3(x3);
		triangle.setY3(y3);
		
		triangle.setOutlineColor(outlineColor);
		triangle.setFillColor(fillColor);
	}

}
