package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComplexTest {

	@Test
	void testParse() {
		String s1 = "1";
		Complex number1 = Complex.parse(s1);
		assertEquals(new Complex(1, 0), number1);
		
		String s2 = "i";
		Complex number2 = Complex.parse(s2);
		assertEquals(new Complex(0, 1), number2);
		
		String s3 = "-1 + i0";
		Complex number3 = Complex.parse(s3);
		assertEquals(new Complex(-1, 0), number3);
		
		String s4 = "0 - i1";
		Complex number4 = Complex.parse(s4);
		assertEquals(new Complex(0, -1), number4);
		
		String s5 = "i0";
		Complex number5 = Complex.parse(s5);
		assertEquals(new Complex(0, 0), number5);
		
		String s6 = " 0+i0";
		Complex number6 = Complex.parse(s6);
		assertEquals(new Complex(0, 0), number6);
		
		String s7 = "-i";
		Complex number7 = Complex.parse(s7);
		assertEquals(new Complex(0, -1), number7);
		
		String s8 = "-8 - i5";
		Complex number8 = Complex.parse(s8);
		assertEquals(new Complex(-8, -5), number8);
	}
	
	@Test
	void testThrows() {
		String s8 = "-8 - 5i";
		assertThrows(NumberFormatException.class, () -> Complex.parse(s8));
		
		String s9 = "-8 + 5i7";
		assertThrows(NumberFormatException.class, () -> Complex.parse(s9));
		
		String s10= "-8ž + i7";
		assertThrows(NumberFormatException.class, () -> Complex.parse(s10));
	}
	
	@Test 
	void divTest() {
		Complex numOne = new Complex(2, 1);
		Complex numTwo = new Complex(-1, 3);
		Complex result = numOne.divide(numTwo);
		assertEquals(new Complex(1.0 / 10, -7.0 / 10), result);
	}

}
