package com.luan.identity_service.dto.request;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor@AllArgsConstructor
@Slf4j
@Builder
public class IntrospectRequest {
    private String token;
}
