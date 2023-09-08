package com.shoescms.domain.cart.controller;


import com.shoescms.domain.cart.entity.GioHang;
import com.shoescms.domain.cart.model.GioHangModel;
import com.shoescms.domain.cart.service.GioHangService;
import com.shoescms.domain.shoes.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class GioHangController {

    @Autowired
    private GioHangService gioHangService;

    @PostMapping("/add")
    public ResponseDto addCart(@RequestBody GioHangModel gioHang) {
        gioHang.setId(null);
        return ResponseDto.of(this.gioHangService.add(gioHang));
    }
}
