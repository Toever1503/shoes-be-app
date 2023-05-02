package com.photoism.cms.domain.user.dto;

import com.photoism.cms.domain.user.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "사용자 상세 조회 응답 정보")
@Data
public class UserDetailResDto {
    @Schema(description = "아이디")
    private Long id;

    @Schema(description = "사용자 아이디")
    private String userId;

    @Schema(description = "이름")
    private String name;

    @Schema(description = "부서")
    private String department;

    @Schema(description = "연락처")
    private String phone;

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "생성일자")
    private LocalDateTime createDate;

    public UserDetailResDto(UserEntity entity) {
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.name = entity.getName();
        this.department = entity.getDepartment();
        this.phone = entity.getPhone();
        this.email = entity.getEmail();
        this.createDate = entity.getCreateDate();
    }
}
