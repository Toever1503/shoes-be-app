package com.photoism.cms.common.enums;

import com.photoism.cms.domain.file.repository.FileEntityRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExcelEnum {
    XLSX000("파일 리스트 조회(테스트)", FileEntityRepository.class, "findByDivision", String.class, "div");
    private String desc;
    private Class<?> repoClazz;
    private String queryId;
    private Class<?> reqClazz;
    private String key;
}
