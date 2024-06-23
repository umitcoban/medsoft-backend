package com.ucoban.medsoft.accountserver.dto;

import jakarta.validation.constraints.NotBlank;

public record PasswordDto(@NotBlank  String password) {
}
