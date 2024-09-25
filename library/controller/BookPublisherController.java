package com.project.library.controller;

import com.project.library.dto.BookPublisherDto;
import com.project.library.service.BookPublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bookPublishers")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class BookPublisherController {

    private final BookPublisherService bookPublisherService;

    @Autowired
    public BookPublisherController(BookPublisherService bookPublisherService) {
        this.bookPublisherService = bookPublisherService;
    }

    @GetMapping
    public List<BookPublisherDto> getAllBookPublishers() {
        return bookPublisherService.getAllBookPublishers();
    }

    @GetMapping("/{id}")
    public BookPublisherDto getBookPublisherById(@PathVariable UUID id) {
        return bookPublisherService.getBookPublisherById(id);
    }

    @GetMapping("/book/{isbn}")
    public List<BookPublisherDto> getBookPublishersByBook(@PathVariable Integer isbn) {
        return bookPublisherService.getBookPublishersByBook(isbn);
    }
}

