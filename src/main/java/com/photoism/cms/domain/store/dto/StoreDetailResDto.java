package com.photoism.cms.domain.store.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "상점 상세 조회 응답 정보")
@Data
@AllArgsConstructor
public class StoreDetailResDto {
    @Schema(description = "아이디", example = "1")
    private Long id;

    @Schema(description = "브랜드 코드", example = "STUDIO")
    private String brandCd;

    @Schema(description = "브랜드 명칭(한글)", example = "스튜디오")
    private String brandNmKr;

    @Schema(description = "브랜드 명칭(영문)", example = "Studio")
    private String brandNmEn;

    @Schema(description = "상점 형태 코드", example = "DMS")
    private String storeTypeCd;

    @Schema(description = "상점 형태 명칭(한글)", example = "직영점")
    private String storeTypeNmKr;

    @Schema(description = "상점 형태 명칭(영문)", example = "Directly managed store")
    private String storeTypeNmEn;

    @Schema(description = "국가 코드", example = "KR")
    private String countryCd;

    @Schema(description = "국가 명칭(한글)", example = "한국")
    private String countryNmKr;

    @Schema(description = "국가 명칭(영문)", example = "Korea")
    private String countryNmEn;

    @Schema(description = "도시 코드", example = "11")
    private String cityCd;

    @Schema(description = "도시 명칭(한글)", example = "서울특별시")
    private String cityNmKr;

    @Schema(description = "도시 명칭(영문)", example = "Seoul")
    private String cityNmEn;

    @Schema(description = "상점명", example = "테스트 서울점")
    private String name;

    @Schema(description = "계약자", example = "홍길동")
    private String contractor;

    @Schema(description = "계약기간", example = "2023.05.01 ~ 2022.06.30")
    private String contractPeriod;

    @Schema(description = "오픈일자")
    private LocalDate openDate;

    @Schema(description = "양도양수일자")
    private LocalDate transferDate;

    @Schema(description = "폐점일자")
    private LocalDate closureDate;

    @Schema(description = "부스 수", example = "2")
    private Integer boothCount;

    @Schema(description = "부스 형태", example = "내부형")
    private String boothType;

    @Schema(description = "사업자등록번호", example = "012-345-67890")
    private String bizRegNo;

    @Schema(description = "대표자명", example = "홍길동")
    private String representative;

    @Schema(description = "연락처", example = "010-1234-5678")
    private String phone;

    @Schema(description = "이메일", example = "example@google.com")
    private String email;

    @Schema(description = "인터넷", example = "랜선")
    private String internet;

    @Schema(description = "수익배분", example = "7:3")
    private String revenueShare;

    @Schema(description = "본사로열티", example = "3.3%")
    private String hqRoyalty;

    @Schema(description = "운영관리비", example = "6.6%")
    private String omCost;

    @Schema(description = "주소", example = "서울특별시 OO구 OO길 OO, 1층")
    private String address;

    @Schema(description = "프린터", example = "xp15010 / 후지필름")
    private String printer;

    @Schema(description = "사이니지 사용여부")
    private Boolean signageYn;

    @Schema(description = "특이사항(비고)", example = "2023/06/01 명의 변경 : 김이박 -> 홍길동")
    private String note;

    @Schema(description = "실면적", example = "33.3m2")
    private String netArea;

    @Schema(description = "보증금/권리금/월세", example = """
                                                        권리금 45,000,000
                                                        보증금 40,000,000
                                                        월 임대료 3,000,000 (부가세별도)""")
    private String sdKmMr;

    @Schema(description = "상권 특성", example = "text")
    private String bdc;

    @Schema(description = "생성일자")
    private LocalDateTime createDate;
}
