package com.example.technical_test.dto;

import lombok.Builder;

@Builder
public record ContactInfoDto(
        String value,
        Integer personId
) {
}
