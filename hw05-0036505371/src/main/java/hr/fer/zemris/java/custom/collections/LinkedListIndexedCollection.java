package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Ovaj razred modelira kolekciju u obliku dvostruko povezane liste. Lista se
 * sastoji od čvorova ListNode koji imaju reference na prethodni i sljedeći
 * čvor, kao i svoju vrijednost. Svaki objekt ima privatne varijable size, first
 * i last. First i last su reference na prvi i zadnji čvor u listi. Ova vrsta
 * kolekcije ne smije imati null vrijednosti, ali smiju postojati dvostruki
 * elementi. Ovaj razred implementira metode sučelja Collection te ima neke
 * specijalizirane metode za rad sa dvostruko povezanom listom.
 * 
 * @author Maja Radočaj
 *
 */
public class LinkedListIndexedCollection<E> implements List<E> {

	/**
	 * Klasa koja modelira jedan čvor liste. Svaki čvor ima referencu na prethodni i
	 * sljedeći čvor u listi, kao i svoju vrijednost.
	 * 
	 * @author Maja Radočaj
	 *
	 */
	private static class ListNode<T> {
		/**
		 * Referenca na prethodni čvor u listi.
		 */
		ListNode<T> previous;
		/**
		 * Referenca na sljedeći čvor u listi.
		 */
		ListNode<T> next;
		/**
		 * Vrijednost čvora.
		 */
		T value;
	}

	/**
	 * Broj elemenata koji se trenutno nalaze u kolekciji.
	 */
	private int size;
	/**
	 * Referenca na prvi član liste.
	 */
	private ListNode<E> first;
	/**
	 * Referenca na posljednji član liste.
	 */
	private ListNode<E> last;

	/**
	 * Brojač promjeni kolekcije.
	 */
	private long modificationCount;

	/**
	 * Prazan konstruktor, po defaultu su previous = null te next = null.
	 * 
	 */
	public LinkedListIndexedCollection() {

	}

	/**
	 * Konstruktor koji prima drugu kolekciju čije elemente kopira i sprema u svoju
	 * listu.
	 * 
	 * @param other kolekcija čiji elementi se kopiraju
	 */
	public LinkedListIndexedCollection(Collection<? extends E> other) {
		addAll(other);
	}

	/**
	 * Metoda koja vraća broj elemenata koji se trenutno nalazi u kolekciji.
	 * 
	 * @return veličinu kolekcije
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Metoda za dodavanje novog elementa u kolekciju. U slučaju da se pokuša dodati
	 * null vrijednost, baca se NullPointerException. Novododani član sada je na
	 * kraju liste. Složenost je O(1).
	 * 
	 * @param value vrijednost koju treba dodati u kolekciju
	 * @throws NullPointerException ako je dana vrijednost null
	 */
	@Override
	public void add(E value) {
		ListNode<E> newNode = new ListNode<>();
		newNode.value = Objects.requireNonNull(value);
		newNode.previous = last;
		if(last != null) {
			last.next = newNode;
		}
		last = newNode;
		if(first == null) {
			first = newNode;
		}
		size++;
		modificationCount++;
	}

	/**
	 * Metoda koja provjerava nalazi li se dani objekt u trenutnoj kolekciji.
	 * 
	 * @param value element za koji se treba provjeriti nalazi li se u kolekciji
	 * @return true ako se element value nalazi u kolekciji, false ako ne
	 */
	@Override
	public boolean contains(Object value) {
		return indexOf(value) != -1;
	}

	/**
	 * Metoda za uklanjanje jednog elementa iz kolekcije. Metoda prolazi kroz polje
	 * te kad prvi put naiđe na instancu traženog objekta ga uklanja i vraća true.
	 * 
	 * @param value element kojeg treba ukloniti
	 * @return true ako se element value nalazi u kolekciji i jednom se ukloni, u
	 *         svakom drugom slučaju false
	 */
	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);
		if(index == -1) {
			return false;
		}
		remove(index);
		return true;
	}

	/**
	 * Metoda koja vraća elemente kolekcije u obliku polja objekata.
	 * 
	 * @return polje objekata koje sadrži elemente kolekcije
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size()];
		ListNode<E> node = first;
		int i = 0;
		while(node != null) {
			array[i] = node.value;
			node = node.next;
			i++;
		}
		return array;
	}

	/**
	 * Metoda koja uklanja sve elemente iz kolekcije (first i last vraća na null,
	 * size postavlja na 0).
	 */
	@Override
	public void clear() {
		first = null;
		last = null;
		size = 0;
		modificationCount++;
	}

	/**
	 * Metoda koja vraća element koji se u listi nalazi na poziciji index. Metoda
	 * može dohvatiti elemente u rasponu od 0 do size - 1 (granice uključene). Ako
	 * se pošalje indeks izvan tog raspona, baca se IndexOutOfBoundsException.
	 * Složenost metode je O(n/2 + 1).
	 * 
	 * @param index indeks s kojeg želimo očitati element
	 * @return element na mjestu index
	 * @throws IndexOutOfBoundsException ako je indeks manji od 0 ili veći od size -
	 *                                   1
	 */
	@Override
	public E get(int index) {
		if(index < 0 || index > (size - 1)) {
			throw new IndexOutOfBoundsException();
		}
		return findNode(index).value;
	}

	/**
	 * Ubacuje element value na mjesto position u listu. Elementi sa indeksom većim
	 * od position se pomiču za jedno mjesto udesno. Pozicija može biti od 0 do size
	 * (granice uključive). Ako je position izvan tog raspona, baca se
	 * IndexOutOfBoundsException. Ako se pokuša dodati null, baca se
	 * NullPointerException. Složenost metode je O(n).
	 * 
	 * @param value    vrijednost koju treba dodati
	 * @param position pozicija na koju treba umetnuti element
	 * @throws IndexOutOfBoundsException ako je indeks manji od 0 ili veći od size
	 * @throws NullPointerException      ako je predana null vrijednost
	 */
	@Override
	public void insert(E value, int position) {
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		if(position == size) {
			add(value); // ako se dodaje na kraj, to je onda kao add metoda
			return;
		}
		ListNode<E> newNode = new ListNode<>();
		newNode.value = Objects.requireNonNull(value);

		if(position == 0) { // ako je pozicija 0, mora se mijenjati first i provjeriti je li on null
			newNode.next = first;
			if(first != null) {
				first.previous = newNode;
			}
			first = newNode;
		} else {
			ListNode<E> node = findNode(position - 1); // ako pozicija nije 0 ni size, onda ide ovaj slučaj

			newNode.next = node.next;
			node.next = newNode;
			newNode.previous = node;
			newNode.next.previous = newNode;
		}
		size++;
		modificationCount++;
	}

	/**
	 * Metoda koja vraća indeks na kojem se prvi put pojavljuje element value. Ako
	 * se vrijednost ne nalazi u listi, vraća se -1. Složenost metode je O(n).
	 * 
	 * @param value tražena vrijednost
	 * @return indeks na kojem se nalazi tražena vrijednost
	 */
	@Override
	public int indexOf(Object value) {
		ListNode<E> node = first;
		int index = 0;
		while(node != null) {
			if(node.value.equals(value)) {
				return index;
			}
			node = node.next;
			index++;
		}
		return -1;
	}

	/**
	 * Metoda uklanja element sa mjesta index. Index može biti u rasponu od 0 do
	 * size - 1 (uključivo). U suprotnom se baca IndexOutOfBoundsException.
	 * 
	 * @param index indeks sa kojeg se uklanja element
	 * @throws IndexOutOfBoundsException ako je indeks manji od 0 ili veći od size
	 */
	@Override
	public void remove(int index) {
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		ListNode<E> node = findNode(index);

		removeNode(node); // poziv privatne metode - za sprečavanje dupliciranja koda
	}

	/**
	 * Privatna pomoćna metoda za uklanjanja čvora iz liste. Metoda provjerava rubne
	 * uvjete (čvor na početku ili na kraju liste) te briše čvor.
	 * 
	 * @param node čvor kojeg treba ukloniti
	 */
	private void removeNode(ListNode<E> node) {
		if(node.previous != null) {
			node.previous.next = node.next;
		} else {
			first = node.next;
		}
		if(node.next != null) {
			node.next.previous = node.previous;
		} else {
			last = node.previous;
		}
		node.value = null;
		size--;
		modificationCount++;
	}

	/**
	 * Privatna pomoćna metoda za nalaženje čvora na nekom indeksu.
	 * 
	 * @param index indeks s kojeg tražimo čvor
	 * @return traženi čvor
	 */
	private ListNode<E> findNode(int index) {
		ListNode<E> node;
		if(index < size / 2) {
			node = first;
			for(int i = 0; i < index; i++) {
				node = node.next;
			}
		} else {
			node = last;
			for(int i = size - 1; i > index; i--) {
				node = node.previous;
			}
		}
		return node;
	}

	/**
	 * Privatni statički razred koji predstavlja implementaciju sučelja
	 * ElementsGettera. Objekt će vratiti novi element koji još nije vraćen, a
	 * ukoliko takvih nema se baca iznimka NoSuchElementException.
	 * 
	 * @author Maja Radočaj
	 *
	 */
	private static class LinkedListElementsGetter<T> implements ElementsGetter<T> {

		/**
		 * Kolekcija čije elemente vraćamo.
		 */
		private LinkedListIndexedCollection<T> col;
		/**
		 * Pomoćni čvor.
		 */
		private ListNode<T> node;
		/**
		 * Brojač promjena kolekcije.
		 */
		private long savedModificationCount;

		/**
		 * Konstruktor koji prima kolekciju i prema njoj definira brojač.
		 * 
		 * @param col predana kolekcija
		 */
		private LinkedListElementsGetter(LinkedListIndexedCollection<T> col) {
			this.col = col;
			this.node = col.first;
			savedModificationCount = col.modificationCount;
		}

		@Override
		public boolean hasNextElement() {
			checkIfModified();
			return node != null;
		}

		@Override
		public T getNextElement() {
			checkIfModified();
			if(!hasNextElement()) {
				throw new NoSuchElementException();
			}
			T nextElement = node.value;
			node = node.next;
			return nextElement;
		}

		/**
		 * Privatna metoda koja baca iznimku ako je došlo do promjene kolekcije.
		 */
		private void checkIfModified() {
			if(savedModificationCount != col.modificationCount) {
				throw new ConcurrentModificationException();
			}
		}
	}

	@Override
	public ElementsGetter<E> createElementsGetter() {
		return new LinkedListElementsGetter<>(this);
	}

}
