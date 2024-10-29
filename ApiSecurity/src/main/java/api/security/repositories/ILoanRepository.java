package api.security.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import api.security.entities.LoanEntity;

@Repository
public interface ILoanRepository extends CrudRepository<LoanEntity, Long> {

}
