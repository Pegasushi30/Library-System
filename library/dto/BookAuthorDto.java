package com.project.library.dto;

import java.util.UUID;

public record BookAuthorDto(
        UUID id,
        Integer bookIsbn,
        AuthorDto author
) {}
