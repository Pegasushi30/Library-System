package com.project.library.dto;

import java.util.UUID;

public record RoleDto(
        UUID id,
        String roleName
) {
}
