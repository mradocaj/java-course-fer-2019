package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentDatabaseTest {			

	StudentDatabase database;
	
	@BeforeEach
	void init() throws IOException {
		database = getDatabase();
	}
	
	@Test
	void testConstructor() {
		List<String> list = new ArrayList<>();
		list.add("0000000012\tVarljajić\tJozefina\t5");
		StudentDatabase database = new StudentDatabase(list);
		assertNotNull(database);
	}
	
	@Test
	void testIllegalConstructor() {
		List<String> list = new ArrayList<>();
		list.add("0000000012\tIvić\tMarko\t-1");
		assertThrows(IllegalArgumentException.class, () -> new StudentDatabase(list));
	}
	
	@Test
	void testIllegalConstructor2() {
		List<String> list = new ArrayList<>();
		list.add("0000000012\tVarljajić\tJozefina");
		assertThrows(IllegalArgumentException.class, () -> new StudentDatabase(list));
	}
	
	@Test
	void testIllegalConstructor3() {
		List<String> list = new ArrayList<>();
		list.add("0000000012\tVarljajić\tJozefina\t5");
		list.add("0000000012\tVarljajić\tJozefina\t2");
		assertThrows(IllegalArgumentException.class, () -> new StudentDatabase(list));
	}
	
	@Test
	void testForSameJMBAG() {
		StudentRecord record = new StudentRecord("0000000003", "Bosnić", "Andrea", 4);
		assertEquals(record, database.forJMBAG("0000000003"));
	}
	
	@Test
	void testForDifferentJMBAG() {
		StudentRecord record = new StudentRecord("0000000003", "Bosnić", "Andrea", 4);
		assertNotEquals(record, database.forJMBAG("0000000005"));
	}
	
	@Test
	void testTrueFilter() {
		List<StudentRecord> result = database.filter(s -> { return true; });
		assertEquals(63, result.size());
	}
	
	@Test
	void testFalseFilter() {
		List<StudentRecord> result = database.filter(s -> { return false; });
		assertEquals(0, result.size());
	}

	static StudentDatabase getDatabase() throws IOException {
		List<String> list = Files.readAllLines(
				Paths.get("./database.txt"), 
				StandardCharsets.UTF_8);
		return new StudentDatabase(list);
	}
}
