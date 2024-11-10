package api.security.dto;

import java.util.Date;

import api.security.entities.AuthorEntity;
import api.security.entities.CategoryEntity;
import api.security.entities.PublisherEntity;

public class BookDTO {

	private Long id;	

	private String title;

	private String isbm;

	private PublisherEntity publisher;

	private CategoryEntity category;

	private AuthorEntity author;

	private Date publicationYear;

	private int quantity;
	
//	@ManyToMany(mappedBy = "books")
//	private Set<Loan> loans = new HashSet<>();
	
	public BookDTO() {
	}

	public BookDTO(String title, String isbm, PublisherEntity publisher, CategoryEntity category, AuthorEntity author, Date publicationYear, int quantity) {
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
