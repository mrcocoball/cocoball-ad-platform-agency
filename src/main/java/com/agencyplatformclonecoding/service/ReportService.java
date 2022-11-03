package com.agencyplatformclonecoding.service;

import com.agencyplatformclonecoding.domain.constrant.ReportType;
import com.agencyplatformclonecoding.dto.PerformanceStatisticsDto;
import com.agencyplatformclonecoding.repository.StatisticsQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ReportService {

    private final StatisticsQueryRepository statisticsQueryRepository;
    private static final String[] PERFORMANCE_HEADER = {"날짜", "소재 ID", "키워드명", "입찰가", "노출수", "클릭수", "전환수", "구매액", "소진액", "클릭률", "전환률", "CPA", "ROAS"};
    private static final String[] CREATIVE_HEADER = {"날짜", "소재 ID", "키워드명", "입찰가", "노출수", "클릭수", "전환수", "구매액", "소진액", "클릭률", "전환률", "CPA", "ROAS"};
    private static final String[] CAMPAIGN_HEADER = {"날짜", "캠페인 ID", "캠페인명", "예산", "노출수", "클릭수", "전환수", "구매액", "소진액", "클릭률", "전환률", "CPA", "ROAS"};
    private static final String[] CLIENT_HEADER_P = {"날짜", "광고주 ID", "광고주명", "업종", "노출수", "클릭수", "전환수", "구매액", "소진액", "클릭률", "전환률", "CPA", "ROAS"};
    private static final String[] CLIENT_HEADER_S = {"날짜", "광고주 ID", "광고주명", "업종", "에이전트 ID", "소진액"};


    // 실적 보고서, 소재 실적 보고서
    @Transactional(readOnly = true)
    public Object getPerformanceStatistics(HttpServletResponse response, Long id, LocalDate startDate, LocalDate lastDate, ReportType reportType) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);
        LocalDate startDateBeforeThirtyDays = defaultLastDate.minusDays(30);


        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeThirtyDays;
        }

        if (reportType == ReportType.PERFORMANCE) {
            List<PerformanceStatisticsDto> performanceList = statisticsQueryRepository.findByCreative_IdAndStatisticsDefault(id, startDate, lastDate);
            createPerformanceExcelReportResponse(response, performanceList, reportType);
            return null;
        }

        if (reportType == ReportType.CREATIVE) {
            List<PerformanceStatisticsDto> performanceList = statisticsQueryRepository.findByCampaign_IdAndStatisticsDefault(id, startDate, lastDate);
            createPerformanceExcelReportResponse(response, performanceList, reportType);
            return null;
        }

        return null;
    }


    // 캠페인 실적 보고서
    @Transactional(readOnly = true)
    public Object getPerformanceStatistics(HttpServletResponse response, String id, LocalDate startDate, LocalDate lastDate, ReportType reportType) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);
        LocalDate startDateBeforeThirtyDays = defaultLastDate.minusDays(30);


        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeThirtyDays;
        }

        if (reportType == ReportType.CAMPAIGN) {
            List<PerformanceStatisticsDto> performanceList = statisticsQueryRepository.findByClientUser_IdAndStatisticsDefault(id, startDate, lastDate);
            createPerformanceExcelReportResponse(response, performanceList, reportType);
        }

        return null;
    }


    // 광고주 실적 보고서
    @Transactional(readOnly = true)
    public Object getPerformanceStatistics(HttpServletResponse response, LocalDate startDate, LocalDate lastDate, ReportType reportType) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);
        LocalDate startDateBeforeThirtyDays = defaultLastDate.minusDays(30);


        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeThirtyDays;
        }

        if (reportType == ReportType.CLIENT) {
            List<PerformanceStatisticsDto> performanceList = statisticsQueryRepository.findClientUserAndPerformanceStatisticsDefault(startDate, lastDate);
            createPerformanceExcelReportResponse(response, performanceList, reportType);
        }

        return null;
    }


    // 광고주 소진액 보고서
    @Transactional(readOnly = true)
    public Object getSpendStatistics(HttpServletResponse response, LocalDate startDate, LocalDate lastDate, ReportType reportType) {

        LocalDate defaultLastDate = LocalDate.parse(LocalDate.now().minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate startDateBeforeSevenDays = defaultLastDate.minusDays(6);
        LocalDate startDateBeforeThirtyDays = defaultLastDate.minusDays(30);


        if (lastDate == null) {
            lastDate = defaultLastDate;
        }

        if (startDate == null) {
            startDate = startDateBeforeThirtyDays;
        }

        if (reportType == ReportType.CLIENT) {
            List<PerformanceStatisticsDto> performanceList = statisticsQueryRepository.findClientUserAndSpendStatisticsDefault(startDate, lastDate);
            createSpendExcelReportResponse(response, performanceList, reportType);
        }

        return null;
    }


    // 실적 보고서 파일 생성
    private void createPerformanceExcelReportResponse(HttpServletResponse response, List<PerformanceStatisticsDto> performanceList, ReportType reportType) {

        try {
            Workbook workbook = new SXSSFWorkbook();
            Sheet sheet = workbook.createSheet(setFileName(reportType) + "_실적_보고서"); // 동적

            // 숫자 포맷 적용
            CellStyle numberCellStyle = workbook.createCellStyle();
            numberCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
            CellStyle percentCellStyle = workbook.createCellStyle();
            percentCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));

            LocalDate reportDate = LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            final String fileName = setFileName(reportType) + "_실적_보고서_" + reportDate; // 동적

            // column width 설정, 1글자당 256
            sheet.setColumnWidth(0, 24 * 256); // 기간
            sheet.setColumnWidth(1, 10 * 256); // ID
            sheet.setColumnWidth(2, 20 * 256); // 이름
            sheet.setColumnWidth(3, 12 * 256); // 서브
            sheet.setColumnWidth(7, 12 * 256); // 구매액
            sheet.setColumnWidth(8, 12 * 256); // 소진액
            sheet.setColumnWidth(12, 11 * 256); // ROAS

            // header
            final String[] header = setHeaderP(reportType);
            Row row = sheet.createRow(0);
            for (int i = 0; i < header.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(header[i]);
            }

            // body
            for (int i = 0; i < performanceList.size(); i++) {
                row = sheet.createRow(i + 1);

                PerformanceStatisticsDto performance = performanceList.get(i);

                Cell cell = null;

                cell = row.createCell(0); // 기간 동적
                setDate(cell, performance);

                cell = row.createCell(1); // 동적 (광고주 / 캠페인 / 소재 ID)
                setIdValue(cell, performance, reportType);

                cell = row.createCell(2); // 동적 (광고주명 / 캠페인명 / 소재 키워드)
                setNameValue(cell, performance, reportType);

                cell = row.createCell(3); // 동적 (광고주 업종 / 예산 / 입찰가)
                cell.setCellStyle(numberCellStyle);
                setSubValue(cell, performance, reportType);

                cell = row.createCell(4); // 노출수
                cell.setCellStyle(numberCellStyle);
                cell.setCellValue(performance.getView());

                cell = row.createCell(5); // 클릭수
                cell.setCellStyle(numberCellStyle);
                cell.setCellValue(performance.getClick());

                cell = row.createCell(6); // 전환수
                cell.setCellStyle(numberCellStyle);
                cell.setCellValue(performance.getConversion());

                cell = row.createCell(7); // 구매액
                cell.setCellStyle(numberCellStyle);
                cell.setCellValue(performance.getPurchase());

                cell = row.createCell(8); // 소진액
                cell.setCellStyle(numberCellStyle);
                cell.setCellValue(performance.getSpend());

                cell = row.createCell(9); // 클릭률
                cell.setCellStyle(percentCellStyle);
                cell.setCellValue(performance.getCTR());

                cell = row.createCell(10); // 전환률
                cell.setCellStyle(percentCellStyle);
                cell.setCellValue(performance.getCVR());

                cell = row.createCell(11); // CPA
                cell.setCellStyle(numberCellStyle);
                cell.setCellValue(performance.getCPA());

                cell = row.createCell(12); // ROAS
                cell.setCellStyle(percentCellStyle);
                cell.setCellValue(performance.getROAS());
            }

            response.setContentType("application/vdn.ms-excel");
            response.setHeader("Content-Disposition", "attatchment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");

            workbook.write(response.getOutputStream());
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    // 소진액 보고서 파일 생성
    private void createSpendExcelReportResponse(HttpServletResponse response, List<PerformanceStatisticsDto> performanceList, ReportType reportType) {

        try {
            Workbook workbook = new SXSSFWorkbook();
            Sheet sheet = workbook.createSheet(setFileName(reportType) + "_소진액_보고서"); // 동적

            // 숫자 포맷 적용
            CellStyle numberCellStyle = workbook.createCellStyle();
            numberCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));

            LocalDate reportDate = LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            final String fileName = setFileName(reportType) + "_소진액_보고서_" + reportDate; // 동적

            // column width 설정, 1글자당 256
            sheet.setColumnWidth(0, 24 * 256); // 기간
            sheet.setColumnWidth(1, 10 * 256); // ID
            sheet.setColumnWidth(2, 20 * 256); // 이름
            sheet.setColumnWidth(3, 20 * 256); // 서브
            sheet.setColumnWidth(4, 12 * 256); // 에이전트명
            sheet.setColumnWidth(5, 12 * 256); // 구매액

            // header
            final String[] header = setHeaderS(reportType);
            Row row = sheet.createRow(0);
            for (int i = 0; i < header.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(header[i]);
            }

            // body
            for (int i = 0; i < performanceList.size(); i++) {
                row = sheet.createRow(i + 1);

                PerformanceStatisticsDto performance = performanceList.get(i);

                Cell cell = null;

                cell = row.createCell(0); // 기간
                setDate(cell, performance);

                cell = row.createCell(1); // 동적 (광고주 / 캠페인 / 소재 ID)
                setIdValue(cell, performance, reportType);

                cell = row.createCell(2); // 동적 (광고주명 / 캠페인명 / 소재 키워드)
                setNameValue(cell, performance, reportType);

                cell = row.createCell(3); // 동적 (광고주 업종 / 예산 / 입찰가)
                cell.setCellStyle(numberCellStyle);
                setSubValue(cell, performance, reportType);

                cell = row.createCell(4); // 에이전트 ID
                cell.setCellValue(performance.getAgentId());

                cell = row.createCell(5); // 소진액
                cell.setCellStyle(numberCellStyle);
                cell.setCellValue(performance.getSpend());

            }

            response.setContentType("application/vdn.ms-excel");
            response.setHeader("Content-Disposition", "attatchment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");

            workbook.write(response.getOutputStream());
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    private String setFileName(ReportType reportType) {

        return switch (reportType) {
            case PERFORMANCE -> "상세";
            case CREATIVE -> "소재";
            case CAMPAIGN -> "캠페인";
            case CLIENT -> "광고주";
        };
    }

    private String[] setHeaderP(ReportType reportType) {

        return switch (reportType) {
            case PERFORMANCE -> PERFORMANCE_HEADER;
            case CREATIVE -> CREATIVE_HEADER;
            case CAMPAIGN -> CAMPAIGN_HEADER;
            case CLIENT -> CLIENT_HEADER_P;
        };
    }

    private String[] setHeaderS(ReportType reportType) {

        return switch (reportType) {
            case PERFORMANCE -> PERFORMANCE_HEADER;
            case CREATIVE -> CREATIVE_HEADER;
            case CAMPAIGN -> CAMPAIGN_HEADER;
            case CLIENT -> CLIENT_HEADER_S;
        };
    }

    private void setIdValue(Cell cell, PerformanceStatisticsDto psd, ReportType reportType) {

        switch (reportType) {
            case PERFORMANCE -> cell.setCellValue(psd.getCreativeId());
            case CREATIVE -> cell.setCellValue(psd.getCreativeId());
            case CAMPAIGN -> cell.setCellValue(psd.getCampaignId());
            case CLIENT -> cell.setCellValue(psd.getClientId());
        }
        ;
    }

    private void setNameValue(Cell cell, PerformanceStatisticsDto psd, ReportType reportType) {

        switch (reportType) {
            case PERFORMANCE -> cell.setCellValue(psd.getKeyword());
            case CREATIVE -> cell.setCellValue(psd.getKeyword());
            case CAMPAIGN -> cell.setCellValue(psd.getName());
            case CLIENT -> cell.setCellValue(psd.getUsername());
        }
        ;
    }

    private void setSubValue(Cell cell, PerformanceStatisticsDto psd, ReportType reportType) {

        switch (reportType) {
            case PERFORMANCE -> cell.setCellValue(psd.getBidingPrice());
            case CREATIVE -> cell.setCellValue(psd.getBidingPrice());
            case CAMPAIGN -> cell.setCellValue(psd.getBudget());
            case CLIENT -> cell.setCellValue(psd.getCategory());
        }
        ;
    }

    private void setDate(Cell cell, PerformanceStatisticsDto psd) {

        if (psd.getLastDate() == null) {
            cell.setCellValue(psd.getStartDate() + "");
        } else {
            cell.setCellValue(psd.getStartDate() + " - " + psd.getLastDate());
        }
    }

}