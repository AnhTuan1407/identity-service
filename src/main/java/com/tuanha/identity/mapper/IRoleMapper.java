package com.tuanha.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.tuanha.identity.dto.request.RoleCreationRequest;
import com.tuanha.identity.dto.response.RoleResponse;
import com.tuanha.identity.model.Role;

@Mapper(componentModel = "spring")
public interface IRoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleCreationRequest request);

    RoleResponse toRoleResponse(Role role);
}
