package com.shoescms.domain.shoes.controller;

import com.shoescms.domain.shoes.dto.DanhMucDTO;
import com.shoescms.domain.shoes.dto.ResponseDto;
import com.shoescms.domain.shoes.entitis.DMGiay;
import com.shoescms.domain.shoes.models.DanhMucGiayModel;
import com.shoescms.domain.shoes.repository.DanhMucRepository;
import com.shoescms.domain.shoes.service.IDanhMucGiayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/danh-muc-giay")
@RestController
public class DanhMucGiayController {
    @Autowired
    DanhMucRepository danhMucRepository;
    @Autowired
    IDanhMucGiayService iDanhMucGiayService;

    @PostMapping("/add")

    public ResponseDto addDanhMuc(@RequestBody DanhMucGiayModel  danhMucGiayModel){
        DanhMucDTO dmGiay =  iDanhMucGiayService.add(danhMucGiayModel);
        return ResponseDto.of(dmGiay);
    }

    @PutMapping("/update/{id}")
    public ResponseDto updateDanhMuc(@RequestBody DanhMucGiayModel  danhMucGiayModel){
        DanhMucDTO dmGiay =  iDanhMucGiayService.update(danhMucGiayModel);
        return ResponseDto.of(dmGiay);
    }

    @DeleteMapping("/delete/{id}")
    public  ResponseDto deleteDanhMuc(@PathVariable Long id){
        return ResponseDto.of(iDanhMucGiayService.deleteById(id));
    }
}
