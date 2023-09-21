package com.shoescms.domain.product.repository;

import com.shoescms.domain.product.entitis.BienThe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface BienTheRepository  extends JpaRepository<BienThe,Long>, JpaSpecificationExecutor<BienThe> {
}
