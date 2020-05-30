package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.Tester;

/**
 * Primjer za demonstraciju rada metoda razreda hr.fer.zemris.java.custom.collections.
 * 
 * @author Maja Radočaj
 *
 */
public class Example8 {
	
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
			 * Metoda koja vraća true ako je broj paran, a false ako je neparan ili nije broj.
			 */
			@Override
			public boolean test(Object obj) {
				if(!(obj instanceof Integer)) return false;
				Integer i = (Integer)obj;
				return i % 2 == 0;
			}
		}
		
		Tester t = new EvenIntegerTester();
		System.out.println(t.test("Ivo"));
		System.out.println(t.test(22));
		System.out.println(t.test(3));
		
		
	}

}
