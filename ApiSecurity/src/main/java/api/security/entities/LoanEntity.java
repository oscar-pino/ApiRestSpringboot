package api.security.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "loans")
public class LoanEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "loans_books",
               joinColumns = @JoinColumn(name = "loan_id"),
               inverseJoinColumns = @JoinColumn(name = "book_id"))
	private List<BookEntity> books;
	
	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private CustomerEntity customer;
	
	@Past(message = "la fecha debe ser anterior a la fecha actual")
	private LocalDate loanDate;
	
	@Future(message = "la fecha debe ser posterior a la fecha actual")
	private LocalDate deliverDate;
	
	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(max = 20, message = "ingrese 20 caracteres como máximo")
	private String status;	
	
	@OneToOne(mappedBy = "loan")
	private ReturnEntity returns;

	public LoanEntity() {
	}	

	public LoanEntity(List<BookEntity> books, CustomerEntity customer, LocalDate loanDate, LocalDate deliverDate, String status) {
		this.books = books;
		this.customer = customer;
		this.loanDate = loanDate;
		this.deliverDate = deliverDate;
		this.status = status;
	}

	public LocalDate getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(LocalDate loanDate) {
		this.loanDate = loanDate;
	}

	public LocalDate getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(LocalDate deliverDate) {
		this.deliverDate = deliverDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public List<BookEntity> getBooks() {
		return books;
	}	

	public void setBooks(List<BookEntity> books) {
		this.books = books;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Loan [loanId=" + id +", customer=" + customer.getFirstName() + ", loanDate=" + loanDate
				+ ", deliverDate=" + deliverDate + ", status=" + status + "]";
	}
}
