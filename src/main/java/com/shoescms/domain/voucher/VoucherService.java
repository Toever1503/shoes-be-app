package com.shoescms.domain.voucher;

import com.shoescms.domain.voucher.dto.VoucherDto;
import com.shoescms.domain.voucher.dto.VoucherReqDto;
import com.shoescms.domain.voucher.entity.VoucherEntity;
import com.shoescms.domain.voucher.repository.IVoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class VoucherService {

    private final IVoucherRepository voucherRepository;
    public void deleteByIds(List<Long> ids) {
        List<VoucherEntity> voucherEntities = voucherRepository.findAllById(ids);
        if(voucherEntities.size() > 0){
            voucherEntities.forEach(i -> i.setNgayXoa(LocalDateTime.now()));
            voucherRepository.saveAllAndFlush(voucherEntities);
        }
    }

    public VoucherDto update(VoucherReqDto reqDto) {
        return  null;
    }

    public VoucherDto add(VoucherReqDto reqDto) {
        return  null;
    }

    public VoucherDto findById(Long id) {
        return null;
    }

    public Page<VoucherDto> filter(Pageable pageable, Specification<Object> where) {
        return null;
    }
}
