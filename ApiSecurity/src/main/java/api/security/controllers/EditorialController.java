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

import api.security.dto.EditorialDTO;
import api.security.entities.EditorialEntity;
import api.security.services.EditorialServiceImp;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/editorials")
public class EditorialController {
	
	@Autowired
	private EditorialServiceImp editorialServiceImp;

	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody EditorialDTO editorialDTO, BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");
		
		Optional<EditorialEntity> recovered = editorialServiceImp.readByName(editorialDTO.getName());
		
		boolean exists = false; 

		if(editorialDTO.getName().isBlank() | editorialDTO.getAddress().isBlank() | editorialDTO.getFoundingDate() == null
				| editorialDTO.getEmail().isBlank() | editorialDTO.getPhone().isBlank() | editorialDTO.getWebSite().isBlank())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos!");
		
		exists = editorialServiceImp.readAll().stream().anyMatch(e -> e != recovered.get() & e.getName().equalsIgnoreCase(editorialDTO.getName()));
		
		if(exists)
			return ResponseEntity.status(HttpStatus.CONFLICT).body(editorialDTO.getName()+" ya existe, pruebe con otro name!");
		
		editorialServiceImp.create(new EditorialEntity(editorialDTO.getName(), editorialDTO.getAddress(), editorialDTO.getPhone(), 
				editorialDTO.getWebSite(), editorialDTO.getEmail(), editorialDTO.getFoundingDate()));
		return ResponseEntity.status(HttpStatus.CREATED).body("editorial creada sastifactoriamente");		
	}
	
	@GetMapping("/read/all")
	public ResponseEntity<?> readAll() {

		List<EditorialDTO> editorials = editorialServiceImp.readAll().stream()
				.map((e) -> new EditorialDTO(e.getId(), e.getName(), e.getAddress(), e.getPhone(), e.getWebSite(), e.getEmail(), e.getFoundingDate())).toList();

		if (editorials.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(editorials);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado editorials.");
	}

	@GetMapping("/read/id/{id}")
	public ResponseEntity<?> readById(@PathVariable Long id) {

		Optional<EditorialEntity> recovered = editorialServiceImp.readById(id);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado editorial con id: " + id + ".");
	}	

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody EditorialDTO editorialDTO,
			BindingResult result) {		

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");
		
		Optional<EditorialEntity> recovered = editorialServiceImp.readById(id);

		if(editorialDTO.getName().isBlank() | editorialDTO.getAddress().isBlank() | editorialDTO.getFoundingDate() == null
				| editorialDTO.getEmail().isBlank() | editorialDTO.getPhone().isBlank() | editorialDTO.getWebSite().isBlank())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos!");
		
		if(!recovered.isPresent())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("no se ha encontrado editorial con el id: "+id+"!");
		else {
			
			EditorialEntity editorial = recovered.get();
			
			editorial.setName(editorialDTO.getName());
			editorial.setAddress(editorialDTO.getAddress());
			editorial.setEmail(editorialDTO.getEmail());
			editorial.setPhone(editorialDTO.getPhone());
			editorial.setWebSite(editorialDTO.getWebSite());
			editorial.setFoundingDate(editorialDTO.getFoundingDate());
			
			editorialServiceImp.create(editorial);
			return ResponseEntity.status(HttpStatus.OK).body("editorial con id: "+id+" actualizada correctamente");	
		}
		
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

		Optional<EditorialEntity> recovered = editorialServiceImp.readById(id);

		if (recovered.isPresent()) {
			editorialServiceImp.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body("editorial con id: " + id + ", eliminada correctamente.");
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("no se ha encontrado editorial con id: " + id + ".");
	}

}
