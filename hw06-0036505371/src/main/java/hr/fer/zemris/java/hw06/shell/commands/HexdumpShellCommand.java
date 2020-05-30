package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.crypto.Util;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Razred koji modelira naredbu za izlazak ispisivanje sadržaja datoteke u
 * heksadekadskom obliku. Naredba prima jedan argument. Detaljniji opis naredbe
 * može se dohvatiti pomoću metode <code>getCommandDescription</code>.
 * 
 * @author Maja Radočaj
 *
 */
public class HexdumpShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	private static final String COMMAND_NAME = "hexdump";
	/**
	 * Opis naredbe.
	 */
	private List<String> commandDescription;

	/**
	 * Defaultni konstruktor.
	 */
	public HexdumpShellCommand() {
		commandDescription = new ArrayList<>();
		commandDescription.add("Hexdump command expects single argument: file name.");
		commandDescription.add("It produces hex-output which represents the content of the given file in hex numbers.");
		commandDescription.add("On the right side, only a standard subset of characters is shown "
				+ "(bytes whose values are between 32 and 127).");
		commandDescription.add("All other characters are replaced with '.'.");

	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> argumentsList;
		try {
			argumentsList = ArgumentsUtil.argumentParser(arguments);
		} catch(IllegalArgumentException ex) {
			env.writeln("Invalid argument. " + ex.getMessage());
			return ShellStatus.CONTINUE;
		}
		if(argumentsList.size() != 1) {
			env.writeln("Command " + COMMAND_NAME + " must have one argument.\n"
					+ "For more details about commands, write \"help\".");
			return ShellStatus.CONTINUE;
		}

		Path path;
		try {
			path = Paths.get(argumentsList.get(0));
		} catch(InvalidPathException ex) {
			env.writeln("String cannot be converted to path.");
			return ShellStatus.CONTINUE;
		}

		try(InputStream is = Files.newInputStream(path)) {
			byte[] buffer = new byte[16];
			for(int i = 0; i > -1; i = i + 16) {
				int r = is.read(buffer);
				if(r < 1)
					break;
				env.writeln(getLine(i, r, buffer));
			}
		} catch(IOException ex) {
			env.writeln("Cannot read file. Check if file exists or is readable.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(commandDescription);
	}

	/**
	 * Pomoćna metoda koja na temelju trenutnog indeksa, broja pročitanih podataka i
	 * pročitanih podataka generira novu liniju ispisa.
	 * 
	 * @param i      indeks linije
	 * @param r      broj pročitanih podataka
	 * @param buffer pročitani podaci
	 * @return nova linija ispisa
	 */
	private static String getLine(int i, int r, byte[] buffer) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%08X: ", i));
		char[] toHex = Util.bytetohex(Arrays.copyOf(buffer, r)).toUpperCase().toCharArray();
		for(int j = 0; j < buffer.length * 2; j = j + 2) {
			if(j < toHex.length) {
				sb.append(String.format("%c%c", toHex[j], toHex[j + 1]));
			} else {
				sb.append("  ");
			}
			if(j != 14) {
				sb.append(" ");
			} else {
				sb.append("|");
			}
		}
		sb.append(" | ");
		for(int j = 0; j < r; j = j + 1) {
			if(buffer[j] >= 32 && buffer[j] <= 127) {
				sb.append((char) buffer[j]);
			} else {
				sb.append(".");
			}
		}
		return sb.toString();
	}
}
