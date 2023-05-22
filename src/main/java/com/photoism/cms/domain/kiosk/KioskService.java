package com.photoism.cms.domain.kiosk;

import com.photoism.cms.common.exception.ObjectNotFoundException;
import com.photoism.cms.common.model.response.CommonIdResult;
import com.photoism.cms.domain.kiosk.dto.KioskDetailResDto;
import com.photoism.cms.domain.kiosk.dto.KioskListResDto;
import com.photoism.cms.domain.kiosk.dto.KioskReqDto;
import com.photoism.cms.domain.kiosk.entity.KioskEntity;
import com.photoism.cms.domain.kiosk.repository.KioskQueryRepository;
import com.photoism.cms.domain.kiosk.repository.KioskRepository;
import com.photoism.cms.domain.store.entity.StoreEntity;
import com.photoism.cms.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class KioskService {
    private final KioskRepository kioskRepository;
    private final StoreRepository storeRepository;
    private final KioskQueryRepository kioskQueryRepository;

    @Transactional
    public CommonIdResult add(KioskReqDto reqDto, Long createUserId) {
        KioskEntity kioskEntity = reqDto.toEntity(createUserId);
        kioskRepository.save(kioskEntity);
        return new CommonIdResult(kioskEntity.getId());
    }

    @Transactional
    public CommonIdResult update(Long id, KioskReqDto reqDto, Long updateUserId) {
        KioskEntity kioskEntity = kioskRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("kiosk"));
        kioskEntity.update(reqDto, updateUserId);
        return new CommonIdResult(kioskEntity.getId());
    }

    @Transactional
    public CommonIdResult delete(Long id, Long deleteUserId) {
        KioskEntity kioskEntity = kioskRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("kiosk"));
        kioskEntity.setDel(deleteUserId);
        return new CommonIdResult(id);
    }

    @Transactional
    public CommonIdResult mappingStore(Long id, Long storeId) {
        KioskEntity kioskEntity = kioskRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("kiosk"));
        StoreEntity storeEntity = storeRepository.findById(storeId).orElseThrow(() -> new ObjectNotFoundException("store"));
        kioskEntity.setStoreId(storeEntity.getId());
        return new CommonIdResult(id);
    }

    @Transactional(readOnly = true)
    public KioskListResDto getList(Long storeId, Pageable pageable) {
        return new KioskListResDto(kioskQueryRepository.findKioskList(storeId, pageable));
    }

    @Transactional(readOnly = true)
    public KioskDetailResDto getDetail(Long id) {
        return kioskQueryRepository.getDetail(id).orElseThrow(() -> new ObjectNotFoundException("kiosk"));
    }
}
