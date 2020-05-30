package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.FileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Razred koji modelira naredbu za ispisivanje stabla direktorija. Naredba prima
 * jedan argument - naziv direktorija od kojeg dalje rekurzivno ispisuje sve
 * direktorije i dokumente.
 * 
 * @author Maja Radočaj
 *
 */
public class TreeShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	private static final String COMMAND_NAME = "tree";
	/**
	 * Opis naredbe.
	 */
	private List<String> commandDescription;

	/**
	 * Defaultni konstruktor.
	 */
	public TreeShellCommand() {
		commandDescription = new ArrayList<>();
		commandDescription.add("The tree command expects a single argument: directory name.");
		commandDescription
				.add("It then prints a tree (each directory level shifts "
						+ "output two charatcers " + "to the right).");
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

		if(!Files.isDirectory(path)) {
			env.writeln("Path to directory expected.");
			return ShellStatus.CONTINUE;
		}

		try {
			Files.walkFileTree(path, new PrintTree(env));
		} catch(IOException ex) {
			env.writeln("Cannot print tree.");
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
	 * Pomoćna metoda za rekurzivni obilazak direktorija te ispisivanje stabla
	 * direktorija. Svaka nova razina odvojena je sa dva razmaka.
	 * 
	 * @author Maja Radočaj
	 *
	 */
	private static class PrintTree implements FileVisitor<Path> {
		private int level;
		Environment env;

		public PrintTree(Environment env) {
			this.env = env;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			level++;
			env.writeln(" ".repeat(2 * level) + dir.getFileName());
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			env.writeln(" ".repeat(2 * level) + file.getFileName());
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			level--;
			return FileVisitResult.CONTINUE;
		}
	}
}
