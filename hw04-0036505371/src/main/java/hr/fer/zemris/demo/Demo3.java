package hr.fer.zemris.demo;

import java.util.Iterator;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;

/**
 * Razred koji demonstrira uporabu mape SimpleHashtable.
 * 
 * @author Maja Radočaj
 *
 */
public class Demo3 {

	/**
	 * Glavni program. Ovdje se demonstrira pravilno uklanjanje elemenata tijekom iteriranja pomoću metode iteratora remove.
	 * 
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); 
		
		System.out.println("Prije brisanja: " + examMarks.toString());
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				iter.remove(); // sam iterator kontrolirano uklanja trenutni element
			}
		}
		
		System.out.println("\nPoslije brisanja: " + examMarks.toString());

	}
}
