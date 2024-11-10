package api.security.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "publishers")
public class PublisherEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(min = 5, max = 20, message = "ingrese 5 caracteres como mínimo y 20 como máximo")
	private String name;

	@Column(unique = true)
	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(max = 50, message = "ingrese 50 caracteres como máximo")
	private String address;

	@Column(unique = true)
	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(min = 10, max = 20, message = "ingrese 10 caracteres como mínimo y 20 como máximo")
	private String phone;

	@Column(name = "web_site", unique = true)
	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(max = 30, message = "ingrese 30 caracteres como máximo")
    @Pattern(regexp = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[-\\w@:%_\\+.~#?&//=]*)?$", 
             message = "el sitio web debe tener un formato válido")
	private String webSite;

	@Column(unique = true)
	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(max = 30, message = "ingrese 30 caracteres como máximo")
	@Email(message = "el email debe tener un formato válido")
	private String email;

	@Column(name = "founding_date")
	@Past(message = "la fecha debe ser anterior a la fecha actual")
	private Date foundingDate;

	public PublisherEntity() {
	}

	public PublisherEntity(String name, String address, String phone, String webSite, String email, Date foundingDate) {
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.webSite = webSite;
		this.email = email;
		this.foundingDate = foundingDate;
	}

	public PublisherEntity(Long id, String name, String address, String phone, String webSite, String email,
			Date foundingDate) {
		this(name, address, phone, webSite, email, foundingDate);
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public Date getFoundingDate() {
		return foundingDate;
	}

	public void setFoundingDate(Date foundingDate) {
		this.foundingDate = foundingDate;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Publisher [publisherId=" + id + ", name=" + name + ", address=" + address + ", phone=" + phone
				+ ", webSite=" + webSite + ", email=" + email + ", foundingDate=" + foundingDate + "]";
	}
}
