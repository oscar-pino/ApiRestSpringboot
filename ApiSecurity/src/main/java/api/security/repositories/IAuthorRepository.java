package api.security.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import api.security.entities.AuthorEntity;

@Repository
public interface IAuthorRepository extends CrudRepository<AuthorEntity, Long> {

	Optional<AuthorEntity> findByWebSite(String webSite);
	
	Optional<AuthorEntity> findByEmail(String email);
}
