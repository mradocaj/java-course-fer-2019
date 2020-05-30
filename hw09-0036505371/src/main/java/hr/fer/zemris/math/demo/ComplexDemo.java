package hr.fer.zemris.math.demo;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Program koji demonstrira rad metoda razreda {@link ComplexPolynomial}, {@link ComplexRootedPolynomial} i {@link Complex}.
 *  
 * @author Maja Radočaj
 *
 */
public class ComplexDemo {

	/**
	 * Glavni program.
	 * 
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(new Complex(2, 0), Complex.ONE, Complex.ONE_NEG,
				Complex.IM, Complex.IM_NEG);
		ComplexPolynomial cp = crp.toComplexPolynom();
		
		System.out.println(crp);
		System.out.println(cp);
		System.out.println(cp.derive());
	}
}
