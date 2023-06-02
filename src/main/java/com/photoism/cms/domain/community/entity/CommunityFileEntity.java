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
@Table( name = "tb_community_file",
        indexes = @Index(columnList = "community_id"))
public class CommunityFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '아이디'")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", nullable = false, columnDefinition = "BIGINT COMMENT '게시물 아이디'")
    private CommunityEntity community;

    @Column(name = "file_id", columnDefinition = "BIGINT COMMENT '파일 아이디'")
    private Long fileId;

    public void setCommunity(CommunityEntity communityEntity) {
        if (this.community != null)
            this.community.getFiles().remove(this);
        this.community = communityEntity;
        communityEntity.getFiles().add(this);
    }
}
