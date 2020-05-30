package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

public interface DAO {

	/**
	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param id ključ zapisa
	 * @return entry ili <code>null</code> ako entry ne postoji
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Dohvaća korisnika sa zadanim nadimkom. Ako takav korisnik ne postoji, vraća 
	 * <code>null</code>.
	 * 
	 * @param nick nadimak korisnika
	 * @return korisnik sa zadanim nadimkom
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public BlogUser getBlogUser(String nick) throws DAOException;
	
	/**
	 * Vraća listu registriranih korisnika.
	 * 
	 * @return lista korisnika
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public List<BlogUser> getBlogUsers() throws DAOException;
	
	/**
	 * Registrira novog korisnika u bazu podataka.
	 * 
	 * @param user korisnik
	 * @return novi korisnik
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public BlogUser registerBlogUser(BlogUser user) throws DAOException;
	
	/**
	 * Vraća popis zapisa za zadanog korisnika.
	 * 
	 * @param creator korisnik
	 * @return popis zapisa korisnika
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public List<BlogEntry> getBlogEntries(BlogUser creator) throws DAOException;
	
	/**
	 * Sprema zapis u bazu podataka.
	 * 
	 * @param entry zapis
	 * @return novi zapis
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public BlogEntry saveBlogEntry(BlogEntry entry) throws DAOException;
	
	/**
	 * Uređuje zapis u bazi podataka.
	 * 
	 * @param entry zapis kojeg treba urediti
	 * @return novi zapis podataka
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public BlogEntry editBlogEntry(BlogEntry entry) throws DAOException;
	
	/**
	 * Metoda za dodavanje novog komentara na zapis entry.
	 * 
	 * @param blogEntry zapis
	 * @param comment komentar na zapisu
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public void addBlogComment(BlogEntry blogEntry, BlogComment comment) throws DAOException;
}