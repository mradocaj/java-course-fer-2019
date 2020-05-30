package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.util.CommentForm;
import hr.fer.zemris.java.tecaj_13.util.EntryForm;

/**
 * Stranica za prikaz zapisa autora.
 * 
 * @author Maja Radočaj
 *
 */
@WebServlet("/servleti/author/*")
public class BlogEntriesPage extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String path = req.getPathInfo();
		if(path == null || path.equals("/")) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		String[] parts = path.substring(1).split("/");
		List<BlogUser> users = DAOProvider.getDAO().getBlogUsers();
		
		boolean userFound = false;
		for(BlogUser user : users) {
			if(user.getNick().equals(parts[0])) {
				userFound = true;
			}
		}
		
		if(!userFound) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		String currentNick = (String) req.getSession().getAttribute("current.user.nick");
		boolean providedNick = false;
		if(currentNick != null && currentNick.equals(parts[0])) {
			providedNick = true;
		}
		
		if(parts.length == 1) {
			BlogUser creator = DAOProvider.getDAO().getBlogUser(parts[0]);
			List<BlogEntry> entries = (List<BlogEntry>) creator.getEntries();
			
			req.setAttribute("entries", entries);
			req.setAttribute("providedNick", providedNick);
			req.getSession().setAttribute("nick", parts[0]);
			req.getRequestDispatcher("/WEB-INF/pages/blogEntries.jsp").forward(req, resp);

		} else if(parts.length == 2) {
			if(parts[1].equals("new")) {
				newPage(req, resp, parts, providedNick, "new");
				
			} else if(parts[1].equals("edit")) {
				newPage(req, resp, parts, providedNick, "edit");
				
			} else {
				viewEntry(req, resp, parts, providedNick);
			}
			
		} else {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		String method = req.getParameter("method");
		String[] parts = req.getPathInfo().substring(1).split("/");
		String atr = parts[parts.length - 1];
		String nick = parts[0];
		
		if("Odustani".equals(method)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/"
					+ req.getSession().getAttribute("current.user.nick"));
			return;
			
		} else if("Dodaj komentar".equals(method)) {
			try {
				editEntry(req, resp, atr);
				return;
				
			} catch(NumberFormatException ex) {
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
		}
		
		EntryForm form = new EntryForm();
		form.fillFromRequest(req);
		form.validate();
		
		if(form.hasError()) {
			req.setAttribute("form", form);
			req.getRequestDispatcher("/WEB-INF/pages/editEntry.jsp").forward(req, resp);
			return;
		}
		
		BlogEntry entry = new BlogEntry();
		form.fillEntry(entry);
		
		if(atr.equals("new")) {
			entry.setCreatedAt(new Date());
			entry.setLastModifiedAt(new Date());

			BlogUser creator = DAOProvider.getDAO().getBlogUser(nick);
			entry.setCreator(creator);
			
			DAOProvider.getDAO().saveBlogEntry(entry);
			
		} else if(atr.equals("edit")) {
			Long id = null;
			try{
				id = Long.parseLong(req.getParameter("id"));
			} catch(Exception ex) {
			}
			
			if(id == null) {
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			
			BlogEntry existingEntry = DAOProvider.getDAO().getBlogEntry(id);
			existingEntry.setLastModifiedAt(new Date());
			existingEntry.setText(entry.getText());
			existingEntry.setTitle(entry.getTitle());
		
			entry = DAOProvider.getDAO().editBlogEntry(existingEntry);
			
		} else {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		resp.sendRedirect(req.getContextPath() + "/servleti/author/" + nick);
	}
	
	/**
	 * Pomoćna metoda za prikaz stranice za novi zapis ili uređivanje postojećeg 
	 * zapisa.
	 * 
	 * @param req zahtjev
	 * @param resp odgovor
	 * @param parts dijelovi patha
	 * @param providedNick nadimak
	 * @param type tip
	 * @throws ServletException u slučaju greške
	 * @throws IOException u slučaju greške
	 */
	private void newPage(HttpServletRequest req, HttpServletResponse resp, String[] parts, 
			boolean providedNick, String type) throws ServletException, IOException {
		
		if(!providedNick) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		EntryForm form = req.getAttribute("form") == null ? new EntryForm() 
				: (EntryForm) req.getAttribute("form");
		
		if(type.equals("edit")) {
			try {
				Long id = Long.parseLong(req.getParameter("id"));
				BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
				BlogUser user = DAOProvider.getDAO().getBlogUser(parts[0]);
				
				if(entry == null || user == null || !entry.getCreator().equals(user)) {
					req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
					return;
				}
				
				form.fillFromEntry(entry);
				req.getSession().setAttribute("atr", "edit");
				req.getSession().setAttribute("title", "Uredi");
				req.getSession().setAttribute("id", id);
				
			} catch(NumberFormatException ex) {
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
		} else {
			req.getSession().setAttribute("atr", "new");
			req.getSession().setAttribute("title", "Novi");
		}
		
		req.setAttribute("form", form);
		req.setAttribute("nick", parts[0]);

		req.getRequestDispatcher("/WEB-INF/pages/editEntry.jsp").forward(req, resp);
	}

	/**
	 * Pomoćna metoda za prikaz stranice za pregled zapisa i komentara.
	 * 
	 *  @param req zahtjev
	 * @param resp odgovor
	 * @param parts dijelovi patha
	 * @param providedNick nadimak
	 * @throws ServletException u slučaju greške
	 * @throws IOException u slučaju greške
	 */
	private void viewEntry(HttpServletRequest req, HttpServletResponse resp, 
			String[] parts, boolean providedNick) throws ServletException, IOException {
		
		try {
			Long id = Long.parseLong(parts[parts.length - 1]);
			BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
			BlogUser user = DAOProvider.getDAO().getBlogUser(parts[0]);
			
			if(entry == null || user == null || !entry.getCreator().equals(user)) {
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
				return;
			}
			
			List<BlogComment> comments = entry.getComments();
			req.setAttribute("entry", entry);
			req.setAttribute("nick", parts[0]);
			req.setAttribute("atr", providedNick);
			req.setAttribute("comments", comments);
			
			Long atrId = (Long) req.getSession().getAttribute("id");
			if(atrId == id) {
				req.setAttribute("form", (CommentForm) req.getSession().getAttribute("form"));
			} else {
				req.getSession().removeAttribute("form");
			}
			req.getRequestDispatcher("/WEB-INF/pages/showEntry.jsp").forward(req, resp);
			
		} catch(NumberFormatException ex) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}
		
	}
	
	/** 
	 * Metoda za uređivanje postojećeg zapisa.
	 * 
	 * @param req zahtjev
	 * @param resp odgovor
	 * @param atr atribut
	 * @throws ServletException u slučaju greške
	 * @throws IOException u slučaju greške
	 */
	private void editEntry(HttpServletRequest req, HttpServletResponse resp, String atr) 
		throws ServletException, IOException {
		
		Long id = Long.parseLong(atr);
		BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(id);
		
		if(blogEntry == null) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}
		
		CommentForm form = new CommentForm();
		form.fillFromRequest(req);
		if(req.getParameter("email") == null) {
			BlogUser user = DAOProvider.getDAO().getBlogUser(
							(String) req.getSession().getAttribute("current.user.nick"));
			form.setEmail(user.getEmail());
		}
		form.validate();
		
		if(form.hasError()) {
			req.setAttribute("form", form);
			req.getSession().setAttribute("form", form);
			req.getSession().setAttribute("id", id);
			resp.sendRedirect(req.getServletContext().getContextPath() 
					+ req.getServletPath() + req.getPathInfo());
			return;
		}
		
		BlogComment comment = new BlogComment();
		form.fillComment(comment);
		
		comment.setBlogEntry(blogEntry);
		comment.setPostedOn(new Date());
		DAOProvider.getDAO().addBlogComment(blogEntry, comment);
		resp.sendRedirect(req.getServletContext().getContextPath() 
				+ req.getServletPath() + req.getPathInfo());
	}
}
