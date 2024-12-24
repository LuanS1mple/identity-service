package com.luan.identity_service.controller;

import com.luan.identity_service.dto.response.UserResponse;
import com.luan.identity_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/all")
    List<UserResponse> getAll(){
        return userService.getAll();
    }
}
