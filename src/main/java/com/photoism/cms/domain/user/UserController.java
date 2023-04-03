package com.photoism.cms.domain.user;

import com.photoism.cms.common.enums.Role;
import com.photoism.cms.common.model.response.BaseResponse;
import com.photoism.cms.common.model.response.CommonResult;
import com.photoism.cms.domain.user.dto.AddAdminReqDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "02. User")
@RequiredArgsConstructor
@RequestMapping(value = "/v1/user")
public class UserController {
    private final UserService userService;
    private final BaseResponse baseResponse;

    @Operation(summary = "사용자 등록(admin)", description = "사용자 등록(admin)")
    @PostMapping(value = "/admin")
    public CommonResult addAdmin(@Parameter(required = true, name = "role", description = "사용자 권한") @RequestParam Role role,
                                 @Parameter(required = true, name = "reqDto", description = "사용자 등록 정보") @RequestBody @Valid AddAdminReqDto reqDto) {
        return baseResponse.getSingleResult(userService.add(role, reqDto));
    }
}
