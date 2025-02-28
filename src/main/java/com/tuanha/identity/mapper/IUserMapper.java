package com.tuanha.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.tuanha.identity.dto.request.UserCreateRequest;
import com.tuanha.identity.dto.request.UserUpdateRequest;
import com.tuanha.identity.dto.response.UserResponse;
import com.tuanha.identity.model.User;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    @Mapping(target = "roles", ignore = true)
    User toUser(UserCreateRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
