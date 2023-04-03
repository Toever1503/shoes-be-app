package com.photoism.cms.domain.auth.repository;

import com.photoism.cms.domain.auth.entity.AuthenticationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationRepository extends JpaRepository<AuthenticationEntity, String> {
    Optional<AuthenticationEntity> findById(String s);
    Optional<AuthenticationEntity> findByRefreshToken(String token);
    Optional<AuthenticationEntity> findFirstByUserIdAndLogoutOrderByCreateDateDesc(Long id, Boolean logout);
    Optional<AuthenticationEntity> findByUserId(Long userId);
}
