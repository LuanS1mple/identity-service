package com.luan.identity_service.controller;

import com.luan.identity_service.dto.request.AddPermissionToRoleRequest;
import com.luan.identity_service.dto.request.RoleCreationRequest;
import com.luan.identity_service.dto.response.ApiResponse;
import com.luan.identity_service.dto.response.RoleResponse;
import com.luan.identity_service.entity.Role;
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
                .result(roleService.add(request))
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
    @PostMapping("/{roleName}")
    ApiResponse<RoleResponse> addPermission(@PathVariable String roleName, @RequestBody AddPermissionToRoleRequest request){
        return ApiResponse.<RoleResponse>builder()
                .code(2002)
                .message("Update roll success")
                .result(roleService.addPermissions(roleName,request))
                .build();

    }

}
