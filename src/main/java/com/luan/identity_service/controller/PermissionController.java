package com.luan.identity_service.controller;

import com.luan.identity_service.dto.request.PermissionCreationRequest;
import com.luan.identity_service.dto.response.ApiResponse;
import com.luan.identity_service.dto.response.PermissionResponse;
import com.luan.identity_service.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/get")
    ApiResponse<List<PermissionResponse>> get(){
        return ApiResponse.<List<PermissionResponse>>builder()
                .code(1004)
                .message("Show all permission success")
                .result(permissionService.getAll())
                .build();
    }
}
