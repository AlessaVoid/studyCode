package com.boco.PUB.controller.reqReset;

import com.boco.SYS.entity.TbReqDetail;
import com.boco.SYS.entity.TbReqresetDetail;
import com.boco.SYS.util.POIExportUtil;
import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 需求调整-下载机构信息excel
 */
public class DownResetPOIUtils {

    /**
     * @param response
     * @param fileName           文件名
     * @param combAmountNameList excel表头名称
     * @param tbReqDetailList    excel数据list
     * @throws Exception
     */
    public static void downPoi(HttpServletRequest request, HttpServletResponse response, String fileName, List<Map<String, String>> combAmountNameList, List<TbReqresetDetail> tbReqDetailList, List<Map<String, String>> organList) throws Exception {
        OutputStream os = response.getOutputStream();

        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-Type", "application/msexcel");

        response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\"", fileName));
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        try {

            new DownResetPOIUtils().new POIS().createFixationSheet(fileName, os, combAmountNameList, tbReqDetailList, organList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    class POIS {

        public void createFixationSheet(String fileName, OutputStream os, List<Map<String, String>> combAmountNameList, List<TbReqresetDetail> tbReqDetailList, List<Map<String, String>> organList) throws Exception {

            //创建工作表
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet();

            //居中样式
            HSSFCellStyle cellStyle = wb.createCellStyle();
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
            cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

            //机构
            HashMap<String, Integer> organMap = new HashMap<>();
            for (int i = 0; i < organList.size(); i++) {
                Map<String, String> map = organList.get(i);
                organMap.put(map.get("organCode").toString(), 4 + i);
            }
            //贷种
            HashMap<String, Integer> combMap = new HashMap<>();
            for (int i = 0; i < combAmountNameList.size(); i++) {
                Map<String, String> map = combAmountNameList.get(i);
                combMap.put(map.get("code").toString(), 1 + i);
            }

            /*写入文件*/
            //表头
            POIExportUtil.CellRangeAddress(sheet, 0, 0, 0, combAmountNameList.size() * 4);
            POIExportUtil.setCell(sheet, 0, 0, fileName, cellStyle);
            //单位
            POIExportUtil.setCell(sheet, 1, 0, "单位:", null);
            if(tbReqDetailList==null||tbReqDetailList.size()==0){
                POIExportUtil.setCell(sheet, 1, 1, "未知", cellStyle);

            }else {

                POIExportUtil.setCell(sheet, 1, 1, tbReqDetailList.get(0).getReqdresetUnit() == 1 ? "万元" : "亿元", cellStyle);
            }

            //机构
            POIExportUtil.CellRangeAddress(sheet, 2, 3, 0, 0);
            POIExportUtil.setCell(sheet, 2, 0, "机构", cellStyle);
            POIExportUtil.CellRangeAddress(sheet, 2, 3, 1, 1);
            POIExportUtil.setCell(sheet, 2, 1, "上报时间", cellStyle);
            for (Map<String, String> map : organList) {
                Integer row = organMap.get(map.get("organCode"));
                if (row != null) {
                    POIExportUtil.setCell(sheet, row, 0, map.get("organName").toString(), cellStyle);
                }
            }
            //贷种
            for (Map<String, String> map : combAmountNameList) {
                Integer column = combMap.get(map.get("code"));
                if (column != null) {
                    POIExportUtil.CellRangeAddress(sheet, 2, 2, (column - 1) * 3 + 1 + 1, (column - 1) * 3 + 3 + 1);
                    POIExportUtil.setCell(sheet, 2, (column - 1) * 3 + 1 + 1, map.get("name").toString(), cellStyle);
                    POIExportUtil.setCell(sheet, 3, (column - 1) * 3 + 1 + 1, "原计划", cellStyle);
                    POIExportUtil.setCell(sheet, 3, (column - 1) * 3 + 2 + 1, "调整量", cellStyle);
                    POIExportUtil.setCell(sheet, 3, (column - 1) * 3 + 3 + 1, "调整后计划", cellStyle);
                }
            }
            //数据 分项
            for (TbReqresetDetail tb : tbReqDetailList) {
                Integer row = organMap.get(tb.getReqdresetOrgan());
                Integer column = combMap.get(tb.getReqdresetCombCode());
                if (row != null && column != null) {
                    POIExportUtil.setCell(sheet, row, (column - 1) * 3 + 1 + 1, tb.getOldPlan(), cellStyle);
                    POIExportUtil.setCell(sheet, row, (column - 1) * 3 + 2 + 1, tb.getReqdresetNum(), cellStyle);
                    POIExportUtil.setCell(sheet, row, (column - 1) * 3 + 3 + 1, tb.getNewPlan(), cellStyle);
                }
            }
            //数据 合计
            for (TbReqresetDetail tb : tbReqDetailList) {
                Integer row = organMap.get(tb.getReqdresetOrgan());
                Integer column = combMap.get("total");
                if (row != null && column != null) {
                    POIExportUtil.setCell(sheet, row, (column - 1) * 3 + 1 + 1, tb.getTotalOldPlan(), cellStyle);
                    POIExportUtil.setCell(sheet, row, (column - 1) * 3 + 2 + 1, tb.getTotalNum(), cellStyle);
                    POIExportUtil.setCell(sheet, row, (column - 1) * 3 + 3 + 1, tb.getTotalNewPlan(), cellStyle);
                }
            }
            //上报时间
            for (TbReqresetDetail tb : tbReqDetailList) {
                Integer row = organMap.get(tb.getReqdresetOrgan());
                if (row != null) {
                    POIExportUtil.setCell(sheet, row, 1, tb.getReqdresetUpdatetime(), cellStyle);
                }
            }
            //设置列宽
            for (int i = 0; i < combAmountNameList.size() * 3 + 1; i++) {
                POIExportUtil.setCellWidth(sheet, i);
            }
            //冻结行列
            POIExportUtil.createFreezePane(sheet, 1, 4);
            //设置列宽
            for (int i = 0; i < combAmountNameList.size() + 1; i++) {
                POIExportUtil.setCellWidth(sheet, i);
            }
            wb.write(os);
            os.flush();
            os.close();
        }


        private void createCell(HSSFWorkbook wb, HSSFRow row, short col, String val) {
            HSSFCell cell = row.createCell(col);
            cell.setCellValue(val);
            HSSFCellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
            cell.setCellStyle(cellStyle);
        }
    }


}
