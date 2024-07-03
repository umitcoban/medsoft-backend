package com.ucoban.medsoft.accountserver.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AssignDepartmentDto(@NotNull long departmentId, @NotBlank String userId) {
}
