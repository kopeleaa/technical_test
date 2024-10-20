package com.example.technical_test.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PersonDataDto(
        @NotNull(message = "Field cannot be null")
        @NotBlank(message = "Field cannot be empty")
        @Size(min = 4, message = "Minimum length: 4") String firstName,
        @NotNull(message = "Field cannot be null")
        @NotBlank(message = "Field cannot be empty")
        @Size(min = 2, message = "Minimum length: 2") String lastName,
        @NotNull(message = "Field cannot be null")
        @Past(message = "Date must be in the past") LocalDate dateOfBirth,
        Integer permanentAddressId,
        Integer temporaryAddressId,
        String phoneNumber,
        @Email String email
) {
}
