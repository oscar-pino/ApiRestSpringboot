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

import api.security.dto.AuthorDTO;
import api.security.entities.AuthorEntity;
import api.security.services.AuthorServiceImp;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

	@Autowired
	private AuthorServiceImp authorServiceImp;

	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody AuthorDTO authorDTO, BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");

		boolean emptyFields = authorDTO.getFirstName().isBlank() | authorDTO.getLastName().isBlank() | authorDTO.getNationality() == null 
				| authorDTO.getEmail().isBlank() | authorDTO.getWebSite().isBlank();
		boolean[] repeated = new boolean[2]; // 0: email, 1: webSite

		if (emptyFields)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos.");
		else {
			repeated[0] = authorServiceImp.readAll().stream().anyMatch(a -> a.getEmail().equalsIgnoreCase(authorDTO.getEmail()));
			repeated[1] = authorServiceImp.readAll().stream().anyMatch(a -> a.getWebSite().equalsIgnoreCase(authorDTO.getWebSite()));
		}

		if (repeated[0] | repeated[1]) {
			if(repeated[0])
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(authorDTO.getEmail() + " ya existe, pruebe con otra email.");
			else
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(authorDTO.getEmail() + " ya existe, pruebe con otra sitio web.");
		}
		else {
			authorServiceImp.create(new AuthorEntity(authorDTO.getFirstName(), authorDTO.getLastName(), authorDTO.getNationality(), authorDTO.getWebSite(), authorDTO.getEmail()));
			return ResponseEntity.status(HttpStatus.CREATED).body(authorDTO.getFirstName() + " "+authorDTO.getLastName()+", creado correctamente.");
		}
	}
	
	@GetMapping("/read/all")
	public ResponseEntity<?> readAll() {

		List<AuthorDTO> editoriales = authorServiceImp.readAll().stream()
				.map(a -> new AuthorDTO(a.getId(), a.getFirstName(), a.getLastName(), a.getNationality(), a.getWebSite(), a.getEmail())).toList();

		if (editoriales.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(editoriales);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado autores.");
	}

	@GetMapping("/read/id/{id}")
	public ResponseEntity<?> readById(@PathVariable Long id) {

		Optional<AuthorEntity> recovered = authorServiceImp.readById(id);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado author con id: " + id + ".");
	}

	@GetMapping("/read/email/{email}")
	public ResponseEntity<?> readByEmail(@PathVariable String email) {

		Optional<AuthorEntity> recovered = authorServiceImp.readByEmail(email);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se ha encontrado author con email: " + email + ".");
	}	

	@GetMapping("/read/web/{web}")
	public ResponseEntity<?> readByWebSite(@PathVariable String web) {

		Optional<AuthorEntity> recovered = authorServiceImp.readByWebSite(web);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se ha encontrado author con sitio web: " + web + ".");
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody AuthorDTO authorDTO,
			BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");

		Optional<AuthorEntity> recovered = authorServiceImp.readById(id);
		
		boolean emptyFields = authorDTO.getFirstName().isBlank() | authorDTO.getLastName().isBlank() | authorDTO.getNationality() == null 
				| authorDTO.getEmail().isBlank() | authorDTO.getWebSite().isBlank();
		boolean[] repeated = new boolean[2]; // 0: email, 1: webSite

		if (emptyFields)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos.");
		else {
			repeated[0] = authorServiceImp.readAll().stream().anyMatch(a -> a != recovered.get() & a.getEmail().equalsIgnoreCase(authorDTO.getEmail()));
			repeated[1] = authorServiceImp.readAll().stream().anyMatch(a -> a != recovered.get() & a.getWebSite().equalsIgnoreCase(authorDTO.getWebSite()));
		}

		if (repeated[0] | repeated[1]) {
			if(repeated[0])
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(authorDTO.getEmail() + " ya existe, pruebe con otra email.");
			else
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(authorDTO.getEmail() + " ya existe, pruebe con otra sitio web.");
		}
		else {

			AuthorEntity author = recovered.get();
			author.setFirstName(authorDTO.getFirstName());
			author.setLastName(authorDTO.getLastName());
			author.setNationality(authorDTO.getNationality());
			author.setEmail(authorDTO.getEmail());
			author.setWebSite(authorDTO.getWebSite());			

			authorServiceImp.create(author);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(authorDTO.getFirstName() + " "+authorDTO.getLastName()+", creado correctamente.");
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

		Optional<AuthorEntity> recovered = authorServiceImp.readById(id);

		if (recovered.isPresent()) {
			authorServiceImp.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body("author con id: " + id + ", eliminado correctamente.");
		} else
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("no se ha encontrado author con id: " + id + ".");
	}

}
