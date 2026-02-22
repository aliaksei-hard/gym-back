package com.learning.gymback.repository.search_specs;

import com.learning.gymback.entity.Slot;
import org.springframework.data.jpa.domain.Specification;

public class SlotSpecification {

    public static Specification<Slot> trainerIdSpec(Long trainerId) {
        return (root, query, cb) ->
                cb.equal(root.get("trainer").get("id"), trainerId);
    }

    public static Specification<Slot> startTimeAfterSpec(Long startTimeEpochSeconds) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("startTime"), startTimeEpochSeconds);
    }

    public static Specification<Slot> endTimeBeforeSpec(Long endTimeEpochSeconds) {
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("endTime"), endTimeEpochSeconds);
    }

    public static Specification<Slot> trainingTypeSpec(String trainingType) {
        return (root, query, cb) ->
                cb.equal(root.get("type"), trainingType);
    }

    public static Specification<Slot> locationSpec(String location) {
        return (root, query, cb) ->
                cb.equal(root.get("location"), location);
    }
}
