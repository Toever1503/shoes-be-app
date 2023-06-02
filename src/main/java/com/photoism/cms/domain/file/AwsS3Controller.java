package com.photoism.cms.domain.file;

import com.photoism.cms.common.enums.FileDivisionEnum;
import com.photoism.cms.common.model.response.BaseResponse;
import com.photoism.cms.common.model.response.CommonResult;
import com.photoism.cms.domain.file.dto.FileListResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Tag(name = "10. File")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/file")
public class AwsS3Controller {
    private final AwsS3Service awsS3Service;
    private final BaseResponse baseResponse;

    @Operation(summary = "s3 파일 업로드", description = "s3 파일 업로드")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResult<FileListResDto> upload(@Parameter(schema = @Schema(allowableValues = {"tmp", "community", "popup"}), required = true, name = "prefix", description = "파일구분", example = "tmp") @RequestPart String prefix,
                                               @Parameter(required = true, name = "files",  description = "파일 리스트") @RequestPart("file") MultipartFile[] files) {
        FileDivisionEnum fileDivisionEnum = FileDivisionEnum.valueOf(prefix);
        return baseResponse.getContentResult(awsS3Service.upload(fileDivisionEnum, List.of(files)));
    }

    @Operation(summary = "s3 파일 다운로드", description = "s3 파일 다운로드")
    @PostMapping(value = "/download")
    public ResponseEntity<byte[]> download(@Parameter(required = true, name = "id", description = "파일 아이디") @RequestParam Long id) throws IOException {
        return awsS3Service.download(id);
    }
}
