package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet za računanje sinusa i kosinusa kuteva iz zadanog raspona.
 * 
 * @author Maja Radočaj
 *
 */
@WebServlet("/trigonometric")
public class TrigonometricServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String aParameter = req.getParameter("a");
		String bParameter = req.getParameter("b");
		
		int a = 0;
		int b = 360;
		
		try {
			a = aParameter == null ? 0 : Integer.parseInt(aParameter);
		} catch(NumberFormatException ignorable) {
		}
		
		try {
			b = bParameter == null ? 360 : Integer.parseInt(bParameter);
		} catch(NumberFormatException ignorable) {
		}
		
		if(a > b) {
			int temp = a;
			a = b;
			b = temp;
		}
		
		if(b > a + 720) {
			b = a + 720;
		}
		
		List<CalculationResult> result = new ArrayList<>();

		for(int i = a; i <= b; i++) {
			double cos = Math.cos(Math.toRadians(i));
			double sin = Math.sin(Math.toRadians(i));
			result.add(new CalculationResult(sin, cos, i));
		}
		
		req.setAttribute("result", result);
		req.setAttribute("a", a);
		req.setAttribute("b", b);
		
		req.getRequestDispatcher("WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}
	
	/**
	 * Statička struktura podataka koja pohranjuje kut, njegov sinus i njegov kosinus.
	 * 
	 * @author Maja Radočaj
	 *
	 */
	public static class CalculationResult {
		/**
		 * Sinus kuta.
		 */
		private double sin;
		/**
		 * Kosinus kuta.
		 */
		private double cos;
		/**
		 * Kut (u stupnjevima).
		 */
		private double angle;
		
		/**
		 * Konstruktor.
		 * 
		 * @param sin sinus kuta
		 * @param cos kosinut kuta 
		 * @param angle kut
		 */
		public CalculationResult(double sin, double cos, double angle) {
			this.sin = sin;
			this.cos = cos;
			this.angle = angle;
		}

		/**
		 * Metoda koja vraća sinus kuta.
		 * 
		 * @return sinus kuta
		 */
		public double getSin() {
			return sin;
		}

		/**
		 * Metoda koja vraća kosinus kuta.
		 * 
		 * @return kosinus kuta
		 */
		public double getCos() {
			return cos;
		}

		/**
		 * Metoda koja vraća kut.
		 * 
		 * @return kut
		 */
		public double getAngle() {
			return angle;
		}
		
	}
}
