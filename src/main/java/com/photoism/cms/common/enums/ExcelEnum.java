package com.photoism.cms.common.enums;

import com.photoism.cms.domain.file.repository.FileEntityRepository;
import com.photoism.cms.domain.user.repository.UserQueryRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExcelEnum {
    XLSX000("파일 리스트 조회(테스트)", FileEntityRepository.class, "findByDivision", String.class, "div"),
    XLSX001("내부직원 계정 조회", UserQueryRepository.class, "exportStaffUserList", String.class, null),
    XLSX002("상점 계정 조회", UserQueryRepository.class, "exportStoreUserList", String.class, null);
    private String desc;
    private Class<?> repoClazz;
    private String queryId;
    private Class<?> reqClazz;
    private String key;
}
