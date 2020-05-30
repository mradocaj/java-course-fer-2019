package hr.fer.zemris.java.p12.servleti;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollEntry;

/**
 * Servlet koji generira rezultate glasanja.
 * 
 * @author Maja Radočaj
 *
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		Long pollId = Long.valueOf(req.getParameter("pollId"));
		List<PollEntry> result = DAOProvider.getDao().getPollList(pollId);
		result.sort((b, o) -> Long.compare(o.getVotes(), b.getVotes()));
		
		List<PollEntry> firstPlace = new ArrayList<>();
		boolean first = true;
		for(PollEntry res : result) {
			if(first) {
				firstPlace.add(res);
				first = false;
				continue;
			}
			if(res.getVotes() == firstPlace.get(0).getVotes()) {
				firstPlace.add(res);
			}
		}
		
		req.setAttribute("result", result);
		req.setAttribute("first", firstPlace);
		req.setAttribute("pollId", pollId);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

}
