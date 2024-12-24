package com.luan.identity_service.mapper;

import com.luan.identity_service.dto.request.PermissionCreationRequest;
import com.luan.identity_service.dto.response.PermissionResponse;
import com.luan.identity_service.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionCreationRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}