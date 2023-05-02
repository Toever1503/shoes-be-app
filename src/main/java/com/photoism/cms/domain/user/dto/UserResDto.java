package com.photoism.cms.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "사용자 조회 응답 정보")
@Data
public class UserResDto {
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

    public UserResDto(Long id, String userId, String name, String department, String phone, String email, LocalDateTime createDate) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.department = department;
        this.phone = phone;
        this.email = email;
        this.createDate = createDate;
    }
}
