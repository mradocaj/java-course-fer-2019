package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Razred koji modelira naredbu za ispisivanje dostupnih kodnih stranica.
 * Naredba ne prima niti jedan argument. Detaljniji opis naredbe može se
 * dohvatiti pomoću metode <code>getCommandDescription</code>.
 * 
 * @author Maja Radočaj
 *
 */
public class CharsetsShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	private static final String COMMAND_NAME = "charsets";
	/**
	 * Opis naredbe.
	 */
	private List<String> commandDescription;

	/**
	 * Defaultni konstruktor.
	 */
	public CharsetsShellCommand() {
		commandDescription = new ArrayList<>();
		commandDescription.add(
				"Command charsets takes no arguments and lists names of supported charsets "
				+ "for current Java platform.");
		commandDescription.add("A single charset name is written per line.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.trim().length() != 0) {
			env.writeln("Command " + COMMAND_NAME + " has no arguments.\n"
					+ "For more details about commands, write \"help\".");
		} else {
			SortedMap<String, Charset> charSets = Charset.availableCharsets();
			charSets.forEach((s, c) -> env.writeln(c.displayName()));
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

}
