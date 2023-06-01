package com.photoism.cms.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FileDivision {
    tmp(0, "Temporary files", "Storage where files are stored by default."),
    notice(1, "Notice Files", "Storage where notice files are stored."),
    popup(2, "Pop-up Files", "Storage where notice files are stored.");

    private Integer id;
    private String title;
    private String desc;
}
