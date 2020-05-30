package hr.fer.zemris.java.p12.model;

/**
 * Razred koji modelira jedan zapis u tablici Polls.
 * 
 * @author Maja Radočaj
 *
 */
public class PollDescription {

	/**
	 * Id ankete.
	 */
	private long id;
	/**
	 * Naslov.
	 */
	private String title;
	/**
	 * Poruka.
	 */
	private String message;
	
	/**
	 * Javni konstruktor.
	 * 
	 * @param id identifikacijski broj
	 * @param title naslov 
	 * @param message poruka
	 */
	public PollDescription(long id, String title, String message) {
		this.id = id;
		this.title = title;
		this.message = message;
	}

	/**
	 * Getter za id.
	 * 
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Getter za naslov.
	 * 
	 * @return naslov
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Getter za poruku.
	 * 
	 * @return poruka
	 */
	public String getMessage() {
		return message;
	}
}
