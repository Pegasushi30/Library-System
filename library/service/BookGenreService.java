package com.project.library.service;

import com.project.library.dto.BookGenreDto;

import java.util.List;
import java.util.UUID;

public interface BookGenreService {
    List<BookGenreDto> getAllBookGenres();
    BookGenreDto getBookGenreById(UUID id);
    List<BookGenreDto> getBookGenresByBook(Integer isbn);
}




