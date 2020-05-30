package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;
import hr.fer.zemris.java.custom.collections.Tester;

/**
 * Primjer za demonstraciju rada metoda razreda hr.fer.zemris.java.custom.collections.
 * 
 * @author Maja Radočaj
 *
 */
public class Example9 {

	/**
	 * Glavni program.
	 * 
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		
		/**
		 * Implementacija sučelja Tester.
		 * 
		 * @author Maja Radočaj
		 *
		 */
		class EvenIntegerTester implements Tester {
			
			/**
			 * Tester koji vraća true ako je broj paran, a false ako je neparan ili nije broj.
			 */
			@Override
			public boolean test(Object obj) {
				if(!(obj instanceof Integer)) return false;
				Integer i = (Integer)obj;
				return i % 2 == 0;
			}
		}

		Collection col1 = new LinkedListIndexedCollection();
		Collection col2 = new ArrayIndexedCollection();
		col1.add(2);
		col1.add(3);
		col1.add(4);
		col1.add(5);
		col1.add(6);
		col2.add(12);
		col2.addAllSatisfying(col1, new EvenIntegerTester());
		col2.forEach(System.out::println);
		}
}
