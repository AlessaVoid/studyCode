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
 * Excel����������
 * 
 * <pre>
 * �޸�����        �޸���    �޸�ԭ��
 * 2014-9-16    	     ����    �½�
 * </pre>
 */
public class ExcelExport {

	/**
	 * 
	 * @param data
	 *            Ҫ����������
	 * @param title
	 *            ���ı���
	 * @param fileName
	 *            ��񱣴��ʵ��·��
	 *            getServletContext().getRealPath("/download/downloadQryResult")
	 *            + File.separator+"a.xsl"
	 * @param path
	 *            ���ص���������·�� "/download/downloadQryResult/" + "a.xsl"
	 * @param arrayHead
	 *            ��ͷ��ʾʱ������
	 * @param arrayData
	 *            �����ֶ�������
	 * @return
	 */
	public String export(List data, String title, String fileName, String path, String[] arrayHead, String[] arrayData,HttpServletResponse response) {
		String sPath = path;
		if (data != null && data.size() > 0) {
			HSSFWorkbook wb = new HSSFWorkbook();// ������HSSFWorkbook����
			HSSFSheet sheet = wb.createSheet("sheet1");// �����µ�sheet����
			HSSFRow titleRow = sheet.createRow((short) 0);// ��������
			HSSFCell titleCell=titleRow.createCell(1);
			titleCell.setCellValue("��Ʒ����������");
			HSSFRow headerRow = sheet.createRow((short) 1);// ��������

		
			// ����ͷ
			for (int i = 0; i < arrayHead.length; i++) {
				HSSFCell csCell = headerRow.createCell((short) ((short) i + 1));
				// csCell.setEncoding(HSSFCell.ENCODING_UTF_16);
				csCell.setCellValue(arrayHead[i]);

			}

			for (int i = 0; i < data.size(); i++) {
				HashMap rowDate = (HashMap) data.get(i);
				HSSFRow row = sheet.createRow((short) (i + 2));// ��������

				HSSFCell csCell = null;
				int m = 0;

				// �������
				csCell = row.createCell((short) m++);
				// csCell.setEncoding(HSSFCell.ENCODING_UTF_16);
				
				// ��������
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

		} else {// ����������Ϊ��
			sPath = "#";
		}

		return sPath;
	}

	/**
	 * 
	 * @param data
	 *            Ҫ����������
	 * @param title
	 *            ���ı���
	 * @param fileName
	 *            ��񱣴��ʵ��·��
	 *            getServletContext().getRealPath("/download/downloadQryResult")
	 *            + File.separator+"a.xsl"
	 * @param path
	 *            ���ص���������·�� "/download/downloadQryResult/" + "a.xsl"
	 * @param arrayHead
	 *            ��ͷ��ʾʱ������
	 * @param arrayData
	 *            �����ֶ�������
	 * @param formatCol
	 *            ��ʽ����
	 * 
	 *            List<Map<String, Object>> list=new ArrayList<Map<String,
	 *            Object>>(); Map<String, Object> map = new HashMap<String,
	 *            Object>(); map.put("xh",new
	 *            Double(350009999999999999999999.22333)); map.put("xm",new
	 *            Date()); list.add(map); String title ="nijjkj"; String str1[]
	 *            ={"ѧ��","����"}; String str2[]={"xh","xm"}; Map<String,FormatCol>
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
			HSSFWorkbook wb = new HSSFWorkbook();// ������HSSFWorkbook����
			HSSFSheet sheet = wb.createSheet("sheet1");// �����µ�sheet����
			wb.setSheetName(0, title);// ��ʾ����
			HSSFRow headerRow = sheet.createRow((short) 0);// ��������

			// �������ͷ
			if (arrayHead.length > 0) {
				HSSFCell csCell = headerRow.createCell((short) 0);
				// csCell.setEncoding(HSSFCell.ENCODING_UTF_16);
				csCell.setCellValue("���");
			}

			// ����ͷ
			for (int i = 0; i < arrayHead.length; i++) {
				HSSFCell csCell = headerRow.createCell((short) ((short) i + 1));
				// csCell.setEncoding(HSSFCell.ENCODING_UTF_16);
				csCell.setCellValue(arrayHead[i]);

			}

			for (int i = 0; i < data.size(); i++) {
				HashMap rowDate = (HashMap) data.get(i);
				HSSFRow row = sheet.createRow((short) (i + 1));// ��������

				HSSFCell csCell = null;
				int m = 0;

				// �������
				csCell = row.createCell((short) m++);
				// csCell.setEncoding(HSSFCell.ENCODING_UTF_16);
				csCell.setCellValue(String.valueOf(i + 1));

				// ��������
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