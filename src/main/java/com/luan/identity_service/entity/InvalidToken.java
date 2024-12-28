package com.luan.identity_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@Setter
@Getter
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvalidToken {
    @Id
    String tokenId;
}
