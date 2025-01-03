package com.luan.identity_service.service;

import com.luan.identity_service.dto.request.AddPermissionToRoleRequest;
import com.luan.identity_service.dto.request.RoleCreationRequest;
import com.luan.identity_service.dto.response.RoleResponse;
import com.luan.identity_service.entity.Permission;
import com.luan.identity_service.entity.Role;
import com.luan.identity_service.mapper.RoleMapper;
import com.luan.identity_service.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleService {
    @Autowired
    PermissionService permissionService;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    RoleRepository roleRepository;

    public RoleResponse add(RoleCreationRequest request){
        Role role = roleMapper.toRole(request);
        role.setPermissions(new ArrayList<>());
        for (int i = 0; i < request.getPermissions().size(); i++) {
            Permission permission= permissionService.getByName(request.getPermissions().get(i));
            role.getPermissions().add(permission);
        }
        return RoleResponse.builder()
                .name(request.getName())
                .description(request.getDescription())
                .permissions(roleRepository.save(role).getPermissions())
                .build();
    }
    public List<RoleResponse> getAll(){
        List<Role> roles = roleRepository.findAll();
        List<RoleResponse> result = new ArrayList<>();
        for (int i = 0; i < roles.size(); i++) {
            result.add(roleMapper.toRoleResponse(roles.get(i)));
        }
        return result;
    }
    public List<Role> getByNames(List<String> roles){
        List<Role> result= new ArrayList<>();
        for (int i = 0; i < roles.size(); i++) {
            result.add(roleRepository.findByName(roles.get(i)));
        }
        return result;
    }
    public RoleResponse addPermissions(String roleName,AddPermissionToRoleRequest request){
        List<String> permissionNames= request.getPermission();
        Role role = roleRepository.findByName(roleName);
        List<Permission> permissions = role.getPermissions();
        for (int i = 0; i < permissionNames.size(); i++) {
            permissions.add(permissionService.getByName(permissionNames.get(i)));
        }
        roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }
}
