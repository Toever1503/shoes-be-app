package com.shoescms.domain.payment.repositories;

import com.shoescms.domain.payment.entities.DonHangEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IDonHangRepository extends JpaRepository<DonHangEntity, Long>, JpaSpecificationExecutor<DonHangEntity> {
    DonHangEntity findByMaDonHang(String vnpTxnRef);
}
