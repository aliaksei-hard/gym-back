package com.learning.gymback.service;

import com.learning.gymback.dto.CancelBookingResDto;
import com.learning.gymback.dto.BookingCreateReqDto;
import com.learning.gymback.dto.BookingCreatedResDto;
import com.learning.gymback.dto.CancelBookingReqDto;
import com.learning.gymback.entity.Booking;
import com.learning.gymback.entity.Slot;
import com.learning.gymback.repository.BookingRepository;
import com.learning.gymback.repository.SlotRepository;
import com.learning.gymback.security.entity.SecurityUser;
import com.learning.gymback.security.repository.SecurityUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SlotRepository slotRepository;
    private final SecurityUserRepository securityUserRepository;

    public BookingCreatedResDto createBooking(BookingCreateReqDto dto) {
        Slot slot = slotRepository.findById(dto.slotId())
                .orElseThrow(() -> new IllegalArgumentException("Slot with this id not found"));
        SecurityUser user = securityUserRepository.findById(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("User with this id not found"));

        if (dto.participantCount() > slot.getCapacity()) {
            throw new IllegalArgumentException("Slot`s capacity is less then participant count");
        }

        slot.setCapacity(slot.getCapacity() - dto.participantCount());

        Booking booking = Booking.builder()
                .participantCount(dto.participantCount())
                .createdAt(Instant.now().getEpochSecond())
                .user(user)
                .build();

        Booking saved = bookingRepository.save(booking);

        return BookingCreatedResDto.builder()
                .id(saved.getId())
                .build();
    }

    public CancelBookingResDto cancelBooking(CancelBookingReqDto dto) {
        Booking toBeCanceled = bookingRepository.findById(dto.bookingId())
                .orElseThrow(() -> new IllegalArgumentException("Booking with this id not found"));

        long secondsUntilStart = toBeCanceled.getSlot().getStartTime() - Instant.now().getEpochSecond();
        if (secondsUntilStart < 6 * 60 * 60) {
            throw new IllegalArgumentException("Trying to cancel less than 6 hours before start.");
        }

        //restoring capacity
        Slot slot = toBeCanceled.getSlot();
        slot.setCapacity(slot.getCapacity() + toBeCanceled.getParticipantCount());

        toBeCanceled.setCancelReason(dto.reason());

        return CancelBookingResDto.builder()
                .bookingId(toBeCanceled.getId())
                .slotId(slot.getId())
                .trainerId(slot.getTrainer().getId())
                .build();
    }
}
