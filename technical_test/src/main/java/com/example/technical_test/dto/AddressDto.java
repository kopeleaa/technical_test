package com.example.technical_test.dto;

import com.example.technical_test.enums.AddressType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AddressDto(
        @NotNull String zipCode,
        @NotNull String city,
        @NotNull String street,
        @NotNull Integer houseNumber
) {
}
