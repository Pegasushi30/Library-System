package com.project.library.service;

import com.project.library.dto.AuthorDto;
import com.project.library.dto.BookAuthorDto;
import com.project.library.exception.BookAuthorNotFoundException;
import com.project.library.exception.BookNotFoundException;
import com.project.library.mapper.BookAuthorMapper;
import com.project.library.model.Book;
import com.project.library.model.BookAuthor;
import com.project.library.repository.BookAuthorRepository;
import com.project.library.repository.BookRepository;
import com.project.library.service.impl.BookAuthorServiceImpl;
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

class BookAuthorServiceTest {

    @Mock
    private BookAuthorRepository bookAuthorRepository;

    @Mock
    private BookAuthorMapper bookAuthorMapper;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookAuthorServiceImpl bookAuthorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBookAuthors() {
        UUID bookAuthorId = UUID.randomUUID(); // UUID kullanılıyor
        BookAuthor bookAuthor = new BookAuthor();
        bookAuthor.setId(bookAuthorId);

        BookAuthorDto bookAuthorDto = new BookAuthorDto(bookAuthorId, 123456, new AuthorDto(UUID.randomUUID(), "FirstName", "LastName", "1990-01-01"));

        when(bookAuthorRepository.findAll()).thenReturn(Collections.singletonList(bookAuthor));
        when(bookAuthorMapper.toDto(bookAuthor)).thenReturn(bookAuthorDto);

        List<BookAuthorDto> result = bookAuthorService.getAllBookAuthors();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(bookAuthorDto, result.get(0));
        verify(bookAuthorRepository, times(1)).findAll();
        verify(bookAuthorMapper, times(1)).toDto(bookAuthor);
    }

    @Test
    void testGetBookAuthorById() {
        UUID bookAuthorId = UUID.randomUUID(); // UUID kullanılıyor
        BookAuthor bookAuthor = new BookAuthor();
        bookAuthor.setId(bookAuthorId);

        BookAuthorDto bookAuthorDto = new BookAuthorDto(bookAuthorId, 123456, new AuthorDto(UUID.randomUUID(), "FirstName", "LastName", "1990-01-01"));

        when(bookAuthorRepository.findById(bookAuthorId)).thenReturn(Optional.of(bookAuthor));
        when(bookAuthorMapper.toDto(bookAuthor)).thenReturn(bookAuthorDto);

        BookAuthorDto result = bookAuthorService.getBookAuthorById(bookAuthorId);

        assertNotNull(result);
        assertEquals(bookAuthorDto, result);
        verify(bookAuthorRepository, times(1)).findById(bookAuthorId);
        verify(bookAuthorMapper, times(1)).toDto(bookAuthor);
    }

    @Test
    void testGetBookAuthorById_NotFound() {
        UUID bookAuthorId = UUID.randomUUID(); // UUID kullanılıyor
        when(bookAuthorRepository.findById(bookAuthorId)).thenReturn(Optional.empty());

        assertThrows(BookAuthorNotFoundException.class, () -> bookAuthorService.getBookAuthorById(bookAuthorId));
        verify(bookAuthorRepository, times(1)).findById(bookAuthorId);
    }

    @Test
    void testGetBookAuthorsByBook() {
        Book book = new Book();
        book.setIsbn(123456);

        UUID bookAuthorId = UUID.randomUUID(); // UUID kullanılıyor
        BookAuthor bookAuthor = new BookAuthor();
        bookAuthor.setId(bookAuthorId);
        bookAuthor.setBook(book);

        BookAuthorDto bookAuthorDto = new BookAuthorDto(bookAuthorId, 123456, new AuthorDto(UUID.randomUUID(), "FirstName", "LastName", "1990-01-01"));

        when(bookRepository.findById(123456)).thenReturn(Optional.of(book));
        when(bookAuthorRepository.findByBook(book)).thenReturn(Collections.singletonList(bookAuthor));
        when(bookAuthorMapper.toDto(bookAuthor)).thenReturn(bookAuthorDto);

        List<BookAuthorDto> result = bookAuthorService.getBookAuthorsByBook(123456);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(bookAuthorDto, result.get(0));
        verify(bookRepository, times(1)).findById(123456);
        verify(bookAuthorRepository, times(1)).findByBook(book);
        verify(bookAuthorMapper, times(1)).toDto(bookAuthor);
    }

    @Test
    void testGetBookAuthorsByBook_NotFound() {
        when(bookRepository.findById(123456)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookAuthorService.getBookAuthorsByBook(123456));
        verify(bookRepository, times(1)).findById(123456);
    }
}
