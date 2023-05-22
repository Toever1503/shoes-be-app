package com.photoism.cms.domain.user.repository;

import com.photoism.cms.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserIdAndDel(String userId, Boolean del);
    Optional<UserEntity> findByIdAndDel(Long userId, Boolean del);
    List<UserEntity> findByNameAndPhone(String name, String phone);
}
