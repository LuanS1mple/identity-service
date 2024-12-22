package com.luan.identity_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Builder
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;
    String username;
    String password;
    int age;
    @ManyToMany
    List<Role> roles;
}
