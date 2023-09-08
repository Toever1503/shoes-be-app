package com.shoescms.domain.shoes.controller;


import com.shoescms.domain.shoes.dto.DanhMucDTO;
import com.shoescms.domain.shoes.dto.ResponseDto;
import com.shoescms.domain.shoes.dto.ThuongHieuDTO;
import com.shoescms.domain.shoes.models.ThuongHieuModel;
import com.shoescms.domain.shoes.service.IThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/thuong-hieu")
public class ThuongHieuController {

    @Autowired
    IThuongHieuService iThuongHieuService;
    @GetMapping("/search")
    public List<ThuongHieuDTO> getThuogHieu(@RequestParam(required = false) String tenThuongHieu,
                                          @RequestParam(required = false) String slug,
                                          Pageable pageable) {
        return iThuongHieuService.getThuongHieus(tenThuongHieu,slug, pageable);
    }
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
