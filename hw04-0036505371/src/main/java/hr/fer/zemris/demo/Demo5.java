package hr.fer.zemris.demo;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;

/**
 * Razred koji demonstrira uporabu mape SimpleHashtable.
 * 
 * @author Maja Radočaj
 *
 */
public class Demo5 {

	/**
	 * Glavni program. Ovdje se demonstrira nepravilno uklanjanje elemenata tijekom iteriranja.
	 * Mijenjanje mape izvana tijekom iteriranja izaziva bacanje iznimke {@link ConcurrentModificationException}
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
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		try {
			while(iter.hasNext()) {
				SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
				if(pair.getKey().equals("Ivana")) {
					examMarks.remove("Ivana");
				}
			}
		} catch(ConcurrentModificationException ex) {
			System.out.println("An exception has been thrown! \nMessage: "+ ex.getMessage());
		}

	}
}
