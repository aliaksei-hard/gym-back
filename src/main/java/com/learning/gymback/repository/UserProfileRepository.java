package com.learning.gymback.repository;

import com.learning.gymback.entity.user_profiles.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository <UserProfile, Long> {
}
