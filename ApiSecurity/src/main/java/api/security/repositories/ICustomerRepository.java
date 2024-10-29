package api.security.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import api.security.entities.CustomerEntity;

@Repository
public interface ICustomerRepository extends CrudRepository<CustomerEntity, Long> {

}
