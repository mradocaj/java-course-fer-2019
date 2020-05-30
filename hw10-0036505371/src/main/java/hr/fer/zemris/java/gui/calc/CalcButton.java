package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Razred koji modelira gumb kalkulatora opće namjene.
 * 
 * @author Maja Radočaj
 *
 */
public class CalcButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor koji stvara gumb sa tekstom <code>text</code> i ActionListenerom <code>a</code>.
	 * 
	 * @param text tekst gumba
	 * @param a action listener
	 */
	public CalcButton(String text, ActionListener a) {
		super(text);
		addActionListener(a);
		setBackground(new Color(153, 204, 255));
		setFont(new Font("Arial", Font.PLAIN, 17));
	}
}
