package com.photoism.cms.domain.community;

import com.photoism.cms.common.exception.ObjectNotFoundException;
import com.photoism.cms.common.model.FileDivision;
import com.photoism.cms.common.model.response.CommonIdResult;
import com.photoism.cms.domain.community.dto.NoticeDetailResDto;
import com.photoism.cms.domain.community.dto.NoticeListResDto;
import com.photoism.cms.domain.community.dto.NoticeReqDto;
import com.photoism.cms.domain.community.entity.NoticeEntity;
import com.photoism.cms.domain.community.entity.NoticeFileEntity;
import com.photoism.cms.domain.community.repository.NoticeQueryRepository;
import com.photoism.cms.domain.community.repository.NoticeRepository;
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
    private final NoticeRepository noticeRepository;
    private final NoticeQueryRepository noticeQueryRepository;
    private final FileEntityRepository fileEntityRepository;

    @Transactional
    public CommonIdResult addNotice(NoticeReqDto reqDto, Long createUserId) {
        NoticeEntity noticeEntity = reqDto.toEntity(createUserId);
        // 첨부파일
        for (Long fileId : reqDto.getFiles()) {
            fileEntityRepository.findByIdAndDivision(fileId, FileDivision.notice.name()).orElseThrow(() -> new ObjectNotFoundException("file or div"));
            NoticeFileEntity noticeFileEntity = NoticeFileEntity.builder().fileId(fileId).build();
            noticeFileEntity.setNotice(noticeEntity);
        }
        noticeRepository.save(noticeEntity);
        return new CommonIdResult(noticeEntity.getId());
    }

    @Transactional
    public CommonIdResult updateNotice(Long id, NoticeReqDto reqDto, Long updateUserId) {
        NoticeEntity noticeEntity = noticeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("notice"));
        noticeEntity.update(reqDto, updateUserId);

        // 첨부파일
        List<NoticeFileEntity> oldFileList = new ArrayList<>(noticeEntity.getFiles());
        List<Long> oldFileIds = new ArrayList<>(oldFileList.stream().map(NoticeFileEntity::getFileId).toList());
        List<Long> newFileIds = new ArrayList<>();
        for (Long fileId : reqDto.getFiles()) {
            if (oldFileIds.contains(fileId))
                oldFileIds.remove(fileId);
            else
                newFileIds.add(fileId);
        }
        // 없어진 파일 삭제
        for (NoticeFileEntity fileEntity : oldFileList) {
            if (oldFileIds.contains(fileEntity.getFileId())) {
                noticeEntity.getFiles().remove(fileEntity);
            }
        }
        // 새로운 파일 매핑
        for (Long newFileId : newFileIds) {
            fileEntityRepository.findByIdAndDivision(newFileId, FileDivision.notice.name()).orElseThrow(() -> new ObjectNotFoundException("file or div"));
            NoticeFileEntity noticeFileEntity = NoticeFileEntity.builder().fileId(newFileId).build();
            noticeFileEntity.setNotice(noticeEntity);
        }

        return new CommonIdResult(noticeEntity.getId());
    }

    @Transactional
    public CommonIdResult deleteNotice(Long id, Long deleteUserId) {
        NoticeEntity noticeEntity = noticeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("notice"));
        noticeEntity.setDel(deleteUserId);
        return new CommonIdResult(id);
    }

    @Transactional(readOnly = true)
    public NoticeListResDto getNoticeList(String title, Pageable pageable) {
        return new NoticeListResDto(noticeQueryRepository.findNoticeList(title, pageable));
    }

    @Transactional(readOnly = true)
    public NoticeDetailResDto getNoticeDetail(Long id) {
        NoticeDetailResDto resDto =  noticeQueryRepository.getNoticeDetail(id).orElseThrow(() -> new ObjectNotFoundException("notice"));
        List<FileResDto> fileList = noticeQueryRepository.getFiles(id);
        resDto.setFiles(fileList);
        return resDto;
    }
}
