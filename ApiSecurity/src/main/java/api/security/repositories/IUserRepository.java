package api.security.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import api.security.entities.UserEntity;

public interface IUserRepository extends CrudRepository<UserEntity, Long> {
	
	Optional<UserEntity> findByUsername(String username);
	
	@Query(value = "SELECT * FROM users", nativeQuery = true)
	List<UserEntity> getAllUsers();

}
