package com.photoism.cms.domain.kiosk.entity;

import com.photoism.cms.common.model.BaseDateEntity;
import com.photoism.cms.domain.kiosk.dto.KioskReqDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table( name = "tb_kiosk",
        indexes = @Index(columnList = "store_id"))
public class KioskEntity extends BaseDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '아이디'")
    private Long id;

    @Column(name = "store_id", columnDefinition = "BIGINT COMMENT '상점 아이디'")
    private Long storeId;

    @Column(name = "device_no", nullable = false, columnDefinition = "NVARCHAR(15) COMMENT '기기 번호'")
    private String deviceNo;

    @Column(name = "device_id", nullable = false, columnDefinition = "NVARCHAR(15) COMMENT '기기 아이디'")
    private String deviceId;

    @Column(name = "license", columnDefinition = "NVARCHAR(128) COMMENT '라이센스'")
    private String license;

    @Column(name = "anydesk", columnDefinition = "NVARCHAR(15) COMMENT '애니데스크'")
    private String anydesk;

    @Column(name = "camera", nullable = false, columnDefinition = "NVARCHAR(16) COMMENT '카메라'")
    private String camera;

    @Column(name = "camera_serial", nullable = false, columnDefinition = "NVARCHAR(16) COMMENT '카메라 시리얼'")
    private String cameraSerial;

    @Column(name = "camera_lens_zoom", nullable = false, columnDefinition = "NVARCHAR(3) COMMENT '카메라 렌즈 줌'")
    private String cameraLensZoom;

    @Column(name = "camera_iso", nullable = false, columnDefinition = "NVARCHAR(8) COMMENT '카메라 ISO'")
    private String cameraISO;

    @Column(name = "camera_shutter_speed", nullable = false, columnDefinition = "NVARCHAR(8) COMMENT '카메라 셔터 속도'")
    private String cameraShutterSpeed;

    @Column(name = "camera_aperture", nullable = false, columnDefinition = "NVARCHAR(8) COMMENT '카메라 조리개'")
    private String cameraAperture;

    @Column(name = "camera_color_temp", nullable = false, columnDefinition = "NVARCHAR(8) COMMENT '카메라 색온도'")
    private String cameraColorTemp;

    @Column(name = "camera_wb_cal", nullable = false, columnDefinition = "NVARCHAR(4) COMMENT '카메라 WB보정'")
    private String cameraWbCal;

    @Column(name = "strobe_intensity", nullable = false, columnDefinition = "NVARCHAR(8) COMMENT '스트로브 세기'")
    private String strobeIntensity;

    @Column(name = "printer", nullable = false, columnDefinition = "NVARCHAR(16) COMMENT '프린터'")
    private String printer;

    @Column(name = "printerCnt", nullable = false, columnDefinition = "INT(2) COMMENT '프린터 수'")
    private Integer printerCnt;

    @Column(name = "monitor", nullable = false, columnDefinition = "NVARCHAR(16) COMMENT '모니터'")
    private String monitor;

    @Column(name = "pc", nullable = false, columnDefinition = "NVARCHAR(16) COMMENT 'PC'")
    private String pc;

    @Column(name = "io_board", nullable = false, columnDefinition = "NVARCHAR(16) COMMENT 'I/O보드'")
    private String ioBoard;

    @Column(name = "internet", columnDefinition = "NVARCHAR(16) COMMENT '인터넷'")
    private String internet;

    @Column(name = "internet_serial", columnDefinition = "NVARCHAR(16) COMMENT '인터넷 시리얼'")
    private String internetSerial;

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

    public void update(KioskReqDto reqDto, Long updateUserId) {
        if (reqDto.getStoreId() != null)            this.storeId = reqDto.getStoreId();
        if (reqDto.getDeviceNo() != null)           this.deviceNo = reqDto.getDeviceNo();
        if (reqDto.getDeviceId() != null)           this.deviceId = reqDto.getDeviceId();
        if (reqDto.getAnydesk() != null)            this.anydesk = reqDto.getAnydesk();
        if (reqDto.getCamera() != null)             this.camera = reqDto.getCamera();
        if (reqDto.getCameraSerial() != null)       this.cameraSerial = reqDto.getCameraSerial();
        if (reqDto.getCameraLensZoom() != null)     this.cameraLensZoom = reqDto.getCameraLensZoom();
        if (reqDto.getCameraISO() != null)          this.cameraISO = reqDto.getCameraISO();
        if (reqDto.getCameraShutterSpeed() != null) this.cameraShutterSpeed = reqDto.getCameraShutterSpeed();
        if (reqDto.getCameraAperture() != null)     this.cameraAperture = reqDto.getCameraAperture();
        if (reqDto.getCameraColorTemp() != null)    this.cameraColorTemp = reqDto.getCameraColorTemp();
        if (reqDto.getCameraWbCal() != null)        this.cameraWbCal = reqDto.getCameraWbCal();
        if (reqDto.getStrobeIntensity() != null)    this.strobeIntensity = reqDto.getStrobeIntensity();
        if (reqDto.getPrinter() != null)            this.printer = reqDto.getPrinter();
        if (reqDto.getPrinterCnt() != null)         this.printerCnt = reqDto.getPrinterCnt();
        if (reqDto.getMonitor() != null)            this.monitor = reqDto.getMonitor();
        if (reqDto.getPc() != null)                 this.pc = reqDto.getPc();
        if (reqDto.getIoBoard() != null)            this.ioBoard = reqDto.getIoBoard();
        if (reqDto.getInternet() != null)           this.internet = reqDto.getInternet();
        if (reqDto.getInternetSerial() != null)     this.internetSerial = reqDto.getInternetSerial();
        this.updateUser = updateUserId;
    }

    public void setDel(Long deleteUserId) {
        this.del = true;
        this.deleteDate = LocalDateTime.now();
        this.deleteUser = deleteUserId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
}
