package com.photoism.cms.domain.auth.repository;

import com.photoism.cms.domain.auth.entity.RoleEntity;
import com.photoism.cms.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    List<RoleEntity> findByUser(UserEntity user);
}