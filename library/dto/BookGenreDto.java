package com.project.library.dto;

import java.util.UUID;

public record BookGenreDto(
        UUID id,
        Integer bookIsbn,
        GenreDto genre
) {
}

