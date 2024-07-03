package com.ucoban.medsoft.accountserver.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record RegisterDto(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @Email String email,
        @NotBlank String password,
        @NotBlank String phone,
        double weight,
        short height,
        LocalDate birthDate) {
}
