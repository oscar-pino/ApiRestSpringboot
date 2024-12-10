package api.security.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import api.security.entities.UserEntity;

public interface IUserRepository extends CrudRepository<UserEntity, Long> {
	
	Optional<UserEntity> findByUsername(@Param(value = "name") String username);

}
