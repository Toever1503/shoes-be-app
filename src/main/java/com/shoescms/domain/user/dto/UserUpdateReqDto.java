package com.shoescms.domain.user.dto;

import com.shoescms.common.enums.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "사용자 수정 요청 정보")
@Data
@NoArgsConstructor
public class UserUpdateReqDto {
    @Schema(description = "ROLE", example = "ROLE_STORE_OWNER")
    private RoleEnum role;

    @Schema(description = "이름", example = "이름")
    @NotBlank
    private String name;
    
    @Schema(description = "연락처", example = "010-1234-1234")
    @NotBlank
    private String phone;

    @Schema(description = "이메일", example = "example@seobuk.kr")
    private String email;
}