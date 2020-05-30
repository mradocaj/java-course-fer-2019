package hr.fer.zemris.java.hw05.db.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.hw05.db.QueryFilter;
import hr.fer.zemris.java.hw05.db.QueryParser;
import hr.fer.zemris.java.hw05.db.QueryParserException;
import hr.fer.zemris.java.hw05.db.RecordFormatter;
import hr.fer.zemris.java.hw05.db.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord;

/**
 * Razred koji demonstrira korištenje baze podataka o studentima te mogućnosti
 * filtriranja zapisa. Program prima naredbe preko standardnog ulaza, a završava
 * se pri upisu "exit".
 * 
 * @author Maja Radočaj
 *
 */
public class StudentDB {

	/**
	 * Glavni program.
	 * 
	 * @param args argumenti naredbenog retka
	 * @throws IOException ako nije dobro definiran <code>path</code> tekstualne
	 *                     datoteke sa podacima o studentima.
	 */
	public static void main(String[] args) throws IOException {
		List<String> list = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		StudentDatabase database = new StudentDatabase(list);
		List<StudentRecord> studentList = new ArrayList<>();
		Scanner sc = new Scanner(System.in);

		while(true) {
			studentList.clear();
			System.out.printf("> ");
			String line = sc.nextLine().trim();
			if(line.equals("exit")) {
				System.out.println("Goodbye!");
				sc.close();
				return;
			}
			if(!line.startsWith("query")) {
				System.out.println("Unknown command: command must be \"query\".");
				continue;
			}

			try {
				QueryParser parser = new QueryParser(line.replace("query", ""));
				
				if(parser.isDirectQuery()) {
					StudentRecord r = database.forJMBAG(parser.getQueriedJMBAG());
					System.out.println("Using index for record retrieval.");
					studentList.add(r);
				} else {
					for(StudentRecord r : database.filter(new QueryFilter(parser.getQuery()))) {
						studentList.add(r);
					}
				}
				List<String> output = RecordFormatter.format(studentList);
				output.forEach(System.out::println);
				
			} catch(QueryParserException | IllegalArgumentException ex) {
				System.out.println("Invalid input: " + ex.getMessage());
			}
		}

	}
}
