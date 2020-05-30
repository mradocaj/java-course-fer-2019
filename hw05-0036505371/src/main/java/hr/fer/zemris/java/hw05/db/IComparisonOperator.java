package hr.fer.zemris.java.hw05.db;

/**
 * Sučelje sa jednom apstraktnom metodom : <code>satisfied</code>.
 * 
 * @author Maja Radočaj
 *
 */
public interface IComparisonOperator {

	/**
	 * Metoda koja uspoređuje dvije String vrijednosti.
	 * Vraća informaciju zadovoljavaju li dvije predane vrijednosti neki definirani uvjet.
	 * 
	 * @param value1 prva vrijednost
	 * @param value2 druga vrijednost
	 * @return <code>true</code> ako je definirani uvjet zadovoljen, <code>false</code> ako nije
	 */
	public boolean satisfied(String value1, String value2);
}
