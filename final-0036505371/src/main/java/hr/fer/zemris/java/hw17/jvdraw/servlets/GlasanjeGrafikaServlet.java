package hr.fer.zemris.java.hw17.jvdraw.servlets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;
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

import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectPainter;

/**
 * Servlet koji grafički prikazuje rezultate glasanja.
 * 
 * @author Maja Radočaj
 *
 */
@WebServlet("/crtaj")
public class GlasanjeGrafikaServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		resp.setContentType("image/png");
		
		List<GeometricalObject> objects = (List<GeometricalObject>) req.getSession().getAttribute("objects");

		GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
		for(GeometricalObject object : objects) {
			object.accept(bbcalc);
		}
		
		Rectangle box = bbcalc.getBoundingBox();
		BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		
		g.translate(-box.x, -box.y);
		GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
		for(GeometricalObject object : objects) {
			object.accept(painter);
		}
		
		OutputStream os = resp.getOutputStream();
		ImageIO.write(image, "png", os);
		
		g.dispose();
		
		
	}


}
