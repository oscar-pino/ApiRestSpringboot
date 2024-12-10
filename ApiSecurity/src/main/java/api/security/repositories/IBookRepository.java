package api.security.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import api.security.entities.BookEntity;

@Repository
public interface IBookRepository extends CrudRepository<BookEntity, Long> {

	Optional<BookEntity> findByIsbm(@Param(value = "isbm") String isbm);
	
	Optional<BookEntity> findByTitle(@Param(value = "title") String title);
	
	@Query("SELECT b FROM BookEntity b WHERE b.editorial.name = :name")
	List<BookEntity> findAllByEditorialName(@Param(value = "name") String name);
	
	@Query("SELECT b FROM BookEntity b WHERE b.author.firstName = :name ORDER BY b.id")
	List<BookEntity> findAllByAuthorName(@Param(value = "name") String name);

	@Query("SELECT b FROM BookEntity b WHERE b.category.name = :name ORDER BY b.id")
	List<BookEntity> findAllByCategoryName(@Param(value = "name") String name); 
}
