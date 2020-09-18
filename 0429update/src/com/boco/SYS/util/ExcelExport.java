package com.boco.SYS.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 
 * 
 * Excel导出工具类
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2014-9-16    	     杨滔    新建
 * </pre>
 */
public class ExcelExport {

	/**
	 * 
	 * @param data
	 *            要导出的数据
	 * @param title
	 *            表格的标题
	 * @param fileName
	 *            表格保存的实际路径
	 *            getServletContext().getRealPath("/download/downloadQryResult")
	 *            + File.separator+"a.xsl"
	 * @param path
	 *            返回的下载链接路径 "/download/downloadQryResult/" + "a.xsl"
	 * @param arrayHead
	 *            表头显示时段数组
	 * @param arrayData
	 *            数据字段名数组
	 * @return
	 */
	public String export(List data, String title, String fileName, String path, String[] arrayHead, String[] arrayData,HttpServletResponse response) {
		String sPath = path;
		if (data != null && data.size() > 0) {
			HSSFWorkbook wb = new HSSFWorkbook();// 建立新HSSFWorkbook对象
			HSSFSheet sheet = wb.createSheet("sheet1");// 建立新的sheet对象
			HSSFRow titleRow = sheet.createRow((short) 0);// 建立新行
			HSSFCell titleCell=titleRow.createCell(1);
			titleCell.setCellValue("产品成立参数表");
			HSSFRow headerRow = sheet.createRow((short) 1);// 建立新行

		
			// 生成头
			for (int i = 0; i < arrayHead.length; i++) {
				HSSFCell csCell = headerRow.createCell((short) ((short) i + 1));
				// csCell.setEncoding(HSSFCell.ENCODING_UTF_16);
				csCell.setCellValue(arrayHead[i]);

			}

			for (int i = 0; i < data.size(); i++) {
				HashMap rowDate = (HashMap) data.get(i);
				HSSFRow row = sheet.createRow((short) (i + 2));// 建立新行

				HSSFCell csCell = null;
				int m = 0;

				// 生成序号
				csCell = row.createCell((short) m++);
				// csCell.setEncoding(HSSFCell.ENCODING_UTF_16);
				
				// 生成数据
				for (int j = 0; j < arrayData.length; j++) {
					csCell = row.createCell((short) m++);
					// csCell.setEncoding(HSSFCell.ENCODING_UTF_16);
					String tempValue = rowDate.get(arrayData[j]) == null ? "" : String.valueOf(rowDate.get(arrayData[j]));
					csCell.setCellValue(tempValue);

				}
			}

			try {
				ServletOutputStream  p=response.getOutputStream();
				response.reset();
				response.setHeader("Content-disposition", "attachment; filename=" + new String((fileName + ".xls").getBytes("gbk"), "iso-8859-1"));
				response.setContentType("application/vnd.ms-excel;charset=gbk");
				wb.write(p);
				p.close();
			} catch (Exception e) {
				System.out.println("Exception : " + e.getMessage());
			}

		} else {// 导出的数据为空
			sPath = "#";
		}

		return sPath;
	}

	/**
	 * 
	 * @param data
	 *            要导出的数据
	 * @param title
	 *            表格的标题
	 * @param fileName
	 *            表格保存的实际路径
	 *            getServletContext().getRealPath("/download/downloadQryResult")
	 *            + File.separator+"a.xsl"
	 * @param path
	 *            返回的下载链接路径 "/download/downloadQryResult/" + "a.xsl"
	 * @param arrayHead
	 *            表头显示时段数组
	 * @param arrayData
	 *            数据字段名数组
	 * @param formatCol
	 *            格式话列
	 * 
	 *            List<Map<String, Object>> list=new ArrayList<Map<String,
	 *            Object>>(); Map<String, Object> map = new HashMap<String,
	 *            Object>(); map.put("xh",new
	 *            Double(350009999999999999999999.22333)); map.put("xm",new
	 *            Date()); list.add(map); String title ="nijjkj"; String str1[]
	 *            ={"学号","姓名"}; String str2[]={"xh","xm"}; Map<String,FormatCol>
	 *            map_ = new HashMap<String,FormatCol>(); FormatCol formatCol1 =
	 *            new FormatCol(); formatCol1.setColName("xm");
	 *            formatCol1.setColType("Date");
	 *            formatCol1.setDateFormat("yyyy-MM-dd"); map_.put("xm",
	 *            formatCol1);
	 * 
	 *            FormatCol formatCol2 = new FormatCol();
	 *            formatCol2.setColName("xh"); formatCol2.setColType("Double");
	 *            formatCol2.setDotNumber(2); map_.put("xh", formatCol2);
	 * 
	 *            new
	 *            Excel().export(list,title,"c:/b.xls","http://www.baidu.com"
	 *            ,str1,str2,map_);
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
	}

	public HSSFWorkbook export(List data, String title, String fileName, String[] arrayHead, String[] arrayData) {
		if (data != null && data.size() > 0) {
			HSSFWorkbook wb = new HSSFWorkbook();// 建立新HSSFWorkbook对象
			HSSFSheet sheet = wb.createSheet("sheet1");// 建立新的sheet对象
			wb.setSheetName(0, title);// 显示标题
			HSSFRow headerRow = sheet.createRow((short) 0);// 建立新行

			// 生成序号头
			if (arrayHead.length > 0) {
				HSSFCell csCell = headerRow.createCell((short) 0);
				// csCell.setEncoding(HSSFCell.ENCODING_UTF_16);
				csCell.setCellValue("序号");
			}

			// 生成头
			for (int i = 0; i < arrayHead.length; i++) {
				HSSFCell csCell = headerRow.createCell((short) ((short) i + 1));
				// csCell.setEncoding(HSSFCell.ENCODING_UTF_16);
				csCell.setCellValue(arrayHead[i]);

			}

			for (int i = 0; i < data.size(); i++) {
				HashMap rowDate = (HashMap) data.get(i);
				HSSFRow row = sheet.createRow((short) (i + 1));// 建立新行

				HSSFCell csCell = null;
				int m = 0;

				// 生成序号
				csCell = row.createCell((short) m++);
				// csCell.setEncoding(HSSFCell.ENCODING_UTF_16);
				csCell.setCellValue(String.valueOf(i + 1));

				// 生成数据
				for (int j = 0; j < arrayData.length; j++) {
					csCell = row.createCell((short) m++);
					// csCell.setEncoding(HSSFCell.ENCODING_UTF_16);
					String tempValue = rowDate.get(arrayData[j]) == null ? "" : String.valueOf(rowDate.get(arrayData[j]));
					csCell.setCellValue(tempValue);
				}
			}
			return wb;
		}
		return null;
	}

	public static void exportData(String[] tableTitles, String[][] tableContents, HttpServletResponse response) {
		try {
			WritableSheet sheet = null;
			WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());
			if (workbook != null) {
				sheet = workbook.createSheet("sheet1", 0);
				for (int i = 0; i < tableTitles.length; i++) {
					sheet.addCell(new Label(i, 0, tableTitles[i]));
				}
			}
			for (int i = 0; i < tableContents.length; i++) {
				int row = i + 1;
				if (tableTitles.length != tableContents[i].length) {
				}
				for (int j = 0; j < tableContents[i].length; j++) {
					sheet.addCell(new Label(j, row, tableContents[i][j]));
				}
			}
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}