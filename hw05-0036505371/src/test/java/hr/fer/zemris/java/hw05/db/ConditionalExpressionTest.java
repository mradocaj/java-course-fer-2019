package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConditionalExpressionTest {

	StudentDatabase database;
	
	@BeforeEach
	void init() throws IOException {
		database = StudentDatabaseTest.getDatabase();
	}
	
	@Test
	void testFirstExpression() {
		StudentRecord record = database.forJMBAG("0000000052");		//0000000052	Slijepčević	Josip	5
		
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.LAST_NAME,
				 "Slijep*",
				 ComparisonOperators.LIKE
				);
		
		assertTrue(expr.getComparisonOperator().satisfied(
				 expr.getFieldGetter().get(record), 
				 expr.getStringLiteral()));
	}

	@Test
	void testSecondExpression() {
		StudentRecord record = database.forJMBAG("0000000031");		//0000000031	Krušelj Posavec	Bojan	4
		
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.FIRST_NAME,
				 "Zoran",
				 ComparisonOperators.GREATER
				);
		
		assertFalse(expr.getComparisonOperator().satisfied(
				 expr.getFieldGetter().get(record), 
				 expr.getStringLiteral()));
	}
	
	@Test
	void testThirdExpression() {
		StudentRecord record = database.forJMBAG("0000000031");		//0000000031	Krušelj Posavec	Bojan	4
		
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.JMBAG,
				 "0000000031",
				 ComparisonOperators.EQUALS
				);
		
		assertTrue(expr.getComparisonOperator().satisfied(
				 expr.getFieldGetter().get(record), 
				 expr.getStringLiteral()));
	}
	
	@Test
	void testForthExpression() {
		StudentRecord record = database.forJMBAG("0000000031");		//0000000008	Ćurić	Marko	5
		
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.FIRST_NAME,
				 "M*a",
				 ComparisonOperators.LIKE
				);
		
		assertFalse(expr.getComparisonOperator().satisfied(
				 expr.getFieldGetter().get(record), 
				 expr.getStringLiteral()));
	}
	
	@Test
	void testFifthExpression() {
		StudentRecord record = database.forJMBAG("0000000060");		//0000000060	Vignjević	Irena	5
		
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.JMBAG,
				 "0000000060",
				 ComparisonOperators.NOT_EQUALS
				);
		
		assertFalse(expr.getComparisonOperator().satisfied(
				 expr.getFieldGetter().get(record), 
				 expr.getStringLiteral()));
	}
	
	@Test
	void testSixthExpression() {
		StudentRecord record = database.forJMBAG("0000000057");		//0000000057	Širanović	Hrvoje	2
		
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.LAST_NAME,
				 "Širanović",
				 ComparisonOperators.GREATER_OR_EQUALS
				);
		
		assertTrue(expr.getComparisonOperator().satisfied(
				 expr.getFieldGetter().get(record), 
				 expr.getStringLiteral()));
	}
}
