package com.photoism.cms.domain.user.dto;

import com.photoism.cms.domain.user.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "사용자 등록")
@Data
@NoArgsConstructor
public class AddAdminReqDto {
    @Schema(description = "사용자 아이디", example = "test1")
    @NotBlank
    private String userId;

    @Schema(description = "비밀번호", example = "test1")
    @NotBlank
    private String password;

    public UserEntity toEntity() {
        return UserEntity.builder()
                .userId(userId)
                .del(false)
                .build();
    }
}
