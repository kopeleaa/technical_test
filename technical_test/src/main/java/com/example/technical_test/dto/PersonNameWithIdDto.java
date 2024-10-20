package com.example.technical_test.dto;

import lombok.Builder;

@Builder
public record PersonNameWithIdDto(
        Integer personId,
        String name
) {
}
