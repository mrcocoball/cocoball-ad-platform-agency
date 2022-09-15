package com.agencyplatformclonecoding.dto;

import com.agencyplatformclonecoding.domain.Category;

import java.time.LocalDateTime;

public record CategoryDto (
        Long id,
        String name,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static CategoryDto of(Long id, String name, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new CategoryDto(id, name, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    // Entity -> dto로 변환
    public static CategoryDto from(Category entity) {
        return new CategoryDto(
                entity.getId(),
                entity.getName(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    // dto -> Entity로 변환
    public Category toEntity() {
        return Category.of(
                name
        );
    }
}