package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Sučelje koje definira jednostavnu naredbu potrebnu za rad u programu
 * {@link MyShell}.
 * 
 * @author Maja Radočaj
 *
 */
public interface ShellCommand {

	/**
	 * Metoda koja izvršava naredbu u danom okruženju i sa danim argumentima. U
	 * slučaju greške, naredba se zaustavlja i čeka se novi korisnikov unos.
	 * 
	 * @param env       okruženje u kojem se naredba vrši
	 * @param arguments argumenti uz naredbu
	 * @return status nakon izvršavanje naredbe {@link ShellStatus}
	 *         (<code>CONTINUE</code> ili <code>TERMINATE</code>).
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Metoda koja vraća naziv naredbe.
	 * 
	 * @return naziv naredbe
	 */
	String getCommandName();

	/**
	 * Metoda koja vraća opis naredbe u obliku liste Stringova.
	 * 
	 * @return opis naredbe
	 */
	List<String> getCommandDescription();

}
