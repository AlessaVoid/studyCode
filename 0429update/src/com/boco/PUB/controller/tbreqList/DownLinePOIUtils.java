package com.boco.PUB.controller.tbreqList;

import com.boco.SYS.entity.TbLineReqDetailDTO;
import com.boco.SYS.entity.TbReqDetail;
import com.boco.SYS.entity.TbReqList;
import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class DownLinePOIUtils {

    /**
     * @param response
     * @param fileName           �ļ���
     * @param combAmountNameList excel��ͷ����
     * @param tbReqDetailList    excel����list
     * @throws Exception
     */
    public static void downPoi(HttpServletRequest request, HttpServletResponse response, String fileName, List<Map<String, String>> combAmountNameList, List<TbLineReqDetailDTO> tbReqDetailList, List<Map<String, String>> combList) throws Exception {
        OutputStream os = response.getOutputStream();

        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-Type", "application/msexcel");

        response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\"", fileName));
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        try {

            new DownLinePOIUtils().new POIS().createFixationSheet(os, combAmountNameList, tbReqDetailList, combList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    class POIS {

        public void createFixationSheet(OutputStream os, List<Map<String, String>> combAmountNameList, List<TbLineReqDetailDTO> tbReqDetailList, List<Map<String, String>> combList) throws Exception {
            //����������
            HSSFWorkbook wb = new HSSFWorkbook();
            //�ڹ������Ͻ�һ�Ź�����
            HSSFSheet sheet = wb.createSheet();
            HSSFRow row = sheet.createRow((short) 0);
            sheet.createFreezePane(0, 1);
            String[] codeStrArr = new String[combAmountNameList.size() + 1];
            createCell(wb, row, (short) 0, "�����������");
            for (int k = 0; k < combAmountNameList.size(); k++) {
                Map<String, String> nameMap = combAmountNameList.get(k);
                createCell(wb, row, (short) (k + 1), nameMap.get("name").toString());
                codeStrArr[k + 1] = nameMap.get("code").toString();
            }
            codeStrArr[0] = "organCode";

            for (int i = 1; i < combList.size() + 1; i++) {
                String combCode = combList.get(i - 1).get("combCode");
                HSSFRow rowi = sheet.createRow((short) i);
                for (int e = 0; e < codeStrArr.length; e++) {
//                    createCell(wb, rowi, (short) e, "0");
                    if (e == 0) {
                        createCell(wb, rowi, (short) e, combList.get(i - 1).get("combName"));
                    }
                    //��ͷcode exp:lxdk1_expNum
                    String codeStr = codeStrArr[e];
                    for (TbLineReqDetailDTO tb : tbReqDetailList) {
                        String tbCombCode = tb.getLineCombCode();
                        if (tbCombCode.equals(combCode)) {
                            if (codeStr.endsWith("expNum")) {
                                if(tb.getLineExpnum()==null){
                                    createCell(wb, rowi, (short) e, "-");
                                }else {
                                    createCell(wb, rowi, (short) e, tb.getLineExpnum().toString());
                                }
                            } else if (codeStr.endsWith("reqNum")) {
                                if(tb.getLineReqnum()==null){
                                    createCell(wb, rowi, (short) e, "-");
                                }else {
                                    createCell(wb, rowi, (short) e, tb.getLineReqnum().toString());
                                }

                            } else if (codeStr.endsWith("rate")) {
                                if(tb.getLineRate()==null){
                                    createCell(wb, rowi, (short) e, "-");
                                }else {
                                    createCell(wb, rowi, (short) e, tb.getLineRate().toString());
                                }
                            } else if (codeStr.endsWith("balance")) {
                                if(tb.getLineBalance()==null){
                                    createCell(wb, rowi, (short) e, "-");
                                }else {
                                    createCell(wb, rowi, (short) e, tb.getLineBalance().toString());
                                }

                            }
                        }
                    }
                }
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
