package hr.fer.zemris.java.tecaj_13.model;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Razred koji modelira jednog korisnika bloga.
 * 
 * @author Maja Radočaj
 *
 */
@Entity
@Table(name="blog_users")
@Cacheable(true)	
public class BlogUser {

	/**
	 * Id.
	 */
	private Long id;
	/**
	 * Ime.
	 */
	private String firstName;
	/**
	 * Prezime.
	 */
	private String lastName;
	/**
	 * Nadimak.
	 */
	private String nick;
	/**
	 * EMail.
	 */
	private String email;
	/**
	 * Hashirana lozinka.
	 */
	private String passwordHash;
	/**
	 * Zapisi.
	 */
	private Collection<BlogEntry> entries = new HashSet<>();
	
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
	 * @param id 
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter za ime.
	 * 
	 * @return ime
	 */
	@Column(nullable=false, length=50)
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter za ime.
	 * 
	 * @param firstName ime
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter za prezime.
	 * 
	 * @return prezime
	 */
	@Column(nullable=false, length=50)
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter za prezime.
	 * 
	 * @param lastName prezime
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter za nadimak.
	 * 
	 * @return nadimak
	 */
	@Column(nullable=false, length=50, unique=true)
	public String getNick() {
		return nick;
	}

	/**
	 * Setter za nadimak.
	 * 
	 * @param nick nadimak
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Getter za EMail.
	 * 
	 * @return EMail
	 */
	@Column(nullable=false, length = 200)
	public String getEmail() {
		return email;
	}

	/**
	 * Setter za EMail.
	 * 
	 * @param email EMail
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter za hash lozinke.
	 * 
	 * @return hash lozinke
	 */
	@Column(nullable=false, length = 200)
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Setter za hash lozinke.
	 * 
	 * @param passwordHash hash lozinke
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Getter za zapise.
	 * 
	 * @return zapisi
	 */
	@OneToMany(mappedBy="creator",fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, 
			orphanRemoval=true)
	public Collection<BlogEntry> getEntries() {
		return entries;
	}

	/**
	 * Setter za zapise.
	 * 
	 * @param entries zapisi
	 */
	public void setEntries(Collection<BlogEntry> entries) {
		this.entries = entries;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((nick == null) ? 0 : nick.hashCode());
		result = prime * result + ((passwordHash == null) ? 0 : passwordHash.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		BlogUser other = (BlogUser) obj;
		if(email == null) {
			if(other.email != null)
				return false;
		} else if(!email.equals(other.email))
			return false;
		if(firstName == null) {
			if(other.firstName != null)
				return false;
		} else if(!firstName.equals(other.firstName))
			return false;
		if(id == null) {
			if(other.id != null)
				return false;
		} else if(!id.equals(other.id))
			return false;
		if(lastName == null) {
			if(other.lastName != null)
				return false;
		} else if(!lastName.equals(other.lastName))
			return false;
		if(nick == null) {
			if(other.nick != null)
				return false;
		} else if(!nick.equals(other.nick))
			return false;
		if(passwordHash == null) {
			if(other.passwordHash != null)
				return false;
		} else if(!passwordHash.equals(other.passwordHash))
			return false;
		return true;
	}
	
}
