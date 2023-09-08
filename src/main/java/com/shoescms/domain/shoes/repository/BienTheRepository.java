package com.shoescms.domain.shoes.repository;

import com.shoescms.domain.shoes.entitis.BienThe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface BienTheRepository  extends JpaRepository<BienThe,Long>, JpaSpecificationExecutor<BienThe> {
}
