package api.security.controllers;

import java.util.Optional;
import java.util.Set;
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

import api.security.dto.CategoryDTO;
import api.security.entities.CategoryEntity;
import api.security.services.CategoryServiceImp;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {


	@Autowired
	private CategoryServiceImp categoryServicesImp;
	
	private Set<CategoryEntity> categories;
	
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result) {	
	
		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("ha ocurrido un error!");
			 
			 categories = categoryServicesImp.readAll().stream().map(c -> {
			        c.setName(c.getName().toLowerCase());
			        c.setName(c.getDescription().toLowerCase());
			        return c;
			    }).collect(Collectors.toSet());
			 
			 if(categoryDTO == null | categoryDTO.getName().isEmpty() | categoryDTO.getDescription().isEmpty()) {
					
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("faltan datos.");					
				}
			 else if(categories.stream().anyMatch((c) -> categoryDTO.getName().equals(c))){
				 
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(categoryDTO+", ya existe esta categoria");
			 }				 
			 else{
	            categoryServicesImp.create(new CategoryEntity(categoryDTO.getName(), categoryDTO.getDescription()));
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(categoryDTO.getName()+ ", creada sastifactoriamente.");
			 }      	
	}
	
	@GetMapping("/read/all")
	public ResponseEntity<?> readAll() {
		
		 
		 categories = categoryServicesImp.readAll().stream().map(c -> {
		        c.setName(c.getName().toLowerCase());
		        c.setDescription(c.getDescription().toLowerCase());
		        return c;
		    }).collect(Collectors.toSet());
		
		if (!categories.isEmpty()) {
			
			categories.stream().map(c -> new CategoryDTO(c.getId(), c.getName(), c.getDescription()));
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(categories);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado categories.");
	}

	
	@GetMapping("/read/id/{id}")
	public ResponseEntity<?> readById(@PathVariable Long id) {

		Optional<CategoryEntity> recovered = categoryServicesImp.readById(id);

		if (recovered.isPresent()) {
			CategoryDTO CategoryDTO = new CategoryDTO(id, recovered.get().getName(), recovered.get().getDescription());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(CategoryDTO);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se ha encontrado categoria con el id: " + id + ".");
	}
	
	@GetMapping("/read/name/{name}")
	public ResponseEntity<?> readByName(@PathVariable String name) {

		Optional<CategoryEntity> recovered = categoryServicesImp.readByName(name);

		if (recovered.isPresent()) {
			CategoryDTO categoryDTO = new CategoryDTO(recovered.get().getId(), recovered.get().getName(), recovered.get().getDescription());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(categoryDTO);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se han encontrado category con el name: " + name + ".");
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody CategoryDTO CategoryDTO, BindingResult result) {
		
		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("ha ocurrido un error!");

		Optional<CategoryEntity> recovered = categoryServicesImp.readById(id);

		if (!recovered.isPresent() | CategoryDTO == null) {

			if (CategoryDTO == null)
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(("faltan datos."));
			else
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(("no existe category con id: " + id));
		}
			
			recovered.get().setName(CategoryDTO.getName());
			recovered.get().setDescription(CategoryDTO.getDescription());
			categoryServicesImp.update(recovered.get());			
			return ResponseEntity.status(HttpStatus.CREATED).body(recovered.get().getName() + ", actualizada sastifactoriamente.");
	}
		
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

		Optional<CategoryEntity> recovered = categoryServicesImp.readById(id);		

		if (recovered.isPresent()) {		
			
			categoryServicesImp.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body("category con id: " + id + ", eliminada correctamente.");
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("no se ha encontrado category con el id: " + id + ".");
	}
	
}
