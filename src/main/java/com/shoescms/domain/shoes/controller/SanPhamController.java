package com.shoescms.domain.shoes.controller;

import com.shoescms.domain.shoes.dto.DanhMucDTO;
import com.shoescms.domain.shoes.dto.ResponseDto;
import com.shoescms.domain.shoes.dto.SanPhamDto;
import com.shoescms.domain.shoes.entitis.SanPham;
import com.shoescms.domain.shoes.models.SanPhamModel;
import com.shoescms.domain.shoes.repository.ISanPhamRepository;
import com.shoescms.domain.shoes.service.ISanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
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

//    @GetMapping("/index")
//    public ResponseEntity<Object> index(@RequestParam(defaultValue = "0") int offset,
//                                        @RequestParam(defaultValue = "2") int limit){
//
//        Pageable pageable = PageRequest.of(offset, limit);
//  //      Page<DanhMucDTO> danhMucDTOS = iSanPhamService.filterEntities(pageable,);
//
//        return  null;
//    }
    @PutMapping("/update/{id}")
    public  ResponseDto updateSanPham(@RequestBody SanPhamModel model){
           return ResponseDto.of(iSanPhamService.update(model));
        }
    @DeleteMapping("/delete/{id}")
    public ResponseDto deleteSanPham(@PathVariable Long id){
        return ResponseDto.of(iSanPhamService.deleteById(id));
    }


}
