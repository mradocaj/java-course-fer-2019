package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.util.LoginForm;

/**
 * Početna stranica aplikacije.
 * 
 * @author Maja Radočaj
 *
 */
@WebServlet("/servleti/main")
public class MainPage extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		LoginForm form = new LoginForm();
		
		List<BlogUser> users = DAOProvider.getDAO().getBlogUsers();
		req.setAttribute("form", form);
		req.getSession().setAttribute("users", users);
		
		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		String method = req.getParameter("method");
		if(!"Prijava".equals(method)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
			return;
		}
		
		LoginForm form = new LoginForm();
		form.fillFromRequest(req);
		form.validate();
		
		if(form.hasError()) {
			form.setPasswordHash("");
			req.setAttribute("form", form);
			req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
			return;
		}

		BlogUser user = new BlogUser();
		form.fillUser(user);
		
		BlogUser existingUser = DAOProvider.getDAO().getBlogUser(user.getNick());
		
		if(existingUser == null) {
			form.setPasswordHash("");
			form.setError("nick", "Nadimak " + user.getNick() + " ne postoji!");
			req.setAttribute("form", form);
			req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
			
		} else if(!existingUser.getPasswordHash().equals(user.getPasswordHash())) {
			form.setPasswordHash("");
			form.setError("password", "Neispravna kombinacija lozinke i nadimka!");
			req.setAttribute("form", form);
			req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
			
		} else {
			req.getSession().setAttribute("current.user.id", existingUser.getId());
			req.getSession().setAttribute("current.user.fn", existingUser.getFirstName());
			req.getSession().setAttribute("current.user.ln", existingUser.getLastName());
			req.getSession().setAttribute("current.user.nick", existingUser.getNick());
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
		}
	}
}
