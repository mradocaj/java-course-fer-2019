package hr.fer.zemris.java.hw06.shell.commands.massrename;

/**
 * Sučelje koje ima jednu metodu za građenje novog imena datoteke.
 * 
 * @author Maja Radočaj
 *
 */
public interface NameBuilder {

	/**
	 * Metoda koja nad danom datotekom koja je rezultat filtriranja obavlja radnju te rezultat svoje radnje
	 * nadovezuje na jedan od argumenata - StringBuilder sb.
	 * 
	 * @param result rezultat filtriranja
	 * @param sb stringbuilder
	 */
	void execute(FilterResult result, StringBuilder sb);	
}
