package com.photoism.cms.domain.user;

import com.photoism.cms.common.model.response.BaseResponse;
import com.photoism.cms.common.model.response.CommonIdResult;
import com.photoism.cms.common.model.response.CommonResult;
import com.photoism.cms.domain.user.dto.ChangePasswordReqDto;
import com.photoism.cms.domain.user.dto.UserDetailResDto;
import com.photoism.cms.domain.user.dto.UserListResDto;
import com.photoism.cms.domain.user.dto.UserReqDto;
import io.swagger.v3.oas.annotations.Hidden;
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
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "02. User")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/user")
@Hidden
public class UserController {
    private final UserService userService;
    private final BaseResponse baseResponse;

    @Operation(summary = "회원 가입", description = "사용자 등록")
    @PostMapping(value = "/")
    public CommonResult<CommonIdResult> add(@Parameter(required = true, name = "reqDto", description = "사용자 등록 정보") @RequestBody @Valid UserReqDto reqDto) {
        return baseResponse.getContentResult(userService.add(reqDto));
    }

    @Operation(summary = "사용자 수정", description = "사용자 수정")
    @PutMapping(value = "/{id}")
    public CommonResult<CommonIdResult> updateUser(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id,
                                                   @Parameter(required = true, name = "reqDto", description = "사용자 수정 정보") @RequestBody @Valid UserReqDto reqDto) {
        return baseResponse.getContentResult(userService.updateUser(id, reqDto));
    }

    @Operation(summary = "사용자 제거", description = "사용자 제거")
    @DeleteMapping(value = "/{id}")
    public CommonResult<CommonIdResult> deleteUser(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id) {
        return baseResponse.getContentResult(userService.deleteUser(id));
    }

    @Operation(summary = "사용자 상세 조회", description = "사용자 상세 조회")
    @GetMapping(value = "/{id}")
    public CommonResult<UserDetailResDto> getUser(@Parameter(required = true, name = "id", description = "아이디") @PathVariable Long id) {
        return baseResponse.getContentResult(userService.getUser(id));
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
    public CommonResult<UserListResDto> getStaffUserList(@Parameter(name = "userId", description = "사용자 아이디") @RequestParam(required = false) String userId,
                                                         @Parameter(name = "name", description = "이름") @RequestParam(required = false) String name,
                                                         @Parameter(name = "department", description = "부서") @RequestParam(required = false) String department,
                                                         @Parameter(name = "phone", description = "연락처") @RequestParam(required = false) String phone,
                                                         @Parameter(name = "email", description = "이메일") @RequestParam(required = false) String email,
                                                         @PageableDefault(sort="createDate", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable) {
        return baseResponse.getContentResult(userService.getStaffUserList(userId, name, department, phone, email, pageable));
    }

    @Operation(summary = "사용자 목록 조회(상점)", description = "상점 계정 목록 조회")
    @GetMapping(value = "/list/store")
    public CommonResult<UserListResDto> getStoreUserList(@Parameter(name = "userId", description = "사용자 아이디") @RequestParam(required = false) String userId,
                                                         @Parameter(name = "name", description = "이름") @RequestParam(required = false) String name,
                                                         @Parameter(name = "phone", description = "연락처") @RequestParam(required = false) String phone,
                                                         @Parameter(name = "email", description = "이메일") @RequestParam(required = false) String email,
                                                         @PageableDefault(sort="createDate", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable) {
        return baseResponse.getContentResult(userService.getStoreUserList(userId, name, phone, email, pageable));
    }
}
