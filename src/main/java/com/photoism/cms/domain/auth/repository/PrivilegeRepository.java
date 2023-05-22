package com.photoism.cms.domain.auth.repository;

import com.photoism.cms.domain.auth.entity.PrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivilegeRepository extends JpaRepository<PrivilegeEntity, Long> {
    List<PrivilegeEntity> findByRoleCd(String roleCd);
}