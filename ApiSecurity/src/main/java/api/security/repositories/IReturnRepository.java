package api.security.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import api.security.entities.ReturnEntity;

@Repository
public interface IReturnRepository extends CrudRepository<ReturnEntity, Long> {

}
