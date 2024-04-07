package com.ucoban.medsoft.accountserver.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public record AccountDto(String id,
                         String firstName,
                         String lastName,
                         String email,
                         String phone,
                         LocalDate birthDate,
                         double weight,
                         short height,
                         int age,
                         Set<RoleDto> roles,
                         LocalDateTime createdAt,
                         LocalDateTime updatedAt) {
}
