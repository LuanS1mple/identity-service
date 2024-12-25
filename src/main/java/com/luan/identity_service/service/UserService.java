package com.luan.identity_service.service;


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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public List<UserResponse> getAll(){
        List<User> users = userRepository.findAll();
        return userMapper.toUserResponse(users);
    }
    public UserResponse addUser(UserCreationRequest request){
        User user = userMapper.toUser(request);
        user.setRoles(null);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }
    public User getUserByUsername(String username) throws Exception {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new Exception("Not found username"));
        return user;
    }
}
