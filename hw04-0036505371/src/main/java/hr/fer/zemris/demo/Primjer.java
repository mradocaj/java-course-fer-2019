package hr.fer.zemris.demo;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;

/**
 * Razred koji demonstrira uporabu mape SimpleHashtable.
 * 
 * @author Maja Radočaj
 *
 */
public class Primjer {

	/**
	 * Glavni program.
	 * 
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		// create collection:
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		
		for(SimpleHashtable.TableEntry<String,Integer> pair : examMarks) {
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
			}
		
		}
	

}
