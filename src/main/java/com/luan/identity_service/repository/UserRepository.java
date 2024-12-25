package com.luan.identity_service.repository;

import com.luan.identity_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByUsername(String username);
}
