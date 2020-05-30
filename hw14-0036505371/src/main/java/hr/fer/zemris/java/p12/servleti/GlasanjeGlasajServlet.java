package hr.fer.zemris.java.p12.servleti;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.dao.sql.SQLConnectionProvider;
import hr.fer.zemris.java.p12.model.PollEntry;

/**
 * Servlet koji vrši glasanje.
 * Zapisuje odabrani glas u tekstualnu datoteku.
 * 
 * @author Maja Radočaj
 *
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		Long id = Long.valueOf(req.getParameter("id"));
		Long pollId = Long.valueOf(req.getParameter("pollId"));

		PollEntry entry = DAOProvider.getDao().getPollEntry(pollId, id);
		
		Connection con = SQLConnectionProvider.getConnection();
		
		try{
			PreparedStatement ps = con.prepareStatement("UPDATE POLLOPTIONS SET votesCount=? "
					+ "WHERE id=? AND POLLID=?");
			ps.setLong(1, entry.getVotes() + 1);
			ps.setLong(2, id);
			ps.setLong(3, pollId);
			
			int rowsAffected = ps.executeUpdate();
			
			if(rowsAffected != 1) {
				throw new SQLException("Couldn't register vote.");
			}
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
				
		
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollId=" + pollId);
	}
	
	
}
