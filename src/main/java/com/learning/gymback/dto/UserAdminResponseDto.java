package com.learning.gymback.dto;

import com.learning.gymback.security.constants.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserAdminResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private List<Role> roles;
    private String email;


}
