package com.photoism.cms.domain.community.dto;

import com.photoism.cms.domain.community.entity.CommunityEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "게시물 등록/수정 요청 정보")
@Data
@NoArgsConstructor
public class CommunityReqDto {
    @Schema(description = "게시물 제목", example = "게시물 테스트1")
    @NotBlank
    private String title;

    @Schema(description = "게시물 내용", example = "게시물 내용1")
    @NotBlank
    private String content;
    
    @Schema(description = "첨부 파일")
    private List<Long> files;

    public CommunityEntity toEntity(String div, Long createUserId) {
        return CommunityEntity.builder()
                .div(div)
                .title(title)
                .content(content)
                .createUser(createUserId)
                .updateUser(createUserId)
                .build();
    }
}
