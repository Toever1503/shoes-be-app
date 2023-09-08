package com.shoescms.domain.shoes.controller;

import com.shoescms.domain.shoes.dto.DanhMucDTO;
import com.shoescms.domain.shoes.dto.ResponseDto;
import com.shoescms.domain.shoes.dto.SanPhamDto;
import com.shoescms.domain.shoes.entitis.SanPham;
import com.shoescms.domain.shoes.entitis.SanPham_;
import com.shoescms.domain.shoes.models.SanPhamModel;
import com.shoescms.domain.shoes.repository.ISanPhamRepository;
import com.shoescms.domain.shoes.service.ISanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/san-pham")
public class SanPhamController {
    @Autowired
    private ISanPhamService iSanPhamService;
    @Autowired
    ISanPhamRepository iSanPhamRepository;
    @PostMapping(value = "/add")
    public ResponseDto addSanPham(@RequestBody SanPhamModel model){
        model.setId(null);
        return ResponseDto.of(this.iSanPhamService.add(model));
    }
    // Lay tat ca Danh Sach San Pham
    @GetMapping("/searchall")
    public  ResponseDto getAllsanPham(@RequestParam (defaultValue = "0") int offset,
                                      @RequestParam(defaultValue = "4") int limit){
        Pageable pageable = PageRequest.of(offset, limit);
        return ResponseDto.of(iSanPhamService.getAll(pageable));
    }

    @PostMapping("/search")
    public  ResponseDto search(@RequestBody SanPhamModel model, Pageable pageable){
        List<Specification<SanPham>> listSpect = new ArrayList<>();

        if(model.getThuongHieu()!=null){
            listSpect.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(SanPham_.THUONG_HIEU),model.getThuongHieu()));
        }
        if(model.getDmGiay()!= null){
            listSpect.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(SanPham_.DM_GIAY),model.getDmGiay()));
        }
            listSpect.add((root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get(SanPham_.NGAY_XOA)));
        Specification<SanPham> finalSpec = null;
        for (Specification spec : listSpect) {
            if (finalSpec == null) {
                finalSpec = Specification.where(spec);
            } else {
                finalSpec = finalSpec.and(spec);
            }
        }
        return ResponseDto.of(iSanPhamService.filterEntities(pageable,finalSpec));
    }


    @PutMapping("/update/{id}")
    public  ResponseDto updateSanPham(@RequestBody SanPhamModel model){
           return ResponseDto.of(iSanPhamService.update(model));
        }
    @DeleteMapping("/delete/{id}")
    public ResponseDto deleteSanPham(@PathVariable Long id){
        return ResponseDto.of(iSanPhamService.deleteById(id));
    }


}
