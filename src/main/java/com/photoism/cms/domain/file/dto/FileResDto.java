package com.photoism.cms.domain.file.dto;

import com.photoism.cms.domain.file.entity.FileEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "파일 업로드 응답 정보")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileResDto {
    @Schema(description = "아이디", example = "1")
    private Long id;

    @Schema(description = "파일명", example = "abc.jpg")
    private String name;

    @Schema(description = "파일별칭", example = "abc.jpg")
    private String alterName;

    @Schema(description = "path", example = "")
    private String path;

    @Schema(description = "삭제 여부", example = "0")
    private Boolean del;

    public FileResDto(FileEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.alterName = entity.getAlterName();
        this.path = entity.getPath();
        this.del = entity.getDel();
    }
}
