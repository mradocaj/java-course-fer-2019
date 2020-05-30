package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Razred za demonstraciju rada {@link BarChartComponent}. Program se pokreće uz
 * jedan argument naredbenog retka - put do datoteke u kojoj je dana definicija
 * stupčastog dijagrama.
 * 
 * @author Maja Radočaj
 *
 */
public class BarChartDemo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Stupčasti dijagram.
	 */
	private BarChart barChart;
	/**
	 * Putanja do datoteke.
	 */
	private String fileName;

	/**
	 * Konstruktor.
	 * 
	 * @param barChart dijagram
	 * @param fileName naziv datoteke
	 */
	public BarChartDemo(BarChart barChart, String fileName) {
		super();
		this.barChart = barChart;
		this.fileName = fileName;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("BarChartDemo");
		setLocation(20, 20);
		setSize(600, 500);
		initGUI();
	}

	/**
	 * Metoda za inicijalizaciju grafičkog sučelja.
	 */
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());
		JComponent chart = new BarChartComponent(barChart);
		chart.setLocation(20, 20);

		add(chart, BorderLayout.CENTER);
		JLabel label = new JLabel(fileName, SwingConstants.CENTER);
		add(label, BorderLayout.PAGE_START);
	}

	/**
	 * Glavni program.
	 * 
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Path to file expected as argument.");
			return;
		}

		try {
			Path path = Paths.get(args[0]);
			List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
			BarChart barChart = getBarChart(lines);

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					BarChartDemo prozor = new BarChartDemo(barChart, path.toString());
					prozor.setVisible(true);
				}
			});
		} catch(IOException ex) {
			System.out.println("Cannot read from file.");
			return;
		} catch(IndexOutOfBoundsException ex) {
			System.out.println("File doesn't have expected number of lines.");
			return;
		} catch(NumberFormatException ex) {
			System.out.println("Invalid values; integer numbers expected.");
			return;
		}
	}

	/**
	 * Metoda koja stvara primjerak razreda {@link BarChart} na temelju liste
	 * linija.
	 * 
	 * @param lines lista Stringova
	 * @return primjerak razreda {@link BarChart}
	 * @throws IndexOutOfBoundsException ako nema dovoljno linija
	 * @throws NumberFormatException     ako se nešto ne može parsirati
	 */
	private static BarChart getBarChart(List<String> lines) {
		String xAxis = lines.get(0);
		String yAxis = lines.get(1);
		String[] parts = lines.get(2).split(" ");

		List<XYValue> values = new ArrayList<>();
		for(String part : parts) {
			String[] numbers = part.split(",");
			if(numbers.length != 2) {
				System.out.println("Wrong format of file.");
				System.exit(-1);
			}
			values.add(new XYValue(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1])));
		}
		int minY = Integer.parseInt(lines.get(3));
		int maxY = Integer.parseInt(lines.get(4));
		int spacing = Integer.parseInt(lines.get(5));

		return new BarChart(values, xAxis, yAxis, minY, maxY, spacing);
	}
}
