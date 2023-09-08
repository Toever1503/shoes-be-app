package com.shoescms.domain.cart.repository;

import com.shoescms.domain.cart.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang,Long>, JpaSpecificationExecutor<GioHang> {

}
