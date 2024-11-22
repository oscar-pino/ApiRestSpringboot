package api.security.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import api.security.entities.EditorialEntity;

@Repository
public interface IEditorialRepository extends CrudRepository<EditorialEntity, Long> {

	Optional<EditorialEntity> findByName(String name);
	
	Optional<EditorialEntity> findByWebSite(String webSite);
	
	Optional<EditorialEntity> findByEmail(String email);
	
	
}
