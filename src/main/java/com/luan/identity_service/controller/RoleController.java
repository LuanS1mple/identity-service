package com.luan.identity_service.controller;

import com.luan.identity_service.dto.request.RoleCreationRequest;
import com.luan.identity_service.dto.response.ApiResponse;
import com.luan.identity_service.dto.response.RoleResponse;
import com.luan.identity_service.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @PostMapping("/add")
    ApiResponse<RoleResponse> add(@RequestBody RoleCreationRequest request){
        return ApiResponse.<RoleResponse>builder()
                .code(1003)
                .message("Add Role success")
                .result(roleService.get(request))
                .build();
    }
    @GetMapping("/all")
    ApiResponse<List<RoleResponse>> getAll(){
        return ApiResponse.<List<RoleResponse>>builder()
                .code(1005)
                .result(roleService.getAll())
                .message("get all role success")
                .build();
    }

}
