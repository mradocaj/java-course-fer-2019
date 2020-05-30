package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Razred koji modelira naredbu koja skida vršni direktorij sa stoga i odbacuje ga.
 * Naredba ne prima argumente. Detaljniji opis naredbe može se dohvatiti pomoću metode 
 * <code>getCommandDescription</code>.
 * 
 * @author Maja Radočaj
 *
 */
public class DropdShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	private static final String COMMAND_NAME = "dropd";
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
	
	public DropdShellCommand() {
		commandDescription = new ArrayList<>();
		commandDescription.add("The dropd command takes no arguments.");
		commandDescription.add("Similarly to popd command, it pops one directory from stack.");
		commandDescription.add("It does not set the popped directory as current directory.");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.trim().length() != 0) {
			env.writeln("Command " + COMMAND_NAME + " must have no arguments.\n"
					+ "For more details about commands, write \"help\".");
			return ShellStatus.CONTINUE;
		}

		Stack<Path> stack = (Stack<Path>) env.getSharedData(STACKNAME);
		if(env.getSharedData(STACKNAME) == null || stack.size() == 0) {
			env.writeln("Cannot pop from stack - stack is empty.");
		} else {
			stack.pop();
			env.writeln("Directory dropped.");
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
