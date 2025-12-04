package com.learning.gymback.service;

import com.learning.gymback.repository.UserRepository;
import com.learning.gymback.security.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("No user with this username"));
    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No user with this id"));
    }

    public List<User> getAllUsersByAdmin() {
        return userRepository.findAll();
    }
}
