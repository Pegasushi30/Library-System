package com.project.library.service.impl;

import com.project.library.dto.GenreDto;
import com.project.library.exception.GenreNotFoundException;
import com.project.library.mapper.GenreMapper;
import com.project.library.model.Genre;
import com.project.library.repository.GenreRepository;
import com.project.library.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository, GenreMapper genreMapper) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
    }

    @Override
    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll().stream().map(genreMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<GenreDto> getGenreById(UUID id) {
        return genreRepository.findById(id).map(genreMapper::toDto);
    }

    @Override
    public GenreDto createGenre(GenreDto genreDto) {
        Genre genre = genreMapper.toModel(genreDto);
        Genre savedGenre = genreRepository.save(genre);
        return genreMapper.toDto(savedGenre);
    }

    @Override
    public GenreDto updateGenre(UUID id, GenreDto genreDto) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new GenreNotFoundException("Genre not found"));
        genre.setGenreName(genreDto.genreName());
        Genre updatedGenre = genreRepository.save(genre);
        return genreMapper.toDto(updatedGenre);
    }

    @Override
    public void deleteGenre(UUID id) {
        genreRepository.deleteById(id);
    }
}

