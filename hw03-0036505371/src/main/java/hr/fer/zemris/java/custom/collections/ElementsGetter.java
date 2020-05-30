package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Sučelje koje definira metode za dohvaćanje elemenata kolekcije.
 * 
 * @author Maja Radočaj
 *
 */
public interface ElementsGetter {

	/**
	 * Metoda koja provjerava postoji li u kolekciji element kojeg još nismo
	 * vratili. Ako je u međuvremenu kolekcija mijenjana, baca se
	 * {@link ConcurrentModificationException}.
	 * 
	 * @return true ako postoji element kojeg još nismo vratili, false u svakom
	 *         drugom sučaju
	 * @throws ConcurrentModificationException ako je kolekcija mijenjana prilikom
	 *                                         dohvaćanja elementa
	 */
	boolean hasNextElement();

	/**
	 * Metoda koja vraća točno jedan element kolekcije koji nije do sad vraćen.
	 * Ukoliko su svi elementi već vraćeni, baca se NoSuchElementException.
	 * 
	 * @return element kolekcije
	 * @throws NoSuchElementException ako su svi elementi već vraćeni
	 */
	Object getNextElement();

	/**
	 * Poziva metodu process nad svim elementima kolekcije koji nisu dosad
	 * dohvaćeni.
	 * 
	 * @param p procesor koji se koristi za obradu elemenata
	 */
	default void processRemaining(Processor p) {
		while(hasNextElement()) {
			p.process(getNextElement());
		}
	}
}
