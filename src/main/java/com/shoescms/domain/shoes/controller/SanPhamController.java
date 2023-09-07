package com.shoescms.domain.shoes.controller;

import com.shoescms.common.security.JwtTokenProvider;
import com.shoescms.domain.shoes.dto.DanhMucDTO;
import com.shoescms.domain.shoes.dto.ResponseDto;
import com.shoescms.domain.shoes.dto.SanPhamDto;
import com.shoescms.domain.shoes.entitis.SanPham;
import com.shoescms.domain.shoes.models.SanPhamModel;
import com.shoescms.domain.shoes.repository.ISanPhamRepository;
import com.shoescms.domain.shoes.service.ISanPhamService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/add")
    public ResponseDto addSanPham(@RequestBody SanPhamModel model, @RequestHeader("x-api-token") String token){
        model.setId(null);
        model.setNguoiTao(Long.parseLong(jwtTokenProvider.getUserPk(token)));
        return ResponseDto.of(this.iSanPhamService.add(model));
    }

    @PostMapping(value = "/add/{id}/variant")
    public Object luuPhanLoaiSP(@Parameter(required = true, description = "san pham id") @PathVariable Long id){
        throw new RuntimeException("Func missed body");
    }

    // Lay tat ca Danh Sach San Pham
    @GetMapping("/searchall")
    public  ResponseDto getAllsanPham(@RequestParam (defaultValue = "0") int offset,
                                      @RequestParam(defaultValue = "4") int limit){
        Pageable pageable = PageRequest.of(offset, limit);
        return ResponseDto.of(iSanPhamService.getAll(pageable));
    }

    //Tìm theo thuong hieu
    @GetMapping("/search")
    public ResponseDto findByThuongHieu(@RequestParam Long id,
                                        @RequestParam(defaultValue = "0")int offset,
                                        @RequestParam(defaultValue = "1")int limit
                                       ){
        Pageable pageable = PageRequest.of(offset,limit);
        return  ResponseDto.of(iSanPhamService.findByThuongHieu(id , pageable));
    }
    // Tìm Theo Danh Muc Giay

    @GetMapping("/search1")
    public ResponseDto findByDanhMuc(@RequestParam Long id,
                                        @RequestParam(defaultValue = "0")int offset,
                                        @RequestParam(defaultValue = "1")int limit
    ){
        Pageable pageable = PageRequest.of(offset,limit);
        return  ResponseDto.of(iSanPhamService.findByDmGiay(id , pageable));
    }

    // tim theo ca thuong hieu va danh muc

    @GetMapping("/search2")

    public  ResponseDto findByThuongHieuAndDanhMuc(@RequestParam Long idth,
                                                   @RequestParam Long iddm,
                                                   @RequestParam(defaultValue = "0") int offset,
                                                   @RequestParam(defaultValue = "1") int limit){
        Pageable pageable = PageRequest.of(offset,limit);

        return ResponseDto.of(iSanPhamService.findByThuongHieuIdAndDmGiayId(idth,iddm,pageable));
    }

    @PutMapping("/update/{id}")
    public  ResponseDto updateSanPham(@RequestBody SanPhamModel model, @RequestHeader("x-api-token") String token){
        model.setNguoiCapNhat(Long.parseLong(jwtTokenProvider.getUserPk(token)));
           return ResponseDto.of(iSanPhamService.update(model));
        }
    @DeleteMapping("/delete/{id}")
    public ResponseDto deleteSanPham(@PathVariable Long id){
        return ResponseDto.of(iSanPhamService.deleteById(id));
    }


}
