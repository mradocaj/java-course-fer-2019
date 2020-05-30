package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.Objects;

/**
 * Razred ArrayIndexedCollection modelira kolekciju u obliku niza promjenjive
 * veličine. Svaki objekt ima dvije privatne varijable - size (broj elemenata
 * koji su trenutno u kolekciji) i niz elemenata elements[]. Ova vrsta kolekcije
 * ne smije imati null vrijednosti, ali smiju postojati dvostruki elementi. Ovaj
 * razred implementira sve nedovršene metode iz svog nadrazreda Collection te
 * ima neke specijalizirane metode za rad sa indeksiranim nizom.
 * 
 * @author Maja Radočaj
 *
 */
public class ArrayIndexedCollection extends Collection {

	/**
	 * Broj elemenata koji se trenutno nalaze u kolekciji.
	 */
	private int size;
	/**
	 * Polje elemenata kolekcije.
	 */
	private Object[] elements;

	/**
	 * Defaultna vrijednost veličine kolekcije.
	 */
	private static final int DEFAULT_VALUE = 16;

	/**
	 * Prazni konstruktor, stvara prazno polje elemenata početne dužine 16. Delegira
	 * to stvaranje konstruktoru koji prima int vrijednost kao argument.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_VALUE);
	}

	/**
	 * Konstruktor koji prima int argument - veličinu polja elemenata. U slučaju da
	 * je argument manji od 1, baca se iznimka.
	 * 
	 * @param initialCapacity veličina polja elemenata
	 * @throws IllegalArgumentException ako je kapacitet manji od 1
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		elements = new Object[initialCapacity];
	}

	/**
	 * Konstruktor koji prima drugu kolekciju čije elemente kopira i sprema u polje
	 * elemenata. Delegira se drugom konstruktoru koji uz kolekciju prima i veličinu
	 * polja.
	 * 
	 * @param other kolekcija prema kojoj modeliramo novu kolekciju
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, other.size());
	}

	/**
	 * Konstruktor koji prima drugu kolekciju i veličinu polja. Prvo se delegira
	 * stvaranje polja drugom konstruktoru, a ako je dan broj manji od 1 se baca
	 * iznimka IllegalArgumentException. <p>Ako je umjesto kolekcije dan null pointer,
	 * također se baca iznimka (NullPointerException). U slučaju da je veličina
	 * druge kolekcije veća od dane veličine polja, za veličinu polja se uzima
	 * veličina druge kolekcije.
	 * 
	 * @param other           kolekcija prema kojoj modeliramo novu kolekciju
	 * @param initialCapacity veličina polja elemenata
	 * @throws NullPointerException ako se preda <code>null</code>
	 * @throws IllegalArgumentException u slučaju greške
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		this(Math.max(initialCapacity, other.size()));
		Objects.requireNonNull(other);
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
	 * null vrijednost, baca se NullPointerException. Ako se u trenutku pozivanja
	 * metode već nalazi maksimalan broj elemenata u polju, vrši se realokacija
	 * memorije (polje postaje dvostruko veće).
	 * 
	 * @param value vrijednost koju treba dodati u kolekciju
	 * @throws NullPointerException ako se preda <code>null</code>
	 */
	@Override
	public void add(Object value) {
		insert(value, size);
	}

	/**
	 * Metoda koja provjerava nalazi li se dani objekt u trenutnoj kolekciji.
	 * 
	 * @param value element za koji se treba provjeriti nalazi li se u kolekciji
	 * @return <code>true</code> ako se element value nalazi u kolekciji, <code>false</code> ako ne
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
	 * Metoda koja vraća elemente kolekcije u obliku polja objekata. Vraćaju se samo
	 * ne-null elementi polja (prvih size elemenata).
	 * 
	 * @return polje objekata koje sadrži elemente kolekcije
	 */
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, size);
	}

	/**
	 * Metoda koja za svaki element kolekcije poziva metodu procesora process.
	 * 
	 * @param processor procesor koji služi za obradu elemenata
	 */
	@Override
	public void forEach(Processor processor) {
		for(int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}

	/**
	 * Metoda koja uklanja sve elemente iz kolekcije (elemente vraća na null, size
	 * postavlja na 0). Složenost metode je O(n).
	 */
	@Override
	public void clear() {
		for(int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}

	/**
	 * Metoda koja vraća objekt koji se u polju nalazi na poziciji index. Metoda
	 * može dohvatiti elemente u rasponu od 0 do size - 1 (granice uključene). Ako
	 * se pošalje indeks izvan tog raspona, baca se IndexOutOfBoundsException.
	 * Složenost metode je O(1).
	 * 
	 * @param index indeks s kojeg želimo očitati element
	 * @return element na mjestu index
	 * @throws IndexOutOfBoundsException ako <code>index</code> nije u dobrom rasponu
	 */
	public Object get(int index) {
		Objects.checkIndex(0, size);
		return elements[index];
	}

	/**
	 * Ubacuje element value na mjesto position u polje. Elementi sa indeksom većim
	 * od position se pomiču za jedno mjesto udesno. Pozicija može biti od 0 do size
	 * (granice uključive). Ako je position izvan tog raspona, baca se
	 * IndexOutOfBoundsException. Složenost metode je O(n).
	 * 
	 * @param value    vrijednost koju treba dodati
	 * @param position pozicija na koju treba umetnuti element
	 * @throws IndexOutOfBoundsException ako je indeks van raspona
	 * @throws NullPointerException ako je predana vrijednost <code>null</code>
	 */
	public void insert(Object value, int position) {
		Objects.checkIndex(0, size + 1);
		Objects.requireNonNull(value);
		if(size == elements.length) {
			elements = Arrays.copyOf(elements, elements.length * 2); // realociranje memorije
		}
		for(int i = size - 1; i >= position; i--) {
			elements[i + 1] = elements[i];
		}
		elements[position] = value;
		size++;
	}

	/**
	 * Metoda koja vraća indeks na kojem se prvi put pojavljuje objekt value. Ako se
	 * vrijednost ne nalazi u polju, vraća se -1. Složenost metode je O(n).
	 * 
	 * @param value tražena vrijednost
	 * @return indeks na kojem se nalazi tražena vrijednost
	 */
	public int indexOf(Object value) {
		for(int i = 0; i < size; i++) {
			if(elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Metoda uklanja element sa mjesta index. Index može biti u rasponu od 0 do
	 * size - 1 (uključivo). U suprotnom se baca IndexOutOfBoundsException. Svi
	 * elementi sa indeksom većim od index se pomiču za jedno mjesto ulijevo.
	 * 
	 * @param index indeks sa kojeg se uklanja element
	 * @throws IndexOutOfBoundsException
	 */
	public void remove(int index) {
		Objects.checkIndex(0, size);
		for(int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}
		elements[size - 1] = null;
		--size;
	}

}
