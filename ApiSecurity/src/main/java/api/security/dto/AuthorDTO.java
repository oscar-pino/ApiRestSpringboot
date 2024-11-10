package api.security.dto;

import java.util.Date;

import api.security.entities.NationalityEntity;

public class AuthorDTO {

	private Long id;

	private String firstName;

	private String lastName;	

	private NationalityEntity nationality;

	private String webSite;

	private String email;

	private Date birthDate;

	public AuthorDTO() {
	}	
	
	public AuthorDTO(String firstName, String lastName, NationalityEntity nationality, Date birthDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.nationality = nationality;
		this.birthDate = birthDate;
	}

	public AuthorDTO(String firstName, String lastName, NationalityEntity nationality, String webSite, String email,
			Date birthDate) {
		this(firstName, lastName, nationality, birthDate);
		this.webSite = webSite;
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}	

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Author [authorId=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", nationality="
				+ nationality + ", webSite=" + webSite + ", email=" + email + ", birthDate=" + birthDate + "]";
	}
}
