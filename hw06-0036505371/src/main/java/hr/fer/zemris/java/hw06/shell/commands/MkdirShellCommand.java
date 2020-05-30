package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Razred koji modelira naredbu za stvaranje strukture direktorija. Naredba
 * prima jedan argument. Detaljniji opis naredbe može se dohvatiti pomoću metode
 * <code>getCommandDescription</code>.
 * 
 * @author Maja Radočaj
 *
 */
public class MkdirShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	private static final String COMMAND_NAME = "mkdir";
	/**
	 * Opis naredbe.
	 */
	private List<String> commandDescription;

	public MkdirShellCommand() {
		commandDescription = new ArrayList<>();
		commandDescription.add("The mkdir command expects a single argument: directory name.");
		commandDescription.add("It then creates the appropriate directory structure.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> argumentsList;
		try {
			argumentsList = ArgumentsUtil.argumentParser(arguments);
		} catch(IllegalArgumentException ex) {
			env.writeln("Illegal arguments. " + ex.getMessage());
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

		try {
			Files.createDirectories(path);
		} catch(IOException ex) {
			env.writeln("Cannot make directory.");
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
