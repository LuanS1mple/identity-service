package com.luan.identity_service.controller;

import com.luan.identity_service.dto.request.PermissionCreationRequest;
import com.luan.identity_service.dto.response.ApiResponse;
import com.luan.identity_service.dto.response.PermissionResponse;
import com.luan.identity_service.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    @PostMapping("/add")
    ApiResponse<PermissionResponse> add(@RequestBody PermissionCreationRequest request){
        return ApiResponse.<PermissionResponse>builder()
                .code(1001)
                .message("Add Permission: success")
                .result(permissionService.add(request))
                .build();
    }
}
