package hr.fer.zemris.java.hw02.demo;

import hr.fer.zemris.java.hw02.ComplexNumber;

/**
 * Program koji obavlja osnovne operacije nad kompleksnim brojevima.
 * 
 * @author Maja Radočaj
 *
 */
public class ComplexDemo {

	/**
	 * Glavna metoda od koje kreće računanje.
	 * 
	 * @param args argumenti naredbenog retka (ne koriste se)
	 */
	public static void main(String[] args) {
		
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57))
		.div(c2).power(3).root(2)[1];
		System.out.println(c3);
	}

}
