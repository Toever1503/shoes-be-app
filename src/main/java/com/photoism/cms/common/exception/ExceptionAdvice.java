package com.photoism.cms.common.exception;

import com.photoism.cms.common.model.response.BaseResponse;
import com.photoism.cms.common.model.response.CommonResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice(basePackages = "com.photoism.cms")
public class ExceptionAdvice {
    private final BaseResponse baseResponse;
    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        log.error("[Internel Exception] ", e);
        return baseResponse.getFailResult(Integer.parseInt(getMessage("unKnown.code")), getMessage("unKnown.message"));
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResult userNotFountException(HttpServletRequest request, Exception e) {
        String message = getMessage("userNotFound.message");
        if (e.getMessage() != null) {
            message = message + "(" + e.getMessage() +")";
        }
        return baseResponse.getFailResult(Integer.parseInt(getMessage("userNotFound.code")), message);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResult objectNotFoundSuccessException(HttpServletRequest request, Exception e) {
        String message = getMessage("objectNotFound.message");
        if (e.getMessage() != null) {
            message = message + "(" + e.getMessage() +")";
        }
        return baseResponse.getFailResult(Integer.parseInt(getMessage("objectNotFound.code")), message);
    }

    @ExceptionHandler(SigninFailedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected CommonResult signInFailed(HttpServletRequest request, SigninFailedException e) {
        return baseResponse.getFailResult(Integer.parseInt(getMessage("signinFailed.code")), getMessage("signinFailed.message"));
    }

    @ExceptionHandler(AuthAccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CommonResult authAccessDeniedException(HttpServletRequest request, AuthAccessDeniedException e) {
        return baseResponse.getFailResult(Integer.parseInt(getMessage("accessDenied.code")), getMessage("accessDenied.message"));
    }

    @ExceptionHandler(AuthEntryPointException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CommonResult authEntryPointException(HttpServletRequest request, AuthEntryPointException e) {
        return baseResponse.getFailResult(Integer.parseInt(getMessage("entryPointException.code")), getMessage("entryPointException.message"));
    }

    @ExceptionHandler(AuthTokenExpiredException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CommonResult authTokenExpiredException(HttpServletRequest request, AuthTokenExpiredException e) {
        return baseResponse.getFailResult(Integer.parseInt(getMessage("tokenExpired.code")), getMessage("tokenExpired.message"));
    }

    @ExceptionHandler(AuthFailedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected CommonResult valifyFailedException(HttpServletRequest request, Exception e) {
        log.error("[AuthFailedException Exception] " + e);
        return baseResponse.getFailResult(Integer.parseInt(getMessage("authfailed.code")), getMessage("authfailed.message"));
    }

    @ExceptionHandler(ProcessFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult processFailedException(HttpServletRequest request, ProcessFailedException e) {
        String message = getMessage("processFailed.message");
        if (e.getMessage() != null) {
            message = message + "(" + e.getMessage() +")";
        }
        return baseResponse.getFailResult(Integer.parseInt(getMessage("processFailed.code")), message);
    }

    @ExceptionHandler(ObjectAlreadExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult objectAlreadExistException(HttpServletRequest request, Exception e) {
        String message = getMessage("objectAlreadyExist.message");
        if (e.getMessage() != null) {
            message = message + "(" + e.getMessage() +")";
        }
        return baseResponse.getFailResult(Integer.parseInt(getMessage("objectAlreadyExist.code")), message);
    }

    // code정보에 해당하는 메시지를 조회
    private String getMessage(String code) {
        return getMessage(code, null);
    }

    // code정보, 추가 argument로 현재 locale에 맞는 메시지를 조회
    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
