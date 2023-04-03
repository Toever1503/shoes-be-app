//package com.photoism.cms.domain.user.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "tb_role")
//public class RoleEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(columnDefinition = "BIGINT COMMENT '권한 아이디'")
//    private Long id;
//
//    @Column(columnDefinition = "NVARCHAR(32) COMMENT '권한 이름'")
//    private String name;
//
//    @OneToMany(mappedBy = "role")
//    private final List<UserRoleEntity> users = new ArrayList<>();
//}
