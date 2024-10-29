package api.security.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import api.security.entities.PublisherEntity;

@Repository
public interface IPublisherRepository extends CrudRepository<PublisherEntity, Long> {

}
