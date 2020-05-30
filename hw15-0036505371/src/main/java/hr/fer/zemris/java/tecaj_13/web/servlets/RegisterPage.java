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
import hr.fer.zemris.java.tecaj_13.util.RegisterForm;

/**
 * Stranica za  registriranje novog korisnika.
 * 
 * @author Maja Radočaj
 *
 */
@WebServlet("/servleti/register")
public class RegisterPage extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		RegisterForm form = new RegisterForm();
		req.setAttribute("form", form);
		
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		
		String method = req.getParameter("method");
		if(!"Registriraj se".equals(method)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
			return;
		}
		
		RegisterForm form = new RegisterForm();
		form.fillFromRequest(req);
		form.validate();
		
		if(form.hasError()) {
			form.setPasswordHash("");
			req.setAttribute("form", form);
			req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
			return;
		}
		
		BlogUser user = new BlogUser();
		form.fillUser(user);
		
		List<BlogUser> users = DAOProvider.getDAO().getBlogUsers();
		
		for(BlogUser current : users) {
			if(current.getNick().equals(user.getNick())) {
				form.setPasswordHash("");
				form.setError("nick", "Nadimak " + user.getNick() + " je zauzet.");
				req.setAttribute("form", form);
				req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
				return;
			}
		}
		
		user = DAOProvider.getDAO().registerBlogUser(user);
		
		req.getSession().setAttribute("current.user.id", user.getId());
		req.getSession().setAttribute("current.user.fn", user.getFirstName());
		req.getSession().setAttribute("current.user.ln", user.getLastName());
		req.getSession().setAttribute("current.user.nick", user.getNick());
		resp.sendRedirect(req.getContextPath() + "/servleti/main");
	}
}
