package com.photoism.cms.domain.etc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photoism.cms.common.enums.CodeGroupEnum;
import com.photoism.cms.common.enums.ExcelEnum;
import com.photoism.cms.domain.etc.dto.CodeResDto;
import com.photoism.cms.domain.etc.dto.ExcelCellInfoReqDto;
import com.photoism.cms.domain.etc.dto.ExcelDownloadReqDto;
import com.photoism.cms.domain.etc.entity.CodeEntity;
import com.photoism.cms.domain.etc.repository.CodeRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommonService {
    private final CodeRepository codeRepository;
    private final ObjectMapper ob;
    private final DefaultListableBeanFactory df;

    @Transactional(readOnly = true)
    public List<CodeResDto> getCode(CodeGroupEnum codeGroup) {
        List<CodeEntity> codeEntityList = codeRepository.findByCodeGroupAndHiddenOrderByPositionAsc(codeGroup.name(), false);
        List<CodeResDto> resDtoList = new ArrayList<>();
        for (CodeEntity codeEntity : codeEntityList) {
            resDtoList.add(new CodeResDto(codeEntity));
        }
        return resDtoList;
    }

    @Transactional(readOnly = true)
    public void excelDownload(HttpServletResponse response, ExcelDownloadReqDto reqDto) {
        SXSSFWorkbook workbook = null;
        OutputStream outputStream = null;

        // Excel Enum 조회
        ExcelEnum excelEnum = reqDto.getExcelEnumId();

        try {
            workbook = new SXSSFWorkbook();
            List<ExcelCellInfoReqDto> arrExcelCellInfo = reqDto.getExcelCellInfo();

            // 시트 명칭 설정
            SXSSFSheet sheet = workbook.createSheet(StringUtils.defaultString(reqDto.getSheetName(), "Sheet1"));

            // Excel Head 설정
            this.generateHead(workbook, sheet, arrExcelCellInfo);

            // 쿼리 파라미터 맵 매핑된 VO 전환
            Object reqValue;
            Map<String, Object> reqSql = Optional.ofNullable(reqDto.getReqSql()).orElse(new HashMap<>());
            if (StringUtils.equals("String", excelEnum.getReqClazz().getSimpleName())) {
                reqValue = reqSql.getOrDefault(excelEnum.getKey(), "");
            } else {
                reqValue = ob.convertValue(reqSql, excelEnum.getReqClazz());
            }

            // 엑셀 조회 데이터 매핑
            Method queryMethod = ReflectionUtils.findMethod(excelEnum.getRepoClazz(), excelEnum.getQueryId(), reqValue.getClass());

            // 엑셀 생성 데이터 조회
            List<?> data = (List<?>) ReflectionUtils.invokeMethod(Objects.requireNonNull(queryMethod), df.getBean(excelEnum.getRepoClazz()), reqValue);

            // 엑셀 컬럼 생성
            for (int rowNum = 0; rowNum < Objects.requireNonNull(data).size(); rowNum++) {
                Row dataRow = sheet.createRow(rowNum + 1);
                @SuppressWarnings("unchecked")
                Map<String, Object> rowData = ob.convertValue(data.get(rowNum), Map.class);

                for (int iColIndex = 0; iColIndex < arrExcelCellInfo.size(); iColIndex++) {
                    // 컬럼 설정 정보 가져오기
                    String sColumnId = arrExcelCellInfo.get(iColIndex).getColumnId();

                    // 생성할 컬럼 데이터 가져오기
                    String oValue = String.valueOf(rowData.getOrDefault(sColumnId, ""));

                    // 조회된 값 타입에 맞는 값으로 치환 필요
                    dataRow.createCell(iColIndex).setCellValue(StringUtils.replace(oValue, "null", ""));
                }
            }
            // 디스크 flush 임시파일
            sheet.flushRows(data.size());

            outputStream = response.getOutputStream();

            response.setHeader("Content-Disposition", "attachment;filename=\"" + reqDto.getExlFileNm() + "\";");
            response.setHeader("Content-Transfer-Encoding", "binary");
            workbook.write(outputStream);

        } catch (IOException e) {
            log.error(e.toString());
        } finally {
            try {
                if (null != outputStream) outputStream.close();
                if (null != workbook) {
                    workbook.close();
                    workbook.dispose();
                }
                response.getOutputStream().flush();
                response.getOutputStream().close();
            } catch (IOException e) {
                log.error(e.toString());
            }
        }
    }

    private void generateHead(Workbook workbook, Sheet sheet, List<ExcelCellInfoReqDto> arrExcelCellInfo) {
        Row row = sheet.createRow(0);
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        for(int iColIndex = 0 ; iColIndex < arrExcelCellInfo.size() ; iColIndex++) {
            // 생성할 컬럼 값 가져오기
            String oValue = arrExcelCellInfo.get(iColIndex).getHeaderDesc();

            // 컬럼 생성
            Cell cell = row.createCell(iColIndex);
            cell.setCellValue(oValue);
            cell.setCellStyle(headerCellStyle);
        }
    }
}
