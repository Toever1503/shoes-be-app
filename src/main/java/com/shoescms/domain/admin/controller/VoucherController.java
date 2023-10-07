package com.shoescms.domain.admin.controller;

import com.shoescms.domain.admin.dto.VoucherDTO;
import com.shoescms.domain.admin.models.VoucherModel;
import com.shoescms.domain.admin.service.IVoucherService;
import com.shoescms.domain.shoes.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/voucher")
@RestController
public class VoucherController {
    @Autowired
    private IVoucherService voucherService;


    @PostMapping(value = "/add")
    public ResponseDto addVoucher(@RequestBody VoucherModel voucherModel){
        VoucherDTO voucherDTO = this.voucherService.add(voucherModel);
        return ResponseDto.of(voucherDTO);
    }

    @PostMapping(value = "/update")
    public ResponseDto updateVoucher(@RequestBody VoucherModel voucherModel){
        VoucherDTO voucherDTO = this.voucherService.update(voucherModel);
        return ResponseDto.of(voucherDTO);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseDto deleteVoucher(@PathVariable Long id){
        return ResponseDto.of(voucherService.deleteById(id));
    }

}
