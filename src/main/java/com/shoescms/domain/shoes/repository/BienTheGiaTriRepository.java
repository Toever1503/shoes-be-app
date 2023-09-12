package com.shoescms.domain.shoes.repository;

import com.shoescms.domain.shoes.entitis.BienTheGiaTri;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface BienTheGiaTriRepository extends JpaRepository<BienTheGiaTri,Long> , JpaSpecificationExecutor<BienTheGiaTri> {
}
