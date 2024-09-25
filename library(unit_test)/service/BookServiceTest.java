package com.project.library.service;

import com.project.library.dto.BookDto;
import com.project.library.exception.BookNotFoundException;
import com.project.library.mapper.BookMapper;
import com.project.library.model.Book;
import com.project.library.repository.BookRepository;
import com.project.library.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks() {
        Book book = new Book();
        BookDto bookDto = new BookDto(1, "Title", 2023, 10, true, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        when(bookRepository.findAll()).thenReturn(Collections.singletonList(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        List<BookDto> books = bookService.getAllBooks();

        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals(bookDto, books.get(0));
        verify(bookRepository, times(1)).findAll();
        verify(bookMapper, times(1)).toDto(book);
    }

    @Test
    void testGetBookById() {
        Book book = new Book();
        BookDto bookDto = new BookDto(1, "Title", 2023, 10, true, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto foundBook = bookService.getBookById(1);

        assertNotNull(foundBook);
        assertEquals(bookDto, foundBook);
        verify(bookRepository, times(1)).findById(1);
        verify(bookMapper, times(1)).toDto(book);
    }

    @Test
    void testCreateBook() {
        Book book = new Book();
        BookDto bookDto = new BookDto(1, "Title", 2023, 10, true, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        when(bookMapper.toModel(bookDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto createdBook = bookService.createBook(bookDto);

        assertNotNull(createdBook);
        assertEquals(bookDto, createdBook);
        verify(bookMapper, times(1)).toModel(bookDto);
        verify(bookRepository, times(1)).save(book);
        verify(bookMapper, times(1)).toDto(book);
    }

    @Test
    void testUpdateBook() {
        Book book = new Book();
        book.setBookAuthors(new HashSet<>());
        book.setBookGenres(new HashSet<>());
        book.setBookPublishers(new HashSet<>());

        BookDto bookDto = new BookDto(1, "UpdatedTitle", 2023, 5, false, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto updatedBook = bookService.updateBook(1, bookDto);

        assertNotNull(updatedBook);
        assertEquals(bookDto, updatedBook);
        verify(bookRepository, times(1)).findById(1);
        verify(bookRepository, times(1)).save(book);
        verify(bookMapper, times(1)).toDto(book);
    }

    @Test
    void testUpdateBook_NotFound() {
        BookDto bookDto = new BookDto(1, "Title", 2023, 10, true, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.updateBook(1, bookDto);
        });

        assertEquals("Book not found for ISBN: 1", exception.getMessage());
        verify(bookRepository, times(1)).findById(1);
        verify(bookRepository, times(0)).save(any(Book.class));
    }

    @Test
    void testDeleteBook() {
        when(bookRepository.existsById(1)).thenReturn(true);
        bookService.deleteBook(1);
        verify(bookRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteBook_NotFound() {
        when(bookRepository.existsById(1)).thenReturn(false);

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.deleteBook(1);
        });

        assertEquals("Book not found for ISBN: 1", exception.getMessage());
        verify(bookRepository, times(1)).existsById(1);
        verify(bookRepository, times(0)).deleteById(1);
    }

    @Test
    void testUpdateBookStock() {
        Book book = new Book();
        book.setReservations(new HashSet<>());
        BookDto bookDto = new BookDto(1, "Title", 2023, 20, true, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto updatedBook = bookService.updateBookStock(1, 20);

        assertNotNull(updatedBook);
        assertEquals(bookDto, updatedBook);
        verify(bookRepository, times(1)).findById(1);
        verify(bookRepository, times(1)).save(book);
        verify(bookMapper, times(1)).toDto(book);
    }


    @Test
    void testUpdateBookStock_NotFound() {
        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.updateBookStock(1, 20);
        });

        assertEquals("Book not found for ISBN: 1", exception.getMessage());
        verify(bookRepository, times(1)).findById(1);
        verify(bookRepository, times(0)).save(any(Book.class));
    }
}