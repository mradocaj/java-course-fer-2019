package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * Singleton koji vraća instancu DAO.
 * 
 * @author Maja Radočaj
 *
 */
public class DAOProvider {

	/**
	 * DAO.
	 */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Getter koji vraća instancu DAO.
	 * 
	 * @return DAO
	 */
	public static DAO getDAO() {
		return dao;
	}
	
}