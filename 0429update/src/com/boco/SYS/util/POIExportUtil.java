package com.boco.SYS.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.math.BigDecimal;

/**
 * POI 导出设置
 */
public class POIExportUtil {

    //冻结窗口(sheet,冻结列数，冻结行数）
    public static void createFreezePane(Sheet sheet,int column, int row) {
        sheet.createFreezePane(column, row, column, row);
    }

    // 合并单元格 s CellRangeAddress(sheet,起始行号, 结束行号, 起始列号, 结束列号)
    public static void CellRangeAddress(Sheet sheet,int rowBegin, int rowEnd, int columnBegin, int columnEnd) {
        sheet.addMergedRegion(new CellRangeAddress(rowBegin, rowEnd, columnBegin, columnEnd));
    }

    //往设定的单元格填充数据 (sheet,行号，列号，数据,样式可以null)
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
    //往设定的单元格填充数据 (sheet,行号，列号，数据，样式可以null)
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
    //往设定的单元格填充数据 (sheet,行号，列号，数据，样式可以null)
    public static void setCell(Sheet sheet, int row, int column, BigDecimal data,CellStyle cellStyle)throws Exception {
        if (data != null) {
            setCell(sheet, row, column, data.doubleValue(), cellStyle);
        } else {
            setCell(sheet, row, column, 0, cellStyle);
        }
    }

    //设置自适应列宽（列号）
    public static void setCellWidth(Sheet sheet,int column) {
        sheet.autoSizeColumn(column);
        sheet.setColumnWidth(column,sheet.getColumnWidth(column)*17/10);
    }

}
