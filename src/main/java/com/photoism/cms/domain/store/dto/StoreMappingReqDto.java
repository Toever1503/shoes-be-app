package com.photoism.cms.domain.store.dto;

import com.photoism.cms.common.enums.StoreMemberEnum;
import com.photoism.cms.domain.store.entity.StoreMemberEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "상점 매핑 요청 정보")
@Data
@NoArgsConstructor
public class StoreMappingReqDto {
    @Schema(description = "사용자 아이디", example = "1")
    @NotNull
    private Long userId;

    @Schema(description = "역할", example = "OWNER / STAFF")
    @NotNull
    private StoreMemberEnum role;

    public StoreMemberEntity toEntity() {
        return StoreMemberEntity.builder()
                .userId(userId)
                .role(role.name())
                .build();
    }
}
