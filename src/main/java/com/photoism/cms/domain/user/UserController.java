package com.photoism.cms.domain.user;

import com.photoism.cms.common.model.response.BaseResponse;
import com.photoism.cms.common.model.response.CommonIdResult;
import com.photoism.cms.common.model.response.CommonResult;
import com.photoism.cms.domain.user.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "02. User")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/user")
public class UserController {
    private final UserService userService;
    private final BaseResponse baseResponse;

    @Operation(summary = "회원 가입", description = "사용자 등록")
    @PostMapping(value = "/")
    public CommonResult<CommonIdResult> addUser(@Parameter(required = true, name = "reqDto", description = "사용자 등록 정보") @RequestBody @Valid UserReqDto reqDto) {
        return baseResponse.getContentResult(userService.addUser(reqDto));
    }

    @Operation(summary = "사용자 승인", description = "사용자 승인")
    @PatchMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ACCOUNT_WRITE')")
    public CommonResult<CommonIdResult> approval(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id) {
        return baseResponse.getContentResult(userService.approval(id));
    }

    @Operation(summary = "사용자 수정", description = "사용자 수정")
    @PutMapping(value = "/{id}")
    public CommonResult<CommonIdResult> updateUser(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id,
                                                   @Parameter(required = true, name = "reqDto", description = "사용자 수정 정보") @RequestBody @Valid UserUpdateReqDto reqDto) {
        return baseResponse.getContentResult(userService.updateUser(id, reqDto));
    }

    @Operation(summary = "사용자 제거", description = "사용자 제거")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ACCOUNT_WRITE')")
    public CommonResult<CommonIdResult> deleteUser(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id) {
        return baseResponse.getContentResult(userService.deleteUser(id));
    }

    @Operation(summary = "사용자 상세 조회", description = "사용자 상세 조회")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ACCOUNT_READ')")
    public CommonResult<UserDetailResDto> getDetail(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id) {
        return baseResponse.getContentResult(userService.getDetail(id));
    }

    @Operation(summary = "아이디 찾기", description = "아이디 찾기")
    @GetMapping(value = "/find-id")
    public CommonResult<List<String>> findId(@Parameter(required = true, name = "name", description = "이름") @RequestParam String name,
                                             @Parameter(required = true, name = "phone", description = "연락처") @RequestParam String phone) {
        return baseResponse.getContentResult(userService.findId(name, phone));
    }

    @Operation(summary = "패스워드 변경", description = "패스워드 변경")
    @PatchMapping(value = "/{id}/password")
    public CommonResult<CommonIdResult> changePassword(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id,
                                                       @Parameter(required = true, name = "reqDto", description = "비밀번호 변경 요청 정보") @RequestBody @Valid ChangePasswordReqDto reqDto) {
        return baseResponse.getContentResult(userService.changePassword(id, reqDto));
    }

    @Operation(summary = "패스워드 초기화", description = "패스워드 초기화")
    @PatchMapping(value = "/resetPassword/{id}")
    public CommonResult<CommonIdResult> resetPassword(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id) {
        return baseResponse.getContentResult(userService.resetPassword(id));
    }

    @Operation(summary = "사용자 목록 조회(직원)", description = "직원 계정 목록 조회")
    @GetMapping(value = "/list/staff")
    @PreAuthorize("hasAuthority('ACCOUNT_READ')")
    public CommonResult<UserListResDto> getStaffUserList(@Parameter(name = "userId", description = "사용자 아이디") @RequestParam(required = false) String userId,
                                                         @Parameter(name = "name", description = "이름") @RequestParam(required = false) String name,
                                                         @Parameter(name = "role", description = "ROLE") @RequestParam(required = false) String roleCd,
                                                         @Parameter(name = "phone", description = "연락처") @RequestParam(required = false) String phone,
                                                         @Parameter(name = "email", description = "이메일") @RequestParam(required = false) String email,
                                                         @Parameter(name = "approved", description = "승인여부") @RequestParam(required = false) Boolean approved,
                                                         @PageableDefault(sort="createDate", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable) {
        return baseResponse.getContentResult(userService.getStaffUserList(userId, name, roleCd, phone, email, approved, pageable));
    }

    @Operation(summary = "사용자 목록 조회(상점)", description = "상점 계정 목록 조회")
    @GetMapping(value = "/list/store")
    @PreAuthorize("hasAuthority('ACCOUNT_READ')")
    public CommonResult<UserListResDto> getStoreUserList(@Parameter(name = "userId", description = "사용자 아이디") @RequestParam(required = false) String userId,
                                                         @Parameter(name = "name", description = "이름") @RequestParam(required = false) String name,
                                                         @Parameter(name = "phone", description = "연락처") @RequestParam(required = false) String phone,
                                                         @Parameter(name = "email", description = "이메일") @RequestParam(required = false) String email,
                                                         @Parameter(name = "approved", description = "승인여부") @RequestParam(required = false) Boolean approved,
                                                         @PageableDefault(sort="createDate", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable) {
        return baseResponse.getContentResult(userService.getStoreUserList(userId, name, phone, email, approved, pageable));
    }

    @Operation(summary = "사용자 검색(상점관리)", description = "사용자 검색(상점관리)")
    @GetMapping(value = "/find")
    @PreAuthorize("hasAuthority('STORE_WRITE')")
    public CommonResult<List<UserResDto>> getUserForStoreMapping(@Parameter(name = "userId", description = "사용자 아이디") @RequestParam(required = false) String userId,
                                                                 @Parameter(name = "name", description = "이름") @RequestParam(required = false) String name) {
        return baseResponse.getContentResult(userService.getUserForStoreMapping(userId, name));
    }
}
