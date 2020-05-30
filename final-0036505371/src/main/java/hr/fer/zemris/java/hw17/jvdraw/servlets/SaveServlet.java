package hr.fer.zemris.java.hw17.jvdraw.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/newFile")
public class SaveServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String fileName = req.getParameter("name");
		if(!fileName.endsWith(".jvd")) {
//			System.out.println("I am here");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}
		char[] parts = fileName.toCharArray();
		for(char letter : parts) {
			if(!Character.isDigit(letter) && !Character.isLetter(letter) && letter != '.') {
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
//				System.out.println("I am here, u messed up: " + letter);
			}
		}
		
		String content = req.getParameter("text");
		
		String file = req.getServletContext().getRealPath("/WEB-INF/images") + "/" + fileName;
		Path path = Paths.get(file);
		
//		System.out.println(path.toString());
		Files.write(path, content.getBytes());
		
		resp.sendRedirect(req.getContextPath() + "/main");
	}
}
