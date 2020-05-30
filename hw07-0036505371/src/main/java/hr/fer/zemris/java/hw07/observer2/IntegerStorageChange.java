package hr.fer.zemris.java.hw07.observer2;

import java.util.Objects;

/**
 * Razred koji definira događaj promijene vrijednosti objekta
 * {@link IntegerStorage}. Razred pohranjuje referencu na razred čije
 * vrijednosti pohranjuje, staru vrijednost te novu vrijednost.
 * 
 * @author Maja Radočaj
 *
 */
public class IntegerStorageChange {

	/**
	 * Razred čije promjene se prate.
	 */
	private IntegerStorage istorage;
	/**
	 * Stara vrijednost.
	 */
	private int oldValue;
	/**
	 * Nova vrijednost.
	 */
	private int newValue;

	/**
	 * Javni konstruktor koji inicijalizira vrijednosti.
	 * 
	 * @param istorage razred čije promjene se bilježe
	 * @param oldValue stara vrijednost
	 * @param newValue nova, promijenjena vrijednost
	 * @throws NullPointerException ako je predani <code>istorage</code>
	 *                              <code>null</code>.
	 */
	public IntegerStorageChange(IntegerStorage istorage, int oldValue, int newValue) {
		this.istorage = Objects.requireNonNull(istorage);
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	/**
	 * Getter koji vraća razred čije promjene se prate.
	 * 
	 * @return razred čije promjene se prate
	 */
	public IntegerStorage getIstorage() {
		return istorage;
	}

	/**
	 * Getter koji vraća vrijednost prije promjene.
	 * 
	 * @return stara vrijednost
	 */
	public int getOldValue() {
		return oldValue;
	}

	/**
	 * Getter koji vraća vrijednost poslije promjene.
	 * 
	 * @return nova vrijednost
	 */
	public int getNewValue() {
		return newValue;
	}

}
