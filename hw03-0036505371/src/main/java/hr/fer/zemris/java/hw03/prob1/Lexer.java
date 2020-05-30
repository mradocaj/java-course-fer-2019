package hr.fer.zemris.java.hw03.prob1;

import java.util.Objects;

/**
 * Razred koji definira lexer za jednostavnu gramatiku. Lexer prima ulazni niz
 * znakova u obliku Stringa kojeg prebacuje u polje elemenata tipa char. Nakon
 * toga prema određenim pravilima grupira znakove te stvara tokene koje vraća
 * korisniku na njegov zahtjev.
 * 
 * @author Maja Radočaj
 *
 */
public class Lexer {

	/**
	 * Ulazni tekst kojeg treba obraditi.
	 */
	private char[] data;
	/**
	 * Zadnji vraćeni token.
	 */
	private Token token;
	/**
	 * Indeks prvog neobrađenog znaka.
	 */
	private int currentIndex;
	/**
	 * Stanje u kojem se lexer trenutačno nalazi.
	 */
	private LexerState state;

	/**
	 * Konstruktor koji prima ulatni tekst koji se tokenizira.
	 * 
	 * @param text
	 */
	public Lexer(String text) {
		data = text.toCharArray();
		state = LexerState.BASIC;
	}

	/**
	 * Metoda koja prema točno određenim pravilima generira i vraća sljedeći token.
	 * Ako dođe do greške ili se zatraži token kad su svi već pročitani, baca se
	 * LexerException.
	 * 
	 * @return novi token
	 * @throws QueryLexerException ako dođe do greške
	 */
	public Token nextToken() {
		if(checkIfEnd())
			return token; // ako su pročitani svi tokeni, sljedeći je EOF
		if(currentIndex > data.length) { // ako je zadnji token EOF, baciti iznimku
			throw new QueryLexerException("There is no next token.");
		}

		while(Character.isWhitespace(data[currentIndex])) {
			currentIndex++;
			if(checkIfEnd())
				return token; // ako su samo razmaci, vraća se EOF
		}

		if(state.equals(LexerState.EXTENDED)) {

			if(data[currentIndex] == '#') {
				// ako smo u EXTENDED načinu rada, vraća
				// simbol samo za '#'
				token = new Token(TokenType.SYMBOL, data[currentIndex++]); 
			} else {
				String longWord = "";

				while(data[currentIndex] != '#' && !Character.isWhitespace(data[currentIndex])) {
					longWord += data[currentIndex];
					currentIndex++;
					if(currentIndex == data.length)
						break;
				}
				token = new Token(TokenType.WORD, longWord);
			}
			return token;
		}

		char character = data[currentIndex];

		if(Character.isLetter(character) || character == '\\') {
			String word = "";

			while(Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
				if(data[currentIndex] == '\\') {
					currentIndex++;
					if(currentIndex == data.length
							|| !Character.isDigit(data[currentIndex]) && data[currentIndex] != '\\') {
						throw new QueryLexerException("Invalid escape sequence.");
					}
				}
				word += data[currentIndex];
				currentIndex++;
				if(currentIndex == data.length)
					break;
			}
			token = new Token(TokenType.WORD, word);
		} else if(Character.isDigit(character)) {
			Long number = 0L;

			while(Character.isDigit(data[currentIndex])) {
				try {
					number = Math.addExact(Math.multiplyExact(number, 10L), Long.parseLong("" + data[currentIndex]));
					currentIndex++;
					if(currentIndex == data.length)
						break;
				} catch(ArithmeticException ex) {
					throw new QueryLexerException("Invalid number.");
				}
			}
			token = new Token(TokenType.NUMBER, number);
		} else {
			token = new Token(TokenType.SYMBOL, data[currentIndex]);
			currentIndex++;
		}

		return token;
	}

	/**
	 * Metoda koja vraća zadnji generirani token, ali ne pokreće generiranje
	 * sljedećeg tokena.
	 * 
	 * @return zadnji generirani token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Metoda pomoću koje korisnik izvana može postaviti željeni način grupiranja
	 * znakova. Baca iznimku {@link NullPointerException} ako se pokuša poslati null
	 * vrijednost.
	 * 
	 * @param state način grupiranja znakova
	 * @throws NullPointerException ako je predano stanje null
	 */
	public void setState(LexerState state) {
		Objects.requireNonNull(state);
		this.state = state;
	}

	/**
	 * Privatna metoda pomoću koje provjeravamo jesmo li došli na kraj teksta. Ako
	 * jesmo, postavlja sljedeći token na EOF.
	 * 
	 * @return true ako smo došli do kraja, false ako nismo
	 */
	private boolean checkIfEnd() {
		if(currentIndex == data.length) { // ako smo došli na kraj, novi token je EOF
			token = new Token(TokenType.EOF, null);
			currentIndex++;
			return true;
		}
		return false;
	}
}
