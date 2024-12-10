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

import api.security.dto.BookDTO;
import api.security.entities.BookEntity;
import api.security.services.BookServiceImp;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {
	
	@Autowired
	private BookServiceImp bookServiceImp;

	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody BookDTO bookDTO, BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");

		boolean[] repeated = new boolean[2]; // 0: title, 1: isbm
		boolean emptyFields = bookDTO.getTitle().isBlank() | bookDTO.getIsbm().isBlank() | bookDTO.getEditorial() == null 
				| bookDTO.getCategory() == null | bookDTO.getAuthor() == null;
		
		if (emptyFields)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos.");
		else if(bookDTO.getQuantity() < 0)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("la cantidad debe ser mayor o igual a cero.");
		else {
			repeated[0] = bookServiceImp.readAll().stream()
					.anyMatch(b -> b.getTitle().equalsIgnoreCase(bookDTO.getTitle()));
			
			repeated[1] = bookServiceImp.readAll().stream()
					.anyMatch(b -> b.getIsbm().equalsIgnoreCase(bookDTO.getIsbm()));
		}

		if (repeated[0] | repeated[1]) {
			
			if(repeated[0])
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(bookDTO.getTitle() + " ya existe, pruebe con otro title.");
			else
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(bookDTO.getTitle() + " ya existe, pruebe con otro isbm.");
		}else {
			
			bookServiceImp.create(new BookEntity(bookDTO.getTitle(), bookDTO.getIsbm(), bookDTO.getQuantity(), 
					bookDTO.getEditorial(), bookDTO.getCategory(), bookDTO.getAuthor()));
			return ResponseEntity.status(HttpStatus.CREATED).body(bookDTO.getTitle() + " creado correctamente.");
		}
	}
	
	@GetMapping("/read/all")
	public ResponseEntity<?> readAll() {

		List<BookDTO> books = bookServiceImp.readAll().stream()
				.map(b -> new BookDTO(b.getId(), b.getTitle(), b.getIsbm(), b.getQuantity(), b.getEditorial(), b.getCategory(), b.getAuthor())).toList();

		if (books.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(books);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado libros.");
	}
	
	@GetMapping("/read/all/editorial_name/{name}")
	public ResponseEntity<?> readAllByEditorialName(@PathVariable String name) {

		List<BookDTO> books = bookServiceImp.readAllByEditorialName(name).stream()
				.map(b -> new BookDTO(b.getId(), b.getTitle(), b.getIsbm(), b.getQuantity(), b.getEditorial(), b.getCategory(), b.getAuthor())).toList();

		if (books.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(books);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado libros con nombre de editorial: "+name+".");
	}	
	
	@GetMapping("/read/all/author_name/{name}")
	public ResponseEntity<?> readAllByAuthorName(@PathVariable String name) {

		List<BookDTO> books = bookServiceImp.readAllByAuthorName(name).stream()
				.map(b -> new BookDTO(b.getId(), b.getTitle(), b.getIsbm(), b.getQuantity(), b.getEditorial(), b.getCategory(), b.getAuthor())).toList();

		if (books.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(books);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado libros con nombre de autor: "+name+".");
	}
	
	@GetMapping("/read/all/category_name/{name}")
	public ResponseEntity<?> readAllByCategoryName(@PathVariable String name) {

		List<BookDTO> books = bookServiceImp.readAllByCategoryName(name).stream()
				.map(b -> new BookDTO(b.getId(), b.getTitle(), b.getIsbm(), b.getQuantity(), b.getEditorial(), b.getCategory(), b.getAuthor())).toList();

		if (books.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(books);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado libros con nombre de categoria "+name+".");
	}

	@GetMapping("/read/id/{id}")
	public ResponseEntity<?> readById(@PathVariable Long id) {

		Optional<BookEntity> recovered = bookServiceImp.readById(id);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado libro con id: " + id + ".");
	}

	@GetMapping("/read/title/{title}")
	public ResponseEntity<?> readByName(@PathVariable String title) {

		Optional<BookEntity> recovered = bookServiceImp.readByTitle(title);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se ha encontrado libro con titulo: " + title + ".");
	}	
	
	@GetMapping("/read/isbm/{isbm}")
	public ResponseEntity<?> readByIsbm(@PathVariable String isbm) {

		Optional<BookEntity> recovered = bookServiceImp.readByIsbm(isbm);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se ha encontrado libro con isbm: " + isbm + ".");
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO,
			BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");

		boolean[] repeated = new boolean[2]; // 0: title, 1: isbm
		boolean emptyFields = bookDTO.getTitle().isBlank() | bookDTO.getIsbm().isBlank() | bookDTO.getEditorial() == null 
				| bookDTO.getCategory() == null | bookDTO.getAuthor() == null;
		Optional<BookEntity> recovered = bookServiceImp.readById(id);

		if (emptyFields)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos.");
		else if(bookDTO.getQuantity() < 0)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("la cantidad debe ser mayor o igual a cero.");
		else if(!recovered.isPresent())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("no se ha encontrado libro con id: "+id);
		else {
			repeated[0] = bookServiceImp.readAll().stream()
					.anyMatch(b -> b.getTitle().equalsIgnoreCase(bookDTO.getTitle()));
			
			repeated[1] = bookServiceImp.readAll().stream()
					.anyMatch(b -> b.getIsbm().equalsIgnoreCase(bookDTO.getIsbm()));
		}

		if (repeated[0] | repeated[1]) {
			
			if(repeated[0])
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(bookDTO.getTitle() + " ya existe, pruebe con otro title.");
			else
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(bookDTO.getTitle() + " ya existe, pruebe con otro isbm.");
		}else {

			BookEntity book = recovered.get();
			book.setTitle(bookDTO.getTitle());
			book.setIsbm(bookDTO.getIsbm());
			book.setAuthor(bookDTO.getAuthor());
			book.setCategory(bookDTO.getCategory());
			book.setEditorial(bookDTO.getEditorial());
			book.setQuantity(bookDTO.getQuantity());

			bookServiceImp.create(book);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(bookDTO.getTitle() + " actualizado correctamente.");
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

		Optional<BookEntity> recovered = bookServiceImp.readById(id);

		if (recovered.isPresent()) {
			bookServiceImp.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body("libro con id: " + id + ", eliminado correctamente.");
		} else
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("no se ha encontrado libro con id: " + id + ".");
	}	
}
