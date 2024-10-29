package api.security.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import api.security.dto.RoleDTO;
import api.security.entities.RoleEntity;
import api.security.services.RoleServiceImp;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
	
	@Autowired
	private RoleServiceImp roleServicesImp;
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody RoleDTO roleDTO) {		

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
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody RoleDTO roleDTO) {

		Optional<RoleEntity> recovered = roleServicesImp.readById(id);

		if (roleDTO.getRoleEnum() == null | roleDTO.getPermissionList().isEmpty()) {

			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos.");

		} 
		else if(recovered.isPresent()) {
			
				RoleEntity roleEntity = recovered.get();
				roleEntity.setRoleEnum(null);
				roleEntity.setPermissionList(null);

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

}
