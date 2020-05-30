package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComplexPolynomialTest {

	@Test
	void testDerive() {
		ComplexPolynomial pol1= new ComplexPolynomial(Complex.ONE, new Complex(5, 0), new Complex(2, 0), 
				new Complex(7, 2));
		assertEquals(new ComplexPolynomial(new Complex(5, 0), new Complex(4, 0), new Complex(21, 6)), pol1.derive());
	}

}
