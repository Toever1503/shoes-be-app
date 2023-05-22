package com.photoism.cms.domain.admin;

import com.photoism.cms.common.model.response.BaseResponse;
import com.photoism.cms.common.model.response.CommonBaseResult;
import com.photoism.cms.common.model.response.CommonResult;
import com.photoism.cms.domain.admin.dto.PrivilegeReqDto;
import com.photoism.cms.domain.admin.dto.RoleResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "09. Admin")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/admin")
public class AdminController {
    private final BaseResponse baseResponse;
    private final AdminService adminService;

    @Operation(summary = "권한 조회", description = "권한 조회")
    @GetMapping(value = "/role")
    public CommonResult<List<RoleResDto>> getRoles() {
        return baseResponse.getContentResult(adminService.getRoles());
    }

    @Operation(summary = "권한 수정", description = "권한 수정")
    @PostMapping(value = "/role")
    public CommonBaseResult updateRoles(@Parameter(required = true, name = "reqDtoList", description = "권한 수정 정보") @RequestBody @Valid List<PrivilegeReqDto> reqDtoList) {
        adminService.updateRoles(reqDtoList);
        return baseResponse.getSuccessResult();
    }
}
