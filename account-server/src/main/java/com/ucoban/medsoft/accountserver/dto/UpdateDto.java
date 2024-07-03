package com.ucoban.medsoft.accountserver.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UpdateDto(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @Email String email,
        @NotBlank String phone,
        @PositiveOrZero double weight,
        @PositiveOrZero short height,
        @PastOrPresent LocalDate birthDate) {
}
