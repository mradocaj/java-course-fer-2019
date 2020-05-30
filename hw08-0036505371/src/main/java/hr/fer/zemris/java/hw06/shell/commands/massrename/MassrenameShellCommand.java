package hr.fer.zemris.java.hw06.shell.commands.massrename;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.commands.ArgumentsUtil;

/**
 * Razred koji modelira naredbu za premještanje i preimenovanje datoteka.
 * Naredba prima nekoliko argumenata i ima nekoliko podnaredbi. 
 * Detaljniji opis naredbe može se dohvatiti pomoću metode <code>getCommandDescription</code>.
 * 
 * @author Maja Radočaj
 *
 */
public class MassrenameShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	private static final String COMMAND_NAME = "massrename";
	/**
	 * Opis naredbe.
	 */
	private List<String> commandDescription;

	/**
	 * Defaultni konstruktor.
	 */

	public MassrenameShellCommand() {
		commandDescription = new ArrayList<>();
		commandDescription.add("The massrename command expects five arguments: DIR1, DIR2, CMD, MASK and OTHER.");
		commandDescription.add("The command is used to rename or move files from DIR1 to DIR2.");
		commandDescription.add("MASK is a regular expression used to determine which "
				+ "files will be moved from DIR1 to DIR2.");
		commandDescription.add("CMD determines the exact action that will be performed.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			List<String> argumentsList = ArgumentsUtil.argumentParser(arguments);
			if (argumentsList.size() != 4 && argumentsList.size() != 5) {
				env.writeln("Command " + COMMAND_NAME + " must have four or five arguments.\n"
						+ "For more details about commands, write \"help\".");
				return ShellStatus.CONTINUE;
			}
			
			Path dir1 = env.getCurrentDirectory().resolve(Paths.get(argumentsList.get(0)));
			Path dir2 = env.getCurrentDirectory().resolve(Paths.get(argumentsList.get(1)));
			if (!Files.isDirectory(dir1) || !Files.isDirectory(dir2)) {
				env.writeln("First two arguments should be directories.");
				return ShellStatus.CONTINUE;
			}

			List<FilterResult> result = filter(dir1, argumentsList.get(3));
			String cmd = argumentsList.get(2);
			if((cmd.equals("filter") || cmd.equals("groups")) && argumentsList.size() != 4
					|| (cmd.equals("show") || cmd.equals("execute")) && argumentsList.size() != 5) {
				env.writeln("Invalid number of arguments for this CMD.");
				return ShellStatus.CONTINUE;
			}
				
			switch (argumentsList.get(2)) {
				case "filter":
					filterFiles(result, env);
					break;
				case "groups":
					groups(result, env);
					break;
				case "show":
					show(result, env, argumentsList.get(4));
					break;
				case "execute":
					execute(result, env, argumentsList.get(4), dir1, dir2);
					break;
				default:
					env.writeln("Invalid CMD.");
					break;
			}
		} catch (InvalidPathException ex) {
			env.writeln("Invalid path given.");
		} catch (IOException ex) {
			env.writeln("Could not read from directory.");
		} catch (PatternSyntaxException ex) {
			env.writeln("Pattern syntax is invalid.");
		} catch (IllegalArgumentException ex) {
			env.writeln("Illegal arguments. " + ex.getMessage());
			return ShellStatus.CONTINUE;
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
	 * Pomoćna metoda koja služi za obradu podnaredbe execute.
	 * Na temelju generiranih novih imena i danih direktorija, datoteke se preimenuju i premiještaju u 
	 * ciljni direktorij.
	 * 
	 * @param filteredFiles lista filtritanih datoteka
	 * @param env environment
	 * @param expression izraz prema kojem se generiraju nova imena datoteka
	 * @param dir1 izvorišni direktorij
	 * @param dir2 ciljni direktorij
	 */
	private void execute(List<FilterResult> filteredFiles, Environment env, String expression, Path dir1, Path dir2) {
		if(filteredFiles.isEmpty()) {
			env.writeln("No files found that match the filter.");
		} else {
			try {
				Map<String, String> newNames = newNames(filteredFiles, expression);
				newNames.forEach((k, v) -> {
					try {
						Files.move(dir1.resolve(k), dir2.resolve(v));
					} catch (IOException e) {
						env.writeln("Couldn't move files.");
					}
				});
				newNames.forEach((k, v) -> env.writeln(dir1.resolve(k).toString() + " => " 
						+ dir2.resolve(v).toString()));
			} catch(NameBuilderException | IllegalArgumentException ex) {
				env.writeln(ex.getMessage());
				return;
			}
		}
	}
	
	/**
	 * Pomoćna metoda za vraćanje liste filtritanih datoteka s obzirom na ime.
	 * 
	 * @param dir izvorišni direktorij
	 * @param pattern uzorak prema kojem se filtriraju imena datoteka
	 * @return lista filtriranih datoteka
	 * @throws IOException ako dođe do greške pri čitanju datoteka
	 */
	private static List<FilterResult> filter(Path dir, String pattern) throws IOException {
		List<FilterResult> result = new ArrayList<>();
		List<Path> files = Files.list(dir).filter(Files::isRegularFile).collect(Collectors.toList());
		Pattern pattern2 = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

		for (Path file : files) {
			Matcher matcher = pattern2.matcher(file.getFileName().toString());
			if (matcher.matches()) {
				result.add(new FilterResult(file, matcher));
			}
		}

		return result;
	}

	
	/**
	 * Pomoćna metoda za ispisivanje rezultata filtriranja datoteka.
	 * 
	 * @param filteredFiles lista filtriranih datoteka
	 * @param env environment
	 */
	private static void filterFiles(List<FilterResult> filteredFiles, Environment env) {
		if(filteredFiles.isEmpty()) {
			env.writeln("No files found that match the filter.");
		} else {
			filteredFiles.forEach(fr -> env.writeln(fr.toString()));
		}
	}

	/**
	 * Pomoćna metoda za obradu podnaredbe groups.
	 * Ispisuje indeks i vrijednost pojedine grupe.
	 * 
	 * @param filteredFiles lista filtriranih datoteka
	 * @param env environment
	 */
	private static void groups(List<FilterResult> filteredFiles, Environment env) {
		if(filteredFiles.isEmpty()) {
			env.writeln("No files found that match the filter.");
		} else {
			for (FilterResult fr : filteredFiles) {
				StringBuilder sb = new StringBuilder();
				sb.append(fr.toString() + " ");
				for (int i = 0; i <= fr.numberOfGroups(); i++) {
					sb.append(i + ": " + fr.group(i) + " ");
				}
				env.writeln(sb.toString());
			}
		}
	}

	/**
	 * Pomoćna metoda za obradu podnaredbe show.
	 * Ispisuje nova imena koja se dobiju izrazom expression.
	 * 
	 * @param filteredFiles lista filtriranih datoteka
	 * @param env environment
	 * @param String expression izraz prema kojem se generiraju nova imena
	 */
	private void show(List<FilterResult> filteredFiles, Environment env, String expression) {
		try {
			if(filteredFiles.isEmpty()) {
				env.writeln("No files found that match the filter.");
			} else {
				Map<String, String> newNames = newNames(filteredFiles, expression);
				newNames.forEach((k, v) -> env.writeln(k + " => " + v));
			}
		} catch(NameBuilderException | IllegalArgumentException ex) {
			env.writeln(ex.getMessage());
			return;
		}
	}
	
	/**
	 * Pomoćna metoda koja vraća mapu kojoj su ključevi stara imena datoteka zajedno sa stazom, 
	 * a vrijednosti novi nazivi datoteka.
	 * 
	 * @param filteredFiles lista filtriranih datoteka
	 * @param expression izraz prema kojem se generiraju nova imena
	 * @return mapa novih naziva datoteka
	 */
	private Map<String, String> newNames (List<FilterResult> filteredFiles, String expression) {
		Map<String, String> newNames = new HashMap<>();
		NameBuilderParser parser = new NameBuilderParser(expression);
		NameBuilder builder = parser.getNameBuilder();
		
		for (FilterResult file : filteredFiles) {
			StringBuilder sb = new StringBuilder();
			builder.execute(file, sb);
			String newName = sb.toString();
			newNames.put(file.toString(), newName);
		}
		return newNames;
	}
	
}
