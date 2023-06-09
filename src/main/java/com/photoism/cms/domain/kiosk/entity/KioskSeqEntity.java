package com.photoism.cms.domain.kiosk.entity;

import com.photoism.cms.common.model.BaseDateEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "tb_kiosk_seq",
        indexes = @Index(columnList = "device_id"))
public class KioskSeqEntity extends BaseDateEntity {
    @Id
    @Column(name = "uid", nullable = false, columnDefinition = "NVARCHAR(64) COMMENT 'UID'")
    private String uid;

    @Column(name = "device_id", nullable = false, columnDefinition = "NVARCHAR(15) COMMENT '기기 아이디'")
    private String deviceId;

    @Column(name = "frame_info", nullable = false, columnDefinition = "NVARCHAR(64) COMMENT '프레임 정보'")
    private String frameInfo;

    @Column(name = "filter", columnDefinition = "NVARCHAR(8) COMMENT '필터'")
    private String filter;

    @Column(name = "ad_count", columnDefinition = "BIGINT COMMENT '광고 번호'")
    private Integer adCount;

    @Column(name = "payment_means", columnDefinition = "NVARCHAR(8) COMMENT '결제 수단'")
    private String paymentMeans;

    @Column(name = "breakage_income", columnDefinition = "INT(11) COMMENT '낙전 금액 '")
    private Integer breakageIncome;

    @Column(name = "payed_price", columnDefinition = "INT(11) COMMENT '지불된 금액'")
    private Integer payedPrice;

    @Column(name = "service_coin", columnDefinition = "INT(2) COMMENT '서비스 코인 횟수'")
    private Integer serviceCoin;

    @Column(name = "payment_dt", columnDefinition = "DATETIME(3) COMMENT '결제일시'")
    private LocalDateTime paymentDt;
}
