package com.shoescms.domain.product.repository;


import com.shoescms.domain.product.entitis.SanPhamBienThe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface SanPhamBienTheRepository extends JpaRepository<SanPhamBienThe, Long>, JpaSpecificationExecutor<SanPhamBienThe> {
    @Modifying
    void deleteAllBySanPhamId(Long spId);
}
