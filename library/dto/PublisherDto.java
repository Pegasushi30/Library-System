package com.project.library.dto;

import java.util.UUID;

public record PublisherDto(
        UUID id,
        String name,
        String address
) {
}
