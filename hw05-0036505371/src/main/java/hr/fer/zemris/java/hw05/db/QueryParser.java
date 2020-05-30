package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.db.lexer.QueryLexer;
import hr.fer.zemris.java.hw05.db.lexer.QueryLexerException;
import hr.fer.zemris.java.hw05.db.lexer.TokenType;

/**
 * Razred koji služi za parsiranje jednostavnih upita - query. Ulazni tekst
 * tokenizira lexer koji baca iznimku ako je tekst nepravilno unesen. Upiti se
 * formiraju prema jednostavnim pravilima. Ako se ta pravila ne poštuju, baca se
 * {@link QueryParserException}.
 * 
 * @author Maja Radočaj
 *
 */
public class QueryParser {

	/**
	 * Lexer za tokeniziranje ulaznih podataka.
	 */
	private QueryLexer lexer;
	/**
	 * Lista uvjetnih izraza dobivenih parsiranjem.
	 */
	private List<ConditionalExpression> query;

	/**
	 * Konstruktor koji inicijalizira lexer te započinje parsiranje. Ako se pri
	 * tokeniziranju ili parsiranju javi greška, baca se
	 * {@link QueryParserException}.
	 * 
	 * @param text ulazni podaci
	 * @throws QueryParserException ako se javi greška pri tokeniziranju ili
	 *                              parsiranju
	 */
	public QueryParser(String text) {
		try {
			lexer = new QueryLexer(text);
			query = parse();
		} catch(QueryLexerException | NullPointerException ex) {
			throw new QueryParserException(ex.getMessage());
		}
	}

	/**
	 * Metoda koja provjerava je li upit direktan (oblika
	 * <code>query jmbag = "xxx"</code>).
	 * 
	 * @return <code>true</code> ako je upit direktan, <code>false</code> ako nije
	 */
	public boolean isDirectQuery() {
		return query.size() == 1 && query.get(0).getFieldGetter().equals(FieldValueGetters.JMBAG)
				&& query.get(0).getComparisonOperator().equals(ComparisonOperators.EQUALS);
	}

	/**
	 * Metoda koja vraća JMBAG za direktan upit. Ukoliko upit nije direktan, baca se
	 * iznimka.
	 * 
	 * @return JMBAG direktnog upita
	 * @throws IllegalStateException ako upit nije direktan
	 */
	public String getQueriedJMBAG() {
		if(!isDirectQuery()) {
			throw new IllegalStateException("This query is not a direct query.");
		}
		return query.get(0).getStringLiteral();
	}

	/**
	 * Metoda koja vraća listu svih uvjetnih izraza nastalih parsiranjem.
	 * 
	 * @return lista uvjetnih izraza
	 */
	public List<ConditionalExpression> getQuery() {
		return query;
	}

	/**
	 * Metoda koja za ulazne tokene vrši parsiranje. Rezultat parsiranja je lista
	 * uvjetnih izraza. Ukoliko se pojavi greška pri parsiranju, baca se
	 * {@link QueryParserException}.
	 * 
	 * @return lista uvjetnih izraza
	 * @throws QueryParserException ako se pojavila greška pri parsiranju
	 * @throws QueryLexerException  ako se pojavila greška pri tokeniziranju
	 */
	private List<ConditionalExpression> parse() {
		if(lexer.nextToken().getType().equals(TokenType.END)) {
			throw new QueryParserException("Query command needs arguments.");
		}
		List<ConditionalExpression> list = new ArrayList<>();

		while(true) {
			if(lexer.getToken().getValue().equals("firstName") 
					|| lexer.getToken().getValue().equals("lastName")
					|| lexer.getToken().getValue().equals("jmbag")) {
				
				addConditionalExpression(list);
			} else {
				throw new QueryParserException("Unsuported attribute name.");
			}

			if(lexer.nextToken().getType().equals(TokenType.END)) {
				break;
			} else if(!lexer.getToken().getValue().toLowerCase().equals("and")) {
				throw new QueryParserException("Expressions must be connected with \"AND\".");
			}

			lexer.nextToken();
		}

		return list;
	}

	/**
	 * Pomoćna metoda koja dodaje uvjetni izraz u listu uvjetnih izraza.
	 * 
	 * @param list lista uvjetnih izraza
	 * @throws QueryParserException ako dođe do greške pri parsiranju
	 */
	private void addConditionalExpression(List<ConditionalExpression> list) {
		IFieldValueGetter getter = fieldGetter();
		IComparisonOperator operator;
		String stringLiteral;

		if(!lexer.nextToken().getType().equals(TokenType.IDENTIFICATOR)
				&& !lexer.getToken().getType().equals(TokenType.OPERATOR)) {
			throw new QueryParserException("Operator expected.");
		}

		operator = getOperator();

		if(!lexer.nextToken().getType().equals(TokenType.STRING_LITERAL)) {
			throw new QueryParserException("String literal expected.");
		}

		stringLiteral = lexer.getToken().getValue();

		try {
			ConditionalExpression expression = new ConditionalExpression(getter, stringLiteral, operator);
			list.add(expression);
		} catch(IllegalArgumentException ex) {
			throw new QueryParserException(ex.getMessage());
		}
	}

	/**
	 * Pomoćna metoda koja definira koje polje zapisa o studentu treba vratiti.
	 * 
	 * @return polje zapisa o studentu
	 */
	private IFieldValueGetter fieldGetter() {
		IFieldValueGetter getter;

		switch(lexer.getToken().getValue()) {
		case "firstName":
			getter = FieldValueGetters.FIRST_NAME;
			break;
		case "lastName":
			getter = FieldValueGetters.LAST_NAME;
			break;
		default:
			getter = FieldValueGetters.JMBAG;
			break;
		}

		return getter;
	}

	/**
	 * Pomoćna metoda koja definira koju operaciju mora vratiti na temelju trenutnog
	 * tokena. Ako trenutni token ne zadovoljava nijedan operator, baca se iznimka.
	 * 
	 * @return novi operator
	 * @throws QueryParserException ako nije dobro definiran operator
	 */
	private IComparisonOperator getOperator() {
		IComparisonOperator operator;

		switch(lexer.getToken().getValue()) {
		case ">":
			operator = ComparisonOperators.GREATER;
			break;
		case ">=":
			operator = ComparisonOperators.GREATER_OR_EQUALS;
			break;
		case "<":
			operator = ComparisonOperators.LESS;
			break;
		case "<=":
			operator = ComparisonOperators.LESS_OR_EQUALS;
			break;
		case "=":
			operator = ComparisonOperators.EQUALS;
			break;
		case "!=":
			operator = ComparisonOperators.NOT_EQUALS;
			break;
		case "LIKE":
			operator = ComparisonOperators.LIKE;
			break;
		default:
			throw new QueryParserException("Operator expected.");
		}

		return operator;
	}
}
