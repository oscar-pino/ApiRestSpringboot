package api.security.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.security.dto.LoanDTO;
import api.security.entities.LoanEntity;
import api.security.services.LoanServiceImp;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
	
	@Autowired
	private LoanServiceImp loanServicesImp;
	
	@GetMapping("/read/all")
	public ResponseEntity<?> readAll() {

		List<LoanEntity> loans = loanServicesImp.readAll();
				
		if (!loans.isEmpty()) {
			
			loans.stream().map((l) -> new LoanDTO(l.getBooks(), l.getCustomer(), l.getLoanDate(), l.getReturnEntity())).toList();
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(loans);			
			
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado loans.");
	}
	
	
	/*
	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody RoleDTO roleDTO, BindingResult result) {		

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("ha ocurrido un error!");
		
		if (roleDTO == null | roleDTO.getPermissionList().isEmpty()) 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("permission no puede ser vacio");
		else		
		{
			
			roleServicesImp.create(new RoleEntity(roleDTO.getRoleEnum(), roleDTO.getPermissionList()));
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(roleDTO.getRoleEnum().name() + ", creado sastifactoriamente.");
			
		} 
	}
	
	@GetMapping("/read/all")
	public ResponseEntity<?> readAll() {

		List<RoleEntity> roles = roleServicesImp.readAll();
				
		if (!roles.isEmpty()) {
			
			roles.stream().map(r -> new RoleDTO(r.getRoleEnum())).toList();
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(roles);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado roles.");
	}
		
	@GetMapping("/read/id/{id}")
	public ResponseEntity<?> readById(@PathVariable Long id) {
	
		Optional<RoleEntity> recovered = roleServicesImp.readById(id);

		if (recovered.isPresent()) {			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered);
		}
		else
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se ha encontrado roles con el id: " + id + ".");
	}
		
	@GetMapping("/read/all/name/{name}")
	public ResponseEntity<?> readAllName(@PathVariable String name) {

		List<RoleEntity> roles = roleServicesImp.readAllByName(name);
				
		if (!roles.isEmpty()) {
			
			roles.stream().map(r -> new RoleDTO(r.getRoleEnum())).toList();
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(roles);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se ha encontrado role con el name: " + name + ".");
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody RoleDTO roleDTO, BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("ha ocurrido un error!");
		
		Optional<RoleEntity> recovered = roleServicesImp.readById(id);
	
		if (roleDTO.getRoleEnum() == null | roleDTO.getPermissionList().isEmpty()) {

			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos.");

		} 
		else if(recovered.isPresent()) {
			
				RoleEntity roleEntity = recovered.get();
				roleEntity.setRoleEnum(roleDTO.getRoleEnum());
				roleEntity.setPermissionList(roleDTO.getPermissionList());

				roleServicesImp.update(roleEntity);

				return ResponseEntity.status(HttpStatus.CONFLICT).body("datos actualizados, correctamente.");
			

		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("no se ha encontrado role con el id: " + id + ".");
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

		Optional<RoleEntity> recovered = roleServicesImp.readById(id);

		if (recovered.isPresent()) {
			roleServicesImp.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body("role con id: " + id + ", eliminada correctamente.");
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("no se ha encontrado role con el id: " + id + ".");
	}
*/
}
