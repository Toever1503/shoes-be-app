package com.photoism.cms.common.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CommonSingleResult<T> extends CommonResult {
    private T content;
}
