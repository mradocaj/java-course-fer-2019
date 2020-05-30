package hr.fer.zemris.java.p12.servleti;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

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

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollEntry;

/**
 * Servlet koji grafički prikazuje rezultate glasanja.
 * 
 * @author Maja Radočaj
 *
 */
@WebServlet("/servleti/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		resp.setContentType("image/png");
		
		Long pollId = Long.valueOf(req.getParameter("pollId"));
		OutputStream os = resp.getOutputStream();
		List<PollEntry> results = DAOProvider.getDao().getPollList(pollId);

		JFreeChart chart = getPiChart(results);
		int width = 500;
		int height = 500;
		ChartUtilities.writeChartAsPNG(os, chart, width, height);
	}

	/**
	 * Metoda koja vraća graf.
	 * 
	 * @param results rezultati glasovanja
	 * @return grafički prikaz rezultata glasovanja
	 */
	private JFreeChart getPiChart(List<PollEntry> results) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		for(int i = 0; i < results.size(); i++) {
			dataset.setValue(results.get(i).getTitle(), results.get(i).getVotes());
		}
		
		String title = "Rezultati glasanja";

		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.9f);
		return chart;
	}
}
