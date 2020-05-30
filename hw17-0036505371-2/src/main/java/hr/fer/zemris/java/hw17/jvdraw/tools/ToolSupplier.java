package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.util.function.Supplier;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;

/**
 * Razred koji služi kao dobavljač alata.
 * <p>Metoda <code>get</code> vraća alat koji je korisnik odabrao u razredu {@link JVDraw}.
 * 
 * @author Maja Radočaj
 *
 */
public class ToolSupplier implements Supplier<Tool>{

	/**
	 * Odabrani alat.
	 */
	private Tool tool;
	
	@Override
	public Tool get() {
		return tool;
	}

	/**
	 * Metoda koja postavlja odabrani alat na onaj predani.
	 * 
	 * @param tool predani alat
	 */
	public void setTool(Tool tool) {
		this.tool = tool;
	}

}
