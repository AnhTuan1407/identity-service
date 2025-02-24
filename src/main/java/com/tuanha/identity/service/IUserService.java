package com.tuanha.identity.service;

import java.util.List;

import com.tuanha.identity.dto.request.UserCreateRequest;
import com.tuanha.identity.dto.request.UserUpdateRequest;
import com.tuanha.identity.dto.response.UserResponse;
import com.tuanha.identity.model.User;

public interface IUserService {
    List<User> getAllUsers();
    User createUser(UserCreateRequest request);
    UserResponse updateUser(UserUpdateRequest request, String userId);
    void deleteUser(String userId);
    UserResponse getUser(String userId);
}
