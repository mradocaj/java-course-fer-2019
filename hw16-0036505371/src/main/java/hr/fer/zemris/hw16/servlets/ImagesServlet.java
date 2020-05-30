package hr.fer.zemris.hw16.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet koji vraća novu sliku.
 * Slika može biti ikona ili slika pune veličine.
 * 
 * @author Maja Radočaj
 *
 */
@WebServlet("/slike")
public class ImagesServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		resp.setContentType("image/jpg");
		OutputStream os = resp.getOutputStream();
		String fileName = req.getParameter("fileName");
		String thumbnail = req.getParameter("thumbnail");

		if(thumbnail == null || fileName == null) {
			return;
		}

		Path path = null;
		if(thumbnail.equals("true")) {
			path = Paths.get(req.getServletContext().getRealPath("/WEB-INF/thumbnails"), fileName);
		} else {
			path = Paths.get(req.getServletContext().getRealPath("/WEB-INF/slike"), fileName);
		}

		BufferedImage image = ImageIO.read(Files.newInputStream(path));
		ImageIO.write(image, "jpg", os);
		os.close();
	}
}
