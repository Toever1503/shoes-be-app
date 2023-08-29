package com.shoescms.domain.payment.repositories;

import com.shoescms.domain.payment.entities.ChiTietDonHangEntity;
import com.shoescms.domain.payment.entities.DonHangEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IChiTietDonHangRepository extends JpaRepository<ChiTietDonHangEntity, Long>, JpaSpecificationExecutor<ChiTietDonHangEntity> {
}
