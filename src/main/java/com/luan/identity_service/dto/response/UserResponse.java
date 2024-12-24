package com.luan.identity_service.dto.response;

import com.luan.identity_service.entity.Role;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class UserResponse {
    String name;
    String username;
    int age;
    List<Role> roles;
}
