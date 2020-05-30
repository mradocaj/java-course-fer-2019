package hr.fer.zemris.java.custom.collections;

/**
 * Sučeljem Tester modeliramo objekte koji primaju neke objekte te ispitaju je
 * li taj objekt prihvatljiv ili ne.
 * 
 * @author Maja Radočaj
 *
 */
public interface Tester {

	/**
	 * Metoda kojom provjeravamo je li objekt prihvatljiv.
	 * 
	 * @param obj objekt kojeg testiramo
	 * @return true ako je prihvatljiv, false ako nije
	 */
	boolean test(Object obj);
}
