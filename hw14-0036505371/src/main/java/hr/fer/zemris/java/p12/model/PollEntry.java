package hr.fer.zemris.java.p12.model;

/**
 * Razred koji modelira jedan zapis u tablici PollOptions.
 * 
 * @author Maja Radočaj
 *
 */
public class PollEntry {

	/**
	 * Id zapisa.
	 */
	private long id;
	/**
	 * Naslov zapisa.
	 */
	private String title;
	/**
	 * Link zapisa.
	 */
	private String link;
	/**
	 * Id pripadajuće ankete.
	 */
	private long pollId;
	/**
	 * Broj glasova.
	 */
	private long votes;
	
	/**
	 * Konstruktor.
	 * 
	 * @param id identifikacijski broj
	 * @param title naslov 
	 * @param link link
	 * @param pollId id pripadajuće ankete
	 * @param votes glasovi
	 */
	public PollEntry(long id, String title, String link, long pollId, long votes) {
		this.id = id;
		this.title = title;
		this.link = link;
		this.pollId = pollId;
		this.votes = votes;
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
	 * Getter za link.
	 * 
	 * @return link
	 */
	public String getLink() {
		return link;
	}
	
	/**
	 * Getter za id pripadajuće ankete.
	 * 
	 * @return id ankete
	 */
	public long getPollId() {
		return pollId;
	}
	
	/**
	 * Getter za broj glasova.
	 * 
	 * @return broj glasova
	 */
	public long getVotes() {
		return votes;
	}
}
