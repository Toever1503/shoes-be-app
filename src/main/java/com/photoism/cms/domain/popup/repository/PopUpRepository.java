package com.photoism.cms.domain.popup.repository;

import com.photoism.cms.domain.popup.entity.PopUpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PopUpRepository extends JpaRepository<PopUpEntity, Long> {
}
