package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Razred koji modelira naredbu koja trenutni direktorij gura na stog i za trenutni postavlja stazu zadanu argumentom.
 * Naredba prima jedan argument - novi radni direktorij. Detaljniji opis naredbe može se dohvatiti pomoću metode 
 * <code>getCommandDescription</code>.
 * 
 * @author Maja Radočaj
 *
 */
public class PushdShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	private static final String COMMAND_NAME = "pushd";
	/**
	 * Opis naredbe.
	 */
	private List<String> commandDescription;
	/**
	 * Pomoćno ime.
	 */
	private static final String STACKNAME = "cdstack";

	/**
	 * Defaultni konstruktor.
	 */
	
	public PushdShellCommand() {
		commandDescription = new ArrayList<>();
		commandDescription.add("The pushd command takes one argument - path to directory.");
		commandDescription.add("When called, the command pushes the current directory to the stack and sets the "
				+ "path argument as current directory.");
	}
	
	@SuppressWarnings("unchecked")
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
		
		try{
			Path path = env.getCurrentDirectory().resolve(Paths.get(argumentsList.get(0)));
			if(!Files.isDirectory(path)) {
				env.writeln("Invalid path given. Path must be directory.");
				return ShellStatus.CONTINUE;
			}
			if(env.getSharedData(STACKNAME) == null) {
				env.setSharedData(STACKNAME, new Stack<Path>());
			}
			Stack<Path> stack = (Stack<Path>) env.getSharedData(STACKNAME);
			stack.push(env.getCurrentDirectory());
			env.setCurrentDirectory(path);
			env.writeln("Directory pushed to stack.");
		} catch(InvalidPathException ex) {
			env.writeln("Invalid path given.");
		} catch(IllegalArgumentException ex) {
			env.writeln(ex.getMessage());
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
