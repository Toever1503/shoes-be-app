package com.photoism.cms.domain.kiosk;

import com.photoism.cms.common.model.response.BaseResponse;
import com.photoism.cms.common.model.response.CommonIdResult;
import com.photoism.cms.common.model.response.CommonResult;
import com.photoism.cms.common.security.JwtTokenProvider;
import com.photoism.cms.domain.kiosk.dto.KioskDetailResDto;
import com.photoism.cms.domain.kiosk.dto.KioskListResDto;
import com.photoism.cms.domain.kiosk.dto.KioskReqDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "04. Kiosk")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/kiosk")
public class KioskController {
    private final KioskService kioskService;
    private final BaseResponse baseResponse;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "장비 등록", description = "장비 등록")
    @PostMapping(value = "/")
    @PreAuthorize("hasAuthority('DEVICE_WRITE')")
    public CommonResult<CommonIdResult> add(@Parameter(required = true, name = "reqDto", description = "장비 등록 정보") @RequestBody @Valid KioskReqDto reqDto,
                                            @Parameter(required = true, description = "servlet") HttpServletRequest request) {
        Long createUserId = Long.parseLong(jwtTokenProvider.getUserPk(request.getHeader("x-api-token")));
        return baseResponse.getContentResult(kioskService.add(reqDto, createUserId));
    }

    @Operation(summary = "장비 수정", description = "장비 수정")
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('DEVICE_WRITE')")
    public CommonResult<CommonIdResult> update(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id,
                                               @Parameter(required = true, name = "reqDto", description = "장비 수정 정보") @RequestBody @Valid KioskReqDto reqDto,
                                               @Parameter(required = true, description = "servlet") HttpServletRequest request) {
        Long updateUserId = Long.parseLong(jwtTokenProvider.getUserPk(request.getHeader("x-api-token")));
        return baseResponse.getContentResult(kioskService.update(id, reqDto, updateUserId));
    }

    @Operation(summary = "장비 제거", description = "장비 제거")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('DEVICE_WRITE')")
    public CommonResult<CommonIdResult> delete(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id,
                                               @Parameter(required = true, description = "servlet") HttpServletRequest request) {
        Long deleteUserId = Long.parseLong(jwtTokenProvider.getUserPk(request.getHeader("x-api-token")));
        return baseResponse.getContentResult(kioskService.delete(id, deleteUserId));
    }

    @Operation(summary = "장비-상점 매핑", description = "장비-상점 매핑")
    @PatchMapping(value = "/{id}/mapping")
    @PreAuthorize("hasAuthority('DEVICE_WRITE')")
    public CommonResult<CommonIdResult> mappingStore(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id,
                                                     @Parameter(name = "storeId", description = "상점 아이디") @RequestParam(required = false) Long storeId) {
        return baseResponse.getContentResult(kioskService.mappingStore(id, storeId));
    }

    @Operation(summary = "장비 목록 조회", description = "장비 목록 조회")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('DEVICE_READ')")
    public CommonResult<KioskListResDto> getList(@Parameter(name = "storeId", description = "상점 아이디") @RequestParam(required = false) Long storeId,
                                                 @PageableDefault(sort="createDate", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable) {
        return baseResponse.getContentResult(kioskService.getList(storeId, pageable));
    }

    @Operation(summary = "장비 상세 조회", description = "장비 상세 조회")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('DEVICE_READ')")
    public CommonResult<KioskDetailResDto> getDetail(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id) {
        return baseResponse.getContentResult(kioskService.getDetail(id));
    }

//    @Operation(summary = "시퀀스 발생", description = "시퀀스 발생")
//    @PostMapping(value = "/seq")
//    public CommonResult<String> addSeq(@Parameter(required = true, name = "reqDto", description = "시퀀스 등록 정보") @RequestBody @Valid KioskSeqReqDto reqDto) {
//        return baseResponse.getContentResult(kioskService.addSeq(reqDto));
//    }
}
