package com.photoism.cms.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Schema(description = "인증 결과")
@Data
public class SignInResDto {
    @Schema(description = "사용자 아이디", example = "test1")
    @NotNull
    private String userId;

    @Schema(description = "사용자 role", example = "ADMIN")
    @NotNull
    private List<String> roles;

    @Schema(description = "access token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9TVVBFUl9BRE1JTiJdLCJpYXQiOjE2ODAxNzI0MDQsImV4cCI6MTY4MDI1ODgwNH0.gTech9LAvdaeaxFDiN6B8xnW1VdGNi4A_Ha17Rjquvc")
    @NotNull
    private String accessToken;

    @Schema(description = "access token expire in", example = "Fri Mar 31 19:33:24 KST 2023")
    @NotNull
    private String accessExpireIn;

    @Schema(description = "refresh token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9TVVBFUl9BRE1JTiJdLCJpYXQiOjE2ODAxNzI0MDQsImV4cCI6MTY4Mjc2NDQwNH0.Vqw74gwMjJwi-pVgWq-C6eNM3MM755eUu5ZPOBwsJBI")
    @NotNull
    private String refreshToken;

    @Schema(description = "refresh token expire in", example = "Sat Apr 29 19:33:24 KST 2023")
    @NotNull
    private String refreshExpireIn;

    @Builder
    public SignInResDto(@NotNull String accessToken, @NotNull String userId, @NotNull List<String> roles, @NotNull String accessExpireIn, @NotNull String refreshToken, @NotNull String refreshExpireIn) {
        this.userId = userId;
        this.roles = roles;
        this.accessToken = accessToken;
        this.accessExpireIn = accessExpireIn;
        this.refreshToken = refreshToken;
        this.refreshExpireIn = refreshExpireIn;
    }
}
