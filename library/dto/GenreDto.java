package com.project.library.dto;

import java.util.UUID;

public record GenreDto(
        UUID id,
        String genreName
) {
}
