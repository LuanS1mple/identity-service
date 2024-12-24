package com.luan.identity_service.service;

import com.luan.identity_service.dto.request.PermissionCreationRequest;
import com.luan.identity_service.dto.response.PermissionResponse;
import com.luan.identity_service.entity.Permission;
import com.luan.identity_service.mapper.PermissionMapper;
import com.luan.identity_service.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    PermissionMapper permissionMapper;
    public PermissionResponse add(PermissionCreationRequest request){
        Permission permission= permissionMapper.toPermission(request);
        permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

}
