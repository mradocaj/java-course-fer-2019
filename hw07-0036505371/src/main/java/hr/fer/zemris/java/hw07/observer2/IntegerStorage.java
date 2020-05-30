package hr.fer.zemris.java.hw07.observer2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Razred koji predstavlja subjekt (tj. objekt) u obliku koda zvan Observer pattern.
 * Naš subjekt enkapsulira jednu vrijednost - <code>int</code> vrijednost.
 * Akcije koje treba izvoditi nad subjektom dodaju se u obliku <code>observera</code>.
 * 
 * @author Maja Radočaj
 *
 */
public class IntegerStorage {

	/**
	 * Vrijednost koju razred pohranjuje.
	 */
	private int value;
	/**
	 * Lista observera koji prate promijene vrijednosti.
	 */
	private List<IntegerStorageObserver> observers; 

	/**
	 * Javni konstruktor koji inicijalizira vrijednost.
	 * 
	 * @param initialValue vrijednost koju razred pohranjuje
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		observers = new ArrayList<>();
	}

	/**
	 * Metoda za dodavanje novog observera (slušača promijena) u internu listu.
	 * U slučaju pokušaja dodavanja <code>null</code> vrijednosti, baca se iznimka.
	 * 
	 * @param observer observer kojeg treba dodati
	 * @throws NullPointerException ako se pokuša dodati <code>null</code> vrijednost
	 */
	public void addObserver(IntegerStorageObserver observer) {
		Objects.requireNonNull(observer);
		if(!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	/**
	 * Metoda za uklanjanje observera iz interne iste.
	 * U slučaju da se metodi pošalje <code>null</code> vrijednost, baca se iznimka.
	 * 
	 * @param observer observer kojeg treba ukloniti
	 * @throws NullPointerException ako se pokuša ukloniti <code>null</code> vrijednost
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		Objects.requireNonNull(observer);
		if(observers.contains(observer)) {
			observers.remove(observer);
		}
	}

	/**
	 * Metoda koja uklanja sve observere iz interne liste.
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Getter koji vraća pohranjenu vrijednost.
	 * 
	 * @return vrijednost koju pohranjujemo
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Metoda koja pohranjuje novu vrijednost i obaviještava sve observere da se vrijednost promijenila.
	 * 
	 * @param value nova vrijednost
	 */
	public void setValue(int value) {
		if (this.value != value) {
			IntegerStorageChange ischange = new IntegerStorageChange(this, this.value, value);
			this.value = value;
			
			if (observers != null) {
				List<IntegerStorageObserver> observersCopy = new ArrayList<>(observers);
				for (IntegerStorageObserver observer : observersCopy) {
					observer.valueChanged(ischange);
				}
			}
		}
	}
}
