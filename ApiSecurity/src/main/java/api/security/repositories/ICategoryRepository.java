package api.security.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import api.security.entities.CategoryEntity;

@Repository
public interface ICategoryRepository extends CrudRepository<CategoryEntity, Long> {
	
	Optional<CategoryEntity> findByName(String name);

}
