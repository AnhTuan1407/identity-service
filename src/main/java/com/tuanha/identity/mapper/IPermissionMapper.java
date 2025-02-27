package com.tuanha.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.tuanha.identity.dto.request.PermissionRequest;
import com.tuanha.identity.dto.response.PermissionResponse;
import com.tuanha.identity.model.Permission;

@Mapper(componentModel = "spring")
public interface IPermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
    
    void updatePermission(@MappingTarget Permission permission, PermissionRequest request);
}
