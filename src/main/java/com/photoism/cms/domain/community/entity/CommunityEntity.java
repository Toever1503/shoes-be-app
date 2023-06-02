package com.photoism.cms.domain.community.entity;

import com.photoism.cms.common.model.BaseDateEntity;
import com.photoism.cms.domain.community.dto.CommunityReqDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "tb_community")
public class CommunityEntity extends BaseDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '아이디'")
    private Long id;

    @Column(name = "division", nullable = false, columnDefinition = "NVARCHAR(16) COMMENT '구분'")
    private String div;

    @Column(name = "title", nullable = false, columnDefinition = "NVARCHAR(128) COMMENT '제목'")
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT COMMENT '내용'")
    private String content;

    @Column(name = "read_count", nullable = false, columnDefinition = "INT(11) DEFAULT 0 COMMENT '조회수'")
    private Integer readCount;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CommunityFileEntity> files = new ArrayList<>();

    @Column(name = "del", columnDefinition = "BOOLEAN DEFAULT FALSE COMMENT '삭제여부'")
    private Boolean del;

    @Column(name = "delete_date", columnDefinition = "DATETIME(3) COMMENT '삭제일'")
    private LocalDateTime deleteDate;

    @Column(name = "create_user", nullable = false, columnDefinition = "BIGINT COMMENT '최초생성유저'")
    private Long createUser;

    @Column(name = "update_user", nullable = false, columnDefinition = "BIGINT COMMENT '최종수정유저'")
    private Long updateUser;

    @Column(name = "delete_user", columnDefinition = "BIGINT COMMENT '삭제유저'")
    private Long deleteUser;

    public void update(CommunityReqDto reqDto, Long updateUserId) {
        if (reqDto.getTitle() != null)      this.title = reqDto.getTitle();
        if (reqDto.getContent() != null)    this.content = reqDto.getContent();
        this.updateUser = updateUserId;
    }

    public void setDel(Long deleteUserId) {
        this.del = true;
        this.deleteDate = LocalDateTime.now();
        this.deleteUser = deleteUserId;
    }
}
