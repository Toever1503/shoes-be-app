package com.photoism.cms.domain.popup.dto;

import com.photoism.cms.domain.popup.entity.PopUpEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Schema(description = "팝업 등록/수정 요청 정보")
@Data
@NoArgsConstructor
public class PopUpReqDto {
    @Schema(description = "팝업 제목", example = "팝업 테스트1")
    @NotBlank
    private String title;

    @Schema(description = "노출여부")
    @NotNull
    private Boolean isShow;

    @Schema(description = "다시보지않기 시간", example = "24")
    @NotNull
    private Integer ignoreTime;

    @Schema(description = "시작일자")
    @NotNull
    private LocalDate startDate;

    @Schema(description = "종료일자")
    @NotNull
    private LocalDate endDate;

    @Schema(description = "팝업 레이어 좌측 위치", example = "10")
    @NotNull
    private Integer layerLeft;

    @Schema(description = "팝업 레이어 상단 위치", example = "10")
    @NotNull
    private Integer layerTop;

    @Schema(description = "팝업 레이어 넓이", example = "300")
    @NotNull
    private Integer layerWidth;

    @Schema(description = "팝업 레이어 높이", example = "400")
    @NotNull
    private Integer layerHeight;

    @Schema(description = "내용", example = "테스트 내용")
    @NotBlank
    private String content;

    public PopUpEntity toEntity(Long createUserId) {
        return PopUpEntity.builder()
                .title(title)
                .isShow(isShow)
                .ignoreTime(ignoreTime)
                .startDate(startDate)
                .endDate(endDate)
                .layerLeft(layerLeft)
                .layerTop(layerTop)
                .layerWidth(layerWidth)
                .layerHeight(layerHeight)
                .content(content)
                .createUser(createUserId)
                .updateUser(createUserId)
                .build();
    }
}
