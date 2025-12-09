package com.learning.gymback.dto;

import lombok.Builder;

@Builder
public record BookingCreateReqDto(long slotId,
                                  Long userId,
                                  int participantCount) {
}
