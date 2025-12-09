package com.learning.gymback.controller;

import com.learning.gymback.dto.BookingCreateReqDto;
import com.learning.gymback.dto.BookingCreatedResDto;
import com.learning.gymback.dto.CancelBookingReqDto;
import com.learning.gymback.dto.CancelBookingResDto;
import com.learning.gymback.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/v1/booking")
    public ResponseEntity<BookingCreatedResDto> createBooking(@RequestBody BookingCreateReqDto dto) {
        BookingCreatedResDto respDto = bookingService.createBooking(dto);

        return ResponseEntity.ok(respDto);
    }

    @PutMapping("/v1/booking/cancel")
    public ResponseEntity<CancelBookingResDto> cancelBooking(CancelBookingReqDto dto) {
        return ResponseEntity.ok(bookingService.cancelBooking(dto));
    }


}
