package com.shoescms.domain.product.controller;


import com.shoescms.domain.product.dto.ResponseDto;
import com.shoescms.domain.product.entitis.BienThe;
import com.shoescms.domain.product.entitis.SanPham;
import com.shoescms.domain.product.entitis.SanPhamBienThe;
import com.shoescms.domain.product.models.SanPhamBienTheModel;
import com.shoescms.domain.product.entitis.*;
import com.shoescms.domain.product.repository.SanPhamBienTheRepository;
import com.shoescms.domain.product.service.impl.ISanPhamBienTheServiceImpl;
import jakarta.persistence.criteria.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/san-pham-bien-the")

public class SanPhamBienTheController {

    @Autowired
    SanPhamBienTheRepository sanPhamBienTheRepository;
    @Autowired
    ISanPhamBienTheServiceImpl iSanPhamBienTheService;

    @PostMapping("/search")
    public ResponseDto search(@RequestBody SanPhamBienTheModel model, Pageable pageable) {
        List<Specification<SanPhamBienThe>> listSpect = new ArrayList<>();
        if (model.getSanPham() != null) {
            listSpect.add((root, query, criteriaBuilder) -> {
                        Join<SanPhamBienThe, SanPham> join = root.join(SanPhamBienThe_.SAN_PHAM);
                        return criteriaBuilder.equal(join.get(SanPham_.ID), model.getSanPham());
                    }
            );

        }
        if (model.getBienThe1() != null) {

            listSpect.add((root, query, criteriaBuilder) ->
                    {
                        Join<SanPhamBienThe, BienThe> join = root.join(SanPhamBienThe_.BIEN_THE1);
                        return criteriaBuilder.equal(join.get(BienThe_.ID), model.getBienThe1());
                    }
            );

        }
        if (model.getBienThe2() != null) {
            listSpect.add((root, query, criteriaBuilder) -> {
                        Join<SanPhamBienThe, BienThe> join = root.join(SanPhamBienThe_.BIEN_THE2);

                        return criteriaBuilder.equal(join.get(BienThe_.ID), model.getBienThe2());
                    }
            );
        }

        listSpect.add((root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get(SanPhamBienThe_.NGAY_XOA)));
        Specification<SanPhamBienThe> finalSpect = null;
        for (Specification spec : listSpect) {
            if (finalSpect == null) {
                finalSpect = Specification.where(spec);
            } else {
                finalSpect = finalSpect.and(spec);
            }
        }
        return ResponseDto.of(iSanPhamBienTheService.filterEntities(pageable, finalSpect));
    }


    @PutMapping("/update/{id}")
    public ResponseDto update(@RequestBody SanPhamBienTheModel sanPhamBienTheModel) {
        return ResponseDto.of(iSanPhamBienTheService.update(sanPhamBienTheModel));
    }

    @DeleteMapping("/delete/{id}")

    public ResponseDto delete(@PathVariable Long id) {
        return ResponseDto.of(iSanPhamBienTheService.deleteById(id));
    }
}
