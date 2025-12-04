package com.learning.gymback.service;

import com.learning.gymback.entity.Slot;
import com.learning.gymback.mapper.SlotMapper;
import com.learning.gymback.repository.SlotRepository;
import com.learning.gymback.repository.UserRepository;
import com.learning.gymback.security.dto.SlotCreateRequestDto;
import com.learning.gymback.security.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlotService {

    private final SlotRepository slotRepository;
    private final UserRepository userRepository;
    private final SlotMapper slotMapper;


    public Slot createSlot(SlotCreateRequestDto dto) {
        User trainer = userRepository.findById(dto.trainerId())
                .orElseThrow(() -> new IllegalArgumentException("No trainer with this id found"));

        Slot slot = slotMapper.toEntity(dto);
        slot.setTrainer(trainer);
        slot.setCreatedBy(trainer); //after it should be user from token???
        Slot saved = slotRepository.save(slot);

        return saved;
    }

    public List<Slot> getAllSlots() {
        return slotRepository.findAll();
    }


}
