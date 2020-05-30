package hr.fer.zemris.java.hw05.db;

import java.util.*;

/**
 * Razred koji modelira jednostavnu bazu podataka studenata. Baza sadrži zapise
 * o studentima te omogućava filtriranje studenata prema nekim kriterijima.
 * 
 * @author Maja Radočaj
 *
 */
public class StudentDatabase {

	/**
	 * Lista zapisa o studentima.
	 */
	List<StudentRecord> records;
	/**
	 * Mapa za brzo vraćanje studenata na temelju JMBAG-a.
	 */
	Map<String, StudentRecord> index;

	/**
	 * Konstruktor koji stvara novu bazu podataka na temelju liste Stringova.
	 * Provjerava se zadovoljava li svaki String liste uvjete potrebne za stvaranje
	 * novog zapisa o studentima. Uvjeti su da ne smije biti duplih JMBAG-ova te
	 * ocjene moraju biti između 1 i 5. Ako ih ne zadovoljava, baca se
	 * {@link IllegalArgumentException}. Predana lista ne smije biti
	 * <code>null</code>.
	 * 
	 * @param data lista Stringova na temelju koje se gradi baza podataka
	 * @throws IllegalArgumentException ako lista Stringova ne zadovoljava uvjete
	 * @throws NullPointerException     ako je dana lista <code>null</code>
	 */
	public StudentDatabase(List<String> data) {
		Objects.requireNonNull(data);
		records = new ArrayList<>();
		index = new HashMap<>();

		try {
			for(String line : data) {
				String[] parts = line.split("\t");
				if(parts.length != 4 || index.containsKey(parts[0]) || Integer.parseInt(parts[3]) > 5
						|| Integer.parseInt(parts[3]) < 1) {
					throw new IllegalArgumentException();
				}
				StudentRecord newRecord = new StudentRecord(parts[0], parts[1], parts[2], 
						Integer.parseInt(parts[3]));
				records.add(newRecord);
				index.put(parts[0], newRecord);
			}
		} catch(NumberFormatException ex) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Metoda za brzo dohvaćanje zapisa o studentu na temelju JMBAG-a. Predani JMBAG
	 * ne smije biti <code>null</code>.
	 * 
	 * @param jmbag JMBAG studenta čiji zapis treba dohvatiti
	 * @return zapis studenta sa danim JMBAG-om, a <code>null</code> ako takav zapis
	 *         ne postoji
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return index.get(Objects.requireNonNull(jmbag));
	}

	/**
	 * Metoda koja prolazi kroz sve zapise studenata u bazi te provjerava
	 * zadovoljava li ona uvjet definiran u objektu <code>filter</code>. Ako metoda
	 * <code>accepts</code> vraća <code>true</code> za neki zapis, on se dodaje u
	 * privremenu listu studenata. Ta se lista na kraju vraća kao rezultat. Predani
	 * filter ne smije biti <code>null</code>.
	 * 
	 * @param filter primjerak razreda koji implementira metodu <code>accepts</code>
	 * @return listu studenata koja zadovoljava metodu <code>accepts</code>
	 * @throws NullPointerException ako je filter <code>null</code>
	 */
	public List<StudentRecord> filter(IFilter filter) {
		Objects.requireNonNull(filter);
		List<StudentRecord> result = new ArrayList<>();

		for(StudentRecord student : records) {
			if(filter.accepts(student)) {
				result.add(student);
			}
		}
		return result;
	}
}
