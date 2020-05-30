package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Razred koji modelira stupčasti dijagram.
 * 
 * @author Maja Radočaj
 *
 */
public class BarChart {

	/**
	 * Vrijednost unutar dijagrama.
	 */
	private List<XYValue> values;
	/**
	 * Naziv x osi dijagrama.
	 */
	private String xAxisName;
	/**
	 * Naziv y osi dijagrama.
	 */
	private String yAxisName;
	/**
	 * Najmanji y.
	 */
	private int minY;
	/**
	 * Najveći y.
	 */
	private int maxY;
	/**
	 * Razmak među y vrijednostima.
	 */
	private int spacing;

	/**
	 * Javni konstruktor.
	 * 
	 * @param values  vrijednosti unurar dijagrama
	 * @param xAxis   naziv x osi
	 * @param yAxis   naziv y osi
	 * @param minY    najmanji y
	 * @param maxY    najveći y
	 * @param spacing razmak među y vrijednostima
	 * @throws IllegalArgumentException ako je minY < 0, maxY <= minY ili jedna od
	 *                                  vrijednosti manja od minY
	 */
	public BarChart(List<XYValue> values, String xAxis, String yAxis, int minY, int maxY, int spacing) {
		if(minY < 0) {
			throw new IllegalArgumentException("Minimum y value must be zero or greater.");
		}
		if(maxY <= minY) {
			throw new IllegalArgumentException("Maximum y value must be greater than minimum y value.");
		}
		for(XYValue value : values) {
			if(value.getY() < minY) {
				throw new IllegalArgumentException("List of values contains values "
						+ "that are not in range.");
			}
		}
		this.values = values;
		this.xAxisName = xAxis;
		this.yAxisName = yAxis;
		this.minY = minY;
		this.maxY = maxY;
		this.spacing = spacing;
	}

	/**
	 * Getter za vrijednosti.
	 * 
	 * @return vrijednosti
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * Getter za naziv x osi.
	 * 
	 * @return naziv x osi
	 */
	public String getxAxisName() {
		return xAxisName;
	}

	/**
	 * Getter za naziv y osi.
	 * 
	 * @return naziv y osi
	 */
	public String getyAxisName() {
		return yAxisName;
	}

	/**
	 * Getter za najmanji y.
	 * 
	 * @return najmanji y
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * Getter za najveći y.
	 * 
	 * @return najveći y
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * Getter za razmake među y
	 * 
	 * @return razmak među y
	 */
	public int getSpacing() {
		return spacing;
	}

}
