package com.shoescms.domain.shoes.controller;

import com.shoescms.domain.shoes.dto.DanhMucDTO;
import com.shoescms.domain.shoes.dto.ResponseDto;
import com.shoescms.domain.shoes.entitis.DMGiay;
import com.shoescms.domain.shoes.models.DanhMucGiayModel;
import com.shoescms.domain.shoes.repository.DanhMucRepository;
import com.shoescms.domain.shoes.service.IDanhMucGiayService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/danh-muc-giay")
@RestController
public class DanhMucGiayController {
    @Autowired
    DanhMucRepository danhMucRepository;
    @Autowired
    IDanhMucGiayService iDanhMucGiayService;

    @GetMapping("/search")
    public List<DanhMucDTO> getDanhMuc(@RequestParam(required = false) String tenDanhMuc,
                                       @RequestParam(required = false) String slug,
                                       Pageable pageable) {
        return iDanhMucGiayService.getDanhMucs(tenDanhMuc,slug,pageable);
    }

    @GetMapping("loc-danh-muc")
    public Page<DanhMucDTO> locDanhMuc(@Parameter(name = "tenDanhMuc") @RequestParam(required = false) String tenDanhMuc,
                                       @PageableDefault(sort = "id", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable){
        return iDanhMucGiayService.locDanhMuc(tenDanhMuc, pageable);
    };

    @PostMapping("/add")

    public ResponseDto addDanhMuc(@RequestBody DanhMucGiayModel danhMucGiayModel) {
        DanhMucDTO dmGiay = iDanhMucGiayService.add(danhMucGiayModel);
        return ResponseDto.of(dmGiay);
    }

    @PutMapping("/update/{id}")
    public ResponseDto updateDanhMuc(@RequestBody DanhMucGiayModel danhMucGiayModel) {
        DanhMucDTO dmGiay = iDanhMucGiayService.update(danhMucGiayModel);
        return ResponseDto.of(dmGiay);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto deleteDanhMuc(@PathVariable Long id) {
        return ResponseDto.of(iDanhMucGiayService.deleteById(id));
    }
}
