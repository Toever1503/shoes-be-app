package com.photoism.cms.domain.kiosk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "장비 리스트 조회 응답 정보")
@Data
public class KioskListResDto {
    @Schema(description = "장비 리스트")
    private List<KioskResDto> userList = new ArrayList<>();

    @Schema(description = "전체 레코드 수")
    private Long totalElements;

    @Schema(description = "마지막 페이지 여부")
    private boolean last;

    @Schema(description = "전체 페이지 수")
    private Integer totalPages;

    @Schema(description = "현재 페이지의 레코드 수")
    private Integer numberOfElements;

    public KioskListResDto(Page<KioskResDto> resDtoPage) {
        for (KioskResDto kioskResDto : resDtoPage) {
            this.userList.add(kioskResDto);
        }
        this.totalElements = resDtoPage.getTotalElements();
        this.last = resDtoPage.isLast();
        this.numberOfElements = resDtoPage.getNumberOfElements();
        this.totalPages = resDtoPage.getTotalPages();
    }
}
