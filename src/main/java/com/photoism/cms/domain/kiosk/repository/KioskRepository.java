package com.photoism.cms.domain.kiosk.repository;

import com.photoism.cms.domain.kiosk.entity.KioskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KioskRepository extends JpaRepository<KioskEntity, Long> {
}