package hr.fer.zemris.java.p12.servleti;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Servlet koji prikazuje mogućnosti odabira anketa.
 * 
 * @author Maja Radočaj
 *
 */
@WebServlet("/servleti/index.html")
public class ServletiIndex extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		ComboPooledDataSource cpds = (ComboPooledDataSource) req.
				getServletContext().getAttribute("hr.fer.zemris.dbpool");
		
		try {
			Connection connection = cpds.getConnection();
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM POLLS");
			ResultSet res = ps.executeQuery();
			
			StringBuilder sb = new StringBuilder();
			sb.append("<html><head><title>Home</title></head>");
			sb.append("<body><h1>Ankete</h1>");
			sb.append("<ul>");
			
			while(res.next()) {
				sb.append("<li><a href=\"glasanje?pollID=" 
						+  res.getLong(1) + "\">" + res.getString(2) + " ovdje!</a></li>");
			}
			sb.append("</ul></body></html>");
			
			resp.getOutputStream().write(sb.toString().getBytes());
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
}
