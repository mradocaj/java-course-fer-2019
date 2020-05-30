package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * Razred koji modelira jedan zapis o studentu.
 * Svaki zapis sadrži JMBAG, ime, prezime i završnu ocjenu studenta.
 * Dva studenta su jednaka ako imaju jednak JMBAG.
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
	 * Prezime studenta.
	 */
	private String lastName;
	/**
	 * Ime studenta.
	 */
	private String firstName;
	/**
	 * Završna ocjena studenta.
	 */
	private int finalGrade;
	
	/**
	 * Konstruktor koji inicijalizira podatke o studentu.
	 * Predani podaci ne smiju biti <code>null</code> vrijednosti.
	 * 
	 * @param jmbag predani JMBAG
	 * @param lastName predano prezime
	 * @param firstName predano ime
	 * @param finalGrade predana završna ocjena
	 * @throws NullPointerException ako su neki od predanih podataka <code>null</code> vrijednosti
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		this.jmbag = Objects.requireNonNull(jmbag);
		this.lastName = Objects.requireNonNull(lastName);
		this.firstName = Objects.requireNonNull(firstName);
		this.finalGrade = finalGrade;
	}
	
	/**
	 * Metoda koja vraća JMBAG studenta.
	 * 
	 * @return JMBAG studenta
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Metoda koja vraća prezime studenta.
	 * 
	 * @return prezime studenta
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Metoda koja vraća ime studenta.
	 * 
	 * @return ime studenta
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Metoda koja vraća završnu ocjenu studenta.
	 * 
	 * @return završna ocjena studenta
	 */
	public int getFinalGrade() {
		return finalGrade;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
	
	
}
