package com.example.technical_test.dto;

import lombok.Builder;

@Builder
public record PersonResponseDto(
        Integer personId,
        String name
) {
}
