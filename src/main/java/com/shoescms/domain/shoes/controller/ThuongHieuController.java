package com.shoescms.domain.shoes.controller;


import com.shoescms.domain.shoes.dto.ResponseDto;
import com.shoescms.domain.shoes.models.ThuongHieuModel;
import com.shoescms.domain.shoes.service.IThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/thuong-hieu")
public class ThuongHieuController {

    @Autowired
    IThuongHieuService iThuongHieuService;

    @PostMapping("/add")

    public ResponseDto addThuongHieu(@RequestBody ThuongHieuModel thuongHieuModel){
        return ResponseDto.of(iThuongHieuService.add(thuongHieuModel));

    }

    @PutMapping("/update/{id}")
    public ResponseDto updateThuongHieu(@RequestBody ThuongHieuModel thuongHieuModel) {
        return ResponseDto.of(iThuongHieuService.update(thuongHieuModel));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseDto deleteThuongHieu(@PathVariable Long id) {
        return ResponseDto.of(iThuongHieuService.deleteById(id));
    }
}
