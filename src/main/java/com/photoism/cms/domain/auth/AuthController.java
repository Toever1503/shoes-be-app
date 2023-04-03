package com.photoism.cms.domain.auth;

import com.photoism.cms.common.exception.AuthAccessDeniedException;
import com.photoism.cms.common.exception.AuthEntryPointException;
import com.photoism.cms.common.exception.AuthTokenExpiredException;
import com.photoism.cms.common.model.response.BaseResponse;
import com.photoism.cms.common.model.response.CommonResult;
import com.photoism.cms.domain.auth.dto.SignInReqDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "01. Auth")
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class AuthController {
    private final AuthService authService;
    private final BaseResponse baseResponse;

    @Operation(hidden = true)
    @GetMapping(value = "/exception/entrypoint")
    public CommonResult entryPointException(){
        throw new AuthEntryPointException();
    }

    @Operation(hidden = true)
    @GetMapping(value = "/exception/accessdenied")
    public CommonResult accessDeniedException(){
        throw new AuthAccessDeniedException();
    }

    @Operation(hidden = true)
    @GetMapping(value = "/exception/tokenexpired")
    public CommonResult tokenExpired(){
        throw new AuthTokenExpiredException();
    }

    @Operation(summary = "로그인", description = "로그인")
    @PostMapping(value = "/auth/sign-in")
    public CommonResult signIn(@Parameter(required = true, name = "reqDto", description = "사용자 로그인 정보") @RequestBody @Valid SignInReqDto reqDto) {
        return baseResponse.getSingleResult(authService.signIn(reqDto));
    }
}
