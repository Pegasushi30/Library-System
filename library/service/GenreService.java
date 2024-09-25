package com.project.library.service;

import com.project.library.dto.GenreDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenreService {
    List<GenreDto> getAllGenres();
    Optional<GenreDto> getGenreById(UUID id);
    GenreDto createGenre(GenreDto genreDto);
    GenreDto updateGenre(UUID id, GenreDto genreDto);
    void deleteGenre(UUID id);
}

