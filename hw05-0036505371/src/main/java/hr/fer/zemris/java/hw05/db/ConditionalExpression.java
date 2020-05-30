package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * Razred koji modelira uvjetni izraz kojim se provjerava neki zapis o studentu.
 * Svaki uvjetni izraz ima polje koje provjerava u zapisu (ime, prezime ili
 * JMBAG), String literal s kojim se to polje treba usporediti te operaciju
 * kojom se uspoređuju.
 * 
 * @author Maja Radočaj
 *
 */
public class ConditionalExpression {

	/**
	 * Primjerak razreda koji implementira sučelje {@link IFieldValueGetter}. Služi
	 * za dohvat određene varijable u zapisu o studentu.
	 */
	private IFieldValueGetter fieldGetter;
	/**
	 * String literal sa kojim se uspoređuje dohvaćena varijabla iz zapisa o
	 * studentu.
	 */
	private String stringLiteral;
	/**
	 * Operacija kojom se uspoređuju dani String i ime, prezime ili JMBAG studenta.
	 */
	private IComparisonOperator comparisonOperator;

	/**
	 * Konstruktor koji inicijalizira uvjetni izraz. Predane vrijednosti ne smiju
	 * biti <code>null</code>.
	 * 
	 * @param fieldGetter        predani primjerak razreda za dohvaćanje informacije
	 *                           o studentu
	 * @param stringLiteral      predani literal
	 * @param comparisonOperator predana operacija
	 * @throws NullPointerException ako je ijedan od predanih parametara
	 *                              <code>null</code>
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		this.fieldGetter = Objects.requireNonNull(fieldGetter);
		this.stringLiteral = Objects.requireNonNull(stringLiteral);
		this.comparisonOperator = Objects.requireNonNull(comparisonOperator);
	}

	/**
	 * Metoda koja vraća primjerak razreda za dohvaćanje informacije o studentu.
	 * 
	 * @return primjerak razreda za dohvaćanje informacije iz zapisa o studentu
	 *         {@link StudentRecord}
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Metoda koja vraća String literal s kojim se treba uspoređivati.
	 * 
	 * @return literal
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Metoda koja vraća operaciju usporedbe.
	 * 
	 * @return operacija usporedbe
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

}
