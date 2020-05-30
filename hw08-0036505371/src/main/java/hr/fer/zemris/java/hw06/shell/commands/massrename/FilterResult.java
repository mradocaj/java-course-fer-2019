package hr.fer.zemris.java.hw06.shell.commands.massrename;

import java.nio.file.Path;
import java.util.Objects;
import java.util.regex.Matcher;

/**
 * Razred koji predstavlja rezultat filtriranja datoteka pri radu u raredu {@link MassrenameShellCommand}.
 * Primjerci ovog razreda predstavljaju po jednu odabranu datoteku zajedno s informacijama o broju pronađenih grupa 
 * te metodom za dohvat grupe.
 * 
 * @author Maja Radočaj
 *
 */
public class FilterResult {

	/**
	 * Staza do datoteke.
	 */
	private Path file;
	/**
	 * Matcher.
	 */
	private Matcher matcher;
	
	/**
	 * Javni konstruktor.
	 * 
	 * @param file staza do datoteke
	 * @param matcher matcher
	 * @throws NullPointerException ako je dana staza ili matcher <code>null</code>
	 */
	public FilterResult(Path file, Matcher matcher) {
		this.file = Objects.requireNonNull(file);
		this.matcher = Objects.requireNonNull(matcher);
	}
	
	/**
	 * Vraća ime datoteke (bez staze).
	 */
	public String toString() { 
		return file.getFileName().toString();
	}
	
	/**
	 * Metoda koja vraća broj pronađenih grupa.
	 * 
	 * @return broj pronađenih grupa
	 */
	public int numberOfGroups() { 
		return matcher.groupCount();
	}
	
	/**
	 * Metoda za dohvat tražene grupe.
	 * 
	 * @param index indeks grupe
	 * @return tražena grupa
	 * @throws IllegalArgumentException ako indeks nije u rasponu <code>[0, numberOfGroups]</code>
	 */
	public String group(int index) { 
		if(index < 0 || index > numberOfGroups()) {
			throw new IllegalArgumentException("Group index isn't in range.");
		}
		return matcher.group(index);
	}
}
