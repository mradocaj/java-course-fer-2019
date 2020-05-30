package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * Sučelje koje definira okoliš i karakteristike određenog shell-a.
 * Nudi metode koje omogućuju korisnikov rad sa shell-om.
 * 
 * @author Maja Radočaj
 *
 */
public interface Environment {

	/**
	 * Metoda koja od korisnika očekuje unos linije te vraća korisnikov unos.
	 * U slučaju greške pri unosu, baca se iznimka {@link ShellIOException}.
	 * 
	 * @return korisnikov unos
	 * @throws ShellIOException ako dođe do greške pri unosu ili čitanju unosa
	 */
	String readLine() throws ShellIOException;  
	
	/**
	 * Metoda koja omogućuje da korisnik na neki izlaz ispiše predani String.
	 * U slučaju greške, baca se iznimka {@link ShellIOException}.
	 * 
	 * @param text tekst koji korisnik želi ispisati
	 * @throws ShellIOException u slučaju greške pri ispisu teksta
	 */
	void write(String text) throws ShellIOException;  
	
	/**
	 * Metoda koja omogućuje da korisnik na neki izlaz ispiše predani String.
	 * Na kraju se ispisuje i znak za novi redak.
	 * U slučaju greške, baca se iznimka {@link ShellIOException}.
	 * 
	 * @param text tekst koji korisnik želi ispisati
	 * @throws ShellIOException u slučaju greške pri ispisu teksta
	 */
	void writeln(String text) throws ShellIOException;  
	
	/**
	 * Metoda koja vraća popis svih naredbi koje su podržane u određenom okruženju.
	 * 
	 * @return nepromjenjiva mapa svih naredbi
	 */
	SortedMap<String, ShellCommand> commands();  
	
	/**
	 * Metoda koja vraća znak koji označava da se naredba proteže kroz više redaka.
	 * 
	 * @return znak koji označava protezanje kroz više redaka
	 */
	Character getMultilineSymbol();  
	
	/**
	 * Metoda koja postavlja novi znak koji označava da se naredba proteže kroz više redaka.
	 * 
	 */
	void setMultilineSymbol(Character symbol);  
	
	/**
	 * Metoda koja vraća znak koji označava mjesto za korisnikov unos.
	 * 
	 * @return znak koji označava mjesto za korisnikov unos
	 */
	Character getPromptSymbol(); 
	
	/**
	 * Metoda koja postavlja znak koji označava mjesto za korisnikov unos.
	 * 
	 */
	void setPromptSymbol(Character symbol);  
	
	/**
	 * Metoda koja vraća znak koji označava da se nakon dane linije očekuje još linija.
	 * 
	 * @return znak koji označava nastavak linije
	 */
	Character getMorelinesSymbol(); 
	
	/**
	 * Metoda koja postavlja znak koji označava da se nakon dane linije očekuje još linija.
	 * 
	 */
	void setMorelinesSymbol(Character symbol);
}
