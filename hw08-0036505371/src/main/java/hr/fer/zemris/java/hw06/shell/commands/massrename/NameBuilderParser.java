package hr.fer.zemris.java.hw06.shell.commands.massrename;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static hr.fer.zemris.java.hw06.shell.commands.massrename.NameBuilderImplementations.*;

/**
 * Razred koji dobiva neki izraz, parsira ga te stvara novi {@link NameBuilder} za generiranje novog imena.
 * 
 * @author Maja Radočaj
 *
 */
public class NameBuilderParser {

	/**
	 * {@link NameBuilder} nastao parsiranjem.
	 */
	private NameBuilder builder;
	/**
	 * Lexer.
	 */
	private NameBuilderLexer lexer;
	/**
	 * Lista NameBuilder-a koji su dio glavnog, kompozitnog NameBuilder-a.
	 */
	private List<NameBuilder> buildersList;
	
	/**
	 * Javni konstruktor.
	 * 
	 * @param expression izraz kojeg treba parsirati
	 */
	public NameBuilderParser(String expression) {
		Objects.requireNonNull(expression);
		buildersList = new ArrayList<>();
		lexer = new NameBuilderLexer(expression);
		parse();
	}

	/**
	 * Getter koji vraća NameBuilder nastao parsiranjem.
	 * @return
	 */
	public NameBuilder getNameBuilder() {
		return builder;
	}

	/**
	 * Pomoćna metoda za parsiranje.
	 * Dodaje Buildere u listu tijekom parsiranja.
	 * Na kraju stvara kompozitni NameBuilder.
	 * 
	 * @throws NameBuilderException u slučaju greške pri tokeniziranju/parsiranju
	 */
	private void parse() {
		while(!lexer.nextToken().getType().equals(NameBuilderTokenType.END)) {
			NameBuilderToken token = lexer.getToken();
			if(token.getType().equals(NameBuilderTokenType.STRING)) {
				buildersList.add(text(token.getValue()));
			} else {
				String[] parts = token.getValue().split(",");
				if(parts.length == 1) {
					buildersList.add(group(Integer.parseInt(parts[0])));
				} else {
					char padding;
					int number;
					if(parts[1].startsWith("0")) {
						padding = '0';
						number = Integer.parseInt(parts[1].substring(1));
					} else {
						padding = ' ';
						number = Integer.parseInt(parts[1]);
					}
					buildersList.add(group(Integer.parseInt(parts[0]), padding, number));
				}
			}
		}
		
		builder = composit(buildersList);
	}

}
