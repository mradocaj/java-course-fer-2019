package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComplexRootedPolynomialTest {

	@Test
	void testApply() {
		ComplexRootedPolynomial pol1 = new ComplexRootedPolynomial(new Complex(1, 1), new Complex(1, 1), new Complex(1, 1));
		assertEquals(Complex.ZERO, pol1.apply(new Complex(1, 1)));
		
		ComplexRootedPolynomial pol2 = new ComplexRootedPolynomial(new Complex(1, 1), new Complex(1, 1));
		assertEquals(new Complex(1, 1), pol2.apply(new Complex(2, 1)));
	}

}
