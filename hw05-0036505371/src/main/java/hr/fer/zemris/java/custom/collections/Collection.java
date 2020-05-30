package hr.fer.zemris.java.custom.collections;

/**
 * Sučelje Collection definira osnovne metode za rad sa kolekcijama.
 * 
 * @author Maja Radočaj
 *
 */
public interface Collection<E> {

	/**
	 * Metoda koja provjerava postoje li elementi u trenutnoj kolekciji.
	 * 
	 * @return true ako je kolekcija prazna, false ako postoji barem 1 element
	 */
	default boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Metoda koja mora vraćati broj elemenata koji se trenutno nalazi u kolekciji.
	 * 
	 * @return veličinu kolekcije
	 */
	int size();

	/**
	 * Metoda za dodavanje novog elementa u kolekciju.
	 * 
	 * @param value vrijednost koju treba dodati u kolekciju
	 */
	void add(E value);

	/**
	 * Metoda koja provjerava nalazi li se dani objekt u trenutnoj kolekciji.
	 * 
	 * @param value element za koji se treba provjeriti nalazi li se u kolekciji
	 * @return true ako se element value nalazi u kolekciji, false ako ne
	 */
	boolean contains(Object value);

	/**
	 * Metoda za uklanjanje jednog elementa iz kolekcije.
	 * 
	 * @param value element kojeg treba ukloniti
	 * @return true ako se element value nalazi u kolekciji i jednom se ukloni, u
	 *         svakom drugom slučaju false
	 */
	boolean remove(Object value);

	/**
	 * Metoda koja vraća elemente kolekcije u obliku polja objekata.
	 * 
	 * @return polje objekata koje sadrži elemente kolekcije
	 */
	Object[] toArray();

	/**
	 * Metoda koja za svaki element kolekcije poziva metodu processora process.
	 * 
	 * @param processor procesor koji služi za obradu elemenata
	 */
	default void forEach(Processor<? super E> processor) {
		ElementsGetter<E> getter = this.createElementsGetter();
		while(getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}

	/**
	 * Metoda koja prima neku kolekciju te dodaje sve njene elemente u našu
	 * kolekciju tako da originalna kolekcija ostane nepromijenjena. To radi pomoću
	 * Processora i metode process koju smo implementirali u lokalnoj klasi.
	 * 
	 * @param other kolekcija čije sve elemente dodajemo u našu kolekciju
	 */
	default void addAll(Collection<? extends E> other) {
		class AddingProcessor<T> implements Processor<T> {

			@Override
			@SuppressWarnings("unchecked")
			public void process(T value) {
				add((E) value);
			}
		}

		other.forEach(new AddingProcessor<>());
	}

	/**
	 * Metoda koja uklanja sve elemente iz kolekcije.
	 */
	void clear();

	/**
	 * Metoda koja vraća objekt koji implementira ElementsGetter. Taj objekt
	 * poslužit će za dohvat elemenata kolekcije.
	 * 
	 * @return objekt za dohvat elemenata
	 */
	ElementsGetter<E> createElementsGetter();

	/**
	 * Metoda dohvaća redom sve elemente iz predane kolekcije col pomoću
	 * ElementsGettera, te u trenutnu kolekciju na kraj dodaje sve elemente koje
	 * predani tester prihvati.
	 * 
	 * @param col    predana kolekcija
	 * @param tester predani tester
	 */
	default void addAllSatisfying(Collection<? extends E> col, Tester<? super E> tester) {
		ElementsGetter<? extends E> getter = col.createElementsGetter();
		while(getter.hasNextElement()) {
			E value = (E) getter.getNextElement();
			if(tester.test(value)) {
				this.add(value);
			}
		}
	}
}
