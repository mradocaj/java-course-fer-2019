package hr.fer.zemris.demo;

import java.util.Iterator;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;

/**
 * Razred koji demonstrira uporabu mape SimpleHashtable.
 * 
 * @author Maja Radočaj
 *
 */
public class Demo4 {

	/**
	 * Glavni program. Ovdje se demonstrira nepravilno uklanjanje elemenata tijekom iteriranja.
	 * Pozivanje metode iteratora remove nad istim elementom izaziva bacanje iznimke {@link IllegalStateException}.
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
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				iter.remove();
				try {
					iter.remove();
				} catch(IllegalStateException ex) {
					System.out.println("An exception has been thrown! \nMessage: "+ ex.getMessage());
				}
			}
		}

	}
}
