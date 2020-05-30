package hr.fer.zemris.java.hw07.observer1;

/**
 * Konkretni observer koji ispisuje kvadrat nove vrijednosti.
 * 
 * @author Maja Radočaj
 *
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorage istorage) {
		int value = istorage.getValue();
		System.out.println("Provided new value: " + value + ", square is " + value * value);
	}

}
