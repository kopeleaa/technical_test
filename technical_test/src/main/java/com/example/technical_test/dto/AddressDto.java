package com.example.technical_test.dto;

import com.example.technical_test.enums.AddressType;

public record AddressDto(
        String zipCode,
        String city,
        String street,
        Integer houseNumber,
        AddressType addressType
) {
}
