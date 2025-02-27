package com.tuanha.identity.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tuanha.identity.dto.request.RoleCreationRequest;
import com.tuanha.identity.dto.response.ApiResponse;
import com.tuanha.identity.dto.response.RoleResponse;
import com.tuanha.identity.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;
    
    @GetMapping
    public ApiResponse<List<RoleResponse>> getAllRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
            .result(roleService.getAll())
            .build();
    }

    @PostMapping
    public ApiResponse<RoleResponse> createRole(@RequestBody RoleCreationRequest request) {
        return ApiResponse.<RoleResponse>builder()
            .result(roleService.create(request))
            .build();
    }

    @DeleteMapping("/{role}")
    public ApiResponse<String> deleteRole(@PathVariable("role") String role) {
        roleService.delete(role);
        return ApiResponse.<String>builder()
            .result("Role has been deleted.")
            .build();
    }
}
