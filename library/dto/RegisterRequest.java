package com.project.library.dto;


public record RegisterRequest(
        String username,
        String password,
        String firstname,
        String lastname,
        String email,
        String phoneNumber,
        String birthdate
) {
}

