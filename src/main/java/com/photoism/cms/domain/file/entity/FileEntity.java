package com.photoism.cms.domain.file.entity;

import com.photoism.cms.common.model.BaseDateEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "tb_file")
public class FileEntity extends BaseDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '파일아이디'")
    private Long id;

    @Column(name = "division", nullable = false, columnDefinition = "NVARCHAR(3) COMMENT '파일 구분(tmp: 임시 파일)'")
    private String division;

    @Column(name = "name", nullable = false, columnDefinition = "NVARCHAR(128) COMMENT '원본명'")
    private String name;

    @Column(name = "alter_name", nullable = false, columnDefinition = "NVARCHAR(128) COMMENT '부여명'")
    private String alterName;

    @Column(name = "path", nullable = false, columnDefinition = "NVARCHAR(1024) COMMENT '파일 경로'")
    private String path;

    @Column(name = "del", columnDefinition = "BOOLEAN DEFAULT FALSE COMMENT '삭제여부'")
    private Boolean del;

    @Column(name = "delete_date", columnDefinition = "DATETIME(3) COMMENT '삭제일'")
    private LocalDateTime deleteDate;

    public void setDel() {
        this.del = true;
        deleteDate = LocalDateTime.now();
    }

}
