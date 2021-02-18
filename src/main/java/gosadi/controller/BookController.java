/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gosadi.controller;

import gosadi.entity.Author;
import gosadi.entity.Book;
import gosadi.repository.AuthorRepo;
import gosadi.repository.BookRepo;
import gosadi.service.BookService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author alkinoos
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private AuthorRepo authorRepo;
    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private BookService bookService;

    // all books
    @GetMapping//@ResponseBody alla exoume @RestController
    public List<Book> findAll() {
        return bookService.findAll();
    }

    // get book by id 
    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable(value = "id") int id) {
        return bookService.findById(id);
    }
    // save book       but with NULL authors  
//    @PostMapping
//    public ResponseEntity<Book> create(@RequestBody Book book){
//        book = bookRepo.save(book);
//        return ResponseEntity.status(HttpStatus.OK).body(book);
//    }

    //save a book to a specific author
    @PostMapping("/authors/{authorId}")
    public ResponseEntity<Book> createBookByAuthor(@RequestBody Book book, @PathVariable int authorId) {
        Author author = authorRepo.findById(authorId).get();
        book.setAuthor(author);
        book = bookRepo.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    // get books by author id
    @GetMapping("/authors/{authorId}")
    public List<Book> getBooksByAuthor(@PathVariable(value = "authorId") int authorId) {
        List<Book> books = bookRepo.findByAuthorId(authorId);
        return books;
    }

    // update a book of an author id    IPARXI LOGIKO LATHOS , DEN ELEGXOUME TO VIVLIO, OPOTE THA PAEI NA KANEI SET ISOS SE LATHOS VIVLIO
    @PutMapping("{bookId}/authors/{authorId}")
    public ResponseEntity<Book> updateBook(
            @RequestBody Book bookDetails,
            @PathVariable int bookId,
            @PathVariable int authorId) {
        boolean authorExists = authorRepo.existsById(authorId);
        if (!authorExists) {
            return ResponseEntity.notFound().build();
        }

        Book book = bookRepo.findById(bookId).get();
        book.setTitle(bookDetails.getTitle());
        book = bookRepo.save(book);
        return ResponseEntity.status(HttpStatus.OK).body(book);

    }

    // delete a book from an author id @DeleteMapping("/authors/{authorId}/books/bookId}")
    @DeleteMapping("/{bookId}/authors/{authorId}")
    public ResponseEntity<String> deleteBookOfAuthor(@PathVariable int bookId, @PathVariable int authorId) {
        //findBook with id=bookId and author_id=authorId
        Optional<Book> optionalBook = bookRepo.findByIdAndAuthorId(bookId, authorId);
        if (!optionalBook.isPresent()) {//if book !exists return NOT_FOUND
            return ResponseEntity.notFound().build();

        } else {//else deletebook
            bookRepo.delete(optionalBook.get());
            return ResponseEntity.ok("Book deleted");
        }

    }

}
