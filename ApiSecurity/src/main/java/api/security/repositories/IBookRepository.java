package api.security.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import api.security.entities.BookEntity;

@Repository
public interface IBookRepository extends CrudRepository<BookEntity, Long> {

}
