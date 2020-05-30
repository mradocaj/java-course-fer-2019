package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.IllegalFormatException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.*;

/**
 * Razred koji predstavlja implementaciju sučelja {@link Environment}.
 * Ovaj razred definira okruženje potrebno za rad ljuske {@link MyShell}.
 * 
 * @author Maja Radočaj
 *
 */
public class EnvironmentImpl implements Environment {

	/**
	 * Znak koji označava da se naredba proteže kroz više redaka.
	 */
	private char multilineSymbol = '|';
	/**
	 * Znak koji označava mjesto za korisnikov unos.
	 */
	private char promptSymbol = '>';
	/**
	 * Znak koji označava da se nakon dane linije očekuje još linija.
	 */
	private char morelinesSymbol = '\\';
	/**
	 * Popis svih podržanih naredbi.
	 */
	private SortedMap<String, ShellCommand> commands;
	/**
	 * Scanner koji se koristi za komunikaciju između korisnika i programa.
	 */
	private Scanner sc;
	
	/**
	 * Javni defaultni konstruktor.
	 */
	public EnvironmentImpl() {
		commands = new TreeMap<>();
		commands.put("cat", new CatShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("tree", new TreeShellCommand());
		
		sc = new Scanner(System.in);
	}
	
	
	@Override
	public String readLine() throws ShellIOException {
		try {
			String line = sc.nextLine();
			return line;
		} catch (NoSuchElementException | IllegalStateException ex) {
			ex.printStackTrace();
			sc.close();
			throw new ShellIOException("Cannot read next line.");
		}
	}

	@Override
	public void write(String text) throws ShellIOException {
		try {
			System.out.printf(text);
		} catch (IllegalFormatException | NullPointerException ex) {
			throw new ShellIOException();
		}
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		multilineSymbol = Objects.requireNonNull(symbol);
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		promptSymbol = Objects.requireNonNull(symbol);
	}

	@Override
	public Character getMorelinesSymbol() {
		return morelinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		morelinesSymbol = Objects.requireNonNull(symbol);
	}

}
