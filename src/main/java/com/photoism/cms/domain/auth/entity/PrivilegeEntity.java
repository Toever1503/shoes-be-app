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
@Table( name = "tb_privilege",
        uniqueConstraints = @UniqueConstraint(columnNames = {"role_cd", "privilege_cd"}),
        indexes = @Index(columnList = "role_cd"))
public class PrivilegeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '아이디'")
    private Long id;

    @Column(name = "role_cd", nullable = false, columnDefinition = "NVARCHAR(32) COMMENT 'ROLE 코드'")
    private String roleCd;

    @Column(name = "privilege_cd", nullable = false, columnDefinition = "NVARCHAR(32) COMMENT '권한 코드'")
    private String privilegeCd;
}
