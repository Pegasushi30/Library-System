package com.project.library.mapper;

import com.project.library.dto.BookGenreDto;
import com.project.library.model.BookGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookGenreMapper {

    private final GenreMapper genreMapper;

    @Autowired
    public BookGenreMapper(GenreMapper genreMapper) {
        this.genreMapper = genreMapper;
    }

    public BookGenreDto toDto(BookGenre bookGenre) {
        return new BookGenreDto(
                bookGenre.getId(),
                bookGenre.getBook().getIsbn(),
                genreMapper.toDto(bookGenre.getGenre())
        );
    }

    public BookGenre toModel(BookGenreDto dto) {
        BookGenre bookGenre = new BookGenre();
        bookGenre.setId(dto.id());
        return bookGenre;
    }
}




