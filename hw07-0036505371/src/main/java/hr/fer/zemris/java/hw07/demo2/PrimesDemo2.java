package hr.fer.zemris.java.hw07.demo2;

/**
 * Program koji služi kao demonstracija rada razreda {@link PrimesCollection}.
 * 
 * @author Maja Radočaj
 *
 */
public class PrimesDemo2 {

	/**
	 * Glavni program.
	 * 
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}
	}

}
