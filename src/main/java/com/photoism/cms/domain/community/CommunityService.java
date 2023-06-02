package com.photoism.cms.domain.community;

import com.photoism.cms.common.enums.CommunityDivEnum;
import com.photoism.cms.common.exception.ObjectNotFoundException;
import com.photoism.cms.common.enums.FileDivisionEnum;
import com.photoism.cms.common.model.response.CommonIdResult;
import com.photoism.cms.domain.community.dto.CommunityDetailResDto;
import com.photoism.cms.domain.community.dto.CommunityListResDto;
import com.photoism.cms.domain.community.dto.CommunityReqDto;
import com.photoism.cms.domain.community.entity.CommunityEntity;
import com.photoism.cms.domain.community.entity.CommunityFileEntity;
import com.photoism.cms.domain.community.repository.CommunityQueryRepository;
import com.photoism.cms.domain.community.repository.CommunityRepository;
import com.photoism.cms.domain.file.dto.FileResDto;
import com.photoism.cms.domain.file.repository.FileEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityQueryRepository communityQueryRepository;
    private final FileEntityRepository fileEntityRepository;

    @Transactional
    public CommonIdResult add(CommunityDivEnum div, CommunityReqDto reqDto, Long createUserId) {
        CommunityEntity communityEntity = reqDto.toEntity(div.name(), createUserId);
        // 첨부파일
        for (Long fileId : reqDto.getFiles()) {
            fileEntityRepository.findByIdAndDivision(fileId, FileDivisionEnum.community.name()).orElseThrow(() -> new ObjectNotFoundException("file or div"));
            CommunityFileEntity communityFileEntity = CommunityFileEntity.builder().fileId(fileId).build();
            communityFileEntity.setCommunity(communityEntity);
        }
        communityRepository.save(communityEntity);
        return new CommonIdResult(communityEntity.getId());
    }

    @Transactional
    public CommonIdResult update(Long id, CommunityReqDto reqDto, Long updateUserId) {
        CommunityEntity communityEntity = communityRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("community"));
        communityEntity.update(reqDto, updateUserId);

        // 첨부파일
        List<CommunityFileEntity> oldFileList = new ArrayList<>(communityEntity.getFiles());
        List<Long> oldFileIds = new ArrayList<>(oldFileList.stream().map(CommunityFileEntity::getFileId).toList());
        List<Long> newFileIds = new ArrayList<>();
        for (Long fileId : reqDto.getFiles()) {
            if (oldFileIds.contains(fileId))
                oldFileIds.remove(fileId);
            else
                newFileIds.add(fileId);
        }
        // 없어진 파일 삭제
        for (CommunityFileEntity fileEntity : oldFileList) {
            if (oldFileIds.contains(fileEntity.getFileId())) {
                communityEntity.getFiles().remove(fileEntity);
            }
        }
        // 새로운 파일 매핑
        for (Long newFileId : newFileIds) {
            fileEntityRepository.findByIdAndDivision(newFileId, FileDivisionEnum.community.name()).orElseThrow(() -> new ObjectNotFoundException("file or div"));
            CommunityFileEntity communityFileEntity = CommunityFileEntity.builder().fileId(newFileId).build();
            communityFileEntity.setCommunity(communityEntity);
        }

        return new CommonIdResult(communityEntity.getId());
    }

    @Transactional
    public CommonIdResult delete(Long id, Long deleteUserId) {
        CommunityEntity communityEntity = communityRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("community"));
        communityEntity.setDel(deleteUserId);
        return new CommonIdResult(id);
    }

    @Transactional(readOnly = true)
    public CommunityListResDto getList(CommunityDivEnum div, String title, Pageable pageable) {
        return new CommunityListResDto(communityQueryRepository.findList(div.name(), title, pageable));
    }

    @Transactional(readOnly = true)
    public CommunityDetailResDto getDetail(Long id) {
        CommunityDetailResDto resDto =  communityQueryRepository.getDetail(id).orElseThrow(() -> new ObjectNotFoundException("community"));
        List<FileResDto> fileList = communityQueryRepository.getFiles(id);
        resDto.setFiles(fileList);
        return resDto;
    }
}
