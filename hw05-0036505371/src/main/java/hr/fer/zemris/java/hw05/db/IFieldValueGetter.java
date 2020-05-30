package hr.fer.zemris.java.hw05.db;

/**
 * Sučelje koje sadrži jednu javnu metodu za dohvat podataka o studentu - metodu <code>get</code>.
 * @author Maja Radočaj
 *
 */
public interface IFieldValueGetter {

	/**
	 * Metoda koja vraća jednu od informacija o studentu iz zapisa o studentu <code>record</code>.
	 * 
	 * @param record zapis o studentu
	 * @return traženu informaciju iz zapisa
	 */
	public String get(StudentRecord record);
}
