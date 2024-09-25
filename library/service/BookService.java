package com.project.library.service;

import com.project.library.dto.BookDto;
import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();
    BookDto getBookById(Integer isbn);
    BookDto createBook(BookDto bookDTO);
    BookDto updateBook(Integer isbn, BookDto bookDTO);
    void deleteBook(Integer isbn);
    BookDto updateBookStock(Integer isbn, int stock);
}





