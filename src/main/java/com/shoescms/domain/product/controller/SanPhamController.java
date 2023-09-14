package com.shoescms.domain.product.controller;

import com.shoescms.common.security.JwtTokenProvider;
import com.shoescms.domain.product.dto.BienTheGiaTriDTO;
import com.shoescms.domain.product.dto.SanPhamBienTheDTO;
import com.shoescms.domain.product.dto.SanPhamDto;
import com.shoescms.domain.product.models.SanPhamBienTheModel;
import com.shoescms.domain.product.service.ISanPhamService;
import com.shoescms.domain.product.dto.ResponseDto;
import com.shoescms.domain.product.entitis.SanPham;
import com.shoescms.domain.product.entitis.SanPham_;
import com.shoescms.domain.product.models.SanPhamModel;
import com.shoescms.domain.product.repository.ISanPhamRepository;
import com.shoescms.domain.product.service.impl.ISanPhamBienTheServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    ISanPhamBienTheServiceImpl iSanPhamBienTheService;
    @PostMapping(value = "/save-step1")
    public SanPhamDto addSanPham(@RequestBody SanPhamModel model,
                                 @RequestHeader("x-api-token") String xApiToken){
        model.setNguoiCapNhat(Long.parseLong(jwtTokenProvider.getUserPk(xApiToken)));
        return this.iSanPhamService.add(model);
    }

    @PostMapping("/save-step2")
    synchronized public SanPhamBienTheDTO save(@RequestBody SanPhamBienTheModel model) {
        return iSanPhamBienTheService.add(model);
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


    @PutMapping("/update-step1/{id}")
    public  ResponseDto updateSanPham(@RequestBody SanPhamModel model){
           return ResponseDto.of(iSanPhamService.update(model));
        }
    @DeleteMapping("/delete/{id}")
    public ResponseDto deleteSanPham(@PathVariable Long id){
        return ResponseDto.of(iSanPhamService.deleteById(id));
    }


    @GetMapping("get-all-gia-tri-bien-the/{id}")
    public List<BienTheGiaTriDTO> getAllBienTheGiaTri(@Parameter(required = true, description = "bien the id", example = "1") @PathVariable Long id)
    {
     return iSanPhamBienTheService.getListBienTheGiaTriByBienTheId(id);
    }
}
