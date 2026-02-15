package com.learning.gymback.repository.search_specs;

import com.learning.gymback.entity.Slot;
import org.springframework.data.jpa.domain.Specification;

public class SlotSpecification {

    public static Specification<Slot> trainerIdSpec(Long trainerId) {
        return (root, query, cb) ->
                cb.equal(root.get("trainer").get("id"), trainerId);
    }
}
