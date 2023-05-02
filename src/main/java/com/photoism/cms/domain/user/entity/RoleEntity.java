package com.photoism.cms.domain.user.entity;

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
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role"}),
        indexes = @Index(columnList = "user_id"))
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '아이디'")
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "BIGINT COMMENT '사용자 아이디'")
    private UserEntity user;

    @Column(name = "role", nullable = false, columnDefinition = "NVARCHAR(32) COMMENT '권한 코드'")
    private String role;
}
