package com.photoism.cms.domain.store.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "상점 조회 응답 정보")
@Data
@AllArgsConstructor
public class StoreResDto {
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

    @Schema(description = "생성일자")
    private LocalDateTime createDate;
}
