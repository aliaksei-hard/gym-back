package com.learning.gymback.security.service;

import com.learning.gymback.security.constants.Role;
import com.learning.gymback.security.dto.UserAuthRequestDto;
import com.learning.gymback.security.dto.UserRegisterRequestDto;
import com.learning.gymback.security.entity.User;
import com.learning.gymback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;

    public User register(UserRegisterRequestDto dto) {
        User user = mapToUser(dto);
        return userRepository.save(user);
    }

    public String auth(UserAuthRequestDto dto) {
        User user = (User) userDetailsService.loadUserByUsername(dto.getUsername());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        return jwtService.generateJwtToken(dto.getUsername());
    }

    private User mapToUser(UserRegisterRequestDto dto) {
        return User.builder()
                .username(dto.getUsername())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(List.of(Role.USER))
                .build();
    }

}
