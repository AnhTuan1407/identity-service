package com.tuanha.identity.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tuanha.identity.constant.PredefinedRole;
import com.tuanha.identity.dto.request.UserCreateRequest;
import com.tuanha.identity.dto.request.UserUpdateRequest;
import com.tuanha.identity.dto.response.UserResponse;
import com.tuanha.identity.exception.AppException;
import com.tuanha.identity.exception.ErrorCode;
import com.tuanha.identity.mapper.IUserMapper;
import com.tuanha.identity.model.Permission;
import com.tuanha.identity.model.Role;
import com.tuanha.identity.model.User;
import com.tuanha.identity.repository.IPermissionRepository;
import com.tuanha.identity.repository.IRoleRepository;
import com.tuanha.identity.repository.IUserRepository;
import com.tuanha.identity.service.IUserService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.AccessLevel;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements IUserService {
    IUserRepository userRepository;

    IUserMapper userMapper;

    PasswordEncoder passwordEncoder;

    IRoleRepository roleRepository;

    IPermissionRepository permissionRepository;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    public User createUser(UserCreateRequest request) {
        if(userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTS);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.ROLE_USER).ifPresent(roles::add);

        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public UserResponse updateUser(UserUpdateRequest request, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUser(user, request);

        var roles = new HashSet<>(roleRepository.findAllById(request.getRoles()));
        user.setRoles(roles);
        
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name or hasRole('ADMIN')")
    public UserResponse getUser(String userId) {
        var context = SecurityContextHolder.getContext();
        log.info("User: {}", context.getAuthentication().getName());
        var user = userMapper.toUserResponse(userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
        return user;
    }
    
    @Override
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        var username = context.getAuthentication().getName();
        return userMapper.toUserResponse(userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }
}
