package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Razred koji modelira naredbu za ispisivanje sadržaja danog direktorija
 * (nerekurzivno). Naredba prima jedan argument. Detaljniji opis naredbe može se
 * dohvatiti pomoću metode <code>getCommandDescription</code>.
 * 
 * @author Maja Radočaj
 *
 */
public class LsShellCommand implements ShellCommand {

	/**
	 * Ime naredbe.
	 */
	private static final String COMMAND_NAME = "ls";
	/**
	 * Opis naredbe.
	 */
	private List<String> commandDescription;

	/**
	 * Defaultni konstruktor.
	 */
	public LsShellCommand() {
		commandDescription = new ArrayList<>();
		commandDescription.add("Command ls takes a single argument – directory – "
				+ "and writes a directory listing (not recursive).");
		commandDescription.add("The output consists of 4 columns.");
		commandDescription.add("First column indicates if current object is directory (d), "
				+ "readable (r), writable (w) and executable (x).");
		commandDescription.add(
				"Second column contains object size in bytes that is right aligned " + 
		"and occupies 10 characters.");
		commandDescription.add("Follows file creation date/time and finally file name.");
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
			env.writeln("Path to directory required as argument.");
			return ShellStatus.CONTINUE;
		}

		try(DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
			for(Path current : stream) {
				env.writeln(getLine(current));
			}
		} catch(IOException ex) {
			env.writeln("Couldn't resolve path.");
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
	 * Pomoćna metoda koja vraća liniju ispisa koja sadrži podatke o predanoj
	 * datoteci ili direktoriju.
	 * 
	 * @param current predani direktorij ili datoteka
	 * @return linija koja sadrži podatke o direktoriju ili datoteci
	 * @throws IOException u slučaju greške pri čitanju podataka
	 */
	private String getLine(Path current) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(Files.isDirectory(current) ? 'd' : '-');
		sb.append(Files.isReadable(current) ? 'r' : '-');
		sb.append(Files.isWritable(current) ? 'w' : '-');
		sb.append(Files.isExecutable(current) ? 'x' : '-');
		sb.append(String.format(" %10s ", Files.size(current)));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BasicFileAttributeView faView = Files.getFileAttributeView(current, 
				BasicFileAttributeView.class,
				LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes = faView.readAttributes();
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

		sb.append(formattedDateTime);
		sb.append(" " + current.getFileName().toString());
		return sb.toString();
	}

}
