package com.boco.SYS.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.math.BigDecimal;

/**
 * POI ��������
 */
public class POIExportUtil {

    //���ᴰ��(sheet,��������������������
    public static void createFreezePane(Sheet sheet,int column, int row) {
        sheet.createFreezePane(column, row, column, row);
    }

    // �ϲ���Ԫ�� s CellRangeAddress(sheet,��ʼ�к�, �����к�, ��ʼ�к�, �����к�)
    public static void CellRangeAddress(Sheet sheet,int rowBegin, int rowEnd, int columnBegin, int columnEnd) {
        sheet.addMergedRegion(new CellRangeAddress(rowBegin, rowEnd, columnBegin, columnEnd));
    }

    //���趨�ĵ�Ԫ��������� (sheet,�кţ��кţ�����,��ʽ����null)
    public static void setCell(Sheet sheet,int row,int column,String data,CellStyle cellStyle)throws Exception {
        Row row1 = sheet.getRow(row);
        if (row1 == null) {
            row1 = sheet.createRow(row);
        }
        Cell cell = row1.createCell((short)column);

        if (data == null) {
            data = "";
        }
        cell.setCellValue(data);

        if (cellStyle != null) {
            cell.setCellStyle(cellStyle);
        }
    }
    //���趨�ĵ�Ԫ��������� (sheet,�кţ��кţ����ݣ���ʽ����null)
    public static void setCell(Sheet sheet,int row,int column,double data,CellStyle cellStyle)throws Exception {
        Row row1 = sheet.getRow(row);
        if (row1 == null) {
            row1 = sheet.createRow(row);
        }
        Cell cell = row1.createCell((short)column);

        cell.setCellValue(data);

        if (cellStyle != null) {
            cell.setCellStyle(cellStyle);
        }

    }
    //���趨�ĵ�Ԫ��������� (sheet,�кţ��кţ����ݣ���ʽ����null)
    public static void setCell(Sheet sheet, int row, int column, BigDecimal data,CellStyle cellStyle)throws Exception {
        if (data != null) {
            setCell(sheet, row, column, data.doubleValue(), cellStyle);
        } else {
            setCell(sheet, row, column, 0, cellStyle);
        }
    }

    //��������Ӧ�п��кţ�
    public static void setCellWidth(Sheet sheet,int column) {
        sheet.autoSizeColumn(column);
        sheet.setColumnWidth(column,sheet.getColumnWidth(column)*17/10);
    }

}
