package com.shoescms.domain.admin.repository;

import com.shoescms.domain.admin.entitis.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher,Long>, JpaSpecificationExecutor<Voucher> {

}
