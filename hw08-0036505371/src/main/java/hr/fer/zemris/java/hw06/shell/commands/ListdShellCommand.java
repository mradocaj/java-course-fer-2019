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
 * Razred koji modelira naredbu koja ispisuje sve staze koje su trenutno na stogu.
 * Naredba ne prima argumente. Detaljniji opis naredbe može se dohvatiti pomoću metode 
 * <code>getCommandDescription</code>.
 * 
 * @author Maja Radočaj
 *
 */
public class ListdShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	private static final String COMMAND_NAME = "listd";
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
	
	public ListdShellCommand() {
		commandDescription = new ArrayList<>();
		commandDescription.add("The listd command takes no arguments.");
		commandDescription.add("When called, the command writes all paths currently on stack.");
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
			env.writeln("No saved directories.");
		} else {
			stack.forEach((p) -> env.writeln(p.toString()));
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
