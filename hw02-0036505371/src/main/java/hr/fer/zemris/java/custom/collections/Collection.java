package hr.fer.zemris.java.custom.collections;

/**
 * Klasa Collection modelira općenitu kolekciju objekata te nudi osnovne
 * operacije sa njima.
 * 
 * @author Maja Radočaj
 *
 */
public class Collection {

	/**
	 * Metoda koja provjerava postoje li elementi u trenutnoj kolekciji.
	 * 
	 * @return true ako je kolekcija prazna, false ako postoji barem 1 element
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Metoda koja vraća broj elemenata koji se trenutno nalazi u kolekciji. Ovdje
	 * je implementirana tako da uvijek vraća 0 te ju nadjačavaju razredi koji
	 * nasljeđuju ovu klasu.
	 * 
	 * @return veličinu kolekcije (u ovoj implementaciji uvijek 0)
	 */
	public int size() {
		return 0;
	}

	/**
	 * Metoda za dodavanje novog elementa u kolekciju. Ovdje je implementirana kao
	 * prazna metoda koju nadjačavaju razredi koji nasljeđuju ovu klasu.
	 * 
	 * @param value vrijednost koju treba dodati u kolekciju
	 */
	public void add(Object value) {

	}

	/**
	 * Metoda koja provjerava nalazi li se dani objekt u trenutnoj kolekciji. Ovdje
	 * je implementirana tako da uvijek vraća false te ju nadjačavaju razredi koji
	 * nasljeđuju ovu klasu.
	 * 
	 * @param value element za koji se treba provjeriti nalazi li se u kolekciji
	 * @return true ako se element value nalazi u kolekciji, false ako ne (u ovoj
	 *         implementaciji uvijek false)
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Metoda za uklanjanje jednog elementa iz kolekcije. Ovdje je implementirana
	 * tako da uvijek vraća false te ju nadjačavaju razredi koji nasljeđuju ovu
	 * klasu.
	 * 
	 * @param value element kojeg treba ukloniti
	 * @return true ako se element value nalazi u kolekciji i jednom se ukloni, u
	 *         svakom drugom slučaju false (u ovoj implementaciji uvijek false)
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Metoda koja vraća elemente kolekcije u obliku polja objekata. Ovdje je
	 * implementirana kao metoda koja uvijek baca iznimku.
	 * 
	 * @return polje objekata koje sadrži elemente kolekcije (u ovoj implementaciji
	 *         samo exception)
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Metoda koja za svaki element kolekcije poziva metodu processora process.
	 * Redoslijed poziva elemenata nije definiran za ovu implementaciju. Ovdje je
	 * implementirana kao prazna metoda koju nadjačavaju razredi koji nasljeđuju ovu
	 * klasu.
	 * 
	 * @param processor procesor koji služi za obradu elemenata
	 */
	public void forEach(Processor processor) {

	}

	/**
	 * Metoda koja prima neku kolekciju te dodaje sve njene elemente u našu
	 * kolekciju tako da originalna kolekcija ostane nepromijenjena. To radi pomoću
	 * Processora i metode process koju smo implementirali u lokalnoj klasi.
	 * 
	 * @param other kolekcija čije sve elemente dodajemo u našu kolekciju
	 */
	public void addAll(Collection other) {
		class AddingProcessor extends Processor {

			@Override
			public void process(Object value) {
				add(value);
			}
		}

		other.forEach(new AddingProcessor());
	}

	/**
	 * Metoda koja uklanja sve elemente iz kolekcije. Ovdje je implementirana kao
	 * prazna metoda koju nadjačavaju razredi koji nasljeđuju ovu klasu.
	 */
	public void clear() {

	}

}
