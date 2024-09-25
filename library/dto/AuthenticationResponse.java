package com.project.library.dto;

import java.util.UUID;

public record AuthenticationResponse(
        String accessToken,
        UUID userId) {
}

