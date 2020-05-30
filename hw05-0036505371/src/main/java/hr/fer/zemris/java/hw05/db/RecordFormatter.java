package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Razred koji služi za formatiranje ispisa tablice baze podataka.
 * 
 * @author Maja Radočaj
 *
 */
public class RecordFormatter {

	/**
	 * Metoda za formatiranje ispisa.
	 * Prima listu zapisa o studentima, a vraća formatirani ispis zapisa u obliku liste Stringova.
	 * Primljena lista ne smije biti <code>null</code>.
	 * 
	 * @param recordList lista zapisa o studentima
	 * @return formatirani ispis zapisa o studentima
	 * @throws NullPointerException ako je predana lista <code>null</code>
	 */
	public static List<String> format(List<StudentRecord> recordList) {
		Objects.requireNonNull(recordList);
		List<String> output = new ArrayList<>();
		int maxFirstNameLength = 0;
		int maxLastNameLength = 0;
		
		for(StudentRecord record : recordList) {
			if(record.getFirstName().length() > maxFirstNameLength) {
				maxFirstNameLength = record.getFirstName().length();
			}
			if(record.getLastName().length() > maxLastNameLength) {
				maxLastNameLength = record.getLastName().length();
			}
		}
		
		if(recordList.size() != 0) {
			String heading = heading(maxLastNameLength, maxFirstNameLength);
			output.add(heading);
			
			for(StudentRecord record : recordList) {
				StringBuilder sb = new StringBuilder();
				sb.append("| " + record.getJmbag() + " ");
				sb.append("| " + record.getLastName());
				for(int i = 0; i <= maxLastNameLength - record.getLastName().length(); i++) {
					sb.append(" ");
				}
				sb.append("| " + record.getFirstName());
				for(int i = 0; i <= maxFirstNameLength - record.getFirstName().length(); i++) {
					sb.append(" ");
				}
				sb.append("| " + record.getFinalGrade() + " |");
				output.add(sb.toString());
			}
			output.add(heading);
		}
		
		output.add("Records selected: " + recordList.size());
		return output;
	}

	/**
	 * Pomoćna metoda koja stvara okvir tablice u ispisu.
	 * 
	 * @param maxLastNameLength širina drugog stupca
	 * @param maxFirstNameLength širina trećeg stupca
	 * @return okvir tablice
	 */
	private static String heading(int maxLastNameLength, int maxFirstNameLength) {
		StringBuilder sb = new StringBuilder();
		sb.append("+============+=");
		for(int i = 0; i < maxLastNameLength; i++) {
			sb.append("=");
		}
		sb.append("=+=");
		for(int i = 0; i < maxFirstNameLength; i++) {
			sb.append("=");
		}
		sb.append("=+===+");
		
		return sb.toString();
	}
}
