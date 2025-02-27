package com.tuanha.identity.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tuanha.identity.dto.request.PermissionRequest;
import com.tuanha.identity.dto.response.PermissionResponse;
import com.tuanha.identity.exception.AppException;
import com.tuanha.identity.exception.ErrorCode;
import com.tuanha.identity.mapper.IPermissionMapper;
import com.tuanha.identity.model.Permission;
import com.tuanha.identity.repository.IPermissionRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionService {
    IPermissionRepository permissionRepository;

    IPermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);

        if(!permissionRepository.findById(request.getName()).isEmpty()) {
            throw new AppException(ErrorCode.PERMISSION_EXISTS);
        }

        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }

    public List<PermissionResponse> getAll() {
        return permissionRepository.findAll().stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }
}
