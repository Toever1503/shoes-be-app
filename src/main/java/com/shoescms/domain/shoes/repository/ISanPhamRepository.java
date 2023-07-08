package com.shoescms.domain.shoes.repository;

import com.shoescms.domain.shoes.entitis.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ISanPhamRepository extends JpaRepository<SanPham,Long>, JpaSpecificationExecutor<SanPham> {
}
