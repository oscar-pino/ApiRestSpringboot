package api.security.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "authors")
public class AuthorEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(min = 3, max = 20, message = "ingrese 3 caracteres como mínimo")
	private String firstName;
	
	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(min = 3, max = 20, message = "ingrese 3 caracteres como mínimo")
	private String lastName;	
	
	@ManyToOne
	@JoinColumn(name = "nationality_id", nullable = false)
	@NotNull(message = "el campo no debe ser null")
	private NationalityEntity nationality;
	
	@Column(name = "web_site", unique=true)
	@Size(max = 30, message = "ingrese 30 caracteres como máximo")
    @Pattern(regexp = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[-\\w@:%_\\+.~#?&//=]*)?$", 
             message = "el sitio web debe tener un formato válido")
	private String webSite;
	
	@Column(unique=true)
	@Size(max = 30, message = "ingrese 30 caracteres como máximo")
	@Email(message = "el email debe tener un formato válido")
	private String email;
	
	@Column(name = "birth_date")
	@Past(message = "la fecha debe ser anterior a la fecha actual")
	private Date birthDate;

	public AuthorEntity() {
	}	
	
	public AuthorEntity(String firstName, String lastName, NationalityEntity nationality, Date birthDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.nationality = nationality;
		this.birthDate = birthDate;
	}

	public AuthorEntity(String firstName, String lastName, NationalityEntity nationality, String webSite, String email,
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
