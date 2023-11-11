package com.shoescms.domain.payment.services.impl;

import com.shoescms.domain.payment.entities.DanhGia;
import com.shoescms.domain.payment.repositories.IDanhGiaRepository;
import com.shoescms.domain.payment.services.IDanhGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DanhGiaServiceImpl implements IDanhGiaService {

    @Autowired
    private IDanhGiaRepository repo;

    @Override
    public DanhGia create(DanhGia danhGia) {
        return repo.save(danhGia);
    }

    @Override
    public List<DanhGia> findByIds(List<Long> ids) {
        return repo.findByIds(ids);
    }
}
