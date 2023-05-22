package com.photoism.cms.domain.etc.repository;

import com.photoism.cms.domain.etc.entity.CodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodeRepository extends JpaRepository<CodeEntity, String> {
    Optional<CodeEntity> findByCode(String code);
    List<CodeEntity> findByCodeGroupAndHiddenOrderByPositionAsc(String codeGroup, Boolean hidden);
    List<CodeEntity> findByCodeGroupOrderByPositionAsc(String codeGroup);
}
