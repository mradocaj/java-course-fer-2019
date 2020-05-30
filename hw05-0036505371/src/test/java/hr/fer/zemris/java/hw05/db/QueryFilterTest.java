package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QueryFilterTest {

	StudentDatabase database;
	
	@BeforeEach
	void init() throws IOException {
		database = StudentDatabaseTest.getDatabase();
	}
	
	@Test
	void testAcceptingFilter() {
		QueryParser qp1 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		StudentRecord record = new StudentRecord("0123456789", "Sakić", "Ivan", 5);
		List<ConditionalExpression> list = qp1.getQuery();
		QueryFilter filter = new QueryFilter(list);
		assertTrue(filter.accepts(record));
	}

	@Test
	void testNonAcceptingFilter() {
		QueryParser qp1 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		StudentRecord record = new StudentRecord("0123456789", "Andrić", "Ivan", 5);
		List<ConditionalExpression> list = qp1.getQuery();
		QueryFilter filter = new QueryFilter(list);
		assertFalse(filter.accepts(record));
	}
	
	@Test
	void testJmbagFilter() {
		QueryParser qp1 = new QueryParser("jmbag=\"0123456789\"");
		StudentRecord record = new StudentRecord("0123456789", "Andrić", "Ivan", 5);
		List<ConditionalExpression> list = qp1.getQuery();
		QueryFilter filter = new QueryFilter(list);
		assertTrue(filter.accepts(record));
	}
	
	@Test
	void testNameLikeFilter() {
		QueryParser qp1 = new QueryParser("jmbag=\"0123456789\" and firstName LIKE \"B*\"");
		StudentRecord record = new StudentRecord("0123456789", "Andrić", "Boris", 5);
		List<ConditionalExpression> list = qp1.getQuery();
		QueryFilter filter = new QueryFilter(list);
		assertTrue(filter.accepts(record));
	}
	
	@Test
	void testFilterDatabase() {
		QueryParser qp1 = new QueryParser("firstName LIKE \"B*\"");
		QueryFilter filter = new QueryFilter(qp1.getQuery());
		List<StudentRecord> list = database.filter(filter);
		assertEquals(4, list.size());
	}
	
	@Test
	void testFilterDatabase2() {
		QueryParser qp1 = new QueryParser("lastName LIKE \"*ić\"");
		QueryFilter filter = new QueryFilter(qp1.getQuery());
		List<StudentRecord> list = database.filter(filter);
		assertEquals(33, list.size());
	}
	
	@Test
	void testFilterDatabase3() {
		QueryParser qp1 = new QueryParser("lastName LIKE \"*ić\" and firstName > \"Kristijan\"");
		QueryFilter filter = new QueryFilter(qp1.getQuery());
		List<StudentRecord> list = database.filter(filter);
		assertEquals(14, list.size());
	}
}
