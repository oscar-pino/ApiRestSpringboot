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

import api.security.dto.BookDTO;
import api.security.entities.AuthorEntity;
import api.security.entities.BookEntity;
import api.security.entities.CategoryEntity;
import api.security.entities.EditorialEntity;
import api.security.services.AuthorServiceImp;
import api.security.services.BookServiceImp;
import api.security.services.CategoryServiceImp;
import api.security.services.EditorialServiceImp;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	private BookServiceImp bookServiceImp;

	@Autowired
	private EditorialServiceImp editorialServiceImp;

	@Autowired
	private AuthorServiceImp authorServiceImp;

	@Autowired
	private CategoryServiceImp categoryServiceImp;

	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody BookDTO bookDTO, BindingResult result) {

		boolean emptyFields = (bookDTO == null | bookDTO.getTitle().isBlank() | bookDTO.getIsbm().isBlank()
				| bookDTO.getEditorial() == null | bookDTO.getCategory() == null | bookDTO.getAuthor() == null);

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");
		else if (emptyFields)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos!");
		else if (bookServiceImp.readByTitle(bookDTO.getTitle()).isPresent()
				| bookServiceImp.readByIsbm(bookDTO.getIsbm()).isPresent()) {
			if (bookServiceImp.readByTitle(bookDTO.getTitle()).isPresent())
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(bookDTO.getTitle() + ", ya existe, pruebe con otro title!");
			else
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(bookDTO.getIsbm() + ", ya existe, pruebe con otro isbm!");
		} else {

			bookServiceImp.create(new BookEntity(bookDTO.getTitle(), bookDTO.getIsbm(), bookDTO.getQuantity(),
					bookDTO.getEditorial(), bookDTO.getCategory(), bookDTO.getAuthor()));
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(bookDTO.getIsbm() + ": " + bookDTO.getTitle() + ", creado sastifactoriamente.");
		}
	}

	@GetMapping("/read/all")
	public ResponseEntity<?> readAll() {

		List<BookDTO> books = bookServiceImp.readAll().stream().map((b) -> new BookDTO(b.getId(), b.getTitle(),
				b.getIsbm(), b.getQuantity(), b.getEditorial(), b.getCategory(), b.getAuthor())).toList();

		if (books.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(books);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado books.");
	}

	@GetMapping("/read/id/{id}")
	public ResponseEntity<?> readById(@PathVariable Long id) {

		Optional<BookEntity> recovered = bookServiceImp.readById(id);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado book con id: " + id + ".");
	}

	@GetMapping("/read/title/{title}")
	public ResponseEntity<?> readByTitle(@PathVariable String title) {

		Optional<BookEntity> recovered = bookServiceImp.readByTitle(title);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado book con title: " + title + ".");
	}

	@GetMapping("/read/isbm/{isbm}")
	public ResponseEntity<?> readByIsbm(@PathVariable String isbm) {

		Optional<BookEntity> recovered = bookServiceImp.readByIsbm(isbm);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado book con ibsm: " + isbm + ".");
	}

	@GetMapping("/read/all/editorialId/{editorialId}")
	public ResponseEntity<?> readAllByEditorialId(@PathVariable Long editorialId) {

		Optional<EditorialEntity> editorial = editorialServiceImp.readById(editorialId);

		if (editorial.isPresent() & bookServiceImp.readAllByEditorial(editorial.get()).size() > 0) {

			List<BookDTO> books = bookServiceImp.readAllByEditorial(editorial.get()).stream()
					.map((b) -> new BookDTO(b.getId(), b.getTitle(), b.getIsbm(), b.getQuantity(), b.getEditorial(),
							b.getCategory(), b.getAuthor()))
					.toList();

			return ResponseEntity.status(HttpStatus.ACCEPTED).body(books);
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("no se han encontrado books con editorial_id: " + editorialId);
	}

	@GetMapping("/read/all/authorId/{authorId}")
	public ResponseEntity<?> readAllByAuthorId(@PathVariable Long authorId) {

		Optional<AuthorEntity> author = authorServiceImp.readById(authorId);

		if (author.isPresent() & bookServiceImp.readAllByAuthor(author.get()).size() > 0) {

			List<BookDTO> books = bookServiceImp.readAllByAuthor(author.get()).stream()
					.map((b) -> new BookDTO(b.getId(), b.getTitle(), b.getIsbm(), b.getQuantity(), b.getEditorial(),
							b.getCategory(), b.getAuthor()))
					.toList();

			return ResponseEntity.status(HttpStatus.ACCEPTED).body(books);
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("no se han encontrado books con author_id: " + authorId);
	}

	@GetMapping("/read/all/categoryId/{categoryId}")
	public ResponseEntity<?> readAllByCategoryId(@PathVariable Long categoryId) {

		Optional<CategoryEntity> category = categoryServiceImp.readById(categoryId);

		if (category.isPresent() & bookServiceImp.readAllByCategory(category.get()).size() > 0) {

			List<BookDTO> books = bookServiceImp.readAllByCategory(category.get()).stream()
					.map((b) -> new BookDTO(b.getId(), b.getTitle(), b.getIsbm(), b.getQuantity(), b.getEditorial(),
							b.getCategory(), b.getAuthor()))
					.toList();

			return ResponseEntity.status(HttpStatus.ACCEPTED).body(books);
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("no se han encontrado books con category_id: " + categoryId);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO, BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");

		boolean emptyFields = bookDTO == null | bookDTO.getTitle().isBlank() | bookDTO.getIsbm().isBlank()
				| bookDTO.getEditorial() == null | bookDTO.getCategory() == null | bookDTO.getAuthor() == null;

		List<BookEntity> books = bookServiceImp.readAll();

		Optional<BookEntity> recovered = bookServiceImp.readById(id);
		boolean[] repeatedData = new boolean[2]; // 0-> title, 1-> isbm

		if (emptyFields)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("faltan datos!");
		else if (!books.isEmpty()) {
				
				books.forEach((b) -> {if(recovered.get() != b & b.getTitle().equalsIgnoreCase(bookDTO.getTitle())) repeatedData[0] = true;
				if(recovered.get() != b & b.getIsbm().equalsIgnoreCase(bookDTO.getIsbm())) repeatedData[1] = true;});


				if (repeatedData[0])
					return ResponseEntity.status(HttpStatus.CONFLICT)
							.body(bookDTO.getTitle() + ", ya existe, pruebe con otro title!");
				else if (repeatedData[1])
					return ResponseEntity.status(HttpStatus.CONFLICT)
							.body(bookDTO.getIsbm() + ", ya existe, pruebe con otro isbm!");	
			}
		
		BookEntity be = recovered.get();
		be.setTitle(bookDTO.getTitle());
		be.setIsbm(bookDTO.getIsbm());
		be.setAuthor(bookDTO.getAuthor());
		be.setCategory(bookDTO.getCategory());
		be.setEditorial(bookDTO.getEditorial());
		be.setQuantity(12);
			
		bookServiceImp.create(recovered.get());
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(bookDTO.getIsbm() + ": " + bookDTO.getTitle() + ", actualizada sastifactoriamente.");
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

		Optional<BookEntity> recovered = bookServiceImp.readById(id);

		if (recovered.isPresent()) {
			bookServiceImp.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("book con id: " + id + ", eliminado correctamente.");
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado book con id: " + id + ".");
	}

}
