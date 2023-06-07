package com.photoism.cms.domain.kiosk.dto;

import com.photoism.cms.domain.kiosk.entity.KioskEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "장비 등록/수정 요청 정보")
@Data
@NoArgsConstructor
public class KioskReqDto {
    @Schema(description = "상점 아이디", example = "1")
    private Long storeId;

    @Schema(description = "기기 번호", example = "BLK-KR-230425-1")
    @NotNull
    private String deviceNo;

    @Schema(description = "기기 아이디", example = "PTS-1343209")
    @NotNull
    private String deviceId;

    @Schema(description = "라이센스", example = "15345-1526156-2315645-32351")
    private String license;

    @Schema(description = "애니데스크", example = "PTS-1343209")
    @NotNull
    private String anydesk;

    @Schema(description = "색상", example = "WHITE")
    private String colorCd;

    @Schema(description = "카메라", example = "850D")
    @NotNull
    private String camera;

    @Schema(description = "카메라 시리얼", example = "278033000081")
    @NotNull
    private String cameraSerial;

    @Schema(description = "카메라 렌즈 줌", example = "24")
    @NotNull
    private String cameraLensZoom;

    @Schema(description = "카메라 ISO", example = "800")
    @NotNull
    private String cameraISO;

    @Schema(description = "카메라 셔터 속도", example = "1/30")
    @NotNull
    private String cameraShutterSpeed;

    @Schema(description = "카메라 조리개", example = "9.0F")
    @NotNull
    private String cameraAperture;

    @Schema(description = "카메라 색온도", example = "K5000")
    @NotNull
    private String cameraColorTemp;

    @Schema(description = "카메라 WB보정", example = "M2")
    @NotNull
    private String cameraWbCal;

    @Schema(description = "스트로브 세기", example = "2.0")
    @NotNull
    private String strobeIntensity;

    @Schema(description = "프린터", example = "RX1")
    @NotNull
    private String printerCd;

    @Schema(description = "프린터 수", example = "1")
    @NotNull
    private Integer printerCnt;

    @Schema(description = "모니터", example = "철제")
    @NotNull
    private String monitor;

    @Schema(description = "PC", example = "구형")
    @NotNull
    private String pc;

    @Schema(description = "I/O보드", example = "구형")
    @NotNull
    private String ioBoard;

    @Schema(description = "인터넷", example = "본사 라우터")
    private String internet;

    @Schema(description = "인터넷 시리얼", example = "1458384")
    private String internetSerial;

    public KioskEntity toEntity(Long createUserId) {
        return KioskEntity.builder()
                .storeId(storeId)
                .deviceNo(deviceNo)
                .deviceId(deviceId)
                .license(license)
                .anydesk(anydesk)
                .colorCd(colorCd)
                .camera(camera)
                .cameraSerial(cameraSerial)
                .cameraLensZoom(cameraLensZoom)
                .cameraISO(cameraISO)
                .cameraShutterSpeed(cameraShutterSpeed)
                .cameraAperture(cameraAperture)
                .cameraColorTemp(cameraColorTemp)
                .cameraWbCal(cameraWbCal)
                .strobeIntensity(strobeIntensity)
                .printerCd(printerCd)
                .printerCnt(printerCnt)
                .monitor(monitor)
                .pc(pc)
                .ioBoard(ioBoard)
                .internet(internet)
                .internetSerial(internetSerial)
                .createUser(createUserId)
                .updateUser(createUserId)
                .build();
    }
}
