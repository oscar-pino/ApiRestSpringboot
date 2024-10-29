package api.security.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.security.dto.BookDTO;
import api.security.entities.BookEntity;
import api.security.entities.UserEntity;
import api.security.repositories.IBookRepository;

@Service
public class BookServiceImp implements IDAO<BookEntity> {
	
	@Autowired
	IBookRepository bookRepository;

	@Override
	public void create(BookEntity t) {
		
	
		
	}
	
	@Override
	public Optional<BookEntity> readById(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
	
	@Override
	public List<BookEntity> readAll() {
		
		return null;
	}
	
	@Override
	public void update(BookEntity t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Long id) {
		

		
	}

	@Override
	public Long getLastId() {
		// TODO Auto-generated method stub
		return null;
	}
}
