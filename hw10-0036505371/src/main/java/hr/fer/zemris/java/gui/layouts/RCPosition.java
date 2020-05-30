package hr.fer.zemris.java.gui.layouts;

/**
 * Razred koji modelira poziciju komponente. Svaka pozicija je definirana sa
 * brojem retka i stupca.
 * 
 * @author Maja Radočaj
 *
 */
public class RCPosition {

	/**
	 * Broj retka.
	 */
	private int row;
	/**
	 * Broj stupca.
	 */
	private int column;

	/**
	 * Konstruktor koji inicijalizira broj retka i stupca.
	 * 
	 * @param row    broj retka
	 * @param column broj stupca
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Getter koji vraća broj retka.
	 * 
	 * @return broj retka
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Getter koji vraća broj stupca.
	 * 
	 * @return broj stupca
	 */
	public int getColumn() {
		return column;
	}

}
