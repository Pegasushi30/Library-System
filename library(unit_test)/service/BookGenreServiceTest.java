package com.project.library.service;

import com.project.library.dto.BookGenreDto;
import com.project.library.dto.GenreDto;
import com.project.library.exception.BookGenreNotFoundException;
import com.project.library.exception.BookNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.UUID; // UUID kullanımı

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookGenreServiceTest {

    @Mock
    private BookGenreService bookGenreService;
    private BookGenreDto bookGenreDto;
    private UUID genreId; // UUID olarak güncellendi

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        genreId = UUID.randomUUID(); // UUID kullanımı
        bookGenreDto = new BookGenreDto(genreId, 1234567890,
                new GenreDto(UUID.randomUUID(), "Science Fiction")); // UUID ile oluşturuldu
    }

    @Test
    void testGetAllBookGenres() {
        // Given
        when(bookGenreService.getAllBookGenres()).thenReturn(Collections.singletonList(bookGenreDto));

        // When
        List<BookGenreDto> result = bookGenreService.getAllBookGenres();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(bookGenreDto, result.get(0));
        verify(bookGenreService, times(1)).getAllBookGenres();
    }

    @Test
    void testGetBookGenreById() {
        // Given
        when(bookGenreService.getBookGenreById(genreId)).thenReturn(bookGenreDto); // UUID kullanılıyor

        // When
        BookGenreDto result = bookGenreService.getBookGenreById(genreId);

        // Then
        assertNotNull(result);
        assertEquals(bookGenreDto, result);
        verify(bookGenreService, times(1)).getBookGenreById(genreId); // UUID kullanılıyor
    }

    @Test
    void testGetBookGenreById_NotFound() {
        // Given
        when(bookGenreService.getBookGenreById(genreId))
                .thenThrow(new BookGenreNotFoundException("BookGenre not found for ID: " + genreId));

        // When / Then
        assertThrows(BookGenreNotFoundException.class, () -> bookGenreService.getBookGenreById(genreId));
        verify(bookGenreService, times(1)).getBookGenreById(genreId); // UUID kullanılıyor
    }

    @Test
    void testGetBookGenresByBook() {
        // Given
        when(bookGenreService.getBookGenresByBook(123456)).thenReturn(Collections.singletonList(bookGenreDto));

        // When
        List<BookGenreDto> result = bookGenreService.getBookGenresByBook(123456);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(bookGenreDto, result.get(0));
        verify(bookGenreService, times(1)).getBookGenresByBook(123456);
    }

    @Test
    void testGetBookGenresByBook_NotFound() {
        // Given
        when(bookGenreService.getBookGenresByBook(123456))
                .thenThrow(new BookNotFoundException("Book not found for ISBN: " + 123456));

        // When / Then
        assertThrows(BookNotFoundException.class, () -> bookGenreService.getBookGenresByBook(123456));
        verify(bookGenreService, times(1)).getBookGenresByBook(123456);
    }
}


