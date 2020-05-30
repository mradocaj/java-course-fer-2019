package hr.fer.zemris.java.hw07.demo4;

import java.util.Objects;

/**
 * Razred koji modelira jedan zapis o studentu. Svaki student ima:
 * <li>JMBAG</li>
 * <li>ime</li>
 * <li>prezime</li>
 * <li>broj bodova na međuispitu</li>
 * <li>broj bodova na završnom ispitu</li>
 * <li>broj bodova na laboratorijskim vježbama</li>
 * <li>završnu ocjenu</li>
 * 
 * @author Maja Radočaj
 *
 */
public class StudentRecord {

	/**
	 * JMBAG studenta.
	 */
	private String jmbag;
	/**
	 * Ime studenta.
	 */
	private String firstName;
	/**
	 * Prezime studenta.
	 */
	private String lastName;
	/**
	 * Broj bodova na međuispitu.
	 */
	private double midtermScore;
	/**
	 * Broj bodova na završnom ispitu.
	 */
	private double finalScore;
	/**
	 * Broj bodova na laboratorijskim vježbama.
	 */
	private double labScore;
	/**
	 * Završna ocjena.
	 */
	private int finalGrade;

	/**
	 * Konstruktor koji inicijalizira zapis o studentu.
	 * 
	 * @param jmbag        JMBAG
	 * @param firstName    ime
	 * @param lastName     prezime
	 * @param midtermScore bodovi na međuispitu
	 * @param finalScore   bodovi na završnom ispitu
	 * @param labScore     bodovi na laboratorijskim vježbama
	 * @param finalGrade   završna ocjena
	 * @throws NullPointerException ako je predano ime, prezime ili JMBAG
	 *                              <code>null</code>
	 */
	public StudentRecord(String jmbag, String firstName, String lastName, double midtermScore,
			double finalScore, double labScore, int finalGrade) {
		
		this.jmbag = Objects.requireNonNull(jmbag);
		this.firstName = Objects.requireNonNull(firstName);
		this.lastName = Objects.requireNonNull(lastName);
		this.midtermScore = midtermScore;
		this.finalScore = finalScore;
		this.labScore = labScore;
		this.finalGrade = finalGrade;
	}

	@Override
	public String toString() {
		return String.format("%s\t%s\t%s\t%.2f\t%.2f\t%.2f\t%d", jmbag, firstName, 
				lastName, midtermScore, finalScore,
				labScore, finalGrade);
	}

	/**
	 * Getter koji vraća JMBAG.
	 * 
	 * @return JMBAG studenta
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Getter koji vraća ime studenta.
	 * 
	 * @return ime studenta
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Getter koji vraća prezime studenta.
	 * 
	 * @return prezime studenta
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter koji vraća broj bodova na međuispitu.
	 * 
	 * @return broj bodova na međuispitu
	 */
	public double getMidtermScore() {
		return midtermScore;
	}

	/**
	 * Getter koji vraća broj bodova na završnom ispitu.
	 * 
	 * @return broj bodova na završnom ispitu
	 */
	public double getFinalScore() {
		return finalScore;
	}

	/**
	 * Getter koji vraća broj bodova na laboratorijskimm vježbama.
	 * 
	 * @return broj bodova na laboratorijskim vježbama
	 */
	public double getLabScore() {
		return labScore;
	}

	/**
	 * Getter koji vraća završnu ocjenu.
	 * 
	 * @return završna ocjena
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

}
