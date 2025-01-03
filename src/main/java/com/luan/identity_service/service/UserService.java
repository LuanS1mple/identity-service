package com.luan.identity_service.service;


import com.luan.identity_service.dto.request.UpdateUserRequest;
import com.luan.identity_service.dto.request.UserCreationRequest;
import com.luan.identity_service.dto.response.UserResponse;
import com.luan.identity_service.entity.User;
import com.luan.identity_service.mapper.UserMapper;
import com.luan.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleService roleService;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();
        return userMapper.toUserResponse(users);
    }

    public UserResponse addUser(UserCreationRequest request) {
        User user = userMapper.toUser(request);
        List<String> defaultRole = new ArrayList<>();
        defaultRole.add("USER");
        user.setRoles(roleService.getByNames(defaultRole));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public User getUserByUsername(String username) throws Exception {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new Exception("Not found username"));
        return user;
    }
    public UserResponse updateUser(UpdateUserRequest request) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = getUserByUsername(username);
        userMapper.updateUser(user,request);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userMapper.toUserResponse(userRepository.save(user));
    }
    public void deleteUser(String username) throws Exception {
        User user  = getUserByUsername(username);
        userRepository.delete(user);
    }

}
