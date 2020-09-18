package com.boco.RE.excel.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Excel工具类
 * @Author zhuhongjiang
 * @Date 2020/1/7 下午2:51
 **/
public class ExcelUtil {
	public static final short EXCEL_FORMAT_CODE_OF_TEXT = 49;
	public static final short EXCEL_FORMAT_CODE_OF_GENERAL = 0; 
	public static final short EXCEL_FORMAT_CODE_OF_DATE = 14;  
    // excel styles
	public static final short FGColor_Null=9;
	public static final short FGColor_Green = 42;
	public static final short FGColor_Yellow = 13;
	public static final short FGColor_LightYellow = 43;
	public static final short FGColor_Red = 10;
	public static final short BGColor = 64;
	public static final short FPattern = 1;
	public static final short BorderBottom = 1;
	public static final short BorderTop = 1;
	public static final short BorderLeft = 1;
	public static final short BorderRight = 1;
	public static final short LeftBorderColor = 64;
	public static final short RightBorderColor = 64;
	public static final short TopBorderColor = 64;
	public static final short BottomBorderColor = 64;
	public static final short Alignment = 1;
	public static final short Vertical = 2;
	
	public static final int MAX_ROW_ID_IN_SHEET = 65535; //SHEET 最大的行数
	public static final short MAX_COLUMN_ID_IN_SHEET = 255; //SHEET 最大的列数
	
	public static final short COMMON_DEFAULT_ROW_HEIGHT = 350; 
	public static final short COMMON_DEFAULT_COLUMN_WIDTH = 25; 
	public static final short COMMON_MAX_COLUMN_WIDTH = 25600;  
    
    public static CellStyle configErrorStyle(CellStyle style, short FGColor){
    	style.setFillForegroundColor(FGColor);
    	style.setFillBackgroundColor(BGColor);
    	style.setFillPattern(FPattern);
    	style.setDataFormat((short)49);
    	style.setBorderBottom(BorderBottom);
    	style.setBorderTop(BorderTop);
    	style.setBorderLeft(BorderLeft);
    	style.setBorderRight(BorderRight);
    	style.setLeftBorderColor(LeftBorderColor);
    	style.setRightBorderColor(RightBorderColor);
    	style.setTopBorderColor(TopBorderColor);
    	style.setBottomBorderColor(BottomBorderColor);
    	style.setAlignment(Alignment);
    	style.setVerticalAlignment(Vertical);
    	style.setDataFormat((short)0);
    	style.setWrapText(true);
    	
    	return style;
    }
    
    public static void setCellStyle(Cell cell, CellStyle style){
    	cell.setCellStyle(style);
    }

	/**
	 * 
	 * 方法说明:
	 * @param cell
	 * @return
	 */
	public static boolean isCellFormatGeneral(Cell cell){
		if(cell == null){
			return false;
		}
		
		int cellFormatCode = cell.getCellStyle().getDataFormat();
		
		if(cellFormatCode == EXCEL_FORMAT_CODE_OF_GENERAL){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * 
	 * 方法说明:
	 * @param cell
	 * @return
	 */
	public static boolean isCellFormatText(Cell cell){
		if(cell == null){
			return false;
		}
		
		int cellFormatCode = cell.getCellStyle().getDataFormat();
		
		if(cellFormatCode == EXCEL_FORMAT_CODE_OF_TEXT){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * 
	 * 方法说明:
	 * @param cell
	 * @return
	 */
	public static boolean isCellFormatDate(Cell cell){
		if(cell == null){
			return false;
		}
		
		int cellFormatCode = cell.getCellStyle().getDataFormat();
		
		if(cellFormatCode == EXCEL_FORMAT_CODE_OF_DATE){
			return true;
		}
		else{
			return false;
		}
	}
	
	public static boolean isExceedCellLengthLimit(Cell cell, int limit){
		if(cell == null){
			return false;
		}
		
		String cellValue = getStringCellValue(cell);
		if ( cellValue == null ){
			return false;
		}
		else if( cellValue.getBytes().length > limit ) {
			return true;
		}
		else{
			return false;
		}
	}
	
	public static boolean isLessThanCellLengthRequirement(Cell cell, int minLength){
		if(cell == null){
			return false;
		}
		
		String cellValue = getStringCellValue(cell);
		if ( cellValue == null ){
			return false;
		}
		else if( cellValue.getBytes().length < minLength ) {
			return true;
		}
		else{
			return false;
		}
	}
	
	public static Cell getCell(Row row, short i) {
		if(row == null || i < 0 || i > MAX_COLUMN_ID_IN_SHEET){
			return null;
		}
		
		Cell cell = row.getCell(i);
		if(cell == null){
			cell = row.createCell(i);
		}
        
        return cell;
    }
	
	public static Cell getCell(Sheet sheet, int rowId, short columnId) {
		if(sheet == null || rowId < 0 || columnId < 0){
			return null;
		}
		
		Row row = sheet.getRow(rowId);
		if(row == null){
			row = sheet.createRow(rowId);
		}
		
		Cell cell = row.getCell(columnId);
		if(cell == null){
			cell = row.createCell(columnId);
		}
        
        return cell;
    }
	
	public static Row getRow(Sheet sheet, int rowId) {
		if(sheet == null || rowId < 0 || rowId > MAX_ROW_ID_IN_SHEET){
			return null;
		}
		
		Row row = sheet.getRow(rowId);
		if(row == null){
			row = sheet.createRow(rowId);
		}
        
        return row;
    }
	
	public static Sheet getSheet(Workbook workbook, int sheetId) {
		if(workbook == null || sheetId < 0){
			return null;
		}
		
		Sheet sheet = workbook.getSheetAt(sheetId);
		if(sheet == null){
			sheet = workbook.createSheet("sheet" + sheetId);
		}
        
        return sheet;
    }
	
	public static String getStringCellValue(Cell cell) {
		if (cell == null){
			return "";
		}

		String result = "";
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BOOLEAN:
				result = String.valueOf(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					result = TIME_FORMATTER.format(cell.getDateCellValue());
				}
				else{
					double doubleValue = cell.getNumericCellValue();
					BigDecimal db = new BigDecimal(String.valueOf(doubleValue));	//添加科学计数法转化
					return  db.toPlainString();
				}
				break;
			case Cell.CELL_TYPE_STRING:
				if (cell.getRichStringCellValue() == null){
					result = "";
				}
				else{
					result = trim(cell.getRichStringCellValue().getString());
				}
				break;
			case Cell.CELL_TYPE_BLANK:
				result = "";
				break;
			case Cell.CELL_TYPE_FORMULA://update by jerry
				try{
					result = String.valueOf(cell.getNumericCellValue());   
		        }catch(Exception e){
		        	result = cell.getRichStringCellValue().getString();
		        }
				break;
			default:
				result = "";
		}
		
		return result;
	}
	
	public static boolean isFormulaExistsInCell(Cell cell){
		boolean result = false;
		if(cell != null && cell.getCellType() == Cell.CELL_TYPE_FORMULA){
			result = true;
		}
		
		return result;
	}
	
	public static boolean isCellTypeSupported(Cell cell){
		if(cell == null){
			return true;
		}
		
		boolean result = false;
		
		int cellType = cell.getCellType();
		
		if(cellType == Cell.CELL_TYPE_STRING || cellType == Cell.CELL_TYPE_NUMERIC 
				|| cellType == Cell.CELL_TYPE_BOOLEAN || cellType == Cell.CELL_TYPE_BLANK ||cellType==Cell.CELL_TYPE_FORMULA){
			result = true;
		}
		
		return result;
	}
	
	public static String getIntStringFromDouble(double d){
		String str = d + "";
		if(str.indexOf(".") >= 0){
			str = str.substring(0, str.lastIndexOf("."));
		}
		return str;
	}
	
	public static int getIntFromDouble(double d){
		String str = d + "";
		if(str.indexOf(".") >= 0){
			str = str.substring(0, str.lastIndexOf("."));
		}
		
		int result = 0;
		try{
			result = Integer.parseInt(str);
		}
		catch(Exception e){
		}
		
		return result;
	}

	public static boolean isNumber(String str) {
		if(str == null || "".equals(str.trim())){
			return false;
		}
		
		try {
			Double.parseDouble(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isStandardDate(String str) {
		if(str == null || "".equals(str.trim())){
			return false;
		}
		
		boolean flag = false;
		
		/*
		if(str.matches("[1-9][0-9]{3}\\-(0[0-9]|1[0-2])\\-(0[0-9]|1[0-9]|2[0-9]|3[0-1])")){
			flag = true;
		}
		*/
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		sdf.setLenient(false); 
		
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			flag = false;
		}
		
		if(date == null){
			flag = false;
		}
		else{
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * A safety method instead of String.trim()
	 */
	public static String trim(String str) {
		if(str == null){
			return null;
		}
		else{
			return str.trim();
		}
	}
	
	public static boolean isEmptyString(String str) {
		if (str == null) {
			return true;
		}
		else if ("".equals(str.trim())) {
			return true;
		}
		else{
			return false;
		}
	}
	
	public static boolean isValueInOptions(Object value, Object[] options){
		boolean result = false;
		
		if(options != null && options.length > 0){
			for(int i = 0; i < options.length; i++ ){
				if(value == null){
					if(options[i] == null){
						result = true;
						break;
					}
				}
				else if(value.equals(options[i])){
					result = true;
					break;
				}
			}
		}
		
		return result;
	}
	
	public static boolean isValueInOptions(Object value, List options){
		boolean result = false;
		
		if(options != null && options.size() > 0){
			for(int i = 0; i < options.size(); i++ ){
				if(value == null){
					if(options.get(i) == null){
						result = true;
						break;
					}
				}
				else if(value.equals(options.get(i))){
					result = true;
					break;
				}
			}
		}
		
		return result;
	}
	
	public static boolean isAllFlagTrue(boolean[] flags){
		boolean result = true;
		
		if(flags == null || flags.length == 0){
			return false;
		}
		
		for(int i = 0; i < flags.length; i++){
			if(flags[i] == false){
				result = false;
			}
		}
		
		return result;
	}

	public static String[][] getExcelCellsValue(MultipartFile file,HttpServletRequest request) throws IOException {
		String[][] values = new String[0][];
		String temp = request.getSession().getServletContext().getRealPath(File.separator)+ "temp"; // 临时目录
	    File tempFile = new File(temp);
	    if (!tempFile.exists()) {
	      tempFile.mkdirs();
	    } 

	    if (file == null)
	      return null;
	    
	    String name = file.getOriginalFilename();// 获取上传文件名,包括路径
	    long size = file.getSize();
	    if ((name == null || name.equals("")) && size == 0)
	      return null;
	   
	    
	    InputStream is = file.getInputStream();
	    try{
	    	HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		    for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
		    	 HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
		         if (hssfSheet == null) {
		           continue;
		         }
		         values = new String[hssfSheet.getLastRowNum() ][];
		          // 循环行Row
		         for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
		        	HSSFRow hssfRow = hssfSheet.getRow(rowNum);
		        	values[rowNum - 1] = new String[hssfRow.getLastCellNum()];
					for (int i = 0; i < hssfRow.getLastCellNum(); i++) {
						HSSFCell hSSFCellValue = hssfRow.getCell(i);
						values[rowNum - 1][i] = hSSFCellValue == null ? "" : hSSFCellValue.toString();
					}
		         }
		         //只读取第一页数据
		         break;
		    }
	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	if(is!=null){
	    		is.close();
	    	}
	    }
	    return values;
	}
	
}
