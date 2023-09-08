package com.shoescms.domain.shoes.controller;


import com.shoescms.domain.shoes.dto.ResponseDto;
import com.shoescms.domain.shoes.models.SanPhamBienTheModel;
import com.shoescms.domain.shoes.repository.SanPhamBienTheRepository;
import com.shoescms.domain.shoes.service.impl.ISanPhamBienTheServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/san-pham-bien-the")

public class SanPhamBienTheController {

    @Autowired
    SanPhamBienTheRepository sanPhamBienTheRepository;
    @Autowired
    ISanPhamBienTheServiceImpl iSanPhamBienTheService;
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
