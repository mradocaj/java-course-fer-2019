package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

/**
 * Komponenta koja iscrtava predani stupčasti dijagram.
 * 
 * @author Maja Radočaj
 *
 */
public class BarChartComponent extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Predani stupčasti dijagram.
	 */
	private BarChart barChart;
	/**
	 * Odmak od lijevog ruba prozora.
	 */
	private static final int OFFSET = 20;
	/**
	 * Odmak od donjeg ruba prozora.
	 */
	private static final int BOTTOM_OFFSET = 60;
	/**
	 * Odmak za brojeve.
	 */
	private static final int NUMBER_OFFSET = 15;
	/**
	 * Duljina linije za oznaku brojeva na osi.
	 */
	private static final int LINE = 5;
	/**
	 * Poravnanje.
	 */
	private static final int ALIGNMENT = 10;

	/**
	 * Javni konstruktor koji prima dijagram koji treba iscrtati.
	 * 
	 * @param barChart stupčasti dijagram
	 */
	public BarChartComponent(BarChart barChart) {
		super();
		this.barChart = barChart;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Dimension dim = getSize();

		Graphics2D g2d = (Graphics2D) g;
		FontMetrics fm = g2d.getFontMetrics();

		int maxY = barChart.getMaxY();
		int numOfDigits = 0;
		while(maxY > 0) {
			numOfDigits++;
			maxY = maxY / 10;
		}

		int x1 = fm.getAscent() + OFFSET + numOfDigits * NUMBER_OFFSET;
		int x2 = dim.width - OFFSET;
		int y1 = OFFSET;
		int y2 = dim.height - fm.getAscent() - BOTTOM_OFFSET;

		drawAxes(g2d, dim, x1, x2, y1, y2);
		drawArrows(g2d, x1, x2, y1, y2);
		drawChart(g2d, x1, x2, y1, y2, numOfDigits, fm, dim);
	}

	/**
	 * Metoda za crtanje koordinatnih osi.
	 * 
	 * @param g2d objekt za grafiku
	 * @param dim dimenzije prozora
	 * @param x1  početni x
	 * @param x2  završni x
	 * @param y1  početni y
	 * @param y2  završni y
	 */
	private void drawAxes(Graphics2D g2d, Dimension dim, int x1, int x2, int y1, int y2) {
		FontMetrics fm = g2d.getFontMetrics();
		AffineTransform old = g2d.getTransform();
		AffineTransform at = new AffineTransform();

		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);
		g2d.setFont(new Font("Arial", Font.PLAIN, 18));
		g2d.drawString(barChart.getyAxisName() // ispis naziva y osi
				, -dim.height / 2 - fm.stringWidth(barChart.getyAxisName()), OFFSET);
		g2d.setTransform(old);

		g2d.drawLine(x1, y1 - LINE, x1, y2); // crtanje y osi
		g2d.drawLine(x1, y2, x2, y2); // crtanje x osi
	}

	/**
	 * Metoda za crtanje strelica na koordinatnim osima.
	 * 
	 * @param g2d objekt za grafiku
	 * @param dim dimenzije prozora
	 * @param x1  početni x
	 * @param x2  završni x
	 * @param y1  početni y
	 * @param y2  završni y
	 */
	private void drawArrows(Graphics2D g2d, int x1, int x2, int y1, int y2) {
		Polygon arrowHead = new Polygon();
		arrowHead.addPoint(x1, y1 - LINE * 3);
		arrowHead.addPoint(x1 - LINE, y1 - LINE); // dodavanje strelice na y os
		arrowHead.addPoint(x1 + LINE, y1 - LINE);
		g2d.fill(arrowHead);

		arrowHead = new Polygon();
		arrowHead.addPoint(x2 + LINE * 2, y2); // dodavanje strelice na x os
		arrowHead.addPoint(x2, y2 - LINE);
		arrowHead.addPoint(x2, y2 + LINE);
		g2d.fill(arrowHead);
	}

	/**
	 * Metoda za crtanje grafa.
	 * 
	 * @param g2d         objekt za grafiku
	 * @param dim         dimenzije prozora
	 * @param x1          početni x
	 * @param x2          završni x
	 * @param y1          početni y
	 * @param y2          završni y
	 * @param fm          karakteristike fonta
	 * @param numOfDigits broj znamenki najvećeg zadanog broja
	 */
	private void drawChart(Graphics2D g2d, int x1, int x2, int y1, int y2, 
			int numOfDigits, FontMetrics fm, Dimension dim) {

		int yLineLength = y2 - y1;
		int y = barChart.getMinY();
		int spacing = barChart.getSpacing();
		int oneRowHeight = yLineLength / (barChart.getMaxY() / spacing);

		for(int i = y2; i >= y1; i -= oneRowHeight) { // dodavanje oznaka na y osi
			g2d.drawLine(x1 - LINE, i, x1, i);
			int width = y;
			int alignment = numOfDigits;
			while(width > 0) {
				alignment--;
				width = width / 10;
			}

			if(y == 0) {
				alignment = numOfDigits - 1;
			}

			alignment *= ALIGNMENT;

			g2d.setFont(new Font("Arial", Font.BOLD, 17));
			g2d.drawString(Integer.toString(y), fm.getAscent() + OFFSET + alignment, i + LINE + 2);

			y += spacing;
		}

		List<XYValue> values = barChart.getValues(); // dodavanje oznaka na x osi
		int xLineWidth = x2 - x1;
		int oneColumnWidth = xLineWidth / values.size();

		values.sort((v1, v2) -> v1.getX() > v2.getX() ? 1 : -1);
		for(int i = x1; i <= x2; i += oneColumnWidth) {
			g2d.drawLine(i, y2 + LINE, i, y2);
		}
		int x = 0;
		for(int i = x1 + oneColumnWidth / 2; i <= x2; i += oneColumnWidth) {
			g2d.drawString(Integer.toString(values.get(x).getX()), i, y2 + OFFSET);
			x++;
		}

		g2d.setFont(new Font("Arial", Font.PLAIN, 16));
		g2d.drawString(barChart.getxAxisName(), 
				dim.width / 2 - fm.stringWidth(barChart.getxAxisName()) / 2,
				dim.height - fm.getAscent());

		x = 0;
		for(int i = x1; x < values.size(); i += oneColumnWidth) { // crtanje grafa
			int yValue = values.get(x).getY();
			int yCoordinate = y2 - yValue * oneRowHeight / spacing;
			Rectangle rect = new Rectangle(i, yCoordinate, oneColumnWidth, y2 - yCoordinate);
			g2d.setColor(new Color(255, 102, 102));
			g2d.fill(rect);
			g2d.setColor(Color.BLACK);
			g2d.drawRect(i, yCoordinate, oneColumnWidth, y2 - yCoordinate);
			x++;
		}
	}
}
