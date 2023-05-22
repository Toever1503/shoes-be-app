package com.photoism.cms.domain.kiosk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "장비 상세 조회 응답 정보")
@Data
@AllArgsConstructor
public class KioskDetailResDto {
    @Schema(description = "아이디", example = "1")
    private Long id;

    @Schema(description = "상점 아이디", example = "1")
    private Long storeId;

    @Schema(description = "기기 번호", example = "BLK-KR-230425-1")
    private String deviceNo;

    @Schema(description = "기기 아이디", example = "PTS-1343209")
    private String deviceId;

    @Schema(description = "라이센스", example = "15345-1526156-2315645-32351")
    private String license;

    @Schema(description = "애니데스크", example = "PTS-1343209")
    private String anydesk;

    @Schema(description = "카메라", example = "850D")
    private String camera;

    @Schema(description = "카메라 시리얼", example = "278033000081")
    private String cameraSerial;

    @Schema(description = "카메라 렌즈 줌", example = "24")
    private String cameraLensZoom;

    @Schema(description = "카메라 ISO", example = "800")
    private String cameraISO;

    @Schema(description = "카메라 셔터 속도", example = "1/30")
    private String cameraShutterSpeed;

    @Schema(description = "카메라 조리개", example = "9.0F")
    private String cameraAperture;

    @Schema(description = "카메라 색온도", example = "K5000")
    private String cameraColorTemp;

    @Schema(description = "카메라 WB보정", example = "M2")
    private String cameraWbCal;

    @Schema(description = "스트로브 세기", example = "2.0")
    private String strobeIntensity;

    @Schema(description = "프린터", example = "RX1")
    private String printer;

    @Schema(description = "프린터 수", example = "1")
    private Integer printerCnt;

    @Schema(description = "모니터", example = "철제")
    private String monitor;

    @Schema(description = "PC", example = "구형")
    private String pc;

    @Schema(description = "I/O보드", example = "구형")
    private String ioBoard;

    @Schema(description = "인터넷", example = "본사 라우터")
    private String internet;

    @Schema(description = "인터넷 시리얼", example = "1458384")
    private String internetSerial;

    @Schema(description = "생성일자")
    private LocalDateTime createDate;
}
