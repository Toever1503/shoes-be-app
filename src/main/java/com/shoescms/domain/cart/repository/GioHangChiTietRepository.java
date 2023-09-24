package com.shoescms.domain.cart.repository;

import com.shoescms.domain.cart.entity.GioHangChiTiet;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet,Long>, JpaSpecificationExecutor<GioHangChiTiet> {

    @Query("SELECT c FROM GioHangChiTiet c WHERE c.gioHang = :gioHang AND c.sanPhamBienThe = :sanPhamBienThe")
    GioHangChiTiet findByGioHangAndSanPhamBienThe(
            @Param("gioHang") Long gioHang,
            @Param("sanPhamBienThe") Long sanPhamBienThe);
    List<GioHangChiTiet> findAllByGioHang(Long id, Sort by);
}
