package com.photoism.cms.domain.store.repository;

import com.photoism.cms.domain.store.entity.StoreMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreMemberRepository extends JpaRepository<StoreMemberEntity, Long> {
    List<StoreMemberEntity> findByStoreId(Long id);
    List<StoreMemberEntity> findByUserId(Long id);
}