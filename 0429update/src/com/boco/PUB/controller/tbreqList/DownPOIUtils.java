package com.boco.PUB.controller.tbreqList;

import com.boco.SYS.entity.TbReqDetail;
import com.boco.SYS.util.POIExportUtil;
import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import static com.boco.SYS.util.POIExportUtil.setCellWidth;

public class DownPOIUtils {

    /**
     * @param response
     * @param fileName           文件名
     * @param combAmountNameList excel表头名称
     * @param tbReqDetailList    excel数据list
     * @throws Exception
     */
    public static void downPoi(HttpServletRequest request, HttpServletResponse response, String fileName, List<Map<String, String>> combAmountNameList, List<TbReqDetail> tbReqDetailList, List<Map<String, String>> organList) throws Exception {
        OutputStream os = response.getOutputStream();

        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-Type", "application/msexcel");

        response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\"", fileName));
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        try {

            new DownPOIUtils().new POIS().createFixationSheet(os, combAmountNameList, tbReqDetailList, organList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    class POIS {

        public void createFixationSheet(OutputStream os, List<Map<String, String>> combAmountNameList, List<TbReqDetail> tbReqDetailList, List<Map<String, String>> organList) throws Exception {
            //创建工作表
            HSSFWorkbook wb = new HSSFWorkbook();
            //在工作簿上建一张工作表
            HSSFSheet sheet = wb.createSheet();
            HSSFRow row = sheet.createRow((short) 0);
            sheet.createFreezePane(0, 1);
            String[] codeStrArr = new String[combAmountNameList.size() + 1];
            createCell(wb, row, (short) 0, "机构名称");
            for (int k = 0; k < combAmountNameList.size(); k++) {
                Map<String, String> nameMap = combAmountNameList.get(k);
                createCell(wb, row, (short) (k + 1), nameMap.get("name").toString());
                codeStrArr[k + 1] = nameMap.get("code").toString();
            }
            codeStrArr[0] = "organCode";

            for (int i = 1; i < organList.size() + 1; i++) {
                String organCode = organList.get(i - 1).get("organCode");
                HSSFRow rowi = sheet.createRow((short) i);
                for (int e = 0; e < codeStrArr.length; e++) {
//                    createCell(wb, rowi, (short) e, "0");
                    if (e == 0) {
                        createCell(wb, rowi, (short) e, organList.get(i - 1).get("organName"));
                    }
                    //表头code exp:lxdk1_expNum
                    String codeStr = codeStrArr[e];
                    for (TbReqDetail tb : tbReqDetailList) {
                        String tbOrganCode = tb.getReqdOrgan();
                        String combCodeStr = tb.getReqdCombCode();
                        if (tbOrganCode.equals(organCode)) {
                            if ("updateTime".equals(codeStr)) {
                                if (tbOrganCode.equals("TangLoveQianForever")) {
                                    createCell(wb, rowi, (short) e, "-");
                                    break;
                                } else {
                                    createCell(wb, rowi, (short) e, tb.getReqdUpdateTime());
                                    break;
                                }
                            }
                            if ("totalAmount".equals(codeStr)) {
                                createCell(wb, rowi, (short) e, tb.getReqdCreateTime());
                                break;
                            }
                            if (codeStr.startsWith(combCodeStr)) {
                                if (codeStr.endsWith("_expNum")) {
                                    createCell(wb, rowi, (short) e, tb.getReqdExpnum().toString());
                                } else if (codeStr.endsWith("_reqNum")) {
                                    createCell(wb, rowi, (short) e, tb.getReqdReqnum().toString());
                                } else if (codeStr.endsWith("_rate")) {
                                    if (tbOrganCode.equals("TangLoveQianForever")) {
                                        createCell(wb, rowi, (short) e, "-");
                                    } else {
                                        createCell(wb, rowi, (short) e, tb.getReqdRate().toString());
                                    }
                                } else if (codeStr.endsWith("_balance")) {
                                    createCell(wb, rowi, (short) e, tb.getReqdBalance().toString());
                                }
                            }

                            if (codeStr.endsWith("total_expNum")) {
                                createCell(wb, rowi, (short) e, tb.getTotalReqdExpnum().toString());
                            } else if (codeStr.endsWith("total_reqNum")) {
                                createCell(wb, rowi, (short) e, tb.getTotalReqdReqnum().toString());
                            } else if (codeStr.endsWith("total_rate")) {
                                    createCell(wb, rowi, (short) e, "-");
                            } else if (codeStr.endsWith("total_balance")) {
                                createCell(wb, rowi, (short) e, tb.getTotalReqdBalance().toString());
                            }
                        }
                    }
                }
            }

//            setCellWidth();
            //设置列宽
            for (int i = 0; i <combAmountNameList.size()+1 ; i++) {
                POIExportUtil.setCellWidth(sheet,i);
            }


            wb.write(os);
            os.flush();
            os.close();
            System.out.println("over-------------------------------");
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
