package hr.fer.zemris.java.p12.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.p12.dao.DAO;
//import hr.fer.zemris.java.p12.dao.DAOException;
////import hr.fer.zemris.java.p12.model.Unos;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.List;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.PollDescription;
import hr.fer.zemris.java.p12.model.PollEntry;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova konkretna
 * implementacija očekuje da joj veza stoji na raspolaganju preko
 * {@link SQLConnectionProvider} razreda, što znači da bi netko prije no što
 * izvođenje dođe do ove točke to trebao tamo postaviti. U web-aplikacijama
 * tipično rješenje je konfigurirati jedan filter koji će presresti pozive
 * servleta i prije toga ovdje ubaciti jednu vezu iz connection-poola, a po
 * zavrsetku obrade je maknuti.
 * 
 * @author marcupic
 */
public class SQLDAO implements DAO {

	@Override
	public List<PollEntry> getPollList(long pollId) throws DAOException {
		List<PollEntry> poll = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("SELECT ID, OPTIONTITLE, VOTESCOUNT, " 
					+ "OPTIONLINK FROM POLLOPTIONS"
					+ " WHERE POLLID=" + pollId + " ORDER BY ID");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs != null && rs.next()) {
						PollEntry entry = new PollEntry(rs.getLong(1), 
								rs.getString(2), rs.getString(4), pollId,
								rs.getLong(3)); // jesu li dobri indeksi?
						poll.add(entry);
					}
				} finally {
					try {
						rs.close();
					} catch(Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch(Exception ignorable) {
				}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}

		return poll;
	}

	@Override
	public PollEntry getPollEntry(long pollId, long id) throws DAOException {
		PollEntry pollEntry = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("SELECT ID, OPTIONTITLE, VOTESCOUNT, "
					+ " OPTIONLINK FROM POLLOPTIONS" 
					+ " WHERE POLLID=" + pollId + " AND ID=" + id + " ORDER BY ID");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if(rs != null && rs.next()) {
						pollEntry = new PollEntry(rs.getLong(1), rs.getString(2), 
								rs.getString(4), pollId, rs.getLong(3));
					}
				} finally {
					try {
						rs.close();
					} catch(Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch(Exception ignorable) {
				}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}

		return pollEntry;
	}

	@Override
	public PollDescription getPollDescription(long id) throws DAOException {
		PollDescription poll = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("SELECT * FROM POLLS WHERE ID=?");
			pst.setLong(1, id);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if(rs != null && rs.next()) {
						poll = new PollDescription(rs.getLong(1), 
								rs.getString(2), rs.getString(3));
					}
				} finally {
					try {
						rs.close();
					} catch(Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch(Exception ignorable) {
				}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}

		return poll;
	}
}