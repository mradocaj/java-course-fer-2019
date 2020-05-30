package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Razred koji modelira kolekciju prostih brojeva.
 * U konstruktoru se definira koliko je prostih brojeva željeno, nakon čega je omogućeno iteriranje kroz njih.
 * 
 * @author Maja Radočaj
 *
 */
public class PrimesCollection implements Iterable<Integer> {

	/**
	 * Željeni broj prostih brojeva.
	 */
	int numberOfPrimes;
	
	/**
	 * Javni konstruktor koji inicijalizira broj prostih brojeva kojih kolekcija pohranjuje.
	 * 
	 * @param numberOfPrimes broj prostih brojeva
	 */
	public PrimesCollection(int numberOfPrimes) {
		this.numberOfPrimes = numberOfPrimes;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new PrimesIterator(numberOfPrimes);
	}
	
	/**
	 * Privatni razred koji predstavlja implementaciju iteratora nad Integerima.
	 * 
	 * @author Maja Radočaj
	 *
	 */
	private static class PrimesIterator implements Iterator<Integer> {

		/**
		 * Maksimalan broj prostih brojeva kojih kolekcija mora vratiti.
		 */
		int primesCount;
		/**
		 * Zadnji vraćeni prosti broj.
		 */
		int current;
		
		/**
		 * Konstruktor koji prima broj prostih brojeva kojih kolekcija mora vratiti.
		 * 
		 * @param primesCount broj prostih brojeva
		 */
		public PrimesIterator(int primesCount) {
			this.primesCount = primesCount;
			current = 1;
		}
		
		@Override
		public boolean hasNext() {
			if(primesCount == 0) return false;
			return true;
		}

		@Override
		public Integer next() {
			if(!hasNext()) {
				throw new NoSuchElementException("All elements have been returned.");
			}
			
			current++;
			while(true) {
				boolean isPrime = true;
				for(int i = 2; i <= Math.sqrt(current); i++) {
					if(current % i == 0) {
						current++;
						isPrime = false;
						break;
					}
				}
				if(isPrime) {
					primesCount--;
					return current;
				}
			}
		}
		
	}
}
