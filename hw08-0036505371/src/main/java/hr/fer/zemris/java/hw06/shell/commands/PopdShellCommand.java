package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Razred koji modelira naredbu koja skida vršnu stazu sa stoga i nju postavlja kao radni direktorij.
 * Naredba ne prima argumente. Detaljniji opis naredbe može se dohvatiti pomoću metode 
 * <code>getCommandDescription</code>.
 * 
 * @author Maja Radočaj
 *
 */
public class PopdShellCommand implements ShellCommand {

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
	
	public PopdShellCommand() {
		commandDescription = new ArrayList<>();
		commandDescription.add("The popd command takes no arguments.");
		commandDescription.add("When called, the command pops one directory from stack and "
				+ "sets it as current directory");
		commandDescription.add("Stack musn't be empty.");
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
			Path path = stack.pop();
			env.writeln("Directory popped off stack.");
			if(Files.exists(path)) {
				env.setCurrentDirectory(path);
			}
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
