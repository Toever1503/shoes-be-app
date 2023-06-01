package com.photoism.cms.domain.popup.entity;

import com.photoism.cms.common.model.BaseDateEntity;
import com.photoism.cms.domain.popup.dto.PopUpReqDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "tb_popup")
public class PopUpEntity  extends BaseDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '아이디'")
    private Long id;

    @Column(name = "title", nullable = false, columnDefinition = "NVARCHAR(64) COMMENT '팝업 제목'")
    private String title;

    @Column(name = "isShow", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE COMMENT '노출여부'")
    private Boolean isShow;

    @Column(name = "ignoreTime", nullable = false, columnDefinition = "INT(2) COMMENT '다시보지않기 시간'")
    private Integer ignoreTime;

    @Column(name = "start_date", nullable = false, columnDefinition = "DATE COMMENT '시작일자'")
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false, columnDefinition = "DATE COMMENT '종료일자'")
    private LocalDate endDate;

    @Column(name = "layer_left", nullable = false, columnDefinition = "INT(4) COMMENT '팝업 레이어 좌측 위치'")
    private Integer layerLeft;

    @Column(name = "layer_top", nullable = false, columnDefinition = "INT(4) COMMENT '팝업 레이어 상단 위치'")
    private Integer layerTop;

    @Column(name = "layer_width", nullable = false, columnDefinition = "INT(4) COMMENT '팝업 레이어 넓이'")
    private Integer layerWidth;

    @Column(name = "layer_height", nullable = false, columnDefinition = "INT(4) COMMENT '팝업 레이어 높이'")
    private Integer layerHeight;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT COMMENT '내용'")
    private String content;

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

    public void update(PopUpReqDto reqDto, Long updateUserId) {
        if (reqDto.getTitle() != null)          this.title = reqDto.getTitle();
        if (reqDto.getIgnoreTime() != null)     this.ignoreTime = reqDto.getIgnoreTime();
        if (reqDto.getStartDate() != null)      this.startDate = reqDto.getStartDate();
        if (reqDto.getEndDate() != null)        this.endDate = reqDto.getEndDate();
        if (reqDto.getLayerLeft() != null)      this.layerLeft = reqDto.getLayerLeft();
        if (reqDto.getLayerTop() != null)       this.layerTop = reqDto.getLayerTop();
        if (reqDto.getLayerWidth() != null)     this.layerWidth = reqDto.getLayerWidth();
        if (reqDto.getLayerHeight() != null)    this.layerHeight = reqDto.getLayerHeight();
        if (reqDto.getContent() != null)        this.content = reqDto.getContent();
        if (reqDto.getIsShow() != null)         this.isShow = reqDto.getIsShow();
        this.updateUser = updateUserId;
    }

    public void setDel(Long deleteUserId) {
        this.del = true;
        this.deleteDate = LocalDateTime.now();
        this.deleteUser = deleteUserId;
    }
}
