package com.project.library.dto;

import java.util.UUID;

public record ReservationDto(
        UUID id,
        Boolean isBorrow,
        Integer bookIsbn,
        UUID userId
) {}

