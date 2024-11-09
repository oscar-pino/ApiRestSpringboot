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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "books")
public class BookEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	
	
	@Column(nullable = false)	
	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(min = 3, max = 20, message = "ingrese 3 caracteres como mínimo y 20 como máximo")
	private String title;
	
	@Column(nullable = false, unique = true)
	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	private String isbm;

	@ManyToOne
	@JoinColumn(name = "publisher_id", nullable = false)
	private PublisherEntity publisher;

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private CategoryEntity category;

	@ManyToOne
	@JoinColumn(name = "author_id", nullable = false)
	private AuthorEntity author;
	
	@Column(nullable = false)
	@Past(message = "la fecha debe ser anterior a la fecha actual")
	private Date publicationYear;
	
	@Column(nullable = false)	
	@Min(value=0, message = "the quantity couldn´t less than zero")
	private int quantity;
	
//	@ManyToMany(mappedBy = "books")
//	private Set<Loan> loans = new HashSet<>();
	
	public BookEntity() {
	}

	public BookEntity(String title, String isbm, PublisherEntity publisher, CategoryEntity category, AuthorEntity author, Date publicationYear, int quantity) {
	this.title = title;
	this.isbm = isbm;
	this.publisher = publisher;
	this.category = category;
	this.author = author;
	this.publicationYear = publicationYear;
	this.quantity = quantity;
}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbm() {
		return isbm;
	}

	public void setIsbm(String isbm) {
		this.isbm = isbm;
	}

	public Date getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(Date publicationYear) {
		this.publicationYear = publicationYear;
	}

	public Long getId() {
		return id;
	}

	public PublisherEntity getPublisher() {
		return publisher;
	}

	public void setPublisher(PublisherEntity publisher) {
		this.publisher = publisher;
	}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}

	public AuthorEntity getAuthor() {
		return author;
	}

	public void setAuthor(AuthorEntity author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + id + ", title=" + title + ", isbm=" + isbm + ", publisher=" + publisher
				+ ", category=" + category + ", author=" + author.getFirstName() + ", publicationYear=" + publicationYear
				+ ", quantity=" + quantity + "]";
	}	
}
