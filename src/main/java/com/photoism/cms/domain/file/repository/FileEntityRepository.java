package com.photoism.cms.domain.file.repository;

import com.photoism.cms.domain.file.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileEntityRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByDivision(String div);
}
