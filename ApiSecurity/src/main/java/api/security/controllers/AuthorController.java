package api.security.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import api.security.dto.AuthorDTO;
import api.security.entities.AuthorEntity;
import api.security.services.AuthorServiceImp;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/authors")
public class AuthorController {

	@Autowired
	private AuthorServiceImp authorServiceImp;

	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody AuthorDTO authorDTO, BindingResult result) {
		
		boolean emptyFields = (authorDTO == null | authorDTO.getFirstName().isBlank() | authorDTO.getLastName().isBlank()
				| authorDTO.getNationality() == null | authorDTO.getWebSite().isBlank() | authorDTO.getEmail().isBlank());

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");
		else if (emptyFields)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos!");
		else if (authorServiceImp.readByWebSite(authorDTO.getWebSite()).isPresent()
				| authorServiceImp.readByEmail(authorDTO.getEmail()).isPresent()) {
			if (authorServiceImp.readByWebSite(authorDTO.getWebSite()).isPresent())
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(authorDTO.getWebSite() + ", ya existe, pruebe con otro web_site!");
			else
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(authorDTO.getEmail() + ", ya existe, pruebe con otro email!");
		} else {

			authorServiceImp.create(new AuthorEntity(authorDTO.getFirstName(), authorDTO.getLastName(), authorDTO.getNationality(), authorDTO.getWebSite(), authorDTO.getEmail()));
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(authorDTO.getFirstName() + " " + authorDTO.getLastName() + ", creado sastifactoriamente.");
		}
	}

	@GetMapping("/read/all")
	public ResponseEntity<?> readAll() {

		List<AuthorDTO> authors = authorServiceImp.readAll().stream().map((a) -> new AuthorDTO(a.getId(), a.getFirstName(), a.getLastName(),
				a.getNationality(), a.getWebSite(), a.getEmail())).toList();

		if (authors.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(authors);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado authors.");
	}

	@GetMapping("/read/id/{id}")
	public ResponseEntity<?> readById(@PathVariable Long id) {

		Optional<AuthorEntity> recovered = authorServiceImp.readById(id);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado author con id: " + id + ".");
	}
	
	@GetMapping("/read/web/{web}")
	public ResponseEntity<?> readByWebSite(@PathVariable String web) {

		Optional<AuthorEntity> recovered = authorServiceImp.readByWebSite(web);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado author con web site: " + web + ".");
	}
	
	@GetMapping("/read/email/{email}")
	public ResponseEntity<?> readByEmail(@PathVariable String email) {

		Optional<AuthorEntity> recovered = authorServiceImp.readByEmail(email);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado author con email: " + email + ".");
	}	

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody AuthorDTO authorDTO, BindingResult result) {
		
		boolean emptyFields = (authorDTO == null | authorDTO.getFirstName().isBlank() | authorDTO.getLastName().isBlank()
				| authorDTO.getNationality() == null | authorDTO.getWebSite().isBlank() | authorDTO.getEmail().isBlank());
		
		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");
		else if (emptyFields)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos!");
		else if (authorServiceImp.readByWebSite(authorDTO.getWebSite()).isPresent()
				| authorServiceImp.readByEmail(authorDTO.getEmail()).isPresent()) {
			if (authorServiceImp.readByWebSite(authorDTO.getWebSite()).isPresent())
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(authorDTO.getWebSite() + ", ya existe, pruebe con otro web_site!");
			else
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(authorDTO.getEmail() + ", ya existe, pruebe con otro email!");
		} else {
			
			AuthorEntity author = null;
			
			if(!authorServiceImp.readById(id).isPresent()) {
				
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body("no existe author con el id: "+id);
			}
			else {
				
			author = authorServiceImp.readById(id).get();
			author.setFirstName(authorDTO.getFirstName());
			author.setLastName(authorDTO.getLastName());
			author.setNationality(authorDTO.getNationality());
			author.setWebSite(authorDTO.getWebSite());
			author.setEmail(authorDTO.getEmail());
				
			authorServiceImp.create(new AuthorEntity(authorDTO.getFirstName(), authorDTO.getLastName(), authorDTO.getNationality(), authorDTO.getWebSite(), authorDTO.getEmail()));
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(authorDTO.getFirstName() + " " + authorDTO.getLastName() + ", actualizada sastifactoriamente.");
			}
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

		Optional<AuthorEntity> recovered = authorServiceImp.readById(id);

		if (recovered.isPresent()) {
			authorServiceImp.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("author con id: " + id + ", eliminado correctamente.");
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado author con id: " + id + ".");
	}

}
