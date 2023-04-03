package com.photoism.cms.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FileDivision {
    tmp(0, "Temporary files", "Storage where files are stored by default.");

    private Integer id;
    private String title;
    private String desc;
}
