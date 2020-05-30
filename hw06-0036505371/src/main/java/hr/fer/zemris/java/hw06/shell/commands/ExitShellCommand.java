package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Razred koji modelira naredbu za izlazak iz shell-a. Naredba ne prima niti
 * jedan argument. Detaljniji opis naredbe može se dohvatiti pomoću metode
 * <code>getCommandDescription</code>.
 * 
 * @author Maja Radočaj
 *
 */
public class ExitShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	private static final String COMMAND_NAME = "exit";
	/**
	 * Opis naredbe.
	 */
	private List<String> commandDescription;

	/**
	 * Defaultni konstruktor.
	 */
	public ExitShellCommand() {
		commandDescription = new ArrayList<>();
		commandDescription.add("Exit command terminates the MyShell shell.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.trim().length() != 0) {
			env.writeln("Command " + COMMAND_NAME + " must have no arguments.\n"
					+ "For more details about commands, write \"help\".");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.TERMINATE;
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
