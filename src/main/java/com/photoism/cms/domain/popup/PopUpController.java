package com.photoism.cms.domain.popup;

import com.photoism.cms.common.model.response.BaseResponse;
import com.photoism.cms.common.model.response.CommonIdResult;
import com.photoism.cms.common.model.response.CommonResult;
import com.photoism.cms.common.security.JwtTokenProvider;
import com.photoism.cms.domain.popup.dto.PopUpDetailResDto;
import com.photoism.cms.domain.popup.dto.PopUpListResDto;
import com.photoism.cms.domain.popup.dto.PopUpReqDto;
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
@Tag(name = "08. Pop-up")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/pop-up")
public class PopUpController {
    private final PopUpService popUpService;
    private final BaseResponse baseResponse;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "팝업 등록", description = "팝업 등록")
    @PostMapping(value = "/")
    @PreAuthorize("hasAuthority('POPUP_WRITE')")
    public CommonResult<CommonIdResult> add(@Parameter(required = true, name = "reqDto", description = "팝업 등록 정보") @RequestBody @Valid PopUpReqDto reqDto,
                                            @Parameter(required = true, description = "servlet") HttpServletRequest request) {
        Long createUserId = Long.parseLong(jwtTokenProvider.getUserPk(request.getHeader("x-api-token")));
        return baseResponse.getContentResult(popUpService.add(reqDto, createUserId));
    }

    @Operation(summary = "팝업 수정", description = "팝업 수정")
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('POPUP_WRITE')")
    public CommonResult<CommonIdResult> update(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id,
                                               @Parameter(required = true, name = "reqDto", description = "팝업 수정 정보") @RequestBody @Valid PopUpReqDto reqDto,
                                               @Parameter(required = true, description = "servlet") HttpServletRequest request) {
        Long updateUserId = Long.parseLong(jwtTokenProvider.getUserPk(request.getHeader("x-api-token")));
        return baseResponse.getContentResult(popUpService.update(id, reqDto, updateUserId));
    }

    @Operation(summary = "팝업 삭제", description = "팝업 삭제")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('POPUP_WRITE')")
    public CommonResult<CommonIdResult> delete(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id,
                                               @Parameter(required = true, description = "servlet") HttpServletRequest request) {
        Long deleteUserId = Long.parseLong(jwtTokenProvider.getUserPk(request.getHeader("x-api-token")));
        return baseResponse.getContentResult(popUpService.delete(id, deleteUserId));
    }

    @Operation(summary = "팝업 목록 조회", description = "팝업 목록 조회")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('POPUP_READ')")
    public CommonResult<PopUpListResDto> getList(@Parameter(name = "isShow", description = "노출여부") @RequestParam(required = false) Boolean isShow,
                                                 @PageableDefault(sort="createDate", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable) {
        return baseResponse.getContentResult(popUpService.getList(isShow, pageable));
    }

    @Operation(summary = "팝업 상세 조회", description = "팝업 상세 조회")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('POPUP_READ')")
    public CommonResult<PopUpDetailResDto> getDetail(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id) {
        return baseResponse.getContentResult(popUpService.getDetail(id));
    }

    @Operation(summary = "팝업 불러오기", description = "팝업 불러오기")
    @GetMapping(value = "/")
    @PreAuthorize("isAuthenticated()")
    public CommonResult<List<PopUpDetailResDto>> get() {
        return baseResponse.getContentResult(popUpService.get());
    }
}
