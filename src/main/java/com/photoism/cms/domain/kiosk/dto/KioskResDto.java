package com.photoism.cms.domain.kiosk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "장비 조회 응답 정보")
@Data
@AllArgsConstructor
public class KioskResDto {
    @Schema(description = "아이디", example = "1")
    private Long id;

    @Schema(description = "상점 아이디", example = "1")
    private Long storeId;

    @Schema(description = "기기 번호", example = "BLK-KR-230425-1")
    private String deviceNo;

    @Schema(description = "기기 아이디", example = "PTS-1343209")
    private String deviceId;

    @Schema(description = "생성일자")
    private LocalDateTime createDate;
}
