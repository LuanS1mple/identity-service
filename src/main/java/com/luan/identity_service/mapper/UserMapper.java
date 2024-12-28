package com.luan.identity_service.mapper;

import com.luan.identity_service.dto.request.UpdateUserRequest;
import com.luan.identity_service.dto.request.UserCreationRequest;
import com.luan.identity_service.dto.response.UserResponse;
import com.luan.identity_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    List<UserResponse> toUserResponse(List<User> users);
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);
    void updateUser(@MappingTarget User user, UpdateUserRequest request);
//    User toUser(UpdateUserRequest request);
}
