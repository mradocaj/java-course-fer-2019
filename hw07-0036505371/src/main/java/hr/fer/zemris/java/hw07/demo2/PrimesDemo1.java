package hr.fer.zemris.java.hw07.demo2;

/**
 * Program koji služi kao demonstracija rada razreda {@link PrimesCollection}.
 * 
 * @author Maja Radočaj
 *
 */
public class PrimesDemo1 {

	/**
	 * Glavni program.
	 * 
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them

		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}

	}

}
