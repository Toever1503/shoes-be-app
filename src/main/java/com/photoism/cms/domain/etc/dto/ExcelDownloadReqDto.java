package com.photoism.cms.domain.etc.dto;

import com.photoism.cms.common.enums.ExcelEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Schema(description = "엑셀 다운로드 요청 정보")
@Data
@NoArgsConstructor
public class ExcelDownloadReqDto {
    @Schema(description = "결과를 받아올 엑셀 id", example = "XLSX001")
    @NotNull
    private ExcelEnum excelEnumId;

    @Schema(description = "생성할 시트명", nullable = true, example = "Sheet1")
    private String sheetName;

    @Schema(description = "엑셀의 헤더/셀 정보 리스트")
    @NotNull
    private List<@Valid ExcelCellInfoReqDto> excelCellInfo;

    @Schema(description = "쿼리 파라미터 맵 (ex : 'id' : '1')", nullable = true, example = "{\"id\": \"1\"}")
    private Map<String, Object> reqSql;

    @Schema(description = "엑셀 파일명", example = "AC0001_admin01_20200722_162700.xlsx")
    @NotBlank
    private String exlFileNm;
}
