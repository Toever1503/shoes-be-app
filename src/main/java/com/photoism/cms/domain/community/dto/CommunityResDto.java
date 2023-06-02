package com.photoism.cms.domain.community.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "게시물 조회 응답 정보")
@Data
@AllArgsConstructor
public class CommunityResDto {
    @Schema(description = "아이디", example = "1")
    private Long id;

    @Schema(description = "제목", example = "title")
    private String title;

    @Schema(description = "첨부파일 유무")
    private Boolean fileYn;

    @Schema(description = "조회수", example = "1")
    private Integer readCount;

    @Schema(description = "생성일자")
    private LocalDateTime createDate;
}
