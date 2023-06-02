package com.photoism.cms.domain.community.dto;

import com.photoism.cms.domain.file.dto.FileResDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "게시물 상세 조회 응답 정보")
@Data
@AllArgsConstructor
public class CommunityDetailResDto {
    @Schema(description = "아이디", example = "1")
    private Long id;

    @Schema(description = "제목", example = "게시물 테스트1")
    private String title;

    @Schema(description = "내용", example = "테스트 내용")
    private String content;

    @Schema(description = "조회수", example = "1")
    private Integer readCount;

    @Schema(description = "생성일자")
    private LocalDateTime createDate;
    
    @Schema(description = "첨부파일")
    private List<FileResDto> files;

    public CommunityDetailResDto(Long id, String title, String content, Integer readCount, LocalDateTime createDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.readCount = readCount;
        this.createDate = createDate;
    }
}
