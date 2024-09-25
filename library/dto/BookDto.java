package com.project.library.dto;

import java.util.List;

public record BookDto(
        Integer isbn,
        String title,
        Integer publicationYear,
        Integer availableStock,
        Boolean isReservable,
        List<AuthorDto> authors,
        List<GenreDto> genres,
        List<PublisherDto> publishers,
        List<ReservationDto> reservations
) {}

