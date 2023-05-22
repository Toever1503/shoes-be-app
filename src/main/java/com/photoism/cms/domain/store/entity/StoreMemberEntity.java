package com.photoism.cms.domain.store.entity;

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
@Table( name = "tb_store_member",
        indexes = @Index(columnList = "store_id"))
public class StoreMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '아이디'")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false, columnDefinition = "BIGINT COMMENT '상점 아이디'")
    private StoreEntity store;

    @Column(name = "user_id", nullable = false, columnDefinition = "BIGINT COMMENT '사용자 아이디'")
    private Long userId;

    @Column(name = "role", nullable = false, columnDefinition = "NVARCHAR(8) COMMENT '역할'")
    private String role;

    public void setStore(StoreEntity store) {
        this.store = store;
    }
}
