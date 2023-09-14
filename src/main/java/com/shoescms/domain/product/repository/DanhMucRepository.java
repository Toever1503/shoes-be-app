package com.shoescms.domain.product.repository;

import com.shoescms.domain.product.entitis.DMGiay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface DanhMucRepository extends JpaRepository<DMGiay, Long> , JpaSpecificationExecutor<DMGiay>{


}
