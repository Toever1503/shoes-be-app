package com.shoescms.domain.payment.repositories;

import com.shoescms.domain.payment.entities.DanhGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDanhGiaRepository extends JpaRepository<DanhGia, Long> {

    @Query("SELECT e FROM DanhGia e WHERE e.donHangChiTietId IN :ids")
    List<DanhGia> findByIds(@Param("ids") List<Long> ids);
}
