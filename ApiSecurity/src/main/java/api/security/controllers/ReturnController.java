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

import api.security.dto.ReturnDTO;
import api.security.entities.ReturnEntity;
import api.security.services.ReturnServiceImp;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/returns")
public class ReturnController {

	@Autowired
	private ReturnServiceImp returnServiceImp;

	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody ReturnDTO returnDTO, BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");

		if (returnDTO.getReturnDate() == null | returnDTO.getPenalty() < 0.0f | returnDTO.getPenalty() < 1 | returnDTO.getDaysLate() < 1)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("corregir datos ingresados!");
		
		
		returnServiceImp.create(new ReturnEntity(null, 0f, 0));
		return ResponseEntity.status(HttpStatus.CREATED).body("return creado sastifactoriamente");		
	}
	
	@GetMapping("/read/all")
	public ResponseEntity<?> readAll() {

		List<ReturnDTO> returns = returnServiceImp.readAll().stream()
				.map((r) -> new ReturnDTO(r.getId(), r.getReturnDate(), r.getPenalty(), r.getDaysLate())).toList();

		if (returns.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(returns);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado returns.");
	}

	@GetMapping("/read/id/{id}")
	public ResponseEntity<?> readById(@PathVariable Long id) {

		Optional<ReturnEntity> recovered = returnServiceImp.readById(id);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado return con id: " + id + ".");
	}	

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ReturnDTO returnDTO,
			BindingResult result) {		

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");
		
		Optional<ReturnEntity> recovered = returnServiceImp.readById(id);

		if (returnDTO.getReturnDate() == null | returnDTO.getPenalty() < 0.0f | returnDTO.getPenalty() < 1 | returnDTO.getDaysLate() < 1)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("corregir datos ingresados!");
		
		if(!recovered.isPresent())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("no se ha encontrado return con el id: "+id+"!");
		else {
			ReturnEntity returned = recovered.get();
			returned.setReturnDate(returnDTO.getReturnDate());
			returned.setPenalty(returnDTO.getPenalty());
			returned.setDaysLate(returnDTO.getDaysLate());
			
			returnServiceImp.create(new ReturnEntity(null, 0f, 0));
			return ResponseEntity.status(HttpStatus.OK).body("return con id: "+id+" actualizada correctamente");	
		}
		
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

		Optional<ReturnEntity> recovered = returnServiceImp.readById(id);

		if (recovered.isPresent()) {
			returnServiceImp.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body("return con id: " + id + ", eliminado correctamente.");
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("no se ha encontrado return con id: " + id + ".");
	}
}
