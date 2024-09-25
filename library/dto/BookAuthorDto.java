package com.project.library.dto;

import java.util.UUID;

public record BookAuthorDto(
        UUID id, // UUID kullanılıyor
        Integer bookIsbn,
        AuthorDto author
) {}
