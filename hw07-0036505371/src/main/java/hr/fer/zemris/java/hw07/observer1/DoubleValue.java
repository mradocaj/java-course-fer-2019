package hr.fer.zemris.java.hw07.observer1;

/**
 * Konkretni observer koji dvostruku vrijednost nove vrijednosti. Observer se
 * uklanja nakon što je vrijednost promijenjena <code>count</code> broj puta.
 * Broj <code>count</code> inicijalizira se u konstruktoru.
 * 
 * @author Maja Radočaj
 *
 */
public class DoubleValue implements IntegerStorageObserver {

	/**
	 * Broj obrade vrijednosti nakon kojeg se observer uklanja.
	 */
	int count;

	/**
	 * Konstruktor koji inicijalizira broj ponavljanja <code>count</code>.
	 * 
	 * @param count broj ponavljanja
	 */
	public DoubleValue(int count) {
		this.count = count;
	}

	@Override
	public void valueChanged(IntegerStorage istorage) {
		if(count <= 0) {
			istorage.removeObserver(this);
		} else {
			count--;
			System.out.println("Double value: " + istorage.getValue() * 2);
		}
	}

}
