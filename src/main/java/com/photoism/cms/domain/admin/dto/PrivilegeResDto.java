package com.photoism.cms.domain.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "권한 조회 응답 정보")
@Data
@AllArgsConstructor
public class PrivilegeResDto {
    @Schema(description = "권한 코드", example = "REVENUE_READ")
    private String privilegeCd;

    @Schema(description = "권한 명칭(한글)", example = "매출 읽기")
    private String privilegeNmKr;

    @Schema(description = "권한 명칭(영문)", example = "Revenue read")
    private String privilegeNmEn;
}
