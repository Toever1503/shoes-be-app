package com.shoescms.domain.voucher;

import com.shoescms.common.exception.ObjectNotFoundException;
import com.shoescms.domain.user.dto.UsermetaDto;
import com.shoescms.domain.user.repository.UserRepository;
import com.shoescms.domain.voucher.dto.VoucherDto;
import com.shoescms.domain.voucher.dto.VoucherReqDto;
import com.shoescms.domain.voucher.entity.VoucherEntity;
import com.shoescms.domain.voucher.repository.IVoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class VoucherService {

    private final IVoucherRepository voucherRepository;
    private final UserRepository userRepository;
    public void deleteByIds(List<Long> ids) {
        List<VoucherEntity> voucherEntities = voucherRepository.findAllById(ids);
        if(voucherEntities.size() > 0){
            voucherEntities.forEach(i -> i.setNgayXoa(LocalDateTime.now()));
            voucherRepository.saveAllAndFlush(voucherEntities);
        }
    }

    @Transactional
    public VoucherDto update(VoucherReqDto reqDto) {
        VoucherEntity entity = reqDto.toEntity();
        voucherRepository.saveAndFlush(entity);
        return VoucherDto.toDto(entity);
    }

    @Transactional
    public VoucherDto add(VoucherReqDto reqDto) {
        VoucherEntity entity = reqDto.toEntity();
        voucherRepository.saveAndFlush(entity);
        return VoucherDto.toDto(entity);
    }

    public VoucherDto findById(Long id) {
        VoucherEntity entity = voucherRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(-99));
        if(entity.getNgayXoa() != null) throw  new ObjectNotFoundException(-99);
        VoucherDto dto = VoucherDto.toDto(entity);
        dto.setNguoiTao(UsermetaDto.toDto(userRepository.findById(entity.getNguoiTao()).orElse(null)));
        dto.setNguoiCapNhat(UsermetaDto.toDto(userRepository.findById(entity.getNguoiCapNhat()).orElse(null)));
        return dto;
    }

    public Page<VoucherDto> filter(Pageable pageable, Specification<VoucherEntity> where) {
        return voucherRepository.findAll(where, pageable)
                .map(entity -> {
                    VoucherDto dto = VoucherDto.toDto(entity);
                    dto.setNguoiTao(UsermetaDto.toDto(userRepository.findById(entity.getNguoiTao()).orElse(null)));
                    dto.setNguoiCapNhat(UsermetaDto.toDto(userRepository.findById(entity.getNguoiCapNhat()).orElse(null)));
                    return dto;
                });
    }
}
