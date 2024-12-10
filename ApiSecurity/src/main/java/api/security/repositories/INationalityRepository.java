package api.security.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import api.security.entities.NationalityEntity;

@Repository
public interface INationalityRepository extends CrudRepository<NationalityEntity, Long> {
	
	Optional<NationalityEntity> findByName(@Param(value = "name") String name);

}
