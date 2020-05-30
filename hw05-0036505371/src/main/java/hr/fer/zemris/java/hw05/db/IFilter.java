package hr.fer.zemris.java.hw05.db;

/**
 * Sučelje koje definira jednu apstraktnu metodu:
 * <code>boolean accepts(StudentRecord record)</code>.
 * 
 * @author Maja Radočaj
 *
 */
public interface IFilter {

	/**
	 * Metoda koja provjerava zadovoljava li student sa zapisom <code>record</code>
	 * neki uvjet. Ako ga zadovoljava, on je prihvaćen.
	 * 
	 * @param record zapis studenta
	 * @return <code>true</code> ako zapis zadovoljava neki uvjet,
	 *         <code>false</code> ako ne
	 */
	public boolean accepts(StudentRecord record);
}
