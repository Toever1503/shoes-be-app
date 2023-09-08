package com.shoescms.domain.cart.repository;

import com.shoescms.domain.cart.entity.GioHang;
import com.shoescms.domain.cart.entity.GioHangChiTiet;
import com.shoescms.domain.shoes.entitis.SanPham;
import com.shoescms.domain.shoes.entitis.SanPhamBienThe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet,Long>, JpaSpecificationExecutor<GioHangChiTiet> {

    @Query("SELECT c FROM GioHangChiTiet c WHERE c.gioHang = :gioHang AND c.sanPham = :sanPham AND c.sanPhamBienThe = :sanPhamBienThe")
    GioHangChiTiet findByGioHangAndSanPhamAndSanPhamBienThe(
            @Param("gioHang") Long gioHang,
            @Param("sanPham") Long sanPham,
            @Param("sanPhamBienThe") Long sanPhamBienThe);

}
