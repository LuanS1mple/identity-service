package com.luan.identity_service.service;


import com.luan.identity_service.dto.response.UserResponse;
import com.luan.identity_service.entity.User;
import com.luan.identity_service.mapper.UserMapper;
import com.luan.identity_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;
    public List<UserResponse> getAll(){
        List<User> users = userRepository.findAll();
        return userMapper.toUserResponse(users);
    }
}
