package com.photoism.cms.domain.user.dto;

import com.photoism.cms.common.enums.RoleEnum;
import com.photoism.cms.domain.user.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "사용자 등록 요청 정보")
@Data
@NoArgsConstructor
public class UserReqDto {
    @Schema(description = "ROLE", example = "ROLE_STORE_OWNER")
    @NotNull
    private RoleEnum role;

    @Schema(description = "사용자 아이디", example = "test01")
    @NotBlank
    private String userId;

    @Schema(description = "비밀번호", example = "password")
    @NotBlank
    private String password;

    @Schema(description = "이름", example = "이름")
    @NotBlank
    private String name;

    @Schema(description = "소속코드", example = "IT_BUSINESS")
    private String departmentCd;
    
    @Schema(description = "연락처", example = "010-1234-1234")
    @NotBlank
    private String phone;

    @Schema(description = "이메일", example = "example@seobuk.kr")
    private String email;

    public UserEntity toEntity() {
        return UserEntity.builder()
                .userId(userId)
                .name(name)
                .departmentCd(departmentCd)
                .phone(phone)
                .email(email)
                .del(false)
                .build();
    }
}
