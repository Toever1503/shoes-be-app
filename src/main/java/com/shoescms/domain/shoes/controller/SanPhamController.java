package com.shoescms.domain.shoes.controller;

import com.shoescms.domain.shoes.dto.ResponseDto;
import com.shoescms.domain.shoes.dto.SanPhamDto;
import com.shoescms.domain.shoes.entitis.SanPham;
import com.shoescms.domain.shoes.models.SanPhamModel;
import com.shoescms.domain.shoes.service.ISanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sanPham")
public class SanPhamController {
    @Autowired
    private ISanPhamService iSanPhamService;

    @PostMapping(value = "/add")
    public ResponseDto addSanPham(@RequestBody SanPhamModel model){
        model.setId(null);
        return ResponseDto.of(this.iSanPhamService.add(model));
    }


}
