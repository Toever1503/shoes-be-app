package com.photoism.cms.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "사용자 로그인")
@Data
@NoArgsConstructor
public class SignInReqDto {
    @Schema(description = "사용자 아이디", example = "test1")
    @NotBlank
    private String userId;

    @Schema(description = "비밀번호", example = "test1")
    @NotBlank
    private String password;
}
