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
 * Excel���빤��
 *
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2014-9-16    	     ����    �½�
 * </pre>
 */
public class ExcelImport {
	public ExcelImport() {
	}

	public Iterator rowIterator = null;// excel�������������
	public HSSFRow currentRow = null;
	public String[] excelHeaders = null; // execel����һ���ֶ�
	public int rowCount = 0;
	short firstColNum = 0;
	short lastColNum = 0;
	public Map excelRecord = null;// �������м�¼��map

	public static void main(String[] args) throws FileNotFoundException {
		String[] chargeTopParamKey = {"TRADETYPE", "TRADECODE", "CHARGEKIND", "SCOPE", "CHARGERATE"};
		List<Map> list = excelImpMapRow(chargeTopParamKey, "D:\\111.xls", 1, 1, 20);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).get("SCOPE").toString());
		}
		System.out.println(list.size());
	}

	/**
	 * TODO ��������������п���Ҫ���ַ�ռλ
	 *
	 * @param keys     ����keyֵ
	 * @param filename �ļ�����
	 * @param cellnum  ��ȡ�ڼ��е�ֵ��1��ʼ
	 * @param beginrow ��ʼ��
	 * @param endrow   ������
	 * @return
	 * @throws FileNotFoundException <pre>
	 *                               �޸�����        �޸���    �޸�ԭ��
	 *                               2015-6-4    	     ����    �½�
	 *                               </pre>
	 */
	public static Map<String, Object> excelImpMap(String[] keys, String filename, int cellnum, int beginrow, int endrow) throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(filename);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HSSFWorkbook _workbook = new HSSFWorkbook(fis);
			if (_workbook.getNumberOfSheets() == 0) {// �ж��Ƿ��е�Ԫ��
				fis.close();
				return null;
			}// if//
			HSSFSheet sheet = _workbook.getSheetAt(0);// ȡ�õ�һ����Ԫ��
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
	 * ������ĿkeysΪ����ֵ������������1��ʼ
	 *
	 * @param keys     ����keyֵ
	 * @param filename �ļ�����
	 * @param cellnum  ��ȡ�ڼ��е�ֵ��1��ʼ
	 * @param beginrow ��ʼ��
	 * @param endrow   ������
	 * @return
	 * @throws FileNotFoundException <pre>
	 *                               �޸�����        �޸���    �޸�ԭ��
	 *                               2015-6-25    	     ����    �½�
	 *                               </pre>
	 */
	public static List<Map> excelImpMapRow(String[] keys, String filename, int cellnum, int beginrow, int endrow) throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(filename);
		List<Map> dataList = new ArrayList<Map>();
		try {
			HSSFWorkbook _workbook = new HSSFWorkbook(fis);
			if (_workbook.getNumberOfSheets() == 0) {// �ж��Ƿ��е�Ԫ��
				fis.close();
				return null;
			}// if//
			HSSFSheet sheet = _workbook.getSheetAt(0);// ȡ�õ�һ����Ԫ��

			int rows = endrow - beginrow;

			if (rows < 0) {
				throw new Exception("��ȡ��¼���Ƿ�");
			}
			if (keys.length <= 0) {
				throw new Exception("����keys�Ƿ�");
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
	 * ������ĿkeysΪ����ֵ������������1��ʼ
	 *
	 * @param filename �ļ�����
	 * @param cellnum  ��ȡ�ڼ��е�ֵ��1��ʼ
	 * @param beginrow ��ʼ��
	 * @return
	 * @throws FileNotFoundException <pre>
	 *                               �޸�����        �޸���    �޸�ԭ��
	 *                               2015-6-25    	     ����    �½�
	 *                               </pre>
	 */
	public static List<String> excelImpOneRow(String filename, int cellnum, int beginrow) throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(filename);
		// List<Map> dataList = new ArrayList<Map>();
		List<String> dataList = new ArrayList<String>();
		try {
			HSSFWorkbook _workbook = new HSSFWorkbook(fis);
			if (_workbook.getNumberOfSheets() == 0) {// �ж��Ƿ��е�Ԫ��
				fis.close();
				return null;
			}// if//
			HSSFSheet sheet = _workbook.getSheetAt(0);// ȡ�õ�һ����Ԫ��

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
	 * TODO ����Execl���ת����List<Map<String,Object>.
	 *
	 * @param keys     ������
	 * @param filename �ļ���
	 * @param beginrow ��ʼ�к�
	 * @param endrow   �����к�
	 * @param begincol ��ʼ�к�
	 * @param endcol   �����к�
	 * @return
	 * @throws FileNotFoundException <pre>
	 *                               �޸�����        �޸���    �޸�ԭ��
	 *                               2015-6-11    	    ����    �½�
	 *                               </pre>
	 */
	public static List<Map<String, Object>> excelImpList(String[] keys, String filename, int beginrow, int begincol, int endcol) throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(filename);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		try {
			HSSFWorkbook _workbook = new HSSFWorkbook(fis);
			if (_workbook.getNumberOfSheets() == 0) {// �ж��Ƿ��е�Ԫ��
				fis.close();
				return null;
			}// if//
			HSSFSheet sheet = _workbook.getSheetAt(0);// ȡ�õ�һ����Ԫ��
			String cellValue = null;
			// ѭ����
			for (int r = beginrow - 1; r <= sheet.getPhysicalNumberOfRows(); r++) {
				map = new HashMap<String, Object>();
				if (keys.length == (endcol - begincol + 1)) {
					int j = 0;
					// ѭ����
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
	 * TODO ����Execl���ת����List<Map<String,Object>.
	 *
	 * @param keys     ������
	 * @param filename �ļ���
	 * @param beginrow ��ʼ�к�
	 * @param endrow   �����к�
	 * @param begincol ��ʼ�к�
	 * @param endcol   �����к�
	 * @return
	 * @throws FileNotFoundException <pre>
	 *                               �޸�����        �޸���    �޸�ԭ��
	 *                               2016-8-17     ����˧ 	  �½�
	 *                               </pre>
	 */
	public static List<Map<String, Object>> excelList(String[] keys, String filename, int beginrow, int begincol, int endcol) throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(filename);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		try {
			HSSFWorkbook _workbook = new HSSFWorkbook(fis);
			if (_workbook.getNumberOfSheets() == 0) {// �ж��Ƿ��е�Ԫ��
				fis.close();
				return null;
			}// if//
			HSSFSheet sheet = _workbook.getSheetAt(0);// ȡ�õ�һ����Ԫ��
			String cellValue = null;
			// ѭ����
			for (int r = beginrow - 1; r <= sheet.getPhysicalNumberOfRows(); r++) {
				map = new HashMap<String, Object>();
				if (keys.length == (endcol - begincol + 1)) {
					int j = 0;
					// ѭ����
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
			if (_workbook.getNumberOfSheets() == 0) {// �ж��Ƿ��е�Ԫ��
				fis.close();
				return;
			}// if//

			HSSFSheet sheet = _workbook.getSheetAt(0);// ȡ�õ�һ����Ԫ��
			rowCount = sheet.getPhysicalNumberOfRows();
			rowIterator = sheet.rowIterator();// ���������м�¼

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

	// /////����excel�����ݵĵ�hashmap�Ƿ�Ϊ�գ��ж�excel���������Ƿ�Ϊ�գ�/////
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

			if (t == HSSFCell.CELL_TYPE_NUMERIC) {// �жϵ�Ԫ���Ƿ�ʽʱ���ʽ

				if (f >= 0x0A && f <= 0x16) { // Datetime format
					try {
						DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA);// ��ʽ���ɱ��ص�ʱ���ʽ
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
	 * �жϱ���е��ֶ����Ƿ�Ϊ��
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
	 * TODO ���ݹ�������Ŷ�ȡExcel���ݵ�Map������.
	 *
	 * @param keys     �������ݵı�������
	 * @param fileName Excel�ļ���
	 * @param sheetNo  ���������(��1��ʼ)
	 * @param colNum   �б��(��1��ʼ)
	 * @param beginRow ��ʼ��(��1��ʼ)
	 * @param endRow   ������(��1��ʼ)
	 * @return
	 * @throws Exception <pre>
	 *                   �޸�����        �޸���    �޸�ԭ��
	 *                   2016��3��18��    	    ����    �½�
	 *                   </pre>
	 */
	public static Map<String, Object> excelToMap(String[] keys, String fileName, int sheetNo, int colNum, int beginRow, int endRow) throws Exception {
		FileInputStream fis = new FileInputStream(fileName);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HSSFWorkbook _workbook = new HSSFWorkbook(fis);
			if (_workbook.getNumberOfSheets() == 0) {
				fis.close();
				//���û�е�Ԫ��!
				throw new SystemException("w120");
			}
			if (sheetNo < 1) {
				fis.close();
				//��ȡ���������{0}����ȷ!
				throw new SystemException("w121", sheetNo);
			}
			int rows = endRow - beginRow + 1;
			if (rows < 0) {
				fis.close();
				//��ȡ��¼���Ƿ�����ʼ��[{0}]���ڽ�����[{1}]
				throw new SystemException("w122", beginRow, endRow);
			}
			if (keys.length != rows) {
				fis.close();
				//���������С[{0}]���ȡ����[{1}]��һ��!
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
			//��ȡ������!
			throw new SystemException("w124", ex);
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				//�ر���ʧ��!
				throw new SystemException("w125", ex);
			}
		}
		return map;
	}

	/**
	 * TODO ���ݹ�������Ŷ�ȡExcel���ݵ�List������.
	 *
	 * @param keys     �������ݵı�������
	 * @param fileName Excel�ļ���
	 * @param sheetNo  ���������(��1��ʼ)
	 * @param beginRow ��ʼ�б��(��1��ʼ)
	 * @param beginCol ��ʼ�б��(��1��ʼ)
	 * @param endCol   �������(��1��ʼ)
	 * @return
	 * @throws Exception <pre>
	 *                   �޸�����        �޸���    �޸�ԭ��
	 *                   2016��3��18��    	    ����    �½�
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
				//���û�е�Ԫ��!
				throw new SystemException("w120");
			}
			if (sheetNo < 1) {
				fis.close();
				//��ȡ���������{0}����ȷ!
				throw new SystemException("w121", sheetNo);
			}
			if (endRow < beginRow) {
				fis.close();
				//��ȡ��¼���Ƿ�����ʼ��[{0}]���ڽ�����[{1}]
				throw new SystemException("w122", beginRow, endRow);
			}
			if (keys.length != (endCol - beginCol + 1)) {
				fis.close();
				throw new SystemException("w126", keys.length, endCol - beginCol + 1);
			}
			HSSFSheet sheet = _workbook.getSheetAt(sheetNo - 1);// ȡ�õ�һ����Ԫ��
			String cellValue = null;
			beginRow--;
			endRow--;
			beginCol--;
			endCol--;
			// ѭ����
			for (int r = beginRow; r <= endRow; r++) {
				map = new HashMap<String, Object>();
				int j = 0;
				// ѭ����
				for (int i = beginCol; i <= endCol; i++) {
					cellValue = String.valueOf(new ExcelImport().getCellValue(sheet.getRow(r).getCell((short) i))).replaceAll(",", "").replaceAll("\"", "").replaceAll("null", "").trim();
					map.put(keys[j], cellValue);
					j++;
				}
				list.add(map);
			}
		} catch (Exception ex) {
			//��ȡ������!
			throw new SystemException("w124", ex);
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				//�ر���ʧ��!
				throw new SystemException("w125", ex);
			}
		}
		return list;
	}

	/**
	 * TODO ��ȡExcelָ����������.
	 *
	 * @param fileName
	 * @param sheetNo
	 * @param rowNum
	 * @param colNum
	 * @return
	 * @throws Exception <pre>
	 *                   �޸�����        �޸���    �޸�ԭ��
	 *                   2016��3��18��    	    ����    �½�
	 *                   </pre>
	 */
	public static String getCellValue(String fileName, int sheetNo, int rowNum, int colNum) throws Exception {
		FileInputStream fis = new FileInputStream(fileName);
		try {
			HSSFWorkbook _workbook = new HSSFWorkbook(fis);
			if (_workbook.getNumberOfSheets() == 0) {
				fis.close();
				//���û�е�Ԫ��!
				throw new SystemException("w120");
			}
			if (sheetNo < 1) {
				fis.close();
				//��ȡ���������{0}����ȷ!
				throw new SystemException("w121", sheetNo);
			}
			if (rowNum < 1) {
				fis.close();
				//��ȡ�кŲ���ȷ!
				throw new SystemException("w127", rowNum);
			}
			if (colNum < 1) {
				fis.close();
				//��ȡ�кŲ���ȷ!
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
			//��ȡ������!
			throw new SystemException("w124", ex);
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				//�ر���ʧ��!
				throw new SystemException("w125", ex);
			}
		}
		return null;
	}

	/**
	 * TODO ���ݹ�������Ŷ�ȡExcel���ݵ�List������.
	 *
	 * @param keys     �������ݵı�������
	 * @param fileName Excel�ļ���
	 * @param sheetNo  ���������(��1��ʼ)
	 * @param beginRow ��ʼ�б��(��1��ʼ)
	 * @param beginCol ��ʼ�б��(��1��ʼ)
	 * @param endCol   �������(��1��ʼ)
	 * @return
	 * @throws Exception <pre>
	 *                   �޸�����        �޸���    �޸�ԭ��
	 *                   2017��2��7��    	   ������  �½�
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
				//���û�е�Ԫ��!
				throw new SystemException("w120");
			}
			if (sheetNo < 1) {
				fis.close();
				//��ȡ���������{0}����ȷ!
				throw new SystemException("w121", sheetNo);
			}
			if (keys.length != (endCol - beginCol + 1)) {
				fis.close();
				throw new SystemException("w126", keys.length, endCol - beginCol + 1);
			}
			HSSFSheet sheet = _workbook.getSheetAt(sheetNo - 1);// ȡ�õ�һ����Ԫ��
			String cellValue = null;
			beginRow--;
			beginCol--;
			endCol--;
			// ѭ����
			for (int r = beginRow; sheet.getRow(r) != null; r++) {
				map = new HashMap<String, Object>();
				int j = 0;
				// ѭ����
				for (int i = beginCol; i <= endCol; i++) {
					cellValue = String.valueOf(new ExcelImport().getCellValue(sheet.getRow(r).getCell((short) i))).replaceAll(",", "").replaceAll("\"", "").replaceAll("null", "").trim();
					map.put(keys[j], cellValue);
					j++;
				}
				list.add(map);
			}
		} catch (Exception ex) {
			//��ȡ������!
			throw new SystemException("w124", ex);
		} finally {
			try {
				fis.close();
			} catch (IOException ex) {
				//�ر���ʧ��!
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
