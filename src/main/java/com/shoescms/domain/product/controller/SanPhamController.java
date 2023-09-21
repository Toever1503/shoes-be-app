package com.shoescms.domain.product.controller;

import com.shoescms.common.security.JwtTokenProvider;
import com.shoescms.domain.product.dto.*;
import com.shoescms.domain.product.enums.ELoaiBienThe;
import com.shoescms.domain.product.models.SanPhamBienTheModel;
import com.shoescms.domain.product.service.ISanPhamService;
import com.shoescms.domain.product.entitis.SanPham;
import com.shoescms.domain.product.entitis.SanPham_;
import com.shoescms.domain.product.models.SanPhamModel;
import com.shoescms.domain.product.repository.ISanPhamRepository;
import com.shoescms.domain.product.service.impl.ISanPhamBienTheServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @PatchMapping("/thay-doi-phan-loai/{id}")
    public void thayDoiPhanLoai(@PathVariable Long id, @RequestParam ELoaiBienThe type){
        iSanPhamService.thayDoiPhanLoai(id, type);
    }
    @PostMapping("/save-step2")
    public SanPhamBienTheDTO save(@RequestBody SanPhamBienTheModel model) {
        return iSanPhamBienTheService.add(model);
    }

    @GetMapping("/phan-loai/{id}")
    public ELoaiBienThe getPhanLoai(@PathVariable Long id){
        return iSanPhamService.getPhanLoai(id);
    }
    @DeleteMapping("/phan-loai/{id}")
    public ResponseDto delete(@PathVariable Long id) {
        return ResponseDto.of(iSanPhamBienTheService.deleteById(id));
    }

    @PatchMapping("/phan-loai/{id}/so-luong")
    public void capNhatSoLuongSanPhamChoBienThe(@PathVariable Long id, @RequestParam int soLuong) {
        iSanPhamBienTheService.capNhatSoLuongSanPhamChoBienThe(id,soLuong);
    }


    @GetMapping("{id}/phan-loai")
    public List<SanPhamBienTheDTO> findAllPhanLoaiTheoSanPham(@PathVariable Long id){
        return iSanPhamBienTheService.findAllPhanLoaiTheoSanPham(id);
    }

    @GetMapping("get-all-gia-tri-bien-the/{id}")
    public List<BienTheGiaTriDTO> getAllBienTheGiaTri(@Parameter(required = true, description = "bien the id", example = "1") @PathVariable Long id)
    {
        return iSanPhamBienTheService.getListBienTheGiaTriByBienTheId(id);
    }


    // Lay chi tiet San Pham theo id
    @GetMapping("{id}")
    public  SanPhamDto chiTietSanPham(@PathVariable Long id){
        return iSanPhamService.findBydId(id);
    }

    @PostMapping("/filter")
    public Page<SanPhamDto> search(@RequestBody SanPhamFilterReqDto model, Pageable pageable){
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
        return iSanPhamService.filterEntities(pageable,finalSpec);
    }


//    @PutMapping("/update-step1/{id}")
//    public  ResponseDto updateSanPham(@RequestBody SanPhamModel model){
//           return ResponseDto.of(iSanPhamService.update(model));
//        }
    @DeleteMapping("/delete/{id}")
    public ResponseDto deleteSanPham(@PathVariable Long id){
        return ResponseDto.of(iSanPhamService.deleteById(id));
    }


    @GetMapping("public/{id}")
    public ChiTietSanPhamDto chiTietSanPhamResDto(@PathVariable Long id){
        return iSanPhamService.chiTietSanPhamResDto(id);
    }
}