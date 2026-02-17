package com.rjproj.reserba.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ItemDto (
        String id,
        String name,
        String description,
        BigDecimal price,
        String imageUrl
) {
}
