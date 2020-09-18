package com.boco.RE.excel.util;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Excel-Workbook 工厂类
 * @Author zhuhongjiang
 * @Date 2020/1/7 下午3:46
 **/
public class ExcelFactory {
	private Workbook workbook;
	private static String fileExtension = "excel2003";	//默认格式为2003版本
	
	public ExcelFactory(String importFilePath){
		File importFile = null;
		FileInputStream fis = null;
		try {
			importFile = new File(importFilePath);
			if(importFile != null){
				fis = new FileInputStream(importFile);
				this.workbook = WorkbookFactory.create(fis);
				fileExtension = validateExcelStyle(importFilePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {if (fis != null){fis.close();}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public ExcelFactory(InputStream is) throws Exception{
		FileInputStream fis = null;
		try {
			this.workbook = WorkbookFactory.create(is);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			try {if (fis != null){fis.close();}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public ExcelFactory(File importFile){
		FileInputStream fis = null;
		try {
			if(importFile != null){
				fis = new FileInputStream(importFile);
				this.workbook = WorkbookFactory.create(fis);
				fileExtension = validateExcelStyle(importFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {if (fis != null){fis.close();}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Workbook getWorkbook(){
		return this.workbook;
	}

	/**
	 * @param content 内容
	 * @return 指定EXCEL版本的填充 内容对象
	 */
	public RichTextString getRichTextString(String content){
		if(Constants.EXCEL_2003.equals(fileExtension)){
			return new HSSFRichTextString(content);
		}
		if(Constants.EXCEL_2007.equals(fileExtension)){
			return new XSSFRichTextString(content);
		}
		return new XSSFRichTextString(content);
	}
	
	/**
	 * 方法说明: 根据文件全路径判断EXCEL版本
	 * @param importFilePath 文件路径
	 * @return EXCEL 版本
	 */
	public String validateExcelStyle(String importFilePath){
		FileInputStream fis = null ;
		 BufferedInputStream bis = null ;
		 try {
			 fis = new FileInputStream(importFilePath);
			 bis = new BufferedInputStream(fis);
			 if(POIFSFileSystem.hasPOIFSHeader(bis)) { //Excel版本为excel2003及以下
				 return Constants.EXCEL_2003;
			 }
			 if(POIXMLDocument.hasOOXMLHeader(bis)) {  //Excel版本为excel2007及以上
				 return Constants.EXCEL_2007;
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {if (fis != null){fis.close();}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {if (bis != null){bis.close();}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Constants.EXCEL_2003 ;
		
	}
	/**
	 * 方法说明: 根据文件判断EXCEL版本
	 * @param importFile 文件
	 * @return EXCEL 版本
	 */
	public String validateExcelStyle(File importFile){
		FileInputStream fis = null ;
		 BufferedInputStream bis = null ;
		 try {
			 fis = new FileInputStream(importFile);
			 bis = new BufferedInputStream(fis);
			 if(POIFSFileSystem.hasPOIFSHeader(bis)) { //Excel版本为excel2003及以下
				 return Constants.EXCEL_2003;
			 }
			 if(POIXMLDocument.hasOOXMLHeader(bis)) {  //Excel版本为excel2007及以上
				 return Constants.EXCEL_2007;
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {if (fis != null){fis.close();}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {if (bis != null){bis.close();}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Constants.EXCEL_2003 ;
	}

	public static void main(String[] args) throws IOException, InvalidFormatException {
		String path = "/home/fxjk/software/tomcat7/tomcat7-8080/webapps/web/libs/excel/rule/RulePsbcRmbBusi.xlsx";
		FileInputStream fi = new FileInputStream(path);
		Workbook workbook = WorkbookFactory.create(fi);
	}
}
