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

import api.security.dto.UserDTO;
import api.security.entities.UserEntity;
import api.security.services.UserServiceImp;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserServiceImp userServiceImp;

	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody UserDTO userDTO, BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("ha ocurrido un error!");
		
		Optional<UserEntity> recovered = userServiceImp.readByUsername(userDTO.getUsername());

		if (!userDTO.getUsername().isEmpty() & !userDTO.getUsername().isEmpty() & !userDTO.getRoles().isEmpty()) {

			if (recovered.isPresent()) {

				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(userDTO.getUsername()+", ya existe, pruebe con otro.");
			} else {
				userServiceImp
						.create(new UserEntity(userDTO.getUsername(), userDTO.getPassword(), userDTO.getRoles().stream().toList()));
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(userDTO.getUsername() + ", creado sastifactoriamente.");
			}
		} else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("faltan datos.");
	}

	@GetMapping("/read/id/{id}")
	public ResponseEntity<?> readById(@PathVariable Long id) {

		Optional<UserEntity> recovered = userServiceImp.readById(id);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se ha encontrado user con id: " + id + ".");
	}

	@GetMapping("/read/username/{username}")
	public ResponseEntity<?> readByName(@PathVariable String username) {

		Optional<UserEntity> recovered = userServiceImp.readByUsername(username);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se ha encontrado user con username: " + username + ".");
	}

	@GetMapping("/read/all")
	public ResponseEntity<?> readAll() {

		List<UserDTO> users = userServiceImp.readAll().stream().map((u) -> new UserDTO(u.getId(), u.getUsername(), u.getPassword(), u.getRoles())).toList();

		if (users.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(users);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado users.");
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO, BindingResult result) {
		
		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("ha ocurrido un error!");
		
		List<UserEntity> users = userServiceImp.readAll();
		Long lastId = userServiceImp.getLastId();
		

		if (id < 1 | id > lastId | userDTO.getUsername().isEmpty()
				| userDTO.getPassword().isEmpty() | userDTO.getRoles().isEmpty()) {

			if (userDTO.getUsername().isEmpty() | userDTO.getPassword().isEmpty() | userDTO.getRoles().isEmpty())
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(("faltan datos."));
			else
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(("no existe user con id: " + id));
		}
		boolean repeat = false;

		
		UserEntity recovered = userServiceImp.readById(id).get();		

		for (UserEntity ue : users) {

			if (ue.getId() != id) {
				if (userDTO.getUsername().equalsIgnoreCase(ue.getUsername())) {
					repeat = true;
					break;
				}
			}
		}

		if (repeat) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(userDTO.getUsername()+" ya existe, pruebe con otro.");
		} else {
			recovered.setId(userDTO.getId());
			recovered.setUsername(userDTO.getUsername());
			recovered.setPassword(userDTO.getPassword());
			recovered.setRoles(userDTO.getRoles());
			
			userServiceImp.update(recovered);
			repeat = false;
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(userDTO.getUsername() + ", actualizado sastifactoriamente.");
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

		Optional<UserEntity> recovered = userServiceImp.readById(id);

		if (recovered.isPresent()) {
			userServiceImp.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body("user con id: " + id + ", eliminado correctamente.");
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("no se ha encontrado user con id: " + id + ".");
	}

}
