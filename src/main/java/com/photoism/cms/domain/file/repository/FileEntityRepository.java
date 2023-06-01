package com.photoism.cms.domain.file.repository;

import com.photoism.cms.domain.file.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileEntityRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByDivision(String div);
    Optional<FileEntity> findByIdAndDivision(Long id, String div);
}
