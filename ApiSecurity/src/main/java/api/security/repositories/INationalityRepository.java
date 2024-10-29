package api.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import api.security.entities.NationalityEntity;


@Repository
public interface INationalityRepository extends CrudRepository<NationalityEntity, Long> {
	
	Optional<NationalityEntity> findByName(String name);

	Optional<NationalityEntity> findById(Long id);
	
	@Query(value = "SELECT MAx(id) FROM nationalities", nativeQuery = true)
	Long getLastId();
}
