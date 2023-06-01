package com.photoism.cms.domain.community.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "tb_notice_file",
        indexes = @Index(columnList = "notice_id"))
public class NoticeFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '아이디'")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id", nullable = false, columnDefinition = "BIGINT COMMENT '공지 아이디'")
    private NoticeEntity notice;

    @Column(name = "file_id", columnDefinition = "BIGINT COMMENT '파일 아이디'")
    private Long fileId;

    public void setNotice(NoticeEntity noticeEntity) {
        if (this.notice != null)
            this.notice.getFiles().remove(this);
        this.notice = noticeEntity;
        noticeEntity.getFiles().add(this);
    }
}
