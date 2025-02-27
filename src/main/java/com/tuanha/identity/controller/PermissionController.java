package com.tuanha.identity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tuanha.identity.dto.request.PermissionRequest;
import com.tuanha.identity.dto.response.ApiResponse;
import com.tuanha.identity.dto.response.PermissionResponse;
import com.tuanha.identity.service.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.tuanha.identity.model.Permission;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/permissions")
@RequiredArgsConstructor
@Slf4j
public class PermissionController {
    PermissionService permissionService;

    @GetMapping()
    public ApiResponse<List<PermissionResponse>> getAllPermission() {
        return ApiResponse.<List<PermissionResponse>>builder()
            .result(permissionService.getAll())
            .build();
    }

    @PostMapping
    public ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
            .result(permissionService.create(request))
            .build();
    }
    
    @DeleteMapping("/{permission}")
    public ApiResponse<String> deletePermission(@PathVariable("permission") String permission) {
        permissionService.delete(permission);
        return ApiResponse.<String>builder()
            .result("Permission has been deleted.")
            .build();
    }
}
