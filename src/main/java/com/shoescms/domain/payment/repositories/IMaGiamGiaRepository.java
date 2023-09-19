package com.shoescms.domain.payment.repositories;


import com.shoescms.domain.payment.entities.MaGiamGiaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IMaGiamGiaRepository extends JpaRepository<MaGiamGiaEntity,Long>, JpaSpecificationExecutor<MaGiamGiaEntity> {

    @Query(value = "SELECT mgg FROM MaGiamGiaEntity mgg WHERE mgg.maCode = ?1 and mgg.ngayBatDau <= CURRENT_TIMESTAMP and mgg.ngayKetThuc >= CURRENT_TIMESTAMP")
    Optional<MaGiamGiaEntity> findByMaCode(String maCode);

    @Query(value = "SELECT mgg FROM MaGiamGiaEntity mgg Where mgg.ngayBatDau <= CURRENT_TIMESTAMP and mgg.ngayKetThuc >= CURRENT_TIMESTAMP")
    Optional<MaGiamGiaEntity> findByActiveInActive();

    @Query(value = "SELECT mgg FROM MaGiamGiaEntity mgg WHERE mgg.ngayBatDau < CURRENT_TIMESTAMP")
    Optional<MaGiamGiaEntity> findByNgayBatDau();
}
