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

import api.security.dto.NationalityDTO;
import api.security.entities.NationalityEntity;
import api.security.services.NationalityServiceImp;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/nationalities")
public class NationalityController {

	@Autowired
	private NationalityServiceImp nationalityServiceImp;

	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody NationalityDTO nationalityDTO, BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("ha ocurrido un error!");
		
		Optional<NationalityEntity> recovered = nationalityServiceImp.readByName(nationalityDTO.getName());		

		if (!nationalityDTO.getName().isEmpty() & !nationalityDTO.getLanguage().isEmpty()) {

			if (recovered.isPresent()) {

				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(nationalityDTO.getName()+", ya existe, pruebe con otra.");
			} else {
				nationalityServiceImp
						.create(new NationalityEntity(nationalityDTO.getName(), nationalityDTO.getLanguage()));
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(nationalityDTO.getName() + ", creada sastifactoriamente.");
			}
		} else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("faltan datos.");
	}

	@GetMapping("/read/id/{id}")
	public ResponseEntity<?> readById(@PathVariable Long id) {

		Optional<NationalityEntity> recovered = nationalityServiceImp.readById(id);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se ha encontrado nacionalidad con id: " + id + ".");
	}

	@GetMapping("/read/name/{name}")
	public ResponseEntity<?> readByName(@PathVariable String name) {

		Optional<NationalityEntity> recovered = nationalityServiceImp.readByName(name);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se ha encontrado nacionalidad con nombre: " + name + ".");
	}

	@GetMapping("/read/all")
	public ResponseEntity<?> readAll() {

		List<NationalityDTO> nationes = nationalityServiceImp.readAll().stream()
				.map((n) -> new NationalityDTO(n.getId(), n.getName(), n.getLanguage())).toList();

		if (nationes.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(nationes);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado nacionalidades.");
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody NationalityDTO nationalityDTO, BindingResult result) {
		
		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("ha ocurrido un error!");
		
		List<NationalityEntity> nations = nationalityServiceImp.readAll();
		Long lastId = nationalityServiceImp.getLastId();
		

		if (id < 1 | id > lastId | nationalityDTO.getName().isEmpty()
				| nationalityDTO.getLanguage().isEmpty()) {

			if (nationalityDTO.getName().isEmpty() | nationalityDTO.getLanguage().isEmpty())
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(("faltan datos."));
			else
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(("no existe nación con id: " + id));
		}
		boolean repeat = false;

		
		NationalityEntity recovered = nationalityServiceImp.readById(id).get();		

		for (NationalityEntity ne : nations) {

			if (ne.getId() != id) {
				if (nationalityDTO.getName().equalsIgnoreCase(ne.getName())) {
					repeat = true;
					break;
				}
			}
		}

		if (repeat) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(nationalityDTO.getName()+" ya existe, pruebe con otra.");
		} else {
			recovered.setLanguage(nationalityDTO.getLanguage());
			recovered.setName(nationalityDTO.getName());
			
			nationalityServiceImp.update(recovered);
			repeat = false;
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(nationalityDTO.getName() + ", actualizada sastifactoriamente.");
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

		Optional<NationalityEntity> recovered = nationalityServiceImp.readById(id);

		if (recovered.isPresent()) {
			nationalityServiceImp.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body("nacionalidad con id: " + id + ", eliminada correctamente.");
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("no se ha encontrado nacionalidad con id: " + id + ".");
	}
}
