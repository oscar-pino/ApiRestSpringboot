package api.security.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import api.security.entities.AuthorEntity;
import api.security.entities.BookEntity;
import api.security.entities.CategoryEntity;
import api.security.entities.EditorialEntity;

@Repository
public interface IBookRepository extends CrudRepository<BookEntity, Long> {

	Optional<BookEntity> findByIsbm(String isbm);
	
	Optional<BookEntity> findByTitle(String title);
	
	List<BookEntity> findByEditorial(EditorialEntity editorial);

	List<BookEntity> findByAuthor(AuthorEntity author);

	List<BookEntity> findByCategory(CategoryEntity category);
}
