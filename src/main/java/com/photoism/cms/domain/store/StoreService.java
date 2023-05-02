package com.photoism.cms.domain.store;

import com.photoism.cms.common.exception.ObjectNotFoundException;
import com.photoism.cms.common.exception.UserNotFoundException;
import com.photoism.cms.common.model.response.CommonIdResult;
import com.photoism.cms.domain.store.dto.StoreReqDto;
import com.photoism.cms.domain.store.entity.StoreEntity;
import com.photoism.cms.domain.store.repository.StoreRepository;
import com.photoism.cms.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommonIdResult add(StoreReqDto reqDto, Long createUserId) {
        StoreEntity storeEntity = reqDto.toEntity(createUserId);
        storeRepository.save(storeEntity);
        return new CommonIdResult(storeEntity.getId());
    }

    @Transactional
    public CommonIdResult update(Long id, StoreReqDto reqDto, Long updateUserId) {
        StoreEntity storeEntity = storeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("store"));
        storeEntity.update(reqDto, updateUserId);
        return new CommonIdResult(storeEntity.getId());
    }

    @Transactional
    public CommonIdResult delete(Long id, Long deleteUserId) {
        StoreEntity storeEntity = storeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("store"));
        storeEntity.setDel(deleteUserId);
        return new CommonIdResult(id);
    }

    @Transactional
    public CommonIdResult mappingOwner(Long id, Long userId) {
        StoreEntity storeEntity = storeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("store"));
        userRepository.findByIdAndDel(userId, false).orElseThrow(UserNotFoundException::new);
        storeEntity.setOwner(userId);
        return new CommonIdResult(storeEntity.getId());
    }

    @Transactional
    public CommonIdResult mappingCancel(Long id) {
        StoreEntity storeEntity = storeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("store"));
        storeEntity.setOwner(null);
        return new CommonIdResult(storeEntity.getId());
    }
}
