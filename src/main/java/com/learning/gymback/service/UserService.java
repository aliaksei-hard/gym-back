package com.learning.gymback.service;

import com.learning.gymback.dto.UserAdminResponseDto;
import com.learning.gymback.dto.UserChangeByAdminReqDto;
import com.learning.gymback.entity.user_profiles.Trainer;
import com.learning.gymback.entity.user_profiles.UserProfile;
import com.learning.gymback.repository.UserProfileRepository;
import com.learning.gymback.security.constants.Role;
import com.learning.gymback.security.repository.SecurityUserRepository;
import com.learning.gymback.security.entity.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final SecurityUserRepository securityUserRepository;
    private final UserDetailsService userDetailsService;
    private final UserProfileRepository userProfileRepository;


    public SecurityUser getUserByUsername(String username) {
        return securityUserRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("No user with this username"));
    }

    public SecurityUser getUserById(long id) {
        return securityUserRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No user with this id"));
    }

//    public UserAdminResponseDto changeUserByAdmin(UserChangeByAdminReqDto dto) {
//        SecurityUser securityUser = (SecurityUser) userDetailsService.loadUserByUsername(dto.username());
//        Set<Role> existingRoles = securityUser.getRoles();
//
//        if (dto.roles().contains(Role.TRAINER) && !existingRoles.contains(Role.TRAINER)) {
//            Trainer trainer = Trainer.builder()
//                    .bio(dto.bio())
//                    .phone(dto.phone())
//                    .
//                    .build();
//
//        }
//        existingRoles.addAll(dto.roles());
//
//        UserProfile toBeChanged = securityUser.getProfile();
//
//        toBeChanged.setEmail(dto.email());
//        toBeChanged.setFirstName(dto.firstName());
//        toBeChanged.setLastName(dto.lastName());
//
//
//
//    }

    public List<SecurityUser> getAllUsersByAdmin() {
        return securityUserRepository.findAll();
    }
}
