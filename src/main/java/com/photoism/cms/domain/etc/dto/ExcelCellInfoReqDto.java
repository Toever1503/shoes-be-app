package com.photoism.cms.domain.etc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "엑셀 셀 요청 정보")
@Data
@NoArgsConstructor
public class ExcelCellInfoReqDto {
    @Schema(description = "셀에 넣을 Column ID. ex) coCd, dtbtGpCd", example = "id")
    @NotBlank
    private String columnId;

    @Schema(description = "헤더에 표현할 문자열", example = "아이디")
    @NotBlank
    private String headerDesc;
}
