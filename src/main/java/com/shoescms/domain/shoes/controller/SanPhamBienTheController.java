package com.shoescms.domain.shoes.controller;


import com.shoescms.domain.shoes.dto.ResponseDto;
import com.shoescms.domain.shoes.entitis.BienThe;
import com.shoescms.domain.shoes.entitis.BienThe_;
import com.shoescms.domain.shoes.entitis.SanPhamBienThe;
import com.shoescms.domain.shoes.entitis.SanPhamBienThe_;
import com.shoescms.domain.shoes.models.SanPhamBienTheModel;
import com.shoescms.domain.shoes.repository.SanPhamBienTheRepository;
import com.shoescms.domain.shoes.service.impl.ISanPhamBienTheServiceImpl;
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
    public  ResponseDto search(@RequestBody SanPhamBienTheModel model, Pageable  pageable){
        List<Specification<SanPhamBienThe>> listSpect= new ArrayList<>();
        if(model.getBienThe()!=null){

            listSpect.add((root, query, criteriaBuilder) ->
            {
                Join<SanPhamBienThe, BienThe> join = root.join(SanPhamBienThe_.BIEN_THE);
                return criteriaBuilder.equal(join.get(BienThe_.ID),model.getBienThe());
            }
            );

        }
            listSpect.add((root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get(SanPhamBienThe_.NGAY_XOA)));
        Specification<SanPhamBienThe> finalSpect = null;
        for (Specification spec : listSpect){
            if(finalSpect == null){
                finalSpect = Specification.where(spec);
            }else {
                finalSpect = finalSpect.and(spec);
            }
        }
        return ResponseDto.of(iSanPhamBienTheService.filterEntities(pageable,finalSpect));
    }

    @PostMapping("/add")

    public ResponseDto add(SanPhamBienTheModel model){
        model.setId(null);
        return ResponseDto.of(iSanPhamBienTheService.add(model));
    }

    @PutMapping("/update/{id}")
    public  ResponseDto update(@RequestBody SanPhamBienTheModel sanPhamBienTheModel){
        return ResponseDto.of(iSanPhamBienTheService.update(sanPhamBienTheModel));
    }

    @DeleteMapping("/delete/{id}")

    public  ResponseDto delete(@PathVariable Long id){
        return ResponseDto.of(iSanPhamBienTheService.deleteById(id));
    }
}
