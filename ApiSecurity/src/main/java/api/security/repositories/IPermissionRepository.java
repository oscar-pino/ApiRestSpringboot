package api.security.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import api.security.entities.PermissionEntity;

@Repository
public interface IPermissionRepository extends CrudRepository<PermissionEntity, Long> {
	
	@Query(value = "SELECT * FROM permissions", nativeQuery = true)
	List<PermissionEntity> getAllPermissions();
	
	@Query(value = "SELECT * FROM permissions WHERE permission_name = :name", nativeQuery = true)	
	List<PermissionEntity> findAllPermissionByName(@Param("name") String name);	
	
	@Query(value = "SELECT DISTINCT permission_name FROM permissions", nativeQuery = true)
	List<PermissionEntity> getUniquePermissions();
	

}


