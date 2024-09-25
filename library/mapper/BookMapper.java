package com.project.library.mapper;

import com.project.library.dto.*;
import com.project.library.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    private final AuthorMapper authorMapper;
    private final GenreMapper genreMapper;
    private final PublisherMapper publisherMapper;
    private final ReservationMapper reservationMapper;

    @Autowired
    public BookMapper(
                      AuthorMapper authorMapper,
                      GenreMapper genreMapper,
                      PublisherMapper publisherMapper,
                      ReservationMapper reservationMapper) {
        this.authorMapper = authorMapper;
        this.genreMapper = genreMapper;
        this.publisherMapper = publisherMapper;
        this.reservationMapper = reservationMapper;
    }

    public BookDto toDto(Book book) {
        return new BookDto(
                book.getIsbn(), // UUID olarak kullanılıyor
                book.getTitle(),
                book.getPublicationYear(),
                book.getAvailableStock(),
                book.getIsReservable(),
                book.getBookAuthors().stream().map(bookAuthor -> authorMapper.toDto(bookAuthor.getAuthor())).collect(Collectors.toList()),
                book.getBookGenres().stream().map(bookGenre -> genreMapper.toDto(bookGenre.getGenre())).collect(Collectors.toList()),
                book.getBookPublishers().stream().map(bookPublisher -> publisherMapper.toDto(bookPublisher.getPublisher())).collect(Collectors.toList()),
                book.getReservations() != null ? book.getReservations().stream().map(reservationMapper::toDto).collect(Collectors.toList()) : new ArrayList<>()
        );
    }

    public Book toModel(BookDto dto) {
        Book book = new Book();
        book.setIsbn(dto.isbn()); // UUID olarak set ediliyor
        book.setTitle(dto.title());
        book.setPublicationYear(dto.publicationYear());
        book.setAvailableStock(dto.availableStock());
        book.setIsReservable(dto.isReservable());

        book.setBookAuthors(toBookAuthors(book, dto.authors()));
        book.setBookGenres(toBookGenres(book, dto.genres()));
        book.setBookPublishers(toBookPublishers(book, dto.publishers()));

        if (dto.reservations() != null) {
            book.setReservations(dto.reservations().stream()
                    .map(reservationDto -> reservationMapper.toModel(reservationDto, book))
                    .collect(Collectors.toSet()));
        } else {
            book.setReservations(new HashSet<>());
        }

        return book;
    }

    public Set<BookAuthor> toBookAuthors(Book book, List<AuthorDto> authorDto_s) {
        return authorDto_s.stream().map(authorDto -> {
            BookAuthor bookAuthor = new BookAuthor();
            Author author = authorMapper.toModel(authorDto);
            bookAuthor.setAuthor(author);
            bookAuthor.setBook(book);
            return bookAuthor;
        }).collect(Collectors.toSet());
    }

    public Set<BookGenre> toBookGenres(Book book, List<GenreDto> genreDto_s) {
        return genreDto_s.stream().map(genreDto -> {
            BookGenre bookGenre = new BookGenre();
            Genre genre = genreMapper.toModel(genreDto);
            bookGenre.setGenre(genre);
            bookGenre.setBook(book);
            return bookGenre;
        }).collect(Collectors.toSet());
    }

    public Set<BookPublisher> toBookPublishers(Book book, List<PublisherDto> publisherDto_s) {
        return publisherDto_s.stream().map(publisherDto -> {
            BookPublisher bookPublisher = new BookPublisher();
            Publisher publisher = publisherMapper.toModel(publisherDto);
            bookPublisher.setPublisher(publisher);
            bookPublisher.setBook(book);
            return bookPublisher;
        }).collect(Collectors.toSet());
    }
}



















