package api.security.dto;

import java.time.LocalDate;
import java.util.List;

import api.security.entities.BookEntity;
import api.security.entities.CustomerEntity;
import api.security.entities.ReturnEntity;

public class LoanDTO {

	private Long id;

	private List<BookEntity> books;

	private CustomerEntity customer;

	private LocalDate loanDate;

	private LocalDate deliverDate;

	private String status;	

	private ReturnEntity returns;

	public LoanDTO() {
	}	

	public LoanDTO(List<BookEntity> books, CustomerEntity customer, LocalDate loanDate, LocalDate deliverDate, String status) {
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
