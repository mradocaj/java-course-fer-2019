package hr.fer.zemris.java.hw07.observer2;

/**
 * Konkretni observer koji ispisuje broj promjena vrijednosti otkad je observer dodan.
 * 
 * @author Maja Radočaj
 *
 */
public class ChangeCounter implements IntegerStorageObserver {

	/**
	 * Broj promjena vrijednosti otkad je observer dodan.
	 */
	int count;
	
	@Override
	public void valueChanged(IntegerStorageChange ischange) {
		count++;
		System.out.println("Number of value changes since tracking: " + count);
	}

}
