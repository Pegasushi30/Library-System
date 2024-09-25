package com.project.library.dto;

import java.util.UUID;

public record BookPublisherDto(
        UUID id,
        Integer bookIsbn,
        PublisherDto publisher
) {
}
