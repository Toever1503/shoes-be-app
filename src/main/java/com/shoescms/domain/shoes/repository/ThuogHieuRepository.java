package com.shoescms.domain.shoes.repository;

import com.shoescms.domain.shoes.entitis.ThuongHieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface ThuogHieuRepository extends JpaRepository<ThuongHieu , Long>, JpaSpecificationExecutor<ThuongHieu> {
}
