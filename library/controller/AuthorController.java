package com.project.library.controller;

import java.util.UUID;
import org.springframework.web.bind.annotation.*;
import com.project.library.dto.AuthorDto;
import com.project.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<AuthorDto> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public Optional<AuthorDto> getAuthorById(@PathVariable UUID id) { // UUID kullanılıyor
        return authorService.getAuthorById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public AuthorDto createAuthor(@RequestBody AuthorDto authorDto) {
        return authorService.createAuthor(authorDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public AuthorDto updateAuthor(@PathVariable UUID id, @RequestBody AuthorDto authorDto) { // UUID kullanılıyor
        return authorService.updateAuthor(id, authorDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable UUID id) { // UUID kullanılıyor
        authorService.deleteAuthor(id);
    }
}

