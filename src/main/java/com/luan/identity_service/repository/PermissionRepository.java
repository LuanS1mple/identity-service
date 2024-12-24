package com.luan.identity_service.repository;

import com.luan.identity_service.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,String> {
    Permission findByName(String name);
}
