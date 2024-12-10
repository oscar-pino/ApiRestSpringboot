package api.security.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import api.security.entities.AuthorEntity;


@Repository
public interface IAuthorRepository extends CrudRepository<AuthorEntity, Long> {

	Optional<AuthorEntity> findByEmail(@Param(value = "email") String email);
	
	Optional<AuthorEntity> findByWebSite(@Param(value = "web") String web);
}
