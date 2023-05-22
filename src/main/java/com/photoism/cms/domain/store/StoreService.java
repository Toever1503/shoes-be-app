package com.photoism.cms.domain.store;

import com.photoism.cms.common.enums.CountryEnum;
import com.photoism.cms.common.enums.RoleEnum;
import com.photoism.cms.common.exception.ObjectNotFoundException;
import com.photoism.cms.common.exception.UserNotFoundException;
import com.photoism.cms.common.model.response.CommonIdResult;
import com.photoism.cms.common.security.JwtTokenProvider;
import com.photoism.cms.domain.etc.repository.CodeRepository;
import com.photoism.cms.domain.store.dto.*;
import com.photoism.cms.domain.store.entity.StoreEntity;
import com.photoism.cms.domain.store.entity.StoreMemberEntity;
import com.photoism.cms.domain.store.repository.StoreMemberRepository;
import com.photoism.cms.domain.store.repository.StoreQueryRepository;
import com.photoism.cms.domain.store.repository.StoreRepository;
import com.photoism.cms.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class StoreService {
    private final StoreRepository storeRepository;
    private final StoreQueryRepository storeQueryRepository;
    private final StoreMemberRepository storeMemberRepository;
    private final UserRepository userRepository;
    private final CodeRepository codeRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public CommonIdResult add(StoreReqDto reqDto, Long createUserId) {
        if (reqDto.getBrandCd() != null && codeRepository.findByCode(reqDto.getBrandCd()).isEmpty())
            throw new ObjectNotFoundException("brand code");
        if (reqDto.getStoreTypeCd() != null && codeRepository.findByCode(reqDto.getStoreTypeCd()).isEmpty())
            throw new ObjectNotFoundException("storeType code");
        if (reqDto.getCountryCd() != null && codeRepository.findByCode(reqDto.getCountryCd()).isEmpty())
            throw new ObjectNotFoundException("country code");
        if (reqDto.getCityCd() != null && codeRepository.findByCode(reqDto.getCityCd()).isEmpty())
            throw new ObjectNotFoundException("city code");

        StoreEntity storeEntity = reqDto.toEntity(createUserId);
        storeRepository.save(storeEntity);
        return new CommonIdResult(storeEntity.getId());
    }

    @Transactional
    public CommonIdResult update(Long id, StoreReqDto reqDto, Long updateUserId) {
        StoreEntity storeEntity = storeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("store"));
        if (reqDto.getBrandCd() != null && codeRepository.findByCode(reqDto.getBrandCd()).isEmpty())
            throw new ObjectNotFoundException("brand code");
        if (reqDto.getStoreTypeCd() != null && codeRepository.findByCode(reqDto.getStoreTypeCd()).isEmpty())
            throw new ObjectNotFoundException("storeType code");
        if (reqDto.getCountryCd() != null && codeRepository.findByCode(reqDto.getCountryCd()).isEmpty())
            throw new ObjectNotFoundException("country code");
        if (reqDto.getCityCd() != null && codeRepository.findByCode(reqDto.getCityCd()).isEmpty())
            throw new ObjectNotFoundException("city code");

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
    public CommonIdResult mappingMember(Long id, List<StoreMappingReqDto> reqDto) {
        StoreEntity storeEntity = storeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("store"));
        List<StoreMemberEntity> oldMembers = storeMemberRepository.findByStoreId(id);
        storeMemberRepository.deleteAll(oldMembers);
        storeMemberRepository.flush();

        for (StoreMappingReqDto storeMappingReqDto : reqDto) {
            userRepository.findByIdAndDel(storeMappingReqDto.getUserId(), false).orElseThrow(UserNotFoundException::new);
            StoreMemberEntity storeMemberEntity = storeMappingReqDto.toEntity();
            storeMemberEntity.setStore(storeEntity);
            storeMemberRepository.save(storeMemberEntity);
        }

        return new CommonIdResult(storeEntity.getId());
    }

    @Transactional(readOnly = true)
    public StoreListResDto getList(String brandCd, String storeTypeCd, String countryCd, String cityCd, String name, Pageable pageable, HttpServletRequest request) {
        Page<StoreResDto> storeResDtoPage;
        String token = jwtTokenProvider.resolveToken(request);
        if (jwtTokenProvider.hasRole(token, RoleEnum.ROLE_SUPER_ADMIN.name()) || jwtTokenProvider.hasRole(token, RoleEnum.ROLE_ADMIN.name())) {
            storeResDtoPage = storeQueryRepository.findStoreList(brandCd, storeTypeCd, countryCd, cityCd, name, pageable, null);
        } else if (jwtTokenProvider.hasRole(token, RoleEnum.ROLE_ADMIN_KR.name())) {
            storeResDtoPage = storeQueryRepository.findStoreList(brandCd, storeTypeCd, CountryEnum.KR.name(), cityCd, name, pageable, null);
        } else if (jwtTokenProvider.hasRole(token, RoleEnum.ROLE_ADMIN_JP.name())) {
            storeResDtoPage = storeQueryRepository.findStoreList(brandCd, storeTypeCd, CountryEnum.JP.name(), cityCd, name, pageable, null);
        } else if (jwtTokenProvider.hasRole(token, RoleEnum.ROLE_ADMIN_PH.name())) {
            storeResDtoPage = storeQueryRepository.findStoreList(brandCd, storeTypeCd, CountryEnum.PH.name(), cityCd, name, pageable, null);
        } else {
            Long userId = Long.valueOf(jwtTokenProvider.getUserPk(token));
            List<Long> storeList = storeMemberRepository.findByUserId(userId).stream().map(StoreMemberEntity::getStore).map(StoreEntity::getId).toList();
            storeResDtoPage = storeQueryRepository.findStoreList(brandCd, storeTypeCd, countryCd, cityCd, name, pageable, storeList);
        }

        return new StoreListResDto(storeResDtoPage);
    }

    @Transactional(readOnly = true)
    public StoreDetailResDto getDetail(Long id) {
        return storeQueryRepository.getDetail(id).orElseThrow(() -> new ObjectNotFoundException("store"));
    }
}
