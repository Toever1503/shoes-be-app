package com.photoism.cms.common.model.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BaseResponse {
    @Autowired
    private MessageSource messageSource;

    public <T> CommonSingleResult<T> getSingleResult(T data) {
        CommonSingleResult<T> result = new CommonSingleResult<>();
        result.setContent(data);
        result.setCode(0);
        result.setMessage("success");
        return result;
    }

    public <T> CommonListResult<T> getListResult(List<T> list) {
        CommonListResult<T> result = new CommonListResult<>();
        result.setContent(list);
        result.setCode(0);
        result.setMessage("success");
        return result;
    }

    public CommonResult getSuccessResult() {
        CommonResult result = new CommonResult();
        result.setCode(0);
        result.setMessage("success");
        return result;
    }

    public CommonResult getFailResult(int code, String message) {
        CommonResult result = new CommonResult();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public String getMessage(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }
}
