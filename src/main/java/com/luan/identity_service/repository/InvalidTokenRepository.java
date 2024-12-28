package com.luan.identity_service.repository;

import com.luan.identity_service.entity.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidTokenRepository extends JpaRepository<InvalidToken,String> {
}
