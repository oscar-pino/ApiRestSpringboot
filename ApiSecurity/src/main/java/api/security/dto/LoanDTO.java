package api.security.dto;

import java.time.LocalDate;
import java.util.Set;

import api.security.entities.BookEntity;
import api.security.entities.CustomerEntity;
import api.security.entities.ReturnEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

public class LoanDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "loans_books", joinColumns = @JoinColumn(name = "loan_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
	private Set<BookEntity> books;

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private CustomerEntity customer;

	private LocalDate loanDate;
	
	@OneToOne(targetEntity = ReturnEntity.class, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "return_id", nullable = false)
	private ReturnEntity returns;

	public LoanDTO() {
	}

	public LoanDTO(Set<BookEntity> books, CustomerEntity customer, LocalDate loanDate) {
		this.books = books;
		this.customer = customer;
		this.loanDate = loanDate;
	}
	
	public LoanDTO(Set<BookEntity> books, CustomerEntity customer, LocalDate loanDate, ReturnEntity returns) {
		this(books, customer, loanDate);
		this.returns=returns;
	}
	
	public LoanDTO(Long id, Set<BookEntity> books, CustomerEntity customer, LocalDate loanDate, ReturnEntity returns) {
		this(books, customer, loanDate, returns);
		this.id=id;
	}

	public LocalDate getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(LocalDate loanDate) {
		this.loanDate = loanDate;
	}

	public Long getId() {
		return id;
	}

	public Set<BookEntity> getBooks() {
		return books;
	}

	public void setBooks(Set<BookEntity> books) {
		this.books = books;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}

	public ReturnEntity getReturnEntity() {

		return returns;
	}

	public void setReturnEntity(ReturnEntity returns) {

		this.returns = returns;
	}

	@Override
	public String toString() {
		return "LoanDTO [id=" + id + ", books=" + books + ", customer=" + customer + ", loanDate=" + loanDate
				+ ", returns=" + returns + "]";
	}
}
