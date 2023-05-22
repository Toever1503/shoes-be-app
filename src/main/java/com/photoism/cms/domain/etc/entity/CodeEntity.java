package com.photoism.cms.domain.etc.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "tb_code")
public class CodeEntity {
    @Id
    @Column(name = "code", columnDefinition = "NVARCHAR(32) COMMENT '코드 값'")
    private String code;

    @Column(name = "code_group", columnDefinition = "NVARCHAR(16) COMMENT '코드 그룹'")
    private String codeGroup;

    @Column(name = "name_kr", columnDefinition = "NVARCHAR(64) COMMENT '코드명(한글)'")
    private String nameKr;

    @Column(name = "name_en", columnDefinition = "NVARCHAR(64) COMMENT '코드명(영문)'")
    private String nameEn;

    @Column(name = "position", columnDefinition = "INT(2) COMMENT '정렬순서'")
    private String position;

    @Column(name = "hidden", columnDefinition = "BOOLEAN DEFAULT FALSE COMMENT '숨김여부'")
    private Boolean hidden;
}
