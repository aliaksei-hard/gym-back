package com.learning.gymback.dto;

import lombok.Builder;

@Builder
public record CancelBookingReqDto(String reason,
                                  long bookingId) {
}
