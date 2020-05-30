package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Program koji čita tekstualnu datoteku sa podacima o studentima te ispisuje
 * različite informacije o studentima i njihovim ispitima i ocjenama.
 * 
 * @author Maja Radočaj
 *
 */
public class StudentDemo {

	/**
	 * Glavni program.
	 * 
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		List<String> lines = new ArrayList<>();
		try {
			lines = Files.readAllLines(Paths.get("./studenti.txt"), StandardCharsets.UTF_8);
		} catch(IOException ex) {
			System.out.println("Cannot read from file. Check if file is in appropriate directory.");
			System.exit(-1);
		}
		List<StudentRecord> records = convert(lines);

		printResult(records);
	}

	/**
	 * Pomoćna metoda za ispis rezultata zadataka.
	 * 
	 * @param records lista zapisa o studentima
	 */
	private static void printResult(List<StudentRecord> records) {
		StringBuilder sb = new StringBuilder();

		sb.append("Zadatak 1\n=========\n");
		sb.append(vratiBodovaViseOd25(records) + "\n");

		sb.append("Zadatak 2\n=========\n");
		sb.append(vratiBrojOdlikasa(records) + "\n");

		sb.append("Zadatak 3\n=========\n");
		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
		odlikasi.forEach(s -> sb.append(s.toString() + "\n"));

		sb.append("Zadatak 4\n=========\n");
		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
		odlikasiSortirano.forEach(s -> sb.append(s.toString() + "\n"));

		sb.append("Zadatak 5\n=========\n");
		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
		nepolozeniJMBAGovi.forEach(s -> sb.append(s + "\n"));

		sb.append("Zadatak 6\n=========\n");
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
		for(Map.Entry<Integer, List<StudentRecord>> entry : mapaPoOcjenama.entrySet()) {
			sb.append("Studenti sa ocjenom " + entry.getKey() + ":\n");
			entry.getValue().forEach(s -> sb.append("\t" + s.toString() + "\n"));
		}

		sb.append("Zadatak 7\n=========\n");
		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
		mapaPoOcjenama2.forEach((k, v) -> sb.append("Broj studenata sa ocjenom " + k + ":\n" + v + "\n"));

		sb.append("Zadatak 8\n=========\n");
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
		for(Map.Entry<Boolean, List<StudentRecord>> passEntry : prolazNeprolaz.entrySet()) {
			sb.append(passEntry.getKey() ? "Prolaz:\n" : "Pad:\n");
			passEntry.getValue().forEach(s -> sb.append("\t" + s.toString() + "\n"));
		}
		System.out.println(sb.toString());
	}

	/**
	 * Metoda koja iz liste Stringova vraća novu listu zapisa o studentima. Ako
	 * zapisi o studentima u tekstualnom obliku nisu ispravno formatirani, baca se
	 * {@link IllegalArgumentException}.
	 * 
	 * @param lines lista linija koju treba formatirati
	 * @return lista zapisa o studentima koje smo dobili iz liste linija
	 * @throws IllegalArgumentException ako lista linija nije ispravno formatirana
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> result = new ArrayList<>();

		for(String line : lines) {
			if(line.trim().equals(""))
				break;
			String[] parts = line.split("\t");
			if(parts.length != 7) {
				throw new IllegalArgumentException("Invalid line. Line must have 7 fields.");
			}
			try {
				StudentRecord record = new StudentRecord(parts[0], parts[1], parts[2],
						Double.parseDouble(parts[3]),
						Double.parseDouble(parts[4]), 
						Double.parseDouble(parts[5]), 
						Integer.parseInt(parts[6]));
				result.add(record);
			} catch(NumberFormatException ex) {
				throw new IllegalArgumentException("Invalid field. Could not parse " 
			+ "grade or exam results.");
			}
		}
		return result;
	}

	/**
	 * Metoda koja vraća broj studenata čija suma bodova sa međuispita, završnog
	 * ispita i labosa je veća o 25.
	 * 
	 * @param records lista zapisa o studentima
	 * @return broj studenata sa ukupnim brojem bodova većim od 25
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream().filter(s -> 
			s.getLabScore() + s.getMidtermScore() + s.getFinalScore() > 25).count();
	}

	/**
	 * Metoda koja vraća broj studenata čija završna ocjena je 5.
	 * 
	 * @param records lista zapisa o studentima
	 * @return broj studenata sa završnom ocjenom 5
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getFinalGrade() == 5).count();
	}

	/**
	 * Metoda koja vraća listu studenata čija završna ocjena je 5.
	 * 
	 * @param records lista zapisa o studentima
	 * @return lista studenata sa završnom ocjenom 5
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getFinalGrade() == 5).collect(Collectors.toList());
	}

	/**
	 * Metoda koja vraća listu studenata čija završna ocjena je 5. Lista je
	 * sortirana silazno prema ukupnom broju bodova.
	 * 
	 * @param records lista zapisa o studentima
	 * @return sortirana lista studenata sa završnom ocjenom 5
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getFinalGrade() == 5)
				.sorted((s1, s2) -> 
					Double.compare(s2.getFinalScore() + s2.getMidtermScore() + s2.getLabScore(),
						s1.getFinalScore() + s1.getMidtermScore() + s1.getLabScore()))
				.collect(Collectors.toList());
	}

	/**
	 * Metoda koja vraća listu JMBAG-ova studenata čija završna ocjena je 1. Lista
	 * je sortirana prema JMBAG-ovima od najmanjeg prema većem.
	 * 
	 * @param records lista zapisa o studentima
	 * @return lista JMBAG-ova studenata sa završnom ocjenom 1
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getFinalGrade() == 1).map(s -> s.getJmbag()).sorted()
				.collect(Collectors.toList());
	}

	/**
	 * Metoda koja vraća mapu studenata gdje su ključevi ocjene, a vrijednosti liste
	 * studenata sa danim ocjenama.
	 * 
	 * @param records lista zapisa o studentima
	 * @return mapa studenata razvrstanih prema ocjenama
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(
			List<StudentRecord> records) {
		
		return records.stream().collect(Collectors.groupingBy(StudentRecord::getFinalGrade));
	}

	/**
	 * Metoda koja vraća mapu studenata gdje su ključevi ocjene, a vrijednosti broj
	 * studenata sa danom ocjenom.
	 * 
	 * @param records lista zapisa o studentima
	 * @return broj studenata razvrstanih prema ocjenama
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.toMap(StudentRecord::getFinalGrade, 
				s2 -> 1, (s, v) -> s + 1));
	}

	/**
	 * Metoda koja vraća mapu studenata sortiranu prema prolazu ili padu.
	 * 
	 * @param records lista zapisa o studentima
	 * @return mapa studenata sortiranih prema prolazu ili padu
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream().collect(Collectors.partitioningBy(s -> s.getFinalGrade() != 1));
	}
}
