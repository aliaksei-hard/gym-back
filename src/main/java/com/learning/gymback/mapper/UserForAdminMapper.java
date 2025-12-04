package com.learning.gymback.mapper;

import com.learning.gymback.dto.UserAdminResponseDto;
import com.learning.gymback.security.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserForAdminMapper {

    UserAdminResponseDto toUserAdminResponseDto(User user);

}
