package com.project.library.service;

import com.project.library.dto.BookAuthorDto;

import java.util.List;
import java.util.UUID;

public interface BookAuthorService {
    List<BookAuthorDto> getAllBookAuthors();
    BookAuthorDto getBookAuthorById(UUID id); // UUID kullanılıyor
    List<BookAuthorDto> getBookAuthorsByBook(Integer isbn); // ISBN Integer olarak kalıyor
}




