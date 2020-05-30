package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

/**
 * Razred koji modelira lexer za potrebe parsiranja u razredu SmartScriptParser.
 * Primjenjuje pravila grupiranja znakova definiranih u 3. zadatku 3. domaće
 * zadaće za neki ulazni tekst.
 * 
 * @author Maja Radočaj
 *
 */
public class SmartScriptLexer {

	/**
	 * Ulazni tekst.
	 */
	private char[] data;
	/**
	 * Trenutni token.
	 */
	private SmartToken token;
	/**
	 * Indeks prvog neobrađenog znaka.
	 */
	private int currentIndex;
	/**
	 * Stanje u kojem se lexer trenutno nalazi.
	 */
	private SmartLexerState state;

	public SmartScriptLexer(String text) {
		data = text.toCharArray();
		state = SmartLexerState.TEXT;
	}

	/**
	 * Metoda koja generira novi token prema točno određenim pravilima. U slučaju
	 * greške baca {@link SmartLexerException}.
	 * 
	 * @return novi token
	 * @throws SmartLexerException ako dođe do greške pri tokeniziranju
	 */
	public SmartToken nextToken() {
		if(checkIfEnd())
			return token; // ako su pročitani svi tokeni, sljedeći je EOF
		if(currentIndex > data.length) { // ako je zadnji token EOF, baciti iznimku
			throw new SmartLexerException("End of file reached - cannot read more tokens!");
		}

		if(state.equals(SmartLexerState.TEXT)) {
			if(data[currentIndex] == '{' && currentIndex + 1 < data.length 
					&& data[currentIndex + 1] == '$') {
				currentIndex = currentIndex + 2;
				token = new SmartToken(SmartTokenType.OPEN_TAG, "{$");
				return token;
			}
			String text = getText();
			token = new SmartToken(SmartTokenType.TEXT, text);

		} else {
			removeWhitepaces();

			if(data[currentIndex] == '$' && currentIndex + 1 < data.length 
					&& data[currentIndex + 1] == '}') {
				currentIndex = currentIndex + 2;
				token = new SmartToken(SmartTokenType.CLOSE_TAG, "$}");
				return token;
			}

			getNewToken();
		}
		return token;
	}

	/**
	 * Metoda koja vraća zadnji generirani token.
	 * 
	 * @return zadnji generirani token
	 */
	public SmartToken getToken() {
		return token;
	}

	/**
	 * Pomoćna metoda koja provjerava jesmo li došli na kraj dokumenta.
	 * 
	 * @return true ako jesmo, false ako ne
	 */
	private boolean checkIfEnd() {
		if(currentIndex == data.length) { // ako smo došli na kraj, novi token je EOF
			token = new SmartToken(SmartTokenType.EOF, null);
			currentIndex++;
			return true;
		}
		return false;
	}

	/**
	 * Metoda za postavljanje stanja leksera.
	 * 
	 * @param state dano stanje
	 */
	public void setState(SmartLexerState state) {
		Objects.requireNonNull(state);
		this.state = state;
	}

	/**
	 * Pomoćna metoda koja provjerava može li trenutni znak postati varijabla. Znak
	 * može biti varijabla ako započinje sa slovom.
	 * 
	 * @return true ako može, false ako ne
	 */
	private boolean isValidVariable() {
		if(Character.isLetter(data[currentIndex]))
			return true;
		return false;
	}

	/**
	 * Pomoćna metoda koja uklanja sve početne razmake.
	 */
	private void removeWhitepaces() {
		while(Character.isWhitespace(data[currentIndex])) {
			currentIndex++;
			if(currentIndex == data.length) {
				break;
			}
		}
	}

	/**
	 * Pomoćna metoda koja dohvaća ime varijable ili funkcije. Svaki sljedeći znak
	 * koji je slovo, brojka ili '_' će ući u naziv varijable ili funkcije.
	 * 
	 * @return ime varijable ili funkcije
	 */
	private String getName() {
		String variable = "";

		while(Character.isLetter(data[currentIndex]) 
				|| Character.isDigit(data[currentIndex])
				|| data[currentIndex] == '_') {
			variable += data[currentIndex];
			currentIndex++;
			if(currentIndex == data.length)
				break;
		}
		return variable;
	}

	/**
	 * Pomoćna metoda koja provjerava poštuju li se pravila esapeanja u tagovima.
	 * Ako poslije \ ne slijedi '\', 'n', 'r', 't' ili '"', baca se iznimka.
	 * 
	 * @throws SmartLexerException ako se ne poštuju pravila escapeanja
	 */
	private String tagEscaping() {
		currentIndex++;
		if(currentIndex == data.length) {
			throw new SmartLexerException("Invalid escaping sequence.");
		}
		char next = data[currentIndex];
		if(next == 'n') {
			return "\n";
		} else if(next == 'r') {
			return "\r";
		} else if(next == 't') {
			return "\t";
		} else if(next == '"') {
			return "\"";
		} else if(next == '\\') {
			return "\\";
		} else {
			throw new SmartLexerException("Invalid escaping sequence.");
		}
	}

	/**
	 * Pomoćna metoda koja vraća tekst u navodnicima.
	 * 
	 * @return tekst u navodnicima
	 * @throws SmartLexerException ako postoje ilegalni escapeovi
	 */
	private String getText() {
		String text = "";

		while(true) { // dok ne naiđe na neki tag, radi se o tekstu
			if(data[currentIndex] == '{') {
				if(currentIndex + 1 < data.length && data[currentIndex + 1] == '$') {
					break;
				}
			}
			if(data[currentIndex] == '\\') {
				currentIndex++;
				if(currentIndex == data.length || data[currentIndex] != '{' 
						&& data[currentIndex] != '\\') {
					throw new SmartLexerException("Invalid escaping sequence.");
					// baca iznimku ako iza '\' ne slijedi '\' ili '{'
				}
			}
			text += data[currentIndex];
			currentIndex++;
			if(currentIndex == data.length) {
				break;
			}
		}

		return text;
	}

	/**
	 * Pomoćna metoda koja stvara novi token koji sadrži broj koji se nalazi unutar tag-a.
	 * Broj može biti double ili integer.
	 */
	private void getNumber() {
		int dotCount = 0;
		String number = "";
		if(data[currentIndex] == '-') { //ako naiđemo na '-' iza kojeg je brojka, 
										//znamo da onda '-' dio broja
			number = "-";
			currentIndex++;
		}
		while(Character.isDigit(data[currentIndex]) || data[currentIndex] == '.') {
			if(data[currentIndex] == '.') {
				dotCount++;
				if(dotCount > 1)
					break;
			}
			number += data[currentIndex];
			currentIndex++;
			if(currentIndex == data.length)
				break;
		}

		if(!number.contains(".")) {
			token = new SmartToken(SmartTokenType.INTEGER, Integer.parseInt(number));
		} else {
			token = new SmartToken(SmartTokenType.DOUBLE, Double.parseDouble(number));
		}
	}

	/**
	 * Pomoćna metoda koja stvara novi token koji sadrži string unutar tag-a.
	 */
	private void getString() {
		if(data[currentIndex] != '"') {
			throw new SmartLexerException("Invalid symbol.");
		}
		currentIndex++; // preskačemo početne navodnike
		String value = "";
		while(data[currentIndex] != '"') {
			if(data[currentIndex] == '\\') {
				value += tagEscaping();
				currentIndex++;
			} else {
				value += data[currentIndex++];
			}
			if(currentIndex == data.length) {
				throw new SmartLexerException("String has to end with '\"'.");
			}
		}
		if(currentIndex != data.length) {
			currentIndex++; // preskačemo završne navodnike
		}

		token = new SmartToken(SmartTokenType.STRING, value);
	}

	/**
	 * Pomoćna metoda koja ovisno o ulaznim znakovima stvara novi token određenog tipa.
	 */
	private void getNewToken() {
		if(currentIndex + 3 < data.length 
				&& Character.toUpperCase(data[currentIndex]) == 'F'
				&& Character.toUpperCase(data[currentIndex + 1]) == 'O'
				&& Character.toUpperCase(data[currentIndex + 2]) == 'R') {

			currentIndex = currentIndex + 3;
			token = new SmartToken(SmartTokenType.FOR, "FOR");
		} else if(data[currentIndex] == '=') {
			currentIndex++;
			token = new SmartToken(SmartTokenType.EQU, "=");
		} else if(currentIndex + 3 < data.length 
				&& Character.toUpperCase(data[currentIndex]) == 'E'
				&& Character.toUpperCase(data[currentIndex + 1]) == 'N'
				&& Character.toUpperCase(data[currentIndex + 2]) == 'D') {

			currentIndex = currentIndex + 3;
			token = new SmartToken(SmartTokenType.END, "END");
		} else if(data[currentIndex] == '-' 
				&& currentIndex + 1 < data.length
				&& Character.isDigit(data[currentIndex + 1]) 
				|| Character.isDigit(data[currentIndex])) {

			getNumber();
		} else if(isValidVariable()) { // provjerava može li se nešto tumačiti kao varijabla
			String variable = getName(); // dohvaća ime varijable
			token = new SmartToken(SmartTokenType.VARIABLE, variable);
		} else if(data[currentIndex] == '@') {
			String function = "";
			currentIndex++; // da zaobiđemo '@'
			function += getName(); // dohvaća ime funkcije
			token = new SmartToken(SmartTokenType.FUNCT, function);
		} else if(data[currentIndex] == '+' 
				|| data[currentIndex] == '-' 
				|| data[currentIndex] == '*'
				|| data[currentIndex] == '/' 
				|| data[currentIndex] == '^') {

			token = new SmartToken(SmartTokenType.OPERATION, "" + data[currentIndex]);
			currentIndex++;
		} else { // ako nije ništa od navedenog, onda je sigurno string
			getString();
		}
	}
}
