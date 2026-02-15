package com.learning.gymback.dto;

import lombok.Builder;

@Builder
public record SlotSearchDto(Long startTimeEpochSeconds,
                            Long endTimeEpochSeconds,
                            String trainingType,
                            Long trainerId,
                            String location) {
}

