package api.security.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.security.entities.LoanEntity;
import api.security.repositories.ILoanRepository;

@Service
public class LoanServiceImp implements IDAO<LoanEntity> {
	
	@Autowired
	private ILoanRepository loanRepository;

	@Override
	public void create(LoanEntity loanEntity) {
		
		loanRepository.save(loanEntity);
	}
	
	@Override
	public List<LoanEntity> readAll() {
		
		return (List<LoanEntity>)loanRepository.findAll();
	}
	
	@Override
	public Optional<LoanEntity> readById(Long id) {
		
		return loanRepository.findById(id);
	}	
	
	@Override
	public void update(LoanEntity loanEntity) {
		
		loanRepository.save(loanEntity);
	}

	@Override
	public void deleteById(Long id) {		

		loanRepository.deleteById(id);
	}
}
