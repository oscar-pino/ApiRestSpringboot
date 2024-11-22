package api.security.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.security.dto.CustomerDTO;
import api.security.dto.UserDTO;
import api.security.entities.CustomerEntity;
import api.security.entities.UserEntity;
import api.security.services.CustomerServiceImp;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	
	@Autowired
	private CustomerServiceImp customerServiceImp;

	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody CustomerDTO customerDTO, BindingResult result) {

		Optional<CustomerEntity> recovered = customerServiceImp.readByEmail(customerDTO.getEmail());
		
		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("ha ocurrido un error");		
		
		if(customerDTO.getFirstName().isBlank() | customerDTO.getLastName().isBlank() | customerDTO.getNationality() == null 
				| customerDTO.getAddress().isBlank() | customerDTO.getEmail().isBlank() | customerDTO.getPhone().isBlank())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("datos vacios!");
		
		else if(recovered.isPresent() | customerServiceImp.readByPhone(customerDTO.getPhone()).isPresent()) {
			
			if(recovered.isPresent())
				return ResponseEntity.status(HttpStatus.CONFLICT).body(customerDTO.getEmail()+" ya existe, pruebe con otro email!");
			else
				return ResponseEntity.status(HttpStatus.CONFLICT).body(customerDTO.getPhone()+" ya existe, pruebe con otro phone!");
				
		}

			customerServiceImp.create(new CustomerEntity(customerDTO.getFirstName(), customerDTO.getLastName(), customerDTO.getNationality(),
					customerDTO.getAddress(), customerDTO.getEmail(), customerDTO.getPhone()));
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(customerDTO.getFirstName() + ", creado sastifactoriamente.");	
	}
	
	@GetMapping("/read/all")
	public ResponseEntity<?> readAll() {

		List<CustomerDTO> customers = customerServiceImp.readAll().stream()
				.map((c) -> new CustomerDTO(c.getFirstName(), c.getLastName(), c.getNationality(),
						c.getAddress(), c.getEmail(), c.getPhone())).toList();

		if (customers.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(customers);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado customers.");
	}

	@GetMapping("/read/id/{id}")
	public ResponseEntity<?> readById(@PathVariable Long id) {

		Optional<CustomerEntity> recovered = customerServiceImp.readById(id);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se ha encontrado customer con id: " + id + ".");
	}

	@GetMapping("/read/email/{email}")
	public ResponseEntity<?> readByEmail(@PathVariable String email) {

		Optional<CustomerEntity> recovered = customerServiceImp.readByEmail(email);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se ha encontrado customer con email: " + email + ".");
	}
	
	@GetMapping("/read/phone/{phone}")
	public ResponseEntity<?> readByPhone(@PathVariable String phone) {

		Optional<CustomerEntity> recovered = customerServiceImp.readByPhone(phone);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se ha encontrado customer con phone: " + phone + ".");
	}

	

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody CustomerDTO customerDTO, BindingResult result) {
		
		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("ha ocurrido un error");
		
		Optional<CustomerEntity> recovered = customerServiceImp.readByEmail(customerDTO.getEmail());
		
		if(customerDTO.getFirstName().isBlank() | customerDTO.getLastName().isBlank() | customerDTO.getNationality() == null 
				| customerDTO.getAddress().isBlank() | customerDTO.getEmail().isBlank() | customerDTO.getPhone().isBlank())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("datos vacios!");
		
		else if(recovered.isPresent() | customerServiceImp.readByPhone(customerDTO.getPhone()).isPresent()) {
			
			if(recovered.isPresent())
				return ResponseEntity.status(HttpStatus.CONFLICT).body(customerDTO.getEmail()+" ya existe, pruebe con otro email!");
			else
				return ResponseEntity.status(HttpStatus.CONFLICT).body(customerDTO.getPhone()+" ya existe, pruebe con otro phone!");
				
		}
		
		CustomerEntity customer = recovered.get();
		customer.setFirstName(customerDTO.getFirstName());
		customer.setLastName(customerDTO.getLastName());
		customer.setNationality(customerDTO.getNationality());
		customer.setAddress(customerDTO.getAddress());
		customer.setEmail(customerDTO.getEmail());
		customer.setPhone(customerDTO.getPhone());
		
		customerServiceImp.update(customer);
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(customerDTO.getFirstName() + ", actualizada sastifactoriamente.");		
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

		Optional<CustomerEntity> recovered = customerServiceImp.readById(id);

		if (recovered.isPresent()) {
			customerServiceImp.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body("customer con id: " + id + ", eliminado correctamente.");
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("no se ha encontrado customer con id: " + id + ".");
	}

}
