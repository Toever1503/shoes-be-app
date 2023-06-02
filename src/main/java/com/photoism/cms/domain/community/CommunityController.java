package com.photoism.cms.domain.community;

import com.photoism.cms.common.enums.CommunityDivEnum;
import com.photoism.cms.common.model.response.BaseResponse;
import com.photoism.cms.common.model.response.CommonIdResult;
import com.photoism.cms.common.model.response.CommonResult;
import com.photoism.cms.common.security.JwtTokenProvider;
import com.photoism.cms.domain.community.dto.CommunityDetailResDto;
import com.photoism.cms.domain.community.dto.CommunityListResDto;
import com.photoism.cms.domain.community.dto.CommunityReqDto;
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
@Tag(name = "07. Community")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/community")
public class CommunityController {
    private final CommunityService communityService;
    private final BaseResponse baseResponse;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "게시물 등록", description = "게시물 등록")
    @PostMapping(value = "/")
    @PreAuthorize("hasAuthority('COMMUNITY_WRITE')")
    public CommonResult<CommonIdResult> add(@Parameter(required = true, name = "div", description = "게시물 구분") @RequestParam @Valid CommunityDivEnum div,
                                            @Parameter(required = true, name = "reqDto", description = "게시물 등록 정보") @RequestBody @Valid CommunityReqDto reqDto,
                                            @Parameter(required = true, description = "servlet") HttpServletRequest request) {
        Long createUserId = Long.parseLong(jwtTokenProvider.getUserPk(request.getHeader("x-api-token")));
        return baseResponse.getContentResult(communityService.add(div, reqDto, createUserId));
    }

    @Operation(summary = "게시물 수정", description = "게시물 수정")
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('COMMUNITY_WRITE')")
    public CommonResult<CommonIdResult> update(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id,
                                               @Parameter(required = true, name = "reqDto", description = "게시물 수정 정보") @RequestBody @Valid CommunityReqDto reqDto,
                                               @Parameter(required = true, description = "servlet") HttpServletRequest request) {
        Long updateUserId = Long.parseLong(jwtTokenProvider.getUserPk(request.getHeader("x-api-token")));
        return baseResponse.getContentResult(communityService.update(id, reqDto, updateUserId));
    }

    @Operation(summary = "게시물 삭제", description = "게시물 삭제")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('COMMUNITY_WRITE')")
    public CommonResult<CommonIdResult> delete(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id,
                                               @Parameter(required = true, description = "servlet") HttpServletRequest request) {
        Long deleteUserId = Long.parseLong(jwtTokenProvider.getUserPk(request.getHeader("x-api-token")));
        return baseResponse.getContentResult(communityService.delete(id, deleteUserId));
    }

    @Operation(summary = "게시물 목록 조회", description = "게시물 목록 조회")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('COMMUNITY_READ')")
    public CommonResult<CommunityListResDto> getList(@Parameter(required = true, name = "div", description = "게시물 구분") @RequestParam @Valid CommunityDivEnum div,
                                                     @Parameter(name = "title", description = "제목") @RequestParam(required = false) String title,
                                                     @PageableDefault(sort="createDate", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable) {
        return baseResponse.getContentResult(communityService.getList(div, title, pageable));
    }

    @Operation(summary = "게시물 상세 조회", description = "게시물 상세 조회")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('COMMUNITY_READ')")
    public CommonResult<CommunityDetailResDto> getDetail(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id) {
        return baseResponse.getContentResult(communityService.getDetail(id));
    }
}
