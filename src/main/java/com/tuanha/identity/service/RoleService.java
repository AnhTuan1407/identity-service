package com.tuanha.identity.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tuanha.identity.dto.request.RoleCreationRequest;
import com.tuanha.identity.dto.response.RoleResponse;
import com.tuanha.identity.exception.AppException;
import com.tuanha.identity.exception.ErrorCode;
import com.tuanha.identity.mapper.IRoleMapper;
import com.tuanha.identity.model.Role;
import com.tuanha.identity.repository.IPermissionRepository;
import com.tuanha.identity.repository.IRoleRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {
    IRoleRepository roleRepository;
    
    IRoleMapper roleMapper;

    IPermissionRepository permissionRepository;

    public RoleResponse create(RoleCreationRequest request) {
        Role role = roleMapper.toRole(request);

        if(!roleRepository.findById(role.getName()).isEmpty()) {
            throw new AppException(ErrorCode.ROLE_EXISTS);
        }

        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    public void delete(String role) {
        roleRepository.deleteById(role);
    }
}
