package api.security.dto;

import java.util.Date;

import api.security.entities.LoanEntity;

public class ReturnDTO {

	private Long id;		

	private LoanEntity loan;
	
	private Date returnDate;

	private Float penalty;

	public ReturnDTO() {
	}	

	public ReturnDTO(LoanEntity loan, Date returnDate, Float penalty) {
		this.loan = loan;
		this.returnDate = returnDate;
		this.penalty = penalty;
	}

	public Date getreturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Float getPenalty() {
		return penalty;
	}

	public void setPenalty(Float penalty) {
		this.penalty = penalty;
	}

	public Long getId() {
		return id;
	}

	public LoanEntity getLoan() {
		return loan;
	}

	public void setLoan(LoanEntity loan) {
		this.loan = loan;
	}

	public Date getReturnDate() {
		return returnDate;
	}	
	
	@Override
	public String toString() {
		return "Return [returnId=" + id + ", loan=" + loan + ", returnDate=" + returnDate + ", penalty=" + penalty
				+ "]";
	}
}
