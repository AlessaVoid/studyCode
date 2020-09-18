package com.boco.SYS.util;

import com.boco.SYS.exception.SystemException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Excel导入工具
 *
 * <pre>
 * 修改日期        修改人    修改原因
 * 2014-9-16    	     杨滔    新建
 * </pre>
 */
public class ExcelImport {
	public ExcelImport() {
	}

	public Iterator rowIterator = null;// excel表格中所有数据
	public HSSFRow currentRow = null;
	public String[] excelHeaders = null; // execel表格第一行字段
	public int rowCount = 0;
	short firstColNum = 0;
	short lastColNum = 0;
	public Map excelRecord = null;// 返回所有记录的map

	public static void main(String[] args) throws FileNotFoundException {
		String[] chargeTopParamKey = {"TRADETYPE", "TRADECODE", "CHARGEKIND", "SCOPE", "CHARGERATE"};
		List<Map> list = excelImpMapRow(chargeTopParamKey, "D:\\111.xls", 1, 1, 20);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).get("SCOPE").toString());
		}
		System.out.println(list.size());
	}

	/**
	 * TODO 导入竖向表格，如果有空需要用字符占位
	 *
	 * @param keys     数组key值
	 * @param filename 文件名称
	 * @param cellnum  获取第几列的值从1开始
	 * @param beginrow 开始行
	 * @param endrow   结束行
	 * @return
	 * @throws FileNotFoundException <pre>
	 *                               修改日期        修改人    修改原因
	 *                               2015-6-4    	     代策    新建
	 *                               </pre>
	 */
	public static Map<String, Object> excelImpMap(String[] keys, String filename, int cellnum, int beginrow, int endrow) throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(filename);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HSSFWorkbook _workbook = new HSSFWorkbook(fis);
			if (_workbook.getNumberOfSheets() == 0) {// 判断是否有单元薄
				fis.close();
				return null;
			}// if//
			HSSFSheet sheet = _workbook.getSheetAt(0);// 取得第一个单元薄
			cellnum--;
			if (keys.length == (endrow - beginrow + 1)) {
				int j = 0;
				for (int i = beginrow - 1; i <= endrow - 1; i++) {
					map.put(keys[j], String.valueOf(new ExcelImport().getCellValue(sheet.getRow(i).getCell((short) cellnum))).replaceAll(",", "").replaceAll("\"", "").replaceAll("null", ""));
					j++;
				}
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				System.out.println("ExcelUtil:" + ex.getMessage());
			}
		}
		return map;
	}

	/**
	 * 解析项目keys为行列值行数，列数从1开始
	 *
	 * @param keys     数组key值
	 * @param filename 文件名称
	 * @param cellnum  获取第几列的值从1开始
	 * @param beginrow 开始行
	 * @param endrow   结束行
	 * @return
	 * @throws FileNotFoundException <pre>
	 *                               修改日期        修改人    修改原因
	 *                               2015-6-25    	     杨滔    新建
	 *                               </pre>
	 */
	public static List<Map> excelImpMapRow(String[] keys, String filename, int cellnum, int beginrow, int endrow) throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(filename);
		List<Map> dataList = new ArrayList<Map>();
		try {
			HSSFWorkbook _workbook = new HSSFWorkbook(fis);
			if (_workbook.getNumberOfSheets() == 0) {// 判断是否有单元薄
				fis.close();
				return null;
			}// if//
			HSSFSheet sheet = _workbook.getSheetAt(0);// 取得第一个单元薄

			int rows = endrow - beginrow;

			if (rows < 0) {
				throw new Exception("读取记录数非法");
			}
			if (keys.length <= 0) {
				throw new Exception("传入keys非法");
			}
			cellnum--;
			ExcelImport ex = new ExcelImport();
			//HSSFCell cell = sheet.getRow(1).getCell(2);
			//HSSFCell cellS = sheet.getRow(1).getCell(1);
			for (int i = beginrow - 1; i < endrow; i++) {
				Map rowMap = new HashMap();
				System.out.println("length:" + keys.length);
				for (int ii = 0; ii < keys.length; ii++) {
					rowMap.put(keys[ii], String.valueOf(new ExcelImport().getCellValue(sheet.getRow(i).getCell((short) (cellnum + ii)))).replaceAll(",", "").replaceAll("\"", "").replaceAll("null", ""));
				}
				dataList.add(rowMap);
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				System.out.println("ExcelUtil:" + ex.getMessage());
			}
		}
		return dataList;
	}

	/**
	 * 解析项目keys为行列值行数，列数从1开始
	 *
	 * @param filename 文件名称
	 * @param cellnum  获取第几列的值从1开始
	 * @param beginrow 开始行
	 * @return
	 * @throws FileNotFoundException <pre>
	 *                               修改日期        修改人    修改原因
	 *                               2015-6-25    	     杨滔    新建
	 *                               </pre>
	 */
	public static List<String> excelImpOneRow(String filename, int cellnum, int beginrow) throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(filename);
		// List<Map> dataList = new ArrayList<Map>();
		List<String> dataList = new ArrayList<String>();
		try {
			HSSFWorkbook _workbook = new HSSFWorkbook(fis);
			if (_workbook.getNumberOfSheets() == 0) {// 判断是否有单元薄
				fis.close();
				return null;
			}// if//
			HSSFSheet sheet = _workbook.getSheetAt(0);// 取得第一个单元薄

			cellnum--;
			ExcelImport ex = new ExcelImport();
			boolean bl = true;
			int i = 0;
			while (bl) {
				String cellValue = String.valueOf(new ExcelImport().getCellValue(sheet.getRow(beginrow - 1).getCell((short) (cellnum + i)))).replaceAll(",", "").replaceAll("\"", "").replaceAll("null", "");
				if ("".equals(cellValue)) {
					bl = false;
					break;
				}
				dataList.add(cellValue);
				i++;
			}


		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				System.out.println("ExcelUtil:" + ex.getMessage());
			}
		}
		return dataList;
	}


	/**
	 * TODO 横向Execl表格转换成List<Map<String,Object>.
	 *
	 * @param keys     变量名
	 * @param filename 文件名
	 * @param beginrow 起始行号
	 * @param endrow   结束行号
	 * @param begincol 起始列号
	 * @param endcol   结束列号
	 * @return
	 * @throws FileNotFoundException <pre>
	 *                               修改日期        修改人    修改原因
	 *                               2015-6-11    	    杨滔    新建
	 *                               </pre>
	 */
	public static List<Map<String, Object>> excelImpList(String[] keys, String filename, int beginrow, int begincol, int endcol) throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(filename);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		try {
			HSSFWorkbook _workbook = new HSSFWorkbook(fis);
			if (_workbook.getNumberOfSheets() == 0) {// 判断是否有单元薄
				fis.close();
				return null;
			}// if//
			HSSFSheet sheet = _workbook.getSheetAt(0);// 取得第一个单元薄
			String cellValue = null;
			// 循环行
			for (int r = beginrow - 1; r <= sheet.getPhysicalNumberOfRows(); r++) {
				map = new HashMap<String, Object>();
				if (keys.length == (endcol - begincol + 1)) {
					int j = 0;
					// 循环列
					for (int i = begincol - 1; i <= endcol - 1; i++) {
						cellValue = String.valueOf(new ExcelImport().getCellValue(sheet.getRow(r).getCell((short) i))).replaceAll(",", "").replaceAll("\"", "").replaceAll("null", "");
						if (!StringUtils.hasText(cellValue)) {
							return list;
						} else {
							map.put(keys[j], cellValue);
							j++;
						}
					}
				}
				list.add(map);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				System.out.println("ExcelUtil:" + ex.getMessage());
			}
		}
		return list;
	}

	/**
	 * TODO 横向Execl表格转换成List<Map<String,Object>.
	 *
	 * @param keys     变量名
	 * @param filename 文件名
	 * @param beginrow 起始行号
	 * @param endrow   结束行号
	 * @param begincol 起始列号
	 * @param endcol   结束列号
	 * @return
	 * @throws FileNotFoundException <pre>
	 *                               修改日期        修改人    修改原因
	 *                               2016-8-17     张兴帅 	  新建
	 *                               </pre>
	 */
	public static List<Map<String, Object>> excelList(String[] keys, String filename, int beginrow, int begincol, int endcol) throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(filename);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		try {
			HSSFWorkbook _workbook = new HSSFWorkbook(fis);
			if (_workbook.getNumberOfSheets() == 0) {// 判断是否有单元薄
				fis.close();
				return null;
			}// if//
			HSSFSheet sheet = _workbook.getSheetAt(0);// 取得第一个单元薄
			String cellValue = null;
			// 循环行
			for (int r = beginrow - 1; r <= sheet.getPhysicalNumberOfRows(); r++) {
				map = new HashMap<String, Object>();
				if (keys.length == (endcol - begincol + 1)) {
					int j = 0;
					// 循环列
					for (int i = begincol - 1; i <= endcol - 1; i++) {
						cellValue = String.valueOf(getCellStringValue(sheet.getRow(r).getCell((short) i))).replaceAll(",", "").replaceAll("\"", "").replaceAll("null", "");
						map.put(keys[j], cellValue);
						j++;
					}
				}
				list.add(map);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				System.out.println("ExcelUtil:" + ex.getMessage());
			}
		}
		return list;
	}

	/**
	 *
	 */
	public ExcelImport(String filename) throws IOException {
		FileInputStream fis = new FileInputStream(filename);
		try {
			HSSFWorkbook _workbook = new HSSFWorkbook(fis);
			if (_workbook.getNumberOfSheets() == 0) {// 判断是否有单元薄
				fis.close();
				return;
			}// if//

			HSSFSheet sheet = _workbook.getSheetAt(0);// 取得第一个单元薄
			rowCount = sheet.getPhysicalNumberOfRows();
			rowIterator = sheet.rowIterator();// 迭代出所有记录

			// Get field headers
			if (rowIterator.hasNext()) {
				HSSFRow headerRow = (HSSFRow) rowIterator.next();
				firstColNum = headerRow.getFirstCellNum();
				lastColNum = headerRow.getLastCellNum();
				excelHeaders = new String[lastColNum - firstColNum + 1];
				for (short i = firstColNum, j = 0; i <= lastColNum; i++, j++) {
					HSSFCell cell = headerRow.getCell(i);
					Object obj = getCellValue(cell);
					if (obj != null) {
						String st = obj.toString().toLowerCase();
						if (contains(obj.toString().toLowerCase())) {
							excelHeaders[j] = st;
						}
					}

					if (excelHeaders == null) {
					}
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				System.out.println("ExcelUtil:" + ex.getMessage());
			}
		}
	}// Excel//

	public Map next() {
		return excelRecord;
	}// current()//

	public String[] getHeaders() {
		return excelHeaders;
	}// getHeaders()//

	public int getRowCount() {
		return rowCount;
	}// getRowCount()//

	// /////返回excel的内容的到hashmap是否为空（判断excel表格的内容是否为空）/////
	public boolean hasNext() {
		if (rowIterator == null || !rowIterator.hasNext())
			return false;

		currentRow = (HSSFRow) rowIterator.next();
		excelRecord = new HashMap();
		for (short i = firstColNum, j = 0; i <= lastColNum; i++, j++) {
			HSSFCell cell = currentRow.getCell(i);

			Object k = excelHeaders[j];
			Object v = getCellValue(cell);

			if (v == null)
				continue;
			excelRecord.put(k, v);
		}// for//

		if (excelRecord.isEmpty())
			return hasNext();

		return true;
	}// next()//

	private Object getCellValue(HSSFCell cell) {
		if (cell != null) {
			int t = cell.getCellType();
			int f = cell.getCellStyle().getDataFormat();

			if (t == HSSFCell.CELL_TYPE_NUMERIC) {// 判断单元薄是否式时间格式

				if (f >= 0x0A && f <= 0x16) { // Datetime format
					try {
						DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA);// 格式化成本地的时间格式
						return "" + df.format(cell.getDateCellValue());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				double d = cell.getNumericCellValue();
				if (d == Math.floor(d))
					return new Long((long) d);
				return new Double(d);
			}// if//
			if (t == HSSFCell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
		}// if//
		return null;
	}// getCellValue()//

	/**
	 * 判断表格中的字段名是否为空
	 *
	 * @param s
	 * @return
	 */
	public static boolean contains(Object s) {
		if (s == null) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * TODO 根据工作簿编号读取Excel数据到Map变量中.
	 *
	 * @param keys     保存数据的变量数组
	 * @param fileName Excel文件名
	 * @param sheetNo  工作簿编号(从1开始)
	 * @param colNum   列编号(从1开始)
	 * @param beginRow 起始行(从1开始)
	 * @param endRow   结束行(从1开始)
	 * @return
	 * @throws Exception <pre>
	 *                   修改日期        修改人    修改原因
	 *                   2016年3月18日    	    杨滔    新建
	 *                   </pre>
	 */
	public static Map<String, Object> excelToMap(String[] keys, String fileName, int sheetNo, int colNum, int beginRow, int endRow) throws Exception {
		FileInputStream fis = new FileInputStream(fileName);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HSSFWorkbook _workbook = new HSSFWorkbook(fis);
			if (_workbook.getNumberOfSheets() == 0) {
				fis.close();
				//表格没有单元薄!
				throw new SystemException("w120");
			}
			if (sheetNo < 1) {
				fis.close();
				//读取工作簿编号{0}不正确!
				throw new SystemException("w121", sheetNo);
			}
			int rows = endRow - beginRow + 1;
			if (rows < 0) {
				fis.close();
				//读取记录数非法，开始行[{0}]大于结束行[{1}]
				throw new SystemException("w122", beginRow, endRow);
			}
			if (keys.length != rows) {
				fis.close();
				//变量数组大小[{0}]与读取行数[{1}]不一致!
				throw new SystemException("w123", keys.length, rows);
			}
			HSSFSheet sheet = _workbook.getSheetAt(sheetNo - 1);
			colNum--;
			beginRow--;
			endRow--;
			int j = 0;
			for (int i = beginRow; i <= endRow; i++) {
				Object value = new ExcelImport().getCellValue(sheet.getRow(i).getCell((short) colNum));
				if (value != null) {
					map.put(keys[j], value.toString().replaceAll(",", "").replaceAll("\"", "").replaceAll("null", ""));
				}
				j++;
			}
		} catch (Exception ex) {
			//读取表格出错!
			throw new SystemException("w124", ex);
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				//关闭流失败!
				throw new SystemException("w125", ex);
			}
		}
		return map;
	}

	/**
	 * TODO 根据工作簿编号读取Excel数据到List变量中.
	 *
	 * @param keys     保存数据的变量数组
	 * @param fileName Excel文件名
	 * @param sheetNo  工作簿编号(从1开始)
	 * @param beginRow 开始行编号(从1开始)
	 * @param beginCol 开始列编号(从1开始)
	 * @param endCol   结束编号(从1开始)
	 * @return
	 * @throws Exception <pre>
	 *                   修改日期        修改人    修改原因
	 *                   2016年3月18日    	    杨滔    新建
	 *                   </pre>
	 */
	public static List<Map<String, Object>> excelToList(String[] keys, String fileName, int sheetNo, int beginRow, int endRow, int beginCol, int endCol) throws Exception {
		FileInputStream fis = new FileInputStream(fileName);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		try {
			HSSFWorkbook _workbook = new HSSFWorkbook(fis);
			if (_workbook.getNumberOfSheets() == 0) {
				fis.close();
				//表格没有单元薄!
				throw new SystemException("w120");
			}
			if (sheetNo < 1) {
				fis.close();
				//读取工作簿编号{0}不正确!
				throw new SystemException("w121", sheetNo);
			}
			if (endRow < beginRow) {
				fis.close();
				//读取记录数非法，开始行[{0}]大于结束行[{1}]
				throw new SystemException("w122", beginRow, endRow);
			}
			if (keys.length != (endCol - beginCol + 1)) {
				fis.close();
				throw new SystemException("w126", keys.length, endCol - beginCol + 1);
			}
			HSSFSheet sheet = _workbook.getSheetAt(sheetNo - 1);// 取得第一个单元薄
			String cellValue = null;
			beginRow--;
			endRow--;
			beginCol--;
			endCol--;
			// 循环行
			for (int r = beginRow; r <= endRow; r++) {
				map = new HashMap<String, Object>();
				int j = 0;
				// 循环列
				for (int i = beginCol; i <= endCol; i++) {
					cellValue = String.valueOf(new ExcelImport().getCellValue(sheet.getRow(r).getCell((short) i))).replaceAll(",", "").replaceAll("\"", "").replaceAll("null", "").trim();
					map.put(keys[j], cellValue);
					j++;
				}
				list.add(map);
			}
		} catch (Exception ex) {
			//读取表格出错!
			throw new SystemException("w124", ex);
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				//关闭流失败!
				throw new SystemException("w125", ex);
			}
		}
		return list;
	}

	/**
	 * TODO 读取Excel指定坐标数据.
	 *
	 * @param fileName
	 * @param sheetNo
	 * @param rowNum
	 * @param colNum
	 * @return
	 * @throws Exception <pre>
	 *                   修改日期        修改人    修改原因
	 *                   2016年3月18日    	    杨滔    新建
	 *                   </pre>
	 */
	public static String getCellValue(String fileName, int sheetNo, int rowNum, int colNum) throws Exception {
		FileInputStream fis = new FileInputStream(fileName);
		try {
			HSSFWorkbook _workbook = new HSSFWorkbook(fis);
			if (_workbook.getNumberOfSheets() == 0) {
				fis.close();
				//表格没有单元薄!
				throw new SystemException("w120");
			}
			if (sheetNo < 1) {
				fis.close();
				//读取工作簿编号{0}不正确!
				throw new SystemException("w121", sheetNo);
			}
			if (rowNum < 1) {
				fis.close();
				//读取行号不正确!
				throw new SystemException("w127", rowNum);
			}
			if (colNum < 1) {
				fis.close();
				//读取列号不正确!
				throw new SystemException("w128", colNum);
			}
			HSSFSheet sheet = _workbook.getSheetAt(sheetNo - 1);
			colNum--;
			rowNum--;
			Object value = new ExcelImport().getCellValue(sheet.getRow(rowNum).getCell((short) colNum));
			if (value != null) {
				return value.toString().replace(",", "").replace("\"", "").replace("null", "");
			}
		} catch (Exception ex) {
			//读取表格出错!
			throw new SystemException("w124", ex);
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				//关闭流失败!
				throw new SystemException("w125", ex);
			}
		}
		return null;
	}

	/**
	 * TODO 根据工作簿编号读取Excel数据到List变量中.
	 *
	 * @param keys     保存数据的变量数组
	 * @param fileName Excel文件名
	 * @param sheetNo  工作簿编号(从1开始)
	 * @param beginRow 开始行编号(从1开始)
	 * @param beginCol 开始列编号(从1开始)
	 * @param endCol   结束编号(从1开始)
	 * @return
	 * @throws Exception <pre>
	 *                   修改日期        修改人    修改原因
	 *                   2017年2月7日    	   谷立羊  新建
	 *                   </pre>
	 */
	public static List<Map<String, Object>> excelToList(String[] keys, String fileName, int sheetNo, int beginRow, int beginCol, int endCol) throws Exception {
		FileInputStream fis = new FileInputStream(fileName);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		try {
			HSSFWorkbook _workbook = new HSSFWorkbook(fis);
			if (_workbook.getNumberOfSheets() == 0) {
				fis.close();
				//表格没有单元薄!
				throw new SystemException("w120");
			}
			if (sheetNo < 1) {
				fis.close();
				//读取工作簿编号{0}不正确!
				throw new SystemException("w121", sheetNo);
			}
			if (keys.length != (endCol - beginCol + 1)) {
				fis.close();
				throw new SystemException("w126", keys.length, endCol - beginCol + 1);
			}
			HSSFSheet sheet = _workbook.getSheetAt(sheetNo - 1);// 取得第一个单元薄
			String cellValue = null;
			beginRow--;
			beginCol--;
			endCol--;
			// 循环行
			for (int r = beginRow; sheet.getRow(r) != null; r++) {
				map = new HashMap<String, Object>();
				int j = 0;
				// 循环列
				for (int i = beginCol; i <= endCol; i++) {
					cellValue = String.valueOf(new ExcelImport().getCellValue(sheet.getRow(r).getCell((short) i))).replaceAll(",", "").replaceAll("\"", "").replaceAll("null", "").trim();
					map.put(keys[j], cellValue);
					j++;
				}
				list.add(map);
			}
		} catch (Exception ex) {
			//读取表格出错!
			throw new SystemException("w124", ex);
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				//关闭流失败!
				throw new SystemException("w125", ex);
			}
		}
		return list;
	}

	private static String getCellStringValue(Cell cell) {
		if (cell == null) {
			return "";
		} else {
			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				return getRealStringValueOfDouble(new Double(NumberToTextConverter.toText(cell.getNumericCellValue())));
				}
			cell.setCellType(1);
			return cell.getStringCellValue().trim();
		}
	}

	private static String getRealStringValueOfDouble(Double d) {
		String doubleStr = d.toString();
		boolean b = doubleStr.contains("E");
		int indexOfPoint = doubleStr.indexOf(".");
		if (b) {
			int indexOfE = doubleStr.indexOf("E");
			BigInteger xs = new BigInteger(doubleStr.substring(indexOfPoint + BigInteger.ONE.intValue(), indexOfE));
			int pow = Integer.valueOf(doubleStr.substring(indexOfE + BigInteger.ONE.intValue()));
			int xsLen = xs.toByteArray().length;
			int scale = xsLen - pow > 0 ? xsLen - pow : 0;
			doubleStr = String.format("%." + scale + "f", d);
		} else {
			Pattern p = Pattern.compile(".0$");
			Matcher m = p.matcher(doubleStr);
			if (m.find()) {
				doubleStr = doubleStr.replace(".0", "");
			}
		}
		return doubleStr;
	}

}
