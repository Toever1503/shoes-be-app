package com.shoescms.domain.payment.repositories;

import com.shoescms.domain.payment.entities.DanhGiaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDanhGiaRepository extends JpaRepository<DanhGiaEntity, Long> {

    @Query("SELECT e FROM DanhGiaEntity e WHERE e.donHangChiTietId IN :ids")
    List<DanhGiaEntity> findByIds(@Param("ids") List<Long> ids);
}
