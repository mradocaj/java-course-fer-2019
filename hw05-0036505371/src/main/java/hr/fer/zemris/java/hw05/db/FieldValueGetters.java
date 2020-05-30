package hr.fer.zemris.java.hw05.db;

/**
 * Razred koji sarži nekoliko implementacija sučelja {@link IFieldValueGetter} u
 * obliku javnih statičkih varijabli.
 * 
 * @author Maja Radočaj
 *
 */
public class FieldValueGetters {

	/**
	 * <code>IFIeldValueGetter</code> koji prima zapis o studentu, a vraća njegovo
	 * ime.
	 */
	public static final IFieldValueGetter FIRST_NAME = s -> s.getFirstName();
	/**
	 * <code>IFIeldValueGetter</code> koji prima zapis o studentu, a vraća njegovo
	 * prezime.
	 */
	public static final IFieldValueGetter LAST_NAME = s -> s.getLastName();
	/**
	 * <code>IFIeldValueGetter</code> koji prima zapis o studentu, a vraća njegov
	 * JMBAG.
	 */
	public static final IFieldValueGetter JMBAG = s -> s.getJmbag();

}
