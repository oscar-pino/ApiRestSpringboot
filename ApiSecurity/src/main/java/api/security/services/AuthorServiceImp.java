package api.security.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.security.entities.AuthorEntity;
import api.security.repositories.IAuthorRepository;

@Service
public class AuthorServiceImp implements IDAO<AuthorEntity> {
	
	@Autowired
	private IAuthorRepository authorRepository;

	@Override
	public void create(AuthorEntity authorEntity) {
		
		authorRepository.save(authorEntity);
		
	}
	
	@Override
	public List<AuthorEntity> readAll() {
		
		return (List<AuthorEntity>)authorRepository.findAll();
	}

	@Override
	public Optional<AuthorEntity> readById(Long id) {
		
		return authorRepository.findById(id);
	}

	public Optional<AuthorEntity> readByWebSite(String webSite) {
		
		return authorRepository.findByWebSite(webSite);
	}
	
	public Optional<AuthorEntity> readByEmail(String email) {
		
		return authorRepository.findByEmail(email);
	}

	@Override
	public void deleteById(Long id) {
		
		authorRepository.deleteById(id);
		
	}

	@Override
	public void update(AuthorEntity authorEntity) {
		
		authorRepository.save(authorEntity);
		
	}
}
