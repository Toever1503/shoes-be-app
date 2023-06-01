package com.photoism.cms.domain.community.dto;

import com.photoism.cms.domain.community.entity.NoticeEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "공지 등록/수정 요청 정보")
@Data
@NoArgsConstructor
public class NoticeReqDto {
    @Schema(description = "공지 제목", example = "공지 테스트1")
    @NotBlank
    private String title;

    @Schema(description = "공지 내용", example = "공지 내용1")
    @NotBlank
    private String content;
    
    @Schema(description = "첨부 파일")
    private List<Long> files;

    public NoticeEntity toEntity(Long createUserId) {
        return NoticeEntity.builder()
                .title(title)
                .content(content)
                .createUser(createUserId)
                .updateUser(createUserId)
                .build();
    }
}
