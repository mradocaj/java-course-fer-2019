package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Razred koji modelira naredbu za ispisivanje trenutnog znaka za <code>PROMPT</code>, <code>MORELINES</code>
 * ili <code>MULTILINE</code>.
 * Naredba prima jedan ili dva argumenta. Detaljniji opis naredbe može se dohvatiti pomoću metode 
 * <code>getCommandDescription</code>.
 * 
 * @author Maja Radočaj
 *
 */
public class SymbolShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	private static final String COMMAND_NAME = "symbol";
	/**
	 * Prompt.
	 */
	private static final String PROMPT = "PROMPT";
	/**
	 * Morelines.
	 */
	private static final String MORELINES = "MORELINES";
	/**
	 * Multiline.
	 */
	private static final String MULTILINE = "MULTILINE";
	/**
	 * Opis naredbe.
	 */
	private List<String> commandDescription;
	
	/**
	 * Defaultni konstruktor.
	 */
	public SymbolShellCommand() {
		commandDescription = new ArrayList<>();
		commandDescription.add("Symbol command is used to get or set symbols for PROMPT, MORELINES or MULTILINE.");
		commandDescription.add("The first argument is mandatory (PROMPT, MORELINES or MULTILINE).");
		commandDescription.add("If written with one argument, it will print the current symbol that "
				+ "represents the given argument.");
		commandDescription.add("If written with two arguments, the second is expected to be a "
				+ "character that will be a new representation of the first argument.");
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = arguments.trim().split("\\s+");
		if(args.length == 1) {
			if(args[0].equals(PROMPT)) {
				env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'.");
			} else if(args[0].equals(MORELINES)) {
				env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'.");
			} else if(args[0].equals(MULTILINE)) {
				env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'.");
			} else {
				env.writeln("Illegal command argument.");
			}
		} else if (args.length == 2) {
			if(args[1].length() != 1) {
				env.writeln("Second argument expected to be character.");
				return ShellStatus.CONTINUE;
			}
			Character newSymbol = args[1].toCharArray()[0];
			
			if(args[0].equals(PROMPT)) {
				env.writeln("Symbol for PROMPT changed from '" + env.getPromptSymbol() + "' to '" 
						+ newSymbol + "'.");
				env.setPromptSymbol(newSymbol);
			} else if(args[0].equals(MORELINES)) {
				env.writeln("Symbol for MORELINES changed from '" + env.getMorelinesSymbol() + "' to '" 
						+ newSymbol + "'.");
				env.setMorelinesSymbol(newSymbol);
			} else if(args[0].equals(MULTILINE)) {
				env.writeln("Symbol for MULTILINE changed from '" + env.getMultilineSymbol() + "' to '" 
						+ newSymbol + "'.");
				env.setMultilineSymbol(newSymbol);
			} else {
				env.writeln("Illegal command argument.");
			}
		} else {
			env.writeln("Command " + COMMAND_NAME + " must have one or two arguments.\n"
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
