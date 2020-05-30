package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

/**
 * Servlet koji generira sliku koja prikazuje graf sa informacijama o korištenju operacijskih
 * sustava.
 * 
 * @author Maja Radočaj
 *
 */
@WebServlet("/reportImage")
public class OSUsageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		resp.setContentType("image/png");
		
		OutputStream os = resp.getOutputStream();
		
		JFreeChart chart = getPiChart();
		int width = 500;
		int height = 500;
		ChartUtilities.writeChartAsPNG(os, chart, width, height);
	}

	/**
	 * Pomoćna metoda koja generira graf.
	 * 
	 * @return graf
	 */
	private JFreeChart getPiChart() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Linux", 29);
		dataset.setValue("Mac", 20);
		dataset.setValue("Windows", 51);
        String title = "Which operating system are you using?";
        
		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;
	}
}
