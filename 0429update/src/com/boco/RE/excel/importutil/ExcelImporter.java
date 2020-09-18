package com.boco.RE.excel.importutil;

import com.boco.RE.excel.util.CommonUtil;
import com.boco.RE.excel.util.Constants;
import com.boco.RE.excel.util.ExcelFactory;
import com.boco.RE.excel.util.ExcelUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Excel���������
 * @Author zhuhongjiang
 * @Date 2020/1/7 ����4:14
 **/
public class ExcelImporter {

	private File templateFile;
	private File importFile;
	private ExcelFactory excelFactory;
	private Workbook workbook;
	private CellStyle errorStyle;
	private List blankDetailLineErrorList = new ArrayList(); 				//������ʱ����հ���ϸ��Ϣ�еĴ�����Ϣ
	private ImportedExcelData importedExcelData = new ImportedExcelData(); 	//���ڽ�������������ݷ��ظ�������
	private Map headTitleRuleMap; 											//���ڱ����ģ���ж�ȡ������ͷ��Ϣ������֤����
	private Map detailTitleRuleMap; 										//���ڱ����ģ���ж�ȡ��������ϸ��Ϣ������֤����
	private Map headDataRuleMap; 											//���ڱ����ģ���ж�ȡ������ͷ��Ϣ������֤����
	private Map detailDataRuleMap; 											//���ڱ����ģ���ж�ȡ��������ϸ��Ϣ������֤����
	//���ȫ�ֲ�������is-multi-sheet-rule=true,��Ὣģ����ͷ�б��⡢������֤���򱣴�
	private List<Map> headTitleRuleList , headDataRuleList , detailTitleRuleList ,detailDataRuleList ;
	private TemplateValidateRule templateValidateRule = new TemplateValidateRule(); //����ģ�����֤����ȫ������
	private boolean existStringCellWithNumberFormat = false; 						//����ָʾ�Ƿ����String�������ݵĵ�Ԫ�����ó�����ֵ��ʽ�����
	private String userId = ""; 		//�����û��ĵ�¼�ʺţ�������ҳ�洫�ݽ���
	private int currentSheetId = 0; 	//��ǰ���ڶ�ȡ��Sheet���
	private int currentRowId = 0; 		//��ǰ���ڶ�ȡ�ĵ�Ԫ���������к�
	private short currentColumnId = 0; 	//��ǰ���ڶ�ȡ�ĵ�Ԫ���������к�

	private List<Object> listForSort = new ArrayList<>();
	private List errorList = new ArrayList();					//���д�����Ϣ����
	private Map<Object,Object> errorRowMap = new HashMap<>(); 	//����Ϊ��λ������д���
	private Map<Object,Object> errorMap = new HashMap<>();		//����Ϊ��λ������д���
    private Set<Integer> lineErrorList= new HashSet<>(); 		//������Ϣ��¼�к�  by zhuhj


	/**
	 * ExcelImporter����
	 * @param templateFilePath	ģ���ļ�·��
	 * @param importFilePath	�ϴ��ļ�·��
	 */
	public ExcelImporter(String templateFilePath, String importFilePath) {
		File importFile = null;
		FileInputStream fis = null;
		try {
			importFile = new File(importFilePath);
			if(importFile != null){
				fis = new FileInputStream(importFile);
				this.excelFactory = new ExcelFactory(importFilePath);
				this.workbook = excelFactory.getWorkbook();
				this.importFile = importFile;
				this.templateFile = new File(templateFilePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public ExcelImporter(String templateFilePath, MultipartFile file) {
		InputStream fis = null;
		try {
			if(file != null){
				fis = file.getInputStream();
				// ��Ҫ�����EXCEL�ļ���������һ��workbook
				excelFactory = new ExcelFactory(fis);
				workbook = excelFactory.getWorkbook();	//����������ȡWorkBook����
				// �ɸ�����ģ���ļ�·������һ��ģ���ļ�����
				this.templateFile = new File(templateFilePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ִ�е���EXCEL�Ĺ��̣���������ɹ������ݱ��浽Map�У�����д��󣬽���ʾ��Ϣ���浽errorList��
	 * @return
	 */
	public boolean importExcel(){
		//1:�ж�ģ���ļ��Ƿ����
		if(this.workbook == null){
			this.errorList.add(Constants.UNKNOWN_FORMAT_EXISTS_IN_UPLOAD_FILE);
			return false;
		}
		if(this.templateFile == null){
			this.errorList.add(Constants.TEMPLATE_FILE_ERROR_ON_SERVER_PLEASE_CONTACT_ADMIN);
			return false;
		}
		//2�������������֤ģ����֤����
		boolean ruleLoaded = this.loadValidateRules();
		if(!ruleLoaded){
			this.errorList.add(Constants.TEMPLATE_FILE_ERROR_ON_SERVER_PLEASE_CONTACT_ADMIN);
			return false;
		}
		//3: ���سɹ�ģ����֤����,ѭ��������֤�ϴ��ļ���ģ��һ���Բ���ȡ�ļ���SHEET����
		// �趨������ʾ�ĵ�Ԫ���ʽ
		this.errorStyle = this.workbook.createCellStyle();
		this.errorStyle = ExcelUtil.configErrorStyle(this.errorStyle, ExcelUtil.FGColor_Red);
		
		int sheetCountForImport = 1;
		// ���ģ����������EXCEL�ļ�����ʱΪ���sheet�����룬��ѭ���������sheet
		if(this.templateValidateRule.isMultiSheet()){
			sheetCountForImport = this.workbook.getNumberOfSheets();
		}
		
		boolean allSheetsImported = true; // ��ʾ�Ƿ�����sheet�����ݶ�����ɹ��ı�־
		
		// ѭ�������EXCEL�ļ�����sheet������
		// �̶��ӵ�һ��sheet��ʼ���룬����Ƕ�sheet���룬��ѭ���������е�sheet
		// �Ժ�Ľ�ʱ��������һ������ѡ�����ָ��Ҫ�����sheet��Χ������ָ������sheet�ı�Ż����ƣ���ָ��sheet��ŵķ�Χ
		for(int i = 0; i < sheetCountForImport; i++ ){
			Sheet sheet = this.workbook.getSheetAt(i);
			this.importedExcelData.addNameOfSheet(this.workbook.getSheetName(i)); // �ѽ�Ҫ�����Sheet���Ʊ��浽importedExcelData��
			this.currentSheetId = i;
			//3.1: ��֤�ϴ��ļ�һ����
			if(this.templateValidateRule.isValidateHeadTitleEnabled()){
				// ��֤EXCEL��ͷ��Ϣ���⣬����д�˵���ϴ���EXCEL�ļ���ģ�岻������������
				if(this.templateValidateRule.isMultiSheetRule()){
					this.headTitleRuleMap = this.headTitleRuleList.get(i);
					this.headDataRuleMap = this.headDataRuleList.get(i);
				}
				if(!validateTitle(this.headTitleRuleMap, sheet)){
					this.errorList.add(Constants.UPLOAD_FILE_NOT_ACCORD_WITH_TEMPLATE); 
					return false;
				}
			}
			
			if(this.templateValidateRule.isValidateDetailTitleEnabled()){
				// ��֤EXCEL����ϸ��Ϣ���⣬����д�˵���ϴ���EXCEL�ļ���ģ�岻������������
				if(this.templateValidateRule.isMultiSheetRule()){
					this.detailTitleRuleMap = this.detailTitleRuleList.get(i);
					this.detailDataRuleMap = this.detailDataRuleList.get(i);
				}
				if(!validateTitle(this.detailTitleRuleMap, sheet)){
					this.errorList.add(Constants.UPLOAD_FILE_NOT_ACCORD_WITH_TEMPLATE); 
					return false;
				}
			}
			//3.2: �����ϴ�SHEET�ļ�
			boolean sheetImported = this.readDataFromSheet(sheet);
			if(!sheetImported){
				allSheetsImported = false;
			}
		}
		
		if(allSheetsImported && this.errorList.size() == 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * ��һ��Sheet�е������ݣ�������ȡ�ɹ������ݴ���importedExcelData������
	 */
	private boolean readDataFromSheet(Sheet sheet){
		boolean result = true;
		//����ͷ��Ϣ
		if(this.readHeadDataFromSheet(sheet) == false){
			result = false;
		}
		//��������Ϣ
		if(readDetailDataFromSheet(sheet) == false){
			result = false;
		}
		
		return result;
	}
	
	/**
	 * 
	 * ����˵��: ��һ��Sheet�е���ͷ��Ϣ���ݣ�������ȡ�ɹ������ݴ���importedExcelData������
	 * @param sheet
	 * @return
	 */
	private boolean readHeadDataFromSheet(Sheet sheet){
		if(sheet == null){
			return false;
		}
		
		boolean result = true;
		Map headDataMap = new HashMap();
		Object cellValue = null;
		
		if(this.headDataRuleMap == null || this.headDataRuleMap.size() == 0){
			return true;
		}
		//ѭ����ȡͷ��Ϣ����
		Set ruleEntrySet = this.headDataRuleMap.entrySet();
		Iterator it = ruleEntrySet.iterator();
		while(it.hasNext()){
			Map.Entry ruleEntry = (Map.Entry)it.next();
			CellValidateRule rule = (CellValidateRule)ruleEntry.getValue();
			if(rule == null){
				continue;
			}
			
			this.currentRowId = rule.getRowId();		//��λ����
			this.currentColumnId = rule.getColumnId();	//��λ����
			
			Row row = sheet.getRow(rule.getRowId());
			if(row == null){
				continue;
			}
			
			Cell cell = row.getCell(rule.getColumnId());
			
			//���»�ȡ��
			if(cell == null){
				cell = row.createCell(rule.getColumnId());
			}
			cellValue = null;
			
			if(this.templateValidateRule.isValidateDataEnabled()){
				if(this.validateCellValue(rule, cell) == true){
					cellValue = this.getCellValue(rule, cell);
				}
				else{
					lineErrorList.add(this.currentRowId+1);
					result = false;
				}
			}
			else{
				cellValue = this.getCellValue(rule, cell);
			}
			
			headDataMap.put(rule.getCellName(), cellValue);
		}
		
		this.importedExcelData.addHeadDataOfSheet(headDataMap);
		
		return result;
	}
	
	/**
	 * ��һ��Sheet�е�����ϸ��Ϣ���ݣ�������ȡ�ɹ������ݴ���importedExcelData������
	 */
	private boolean readDetailDataFromSheet(Sheet sheet){
		if(sheet == null){
			return false;
		}
		//System.out.println("readDetailDataFromSheet sheetName :"+sheet.getSheetName());
		boolean result = true;
		int lineId = 0; // ��¼��ǰ��������ϸ��Ϣ�ǵڼ��У��У�
		boolean thisSheetHasDetailLine = false;
		boolean allLinesPassedValidation = true;
		List dataLinesOfSheet = new ArrayList();
		Object cellValue = null;
		//System.out.println("-------------detailDataRuleMap"+com.alibaba.fastjson.JSON.toJSONString(this.detailDataRuleMap));
		//1: ѭ����ȡ������ϸ��Ϣ�����ݣ�ֱ��������EXCEL�ļ����������λ��
		while( ! this.isAllDetailCellReachPhysicalEnd(sheet, lineId, this.detailDataRuleMap)){
			Map lineDataMap = new HashMap();
			boolean currentLinePassedValidation = true;
			//2: �жϵ�������������е�Ԫ��Ϊ�հ�
			if(this.isBlankDetailLine(sheet, lineId, this.detailDataRuleMap)){
				//2.1�����е�Ԫ��Ϊ�հף�����ݵ�����֤������ȷ���Ƿ񱨴�
				if( ! this.templateValidateRule.isBlankDetailLineAllowed()){
//					String errorMsg = "Excel�ļ��е�" + (this.currentSheetId + 1) + "��Sheet\"" 
//						+ this.workbook.getSheetName(this.currentSheetId) + "\" �ĵ�" + (lineId + 1) + "����ϸ��Ϣδ��д�κ���Ч���ݣ��뽫������д�����򽫸��У��У�ɾ����";
					
					String errorMsg = "Excel\u6587\u4ef6\u4e2d\u7b2c" + (this.currentSheetId + 1) + "\u4e2aSheet\"" 
					+ this.workbook.getSheetName(this.currentSheetId) + "\" \u7684\u7b2c" + (lineId + 1) + "\u6761\u8be6\u7ec6\u4fe1\u606f\u672a\u586b\u5199\u4efb\u4f55\u6709\u6548\u6570\u636e\uff0c\u8bf7\u5c06\u6570\u636e\u586b\u5199\u5b8c\u6574\u6216\u5c06\u8be5\u884c\uff08\u5217\uff09\u5220\u9664\uff01";
					if(! this.blankDetailLineErrorList.contains(errorMsg)){
						this.blankDetailLineErrorList.add(errorMsg);
					}
				}
			}else{
				//3��������ж����ǿհ��У���֮ǰ�Ѿ����ֵĿհ��д�����ʾ��Ϣ�ӻ���List����������errorList�У���ֹĳ��EXCELĩβ��������ʱҲ��ʾ����
				if(this.blankDetailLineErrorList != null && this.blankDetailLineErrorList.size() > 0){
					this.errorList.addAll(this.blankDetailLineErrorList);
					this.blankDetailLineErrorList.clear(); // Ȼ��ѿհ��д�����ʾ��Ϣ�Ļ���List���
				}
				
				// �����Ҫ������в������е�Ԫ��ȫ��Ϊ�հף���ѭ����ȡ�����и���detail-data-cell��ֵ
				currentLinePassedValidation = true; // ��ʼ�������Ƿ�ȫ��ͨ����֤�ı�־
				thisSheetHasDetailLine = true;
				
				Set ruleEntrySet = this.detailDataRuleMap.entrySet();
				Iterator it = ruleEntrySet.iterator();
				while(it.hasNext()){
					Map.Entry ruleEntry = (Map.Entry)it.next();
					CellValidateRule rule = (CellValidateRule)ruleEntry.getValue();
					if(rule == null){
						//System.out.println("rule == null"); // (*)
						continue;
					}
					
					//System.out.println("reading data for " + rule.getCellName());
	
					// ���ݸ��ֶε���֤���򡢵�ǰ��������кţ������Ҫ��ȡ�ĵ�Ԫ�����
					Cell cell = this.getDetailCellOfSheet(sheet, lineId, rule);
					cellValue = null; //
					
					if(this.templateValidateRule.isValidateDataEnabled()){
						if(this.validateCellValue(rule, cell) == true){
							cellValue = this.getCellValue(rule, cell);
						}
						else{
							lineErrorList.add(this.currentRowId+1);
							result = false;
							currentLinePassedValidation = false;
							
							//System.out.println("cell not passed validation!"); // (*)
						}
					}
					else{
						cellValue = this.getCellValue(rule, cell);
					}
					
					lineDataMap.put(rule.getCellName(), cellValue);
					//System.out.println(rule.getCellName() + " = " + cellValue); //(*)
				}
				
				// ������������е�Ԫ���ͨ����֤���ͱ�������
				//if(currentLinePassedValidation){ (*)
					lineDataMap.put("lineId", this.currentRowId+1);//�����к�  byzhuhj
					dataLinesOfSheet.add(lineDataMap);
				//} (*)
			}
			
			lineId++;
		}
		
		if( ! allLinesPassedValidation){
			result = false;
		}
		
		if(this.templateValidateRule.isDetailMustExsit() && thisSheetHasDetailLine == false){
//			this.errorList.add("Excel�ļ��е�" + (this.currentSheetId + 1) + "��Sheet\"" 
//					+ this.workbook.getSheetName(this.currentSheetId) + "\" û���κ���ϸ����Ϣ��");
			this.errorList.add("Excel\u6587\u4ef6\u4e2d\u7b2c" + (this.currentSheetId + 1) + "\u4e2aSheet\"" 
					+ this.workbook.getSheetName(this.currentSheetId) + "\" \u6ca1\u6709\u4efb\u4f55\u8be6\u7ec6\u884c\u4fe1\u606f\uff01");
		}
		
		this.importedExcelData.addDetailDataOfSheet(dataLinesOfSheet);
		
		return result;
	}
	
	/**
	 * �õ���Sheet��Ŀǰ��Ҫ��ȡ��һ����ϸ��Ϣ��ĳ����Ԫ����к�
	 */
	private int getDetailCellRowId(int lineId, CellValidateRule rule){
		if(lineId < 0 || rule == null){
			return -1;
		}

		int rowId = rule.getRowId(); // ���ֶε���ʼ�к�
		
		String validRange = rule.getValidRange();
		if(validRange == null || "".equals(validRange.trim())){
			rowId = -1;
		}
		else if("this".equals(validRange) && lineId > 0){
			rowId = -1;
		}
		else if("all".equals(validRange)){
			rowId = rowId + lineId;
		}
		else if("row+all".equals(validRange)){
			rowId = rowId + lineId;
		}
		else if("row-all".equals(validRange)){
			rowId = rowId - lineId;
		}
		else if(validRange.indexOf("row+") >= 0){
			int n = 0;
			
			try{
				n = Integer.parseInt(validRange.substring(validRange.indexOf("row+") + "row+".length()));
			}
			catch(Exception e){
			}
			
			if(lineId <= n){
				rowId = rowId + lineId;
			}
			else{
				rowId = -1;
			}
		}
		else if(validRange.indexOf("row-") >= 0){
			int n = 0;
			
			try{
				n = Integer.parseInt(validRange.substring(validRange.indexOf("row-") + "row-".length()));
			}
			catch(Exception e){
			}
			
			if(lineId <= n){
				rowId = rowId - lineId;
			}
			else{
				rowId = -1;
			}
		}
		
		// �ж��Ƿ񳬹���EXCEL���������к�
		if(rowId > ExcelUtil.MAX_ROW_ID_IN_SHEET){
			rowId = -1;
		}
		
		return rowId;
	}
	
	/**
	 * �õ���Sheet��Ŀǰ��Ҫ��ȡ��һ����ϸ��Ϣ��ĳ����Ԫ����к�
	 */
	private short getDetailCellColumnId(int lineId, CellValidateRule rule){
		if(lineId < 0 || rule == null){
			return -1;
		}

		short columnId = rule.getColumnId();
		
		String validRange = rule.getValidRange();
		if(validRange == null || "".equals(validRange.trim())){
			columnId = -1;
		}
		else if("this".equals(validRange) && lineId > 0){
			columnId = -1;
		}
		else if("all".equals(validRange)){
			columnId = (short)(columnId + lineId);
		}
		else if("column+all".equals(validRange)){
			columnId = (short)(columnId + lineId);
		}
		else if("column-all".equals(validRange)){
			columnId = (short)(columnId - lineId);
		}
		else if(validRange.indexOf("column+") >= 0){
			int n = 0;
			
			try{
				n = Integer.parseInt(validRange.substring(validRange.indexOf("column+") + "column+".length()));
			}
			catch(Exception e){
			}
			
			if(lineId <= n){
				columnId = (short)(columnId + lineId);
			}
			else{
				columnId = -1;
			}
		}
		else if(validRange.indexOf("column-") >= 0){
			int n = 0;
			
			try{
				n = Integer.parseInt(validRange.substring(validRange.indexOf("column-") + "column-".length()));
			}
			catch(Exception e){
			}
			
			if(lineId <= n){
				columnId = (short)(columnId - lineId);
			}
			else{
				columnId = -1;
			}
		}
		
		// �ж��Ƿ񳬹���EXCEL���������к�
		if(columnId > ExcelUtil.MAX_COLUMN_ID_IN_SHEET){
			columnId = -1;
		}
		
		return columnId;
	}
	
	/**
	 * �õ���Sheet��Ŀǰ��Ҫ��ȡ��һ����ϸ��Ϣ�е�ĳ����Ԫ��
	 */
	private Cell getDetailCellOfSheet(Sheet sheet, int lineId, CellValidateRule rule){
		if(sheet == null || lineId < 0 || rule == null){
			//System.out.println("Detail No." + lineId + " of " + rule.getCellName() + "is empty because sheet == null || lineId < 0 || rule == null."); // (*)
			return null;
		}

		int rowId = this.getDetailCellRowId(lineId, rule);
		short columnId = this.getDetailCellColumnId(lineId, rule);
		if(rowId < 0 || columnId < 0){
			//System.out.println("Detail No." + lineId + " of " + rule.getCellName() + "is empty because rowId < 0 || columnId < 0"); // (*)
			return null;
		}
		
		this.currentRowId = rowId;
		this.currentColumnId = columnId;

		Row row = sheet.getRow(rowId);
		Cell cell = null;
		if(row != null){
			cell = row.getCell(columnId);
			if(cell == null){
				cell = row.createCell(columnId); //
			}
		}
		
		/*
		if(cell == null){
			System.out.println("getDetailCellOfSheet, rowId:" + rowId + " columnId:" + columnId + ", cell is null"); // (*)
		}
		else{
			System.out.println("getDetailCellOfSheet, rowId:" + rowId + " columnId:" + columnId + ", cell is not null"); // (*)
		}
		*/
		
		return cell;
	}
	
	/**
	 * ������֤����
	 * �ӹ��췽�����ݽ�����ģ���ļ��ж�ȡ��ص���֤��������
	 */
	private boolean loadValidateRules(){
		//1:���ģ���ļ�δָ�������޷�������֤����
		if(this.templateFile == null){
			return false;
		}
		
		//2: ����ģ���ļ�����չ����ȷ���ú��ֹ������ʵ��������ȡ��֤����
		String templateFileName = this.templateFile.getAbsolutePath();
		if(templateFileName == null || "".equals(templateFileName) || templateFileName.indexOf(".") == -1){
			return false;
		}
		
		ValidateRuleLoader ruleLoader = null; //������֤����Ĺ�����
		
		String fileExt = templateFileName.substring(templateFileName.lastIndexOf(".")+1);
		if(Constants.FILE_XLS.equalsIgnoreCase(fileExt) || Constants.FILE_XLSX.equalsIgnoreCase(fileExt)){
			ruleLoader = new ValidateRuleExcelLoader();
		}else{
			this.errorList.add(Constants.UNKNOWN_VALIDATE_RULE_TEMPLATE_FILE_FORMAT);
			return false;
		}
		//3:��֤������ģ���еĹ���(ȫ������/������뵥Ԫ������)
		boolean ruleLoaded = ruleLoader.loadValidateRules(this.templateFile);
		if(!ruleLoaded){
			// �ڿ���̨��ӡ������֤����������������Ĵ�����ʾ��Ϣ
//			List loadRuleErrors = ruleLoader.getErrorList();
//			if(loadRuleErrors != null && loadRuleErrors.size() > 0){
//				for(int i = 0; i < loadRuleErrors.size(); i++){
//					System.out.println("error when loading validate rule: " + loadRuleErrors.get(i));
//				}
//			}
			return false;
		}

		// 1.��ȡ����ģ�����֤����ȫ�����ã���װ��TemplateValidateRule����
		this.templateValidateRule = ruleLoader.getTemplateValidateRule();
		// 2.��ȡͷ��Ϣ�������֤����
		this.headTitleRuleMap = ruleLoader.getHeadTitleRuleMap();
		// 3.��ȡ��ϸ��Ϣ�������֤����
		this.detailTitleRuleMap = ruleLoader.getDetailTitleRuleMap();
		// 4.��ȡͷ��Ϣ���ݵ���֤����
		this.headDataRuleMap = ruleLoader.getHeadDataRuleMap();
		// 5.��ȡ��ϸ��Ϣ���ݵ���֤����
		this.detailDataRuleMap = ruleLoader.getDetailDataRuleMap();
		// 6.������ö��SHEET����,��ȡ
		this.headTitleRuleList = ruleLoader.getHeadTitleRuleList();
		this.headDataRuleList = ruleLoader.getHeadDataRuleList();
		this.detailTitleRuleList = ruleLoader.getDetailTitleRuleList() ;
		this.detailDataRuleList = ruleLoader.getDetailDataRuleList() ;
		
		return true;
	}
	
	/**
	 * �õ���EXCEL��������ݣ��������ʱ�д����򷵻�null
	 */
	public ImportedExcelData getImportedExcelData(){
		if(this.errorList == null || this.errorList.size() == 0){
			return this.importedExcelData;
		}
		else{
			return null;
		}
	}
	
	/**
	 * �ƿ�����������
	 * add by avent
	 */
	public ImportedExcelData getImportedOriExcelData(){
		return this.importedExcelData;
	}

	/**
	 * 
	 * ����˵��: ��֤Ҫ�����EXCEL�ļ��еı�����ģ���е��Ƿ�һ��
	 * @param ruleMap ģ���еı���
	 * @param sheet ����EXCEL ��SHEET
	 * @return ��֤���
	 */
	private boolean validateTitle(Map ruleMap, Sheet sheet){
		if(sheet == null){
			return false;
		}
		
		if(ruleMap == null || ruleMap.size() == 0){
			return true;
		}
		
		boolean result = true;
		//����ģ���еı��⼯��
		Set ruleEntrySet = ruleMap.entrySet();
		Iterator it = ruleEntrySet.iterator();
		while(it.hasNext()){
			Map.Entry ruleEntry = (Map.Entry)it.next();
			//��ȡ��������Ϣ(��ID����ID����Ϣ����)
			CellValidateRule rule = (CellValidateRule)ruleEntry.getValue();
			
			Row row = sheet.getRow(rule.getRowId()); //����ģ����ID��λ������EXCEL��
			if(row == null){
				result = false;
				break;
			}

			Cell cell = row.getCell(rule.getColumnId());//����ģ����ID��λ�������е���
			//�����Ӧ���в������򱨴�
			if(cell == null){
				result = false;
				cell = row.createCell(rule.getColumnId());
				cell.setCellStyle(this.errorStyle);
				break;
			}//���ڶ�Ӧ������У������Ƿ���ͬ
			else{
				String cellValueStr = ExcelUtil.getStringCellValue(cell);
				if(rule.getCellTitle() != null){
					if(cellValueStr == null || ! cellValueStr.equals(rule.getCellTitle())){
						result = false;
						cell.setCellStyle(this.errorStyle);
						break;
					}
				}
				else{
					if(cellValueStr != null && ! "".equals(cellValueStr)){
						result = false;
						cell.setCellStyle(this.errorStyle);
						break;
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * �õ���س�����Ϣ��List
	 */
	public List getErrorList(){
		return this.errorList;
	}
	
	/**
	 * �õ���س�����Ϣ��String
	 * ����errorList�е�������Ϣƴ�����������»���_�ָ���
	 */
	public String getErrorString(){
		Set s = errorMap.keySet();
		Iterator its = s.iterator();
		this.listForSort = new ArrayList<Object>();
		while(its.hasNext()){
			listForSort.add((Integer)its.next());
		}
	   Collections.sort(listForSort, null);
	   List errorSortList = new ArrayList();	//�ϲ����еĴ�����Ϣ���������У���µĴ�����Ϣ
	   errorSortList.addAll(this.errorList);
		for(Object i :listForSort){
			errorSortList.add(errorMap.get(i));
		}
		Iterator it = errorSortList.iterator();
		StringBuffer sb = new StringBuffer();
		
		while(it.hasNext()){
			String str = (String)it.next();
			sb.append(str);
			if(it.hasNext()){
				sb.append("<br>");
			}
		}
		return sb.toString();
	}
	public String getErrorRowString(){
		Set s = errorRowMap.keySet();
		Iterator its = s.iterator();
	    List<Object> listRowForSort = new ArrayList<Object>();
		while(its.hasNext()){
			listRowForSort.add((Integer)its.next());
		}
	   Collections.sort(listRowForSort, null);
	   List errorRowList = new ArrayList();
	   errorRowList.addAll(this.errorList);
		for(Object i :listRowForSort){
			errorRowList.add(errorRowMap.get(i));
		}
		Iterator it = errorRowList.iterator();
		StringBuffer sb = new StringBuffer();
		
		while(it.hasNext()){
			String str = (String)it.next();
			sb.append(str);
			if(it.hasNext()){
				sb.append("<br>");
			}
		}
		return sb.toString();
	}
	
	/**
	 * �õ�WorkBook
	 */
	public Workbook getWorkbook() {
		return this.workbook;
	}

	/**
	 * �жϸ�EXCEL����ĳһline��ϸ��Ϣ���������Ƿ�ȫ��Ϊ��
	 * ���������Ӧ�����е���ϸ��Ϣ��Ԫ����ϳ�һ��line�ķ�ʽ
	 */
	private boolean isBlankDetailLine(Sheet sheet, int lineId, Map detailRuleMap){
		if(sheet == null || lineId < 0 || detailRuleMap == null || detailRuleMap.size() == 0){
			return true;
		}
		
		boolean result = true;
		
		Set ruleEntrySet = detailRuleMap.entrySet();
		Iterator it = ruleEntrySet.iterator();
		while(it.hasNext()){
			Map.Entry ruleEntry = (Map.Entry)it.next();
			CellValidateRule rule = (CellValidateRule)ruleEntry.getValue();

			Cell cell = this.getDetailCellOfSheet(sheet, lineId, rule);
			if(cell != null){
				String cellValueStr = ExcelUtil.getStringCellValue(cell);
				if(cellValueStr != null && ! "".equals(cellValueStr.trim())){
					//System.out.println("cell Value: " + cellValueStr + " ,so Line " + lineId + " is not a blank detail line.");
					result = false;
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * �ж�Ŀǰ�Ƿ��Ѷ�����EXCEL�ļ����������λ�ã����ھ����Ƿ�ֹͣ��ȡ��ϸ��Ϣ
	 * ���������Ӧ�����е���ϸ��Ϣ��Ԫ����ϳ�һ��line�ķ�ʽ
	 */
	private boolean isAllDetailCellReachPhysicalEnd(Sheet sheet, int lineId, Map detailRuleMap){
		if(sheet == null || lineId < 0 || detailRuleMap == null || detailRuleMap.size() == 0){
			return true;
		}
		if(this.isBlankDetailLine(sheet, lineId, this.detailDataRuleMap)){ //�������У��˳�ѭ��(�ɼ����ÿ���)   add by zhuhongjiang
			return true;
		}
		
		boolean result = true;
		
		Set ruleEntrySet = detailRuleMap.entrySet();
		Iterator it = ruleEntrySet.iterator();
		while(it.hasNext()){
			Map.Entry ruleEntry = (Map.Entry)it.next();
			CellValidateRule rule = (CellValidateRule)ruleEntry.getValue();
			
			int rowId = this.getDetailCellRowId(lineId, rule);
			short columnId = this.getDetailCellColumnId(lineId, rule);
			
			//System.out.println("judge is Detail Cell Reach Physical End, rowId:" + rowId + " columnId:" + columnId); //(*)
			
			if(rowId >= 0 && rowId <= sheet.getLastRowNum() && columnId >= 0){
				Row row = sheet.getRow(rowId);
				if(row == null || columnId <= row.getLastCellNum()){
					result = false;
//					System.out.println("its not physical end because rowId < "+sheet.getLastRowNum() +"and columnId < "+row.getLastCellNum()); // 
					break;
				}
			}
		}

		return result;
	}
	
	/**
	 * ��֤�����Ĺ����������Ӧ�ĵ�Ԫ�������
	 */
	private boolean validateCellValue(CellValidateRule rule, Cell cell){
		boolean result = true;
		
		String cellValue = ExcelUtil.getStringCellValue(cell);
		
		StringBuffer sb = new StringBuffer(); // ��ʾ��Ϣ�е�sheet���С��С��ֶ�������Ϣ
//		sb.append("Excel�ļ��е�");
//		sb.append(this.currentSheetId + 1);
//		sb.append("��Sheet\"");
//		sb.append(this.workbook.getSheetName(this.currentSheetId));
//		sb.append("\" ��");
//		sb.append(this.currentRowId + 1);
//		sb.append("�� ��");
//		sb.append(this.currentColumnId + 1);
//		sb.append("�е�");
		sb.append("Excel\u6587\u4ef6\u4e2d\u7b2c");
		sb.append(this.currentSheetId + 1);
		sb.append("\u4e2aSheet\"");
		sb.append(this.workbook.getSheetName(this.currentSheetId));
		sb.append("\" \u7b2c");
		sb.append(this.currentRowId + 1);
		sb.append("\u884c \u7b2c");
		sb.append(this.currentColumnId + 1);
		sb.append("\u5217\u7684");

		String errorMsgHead = sb.toString();
		
		String detailErrorMsg = "";
		String batchErrorMsg = "";
		
		if(cell == null){
			if(rule.isRequired()){
				result = false;
//				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"һ������Ϊ�գ�";
//				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"һ������Ϊ�գ�";
				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u4e0d\u80fd\u4e3a\u7a7a\uff01";
				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u4e0d\u80fd\u4e3a\u7a7a\uff01";
				this.addCellErrorMessage(rule, batchErrorMsg, detailErrorMsg);
			}
			return result; // cellΪnull����������
		}
		
		// �жϵ�Ԫ���Ƿ���ϵͳ��֧�ֵĸ�ʽ
		else if( ! ExcelUtil.isCellTypeSupported(cell)){
			result = false;
//			detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"һ����Ԫ����ϵͳ��֧�ֵĸ�ʽ��";
//			batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"һ����Ԫ����ϵͳ��֧�ֵĸ�ʽ��";
			detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u5355\u5143\u683c\u542b\u6709\u7cfb\u7edf\u4e0d\u652f\u6301\u7684\u683c\u5f0f\uff01";
			batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u5355\u5143\u683c\u542b\u6709\u7cfb\u7edf\u4e0d\u652f\u6301\u7684\u683c\u5f0f\uff01";
			this.addCellErrorMessage(rule, batchErrorMsg, detailErrorMsg);
			return false; // ��Ԫ���в�֧�ֵĸ�ʽ�������ٽ���������֤����������
		}
		
		
		// ����Ǳ������֤�Ƿ�Ϊ��
		if(rule.isRequired()){
			if(cellValue == null || "".equals(cellValue.trim())){
				result = false;
				//this.addComment(cell, "��������Ϊ�գ�", rule.getRowId()); //�����ڵ�Ԫ�����ע��д�������Ϣ��������POI�Ĺ������⣬��ʱ�޷�ʵ��
//				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"һ������Ϊ�գ�";
//				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"һ������Ϊ�գ�";
				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u4e0d\u80fd\u4e3a\u7a7a\uff01";
				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u4e0d\u80fd\u4e3a\u7a7a\uff01";
				this.addCellErrorMessage(rule, batchErrorMsg, detailErrorMsg);
			}
		}
		
		double maxValue = rule.getMaxValue();
		double minValue = rule.getMinValue();
		String maxValueStr = String.valueOf(rule.getMaxValue());
		String minValueStr = String.valueOf(rule.getMinValue());
		boolean passedNumberTypesValidate = true; // �ж��Ƿ����ģ����ָ������֤�������͵�Ҫ��
		
		// �������Ҫ�������֣���֤�Ƿ�Ϊ���ָ�ʽ
		if("Number".equalsIgnoreCase(rule.getValidateType()) || "Double".equalsIgnoreCase(rule.getValidateType()) || "Percent".equalsIgnoreCase(rule.getValidateType())){
			if(cellValue != null && ! "".equals(cellValue.trim())){
				if("Percent".equalsIgnoreCase(rule.getValidateType())){ //�ٷֱ��޳�%�� add by zhuhongjiang
					cellValue = cellValue.replaceAll("%", "");
				}
				if(!ExcelUtil.isNumber(cellValue)){
					result = false;
					//this.addComment(cell, "���������������֣�", rule.getRowId()); //�����ڵ�Ԫ�����ע��д�������Ϣ��������POI�Ĺ������⣬��ʱ�޷�ʵ��
//					detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"һ�������������֣�";
//					batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"һ�������������֣�";
					detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u5fc5\u987b\u8f93\u5165\u6570\u5b57\uff01";
					batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u5fc5\u987b\u8f93\u5165\u6570\u5b57\uff01";
					this.addCellErrorMessage(rule, batchErrorMsg, detailErrorMsg);
				}
			}
		}
		// �������Ҫ�������ڣ���֤�Ƿ�Ϊ���ڸ�ʽ
		else if("Date".equalsIgnoreCase(rule.getValidateType())){
			if(cellValue != null && ! "".equals(cellValue.trim()) && ! ExcelUtil.isStandardDate(cellValue)){
				result = false;
				//this.addComment(cell, "��������������ڲ����ڻ��ʽ����ȷ����׼��ʽΪ��2007-03-05)", rule.getRowId()); // �����ڵ�Ԫ�����ע��д�������Ϣ��������POI�Ĺ������⣬��ʱ�޷�ʵ��
//				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"һ������������ڲ����ڻ��ʽ����ȷ����׼��ʽΪ��2007-03-05)��";
//				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"һ������������ڲ����ڻ��ʽ����ȷ����׼��ʽΪ��2007-03-05)��";
				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u6240\u8f93\u5165\u7684\u65e5\u671f\u4e0d\u5b58\u5728\u6216\u683c\u5f0f\u4e0d\u6b63\u786e\uff08\u6807\u51c6\u683c\u5f0f\u4e3a\uff1a2007-03-05)\uff01";
				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u6240\u8f93\u5165\u7684\u65e5\u671f\u4e0d\u5b58\u5728\u6216\u683c\u5f0f\u4e0d\u6b63\u786e\uff08\u6807\u51c6\u683c\u5f0f\u4e3a\uff1a2007-03-05)\uff01";
				this.addCellErrorMessage(rule, batchErrorMsg, detailErrorMsg);
			}
		}
		// �������Ҫ�����ַ�������֤��Ԫ���Ƿ�Ϊ�ı���ʽ
		else if("String".equalsIgnoreCase(rule.getValidateType())){
			if(false){//ȡ�����ַ�������֤
				result = false;
				this.existStringCellWithNumberFormat = true;
				//this.addComment(cell, "�õ�Ԫ��Ӧ��Ϊ�ı���ʽ��ȴ����Ϊ����ֵ��ʽ�����ܵ������ݵ������", rule.getRowId()); // �����ڵ�Ԫ�����ע��д�������Ϣ��������POI�Ĺ������⣬��ʱ�޷�ʵ��
//				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"һ���ĵ�Ԫ��Ӧ��Ϊ�ı���ʽ��ȴ����Ϊ����ֵ��ʽ�����ܵ������ݵ������";
//				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"һ���ĵ�Ԫ��Ӧ��Ϊ�ı���ʽ��ȴ����Ϊ����ֵ��ʽ�����ܵ������ݵ������";
				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u7684\u5355\u5143\u683c\u5e94\u8be5\u4e3a\u6587\u672c\u683c\u5f0f\uff0c\u5374\u8bbe\u7f6e\u4e3a\u4e86\u6570\u503c\u683c\u5f0f\uff0c\u53ef\u80fd\u5bfc\u81f4\u6570\u636e\u5bfc\u5165\u51fa\u9519\uff01";
				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u7684\u5355\u5143\u683c\u5e94\u8be5\u4e3a\u6587\u672c\u683c\u5f0f\uff0c\u5374\u8bbe\u7f6e\u4e3a\u4e86\u6570\u503c\u683c\u5f0f\uff0c\u53ef\u80fd\u5bfc\u81f4\u6570\u636e\u5bfc\u5165\u51fa\u9519\uff01";
				this.addCellErrorMessage(rule, batchErrorMsg, detailErrorMsg);
			}
		}
		// �������Ҫ����Integer���ͣ���֤���ݸ�ʽ�Ƿ���ȷ
		else if("Integer".equalsIgnoreCase(rule.getValidateType())){
			if(maxValue > Integer.MAX_VALUE){
				maxValue = Integer.MAX_VALUE;
				maxValueStr = String.valueOf(Integer.MAX_VALUE);
			}
			if(minValue < Integer.MIN_VALUE || minValue == Double.MIN_VALUE){
				minValue = Integer.MIN_VALUE;
				minValueStr = String.valueOf(Integer.MIN_VALUE);
			}
			
			if(cellValue != null && ! "".equals(cellValue.trim())){
				Double doubleObject = CommonUtil.getDoubleObjectFromString(cellValue);
				if(doubleObject == null){ // ˵������Ĳ�������
					passedNumberTypesValidate = false;
				}
				else if(doubleObject.doubleValue() <= Integer.MAX_VALUE && doubleObject.doubleValue() >= Integer.MIN_VALUE){
					Integer integerObject = CommonUtil.getIntegerObjectFromString(cellValue);
					if(integerObject == null){
						passedNumberTypesValidate = false;
					}
				}
			}
			
			if(passedNumberTypesValidate == false){
				result = false;
//				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"һ����������������";
//				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"һ����������������";
				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u5fc5\u987b\u8f93\u5165\u6574\u6570\uff01";
				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u5fc5\u987b\u8f93\u5165\u6574\u6570\uff01";
				this.addCellErrorMessage(rule, batchErrorMsg, detailErrorMsg);
			}
		}
		// �������Ҫ����Long���ͣ���֤���ݸ�ʽ�Ƿ���ȷ
		else if("Long".equalsIgnoreCase(rule.getValidateType())){
			if(maxValue > Long.MAX_VALUE){
				maxValue = Long.MAX_VALUE;
				maxValueStr = String.valueOf(Long.MAX_VALUE);
			}
			if(minValue < Long.MIN_VALUE || minValue == Double.MIN_VALUE){
				minValue = Long.MIN_VALUE;
				minValueStr = String.valueOf(Long.MIN_VALUE);
			}
			
			if(cellValue != null && ! "".equals(cellValue.trim())){
				Double doubleObject = CommonUtil.getDoubleObjectFromString(cellValue);
				if(doubleObject == null){
					passedNumberTypesValidate = false;
				}
				else if(doubleObject.doubleValue() <= Long.MAX_VALUE && doubleObject.doubleValue() >= Long.MIN_VALUE){
					Long longObject = CommonUtil.getLongObjectFromString(cellValue);
					if(longObject == null){
						passedNumberTypesValidate = false;
					}
				}
			}
			
			if(passedNumberTypesValidate == false){
				result = false;
//				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"һ����������������";
//				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"һ����������������";
				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u5fc5\u987b\u8f93\u5165\u6574\u6570\uff01";
				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u5fc5\u987b\u8f93\u5165\u6574\u6570\uff01";
				this.addCellErrorMessage(rule, batchErrorMsg, detailErrorMsg);
			}
		}
		// �������Ҫ����Short���ͣ���֤���ݸ�ʽ�Ƿ���ȷ
		else if("Short".equalsIgnoreCase(rule.getValidateType())){
			if(maxValue > Short.MAX_VALUE){
				maxValue = Short.MAX_VALUE;
				maxValueStr = String.valueOf(Short.MAX_VALUE);
			}
			if(minValue < Short.MIN_VALUE || minValue == Double.MIN_VALUE){
				minValue = Short.MIN_VALUE;
				minValueStr = String.valueOf(Short.MIN_VALUE);
			}
			
			if(cellValue != null && ! "".equals(cellValue.trim())){
				Double doubleObject = CommonUtil.getDoubleObjectFromString(cellValue);
				if(doubleObject == null){
					passedNumberTypesValidate = false;
				}
				else if(doubleObject.doubleValue() <= Short.MAX_VALUE && doubleObject.doubleValue() >= Short.MIN_VALUE){
					Short shortObject = CommonUtil.getShortObjectFromString(cellValue);
					if(shortObject == null){
						passedNumberTypesValidate = false;
						
					}
				}
			}
			
			if(passedNumberTypesValidate == false){
				result = false;
//				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"һ����������������";
//				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"һ����������������";
				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u5fc5\u987b\u8f93\u5165\u6574\u6570\uff01";
				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u5fc5\u987b\u8f93\u5165\u6574\u6570\uff01";
				this.addCellErrorMessage(rule, batchErrorMsg, detailErrorMsg);
			}
		}
		
		
		// �������Ҫ���Ǹ�����ֵ���ͣ���֤�Ƿ�������ֵ����СֵҪ��
		if("Number".equalsIgnoreCase(rule.getValidateType()) || "Double".equalsIgnoreCase(rule.getValidateType()) 
				|| "Integer".equalsIgnoreCase(rule.getValidateType()) || "Long".equalsIgnoreCase(rule.getValidateType())
				|| "Short".equalsIgnoreCase(rule.getValidateType()) || "Percent".equalsIgnoreCase(rule.getValidateType())){
			
			if(passedNumberTypesValidate && cellValue != null && ! "".equals(cellValue)){
				Double doubleObject = CommonUtil.getDoubleObjectFromString(cellValue);
				if(doubleObject != null && doubleObject.doubleValue() > maxValue){
					result = false;
					if(maxValueStr != null && maxValueStr.endsWith(".0")){
						maxValueStr = maxValueStr.substring(0, maxValueStr.indexOf(".0"));
					}
//					detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"һ����ֵ���ܴ���" + maxValueStr + "��";
//					batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"һ����ֵ���ܴ���" + maxValueStr + "��";
					detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u7684\u503c\u4e0d\u80fd\u5927\u4e8e" + maxValueStr + "\uff01";
					batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u7684\u503c\u4e0d\u80fd\u5927\u4e8e" + maxValueStr + "\uff01";
					this.addCellErrorMessage(rule, batchErrorMsg, detailErrorMsg);
				}
				
				if(minValue != Double.MIN_VALUE && doubleObject != null && doubleObject.doubleValue() < minValue){
					result = false;
					if(minValueStr != null && minValueStr.endsWith(".0")){
						minValueStr = minValueStr.substring(0, minValueStr.indexOf(".0"));
					}
//					detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"һ����ֵ����С��" + minValueStr + "��";
//					batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"һ����ֵ����С��" + minValueStr + "��";
					detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u7684\u503c\u4e0d\u80fd\u5c0f\u4e8e" + minValueStr + "\uff01";
					batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u7684\u503c\u4e0d\u80fd\u5c0f\u4e8e" + minValueStr + "\uff01";
					this.addCellErrorMessage(rule, batchErrorMsg, detailErrorMsg);
				}
			}
		}
		
		// �������󳤶����ƣ���֤�����Ƿ񳬹�����, -999����û�г�������
		int maxLength = rule.getMaxLength();
		if(maxLength != -999 && ExcelUtil.isExceedCellLengthLimit(cell, maxLength)){
			result = false;
			//this.addComment(cell, "�����ĳ��Ȳ��ܳ���" + limit + "���ַ�����" + limit/3 + "�����֣���", rule.getRowId()); //�����ڵ�Ԫ�����ע��д�������Ϣ��������POI�Ĺ������⣬��ʱ�޷�ʵ��
			String chineseLimitMsg = "";
			if(maxLength >= 3){
//				chineseLimitMsg = "����" + maxLength/3 + "�����֣�";
				chineseLimitMsg = "\uff08\u6216" + maxLength/3 + "\u4e2a\u6c49\u5b57\uff09";
			}
//			detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"һ���ĳ��Ȳ��ܳ���" + maxLength + "���ַ�" + chineseLimitMsg + "��";
//			batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"һ���ĳ��Ȳ��ܳ���" + maxLength + "���ַ�" + chineseLimitMsg + "��";
			detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u7684\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc7" + maxLength + "\u4e2a\u5b57\u7b26" + chineseLimitMsg + "\uff01";
			batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u7684\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc7" + maxLength + "\u4e2a\u5b57\u7b26" + chineseLimitMsg + "\uff01";
			this.addCellErrorMessage(rule, batchErrorMsg, detailErrorMsg);
		}
		
		// �������С����Ҫ����֤�����Ƿ�С����С����
		int minLength = rule.getMinLength();
		if(minLength > 0 && ExcelUtil.isLessThanCellLengthRequirement(cell, minLength)){
			result = false;
			//this.addComment(cell, "�����ĳ��Ȳ�������" + limit + "���ַ�����" + limit/3 + "�����֣���", rule.getRowId()); //�����ڵ�Ԫ�����ע��д�������Ϣ��������POI�Ĺ������⣬��ʱ�޷�ʵ��
			String chineseLimitMsg = "";
			//if(minLength >= 3){
//				chineseLimitMsg = "����" + ((minLength + 2)/3) + "�����֣�";
			chineseLimitMsg = "\uff08\u6216" + ((minLength + 2)/3) + "\u4e2a\u6c49\u5b57\uff09";
			//}
//			detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"һ���ĳ��Ȳ�������" + minLength + "���ַ�" + chineseLimitMsg + "��";
//			batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"һ���ĳ��Ȳ�������" + minLength + "���ַ�" + chineseLimitMsg + "��";
			detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u7684\u957f\u5ea6\u4e0d\u80fd\u5c11\u4e8e" + minLength + "\u4e2a\u5b57\u7b26" + chineseLimitMsg + "\uff01";
			batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u7684\u957f\u5ea6\u4e0d\u80fd\u5c11\u4e8e" + minLength + "\u4e2a\u5b57\u7b26" + chineseLimitMsg + "\uff01";
			this.addCellErrorMessage(rule, batchErrorMsg, detailErrorMsg);
		}
		
		if(result == false){
			cell.setCellStyle(this.errorStyle);
		}
		
		return result;
	}

	/**
	 * ��errorList����ӳ�����ʾ��Ϣ
	 * @param rule
	 * @param batchErrorMsg
	 * @param detailErrorMsg
	 */
	private void addCellErrorMessage(CellValidateRule rule, String batchErrorMsg, String detailErrorMsg){
		if(rule.hasBatchErrorMsgInErrorList() && ! this.errorList.contains(batchErrorMsg)){
			this.errorList.add(batchErrorMsg);
		}
		if(rule.hasDetailErrorMsgInErrorList()){
			errorMap.put(Integer.parseInt(String.valueOf(rule.getColumnId())), detailErrorMsg);
			addRowErrorMessage(rule,detailErrorMsg);	//���еķ�ʽ�洢��Ϣ
		}
//		this.errorList.add(detailErrorMsg);
	}

	private void addRowErrorMessage(CellValidateRule rule, String detailErrorMsg){
		Integer rowId = this.currentRowId ;
		Object obj = errorRowMap.get(rowId) ;
		if( null != obj ){
			errorRowMap.put(rowId, obj.toString() + "��"+ detailErrorMsg );
		}else{
			errorRowMap.put(rowId,  detailErrorMsg );
		}
	}
	/**
	 * ��ȡ�����Ĺ����������Ӧ�ĵ�Ԫ�������
	 * ������cellValidateRule�е����÷�����Ӧ���͵Ķ���
	 * update by jerry 
	 */
	private Object getCellValue(CellValidateRule rule, Cell cell){
		String cellValueString = ExcelUtil.getStringCellValue(cell);
		
		//System.out.println("------------>>" + cellValueString);
		
		String returnType = rule.getReturnType();
		if(returnType == null || "".equals(returnType) || "String".equalsIgnoreCase(returnType)){
			if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
				if(cellValueString.endsWith(".0")){//ȥ�����ַ���Ϊ��ֵ�͵�ʱ������ϵ�'.0',��������ַ�����'.0'��β�ֲ��뱻�ص��Ļ��뽫��Ԫ������Ϊ�ı��� by zwj
					return cellValueString.substring(0,cellValueString.lastIndexOf("."));
				}else{
					return cellValueString;
				}
			}else{
				return cellValueString;
			}
		}
		else if("Double".equalsIgnoreCase(returnType)){
			return CommonUtil.getDoubleObjectFromString(cellValueString);
		}
		else if("Integer".equalsIgnoreCase(returnType)){
			return CommonUtil.getIntegerObjectFromString(cellValueString);
		}
		else if("Long".equalsIgnoreCase(returnType)){
			return CommonUtil.getLongObjectFromString(cellValueString);
		}
		else if("Short".equalsIgnoreCase(returnType)){
			return CommonUtil.getShortObjectFromString(cellValueString);
		}
		else if("Date".equalsIgnoreCase(returnType)){
			return CommonUtil.getJavaUtilDateFromDateString(cellValueString);
		}
		else{
			return cellValueString;
		}
	}

	/**
	 * �õ�ĳ��ͷ��Ϣ��Ԫ����EXCEL�ļ����������кţ�ע�⣺ֵ��0��ʼ��
	 */
	public int getExcelRowIdOfHeadData(String cellName){
		if(cellName == null || "".equals(cellName)){
			return -1;
		}
		
		CellValidateRule rule = (CellValidateRule)this.headDataRuleMap.get(cellName);
		if(rule == null){
			return -1;
		}
		else{
			return rule.getRowId();
		}
	}
	
	/**
	 * �õ�ĳ����ϸ��Ϣ��Ԫ����EXCEL�ļ����������кţ�ע�⣺ֵ��0��ʼ��
	 */
	public int getExcelRowIdOfDetailData(int lineId, String cellName){
		if(cellName == null || "".equals(cellName)){
			return -1;
		}
		
		CellValidateRule rule = (CellValidateRule)this.detailDataRuleMap.get(cellName);
		if(rule == null){
			return -1;
		}
		else{
			return this.getDetailCellRowId(lineId, rule);
		}
	}
	
	/**
	 * �õ�ĳ��ͷ��Ϣ��Ԫ����EXCEL�ļ����������кţ�ע�⣺ֵ��0��ʼ��
	 */
	public short getExcelColumnIdOfHeadData(String cellName){
		if(cellName == null || "".equals(cellName)){
			return -1;
		}
		
		CellValidateRule rule = (CellValidateRule)this.headDataRuleMap.get(cellName);
		if(rule == null){
			return -1;
		}
		else{
			return rule.getColumnId();
		}
	}
	
	/**
	 * �õ�ĳ����ϸ��Ϣ��Ԫ����EXCEL�ļ����������кţ�ע�⣺ֵ��0��ʼ��
	 */
	public short getExcelColumnIdOfDetailData(int lineId, String cellName){
		if(cellName == null || "".equals(cellName)){
			return -1;
		}
		
		CellValidateRule rule = (CellValidateRule)this.detailDataRuleMap.get(cellName);
		if(rule == null){
			return -1;
		}
		else{
			return this.getDetailCellColumnId(lineId, rule);
		}
	}
	
	public File getTemplateFile() {
		return templateFile;
	}

	public File getImportFile() {
		return importFile;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	

	public Set<Integer> getLineErrorList(){
		return this.lineErrorList;
	}
}
