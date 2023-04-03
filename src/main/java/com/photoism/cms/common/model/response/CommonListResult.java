package com.photoism.cms.common.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class CommonListResult<T> extends CommonResult {
    private List<T> content;
}
