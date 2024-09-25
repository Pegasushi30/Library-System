package com.project.library.service.impl;

import com.project.library.dto.BookGenreDto;
import com.project.library.exception.BookGenreNotFoundException;
import com.project.library.exception.BookNotFoundException;
import com.project.library.mapper.BookGenreMapper;
import com.project.library.model.Book;
import com.project.library.repository.BookGenreRepository;
import com.project.library.repository.BookRepository;
import com.project.library.service.BookGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookGenreServiceImpl implements BookGenreService {

    private final BookGenreRepository bookGenreRepository;
    private final BookGenreMapper bookGenreMapper;
    private final BookRepository bookRepository;

    @Autowired
    public BookGenreServiceImpl(BookGenreRepository bookGenreRepository, BookGenreMapper bookGenreMapper, BookRepository bookRepository) {
        this.bookGenreRepository = bookGenreRepository;
        this.bookGenreMapper = bookGenreMapper;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookGenreDto> getAllBookGenres() {
        return bookGenreRepository.findAll().stream().map(bookGenreMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public BookGenreDto getBookGenreById(UUID id) {
        return bookGenreRepository.findById(id).map(bookGenreMapper::toDto).orElseThrow(() -> new BookGenreNotFoundException("BookGenre not found for ID: " + id));
    }

    @Override
    public List<BookGenreDto> getBookGenresByBook(Integer isbn) {
        Book book = bookRepository.findById(isbn).orElseThrow(() -> new BookNotFoundException("Book not found for ISBN: " + isbn));
        return bookGenreRepository.findByBook(book).stream().map(bookGenreMapper::toDto).collect(Collectors.toList());
    }
}
