package com.project.library.service;

import com.project.library.dto.BookPublisherDto;
import com.project.library.dto.PublisherDto;
import com.project.library.exception.BookNotFoundException;
import com.project.library.exception.BookPublisherNotFoundException;
import com.project.library.mapper.BookPublisherMapper;
import com.project.library.model.Book;
import com.project.library.model.BookPublisher;
import com.project.library.repository.BookPublisherRepository;
import com.project.library.repository.BookRepository;
import com.project.library.service.impl.BookPublisherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID; // UUID kullanımı

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookPublisherServiceTest {

    @Mock
    private BookPublisherRepository bookPublisherRepository;

    @Mock
    private BookPublisherMapper bookPublisherMapper;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookPublisherServiceImpl bookPublisherService;

    private UUID publisherId; // UUID kullanımı

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        publisherId = UUID.randomUUID(); // UUID kullanımı
    }

    @Test
    void testGetAllBookPublishers() {
        BookPublisher bookPublisher = new BookPublisher();
        bookPublisher.setId(publisherId); // UUID kullanımı

        BookPublisherDto bookPublisherDto = new BookPublisherDto(publisherId, 123456, new PublisherDto(publisherId, "Publisher Name", "Publisher Address")); // UUID kullanımı

        when(bookPublisherRepository.findAll()).thenReturn(Collections.singletonList(bookPublisher));
        when(bookPublisherMapper.toDto(bookPublisher)).thenReturn(bookPublisherDto);

        List<BookPublisherDto> result = bookPublisherService.getAllBookPublishers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(bookPublisherDto, result.get(0));
        verify(bookPublisherRepository, times(1)).findAll();
        verify(bookPublisherMapper, times(1)).toDto(bookPublisher);
    }

    @Test
    void testGetBookPublisherById() {
        BookPublisher bookPublisher = new BookPublisher();
        bookPublisher.setId(publisherId); // UUID kullanımı

        BookPublisherDto bookPublisherDto = new BookPublisherDto(publisherId, 123456, new PublisherDto(publisherId, "Publisher Name", "Publisher Address")); // UUID kullanımı

        when(bookPublisherRepository.findById(publisherId)).thenReturn(Optional.of(bookPublisher));
        when(bookPublisherMapper.toDto(bookPublisher)).thenReturn(bookPublisherDto);

        BookPublisherDto result = bookPublisherService.getBookPublisherById(publisherId);

        assertNotNull(result);
        assertEquals(bookPublisherDto, result);
        verify(bookPublisherRepository, times(1)).findById(publisherId); // UUID kullanımı
        verify(bookPublisherMapper, times(1)).toDto(bookPublisher);
    }

    @Test
    void testGetBookPublisherById_NotFound() {
        when(bookPublisherRepository.findById(publisherId)).thenReturn(Optional.empty());

        assertThrows(BookPublisherNotFoundException.class, () -> bookPublisherService.getBookPublisherById(publisherId));
        verify(bookPublisherRepository, times(1)).findById(publisherId); // UUID kullanımı
    }

    @Test
    void testGetBookPublishersByBook() {
        Book book = new Book();
        book.setIsbn(123456);

        BookPublisher bookPublisher = new BookPublisher();
        bookPublisher.setId(publisherId); // UUID kullanımı
        bookPublisher.setBook(book);

        BookPublisherDto bookPublisherDto = new BookPublisherDto(publisherId, 123456, new PublisherDto(publisherId, "Publisher Name", "Publisher Address")); // UUID kullanımı

        when(bookRepository.findById(123456)).thenReturn(Optional.of(book));
        when(bookPublisherRepository.findByBook(book)).thenReturn(Collections.singletonList(bookPublisher));
        when(bookPublisherMapper.toDto(bookPublisher)).thenReturn(bookPublisherDto);

        List<BookPublisherDto> result = bookPublisherService.getBookPublishersByBook(123456);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(bookPublisherDto, result.get(0));
        verify(bookRepository, times(1)).findById(123456);
        verify(bookPublisherRepository, times(1)).findByBook(book);
        verify(bookPublisherMapper, times(1)).toDto(bookPublisher);
    }

    @Test
    void testGetBookPublishersByBook_NotFound() {
        when(bookRepository.findById(123456)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookPublisherService.getBookPublishersByBook(123456));
        verify(bookRepository, times(1)).findById(123456);
    }
}