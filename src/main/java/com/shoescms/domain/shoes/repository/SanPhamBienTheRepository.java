package com.shoescms.domain.shoes.repository;


import com.shoescms.domain.shoes.entitis.SanPhamBienThe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface SanPhamBienTheRepository extends JpaRepository<SanPhamBienThe , Long>, JpaSpecificationExecutor<SanPhamBienThe> {
}
