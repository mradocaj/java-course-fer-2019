package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Razred koji pretstavlja implementaciju modela kalkulatora zadanu sučeljem {@link CalcModel}.
 * 
 * @author Maja Radočaj
 *
 */
public class CalcModelImpl implements CalcModel {

	/**
	 * Zastavica koja nam govori je li objekt editabilan.
	 */
	private boolean editable;
	/**
	 * Zastavica koja nam govori je li objekt pozitivan.
	 */
	private boolean positive;
	/**
	 * String reprezentacija znamenki.
	 */
	private String digits;
	/**
	 * Trenutna decimalna vrijednost.
	 */
	private double decimalValue;
	/**
	 * Aktivni operand.
	 */
	private double activeOperand;
	/**
	 * Zastavica koja nam govori je li postavljen aktivni operand.
	 */
	private boolean activeOperandSet;
	/**
	 * Binarna operacija na čekanju.
	 */
	private DoubleBinaryOperator pendingOperation;
	/**
	 * Slušači promjena kalkulatora.
	 */
	private List<CalcValueListener> calcValueListeners;
	/**
	 * Zastavica koja nam govori je li nedavno dodan novi operand.
	 */
	private boolean recentlyAddedOperand;
	/**
	 * Najveći dozvoljen broj znamenki.
	 */
	private static final int DIGIT_LIMIT = 308;
	
	/**
	 * Javni konstruktor.
	 */
	public CalcModelImpl() {
		editable = true;
		positive = true;
		digits = "";
		calcValueListeners = new ArrayList<>();
	}
	
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		Objects.requireNonNull(l);
		calcValueListeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		calcValueListeners.remove(l);
	}

	@Override
	public double getValue() {
		return decimalValue;
	}

	@Override
	public void setValue(double value) {
		editable = false;
		decimalValue = value;
		positive = decimalValue < 0 ? false : true;
		
		if(Double.isNaN(decimalValue)) {
			digits = "NaN";
		} else if(Double.isInfinite(decimalValue)) {
			digits = "Infinity";
		} else {
			String num = Double.toString(decimalValue);
			digits = num.startsWith("-") ? num.replace("-", "") : num;	//ovdje mičem predznak iz string-a
		}
		
		notifyListeners();
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		digits = "";
		decimalValue = 0;
		notifyListeners();
	}

	@Override
	public void clearAll() {
		digits = "";
		decimalValue = 0;
		activeOperand = 0;
		activeOperandSet = false;
		pendingOperation = null;
		editable = true;
		positive = true;
		notifyListeners();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!editable) {
			throw new CalculatorInputException("Cannot swap sign - calculator is not editable.");
		}
		
		positive = positive ? false : true;
		decimalValue = decimalValue * (-1);
		notifyListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!editable) {
			throw new CalculatorInputException("Cannot insert decimal point - calculator is not editable.");
		}
		if(digits.contains(".")) {
			throw new CalculatorInputException("Cannot insert decimal point - decimal point already exists."); 
		}
		if(digits.length() == 0) {
			throw new CalculatorInputException("Cannot insert decimal point - number expected before point.");
		}
		
		digits = digits + ".";
		notifyListeners();
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!editable) {
			throw new CalculatorInputException("Cannot insert digit - calculator is not editable.");
		}
		if(digits.length() == DIGIT_LIMIT)  {
			throw new CalculatorInputException("Cannot insert digit - number is too big.");
		}
		if(recentlyAddedOperand) {
			recentlyAddedOperand = false;
			positive = true;
			clear();
		}
		try {
			decimalValue = Double.parseDouble(digits + digit); 
			if(!positive) {
				decimalValue = decimalValue * (-1);
			}
			digits += digit;
		} catch(NumberFormatException ex) {
			throw new CalculatorInputException("Invalid digit given.");
		}
		
		notifyListeners();
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperandSet;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(!isActiveOperandSet()) {
			throw new IllegalStateException("Cannot get active operand - active operand isn't set.");
		}
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		activeOperandSet = true;
		recentlyAddedOperand = true;
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = 0;
		activeOperandSet = false;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		if(pendingOperation != null) {
			setValue(pendingOperation.applyAsDouble(getActiveOperand(), getValue())); 
			notifyListeners();
		}
		pendingOperation = op;
		setActiveOperand(decimalValue);		
		editable = true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(!positive) {
			sb.append("-");
		}
		if(digits.length() == 0) {
			return sb.append("0").toString();
		}
		while(digits.startsWith("0") && digits.length() > 1 && digits.charAt(1) != '.') {
			digits = digits.replaceFirst("0", "");
		}
		
		return sb.append(digits).toString();
	}
	
	/**
	 * Pomoćna metoda koja slušačima javlja da je došlo do promjene u modelu kalkulatora.
	 */
	private void notifyListeners() {
		for(CalcValueListener listener : calcValueListeners) {
			listener.valueChanged(this);
		}
	}
}
