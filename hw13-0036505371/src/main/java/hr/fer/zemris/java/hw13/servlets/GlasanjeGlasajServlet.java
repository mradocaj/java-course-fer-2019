package hr.fer.zemris.java.hw13.servlets;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet koji vrši glasanje.
 * Zapisuje odabrani glas u tekstualnu datoteku.
 * 
 * @author Maja Radočaj
 *
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path filePath = Paths.get(fileName);
		
		if(!Files.exists(filePath)) {
			Files.createFile(filePath);
		}
		
		List<String> lines = Files.readAllLines(filePath);
		
		StringBuilder sb = new StringBuilder();
		boolean edited = false;
		
		int votedId = Integer.parseInt(req.getParameter("id"));
		
		for(String line : lines) {
			if(line.length() == 0) continue;
			String[] parts = line.split("\t");
			if(Integer.parseInt(parts[0]) > votedId && !edited) {
				sb.append(votedId + "\t" + 1 + "\n");
				edited = true;
			}
			if(parts[0].equals(req.getParameter("id"))) {
				sb.append(parts[0] + "\t" + (Integer.parseInt(parts[1]) + 1) + "\n");
				edited = true;
			} else {
				sb.append(line + "\n");
			}
		}
		
		if(!edited) {
			sb.append(votedId + "\t" + 1 + "\n");
		}
	
		FileOutputStream os = new FileOutputStream(fileName, false);
		os.write(sb.toString().getBytes());
		os.close();
		
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
}
