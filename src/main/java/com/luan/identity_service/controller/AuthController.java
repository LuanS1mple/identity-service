package com.luan.identity_service.controller;

import com.luan.identity_service.dto.response.ApiResponse;
import com.luan.identity_service.dto.response.AuthResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
public class AuthController {
    @PostMapping("/token")
    ApiResponse<AuthResponse> getToken(){

    }
}
