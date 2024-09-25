package com.project.library.dto;

import java.util.UUID;

public record RegisterResponse(
        String token,
        UUID userId
) {
}

