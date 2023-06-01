package com.photoism.cms.domain.community;

import com.photoism.cms.common.model.response.BaseResponse;
import com.photoism.cms.common.model.response.CommonIdResult;
import com.photoism.cms.common.model.response.CommonResult;
import com.photoism.cms.common.security.JwtTokenProvider;
import com.photoism.cms.domain.community.dto.NoticeDetailResDto;
import com.photoism.cms.domain.community.dto.NoticeListResDto;
import com.photoism.cms.domain.community.dto.NoticeReqDto;
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

    @Operation(summary = "공지 등록", description = "공지 등록")
    @PostMapping(value = "/notice")
    @PreAuthorize("hasAuthority('COMMUNITY_WRITE')")
    public CommonResult<CommonIdResult> addNotice(@Parameter(required = true, name = "reqDto", description = "공지 등록 정보") @RequestBody @Valid NoticeReqDto reqDto,
                                                  @Parameter(required = true, description = "servlet") HttpServletRequest request) {
        Long createUserId = Long.parseLong(jwtTokenProvider.getUserPk(request.getHeader("x-api-token")));
        return baseResponse.getContentResult(communityService.addNotice(reqDto, createUserId));
    }

    @Operation(summary = "공지 수정", description = "공지 수정")
    @PutMapping(value = "/notice/{id}")
    @PreAuthorize("hasAuthority('COMMUNITY_WRITE')")
    public CommonResult<CommonIdResult> updateNotice(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id,
                                                     @Parameter(required = true, name = "reqDto", description = "공지 수정 정보") @RequestBody @Valid NoticeReqDto reqDto,
                                                     @Parameter(required = true, description = "servlet") HttpServletRequest request) {
        Long updateUserId = Long.parseLong(jwtTokenProvider.getUserPk(request.getHeader("x-api-token")));
        return baseResponse.getContentResult(communityService.updateNotice(id, reqDto, updateUserId));
    }

    @Operation(summary = "공지 삭제", description = "공지 삭제")
    @DeleteMapping(value = "/notice/{id}")
    @PreAuthorize("hasAuthority('COMMUNITY_WRITE')")
    public CommonResult<CommonIdResult> deleteNotice(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id,
                                                     @Parameter(required = true, description = "servlet") HttpServletRequest request) {
        Long deleteUserId = Long.parseLong(jwtTokenProvider.getUserPk(request.getHeader("x-api-token")));
        return baseResponse.getContentResult(communityService.deleteNotice(id, deleteUserId));
    }

    @Operation(summary = "공지 목록 조회", description = "공지 목록 조회")
    @GetMapping(value = "/notice/list")
    @PreAuthorize("hasAuthority('COMMUNITY_READ')")
    public CommonResult<NoticeListResDto> getList(@Parameter(name = "title", description = "제목") @RequestParam(required = false) String title,
                                                  @PageableDefault(sort="createDate", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable) {
        return baseResponse.getContentResult(communityService.getNoticeList(title, pageable));
    }

    @Operation(summary = "공지 상세 조회", description = "공지 상세 조회")
    @GetMapping(value = "/notice/{id}")
    @PreAuthorize("hasAuthority('COMMUNITY_READ')")
    public CommonResult<NoticeDetailResDto> getDetail(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id) {
        return baseResponse.getContentResult(communityService.getNoticeDetail(id));
    }
}
