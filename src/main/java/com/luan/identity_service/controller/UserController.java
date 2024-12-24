package com.luan.identity_service.controller;

import com.luan.identity_service.dto.request.UserCreationRequest;
import com.luan.identity_service.dto.response.ApiResponse;
import com.luan.identity_service.dto.response.UserResponse;
import com.luan.identity_service.entity.User;
import com.luan.identity_service.mapper.UserMapper;
import com.luan.identity_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
    @GetMapping("/all")
    List<UserResponse> getAll(){
        return userService.getAll();
    }
    @PostMapping("/add")
    ApiResponse<UserResponse> add(@RequestBody UserCreationRequest request){
        UserResponse userResponse = userService.addUser(request);
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("Success")
                .result(userResponse)
                .build();
    }
}
