package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Razred koji modelira komentar.
 * 
 * @author Maja Radočaj
 *
 */
@Entity
@Table(name="blog_comments")
public class BlogComment {

	/**
	 * ID komentara.
	 */
	private Long id;
	/**
	 * Zapis komentara.
	 */
	private BlogEntry blogEntry;
	/**
	 * EMail korisnika.
	 */
	private String usersEMail;
	/**
	 * Komentar.
	 */
	private String message;
	/**
	 * Datum objave.
	 */
	private Date postedOn;
	
	/**
	 * Getter za id.
	 * 
	 * @return id
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Setter za id.
	 * 
	 * @param id id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter za zapis.
	 * 
	 * @return zapis
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	/**
	 * Setter za zapis.
	 * 
	 * @param blogEntry zapis
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Getter za EMail.
	 * 
	 * @return EMail
	 */
	@Column(length=100,nullable=false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Setter za EMail.
	 * 
	 * @param usersEMail
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Getter za komentar.
	 * 
	 * @return komentar
	 */
	@Column(length=4096,nullable=false)
	public String getMessage() {
		return message;
	}

	/**
	 * Setter za komentar.
	 * 
	 * @param message komentar
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter za datum objave.
	 * 
	 * @return datum objave
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Setter za datum objave.
	 * 
	 * @param postedOn datum objave
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}