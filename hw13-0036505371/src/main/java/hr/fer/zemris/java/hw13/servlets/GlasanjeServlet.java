package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet za glasanje. 
 * <p>Iz definirane liste bendova i pjesama stvara stranicu koja omogućuje glasanje.
 * 
 * @author Maja Radočaj
 *
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> bandLines = Files.readAllLines(Paths.get(fileName));
		List<Band> bands = new ArrayList<>();

		for(String band : bandLines) {
			String[] parts = band.split("\t");
			bands.add(new Band(Integer.parseInt(parts[0]), parts[1], parts[2]));
		}

		bands.sort((b, o) -> Integer.compare(b.getId(), o.getId())); // sortiranje po id-u
		req.setAttribute("bands", bands);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

	/**
	 * Struktura podataka koja pohranjuje informacije o bendu.
	 * 
	 * @author Maja Radočaj
	 *
	 */
	public static class Band {
		/**
		 * Identifikacijski broj benda.
		 */
		private int id;
		/**
		 * Ime benda.
		 */
		private String name;
		/**
		 * Link na pjesmu benda.
		 */
		private String link;

		/**
		 * Javni konstruktor.
		 * 
		 * @param id   identifikacijski broj benda
		 * @param name ime benda
		 * @param link link na pjesmu
		 */
		public Band(int id, String name, String link) {
			this.id = id;
			this.name = name;
			this.link = link;
		}

		/**
		 * Getter koji vraća identifikacijski broj benda.
		 * 
		 * @return identifikacijski broj benda
		 */
		public int getId() {
			return id;
		}

		/**
		 * Getter koji vraća ime benda.
		 * 
		 * @return ime benda
		 */
		public String getName() {
			return name;
		}

		/**
		 * Getter koji vraća link pjesme benda.
		 * 
		 * @return link pjesme benda
		 */
		public String getLink() {
			return link;
		}

	}
}
