package hr.fer.zemris.java.p12.servleti;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollDescription;
import hr.fer.zemris.java.p12.model.PollEntry;

/**
 * Servlet za glasanje. 
 * <p>Iz definirane liste bendova i pjesama stvara stranicu koja omogućuje glasanje.
 * 
 * @author Maja Radočaj
 *
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		long pollId = Long.valueOf(req.getParameter("pollID"));
		List<PollEntry> pollList = DAOProvider.getDao().getPollList(pollId);
		PollDescription poll = DAOProvider.getDao().getPollDescription(pollId);

		req.setAttribute("pollList", pollList);
		req.removeAttribute("pollDescription");
		req.setAttribute("pollDescription", poll);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

}
