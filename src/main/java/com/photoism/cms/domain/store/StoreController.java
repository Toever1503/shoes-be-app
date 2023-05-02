package com.photoism.cms.domain.store;

import com.photoism.cms.common.model.response.BaseResponse;
import com.photoism.cms.common.model.response.CommonIdResult;
import com.photoism.cms.common.model.response.CommonResult;
import com.photoism.cms.common.security.JwtTokenProvider;
import com.photoism.cms.domain.store.dto.StoreReqDto;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "02. Store")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/store")
@Hidden
public class StoreController {
    private final StoreService storeService;
    private final BaseResponse baseResponse;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "상점 등록", description = "상점 등록")
    @PostMapping(value = "/")
    public CommonResult<CommonIdResult> add(@Parameter(required = true, name = "reqDto", description = "상점 등록 정보") @RequestBody @Valid StoreReqDto reqDto,
                                            @Parameter(required = true, description = "servlet") HttpServletRequest request) {
        Long createUserId = Long.parseLong(jwtTokenProvider.getUserPk(request.getHeader("x-api-token")));
        return baseResponse.getContentResult(storeService.add(reqDto, createUserId));
    }

    @Operation(summary = "상점 수정", description = "상점 수정")
    @PutMapping(value = "/{id}")
    public CommonResult<CommonIdResult> update(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id,
                                               @Parameter(required = true, name = "reqDto", description = "상점 수정 정보") @RequestBody @Valid StoreReqDto reqDto,
                                               @Parameter(required = true, description = "servlet") HttpServletRequest request) {
        Long updateUserId = Long.parseLong(jwtTokenProvider.getUserPk(request.getHeader("x-api-token")));
        return baseResponse.getContentResult(storeService.update(id, reqDto, updateUserId));
    }

    @Operation(summary = "상점 제거", description = "상점 제거")
    @DeleteMapping(value = "/{id}")
    public CommonResult<CommonIdResult> delete(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id,
                                               @Parameter(required = true, description = "servlet") HttpServletRequest request) {
        Long deleteUserId = Long.parseLong(jwtTokenProvider.getUserPk(request.getHeader("x-api-token")));
        return baseResponse.getContentResult(storeService.delete(id, deleteUserId));
    }

    @Operation(summary = "소유자 매핑", description = "소유자 매핑")
    @PatchMapping(value = "/{id}/mapping")
    public CommonResult<CommonIdResult> mappingOwner(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id,
                                                     @Parameter(required = true, name = "userId", description = "소유자 아이디") @RequestParam Long userId) {
        return baseResponse.getContentResult(storeService.mappingOwner(id, userId));
    }

    @Operation(summary = "소유자 매핑 제거", description = "소유자 매핑 제거")
    @PatchMapping(value = "/{id}/mapping/cancel")
    public CommonResult<CommonIdResult> mappingCancel(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id) {
        return baseResponse.getContentResult(storeService.mappingCancel(id));
    }
}
