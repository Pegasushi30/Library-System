package com.project.library.service.impl;

import com.project.library.dto.BookAuthorDto;
import com.project.library.exception.BookAuthorNotFoundException;
import com.project.library.exception.BookNotFoundException;
import com.project.library.mapper.BookAuthorMapper;
import com.project.library.model.Book;
import com.project.library.repository.BookAuthorRepository;
import com.project.library.repository.BookRepository;
import com.project.library.service.BookAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookAuthorServiceImpl implements BookAuthorService {

    private final BookAuthorRepository bookAuthorRepository;
    private final BookAuthorMapper bookAuthorMapper;
    private final BookRepository bookRepository;

    @Autowired
    public BookAuthorServiceImpl(BookAuthorRepository bookAuthorRepository, BookAuthorMapper bookAuthorMapper, BookRepository bookRepository) {
        this.bookAuthorRepository = bookAuthorRepository;
        this.bookAuthorMapper = bookAuthorMapper;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookAuthorDto> getAllBookAuthors() {
        return bookAuthorRepository.findAll().stream().map(bookAuthorMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public BookAuthorDto getBookAuthorById(UUID id) {
        return bookAuthorRepository.findById(id)
                .map(bookAuthorMapper::toDto)
                .orElseThrow(() -> new BookAuthorNotFoundException("BookAuthor not found for ID: " + id));
    }

    @Override
    public List<BookAuthorDto> getBookAuthorsByBook(Integer isbn) {
        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book not found for ISBN: " + isbn));
        return bookAuthorRepository.findByBook(book).stream().map(bookAuthorMapper::toDto).collect(Collectors.toList());
    }
}


