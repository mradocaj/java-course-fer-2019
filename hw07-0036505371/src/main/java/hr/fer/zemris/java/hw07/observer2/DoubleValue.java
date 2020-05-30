package hr.fer.zemris.java.hw07.observer2;

/**
 * Konkretni observer koji dvostruku vrijednost nove vrijednosti.
 * Observer se uklanja nakon što je vrijednost promijenjena <code>count</code> broj puta.
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
	 * @param count broj ponavljanja
	 */
	public DoubleValue(int n) {
		this.count = n;
	}
	
	@Override
	public void valueChanged(IntegerStorageChange ischange) {
		if(count <= 0) {
			ischange.getIstorage().removeObserver(this);
		} else {
			count--;
			System.out.println("Double value: " + ischange.getNewValue() * 2);
		}
	}

}
