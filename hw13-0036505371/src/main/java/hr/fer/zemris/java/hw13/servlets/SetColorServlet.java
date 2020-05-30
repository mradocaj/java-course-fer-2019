package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet za mijenjanje boje stranica web aplikacije. Boja vrijedi za čitav session.
 * <p>Ako boja nije odabrana, bira se bijela.
 * 
 * @author Maja Radočaj
 *
 */
@WebServlet(name="c", urlPatterns= {"/setcolor"})
public class SetColorServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Defaultna boja.
	 */
	private static final String defaultColor = "white";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String color = req.getParameter("color");
		
		if(color != null) {
			req.getSession().setAttribute("pickedBgCol", color);
		} else {
			req.getSession().setAttribute("pickedBgCol", defaultColor);
		}
		
		req.getRequestDispatcher("colors.jsp").forward(req, resp);
	}
}

