package com.learning.gymback.dto;

import lombok.Builder;

@Builder
public record CancelBookingResDto(long bookingId,
                                  long trainerId,
                                  long slotId) {
}
