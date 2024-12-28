package com.luan.identity_service.controller;

import com.luan.identity_service.dto.request.AddRoleToUserRequest;
import com.luan.identity_service.dto.request.UserCreationRequest;
import com.luan.identity_service.dto.response.ApiResponse;
import com.luan.identity_service.dto.response.UserResponse;
import com.luan.identity_service.entity.Role;
import com.luan.identity_service.entity.User;
import com.luan.identity_service.mapper.UserMapper;
import com.luan.identity_service.repository.UserRepository;
import com.luan.identity_service.service.RoleService;
import com.luan.identity_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleService roleService;
    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasAuthority('ADMIN')")
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
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{username}")
    ApiResponse<UserResponse> addRoleToUser(@PathVariable String username, @RequestBody AddRoleToUserRequest request) throws Exception {
        User user = userService.getUserByUsername(username);
        List<Role> roles = user.getRoles();
        List<Role> rolesAdd = roleService.getByNames(request.getRoles());
        for (int i = 0; i < rolesAdd.size(); i++) {
            roles.add(rolesAdd.get(i));
        }
        userRepository.save(user);
        UserResponse userResponse = userMapper.toUserResponse(user);
        return ApiResponse.<UserResponse>builder()
                .code(2001)
                .message("Add role success")
                .result(userResponse)
                .build();
    }
}
