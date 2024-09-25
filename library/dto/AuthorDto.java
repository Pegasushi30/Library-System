package com.project.library.dto;
import java.util.UUID;

public record AuthorDto(
        UUID id,  // UUID olarak değiştirildi
        String name,
        String surname,
        String birthdate
) {}