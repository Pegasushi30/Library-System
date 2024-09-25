package com.project.library.controller;

import com.project.library.dto.BookDto;
import com.project.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{isbn}")
    public BookDto getBookById(@PathVariable Integer isbn) {
        return bookService.getBookById(isbn);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public BookDto createBook(@RequestBody BookDto bookDto) {
        return bookService.createBook(bookDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{isbn}")
    public BookDto updateBook(@PathVariable Integer isbn, @RequestBody BookDto bookDto) {
        return bookService.updateBook(isbn, bookDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{isbn}")
    public void deleteBook(@PathVariable Integer isbn) {
        bookService.deleteBook(isbn);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{isbn}/stock")
    public BookDto updateBookStock(@PathVariable Integer isbn, @RequestParam int stock) {
        return bookService.updateBookStock(isbn, stock);
    }
}



