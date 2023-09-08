package com.shoescms.domain.shoes.repository;

import com.shoescms.domain.shoes.entitis.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ISanPhamRepository extends JpaRepository<SanPham,Long>, JpaSpecificationExecutor<SanPham> {


    Page<SanPham> findAll(Pageable pageable);

    Page<SanPham> findByThuongHieuIdAndDmGiayId(Long idThuongHieu, Long idDanhMucGiay, Pageable pageable);
}
