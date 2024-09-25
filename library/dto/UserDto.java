package com.project.library.dto;

import java.util.UUID;

public record UserDto(
        UUID id,
        String username,
        String password,
        String firstname,
        String lastname,
        String email,
        String phoneNumber,
        String birthdate,
        RoleDto role
) {
}
