package com.photoism.cms.domain.popup.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "팝업 상세 조회 응답 정보")
@Data
@AllArgsConstructor
public class PopUpDetailResDto {
    @Schema(description = "아이디", example = "1")
    private Long id;

    @Schema(description = "팝업 제목", example = "팝업 테스트1")
    private String title;

    @Schema(description = "노출여부")
    private Boolean isShow;

    @Schema(description = "다시보지않기 시간", example = "24")
    private Integer ignoreTime;

    @Schema(description = "시작일자")
    private LocalDate startDate;

    @Schema(description = "종료일자")
    private LocalDate endDate;

    @Schema(description = "팝업 레이어 좌측 위치", example = "10")
    private Integer layerLeft;

    @Schema(description = "팝업 레이어 상단 위치", example = "10")
    private Integer layerTop;

    @Schema(description = "팝업 레이어 넓이", example = "300")
    private Integer layerWidth;

    @Schema(description = "팝업 레이어 높이", example = "400")
    private Integer layerHeight;

    @Schema(description = "내용", example = "테스트 내용")
    private String content;

    @Schema(description = "생성일자")
    private LocalDateTime createDate;
}
