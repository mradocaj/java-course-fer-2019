package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
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
 * Razred koji modelira naredbu za ispisivanje sadržaja dokumenta. Naredba prima
 * jedan ili dva argumenta. Detaljniji opis naredbe može se dohvatiti pomoću
 * metode <code>getCommandDescription</code>.
 * 
 * @author Maja Radočaj
 *
 */
public class CatShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	private static final String COMMAND_NAME = "cat";
	/**
	 * Opis naredbe.
	 */
	private List<String> commandDescription;

	/**
	 * Defaultni konstruktor.
	 */
	public CatShellCommand() {
		commandDescription = new ArrayList<>();
		commandDescription.add("This command opens given file and writes its content to console.");
		commandDescription.add("Command cat takes one or two arguments.");
		commandDescription.add("The first argument is path to some file and is mandatory.");
		commandDescription
				.add("The second argument is charset name that should be "
						+ "used to interpret chars from bytes.");
		commandDescription.add("If not provided, a default platform charset will be used.");
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
		if(argumentsList.size() != 1 && argumentsList.size() != 2) {
			env.writeln("Command " + COMMAND_NAME + " must have one or two arguments.\n"
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

		Charset charset;
		try {
			charset = argumentsList.size() == 2 ? Charset.forName(argumentsList.get(1)) 
					: Charset.defaultCharset();
		} catch(IllegalCharsetNameException | UnsupportedCharsetException ex) {
			env.writeln("Given charset cannot be resolved. \nIf path contains whitespaces, "
					+ "it must be written within quotation marks (\"C:\\home\\file.txt\").");
			return ShellStatus.CONTINUE;
		}

		try(BufferedReader br = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(Files.newInputStream(path)), charset))) {
			
			String line = br.readLine();
			while(line != null) {
				env.writeln(line);
				line = br.readLine();
			}
		} catch(IOException e) {
			env.writeln("Could not read file. Check if file exists or is readable.");
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
