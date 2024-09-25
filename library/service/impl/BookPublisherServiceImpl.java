package com.project.library.service.impl;

import com.project.library.dto.BookPublisherDto;
import com.project.library.exception.BookNotFoundException;
import com.project.library.exception.BookPublisherNotFoundException;
import com.project.library.mapper.BookPublisherMapper;
import com.project.library.model.Book;
import com.project.library.repository.BookPublisherRepository;
import com.project.library.repository.BookRepository;
import com.project.library.service.BookPublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookPublisherServiceImpl implements BookPublisherService {

    private final BookPublisherRepository bookPublisherRepository;
    private final BookPublisherMapper bookPublisherMapper;
    private final BookRepository bookRepository;

    @Autowired
    public BookPublisherServiceImpl(BookPublisherRepository bookPublisherRepository, BookPublisherMapper bookPublisherMapper, BookRepository bookRepository) {
        this.bookPublisherRepository = bookPublisherRepository;
        this.bookPublisherMapper = bookPublisherMapper;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookPublisherDto> getAllBookPublishers() {
        return bookPublisherRepository.findAll().stream().map(bookPublisherMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public BookPublisherDto getBookPublisherById(UUID id) {
        return bookPublisherRepository.findById(id).map(bookPublisherMapper::toDto).orElseThrow(() -> new BookPublisherNotFoundException("BookPublisher not found for ID: " + id));
    }

    @Override
    public List<BookPublisherDto> getBookPublishersByBook(Integer isbn) {
        Book book = bookRepository.findById(isbn).orElseThrow(() -> new BookNotFoundException("Book not found for ISBN: " + isbn));
        return bookPublisherRepository.findByBook(book).stream().map(bookPublisherMapper::toDto).collect(Collectors.toList());
    }
}

