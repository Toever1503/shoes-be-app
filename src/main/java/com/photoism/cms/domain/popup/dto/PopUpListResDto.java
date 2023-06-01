package com.photoism.cms.domain.popup.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "팝업 리스트 조회 응답 정보")
@Data
public class PopUpListResDto {
    @Schema(description = "팝업 리스트")
    private List<PopUpResDto> popUpList = new ArrayList<>();

    @Schema(description = "전체 레코드 수")
    private Long totalElements;

    @Schema(description = "마지막 페이지 여부")
    private boolean last;

    @Schema(description = "전체 페이지 수")
    private Integer totalPages;

    @Schema(description = "현재 페이지의 레코드 수")
    private Integer numberOfElements;

    public PopUpListResDto(Page<PopUpResDto> resDtoPage) {
        for (PopUpResDto popUpResDto : resDtoPage) {
            this.popUpList.add(popUpResDto);
        }
        this.totalElements = resDtoPage.getTotalElements();
        this.last = resDtoPage.isLast();
        this.numberOfElements = resDtoPage.getNumberOfElements();
        this.totalPages = resDtoPage.getTotalPages();
    }
}
