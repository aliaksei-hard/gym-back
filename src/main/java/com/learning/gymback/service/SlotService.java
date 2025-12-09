package com.learning.gymback.service;

import com.learning.gymback.dto.SlotCreateRequestDto;
import com.learning.gymback.entity.Slot;
import com.learning.gymback.mapper.SlotMapper;
import com.learning.gymback.repository.SlotRepository;
import com.learning.gymback.security.constants.Role;
import com.learning.gymback.security.entity.SecurityUser;
import com.learning.gymback.security.repository.SecurityUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlotService {

    private final SlotRepository slotRepository;
    private final SecurityUserRepository securityUserRepository;
    private final SlotMapper slotMapper;


    public Slot createSlot(SlotCreateRequestDto dto) {

        checkSlotTime(dto);

        SecurityUser trainer = securityUserRepository.findById(dto.trainerId())
                .orElseThrow(() -> new IllegalArgumentException("No trainer with this id found"));

        if (!trainer.getRoles().contains(Role.TRAINER)) {
            throw new IllegalArgumentException("User with this Id doesn`t have role TRAINER");
        }

        List<Slot> conflicts = slotRepository.findTimeConflicts(trainer, dto.startTime(), dto.endTime());
        if (conflicts != null && !conflicts.isEmpty()) {
            throw new IllegalArgumentException("There are already slots in this time period, reschedule");
        }

        Slot slot = slotMapper.toEntity(dto);
        slot.setTrainer(trainer);

        Slot saved = slotRepository.save(slot);

        return saved;
    }

    public List<Slot> getAllSlots() {
        return slotRepository.findAll();
    }

    public boolean deleteSlot(long id) {
        Optional<Slot> toBeDeleted = slotRepository.findById(id);
        if (toBeDeleted.isEmpty()) {
            return false;
        }
        slotRepository.delete(toBeDeleted.get());

        return true;
    }

    public void checkSlotTime(SlotCreateRequestDto dto) {
        long nowEpoch = Instant.now().getEpochSecond();
        if (dto.startTime() < nowEpoch) {
            throw new IllegalArgumentException("Slot cannot be created in the past");
        }

        if (dto.duration() % 1800 != 0 || dto.duration() > 7200) {
            throw new IllegalArgumentException("");
        }

        LocalDateTime start = Instant.ofEpochSecond(dto.startTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        int minute = start.getMinute();
        int second = start.getSecond();
        int nano = start.getNano();
        if (!((minute % 30 == 0) && second == 0 && nano == 0)) {
            throw new IllegalArgumentException("Slot must start on the hour or half hour (e.g. 13:00 or 13:30)");
        }

        if (dto.duration() % 1800 != 0) {
            throw new IllegalArgumentException("Slot duration step is not 30 minutes!");
        }

        LocalDateTime dayStart = start.withHour(7).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime dayEnd = start.withHour(22).withMinute(0).withSecond(0).withNano(0);

        if (start.isBefore(dayStart)) {
            throw new IllegalArgumentException("Slot must start no earlier than 07:00");
        }

        LocalDateTime end = start.plusSeconds(dto.duration());
        if (end.isAfter(dayEnd)) {
            throw new IllegalArgumentException("Slot must end no later than 22:00");
        }
    }

}
