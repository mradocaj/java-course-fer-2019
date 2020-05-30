package hr.fer.zemris.hw16.model;

import java.util.List;

/**
 * Razred koji modelira sliku.
 * 
 * @author Maja Radočaj
 *
 */
public class Image {

	/**
	 * Ime slike u datotečnom sustavu.
	 */
	private String fileName;
	/**
	 * Naslov slike.
	 */
	private String title;
	/**
	 * Oznake slike.
	 */
	private List<String> tags;
	
	/**
	 * Javni konstruktor.
	 * 
	 * @param fileName ime slike
	 * @param title naslov slike
	 * @param tags oznake
	 */
	public Image(String fileName, String title, List<String> tags) {
		this.fileName = fileName;
		this.title = title;
		this.tags = tags;
	}

	/**
	 * Getter koji vraća ime slike.
	 * 
	 * @return ime slike
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Getter koji vraća naslov slike.
	 * 
	 * @return naslov slike
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Getter koji vraća listu tagova.
	 * 
	 * @return tagovi
	 */
	public List<String> getTags() {
		return tags;
	}
	
}
