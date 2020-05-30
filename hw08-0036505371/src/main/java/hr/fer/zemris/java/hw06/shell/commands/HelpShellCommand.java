package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Razred koji modelira naredbu za pomoć oko rada sa {@link MyShell}.
 * Naredba prima ili nula ili jedan argument. Detaljniji opis naredbe može se dohvatiti pomoću metode 
 * <code>getCommandDescription</code>.
 * 
 * @author Maja Radočaj
 *
 */
public class HelpShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	private static final String COMMAND_NAME = "help";
	/**
	 * Opis naredbe.
	 */
	private List<String> commandDescription;
	
	/**
	 * Defaultni konstruktor.
	 */
	public HelpShellCommand() {
		commandDescription = new ArrayList<>();
		commandDescription.add("Help command is used to get descriptions of shell commands.");
		commandDescription.add("If started with no arguments, names of all supported commands will be listed.");
		commandDescription.add("If started with single argument, name and the description of "
				+ "selected command will be printed (if such command exists).");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] argumentsSplit = arguments.trim().split("\\s+");
		
		if(arguments.trim().equals("")) {
			env.commands().forEach((s,c) -> env.writeln(s));
		} else if (argumentsSplit.length == 1) {
			SortedMap<String, ShellCommand> commands = env.commands();
			List<String> description = null;
			
			for(Map.Entry<String, ShellCommand> command : commands.entrySet()) {
				if(command.getKey().equals(argumentsSplit[0])) {
					description = command.getValue().getCommandDescription();
					break;
				}
			}
			if(description != null) {
				description.forEach(s -> env.writeln(s));
			} else {
				env.writeln("Command " + argumentsSplit[0] + " doesn't exist.");
			}
		} else {
			env.writeln("Command " + COMMAND_NAME + " must have zero or one arguments.\n"
					+ "For more details about commands, write \"help\".");
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
