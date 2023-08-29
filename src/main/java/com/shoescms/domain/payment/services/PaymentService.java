package com.shoescms.domain.payment.services;

import com.shoescms.common.exception.CommonMessageException;
import com.shoescms.domain.payment.dtos.DatHangReqDto;
import com.shoescms.domain.payment.entities.DonHangEntity;
import com.shoescms.domain.payment.repositories.IChiTietDonHangRepository;
import com.shoescms.domain.payment.repositories.IDiaChiRepository;
import com.shoescms.domain.payment.repositories.IDonHangRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Component
@Log4j
@RequiredArgsConstructor
public class PaymentService {

    private final IDonHangRepository donHangRepository;
    private final IChiTietDonHangRepository chiTietDonHangRepository;
    private final IDiaChiRepository diaChiRepository;


    @Transactional
    public void datHang(DatHangReqDto reqDto, Long userId) {
        if (ObjectUtils.isEmpty(reqDto.getDiaChiNhanHang()) && ObjectUtils.isEmpty(reqDto.getDiaChiId()))
            throw new CommonMessageException(1);

        DonHangEntity donHangEntity = new DonHangEntity();
        if(!ObjectUtils.isEmpty(reqDto.getDiaChiId()))
            donHangEntity.setDiaChi(diaChiRepository.findById(reqDto.getDiaChiId()).orElseThrow(() -> new CommonMessageException(1)));

//        ...
    }

    public Long getDonHang(Long id) {
        return null;
    }
}
