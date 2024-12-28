package com.luan.identity_service.controller;

import com.luan.identity_service.dto.request.AddRoleToUserRequest;
import com.luan.identity_service.dto.request.UpdateUserRequest;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/myinfo")
    ApiResponse<UserResponse> getMyInfor() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserResponse userResponse= userMapper.toUserResponse(userService.getUserByUsername(username));
        return new ApiResponse().<UserResponse>builder()
                .message("This is your information")
                .code(1000)
                .result(userResponse)
                .build();
    }
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/change/{username}")
    ApiResponse<UserResponse> updateInfo(@RequestBody UpdateUserRequest request, @PathVariable String username) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!username.equals(authentication.getName())){
            return ApiResponse.<UserResponse>builder()
                    .code(1000)
                    .message("You cannot change account information of others")
                    .build();
        }
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("Update success")
                .result(userService.updateUser(request))
                .build();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{username}")
    public ApiResponse<Void> deleteUser(@PathVariable String username) throws Exception {
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Delete success")
                .build();
    }
}
