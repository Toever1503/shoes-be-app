package com.photoism.cms.domain.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "ROLE 조회 응답 정보")
@Data
public class RoleResDto {
    @Schema(description = "ROLE 코드", example = "ROLE_OPERATION")
    private String roleCd;

    @Schema(description = "ROLE 명칭(한글)", example = "운영팀")
    private String roleNmKr;

    @Schema(description = "ROLE 명칭(영문)", example = "Operation team")
    private String roleNmEn;
    
    @Schema(description = "권한 리스트")
    private List<PrivilegeResDto> privileges;

    public RoleResDto(String roleCd, String roleNmKr, String roleNmEn) {
        this.roleCd = roleCd;
        this.roleNmKr = roleNmKr;
        this.roleNmEn = roleNmEn;
    }
}
