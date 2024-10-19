package com.example.technical_test.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record PersonRequestDto(
        @NotNull(message = "Field cannot be null")
        @NotBlank(message = "Field cannot be empty")
        @Size(min = 4, message = "Minimum length: 4") String firstName,
        @NotNull(message = "Field cannot be null")
        @NotBlank(message = "Field cannot be empty")
        @Size(min = 2, message = "Minimum length: 2") String lastName
) {
}
