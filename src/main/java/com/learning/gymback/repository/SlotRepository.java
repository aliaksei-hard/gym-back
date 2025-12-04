package com.learning.gymback.repository;

import com.learning.gymback.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlotRepository extends JpaRepository<Slot, Long> {
}
