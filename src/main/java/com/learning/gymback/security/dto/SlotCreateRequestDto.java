package com.learning.gymback.security.dto;

public record SlotCreateRequestDto(
        long trainerId,
        long start,
        long duration,
        int capacity,
        String type,
        String location,
        String reason) {}