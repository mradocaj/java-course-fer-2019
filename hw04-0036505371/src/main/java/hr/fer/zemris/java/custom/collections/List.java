package hr.fer.zemris.java.custom.collections;

/**
 * Sučelje koje definira kolekcije koje su indeksirane liste i njihove metode.
 * 
 * @author Maja Radočaj
 *
 */
public interface List<E> extends Collection<E> {

	/**
	 * Metoda koja vraća element koji se trenutno nalazi na traženom indeksu.
	 * 
	 * @param index traženi indeks
	 * @return element na traženom indeksu
	 */
	E get(int index);

	/**
	 * Metoda koja dodaje novi element sa vrijednosti value u listu na mjestu
	 * position.
	 * 
	 * @param value    vrijednost elementa
	 * @param position mjesto na koje treba dodati novi element
	 */
	void insert(E value, int position);

	/**
	 * Metoda koja vraća prvi indeks na kojem se pojavljuje element value.
	 * 
	 * @param value traženi element
	 * @return indeks na kojem se pojavljuje traženi element
	 */
	int indexOf(Object value);

	/**
	 * Metoda koja uklanja element sa nekog danog indeksa.
	 * 
	 * @param index dani indeks
	 */
	void remove(int index);

}
