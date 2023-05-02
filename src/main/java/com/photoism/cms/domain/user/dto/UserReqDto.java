package com.photoism.cms.domain.user.dto;

import com.photoism.cms.common.enums.RoleEnum;
import com.photoism.cms.domain.user.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "사용자 등록/수정 요청 정보")
@Data
@NoArgsConstructor
public class UserReqDto {
    @Schema(description = "권한 리스트")
    @NotNull
    List<RoleEnum> roleList;

    @Schema(description = "사용자 아이디", example = "test01")
    @NotBlank
    private String userId;

    @Schema(description = "비밀번호", example = "password")
    @NotBlank
    private String password;

    @Schema(description = "성명", example = "이름")
    @NotBlank
    private String name;

    @Schema(description = "부서", example = "IT 사업부")
    private String department;
    
    @Schema(description = "휴대전화번호", example = "010-1234-1234")
    @NotBlank
    private String phone;

    @Schema(description = "이메일", example = "example@seobuk.kr")
    private String email;

    public UserEntity toEntity() {
        return UserEntity.builder()
                .userId(userId)
                .name(name)
                .department(department)
                .phone(phone)
                .email(email)
                .del(false)
                .build();
    }
}
