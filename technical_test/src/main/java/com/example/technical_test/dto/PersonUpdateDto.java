package com.example.technical_test.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PersonUpdateDto(
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        Integer permanentAddressId,
        Integer temporaryAddressId,
        String phoneNumber,
        @Email String email
) {
}
