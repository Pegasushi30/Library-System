package com.project.library.service;

import com.project.library.dto.GenreDto;
import com.project.library.mapper.GenreMapper;
import com.project.library.model.Genre;
import com.project.library.repository.GenreRepository;
import com.project.library.service.impl.GenreServiceImpl;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private GenreMapper genreMapper;

    @InjectMocks
    private GenreServiceImpl genreService;

    private UUID genreId; // UUID kullanımı
    private GenreDto genreDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        genreId = UUID.randomUUID(); // UUID oluşturuluyor
        genreDto = new GenreDto(genreId, "Science Fiction"); // UUID kullanılıyor
    }

    @Test
    void testGetAllGenres() {
        Genre genre = new Genre();

        when(genreRepository.findAll()).thenReturn(Collections.singletonList(genre));
        when(genreMapper.toDto(genre)).thenReturn(genreDto);

        List<GenreDto> genres = genreService.getAllGenres();

        assertNotNull(genres);
        assertEquals(1, genres.size());
        assertEquals(genreDto, genres.get(0));
        verify(genreRepository, times(1)).findAll();
        verify(genreMapper, times(1)).toDto(genre);
    }

    @Test
    void testGetGenreById() {
        Genre genre = new Genre();

        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre)); // UUID kullanılıyor
        when(genreMapper.toDto(genre)).thenReturn(genreDto);

        Optional<GenreDto> foundGenre = genreService.getGenreById(genreId); // UUID kullanılıyor

        assertTrue(foundGenre.isPresent());
        assertEquals(genreDto, foundGenre.get());
        verify(genreRepository, times(1)).findById(genreId); // UUID kullanılıyor
        verify(genreMapper, times(1)).toDto(genre);
    }

    @Test
    void testCreateGenre() {
        Genre genre = new Genre();

        when(genreMapper.toModel(genreDto)).thenReturn(genre);
        when(genreRepository.save(genre)).thenReturn(genre);
        when(genreMapper.toDto(genre)).thenReturn(genreDto);

        GenreDto createdGenre = genreService.createGenre(genreDto);

        assertNotNull(createdGenre);
        assertEquals(genreDto, createdGenre);
        verify(genreMapper, times(1)).toModel(genreDto);
        verify(genreRepository, times(1)).save(genre);
        verify(genreMapper, times(1)).toDto(genre);
    }

    @Test
    void testUpdateGenre() {
        Genre genre = new Genre();
        GenreDto updatedGenreDto = new GenreDto(genreId, "Updated Genre"); // UUID kullanılıyor

        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre)); // UUID kullanılıyor
        when(genreRepository.save(genre)).thenReturn(genre);
        when(genreMapper.toDto(genre)).thenReturn(updatedGenreDto);

        GenreDto updatedGenre = genreService.updateGenre(genreId, updatedGenreDto); // UUID kullanılıyor

        assertNotNull(updatedGenre);
        assertEquals(updatedGenreDto, updatedGenre);
        verify(genreRepository, times(1)).findById(genreId); // UUID kullanılıyor
        verify(genreRepository, times(1)).save(genre);
        verify(genreMapper, times(1)).toDto(genre);
    }

    @Test
    void testUpdateGenre_NotFound() {
        when(genreRepository.findById(genreId)).thenReturn(Optional.empty()); // UUID kullanılıyor

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            genreService.updateGenre(genreId, genreDto); // UUID kullanılıyor
        });

        assertEquals("Genre not found", exception.getMessage());
        verify(genreRepository, times(1)).findById(genreId); // UUID kullanılıyor
        verify(genreRepository, times(0)).save(any(Genre.class));
    }

    @Test
    void testDeleteGenre() {
        genreService.deleteGenre(genreId); // UUID kullanılıyor
        verify(genreRepository, times(1)).deleteById(genreId); // UUID kullanılıyor
    }
}