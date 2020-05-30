package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Singleton koji vraća instancu {@link EntityManagerFactory}.
 * 
 * @author Maja Radočaj
 *
 */
public class JPAEMFProvider {

	/**
	 * Instanca EntityManagerFactory.
	 */
	public static EntityManagerFactory emf;
	
	/**
	 * Getter za emf.
	 * 
	 * @return emf
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * Setter za emf.
	 * 
	 * @param emf emf
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}