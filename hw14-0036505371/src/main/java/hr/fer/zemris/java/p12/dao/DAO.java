package hr.fer.zemris.java.p12.dao;

import java.util.List;

import hr.fer.zemris.java.p12.model.PollDescription;
import hr.fer.zemris.java.p12.model.PollEntry;


/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author Maja Radočaj
 *
 */
public interface DAO {	

	/**
	 * Dohvaća podatke za zadani id i pollId. Ako unos ne postoji, vraća <code>null</code>.
	 * 
	 * @param id id unosa
	 * @param pollId id ankete
	 * @return zapis iz tablice sa traženim id-jem i pollId-jem
	 * @throws DAOException u slučaju pogreške
	 */
	public PollEntry getPollEntry(long pollId, long id) throws DAOException;
	
	/**
	 * Dohvaća sve postojeće unose u bazi, i vraća ih u obliku liste.
	 * 
	 * @return listu unosa
	 * @throws DAOException u slučaju pogreške
	 */
	public List<PollEntry> getPollList(long pollId) throws DAOException;

	/**
	 * Dohvaća podatke o anketi za zadani id ankete. Ako unos ne postoji, vraća <code>null</code>.
	 * 
	 * @param id id ankete
	 * @return opis ankete
	 * @throws DAOException u slučaju pogreške
	 */
	public PollDescription getPollDescription(long id) throws DAOException;
}