package com.photoism.cms.domain.file.dto;

import com.photoism.cms.domain.file.entity.FileEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "파일 업로드 리스트 응답 정보")
@Data
public class FileListResDto {
    @Schema(description = "파일 리스트")
    public List<FileResDto> files = new ArrayList<>();

    public FileListResDto(List<FileEntity> fileList) {
        for (FileEntity file : fileList) {
            this.files.add(new FileResDto(file));
        }
    }

    public void setFile(FileEntity file) {
        FileResDto dto = new FileResDto(file);
        files.add(dto);
    }
}
