package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Parametrizirani razred koji modelira jednostavnu mapu i metode za rad s
 * njome. On pod zadanim ključem pamti neku vrijednost. Ključevi ne smiju biti
 * <code>null</code>, dok vrijednosti smiju.
 * 
 * @author Maja Radočaj
 *
 * @param <K> tip ključa
 * @param <V> tip vrijednosti
 */
public class Dictionary<K, V> {

	/**
	 * Kolekcija koja nam služi za pohranjivanje vrijednosti.
	 */
	private ArrayIndexedCollection<Entry<K, V>> values;

	/**
	 * Razred koji nam služi za modeliranje jednog zapisa. Svaki zapis ima svoj
	 * ključ i svoju vrijednost.
	 * 
	 * @author Maja Radočaj
	 *
	 * @param <T> tip ključa
	 * @param <E> tip vrijednosti
	 */
	private static class Entry<K, V> {
		/**
		 * Ključ zapisa.
		 */
		private K key;
		/**
		 * Vrijednost zapisa.
		 */
		private V value;

		/**
		 * Konstruktor koji inicijalizira ključ i vrijednost zapisa na zadane
		 * vrijednosti. Ključ ne smije biti <code>null</code>. Ukoliko je, baca se
		 * {@link NullPointerException}.
		 * 
		 * @param key   predani ključ
		 * @param value predana vrijednost
		 * @throws NullPointerException ako je predani ključ <code>null</code>.
		 */
		private Entry(K key, V value) {
			this.key = Objects.requireNonNull(key);
			this.value = value;
		}

	}

	/**
	 * Javni konstruktor za stvaranje nove mape.
	 */
	public Dictionary() {
		values = new ArrayIndexedCollection<>();
	}

	/**
	 * Metoda koja vraća informaciju o tome je li mapa prazna.
	 * 
	 * @return <code>true</code> ako je mapa prazna, <code>false</code> ako nije
	 */
	public boolean isEmpty() {
		return values.isEmpty();
	}

	/**
	 * Metoda koja vraća broj elemenata trenutno pohranjenih u mapi.
	 * 
	 * @return broj elemenata pohranjenih u mapi
	 */
	public int size() {
		return values.size();
	}

	/**
	 * Metoda za brisanje svih elemenata iz mape.
	 */
	public void clear() {
		values.clear();
	}

	/**
	 * Metoda koja dodaje novi zapis u mapu. Ako već postoji neki zapis pod istim
	 * ključem, uklanjamo stari zapis i dodajemo novi. Argument <code>key</code> ne
	 * smije biti <code>null</code>. Ukoliko je, baca se
	 * {@link NullPointerException}.
	 * 
	 * @param key   ključ zapisa
	 * @param value vrijednost zapisa
	 * @throws NullPointerException ako je predani ključ <code>null</code>
	 */
	public void put(K key, V value) {
		Entry<K, V> newEntry = new Entry<>(key, value);
		ElementsGetter<Entry<K, V>> getter = values.createElementsGetter();

		while(getter.hasNextElement()) {
			Entry<K, V> element = getter.getNextElement();
			if(element.key.equals(key)) {
				values.remove(element);
				break;
			}
		}
		values.add(newEntry);
	}

	/**
	 * Metoda koja vraća vrijednost za neki predani ključ. Ukoliko ne postoji
	 * vrijednost sa danim ključem u mapi, vraća se <code>null</code>.
	 * 
	 * @param key ključ čiju vrijednost tražimo
	 * @return nađena vrijednost ili <code>null</code> ako takva vrijednost ne
	 *         postoji
	 */
	public V get(Object key) {
		if(key == null)
			return null;
		ElementsGetter<Entry<K, V>> getter = values.createElementsGetter();

		while(getter.hasNextElement()) {
			Entry<K, V> element = getter.getNextElement();
			if(element.key.equals(key)) {
				return element.value;
			}
		}
		return null;
	}

}
