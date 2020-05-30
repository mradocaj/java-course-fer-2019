package hr.fer.zemris.java.gui.charts;

/**
 * Razred koji modelira vrijednost u grafu za potrebe razreda {@link BarChart}.
 * 
 * @author Maja Radočaj
 *
 */
public class XYValue {

	/**
	 * X vrijednost.
	 */
	private int x;
	/**
	 * Y vrijednost.
	 */
	private int y;

	/**
	 * Javni konstruktor.
	 * 
	 * @param x x vrijednost
	 * @param y y vrijednost
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Metoda koja vraća x vrijednost.
	 * 
	 * @return x vrijednost
	 */
	public int getX() {
		return x;
	}

	/**
	 * Metoda koja vraća y vrijednost.
	 * 
	 * @return y vrijednost
	 */
	public int getY() {
		return y;
	}

}
