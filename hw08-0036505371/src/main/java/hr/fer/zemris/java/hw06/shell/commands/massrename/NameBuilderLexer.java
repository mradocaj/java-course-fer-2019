package hr.fer.zemris.java.hw06.shell.commands.massrename;

import java.util.Objects;

/**
 * Lexer koji tokenizira nizove u svrhu parsiranja u razredu {@link NameBuilderParser}.
 * 
 * @author Maja Radočaj
 *
 */
public class NameBuilderLexer {

	/**
	 * Ulazni podaci u obliku polja charactera.
	 */
	private char[] data;
	/**
	 * Zadnji vraćeni token.
	 */
	private NameBuilderToken token;
	/**
	 * Zadnji obrađeni indeks ulaznih podataka.
	 */
	private int currentIndex;
	
	/**
	 * Konstruktor koji inicijalizira lexer.
	 * Primljeni String ne smije biti <code>null</code>. Ako je, baca se {@link NullPointerException}.
	 * 
	 * @param text ulazni podaci
	 * @throws NullPointerException ako predana vrijednost <code>null</code>
	 */
	public NameBuilderLexer(String text) {
		data = (Objects.requireNonNull(text).toCharArray());
	}
	
	/**
	 * Metoda koja vraća zadnji pročitani token.
	 * 
	 * @return zadnji pročitani token.
	 */
	public NameBuilderToken getToken() {
		return token;
	}
	
	/**
	 * Metoda koja prolazi kroz ulazne podatke od trenutnog indeksa <code>currentIndex</code> te iz njih stvara novi
	 * token.
	 * 
	 * @return sljedeći token
	 * @throws NameBuilderException ako nije moguće stvoriti token
	 */
	public NameBuilderToken nextToken() {
		if(isEnd()) {			
			token = new NameBuilderToken(NameBuilderTokenType.END, "");
			currentIndex++;
			return token;				
		}
		
		if(currentIndex > data.length) {
			throw new NameBuilderException("All tokens have been returned.");
		}
		if(data[currentIndex] == '$' && currentIndex + 1 < data.length && data[currentIndex + 1] == '{') {
			currentIndex = currentIndex + 2;
			getSupstitutionTag();
		} else {
			getString();
		}
		
		return token;
	}
	
	/**
	 * Pomoćna metoda koja vraća novi token u obliku String-a.
	 * Sve to znakova otvaranja novog tag-a se tumači kao tekst.
	 */
	private void getString() {
		StringBuilder sb = new StringBuilder();
		while(currentIndex < data.length) {
			if(data[currentIndex] == '$' && currentIndex + 1 < data.length && data[currentIndex + 1] == '{') break;
			sb.append(data[currentIndex]);
			currentIndex++;
		}
		token = new NameBuilderToken(NameBuilderTokenType.STRING, sb.toString());
	}

	/**
	 * Pomoćna metoda koja vraća novi token u obliku unutrašnjosti tag-a za supstituciju.
	 * 
	 * @throws NameBuilderException ako se ne poštuje sintaksa pisanja unutar tag-a
	 */
	private void getSupstitutionTag() {
		StringBuilder sb = new StringBuilder();
		int commas = 0;
		
		while(true) {
			if(isEnd()) {
				throw new NameBuilderException("Illegal supstitution tag syntax. Tag must end with '}'.");
			}
			if(data[currentIndex] == '}') {
				if(sb.toString().endsWith(",")) {	//iznimka ako tag završava sa zarezom, a ne sa brojkom
					throw new NameBuilderException("Illegal supstitution tag syntax. Tag mustn't end with comma.");
				}
				currentIndex++;
				break;
			}
			if(data[currentIndex] == ',') {
				if(commas >= 1 || sb.length() == 0) {	//iznimka ako tag počinje sa zarezom, a ne sa brojkom
					throw new NameBuilderException("Illegal supstitution tag syntax.");
				}
				sb.append(',');
				currentIndex++;
				commas++;
			} else if(Character.isDigit(data[currentIndex])) {
				sb.append(data[currentIndex]);
				currentIndex++;
			} else {
				throw new NameBuilderException("Illegal supstitution tag syntax. Only digits and one comma allowed.");
			}
		}

		token = new NameBuilderToken(NameBuilderTokenType.TAG, sb.toString());
	}


	/**
	 * Pomoćna metoda koja provjerava jesmo li pročitali sve tokene i došli do kraja ulaznih podataka.
	 * Uz to, uklanja sve početne razmake.
	 * 
	 * @return <code>true</code> ako nema više nikakvih tokena, <code>false</code> ako ima
	 */
	private boolean isEnd() {
		if(currentIndex >= data.length) return true;		
		while(Character.isWhitespace(data[currentIndex])) {
			currentIndex++;
			if(currentIndex == data.length) return true;
		}
		return false;
	}

}
