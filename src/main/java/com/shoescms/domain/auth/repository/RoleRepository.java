package com.shoescms.domain.auth.repository;

import com.shoescms.common.enums.RoleEnum;
import com.shoescms.domain.auth.entity.RoleEntity;
import com.shoescms.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByRoleCd(String roleEnum);
}