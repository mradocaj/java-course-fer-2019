package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;
import static java.lang.Math.*;

/**
 * Razred koji pretstavlja implementaciju kakulatora sa kojom korisnik može
 * raditi pomoću grafičkog sučelja.
 * 
 * @author Maja Radočaj
 *
 */
public class Calculator extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Implementacija modela kalkulatora.
	 */
	CalcModelImpl calc;
	/**
	 * Lista invertibilnih funkcija.
	 */
	List<UnaryOperationButton> invertibleFunctions = new ArrayList<>();
	/**
	 * Stog.
	 */
	Stack<Double> stack = new Stack<>();

	/**
	 * Javni konstruktor.
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(655, 360);
		initGUI();
	}

	/**
	 * Pomoćna metoda koja postavlja grafičko sučelje.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		calc = new CalcModelImpl();
		cp.setLayout(new CalcLayout(3));

		JLabel label = new JLabel("0", SwingConstants.RIGHT);
		label.setOpaque(true);
		label.setBackground(Color.yellow);
		label.setFont(new Font("Arial", Font.BOLD, 40));
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		calc.addCalcValueListener(v -> label.setText(calc.toString()));
		cp.add(label, new RCPosition(1, 1));

		addNumberButtons(cp);
		addBinaryOperationButtons(cp);
		addInvertibleOperations(cp);

		cp.add(new CalcButton("=", e -> {
			double operand = calc.getActiveOperand();
			calc.setValue(calc.getPendingBinaryOperation().applyAsDouble(operand, calc.getValue()));
			label.setText(calc.toString());
		}), new RCPosition(1, 6));

		cp.add(new CalcButton("clr", e -> calc.clear()), new RCPosition(1, 7));
		cp.add(new CalcButton("reset", e -> calc.clearAll()), new RCPosition(2, 7));
		cp.add(new CalcButton("push", e -> stack.push(calc.getValue())), new RCPosition(3, 7));
		cp.add(new CalcButton("pop", e -> calc.setValue(stack.pop())), new RCPosition(4, 7));
		cp.add(new CalcButton("+/-", e -> calc.swapSign()), new RCPosition(5, 4));
		cp.add(new CalcButton(".", e -> calc.insertDecimalPoint()), new RCPosition(5, 5));

		JCheckBox inv = new JCheckBox("Inv");
		inv.setFont(new Font("Arial", Font.PLAIN, 17));
		inv.addActionListener(e -> {
			for(UnaryOperationButton function : invertibleFunctions) {
				function.inverse();
			}
		});
		cp.add(inv, new RCPosition(5, 7));
	}

	/**
	 * Pomoćna metoda koja stvara gumb na čiji pritisak se pamti brojka.
	 * 
	 * @param text tekst gumba
	 * @return novi gumb
	 */
	private JButton numberButton(String text) {
		JButton numberButton = new JButton(text);
		numberButton.setBackground(new Color(153, 204, 255));
		numberButton.setFont(new Font("Arial", Font.BOLD, 40));
		numberButton.addActionListener(e -> calc.insertDigit(Integer.parseInt(text)));
		return numberButton;
	}

	/**
	 * Pomoćna metoda za dodavanje niza gumbova za broj.
	 * 
	 * @param cp kontejner u koji se dodaju gumbovi
	 */
	private void addNumberButtons(Container cp) {
		cp.add(numberButton("0"), new RCPosition(5, 3));
		cp.add(numberButton("1"), new RCPosition(4, 3));
		cp.add(numberButton("2"), new RCPosition(4, 4));
		cp.add(numberButton("3"), new RCPosition(4, 5));
		cp.add(numberButton("4"), new RCPosition(3, 3));
		cp.add(numberButton("5"), new RCPosition(3, 4));
		cp.add(numberButton("6"), new RCPosition(3, 5));
		cp.add(numberButton("7"), new RCPosition(2, 3));
		cp.add(numberButton("8"), new RCPosition(2, 4));
		cp.add(numberButton("9"), new RCPosition(2, 5));
	}

	/**
	 * Pomoćna metoda za dodavanje niza gumbova binarne operacije.
	 * 
	 * @param cp kontejner u koji se dodaju gumbovi
	 */
	private void addBinaryOperationButtons(Container cp) {
		cp.add(new CalcButton("/", e -> calc.setPendingBinaryOperation((l, r) -> l / r)), 
				new RCPosition(2, 6));
		cp.add(new CalcButton("*", e -> calc.setPendingBinaryOperation((l, r) -> l * r)), 
				new RCPosition(3, 6));
		cp.add(new CalcButton("-", e -> calc.setPendingBinaryOperation((l, r) -> l - r)), 
				new RCPosition(4, 6));
		cp.add(new CalcButton("+", e -> calc.setPendingBinaryOperation((l, r) -> l + r)), 
				new RCPosition(5, 6));
		UnaryOperationButton powButton = new UnaryOperationButton("x^n",
				e -> calc.setPendingBinaryOperation((l, r) -> Math.pow(l, r)), "x^(1/n)",
				e -> calc.setPendingBinaryOperation((l, r) -> pow(l, 1 / r)));
		cp.add(powButton, new RCPosition(5, 1));
		invertibleFunctions.add(powButton);
	}

	/**
	 * Pomoćna metoda za dodavanje niza gumbova za dodavanje invertibilnih funkcija.
	 * 
	 * @param cp kontejner u koji se dodaju gumbovi
	 */
	private void addInvertibleOperations(Container cp) {
		UnaryOperationButton button = new UnaryOperationButton("1/x", 
				e -> calc.setValue(1 / calc.getValue()), "1/x",
				e -> calc.setValue(1 / calc.getValue()));
		cp.add(button, new RCPosition(2, 1));
		invertibleFunctions.add(button);

		button = new UnaryOperationButton("sin", e -> calc.setValue(sin(calc.getValue())), "arcsin",
				e -> calc.setValue(asin(calc.getValue())));
		cp.add(button, new RCPosition(2, 2));
		invertibleFunctions.add(button);

		button = new UnaryOperationButton("log", e -> calc.setValue(log10(calc.getValue())), "10^x",
				e -> calc.setValue(pow(10, calc.getValue())));
		cp.add(button, new RCPosition(3, 1));
		invertibleFunctions.add(button);

		button = new UnaryOperationButton("cos", e -> calc.setValue(cos(calc.getValue())), "arccos",
				e -> calc.setValue(acos(calc.getValue())));
		cp.add(button, new RCPosition(3, 2));
		invertibleFunctions.add(button);

		button = new UnaryOperationButton("ln", e -> calc.setValue(log(calc.getValue())), "e^x",
				e -> calc.setValue(pow(Math.E, calc.getValue())));
		cp.add(button, new RCPosition(4, 1));
		invertibleFunctions.add(button);

		button = new UnaryOperationButton("tan", e -> calc.setValue(tan(calc.getValue())), "arctan",
				e -> calc.setValue(atan(calc.getValue())));
		cp.add(button, new RCPosition(4, 2));
		invertibleFunctions.add(button);

		button = new UnaryOperationButton("ctg", e -> calc.setValue(1 / tan(calc.getValue())), "arcctg",
				e -> calc.setValue(1 / atan(calc.getValue())));
		cp.add(button, new RCPosition(5, 2));
		invertibleFunctions.add(button);
	}

	/**
	 * Glavni program.
	 * 
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}
}
