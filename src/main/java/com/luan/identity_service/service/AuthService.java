package com.luan.identity_service.service;

import com.luan.identity_service.dto.request.AuthRequest;
import com.luan.identity_service.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserService userService;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public boolean isAnUser(AuthRequest request) throws Exception {
        User user = userService.getUserByUsername(request.getUsername());
        return user.getPassword().equals(passwordEncoder.encode(request.getPassword()));
    }


}
