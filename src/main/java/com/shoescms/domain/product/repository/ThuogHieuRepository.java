package com.shoescms.domain.product.repository;

import com.shoescms.domain.product.entitis.ThuongHieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface ThuogHieuRepository extends JpaRepository<ThuongHieu , Long>, JpaSpecificationExecutor<ThuongHieu> {
}