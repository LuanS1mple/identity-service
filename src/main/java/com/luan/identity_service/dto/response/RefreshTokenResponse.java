package com.luan.identity_service.dto.response;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Slf4j
@Builder
public class RefreshTokenResponse {
    String token;
}
