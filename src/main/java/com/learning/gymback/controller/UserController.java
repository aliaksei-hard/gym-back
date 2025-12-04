package com.learning.gymback.controller;

import com.learning.gymback.dto.UserAdminResponseDto;
import com.learning.gymback.mapper.UserForAdminMapper;
import com.learning.gymback.security.entity.User;
import com.learning.gymback.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

//    GET /users/:id — profile (admin может получить резюме, но не личную инфо у других)
// todo   PUT /users/:id — update profile (user or admin)
    private final UserService userService;
    private final UserForAdminMapper userForAdminMapper;

    @GetMapping("/v1/users")
    public ResponseEntity<UserAdminResponseDto> getUserForAdminByUsername(@RequestParam String username) {
        log.info("/v1/users/{}", username);
        User user = userService.getUserByUsername(username);
        UserAdminResponseDto userAdminResponseDto = userForAdminMapper.toUserAdminResponseDto(user);

        return userAdminResponseDto != null ? ResponseEntity.ok(userAdminResponseDto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/v1/users/{id}")
    public ResponseEntity<UserAdminResponseDto> getUserForAdminById(@RequestParam long id) {
        log.info("/v1/users/{}", id);
        User user = userService.getUserById(id);
        UserAdminResponseDto userAdminResponseDto = userForAdminMapper.toUserAdminResponseDto(user);

        return userAdminResponseDto != null ? ResponseEntity.ok(userAdminResponseDto) : ResponseEntity.notFound().build();
    }
}
