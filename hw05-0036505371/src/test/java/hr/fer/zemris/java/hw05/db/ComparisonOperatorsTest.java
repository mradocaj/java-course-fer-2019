package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComparisonOperatorsTest {

	@Test
	void testLikeTrue() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertTrue(oper.satisfied("AAAA", "AA*AA")); 
	}

	@Test
	void testLikeTrue2() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertTrue(oper.satisfied("AAAA", "*")); 
	}
	
	@Test
	void testLikeTrue3() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertTrue(oper.satisfied("Kristijan", "*an")); 
	}
	
	@Test
	void testLikeTrue4() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertTrue(oper.satisfied("Kristijan", "Kri*")); 
	}
	
	@Test
	void testLikeTrue5() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertTrue(oper.satisfied("Kristijan", "Kri*an")); 
	}
	
	@Test
	void testLikeFalse() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertFalse(oper.satisfied("AAA", "AA*AA")); 
	}

	@Test
	void testLikeFalse2() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertFalse(oper.satisfied("Zagreb", "Aba*")); 
	}
	
	@Test
	void testLikeFalse3() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertFalse(oper.satisfied("Zagreb", "*jeb")); 
	}
	
	@Test
	void testLikeThrows() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertThrows(IllegalArgumentException.class, () -> oper.satisfied("Damjan", "D*m*n"));
	}
	
	@Test
	void testLess() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertTrue(oper.satisfied("Ana", "Jasna"));
		assertFalse(oper.satisfied("Zoran", "Jasna"));
	}
	
	@Test
	void testLessOrEquals() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertTrue(oper.satisfied("Ana", "Ana"));
		assertFalse(oper.satisfied("Zoran", "Jasna"));
	}
	
	@Test
	void testGreater() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertFalse(oper.satisfied("Ana", "Jasna"));
		assertTrue(oper.satisfied("Zoran", "Jasna"));
	}
	
	@Test
	void testGreaterOrEquals() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertTrue(oper.satisfied("Ana", "Ana"));
		assertTrue(oper.satisfied("Zoran", "Jasna"));
	}
	
	@Test
	void testEquals() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		assertTrue(oper.satisfied("Ana", "Ana"));
		assertFalse(oper.satisfied("Zoran", "Jasna"));
	}
	
	@Test
	void testNotEquals() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		assertFalse(oper.satisfied("Marko", "Marko"));
		assertTrue(oper.satisfied("Ivica", "Ivona"));
	}
}
