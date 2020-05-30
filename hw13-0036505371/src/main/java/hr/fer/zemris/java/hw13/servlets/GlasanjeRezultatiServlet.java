package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet koji generira rezultate glasanja.
 * 
 * @author Maja Radočaj
 *
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		List<VoteResult> result = getVoteResults(req);
		
		List<VoteResult> firstPlace = new ArrayList<>();
		boolean first = true;
		for(VoteResult res : result) {
			if(first) {
				firstPlace.add(res);
				first = false;
				continue;
			}
			if(res.getVote() == firstPlace.get(0).getVote()) {
				firstPlace.add(res);
			}
		}
		
		req.setAttribute("result", result);
		req.setAttribute("first", firstPlace);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
	
	/**
	 * Metoda koja vraća listu rezultata glasanja.
	 * 
	 * @param req zahtjev
	 * @return lista rezultata glasanja
	 * @throws IOException ako dođe do greške
	 */
	public static List<VoteResult> getVoteResults(HttpServletRequest req) throws IOException {
		List<VoteResult> result = new ArrayList<>();
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path filePath = Paths.get(fileName);
		
		if(!Files.exists(filePath)) {
			Files.createFile(filePath);
		}
		
		List<String> votesLines = Files.readAllLines(filePath);
		Path definitionPath = Paths.get(req.getServletContext().
				getRealPath("/WEB-INF/glasanje-definicija.txt"));
		List<String> definitionLines = Files.readAllLines(definitionPath);
		
		for(String voteResult : votesLines) {
			String[] voteParts = voteResult.split("\t");
			
			for(String band : definitionLines) {
				String[] resultParts = band.split("\t");
				if(voteParts[0].equals(resultParts[0])) {	//ako imaju isti id
					result.add(new VoteResult(Integer.parseInt(voteParts[0]), 
							Integer.parseInt(voteParts[1]), resultParts[1], resultParts[2]));
					break;
				}
			}
		}
		
		for(String definitionLine : definitionLines) {
			boolean hasVotes = false;
			String[] bandParts = definitionLine.split("\t");
			
			for(int i = 0; i < result.size(); i++) {
				if(bandParts[0].equals(Integer.toString(result.get(i).getId()))) {
					hasVotes = true;
					break;
				}
			}
			if(!hasVotes) {
				result.add(new VoteResult(Integer.parseInt(bandParts[0]), 0, 
						bandParts[1], bandParts[2]));
			}
		}
		
		result.sort((o, b) -> Integer.compare(b.getVote(), o.getVote()));
		return result;
	}
	
	/**
	 * Struktura podataka koja pohranjuje rezultate glasanja.
	 * 
	 * @author Maja Radočaj
	 *
	 */
	public static class VoteResult {
		/**
		 * Identifikacijski broj benda.
		 */
		private int id;
		/**
		 * Broj glasova.
		 */
		private int vote;
		/**
		 * Ime benda.
		 */
		private String name;
		/**
		 * Link pjesme.
		 */
		private String link;

		/**
		 * Javni konstruktor.
		 * 
		 * @param id identifikacijski broj
		 * @param vote broj glasova 
		 * @param name ime benda
		 * @param link link pjesme
		 */
		public VoteResult(int id, int vote, String name, String link) {
			this.id = id;
			this.vote = vote;
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
		 * Getter koji vraća broj glasova.
		 * 
		 * @return broj glasova
		 */
		public int getVote() {
			return vote;
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
