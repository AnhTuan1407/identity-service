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
    User toUser(UserCreateRequest request);
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
    UserResponse toUserResponse(User user);
}
