package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Razred koji modelira kolekciju sličnu mapi - postoje ključevi, a svakom ključu je pridodana vrijednost koja
 * predstavlja jedan stog. Za svaki od stogova omogućene su radnje karakteristične za stogove (push, pop, peek).
 * 
 * @author Maja Radočaj
 *
 */
public class ObjectMultistack {

	/**
	 * Mapa u koju se pohranjuju vrijednosti.
	 */
	private Map<String, MultistackEntry> entries = new HashMap<>();
	
	/**
	 * Privatni razred koji modelira jedan čvor (element na stogu).
	 * Svaki čvor ima vrijednost i referencu na sljedeći čvor.
	 * 
	 * @author Maja Radočaj
	 *
	 */
	private static class MultistackEntry {
		/**
		 * Vrijednost koju čvor pohranjuje.
		 */
		ValueWrapper value;
		/**
		 * Referenca na sljedeći čvor.
		 */
		MultistackEntry next;
		
		/**
		 * Konstruktor koji inicijalizira čvor.
		 * 
		 * @param value vrijednost čvora
		 * @throws NullPointerException ako je vrijednost <code>null</code>
		 */
		public MultistackEntry(ValueWrapper value) {
			this.value = Objects.requireNonNull(value);
		}
	}
	
	/**
	 * Metoda koja dodaje novi element na stog sa ključem <code>keyName</code>.
	 * Ključ i vrijednost ne smiju biti <code>null</code>.
	 * 
	 * @param keyName ključ
	 * @param valueWrapper vrijednost
	 * @throws NullPointerException ako se pokuša dodati <code>null</code> vrijednost ili ključ
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {	
		Objects.requireNonNull(keyName);
		MultistackEntry newEntry = new MultistackEntry(valueWrapper);
		
		if(entries.containsKey(keyName)) {
			newEntry.next = entries.get(keyName);
		}
		entries.put(keyName, newEntry);
	}
	
	/**
	 * Metoda koja skida jedan element sa stoga i vraća njegovu vrijednost.
	 * Metoda se ne smije pozvati nad ključem koji se ne nalazi u kolekciji.
	 * Predana vrijednost ne smije biti <code>null</code>.
	 * 
	 * @param keyName ključ stoga
	 * @return vrijednost sa vrha stoga
	 * @throws NullPointerException ako je predani ključ <code>null</code>
	 * @throws IllegalStateException ako se pokuša skinuti element sa praznog stoga
	 */
	public ValueWrapper pop(String keyName) {
		checkIfEmpty(Objects.requireNonNull(keyName));
		MultistackEntry entry = entries.get(keyName);
		
		if(entry.next == null) {
			entries.remove(keyName);
		} else {
			entries.put(keyName, entry.next);
		}
		return entry.value;
	}
	
	/**
	 * Metoda koja vraća vrijednost sa vrha stoga sa ključem <code>keyName</code>.
	 * Metoda se ne smije pozvati nad ključem koji se ne nalazi u kolekciji.
	 * Predana vrijednost ne smije biti <code>null</code>.
	 * 
	 * @param keyName ključ stoga
	 * @return vrijednost sa vrha stoga
	 * @throws NullPointerException ako je predani ključ <code>null</code>
	 * @throws IllegalStateException ako se pokuša skinuti element sa praznog stoga
	 */
	public ValueWrapper peek(String keyName) {
		checkIfEmpty(Objects.requireNonNull(keyName));
		return entries.get(keyName).value;
	}
	
	/**
	 * Metoda koja provjerava sadrži li kolekcija stog sa nekim <code>keyName</code>.
	 * 
	 * @param keyName ključ stoga
	 * @return <code>true</code> ako nema takvog stoga, <code>false</code> ako ima
	 */
	public boolean isEmpty(String keyName) {
		return !entries.containsKey(keyName);
	}
	
	/**
	 * Pomoćna metoda koja provjerava je li stog sa ključem <code>keyName</code> prazan.
	 * Ako je, baca se iznimka.
	 * 
	 * @param keyName ključ stoga
	 * @throws IllegalStateException ako je stog prazan
	 */
	private void checkIfEmpty(String keyName) {
		if(isEmpty(keyName)) {
			throw new IllegalStateException("Stack is empty.");
		}
	}
	
}
