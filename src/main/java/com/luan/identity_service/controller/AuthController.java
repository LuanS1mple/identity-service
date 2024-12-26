package com.luan.identity_service.controller;

import com.luan.identity_service.dto.request.AuthRequest;
import com.luan.identity_service.dto.request.IntrospectRequest;
import com.luan.identity_service.dto.response.ApiResponse;
import com.luan.identity_service.dto.response.AuthResponse;
import com.luan.identity_service.dto.response.IntrospectResponse;
import com.luan.identity_service.entity.User;
import com.luan.identity_service.error.Error;
import com.luan.identity_service.mapper.UserMapper;
import com.luan.identity_service.service.AuthService;
import com.luan.identity_service.service.UserService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;


    @Autowired
    UserService userService;
    @PostMapping("/token")
    ApiResponse<AuthResponse> getToken(@RequestBody AuthRequest request) throws Exception {
        boolean isUser = authService.isAnUser(request);
        ApiResponse<AuthResponse> apiResponse = new ApiResponse<>();
        if(!isUser) {
            apiResponse.setCode(Error.UNAUTHENTICATED.getCode());
            apiResponse.setMessage(Error.UNAUTHENTICATED.getMessage());
            return apiResponse;
        }
        apiResponse.setCode(2000);
        apiResponse.setMessage("Login success");
        User user= userService.getUserByUsername(request.getUsername());
        AuthResponse authResponse = new AuthResponse().builder()
                .token(authService.generateToken(user))
                .build();
        apiResponse.setResult(authResponse);
        return apiResponse;
    }
    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        return ApiResponse.<IntrospectResponse>builder()
                .code(2004)
                .message("Introspect token")
                .result(authService.introspect(request))
                .build();
    }
}
