package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComplexNumberTest {			

	@Test
	void testConstructor() {
		ComplexNumber number = new ComplexNumber(2, 8);
		assertEquals(2, number.getReal());
		assertEquals(8, number.getImaginary());
	}
	
	@Test
	void fromRealTest() {
		ComplexNumber number = ComplexNumber.fromReal(8);
		assertEquals(8, number.getReal());
		assertEquals(0, number.getImaginary());
	}
	
	@Test
	void fromImaginaryTest() {
		ComplexNumber number = ComplexNumber.fromImaginary(8);
		assertEquals(0, number.getReal());
		assertEquals(8, number.getImaginary());
	}
	
	@Test
	void fromMagnitudeAndAngleTest() {
		ComplexNumber number = ComplexNumber.fromMagnitudeAndAngle(1, Math.PI / 2);
		ComplexNumber myNumber = new ComplexNumber(0, 1);
		assertEquals(myNumber, number);
		
		ComplexNumber numberTwo = ComplexNumber.fromMagnitudeAndAngle(1, Math.PI);
		ComplexNumber myNumberTwo = new ComplexNumber(-1, 0);
		assertEquals(myNumberTwo, numberTwo);
	}
	
	@Test
	void onlyImaginaryParse() {
		assertEquals(new ComplexNumber(0, 351), ComplexNumber.parse("351i"));
		assertEquals(new ComplexNumber(0, -317), ComplexNumber.parse("-317i"));
		assertEquals(new ComplexNumber(0, 3.51), ComplexNumber.parse("3.51i"));
	}
	
	@Test
	void parseTest() {
		String s = "2+8i";
		ComplexNumber number = ComplexNumber.parse(s);
		assertEquals(2, number.getReal());
		assertEquals(8, number.getImaginary());
		
		String string= "+7.82-3i";
		ComplexNumber secondNumber = ComplexNumber.parse(string);
		assertEquals(7.82, secondNumber.getReal());
		assertEquals(-3, secondNumber.getImaginary());
	}
	
	@Test
	void wrongParseTest() {
		String s = "-2++8i";
		assertThrows(NumberFormatException.class, () -> ComplexNumber.parse(s));
		
		String string = "-8.36.4i";
		assertThrows(NumberFormatException.class, () -> ComplexNumber.parse(string));
		
		String anotherOne = "1+1+1i";
		assertThrows(NumberFormatException.class, () -> ComplexNumber.parse(anotherOne));
		
		String anotherString = "i8";
		assertThrows(NumberFormatException.class, () -> ComplexNumber.parse(anotherString));
		
		String newString= "+7.82-4ži";
		assertThrows(NumberFormatException.class, () -> ComplexNumber.parse(newString));
	}

	@Test
	void getRealTest() {
		ComplexNumber num = new ComplexNumber(3.14, 8);
		assertEquals(3.14, num.getReal());
	}
	
	@Test
	void getImaginaryTest() {
		ComplexNumber num = new ComplexNumber(3.14, 8);
		assertEquals(8, num.getImaginary());
	}
	
	@Test
	void getMagnitudeTest() {
		ComplexNumber num = new ComplexNumber(3, 4);
		assertEquals(5, num.getMagnitude());
	}
	
	@Test
	void getAngleTest() {
		ComplexNumber numOne = new ComplexNumber(1, 1);
		assertEquals(Math.PI / 4, numOne.getAngle());
		
		ComplexNumber numTwo = new ComplexNumber(-1, 1);
		assertEquals(3 * Math.PI / 4, numTwo.getAngle());
		
		ComplexNumber numThree = new ComplexNumber(-1, -1);
		assertEquals(5 * Math.PI / 4, numThree.getAngle());
		
		ComplexNumber numFour = new ComplexNumber(1, -1);
		assertEquals(7 * Math.PI / 4, numFour.getAngle());
		
		ComplexNumber numFive = new ComplexNumber(0, -1);
		assertEquals(3 * Math.PI / 2, numFive.getAngle());
		
		ComplexNumber numSix = new ComplexNumber(0, 1);
		assertEquals(Math.PI / 2, numSix.getAngle());
	}
	
	@Test
	void addTest() {
		ComplexNumber numOne = new ComplexNumber(1, 1);
		ComplexNumber numTwo = new ComplexNumber(-3, 1);
		ComplexNumber result = numOne.add(numTwo);
		assertEquals(new ComplexNumber(-2, 2), result);
	}
	
	@Test
	void subTest() {
		ComplexNumber numOne = new ComplexNumber(1, 1);
		ComplexNumber numTwo = new ComplexNumber(-3, 1);
		ComplexNumber result = numOne.sub(numTwo);
		assertEquals(new ComplexNumber(4, 0), result);
	}
	
	@Test
	void mulTest() {
		ComplexNumber numOne = new ComplexNumber(2, 1);
		ComplexNumber numTwo = new ComplexNumber(-1, 3);
		ComplexNumber result = numOne.mul(numTwo);
		assertEquals(new ComplexNumber(-5, 5), result);
	}
	
	@Test 
	void divTest() {
		ComplexNumber numOne = new ComplexNumber(2, 1);
		ComplexNumber numTwo = new ComplexNumber(-1, 3);
		ComplexNumber result = numOne.div(numTwo);
		assertEquals(new ComplexNumber(1.0 / 10, -7.0 / 10), result);
	}
	
	@Test
	void powerTest() {
		ComplexNumber num = ComplexNumber.fromMagnitudeAndAngle(Math.sqrt(2), Math.PI / 4);
		ComplexNumber result = num.power(10);
		assertEquals(new ComplexNumber(0, 32), result);
		assertThrows(IllegalArgumentException.class, () -> num.power(-1));
	}
	
	@Test
	void rootTest() {
		ComplexNumber num = new ComplexNumber(1, 0);
		ComplexNumber[] result = num.root(6);
		assertEquals(6, result.length);
		assertEquals(new ComplexNumber(-1, 0), result[3]);
		assertThrows(IllegalArgumentException.class, () -> num.root(0));
	}
	
	@Test
	void equalsTest() {
		ComplexNumber numberOne = new ComplexNumber(1, 0);
		ComplexNumber numberTwo = numberOne;
		ComplexNumber numberThree = new ComplexNumber(5,6);
		assertEquals(numberOne, numberTwo);
		assertNotEquals(numberOne, numberThree);
	}
	
	@Test
	void toStringTest() {
		ComplexNumber numberOne = new ComplexNumber(1, 2);
		assertEquals("1.0000+2.0000i", numberOne.toString());
	}
	
}
