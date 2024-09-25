package com.project.library.controller;

import com.project.library.dto.BookAuthorDto;
import com.project.library.service.BookAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bookAuthors")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class BookAuthorController {

    private final BookAuthorService bookAuthorService;

    @Autowired
    public BookAuthorController(BookAuthorService bookAuthorService) {
        this.bookAuthorService = bookAuthorService;
    }

    @GetMapping
    public List<BookAuthorDto> getAllBookAuthors() {
        return bookAuthorService.getAllBookAuthors();
    }

    @GetMapping("/{id}")
    public BookAuthorDto getBookAuthorById(@PathVariable UUID id) { 
        return bookAuthorService.getBookAuthorById(id);
    }

    @GetMapping("/book/{isbn}")
    public List<BookAuthorDto> getBookAuthorsByBook(@PathVariable Integer isbn) {
        return bookAuthorService.getBookAuthorsByBook(isbn); 
    }
}


