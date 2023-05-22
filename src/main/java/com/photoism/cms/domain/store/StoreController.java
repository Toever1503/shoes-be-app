package com.photoism.cms.domain.store;

import com.photoism.cms.common.model.response.BaseResponse;
import com.photoism.cms.common.model.response.CommonIdResult;
import com.photoism.cms.common.model.response.CommonResult;
import com.photoism.cms.common.security.JwtTokenProvider;
import com.photoism.cms.domain.store.dto.StoreDetailResDto;
import com.photoism.cms.domain.store.dto.StoreListResDto;
import com.photoism.cms.domain.store.dto.StoreMappingReqDto;
import com.photoism.cms.domain.store.dto.StoreReqDto;
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

import java.util.List;

@Slf4j
@Tag(name = "03. Store")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/store")
public class StoreController {
    private final StoreService storeService;
    private final BaseResponse baseResponse;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "상점 등록", description = "상점 등록")
    @PostMapping(value = "/")
    @PreAuthorize("hasAuthority('STORE_WRITE')")
    public CommonResult<CommonIdResult> add(@Parameter(required = true, name = "reqDto", description = "상점 등록 정보") @RequestBody @Valid StoreReqDto reqDto,
                                            @Parameter(required = true, description = "servlet") HttpServletRequest request) {
        Long createUserId = Long.parseLong(jwtTokenProvider.getUserPk(request.getHeader("x-api-token")));
        return baseResponse.getContentResult(storeService.add(reqDto, createUserId));
    }

    @Operation(summary = "상점 수정", description = "상점 수정")
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('STORE_WRITE')")
    public CommonResult<CommonIdResult> update(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id,
                                               @Parameter(required = true, name = "reqDto", description = "상점 수정 정보") @RequestBody @Valid StoreReqDto reqDto,
                                               @Parameter(required = true, description = "servlet") HttpServletRequest request) {
        Long updateUserId = Long.parseLong(jwtTokenProvider.getUserPk(request.getHeader("x-api-token")));
        return baseResponse.getContentResult(storeService.update(id, reqDto, updateUserId));
    }

    @Operation(summary = "상점 제거", description = "상점 제거")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('STORE_WRITE')")
    public CommonResult<CommonIdResult> delete(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id,
                                               @Parameter(required = true, description = "servlet") HttpServletRequest request) {
        Long deleteUserId = Long.parseLong(jwtTokenProvider.getUserPk(request.getHeader("x-api-token")));
        return baseResponse.getContentResult(storeService.delete(id, deleteUserId));
    }

    @Operation(summary = "상점 멤버 매핑", description = "상점 멤버 매핑")
    @PostMapping(value = "/{id}/member")
    @PreAuthorize("hasAuthority('STORE_WRITE')")
    public CommonResult<CommonIdResult> mappingMember(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id,
                                                      @Parameter(required = true, name = "reqDto", description = "상점 멤버 매핑 정보") @RequestBody @Valid List<StoreMappingReqDto> reqDto) {
        return baseResponse.getContentResult(storeService.mappingMember(id, reqDto));
    }

    @Operation(summary = "상점 목록 조회", description = "상점 목록 조회")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('STORE_READ')")
    public CommonResult<StoreListResDto> getList(@Parameter(name = "brand", description = "브랜드 코드") @RequestParam(required = false) String brandCd,
                                                 @Parameter(name = "storeType", description = "상점 형태 코드") @RequestParam(required = false) String storeTypeCd,
                                                 @Parameter(name = "country", description = "국가 코드") @RequestParam(required = false) String countryCd,
                                                 @Parameter(name = "city", description = "도시 코드") @RequestParam(required = false) String cityCd,
                                                 @Parameter(name = "name", description = "상점명") @RequestParam(required = false) String name,
                                                 @PageableDefault(sort="createDaste", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable,
                                                 @Parameter(required = true, description = "servlet") HttpServletRequest request) {
        return baseResponse.getContentResult(storeService.getList(brandCd, storeTypeCd, countryCd, cityCd, name, pageable, request));
    }

    @Operation(summary = "상점 상세 조회", description = "상점 상세 조회")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('STORE_READ')")
    public CommonResult<StoreDetailResDto> getDetail(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id) {
        return baseResponse.getContentResult(storeService.getDetail(id));
    }
}
