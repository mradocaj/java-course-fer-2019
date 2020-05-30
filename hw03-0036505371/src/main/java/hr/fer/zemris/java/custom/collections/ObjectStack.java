package hr.fer.zemris.java.custom.collections;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Klasa ObjectStack predstavlja stog te modelira osnovne funkcije za rad s
 * njime.
 * 
 * @author Maja Radočaj
 *
 */
public class ObjectStack {

	/**
	 * Prostor za pohranu elemenata stoga.
	 */
	private ArrayIndexedCollection stack;

	/**
	 * Prazni konstruktor, stvara novi primjerak razreda ArrayIndexedCollection za
	 * pohranu stoga.
	 *
	 */
	public ObjectStack() {
		stack = new ArrayIndexedCollection();
	}

	/**
	 * Metoda koja vraća informaciju o ispunjenosti stoga.
	 * 
	 * @return true ako je stog prazan, false ako ima barem jedan element na stogu
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	/**
	 * Metoda koja vraća broj elemenata na stogu.
	 * 
	 * @return broj elemenata na stogu
	 */
	public int size() {
		return stack.size();
	}

	/**
	 * Metoda za dodavanje elemenata na stog. U slučaju pokušaja dodavanja null
	 * vrijednosti na stog, baca se NullPointerException.
	 * 
	 * @param value element koji treba dodati na stog
	 * @throws NullPointerException
	 */
	public void push(Object value) {
		stack.add(value);
	}

	/**
	 * Metoda za skidanje elemenata s vrha stoga. U slučaju da u trenutku poziva
	 * metode nema elemenata na stogu, baca se EmptyStackException.
	 * 
	 * @return element s vrha stoga
	 * @throws EmptyStackException
	 */
	public Object pop() {
		Object element = peek();
		stack.remove(stack.size() - 1);
		return element;
	}

	/**
	 * Metoda koja vraća element s vrha stoga, ali ga ne uklanja. U slučaju da u
	 * trenutku poziva metode nema elemenata na stogu, baca se EmptyStackException.
	 * 
	 * @return element s vrha stoga
	 * @throws EmptyStackException
	 */
	public Object peek() {
		if(stack.isEmpty()) {
			throw new EmptyStackException();
		}
		Object element = stack.get(stack.size() - 1);
		return element;
	}

	/**
	 * Metoda za uklanjanje svih elemenata sa stoga.
	 */
	public void clear() {
		stack.clear();
	}
}
