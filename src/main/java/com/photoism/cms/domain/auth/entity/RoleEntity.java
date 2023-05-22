package com.photoism.cms.domain.auth.entity;

import com.photoism.cms.domain.user.entity.UserEntity;
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
@Table( name = "tb_role",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_cd"}),
        indexes = @Index(columnList = "user_id"))
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '아이디'")
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "BIGINT COMMENT '사용자 아이디'")
    private UserEntity user;

    @Column(name = "role_cd", nullable = false, columnDefinition = "NVARCHAR(32) COMMENT 'ROLE 코드'")
    private String roleCd;
}
