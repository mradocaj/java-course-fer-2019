package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import static java.lang.Math.*;

/**
 * Razred SimpleHashtable modelira tablicu raspršenog adresiranja koja omogućava
 * pohranu uređenih parova ključ, vrijednost. U ovoj implementaciji ključevi ne
 * smiju biti <code>null</code>, dok vrijednosti smiju. Osim pohranu
 * vrijednosti, razred nudi brojne duge operacije tipične za rad sa kolekcijama.
 * 
 * @author Maja Radočaj
 *
 * @param <K> tip ključa
 * @param <V> tip vrijednosti
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/**
	 * Tablica za pohranu zapisa u obliku uređenih parova ključ, vrijednost.
	 */
	private TableEntry<K, V>[] table;
	/**
	 * Broj trenutno pohranjenih elemenata u tablici.
	 */
	private int size;
	/**
	 * Brojač izmjena kolekcije.
	 */
	private int modificationCount;
	/**
	 * Pretpostavljena veličina tablice pri stvaranju nove kolekcije.
	 */
	private static final int DEFAULT_SIZE = 16;
	/**
	 * Baza za određivanje veličine tablice.
	 */
	private static final int BASE = 2;
	/**
	 * Pomoćna vrijednost pri određivanju treba li povećati kapacitet tablice.
	 */
	private static final double CAPACITY_PERCENTAGE = 0.75;

	/**
	 * Razred koji modelira jedan zapis u tablici raspršenog adresiranja. Svaki
	 * zapis ima svoj ključ (<code>key</code>) i vrijednost (<code>value</code>). Uz
	 * to, svaki zapis mora pamtiti koji je sljedeći zapis u istom slotu tablice
	 * (<code>next</code>).
	 * 
	 * @author Maja Radočaj
	 *
	 * @param <K> tip ključa
	 * @param <V> tip vrijednosti
	 */
	public static class TableEntry<K, V> {

		/**
		 * Ključ zapisa.
		 */
		private K key;
		/**
		 * Vrijednost zapisa.
		 */
		private V value;
		/**
		 * Sljedeći zapis u istom slotu tablice.
		 */
		private TableEntry<K, V> next;

		/**
		 * Konstruktor za inicijaliziranje zapisa. Ukoliko je predani ključ
		 * <code>null</code>, baca se {@link NullPointerException}.
		 * 
		 * @param key   ključ zapisa
		 * @param value vrijednost zapisa
		 * @throws NullPointerException ako je predani ključ <code>null</code>
		 */
		public TableEntry(K key, V value) {
			this.key = Objects.requireNonNull(key);
			this.value = value;
		}

		/**
		 * Getter koji vraća vrijednost zapisa.
		 * 
		 * @return vrijednost zapisa
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Setter koji postavlja vrijednost zapisa.
		 * 
		 * @param value nova vrijednost zapisa
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Getter koji vraća ključ zapisa.
		 * 
		 * @return ključ zapisa
		 */
		public K getKey() {
			return key;
		}

	}

	/**
	 * Konstruktor koji stvara novu tablicu pretpostavljene veličine (16).
	 */
	public SimpleHashtable() {
		this(DEFAULT_SIZE);
	}

	/**
	 * Konstruktor koji stvara novu tablicu raspršenog adresiranja veličine
	 * <code>capacity</code>. Predana veličina mora biti veća ili jednaka 1. Ukoliko
	 * je dana veličina manja od 1, baca se {@link IllegalArgumentException}.
	 * 
	 * @param capacity
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if(capacity < 1) {
			throw new IllegalArgumentException("Size of table should be 1 or greater.");
		}
		int exponent = (int) floor(log(capacity) / log(BASE));
		table = (TableEntry<K, V>[]) new TableEntry[(int) pow(BASE, exponent)];
	}

	/**
	 * Metoda koja stavlja novi zapis u kolekciju (ako zapis sa takvim ključem već
	 * ne postoji). Predani ključ ne smije biti <code>null</code>, dok vrijednost
	 * smije. Ukoliko je ključ <code>null</code>, baca se
	 * {@link NullPointerException}. Ako već postoji zapis sa danim ključem, samo se
	 * postavlja nova vrijednost tog zapisa.
	 * 
	 * @param key   ključ zapisa
	 * @param value vrijednost zapisa
	 * @throws NullPointerException ako je predani ključ <code>null</code>.
	 */
	public void put(K key, V value) {
		Objects.requireNonNull(key);
		if(containsKey(key)) {
			getEntry(key).setValue(value);
		} else {
			checkCapacity();
			TableEntry<K, V> newEntry = new TableEntry<>(key, value);
			int slot = abs(key.hashCode()) % table.length;
			TableEntry<K, V> entry = table[slot];
			if(entry == null) {
				table[slot] = newEntry;
			} else {
				while(entry.next != null) {
					entry = entry.next;
				}
				entry.next = newEntry;
			}
			size++;
			modificationCount++;
		}
	}

	/**
	 * Metoda koja prima neki ključ <code>key</code> te, ako taj ključ postoji u
	 * tablici, vraća vrijednost koja mu pripada. Ako ne postoji dani ključ u
	 * tablici, vraća se <code>null</code>.
	 * 
	 * @param key predani ključ
	 * @return vrijednost koja odgovara predanom ključu ili <code>null</code> ako u
	 *         tablici ne postoji zapis sa predanim ključem.
	 */
	public V get(Object key) {
		if(!containsKey(key)) {
			return null;
		}
		return getEntry(key).value;
	}

	/**
	 * Metoda koja vraća broj uređenih parova u tablici.
	 * 
	 * @return broj uređenih parova u tablici
	 */
	public int size() {
		return size;
	}

	/**
	 * Metoda koja provjerava sadrži li tablica zapis sa predanim ključem
	 * <code>key</code>.
	 * 
	 * @param key predani ključ
	 * @return <code>true</code> ako sadrži zapis sa ključem <code>key</code>, u
	 *         suprotnome <code>false</code>
	 */
	public boolean containsKey(Object key) {
		if(key == null)
			return false;
		if(getEntry(key) == null)
			return false;
		return true;
	}

	/**
	 * Metoda koja provjerava sadrži li tablica zapis sa predanom vrijednosti
	 * <code>value</code>.
	 * 
	 * @param value predana vrijednost
	 * @return <code>true</code> ako sadrži zapis sa vrijednosi <code>value</code>,
	 *         u suprotnome <code>false</code>
	 */
	public boolean containsValue(Object value) {
		for(int i = 0; i < table.length; i++) {
			TableEntry<K, V> entry = table[i];

			if(value == null) {
				while(entry != null) {
					if(entry.getValue() == null) {
						return true;
					}
					entry = entry.next;
				}
			} else {
				while(entry != null) {
					if(entry.getValue().equals(value)) {
						return true;
					}
					entry = entry.next;
				}
			}
		}
		return false;
	}

	/**
	 * Metoda koja uklanja zapis koji ima ključ <code>key</code>. Ukoliko takav
	 * zapis ne postoji, nema promjena u tablici.
	 * 
	 * @param key ključ elementa kojeg treba ukloniti
	 */
	public void remove(Object key) {
		if(!containsKey(key))
			return;
		int slot = abs(key.hashCode()) % table.length;
		TableEntry<K, V> entry = table[slot];
		TableEntry<K, V> previousEntry = null;

		while(entry != null) {
			if(entry.getKey().equals(key)) {
				break;
			}
			previousEntry = entry;
			entry = entry.next;
		}
		if(previousEntry != null) {
			previousEntry.next = entry.next;
		} else {
			table[slot] = entry.next;
		}
		entry = null;
		size--;
		modificationCount++;
	}

	/**
	 * Metoda koja vraća informaciju o tomme je li tablica prazna.
	 * 
	 * @return <code>true</code> ako je prazna, <code>false</code> ako nije
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i = 0; i < table.length; i++) {
			TableEntry<K, V> entry = table[i];

			while(entry != null) {
				sb.append(entry.getKey().toString() + "=" + entry.getValue().toString());
				entry = entry.next;
				if(entry == null && i == table.length - 1) {
					break;
				}
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Metoda koja briše sve elemente iz tablice.
	 */
	public void clear() {
		for(int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		size = 0;
		modificationCount++;
	}

	/**
	 * Pomoćna metoda koja vraća zapis tipa <code>TableEntry<K, V></code> koji ima
	 * ključ <code>key</code>. Ako takav zapis ne postoji, vraća se
	 * <code>null</code>.
	 * 
	 * @param key ključ zapisa
	 * @return zapis sa danim ključem ili <code>null</code> ako takav zapis ne
	 *         postoji
	 */
	private TableEntry<K, V> getEntry(Object key) {
		int slot = abs(key.hashCode()) % table.length;
		TableEntry<K, V> entry = table[slot];

		while(entry != null) {
			if(entry.getKey().equals(key)) {
				return entry;
			}
			entry = entry.next;
		}
		return null; // vraća null ako nema entry-ja sa tim ključem
	}

	/**
	 * Metoda koja provjerava treba li povećati kapacitet tablice. Ako ne treba,
	 * izlazi se iz metode bez ikakvih promjena. Ako treba, dvostruko se povećava
	 * kapacitet tablice.
	 */
	private void checkCapacity() {
		if(size < CAPACITY_PERCENTAGE * table.length)
			return;
		TableEntry<K, V>[] oldTable = Arrays.copyOf(table, table.length);
		clear();
		table = Arrays.copyOf(table, table.length * 2);
		addAll(oldTable);
	}

	/**
	 * Pomoćna metoda koja dodaje sve elemente iz stare, manje tablice u novu,
	 * dvostruko veću tablicu.
	 * 
	 * @param other stara tablica
	 */
	private void addAll(TableEntry<K, V>[] other) {
		for(int i = 0; i < other.length; i++) {
			TableEntry<K, V> entry = other[i];

			while(entry != null) {
				put(entry.getKey(), entry.getValue());
				entry = entry.next;
			}
		}
	}

	/**
	 * Implementacija iteratora nad zapisima tablice TableEntry<K, V>. Uz dohvaćanje
	 * sljedećeg elementa, iterator omogućava i brisanje elementa tijekom
	 * iteriranja.
	 * 
	 * @author Maja Radočaj
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		/**
		 * Spremljeni broj modifikacija pri stvaranju iteratora.
		 */
		private int savedModification;
		/**
		 * Trenutni slot tablice u kojem se iterator nalazi.
		 */
		private int currentTableIndex;
		/**
		 * Trenutni zapis na kojem se nalazi iterator.
		 */
		private TableEntry<K, V> currentEntry;
		/**
		 * Prethodni zapis na kojem je iterator bio.
		 */
		private TableEntry<K, V> previousEntry;

		/**
		 * Konstruktor koji inicijalizira iterator. Sprema broj modifikacija primjerka
		 * nad kojim vrši iteriranje te pronalazi prvi zapis u tablici.
		 */
		private IteratorImpl() {
			savedModification = SimpleHashtable.this.modificationCount;
			currentTableIndex = getIndex(0);
			currentEntry = SimpleHashtable.this.table[currentTableIndex];
		}

		@Override
		public boolean hasNext() {
			checkIfModified();
			if(currentEntry == null)
				return false;
			return true;
		}

		@Override
		public TableEntry<K, V> next() {
			checkIfModified();
			if(!hasNext()) {
				throw new NoSuchElementException("All elements have been returned.");
			}
			previousEntry = currentEntry;
			currentEntry = getNextEntry();
			return previousEntry;
		}

		@Override
		public void remove() {
			checkIfModified();
			if(previousEntry == null) {
				throw new IllegalStateException("Must call the method next before remove.");
			}
			SimpleHashtable.this.remove(previousEntry.getKey());
			savedModification++;
			previousEntry = null;
		}

		/**
		 * Pomoćna metoda koja provjerava je li se kolekcija mijenjala izvana. Ako je,
		 * baca se {@link ConcurrentModificationException}.
		 * 
		 * @throws ConcurrentModificationException ako se kolekcija mijenja izvana
		 *                                         tijekom iteriranja
		 */
		private void checkIfModified() {
			if(savedModification != SimpleHashtable.this.modificationCount) {
				throw new ConcurrentModificationException("Collection mustn't be modified while iterating.");
			}
		}

		/**
		 * Pomoćna metoda koja vraća sljedeći slot u tablici koji sadrži neki zapis.
		 * Pretraživanje slotova kreće od indeksa <code>n</code>.
		 * 
		 * @param n indeks od kojeg kreće pretraživanje tablice
		 * @return prvi indeks na kojem se nalazi neki element ili 0 ako nema više
		 *         zapisa u tablici
		 */
		private int getIndex(int n) {
			TableEntry<K, V>[] table = SimpleHashtable.this.table;

			for(int i = n; i < table.length; i++) {
				if(table[i] != null) {
					return i;
				}
			}
			return 0;
		}

		private TableEntry<K, V> getNextEntry() {
			TableEntry<K, V>[] table = SimpleHashtable.this.table;

			if(currentEntry.next != null) {
				return currentEntry.next;
			} else {
				currentTableIndex = getIndex(currentTableIndex + 1);
				if(currentTableIndex == 0) {
					return null; // getIndex će vratiti 0 samo ako više nema nikakvih zapisa u tablici
				}
				return table[currentTableIndex];
			}
		}
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

}
