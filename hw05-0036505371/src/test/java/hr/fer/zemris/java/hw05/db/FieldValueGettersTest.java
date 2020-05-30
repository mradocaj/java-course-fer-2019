package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FieldValueGettersTest {

	StudentDatabase database;
	
	@BeforeEach
	void init() throws IOException {
		database = StudentDatabaseTest.getDatabase();
	}
	
	@Test
	void testFirstName() {
		StudentRecord record = database.forJMBAG("0000000017");
		assertEquals("Goran",  FieldValueGetters.FIRST_NAME.get(record));
	}
	
	@Test
	void testFirstName2() {
		StudentRecord record = database.forJMBAG("0000000024");
		assertEquals("Đive",  FieldValueGetters.FIRST_NAME.get(record));
	}
	
	@Test
	void testLastName() {
		StudentRecord record = database.forJMBAG("0000000017");
		assertEquals("Grđan",  FieldValueGetters.LAST_NAME.get(record));
	}
	
	@Test
	void testLastName2() {
		StudentRecord record = database.forJMBAG("0000000015");
		assertEquals("Glavinić Pecotić",  FieldValueGetters.LAST_NAME.get(record));
	}
	
	@Test
	void testJMBAG() {
		StudentRecord record = database.forJMBAG("0000000017");
		assertEquals("0000000017",  FieldValueGetters.JMBAG.get(record));
	}

	@Test
	void testJMBAG2() {
		StudentRecord record = database.forJMBAG("0000000015");
		assertEquals("0000000015",  FieldValueGetters.JMBAG.get(record));
	}
	
}
