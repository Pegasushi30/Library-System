package com.project.library.service.impl;

import com.project.library.dto.BookDto;
import com.project.library.exception.BookNotFoundException;
import com.project.library.mapper.BookMapper;
import com.project.library.model.Book;
import com.project.library.repository.BookRepository;
import com.project.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public BookDto getBookById(Integer isbn) {
        return bookRepository.findById(isbn).map(bookMapper::toDto)
                .orElseThrow(() -> new BookNotFoundException("Book not found for ISBN: " + isbn));
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book book = bookMapper.toModel(bookDto);
        bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto updateBook(Integer isbn, BookDto bookDto) {
        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book not found for ISBN: " + isbn));

        book.setTitle(bookDto.title());
        book.setPublicationYear(bookDto.publicationYear());
        book.setAvailableStock(bookDto.availableStock());
        book.setIsReservable(bookDto.isReservable());

        if (book.getBookAuthors() != null) {
            book.getBookAuthors().clear();
            book.getBookAuthors().addAll(bookMapper.toBookAuthors(book, bookDto.authors()));
        } else {
            book.setBookAuthors(bookMapper.toBookAuthors(book, bookDto.authors()));
        }

        if (book.getBookGenres() != null) {
            book.getBookGenres().clear();
            book.getBookGenres().addAll(bookMapper.toBookGenres(book, bookDto.genres()));
        } else {
            book.setBookGenres(bookMapper.toBookGenres(book, bookDto.genres()));
        }

        if (book.getBookPublishers() != null) {
            book.getBookPublishers().clear();
            book.getBookPublishers().addAll(bookMapper.toBookPublishers(book, bookDto.publishers()));
        } else {
            book.setBookPublishers(bookMapper.toBookPublishers(book, bookDto.publishers()));
        }

        bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    public void deleteBook(Integer isbn) {
        if (!bookRepository.existsById(isbn)) {
            throw new BookNotFoundException("Book not found for ISBN: " + isbn);
        }
        bookRepository.deleteById(isbn);
    }

    @Override
    public BookDto updateBookStock(Integer isbn, int newStock) {
        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book not found for ISBN: " + isbn));

        // Mevcut rezervasyonları hesaba kat
        long currentReservations = book.getReservations().stream()
                .filter(reservation -> reservation.getReservationEndDate().isAfter(LocalDate.now()))
                .count();

        // Yeni stok miktarını hesapla
        int remainingStock = newStock - (int) currentReservations;
        book.setAvailableStock(Math.max(remainingStock, 0)); // Negatif stok olmasını engelle
        bookRepository.save(book);

        return bookMapper.toDto(book);
    }
}