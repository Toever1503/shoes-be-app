package com.shoescms.domain.voucher.repository;

import com.shoescms.domain.voucher.entity.VoucherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IVoucherRepository extends JpaRepository<VoucherEntity, Long>, JpaSpecificationExecutor<VoucherEntity> {
}
