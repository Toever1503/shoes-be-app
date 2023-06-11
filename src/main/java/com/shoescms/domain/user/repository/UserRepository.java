package com.shoescms.domain.user.repository;

import com.shoescms.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserIdAndDel(String userId, Boolean del);
    Optional<UserEntity> findByIdAndDel(Long userId, Boolean del);
    Optional<UserEntity> findByUserIdAndNameAndEmail(String userId, String name, String email);
    List<UserEntity> findByNameAndPhone(String name, String phone);
}
