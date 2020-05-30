package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class QueryParserTest {

	@Test
	void testDirectQuery() {
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		assertTrue(qp1.isDirectQuery()); // true
		assertEquals("0123456789", qp1.getQueriedJMBAG());
		assertEquals(1,  qp1.getQuery().size()); // 
	}

	@Test
	void testNonDirectQuery() {
		QueryParser qp1 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertFalse(qp1.isDirectQuery()); // true
		assertThrows(IllegalStateException.class, () -> qp1.getQueriedJMBAG());
		assertEquals(2,  qp1.getQuery().size()); // 
	}

	@Test
	void testLongQuery() {
		QueryParser qp = new QueryParser("firstName>\"A\" and firstName<\"C\" and "
				+ "lastName LIKE \"B*ć\" and jmbag>\"0000000002\"");
		assertFalse(qp.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> qp.getQueriedJMBAG());
		assertEquals(4,  qp.getQuery().size());
		List<ConditionalExpression> list = qp.getQuery();
		assertTrue(list.get(0).getComparisonOperator().equals(ComparisonOperators.GREATER));
		assertTrue(list.get(2).getComparisonOperator().equals(ComparisonOperators.LIKE));
	}
	
	@Test
	void testWrongStringQuery() {
		assertThrows(QueryParserException.class, () -> new QueryParser("firstName>xdLol and firstName<name"));
	}
	
	@Test
	void testFirstString() {
		assertThrows(QueryParserException.class, () -> new QueryParser("\"Ante\" = firstName"));
	}
	
	@Test
	void testNoAnd() {
		assertThrows(QueryParserException.class, () -> new QueryParser("firstName = \"Ante\" lastName = \"Antić\""));
	}
	
	@Test
	void testCasing() {
		assertThrows(QueryParserException.class, () -> new QueryParser("firstName LikE \"Ante\" AND lastName = \"Antić\""));
	}
	
	@Test
	void testNoStringEnd() {
		assertThrows(QueryParserException.class, () -> new QueryParser("firstName LikE \"Ante"));
	}
}
