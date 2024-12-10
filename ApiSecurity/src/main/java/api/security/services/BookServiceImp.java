package api.security.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.security.entities.BookEntity;
import api.security.repositories.IBookRepository;

@Service
public class BookServiceImp implements IDAO<BookEntity> {

	@Autowired
	IBookRepository bookRepository;

	@Override
	public void create(BookEntity bookEntity) {

		bookRepository.save(bookEntity);
	}
	
	@Override
	public List<BookEntity> readAll() {

		return (List<BookEntity>) bookRepository.findAll();
	}

	@Override
	public Optional<BookEntity> readById(Long id) {

		return bookRepository.findById(id);
	}

	public Optional<BookEntity> readByTitle(String title) {

		return bookRepository.findByTitle(title);
	}

	public Optional<BookEntity> readByIsbm(String isbm) {

		return bookRepository.findByIsbm(isbm);
	}	
	
	public List<BookEntity> readAllByEditorialName(String editorialName) {

		return (List<BookEntity>) bookRepository.findAllByEditorialName(editorialName);
	}	

	public List<BookEntity> readAllByAuthorName(String authorName) {

		return (List<BookEntity>) bookRepository.findAllByAuthorName(authorName);
	}
	
	public List<BookEntity> readAllByCategoryName(String categoryName){
		
		return (List<BookEntity>) bookRepository.findAllByCategoryName(categoryName);
	}

	@Override
	public void update(BookEntity bookEntity) {

		bookRepository.save(bookEntity);
	}

	@Override
	public void deleteById(Long id) {

		bookRepository.deleteById(id);
	}
}
