package com.shoescms.domain.voucher;

import com.shoescms.domain.voucher.dto.VoucherDto;
import com.shoescms.domain.voucher.dto.VoucherFilterReqDto;
import com.shoescms.domain.voucher.dto.VoucherReqDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/voucher")
public class VoucherResource {

    @Autowired
    private VoucherService voucherService;

    @PostMapping("filter")
    public Page<VoucherDto> filter(@RequestBody VoucherFilterReqDto reqDto, Pageable pageable) {
        return voucherService.filter(pageable, Specification.where(null));
    }

    @GetMapping("{id}")
    public VoucherDto findById(@PathVariable Long id) {
        return voucherService.findById(id);
    }

    @PostMapping
    public VoucherDto add(@RequestBody VoucherReqDto reqDto) {
        reqDto.setId(null);
        return voucherService.add(reqDto);
    }



    @PutMapping("{id}")
    public VoucherDto update(@PathVariable(name = "id") Long id, @RequestBody VoucherReqDto reqDto) {
        reqDto.setId(id);
        return voucherService.update(reqDto);
    }

    @DeleteMapping("bulk")
    public void delete(@RequestParam List<Long> ids) {
        this.voucherService.deleteByIds(ids);
    }

}
