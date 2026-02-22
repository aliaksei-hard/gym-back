package com.learning.gymback.security.controller;

import com.learning.gymback.security.dto.UserRegisterResponseDto;
import com.learning.gymback.security.dto.UserAuthRequestDto;
import com.learning.gymback.security.dto.UserRegisterRequestDto;
import com.learning.gymback.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthController {

    // POST /auth/register — { name, email, password } → 201 + { user, token }
    // POST /auth/login — { email, password } → 200 + { user, token }
    // POST /auth/refresh — refresh token

    private final AuthService authService;

    @PostMapping("/v1/auth/register")
    public ResponseEntity<UserRegisterResponseDto> register(
            @RequestBody UserRegisterRequestDto userRegisterRequestDto) {
        log.info("/v1/auth/register: {}", userRegisterRequestDto);
        // TODO check same username/names/email
        UserRegisterResponseDto securityUser = authService.register(userRegisterRequestDto);

        return ResponseEntity.ok(securityUser);
    }

    @PostMapping("/v1/auth/login")
    public ResponseEntity<Map<String, Object>> auth(@RequestBody UserAuthRequestDto dto, HttpServletResponse response) {
        log.info("v1/auth/login: {}", dto);
        Map<String, Object> authResponse = authService.auth(dto);

        // Extract token from response
        String token = (String) authResponse.get("token");

        Cookie cookie = new Cookie("jwt_token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Set to true in production with HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days

        response.addCookie(cookie);


        return ResponseEntity.ok(authResponse);
    }

}
