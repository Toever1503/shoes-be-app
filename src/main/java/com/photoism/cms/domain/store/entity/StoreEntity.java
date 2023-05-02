package com.photoism.cms.domain.store.entity;

import com.photoism.cms.common.model.BaseDateEntity;
import com.photoism.cms.domain.store.dto.StoreReqDto;
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
@Table(name = "tb_store")
public class StoreEntity extends BaseDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT COMMENT '아이디'")
    private Long id;

    @Column(name = "brand", nullable = false, columnDefinition = "NVARCHAR(16) COMMENT '브랜드 코드'")
    private String brand;

    @Column(name = "store_type", nullable = false, columnDefinition = "NVARCHAR(3) COMMENT '상점 형태 코드'")
	private String storeType;

	@Column(name = "country", nullable = false, columnDefinition = "NVARCHAR(2) COMMENT '국가 코드'")
	private String country;

	@Column(name = "city", columnDefinition = "NVARCHAR(2) COMMENT '도시 코드'")
	private String city;

	@Column(name = "name", nullable = false, columnDefinition = "NVARCHAR(64) COMMENT '상점명'")
    private String name;

	@Column(name = "contractor", columnDefinition = "NVARCHAR(32) COMMENT '계약자'")
	private String contractor;

	@Column(name = "contract_period", columnDefinition = "NVARCHAR(128) COMMENT '계약기간'")
	private String contractPeriod;

	@Column(name = "open_date", columnDefinition = "DATE COMMENT '오픈일자'")
	private LocalDate openDate;

	@Column(name = "transfer_date", columnDefinition = "DATE COMMENT '양도양수일자'")
	private LocalDate transferDate;

	@Column(name = "closure_date", columnDefinition = "DATE COMMENT '폐점일자'")
	private LocalDate closureDate;

	@Column(name = "booth_count", columnDefinition = "INT(2) COMMENT '부스 수'")
	private Integer boothCount;

	@Column(name = "booth_type", columnDefinition = "NVARCHAR(32) COMMENT '부스 형태'")
	private String boothType;

	@Column(name = "biz_reg_no", columnDefinition = "NVARCHAR(16) COMMENT '사업자등록번호'")
	private String bizRegNo;

	@Column(name = "representative", columnDefinition = "NVARCHAR(32) COMMENT '대표자명'")
	private String representative;

	@Column(name = "phone", columnDefinition = "NVARCHAR(30) COMMENT '연락처'")
	private String phone;

	@Column(name = "email", columnDefinition = "NVARCHAR(64) COMMENT '이메일'")
	private String email;

	@Column(name = "internet", columnDefinition = "NVARCHAR(16) COMMENT '인터넷'")
	private String internet;

	@Column(name = "revenue_share", columnDefinition = "NVARCHAR(16) COMMENT '수익배분'")
	private String revenueShare;

	@Column(name = "hq_royalty", columnDefinition = "NVARCHAR(8) COMMENT '본사로열티'")
	private String hqRoyalty;

	@Column(name = "om_cost", columnDefinition = "NVARCHAR(8) COMMENT '운영관리비'")
	private String omCost;

	@Column(name = "address", columnDefinition = "NVARCHAR(128) COMMENT '주소'")
    private String address;

	@Column(name = "printer", columnDefinition = "NVARCHAR(32) COMMENT '프린터'")
	private String printer;

	@Column(name = "signage_yn", columnDefinition = "BOOLEAN DEFAULT FALSE COMMENT '사이니지 사용여부'")
	private Boolean signageYn;

	@Column(name = "note", columnDefinition = "NVARCHAR(1024) COMMENT '특이사항(비고)'")
	private String note;

	@Column(name = "net_area", columnDefinition = "NVARCHAR(1024) COMMENT '실면적'")
	private String netArea;

	@Column(name = "sd_km_mr", columnDefinition = "NVARCHAR(128) COMMENT '보증금/권리금/월세'")
	private String sdKmMr;

	@Column(name = "bdc", columnDefinition = "NVARCHAR(128) COMMENT '상권 특성'")
	private String bdc;

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

	@Column(name = "owner", columnDefinition = "BIGINT COMMENT '소유자'")
	private Long owner;

	public void update(StoreReqDto reqDto, Long updateUserId) {
		if (reqDto.getBrand() != null)			this.brand = reqDto.getBrand();
		if (reqDto.getStoreType() != null)		this.storeType = reqDto.getStoreType();
		if (reqDto.getCountry() != null)		this.country = reqDto.getCountry();
		if (reqDto.getCity() != null)			this.city = reqDto.getCity();
		if (reqDto.getName() != null)			this.name = reqDto.getName();
		if (reqDto.getContractor() != null)		this.contractor = reqDto.getContractor();
		if (reqDto.getContractPeriod() != null)	this.contractPeriod = reqDto.getContractPeriod();
		if (reqDto.getOpenDate() != null)		this.openDate = reqDto.getOpenDate();
		if (reqDto.getTransferDate() != null)	this.transferDate = reqDto.getTransferDate();
		if (reqDto.getClosureDate() != null)	this.closureDate = reqDto.getClosureDate();
		if (reqDto.getBoothCount() != null)		this.boothCount = reqDto.getBoothCount();
		if (reqDto.getBoothType() != null)		this.boothType = reqDto.getBoothType();
		if (reqDto.getBizRegNo() != null)		this.bizRegNo = reqDto.getBizRegNo();
		if (reqDto.getRepresentative() != null)	this.representative = reqDto.getRepresentative();
		if (reqDto.getPhone() != null)			this.phone = reqDto.getPhone();
		if (reqDto.getEmail() != null)			this.email = reqDto.getEmail();
		if (reqDto.getInternet() != null)		this.internet = reqDto.getInternet();
		if (reqDto.getRevenueShare() != null)	this.revenueShare = reqDto.getRevenueShare();
		if (reqDto.getHqRoyalty() != null)		this.hqRoyalty = reqDto.getHqRoyalty();
		if (reqDto.getOmCost() != null)			this.omCost = reqDto.getOmCost();
		if (reqDto.getAddress() != null)		this.address = reqDto.getAddress();
		if (reqDto.getPrinter() != null)		this.printer = reqDto.getPrinter();
		if (reqDto.getSignageYn() != null)		this.signageYn = reqDto.getSignageYn();
		if (reqDto.getNote() != null)			this.note = reqDto.getNote();
		if (reqDto.getNetArea() != null)		this.netArea = reqDto.getNetArea();
		if (reqDto.getSdKmMr() != null)			this.sdKmMr = reqDto.getSdKmMr();
		if (reqDto.getBdc() != null)			this.bdc = reqDto.getBdc();
		this.updateUser = updateUserId;
	}

	public void setDel(Long deleteUserId) {
		this.del = true;
		this.deleteDate = LocalDateTime.now();
		this.deleteUser = deleteUserId;
	}

	public void setOwner(Long userId) {
		this.owner = userId;
	}
}
