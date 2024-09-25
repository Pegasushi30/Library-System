package com.project.library.controller;

import com.project.library.dto.BookGenreDto;
import com.project.library.service.BookGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bookGenres")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class BookGenreController {

    private final BookGenreService bookGenreService;

    @Autowired
    public BookGenreController(BookGenreService bookGenreService) {
        this.bookGenreService = bookGenreService;
    }

    @GetMapping
    public List<BookGenreDto> getAllBookGenres() {
        return bookGenreService.getAllBookGenres();
    }

    @GetMapping("/{id}")
    public BookGenreDto getBookGenreById(@PathVariable UUID id) {
        return bookGenreService.getBookGenreById(id);
    }

    @GetMapping("/book/{isbn}")
    public List<BookGenreDto> getBookGenresByBook(@PathVariable Integer isbn) {
        return bookGenreService.getBookGenresByBook(isbn);
    }
}

