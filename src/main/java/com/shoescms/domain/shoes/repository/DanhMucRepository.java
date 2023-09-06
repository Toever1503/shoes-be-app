package com.shoescms.domain.shoes.repository;

import com.shoescms.domain.shoes.entitis.DMGiay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface DanhMucRepository extends JpaRepository<DMGiay, Long> , JpaSpecificationExecutor<DMGiay>{


}
