package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Razred za pohranu elementa koji su u obliku funkcija.
 * Nasljeđuje razred Element te nadjačava njegvu jedinu metodu - asText.
 * Pohranjuje vrijednosti tokena pri parsiranju u razredu {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser}.
 * 
 * @author Maja Radočaj
 *
 */
public class ElementFunction extends Element {

	/**
	 * Ime funkcije.
	 */
	private String name;

	/**
	 * Javni konstruktor razreda.
	 * Inicijalizira vrijednost članske varijable name.
	 * 
	 * @param name predana vrijednost
	 */
	public ElementFunction(String name) {
		this.name = name;
	}

	/**
	 * Getter koji vraća naziv funkcije.
	 * 
	 * @return naziv funkcije
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String asText() {
		return name;
	}
}
