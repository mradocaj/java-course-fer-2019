package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Razred za pohranu elementa u obliku varijabli.
 * Nasljeđuje razred Element te nadjačava njegvu jedinu metodu - asText.
 * Pohranjuje vrijednosti tokena pri parsiranju u razredu {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser}.
 * 
 * @author Maja Radočaj
 *
 */
public class ElementVariable extends Element {

	/**
	 * Ime varijable.
	 */
	private String name;

	/**
	 * Javni konstruktor koji inicijalizira ime varijable.
	 * 
	 * @param name ime varijable
	 */
	public ElementVariable(String name) {
		this.name = name;
	}

	/**
	 * Getter koji vraća ime varijable.
	 * 
	 * @return ime varijable
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String asText() {
		return name;
	}
}
