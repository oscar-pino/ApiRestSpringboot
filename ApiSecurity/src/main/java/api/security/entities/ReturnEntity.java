package api.security.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "returns")
public class ReturnEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	
	
	@OneToOne
    @JoinColumn(name = "id")
	private LoanEntity loan;
	
	@PastOrPresent(message = "la fecha no puede ser posterior a la fecha de hoy")
	private Date returnDate;
	
	@Column(precision = 3)
	@NotNull(message = "el valor no puede ser nulo")
	@DecimalMin(value = "0.0", inclusive = true, message = "El precio debe ser mayor o igual a 0")
	private Float penalty;

	public ReturnEntity() {
	}	

	public ReturnEntity(LoanEntity loan, Date returnDate, Float penalty) {
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
