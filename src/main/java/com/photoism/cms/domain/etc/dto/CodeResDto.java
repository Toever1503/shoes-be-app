package com.photoism.cms.domain.etc.dto;

import com.photoism.cms.domain.etc.entity.CodeEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "코드 응답 정보")
@Data
public class CodeResDto {
    @Schema(description = "code", example = "JOI01")
    private String code;

    @Schema(description = "name(kr)", example = "코드명(한글)")
    private String nameKr;

    @Schema(description = "name(en)", example = "코드명(영문)")
    private String nameEn;

    public CodeResDto(CodeEntity codeEntity) {
        this.code = codeEntity.getCode();
        this.nameKr = codeEntity.getNameKr();
        this.nameEn = codeEntity.getNameEn();
    }
}