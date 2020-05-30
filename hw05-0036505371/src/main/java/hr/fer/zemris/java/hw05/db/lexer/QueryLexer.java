package hr.fer.zemris.java.hw05.db.lexer;

import java.util.Objects;

import hr.fer.zemris.java.hw05.db.QueryParser;

/**
 * Lexer koji služi za tokeniziranje ulaznog niza u svrhu parsiranja u razredu
 * {@link QueryParser}.
 * 
 * @author Maja Radočaj
 *
 */
public class QueryLexer {

	/**
	 * Ulazni podaci u obliku polja charactera.
	 */
	private char[] data;
	/**
	 * Zadnji vraćeni token.
	 */
	private Token token;
	/**
	 * Zadnji obrađeni indeks ulaznih podataka.
	 */
	private int currentIndex;

	/**
	 * Konstruktor koji inicijalizira lexer. Primljeni String ne smije biti
	 * <code>null</code>. Ako je, baca se {@link NullPointerException}.
	 * 
	 * @param text ulazni podaci
	 * @throws NullPointerException ako predana vrijednost <code>null</code>
	 */
	public QueryLexer(String text) {
		data = (Objects.requireNonNull(text).toCharArray());
	}

	/**
	 * Metoda koja vraća zadnji pročitani token.
	 * 
	 * @return zadnji pročitani token.
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Metoda koja prolazi kroz ulazne podatke od trenutnog indeksa
	 * <code>currentIndex</code> te iz njih stvara novi token. Ukoliko se ne može
	 * pravilno stvoriti novi token, baca se {@link QueryLexerException}.
	 * 
	 * @return sljedeći token
	 * @throws QueryLexerException ako nije moguće stvoriti token
	 */
	public Token nextToken() {
		if(isEnd()) {
			token = new Token(TokenType.END, "");
			currentIndex++;
			return token;
		}

		if(currentIndex > data.length) {
			throw new QueryLexerException("All tokens have been returned.");
		}

		if(data[currentIndex] == '"') {
			getStringLiteral();
		} else if(Character.isLetter(data[currentIndex])) {
			getIdentificator();
		} else {
			getOperator();
		}

		return token;
	}

	/**
	 * Pomoćna metoda koja provjerava jesmo li pročitali sve tokene i došli do kraja
	 * ulaznih podataka. Uz to, uklanja sve početne razmake.
	 * 
	 * @return <code>true</code> ako nema više nikakvih tokena, <code>false</code>
	 *         ako ima
	 */
	private boolean isEnd() {
		if(currentIndex == data.length)
			return true;
		while(Character.isWhitespace(data[currentIndex])) {
			currentIndex++;
			if(currentIndex == data.length)
				return true;
		}
		return false;
	}

	/**
	 * Pomoćna metoda koja dohvaća String literal u navodnicima. Ako literal nije
	 * okružen navodnicima, baca se {@link QueryLexerException}.
	 */
	private void getStringLiteral() {
		currentIndex++;
		StringBuilder sb = new StringBuilder("");

		while(true) {
			if(currentIndex == data.length) {
				throw new QueryLexerException("String literal must end with '\"'.");
			}
			if(data[currentIndex] == '"')
				break;
			sb.append(data[currentIndex]);
			currentIndex++;
		}

		currentIndex++; // preskačemo završne navodnike
		token = new Token(TokenType.STRING_LITERAL, sb.toString());
	}

	/**
	 * Pomoćna metoda za dohvaćanje identifikatora. Identifikator se sastoji samo od
	 * slova.
	 */
	private void getIdentificator() {
		StringBuilder sb = new StringBuilder("");

		while(Character.isLetter(data[currentIndex])) {
			sb.append(data[currentIndex]);
			currentIndex++;
			if(currentIndex == data.length)
				break;
		}

		token = new Token(TokenType.IDENTIFICATOR, sb.toString());
	}

	/**
	 * Pomoćna metoda za dohvaćanje operatora. Ako se ne može stvoriti jedan od
	 * podržanih operatora, baca se {@link QueryLexerException}.
	 * 
	 * @throws QueryLexerException ako nije validan operator
	 */
	private void getOperator() {
		String operator = "";

		switch(data[currentIndex]) {
			case '=':
				operator = "=";
				break;
	
			case '!':
				currentIndex++;
				if(currentIndex < data.length && data[currentIndex] == '=') {
					operator = "!=";
				} else {
					throw new QueryLexerException("Invalid operator.");
				}
				break;
	
			case '>':
				if(currentIndex + 1 < data.length && data[currentIndex + 1] == '=') {
					currentIndex++;
					operator = ">=";
				} else {
					operator = ">";
				}
				break;
	
			case '<':
				if(currentIndex + 1 < data.length && data[currentIndex + 1] == '=') {
					currentIndex++;
					operator = "<=";
				} else {
					operator = "<";
				}
				break;
	
			default:
				throw new QueryLexerException("Operator expected.");

		}

		token = new Token(TokenType.OPERATOR, operator);
		currentIndex++;
	}
}
