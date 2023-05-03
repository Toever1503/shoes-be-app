package com.photoism.cms.domain.etc;

import com.photoism.cms.common.enums.CodeGroupEnum;
import com.photoism.cms.common.model.response.BaseResponse;
import com.photoism.cms.common.model.response.CommonBaseResult;
import com.photoism.cms.common.model.response.CommonResult;
import com.photoism.cms.domain.etc.dto.CodeResDto;
import com.photoism.cms.domain.etc.dto.ExcelDownloadReqDto;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "11. Etc")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/etc")
@Hidden
public class CommonController {
    private final BaseResponse baseResponse;
    private final CommonService commonService;

    @Operation(summary = "코드 조회", description = "코드 조회")
    @GetMapping(value = "/code")
    public CommonResult<List<CodeResDto>> getCode(@Parameter(required = true, description = "조회할 코드 그룹", example = "ROLE") @RequestParam CodeGroupEnum codeGroup) {
        return baseResponse.getContentResult(commonService.getCode(codeGroup));
    }

    @Operation(summary = "엑셀 다운로드", description = "엑셀 다운로드")
    @PostMapping(value = "/excelDownload")
    public void excelDownload(@Parameter(required = true, description = "servlet") HttpServletResponse response,
                              @Parameter(required = true, name = "reqDto", description = "엑셀 다운로드 정보") @RequestBody @Valid ExcelDownloadReqDto reqDto) {
        commonService.excelDownload(response, reqDto);
    }

    @Operation(summary = "health check", description = "health check")
    @RequestMapping(value = "/health-check")
    @ResponseStatus(HttpStatus.OK)
    public CommonBaseResult ok() {
        return baseResponse.getSuccessResult();
    }
}
