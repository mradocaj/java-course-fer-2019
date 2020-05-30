package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
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
 * Razred koji modelira naredbu za kopiranje sadržaja dokumenta u novi dokument.
 * Naredba prima jedan ili dva argumenta. Detaljniji opis naredbe može se
 * dohvatiti pomoću metode <code>getCommandDescription</code>.
 * 
 * @author Maja Radočaj
 *
 */
public class CopyShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	private static final String COMMAND_NAME = "copy";
	/**
	 * Opis naredbe.
	 */
	private List<String> commandDescription;

	/**
	 * Defaultni konstruktor.
	 */
	public CopyShellCommand() {
		commandDescription = new ArrayList<>();
		commandDescription.add("The copy command expects two arguments: source file name and destination file name.");
		commandDescription.add("If destination file exists, user is asked if it is allowed to overwrite it.");
		commandDescription.add("Copy command is aplicable only for files.");
		commandDescription.add("If the second argument is directory, the file will be copied into that directory "
				+ "with its original name.");
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
		if(argumentsList.size() != 2) {
			env.writeln("Command " + COMMAND_NAME + " must have two arguments.\n"
					+ "For more details about commands, write \"help\".");
			return ShellStatus.CONTINUE;
		}

		Path file, destination;
		try {
			file = env.getCurrentDirectory().resolve(Paths.get(argumentsList.get(0)));
			destination = env.getCurrentDirectory().resolve(Paths.get(argumentsList.get(1)));
		} catch(InvalidPathException ex) {
			env.writeln("String cannot be converted to path.");
			return ShellStatus.CONTINUE;
		}

		if(!Files.isRegularFile(file)) {
			env.writeln("File " + argumentsList.get(0) + " does not exist.");
			return ShellStatus.CONTINUE;
		}
		if(Files.isDirectory(destination)) {
			destination = Paths.get(destination.toString(), file.getFileName().toString());
		} else if(destination.getParent() == null || !Files.isDirectory(destination.getParent())) {	
			env.writeln("File " + destination.getFileName().toString() + " must be in existing directory.");
			return ShellStatus.CONTINUE;
		}

		try {
			if(Files.exists(destination)) {
				while(true) {
					env.write("File " + destination.toString() + " already exists. Overwrite file? (Y/N)\n"
							+ env.getPromptSymbol() + " ");
					String answer = env.readLine();
					if(answer.toLowerCase().equals("y")) {
						writeFile(file, destination);
						env.writeln("File copied.");
						break;
					} else if(answer.toLowerCase().equals("n")) {
						env.writeln("Copying canceled.");
						break;
					}
				}
			} else {
				writeFile(file, destination);
				env.writeln("File copied.");
			}
		} catch(IOException ex) {
			env.writeln("Cannot copy file.");
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
	 * Pomoćna metoda koja kopira sadržaj iz dokumenta <code>file</code> u novi dokument <code>destination</code>.
	 * U slučaju greške, baca se {@link IOException}.
	 * 
	 * @param file dokument čiji sadržaj treba kopirati
	 * @param destination dokument koji nastaje kao rezultat kopiranja
	 * @throws IOException u slučaju greške pri čitanju/pisanju dokumenta
	 */
	private static void writeFile(Path file, Path destination) throws IOException {
		BufferedInputStream is = new BufferedInputStream(Files.newInputStream(file));
		BufferedOutputStream os = new BufferedOutputStream(Files.newOutputStream(destination));

		byte[] buffer = new byte[1024];
		int result = is.read(buffer);
		
		while(result != -1) {
			os.write(buffer, 0, result);
			result = is.read(buffer);
		}
		
		os.close();
		is.close();
	}
}
