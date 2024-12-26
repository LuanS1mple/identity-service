package com.luan.identity_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class AddRoleToUserRequest {
    private List<String> roles;
}
