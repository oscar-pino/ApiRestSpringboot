package api.security.controllers;

import java.util.List;
import java.util.Optional;

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

import api.security.dto.PermissionDTO;
import api.security.entities.PermissionEntity;
import api.security.entities.enums.PermissionEnum;
import api.security.services.PermissionServiceImp;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {
	
	@Autowired
	private PermissionServiceImp permissionServicesImp;
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody PermissionDTO permissionDTO, BindingResult result) {	
		
		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("ha ocurrido un error!");
		
		PermissionEnum permission = null;
			 
			 if(permissionDTO == null) {
					
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("faltan datos.");					
				}
			
	            
				 permission = PermissionEnum.valueOf(permissionDTO.getPermission().name().toUpperCase());
				 
				 if(permission == null)
					 return ResponseEntity.badRequest().body("no existe permission: " + permissionDTO.getPermission().name().toUpperCase());
				 
				
	            permissionServicesImp.create(new PermissionEntity(permission));
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(permissionDTO.getPermission() + ", creado sastifactoriamente.");	 	            
	     		
	}

	
	@GetMapping("/read/id/{id}")
	public ResponseEntity<?> readById(@PathVariable Long id) {

		Optional<PermissionEntity> recovered = permissionServicesImp.readById(id);

		if (recovered.isPresent()) {
			PermissionDTO permissionDTO = new PermissionDTO(id, recovered.get().getPermissionEnum());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(permissionDTO);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se ha encontrado permission con el id: " + id + ".");
	}
	
	@GetMapping("/read/all/name/{name}")
	public ResponseEntity<?> readByName(@PathVariable String name) {

		List<PermissionEntity> permissions = permissionServicesImp.readAllPermissionByName(name);

		if (!permissions.isEmpty()) {
			List<PermissionDTO> permissionsDTO = permissions.stream().map(p -> new PermissionDTO(p.getId(), p.getPermissionEnum())).toList();
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(permissionsDTO);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se han encontrado permission con el name: " + name + ".");
	}

	@GetMapping("/read/all")
	public ResponseEntity<?> readAll() {

		List<PermissionEntity> permissions = permissionServicesImp.readAll();
				
		if (!permissions.isEmpty()) {
			
			permissions.stream().map(p -> new PermissionDTO(p.getId(), p.getPermissionEnum()));
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(permissions);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado roles.");
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PermissionDTO permissionDTO, BindingResult result) {
		
		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("ha ocurrido un error!");
		
		Long lastId = permissionServicesImp.getLastId();
		Optional<PermissionEntity> recovered = null;

		if (id < 1 | id > lastId | permissionDTO == null) {

			if (permissionDTO == null)
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(("faltan datos."));
			else
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(("no existe permission con id: " + id));
		}else {
			
			recovered = permissionServicesImp.readById(id);
		}
		
		
		if(recovered.isPresent()) {
			
			recovered.get().setPermissionEnum(permissionDTO.getPermission());
			permissionServicesImp.update(recovered.get());			
			return ResponseEntity.status(HttpStatus.CREATED).body(recovered.get().getPermissionEnum() + ", actualizada sastifactoriamente.");
		}
			
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(("ingrese un permission valido!"));
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

		Optional<PermissionEntity> recovered = permissionServicesImp.readById(id);		

		if (recovered.isPresent()) {		
			
			permissionServicesImp.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body("permission con id: " + id + ", eliminada correctamente.");
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("no se ha encontrado permission con el id: " + id + ".");
	}
}
