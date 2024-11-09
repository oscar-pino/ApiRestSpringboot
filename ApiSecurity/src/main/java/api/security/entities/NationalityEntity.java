package api.security.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "nationalities")
public class NationalityEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	

	@Column(unique = true, nullable = false)
	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(min = 4, max = 20, message = "ingrese 4 caracteres como mínimo y 20 como máximo")
	private String name;	

	@Column(nullable = false)	
	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(min = 5, max = 20, message = "ingrese 5 caracteres como mínimo y 20 como máximo")
	private String language;

	public NationalityEntity() {
	}

	public NationalityEntity(String name, String language) {
		this.name = name;
		this.language = language;
	}
	
	public NationalityEntity(Long id, String name, String language) {
		this(name, language);
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Nationality [nationalityId=" + id + ", name=" + name + ", language=" + language + "]";
	}

	@Override
	public boolean equals(Object obj) {
		
		NationalityEntity ne = (NationalityEntity) obj;
		
		if(obj == null)
			return false;
		else if(!ne.name.equalsIgnoreCase(this.name) | !ne.language.equalsIgnoreCase(this.language))
			return false;
		return true;
	}	
	
	
}
