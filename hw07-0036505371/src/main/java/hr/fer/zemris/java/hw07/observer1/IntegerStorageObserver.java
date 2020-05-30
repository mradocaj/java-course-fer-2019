package hr.fer.zemris.java.hw07.observer1;

/**
 * Sučelje koje definira observera (slušača promjena vrijednosti) za razred {@link IntegerStorage}.
 * @author Maja Radočaj
 *
 */
public interface IntegerStorageObserver {

	/**
	 * Radnja koju treba izvršiti kad se zabilježi promjena vrijednosti u razredu {@link IntegerStorage}.
	 * 
	 * @param istorage razred koji observer prati
	 */
	public void valueChanged(IntegerStorage istorage);

}
