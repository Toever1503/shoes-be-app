package com.photoism.cms.domain.admin.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "권한 수정 요청 정보")
@Data
@NoArgsConstructor
public class PrivilegeReqDto {
    @Schema(description = "ROLE 코드", example = "ROLE_OPERATION")
    @NotNull
    private String roleCd;

    @ArraySchema(schema = @Schema(description = "권한 코드", example = "COMMUNITY_READ"))
    private List<String> privileges;
}
