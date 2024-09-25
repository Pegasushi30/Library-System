package com.project.library.mapper;

import com.project.library.dto.GenreDto;
import com.project.library.model.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    public GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getGenreName());
    }

    public Genre toModel(GenreDto genreDTO) {
        Genre genre = new Genre();
        genre.setId(genreDTO.id());
        genre.setGenreName(genreDTO.genreName());
        return genre;
    }
}



