package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Implementacija DAO.
 * 
 * @author Maja Radočaj
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public BlogUser getBlogUser(String nick) throws DAOException {
		List<BlogUser> blogUsers = JPAEMProvider.getEntityManager().createQuery("select u "
				+ "from BlogUser as u where u.nick=:ni", BlogUser.class)
				.setParameter("ni", nick)
				.getResultList();
		
		return blogUsers.isEmpty() ? null : blogUsers.get(0);
	}

	@Override
	public List<BlogUser> getBlogUsers() throws DAOException {
		List<BlogUser> blogUsers = JPAEMProvider.getEntityManager().createQuery("select u "
				+ "from BlogUser as u", BlogUser.class)
				.getResultList();
		
		return blogUsers;
	}

	@Override
	public BlogUser registerBlogUser(BlogUser user) throws DAOException {
		EntityManagerFactory emf = JPAEMFProvider.getEmf();
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		em.persist(user);
		
		em.getTransaction().commit();
		em.close();
		
		return user;
	}

	@Override
	public List<BlogEntry> getBlogEntries(BlogUser creator) throws DAOException {
		List<BlogEntry> blogEntries = JPAEMProvider.getEntityManager().createQuery("select b "
				+ "from BlogEntry as b where b.creator=:cr", BlogEntry.class)
				.setParameter("cr", creator)
				.getResultList();
		
		return blogEntries;
	}

	@Override
	public BlogEntry saveBlogEntry(BlogEntry entry) throws DAOException {
		EntityManagerFactory emf = JPAEMFProvider.getEmf();
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		em.persist(entry);
		
		em.getTransaction().commit();
		em.close();
		
		return entry;
	}

	@Override
	public BlogEntry editBlogEntry(BlogEntry entry) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		return em.merge(entry);
	}
	
	@Override
	public void addBlogComment(BlogEntry blogEntry, BlogComment blogComment) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		em.persist(blogComment);

		blogEntry.getComments().add(blogComment);
	}

}