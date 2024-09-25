package com.project.library.service;

import com.project.library.dto.AuthorDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorService {
    List<AuthorDto> getAllAuthors();
    Optional<AuthorDto> getAuthorById(UUID id); 
    AuthorDto createAuthor(AuthorDto authorDto);
    AuthorDto updateAuthor(UUID id, AuthorDto authorDto); 
    void deleteAuthor(UUID id); 
}






