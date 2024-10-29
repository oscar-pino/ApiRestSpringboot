package api.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.security.services.UserServiceImp;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserServiceImp userServiceImp;

	
	@GetMapping("/read/id/{id}")
	public ResponseEntity<?> readById(Long id) {
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(userServiceImp.readById(id).get());
	}

}
