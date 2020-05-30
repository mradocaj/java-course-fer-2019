package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Testna klasa za klasu Factorial.
 * 
 * @author Maja Radočaj
 *
 */
class FactorialTest {

		@Test
		public void testFactorialCalculation() {
			assertEquals(1, Factorial.calculateFactorial(0));
			assertEquals(6, Factorial.calculateFactorial(3));
			assertEquals(6227020800L, Factorial.calculateFactorial(13));
		}
		
		@Test
		public void testUpperLimit() {
			boolean exception = false;
			try {
				Factorial.calculateFactorial(21);
			} catch(IllegalArgumentException ex) {
				exception = true;
			}
			assertTrue(exception);
		}
		
		@Test
		public void testLowerLimit() {
			boolean exception = false;
			try {
				Factorial.calculateFactorial(-1);
			} catch(IllegalArgumentException ex) {
				exception = true;
			}
			assertTrue(exception);
		}

}
