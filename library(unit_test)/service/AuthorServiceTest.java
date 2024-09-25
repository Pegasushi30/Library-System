package com.project.library.service;

import com.project.library.dto.AuthorDto;
import com.project.library.exception.AuthorNotFoundException;
import com.project.library.mapper.AuthorMapper;
import com.project.library.model.Author;
import com.project.library.repository.AuthorRepository;
import com.project.library.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAuthors() {
        Author author = new Author();
        UUID authorId = UUID.randomUUID();
        AuthorDto authorDto = new AuthorDto(authorId, "Name", "Surname", "Birthdate");

        when(authorRepository.findAll()).thenReturn(Collections.singletonList(author));
        when(authorMapper.toDto(author)).thenReturn(authorDto);

        List<AuthorDto> authors = authorService.getAllAuthors();

        assertNotNull(authors);
        assertEquals(1, authors.size());
        assertEquals(authorDto, authors.get(0));
        verify(authorRepository, times(1)).findAll();
        verify(authorMapper, times(1)).toDto(author);
    }

    @Test
    void testGetAuthorById() {
        Author author = new Author();
        UUID authorId = UUID.randomUUID(); // UUID oluşturuluyor
        AuthorDto authorDto = new AuthorDto(authorId, "Name", "Surname", "Birthdate");

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(authorMapper.toDto(author)).thenReturn(authorDto);

        Optional<AuthorDto> foundAuthor = authorService.getAuthorById(authorId);

        assertTrue(foundAuthor.isPresent());
        assertEquals(authorDto, foundAuthor.get());
        verify(authorRepository, times(1)).findById(authorId);
        verify(authorMapper, times(1)).toDto(author);
    }

    @Test
    void testCreateAuthor() {
        Author author = new Author();
        UUID authorId = UUID.randomUUID(); // UUID oluşturuluyor
        AuthorDto authorDto = new AuthorDto(authorId, "Name", "Surname", "Birthdate");

        when(authorMapper.toModel(authorDto)).thenReturn(author);
        when(authorRepository.save(author)).thenReturn(author);
        when(authorMapper.toDto(author)).thenReturn(authorDto);

        AuthorDto createdAuthor = authorService.createAuthor(authorDto);

        assertNotNull(createdAuthor);
        assertEquals(authorDto, createdAuthor);
        verify(authorMapper, times(1)).toModel(authorDto);
        verify(authorRepository, times(1)).save(author);
        verify(authorMapper, times(1)).toDto(author);
    }

    @Test
    void testUpdateAuthor() {
        Author author = new Author();
        UUID authorId = UUID.randomUUID(); // UUID oluşturuluyor
        AuthorDto authorDto = new AuthorDto(authorId, "UpdatedName", "UpdatedSurname", "UpdatedBirthdate");

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(authorRepository.save(author)).thenReturn(author);
        when(authorMapper.toDto(author)).thenReturn(authorDto);

        AuthorDto updatedAuthor = authorService.updateAuthor(authorId, authorDto);

        assertNotNull(updatedAuthor);
        assertEquals(authorDto, updatedAuthor);
        verify(authorRepository, times(1)).findById(authorId);
        verify(authorRepository, times(1)).save(author);
        verify(authorMapper, times(1)).toDto(author);
    }

    @Test
    void testUpdateAuthor_NotFound() {
        UUID authorId = UUID.randomUUID(); // UUID oluşturuluyor
        AuthorDto authorDto = new AuthorDto(authorId, "Name", "Surname", "Birthdate");

        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        AuthorNotFoundException exception = assertThrows(AuthorNotFoundException.class, () -> {
            authorService.updateAuthor(authorId, authorDto);
        });

        assertEquals("Author not found for ID: " + authorId, exception.getMessage());
        verify(authorRepository, times(1)).findById(authorId);
        verify(authorRepository, times(0)).save(any(Author.class));
    }

    @Test
    void testDeleteAuthor() {
        UUID authorId = UUID.randomUUID(); // UUID oluşturuluyor
        authorService.deleteAuthor(authorId);
        verify(authorRepository, times(1)).deleteById(authorId);
    }
}
