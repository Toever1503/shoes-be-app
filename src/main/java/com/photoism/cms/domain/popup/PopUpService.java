package com.photoism.cms.domain.popup;

import com.photoism.cms.common.exception.ObjectNotFoundException;
import com.photoism.cms.common.model.response.CommonIdResult;
import com.photoism.cms.domain.popup.dto.PopUpDetailResDto;
import com.photoism.cms.domain.popup.dto.PopUpListResDto;
import com.photoism.cms.domain.popup.dto.PopUpReqDto;
import com.photoism.cms.domain.popup.entity.PopUpEntity;
import com.photoism.cms.domain.popup.entity.PopUpQueryRepository;
import com.photoism.cms.domain.popup.repository.PopUpRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PopUpService {
    private final PopUpRepository popUpRepository;
    private final PopUpQueryRepository popUpQueryRepository;

    @Transactional
    public CommonIdResult add(PopUpReqDto reqDto, Long createUserId) {
        PopUpEntity popUpEntity = reqDto.toEntity(createUserId);
        popUpRepository.save(popUpEntity);
        return new CommonIdResult(popUpEntity.getId());
    }

    @Transactional
    public CommonIdResult update(Long id, PopUpReqDto reqDto, Long updateUserId) {
        PopUpEntity popUpEntity = popUpRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("popUp"));
        popUpEntity.update(reqDto, updateUserId);
        return new CommonIdResult(popUpEntity.getId());
    }

    @Transactional
    public CommonIdResult delete(Long id, Long deleteUserId) {
        PopUpEntity popUpEntity = popUpRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("popUp"));
        popUpEntity.setDel(deleteUserId);
        return new CommonIdResult(id);
    }

    @Transactional(readOnly = true)
    public PopUpListResDto getList(Boolean isShow, Pageable pageable) {
        return new PopUpListResDto(popUpQueryRepository.findPopUpList(isShow, pageable));
    }

    @Transactional(readOnly = true)
    public PopUpDetailResDto getDetail(Long id) {
        return popUpQueryRepository.getDetail(id).orElseThrow(() -> new ObjectNotFoundException("popUp"));
    }

    @Transactional(readOnly = true)
    public List<PopUpDetailResDto> get() {
        return popUpQueryRepository.get();
    }

}
