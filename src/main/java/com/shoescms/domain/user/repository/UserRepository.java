package com.shoescms.domain.user.repository;

import com.shoescms.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserNameAndDel(String userId, Boolean del);
    Optional<UserEntity> findByIdAndDel(Long userId, Boolean del);
    Optional<UserEntity> findByUserNameAndNameAndEmail(String userId, String name, String email);
    List<UserEntity> findByNameAndPhone(String name, String phone);

    @Query("select u from UserEntity u where u.id = ?1")
    UserEntity findByIdUser(Long id);

}
