package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Razred koji modelira gumb koji poziva unarnu operaciju.
 * 
 * @author Maja Radočaj
 *
 */
public class UnaryOperationButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Naziv funkcije.
	 */
	private String text;
	/**
	 * Naziv inverzne funkcije
	 */
	private String inverseText;
	/**
	 * Funkcija.
	 */
	private ActionListener function;
	/**
	 * Inverzna funkcija.
	 */
	private ActionListener inverseFunction;
	/**
	 * Zastavica koja govori je li funkcija trenutno invertirana.
	 */
	private boolean inverse;
	
	/**
	 * Javni konstruktor.
	 * 
	 * @param text
	 * @param function
	 * @param inverseText
	 * @param inverseFunction
	 */
	public UnaryOperationButton(String text, ActionListener function, String inverseText, 
			ActionListener inverseFunction) {
		super(text);
		this.text = text;
		addActionListener(function);
		this.function = function;
		this.inverseText = inverseText;
		this.inverseFunction = inverseFunction;
		setBackground(new Color(153, 204, 255));
		setFont(new Font("Arial", Font.PLAIN, 17));
	}

	/**
	 * Metoda koja stvara inverz funkcije.
	 */
	public void inverse() {
		if(inverse) {
			removeActionListener(inverseFunction);
			addActionListener(function);
			setText(text);
		} else {
			removeActionListener(function);
			addActionListener(inverseFunction);
			setText(inverseText);
		}
		inverse = inverse ? false : true;
	}
}
