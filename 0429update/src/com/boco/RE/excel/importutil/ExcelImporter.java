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
 * Excel导入操作类
 * @Author zhuhongjiang
 * @Date 2020/1/7 下午4:14
 **/
public class ExcelImporter {

	private File templateFile;
	private File importFile;
	private ExcelFactory excelFactory;
	private Workbook workbook;
	private CellStyle errorStyle;
	private List blankDetailLineErrorList = new ArrayList(); 				//用于临时缓存空白详细信息行的错误信息
	private ImportedExcelData importedExcelData = new ImportedExcelData(); 	//用于将导入的所有数据返回给调用者
	private Map headTitleRuleMap; 											//用于保存从模板中读取出来的头信息标题验证规则
	private Map detailTitleRuleMap; 										//用于保存从模板中读取出来的详细信息标题验证规则
	private Map headDataRuleMap; 											//用于保存从模板中读取出来的头信息数据验证规则
	private Map detailDataRuleMap; 											//用于保存从模板中读取出来的详细信息数据验证规则
	//如果全局参数配置is-multi-sheet-rule=true,则会将模板中头行标题、数据验证规则保存
	private List<Map> headTitleRuleList , headDataRuleList , detailTitleRuleList ,detailDataRuleList ;
	private TemplateValidateRule templateValidateRule = new TemplateValidateRule(); //整个模板的验证规则全局配置
	private boolean existStringCellWithNumberFormat = false; 						//用于指示是否存在String类型数据的单元格设置成了数值格式的情况
	private String userId = ""; 		//操作用户的登录帐号，可以由页面传递进来
	private int currentSheetId = 0; 	//当前正在读取的Sheet编号
	private int currentRowId = 0; 		//当前正在读取的单元格所处的行号
	private short currentColumnId = 0; 	//当前正在读取的单元格所处的列号

	private List<Object> listForSort = new ArrayList<>();
	private List errorList = new ArrayList();					//所有错误信息集合
	private Map<Object,Object> errorRowMap = new HashMap<>(); 	//以行为单位存放所有错误
	private Map<Object,Object> errorMap = new HashMap<>();		//以列为单位存放所有错误
    private Set<Integer> lineErrorList= new HashSet<>(); 		//错误信息记录行号  by zhuhj


	/**
	 * ExcelImporter构造
	 * @param templateFilePath	模板文件路径
	 * @param importFilePath	上传文件路径
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
				// 由要导入的EXCEL文件对象生成一个workbook
				excelFactory = new ExcelFactory(fis);
				workbook = excelFactory.getWorkbook();	//工厂方法获取WorkBook对象
				// 由给定的模板文件路径生成一个模板文件对象
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
	 * 执行导入EXCEL的过程，并将导入成功的数据保存到Map中，如果有错误，将提示信息保存到errorList中
	 * @return
	 */
	public boolean importExcel(){
		//1:判断模板文件是否存在
		if(this.workbook == null){
			this.errorList.add(Constants.UNKNOWN_FORMAT_EXISTS_IN_UPLOAD_FILE);
			return false;
		}
		if(this.templateFile == null){
			this.errorList.add(Constants.TEMPLATE_FILE_ERROR_ON_SERVER_PLEASE_CONTACT_ADMIN);
			return false;
		}
		//2：存在则加载验证模板验证规则
		boolean ruleLoaded = this.loadValidateRules();
		if(!ruleLoaded){
			this.errorList.add(Constants.TEMPLATE_FILE_ERROR_ON_SERVER_PLEASE_CONTACT_ADMIN);
			return false;
		}
		//3: 加载成功模板验证规则,循环遍历验证上传文件与模板一致性并读取文件中SHEET内容
		// 设定错误提示的单元格格式
		this.errorStyle = this.workbook.createCellStyle();
		this.errorStyle = ExcelUtil.configErrorStyle(this.errorStyle, ExcelUtil.FGColor_Red);
		
		int sheetCountForImport = 1;
		// 如果模板中声明该EXCEL文件导入时为多表（sheet）导入，则循环导入各个sheet
		if(this.templateValidateRule.isMultiSheet()){
			sheetCountForImport = this.workbook.getNumberOfSheets();
		}
		
		boolean allSheetsImported = true; // 表示是否所有sheet的数据都导入成功的标志
		
		// 循环导入该EXCEL文件各个sheet的数据
		// 固定从第一个sheet开始导入，如果是多sheet导入，则循环导入所有的sheet
		// 以后改进时可以增加一个配置选项，用于指定要导入的sheet范围，可以指定几个sheet的编号或名称，或指定sheet编号的范围
		for(int i = 0; i < sheetCountForImport; i++ ){
			Sheet sheet = this.workbook.getSheetAt(i);
			this.importedExcelData.addNameOfSheet(this.workbook.getSheetName(i)); // 把将要导入的Sheet名称保存到importedExcelData中
			this.currentSheetId = i;
			//3.1: 验证上传文件一致性
			if(this.templateValidateRule.isValidateHeadTitleEnabled()){
				// 验证EXCEL的头信息标题，如果有错，说明上传的EXCEL文件与模板不符，立即返回
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
				// 验证EXCEL的详细信息标题，如果有错，说明上传的EXCEL文件与模板不符，立即返回
				if(this.templateValidateRule.isMultiSheetRule()){
					this.detailTitleRuleMap = this.detailTitleRuleList.get(i);
					this.detailDataRuleMap = this.detailDataRuleList.get(i);
				}
				if(!validateTitle(this.detailTitleRuleMap, sheet)){
					this.errorList.add(Constants.UPLOAD_FILE_NOT_ACCORD_WITH_TEMPLATE); 
					return false;
				}
			}
			//3.2: 加载上传SHEET文件
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
	 * 从一个Sheet中导入数据，并将读取成功的数据存入importedExcelData对象中
	 */
	private boolean readDataFromSheet(Sheet sheet){
		boolean result = true;
		//导入头信息
		if(this.readHeadDataFromSheet(sheet) == false){
			result = false;
		}
		//导入行信息
		if(readDetailDataFromSheet(sheet) == false){
			result = false;
		}
		
		return result;
	}
	
	/**
	 * 
	 * 方法说明: 从一个Sheet中导入头信息数据，并将读取成功的数据存入importedExcelData对象中
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
		//循环获取头信息集合
		Set ruleEntrySet = this.headDataRuleMap.entrySet();
		Iterator it = ruleEntrySet.iterator();
		while(it.hasNext()){
			Map.Entry ruleEntry = (Map.Entry)it.next();
			CellValidateRule rule = (CellValidateRule)ruleEntry.getValue();
			if(rule == null){
				continue;
			}
			
			this.currentRowId = rule.getRowId();		//定位到行
			this.currentColumnId = rule.getColumnId();	//定位到列
			
			Row row = sheet.getRow(rule.getRowId());
			if(row == null){
				continue;
			}
			
			Cell cell = row.getCell(rule.getColumnId());
			
			//重新获取下
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
	 * 从一个Sheet中导入详细信息数据，并将读取成功的数据存入importedExcelData对象中
	 */
	private boolean readDetailDataFromSheet(Sheet sheet){
		if(sheet == null){
			return false;
		}
		//System.out.println("readDetailDataFromSheet sheetName :"+sheet.getSheetName());
		boolean result = true;
		int lineId = 0; // 记录当前所读的详细信息是第几行（列）
		boolean thisSheetHasDetailLine = false;
		boolean allLinesPassedValidation = true;
		List dataLinesOfSheet = new ArrayList();
		Object cellValue = null;
		//System.out.println("-------------detailDataRuleMap"+com.alibaba.fastjson.JSON.toJSONString(this.detailDataRuleMap));
		//1: 循环读取各行详细信息的数据，直到读到该EXCEL文件的物理结束位置
		while( ! this.isAllDetailCellReachPhysicalEnd(sheet, lineId, this.detailDataRuleMap)){
			Map lineDataMap = new HashMap();
			boolean currentLinePassedValidation = true;
			//2: 判断导入的想象行所有单元格都为空白
			if(this.isBlankDetailLine(sheet, lineId, this.detailDataRuleMap)){
				//2.1：所有单元格都为空白，则根据导入验证的配置确定是否报错
				if( ! this.templateValidateRule.isBlankDetailLineAllowed()){
//					String errorMsg = "Excel文件中第" + (this.currentSheetId + 1) + "个Sheet\"" 
//						+ this.workbook.getSheetName(this.currentSheetId) + "\" 的第" + (lineId + 1) + "条详细信息未填写任何有效数据，请将数据填写完整或将该行（列）删除！";
					
					String errorMsg = "Excel\u6587\u4ef6\u4e2d\u7b2c" + (this.currentSheetId + 1) + "\u4e2aSheet\"" 
					+ this.workbook.getSheetName(this.currentSheetId) + "\" \u7684\u7b2c" + (lineId + 1) + "\u6761\u8be6\u7ec6\u4fe1\u606f\u672a\u586b\u5199\u4efb\u4f55\u6709\u6548\u6570\u636e\uff0c\u8bf7\u5c06\u6570\u636e\u586b\u5199\u5b8c\u6574\u6216\u5c06\u8be5\u884c\uff08\u5217\uff09\u5220\u9664\uff01";
					if(! this.blankDetailLineErrorList.contains(errorMsg)){
						this.blankDetailLineErrorList.add(errorMsg);
					}
				}
			}else{
				//3：如果该行都不是空白行，则将之前已经出现的空白行错误提示信息从缓存List中真正放入errorList中，防止某个EXCEL末尾有许多空行时也提示错误
				if(this.blankDetailLineErrorList != null && this.blankDetailLineErrorList.size() > 0){
					this.errorList.addAll(this.blankDetailLineErrorList);
					this.blankDetailLineErrorList.clear(); // 然后把空白行错误提示信息的缓存List清空
				}
				
				// 如果所要导入的行并非所有单元格全部为空白，则循环读取该行中各个detail-data-cell的值
				currentLinePassedValidation = true; // 初始化本行是否全部通过验证的标志
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
	
					// 根据该字段的验证规则、当前所导入的行号，来获得要读取的单元格对象
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
				
				// 如果该行中所有单元格均通过验证，就保存数据
				//if(currentLinePassedValidation){ (*)
					lineDataMap.put("lineId", this.currentRowId+1);//存入行号  byzhuhj
					dataLinesOfSheet.add(lineDataMap);
				//} (*)
			}
			
			lineId++;
		}
		
		if( ! allLinesPassedValidation){
			result = false;
		}
		
		if(this.templateValidateRule.isDetailMustExsit() && thisSheetHasDetailLine == false){
//			this.errorList.add("Excel文件中第" + (this.currentSheetId + 1) + "个Sheet\"" 
//					+ this.workbook.getSheetName(this.currentSheetId) + "\" 没有任何详细行信息！");
			this.errorList.add("Excel\u6587\u4ef6\u4e2d\u7b2c" + (this.currentSheetId + 1) + "\u4e2aSheet\"" 
					+ this.workbook.getSheetName(this.currentSheetId) + "\" \u6ca1\u6709\u4efb\u4f55\u8be6\u7ec6\u884c\u4fe1\u606f\uff01");
		}
		
		this.importedExcelData.addDetailDataOfSheet(dataLinesOfSheet);
		
		return result;
	}
	
	/**
	 * 得到该Sheet中目前将要读取的一组详细信息中某个单元格的行号
	 */
	private int getDetailCellRowId(int lineId, CellValidateRule rule){
		if(lineId < 0 || rule == null){
			return -1;
		}

		int rowId = rule.getRowId(); // 该字段的起始行号
		
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
		
		// 判断是否超过了EXCEL允许的最大行号
		if(rowId > ExcelUtil.MAX_ROW_ID_IN_SHEET){
			rowId = -1;
		}
		
		return rowId;
	}
	
	/**
	 * 得到该Sheet中目前将要读取的一组详细信息中某个单元格的列号
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
		
		// 判断是否超过了EXCEL允许的最大行号
		if(columnId > ExcelUtil.MAX_COLUMN_ID_IN_SHEET){
			columnId = -1;
		}
		
		return columnId;
	}
	
	/**
	 * 得到该Sheet中目前将要读取的一组详细信息中的某个单元格
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
	 * 加载验证规则
	 * 从构造方法传递进来的模板文件中读取相关的验证规则数据
	 */
	private boolean loadValidateRules(){
		//1:如果模板文件未指定，则无法加载验证规则
		if(this.templateFile == null){
			return false;
		}
		
		//2: 根据模板文件的扩展名来确定用何种规则加载实现类来读取验证规则
		String templateFileName = this.templateFile.getAbsolutePath();
		if(templateFileName == null || "".equals(templateFileName) || templateFileName.indexOf(".") == -1){
			return false;
		}
		
		ValidateRuleLoader ruleLoader = null; //加载验证规则的工具类
		
		String fileExt = templateFileName.substring(templateFileName.lastIndexOf(".")+1);
		if(Constants.FILE_XLS.equalsIgnoreCase(fileExt) || Constants.FILE_XLSX.equalsIgnoreCase(fileExt)){
			ruleLoader = new ValidateRuleExcelLoader();
		}else{
			this.errorList.add(Constants.UNKNOWN_VALIDATE_RULE_TEMPLATE_FILE_FORMAT);
			return false;
		}
		//3:验证并加载模板中的规则(全局配置/分配表导入单元格配置)
		boolean ruleLoaded = ruleLoader.loadValidateRules(this.templateFile);
		if(!ruleLoaded){
			// 在控制台打印加载验证规则过程中所产生的错误提示信息
//			List loadRuleErrors = ruleLoader.getErrorList();
//			if(loadRuleErrors != null && loadRuleErrors.size() > 0){
//				for(int i = 0; i < loadRuleErrors.size(); i++){
//					System.out.println("error when loading validate rule: " + loadRuleErrors.get(i));
//				}
//			}
			return false;
		}

		// 1.读取整个模板的验证规则全局配置，封装成TemplateValidateRule对象
		this.templateValidateRule = ruleLoader.getTemplateValidateRule();
		// 2.读取头信息标题的验证规则
		this.headTitleRuleMap = ruleLoader.getHeadTitleRuleMap();
		// 3.读取详细信息标题的验证规则
		this.detailTitleRuleMap = ruleLoader.getDetailTitleRuleMap();
		// 4.读取头信息数据的验证规则
		this.headDataRuleMap = ruleLoader.getHeadDataRuleMap();
		// 5.读取详细信息数据的验证规则
		this.detailDataRuleMap = ruleLoader.getDetailDataRuleMap();
		// 6.如果配置多个SHEET规则,获取
		this.headTitleRuleList = ruleLoader.getHeadTitleRuleList();
		this.headDataRuleList = ruleLoader.getHeadDataRuleList();
		this.detailTitleRuleList = ruleLoader.getDetailTitleRuleList() ;
		this.detailDataRuleList = ruleLoader.getDetailDataRuleList() ;
		
		return true;
	}
	
	/**
	 * 得到从EXCEL导入的数据，如果导入时有错误，则返回null
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
	 * 绕开错误获得数据
	 * add by avent
	 */
	public ImportedExcelData getImportedOriExcelData(){
		return this.importedExcelData;
	}

	/**
	 * 
	 * 方法说明: 验证要导入的EXCEL文件中的标题与模板中的是否一致
	 * @param ruleMap 模板中的标题
	 * @param sheet 导入EXCEL 的SHEET
	 * @return 验证结果
	 */
	private boolean validateTitle(Map ruleMap, Sheet sheet){
		if(sheet == null){
			return false;
		}
		
		if(ruleMap == null || ruleMap.size() == 0){
			return true;
		}
		
		boolean result = true;
		//遍历模板中的标题集合
		Set ruleEntrySet = ruleMap.entrySet();
		Iterator it = ruleEntrySet.iterator();
		while(it.hasNext()){
			Map.Entry ruleEntry = (Map.Entry)it.next();
			//获取包含列信息(行ID、列ID的信息对象)
			CellValidateRule rule = (CellValidateRule)ruleEntry.getValue();
			
			Row row = sheet.getRow(rule.getRowId()); //根据模板行ID定位到导入EXCEL行
			if(row == null){
				result = false;
				break;
			}

			Cell cell = row.getCell(rule.getColumnId());//根据模板列ID定位到导入行的列
			//如果对应的列不存在则报错
			if(cell == null){
				result = false;
				cell = row.createCell(rule.getColumnId());
				cell.setCellStyle(this.errorStyle);
				break;
			}//存在对应的列则校验标题是否相同
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
	 * 得到相关出错信息的List
	 */
	public List getErrorList(){
		return this.errorList;
	}
	
	/**
	 * 得到相关出错信息的String
	 * 即将errorList中的所有信息拼接起来（用下划线_分隔）
	 */
	public String getErrorString(){
		Set s = errorMap.keySet();
		Iterator its = s.iterator();
		this.listForSort = new ArrayList<Object>();
		while(its.hasNext()){
			listForSort.add((Integer)its.next());
		}
	   Collections.sort(listForSort, null);
	   List errorSortList = new ArrayList();	//合并所有的错误信息，并添加入校验新的错误信息
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
	 * 得到WorkBook
	 */
	public Workbook getWorkbook() {
		return this.workbook;
	}

	/**
	 * 判断该EXCEL表中某一line详细信息的所有项是否全部为空
	 * 可以灵活适应行与列的详细信息单元格组合成一个line的方式
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
	 * 判断目前是否已读到该EXCEL文件的物理结束位置，用于决定是否停止读取详细信息
	 * 可以灵活适应行与列的详细信息单元格组合成一个line的方式
	 */
	private boolean isAllDetailCellReachPhysicalEnd(Sheet sheet, int lineId, Map detailRuleMap){
		if(sheet == null || lineId < 0 || detailRuleMap == null || detailRuleMap.size() == 0){
			return true;
		}
		if(this.isBlankDetailLine(sheet, lineId, this.detailDataRuleMap)){ //读到空行，退出循环(可加配置控制)   add by zhuhongjiang
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
	 * 验证给定的规则对象所对应的单元格的内容
	 */
	private boolean validateCellValue(CellValidateRule rule, Cell cell){
		boolean result = true;
		
		String cellValue = ExcelUtil.getStringCellValue(cell);
		
		StringBuffer sb = new StringBuffer(); // 提示信息中的sheet、行、列、字段名等信息
//		sb.append("Excel文件中第");
//		sb.append(this.currentSheetId + 1);
//		sb.append("个Sheet\"");
//		sb.append(this.workbook.getSheetName(this.currentSheetId));
//		sb.append("\" 第");
//		sb.append(this.currentRowId + 1);
//		sb.append("行 第");
//		sb.append(this.currentColumnId + 1);
//		sb.append("列的");
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
//				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"一栏不能为空！";
//				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"一栏不能为空！";
				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u4e0d\u80fd\u4e3a\u7a7a\uff01";
				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u4e0d\u80fd\u4e3a\u7a7a\uff01";
				this.addCellErrorMessage(rule, batchErrorMsg, detailErrorMsg);
			}
			return result; // cell为null，立即返回
		}
		
		// 判断单元格是否有系统不支持的格式
		else if( ! ExcelUtil.isCellTypeSupported(cell)){
			result = false;
//			detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"一栏单元格含有系统不支持的格式！";
//			batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"一栏单元格含有系统不支持的格式！";
			detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u5355\u5143\u683c\u542b\u6709\u7cfb\u7edf\u4e0d\u652f\u6301\u7684\u683c\u5f0f\uff01";
			batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u5355\u5143\u683c\u542b\u6709\u7cfb\u7edf\u4e0d\u652f\u6301\u7684\u683c\u5f0f\uff01";
			this.addCellErrorMessage(rule, batchErrorMsg, detailErrorMsg);
			return false; // 单元格有不支持的格式，不必再进行其它验证，立即返回
		}
		
		
		// 如果是必填项，验证是否为空
		if(rule.isRequired()){
			if(cellValue == null || "".equals(cellValue.trim())){
				result = false;
				//this.addComment(cell, "该栏不能为空！", rule.getRowId()); //用于在单元格的批注中写入出错信息，但由于POI的功能问题，暂时无法实现
//				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"一栏不能为空！";
//				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"一栏不能为空！";
				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u4e0d\u80fd\u4e3a\u7a7a\uff01";
				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u4e0d\u80fd\u4e3a\u7a7a\uff01";
				this.addCellErrorMessage(rule, batchErrorMsg, detailErrorMsg);
			}
		}
		
		double maxValue = rule.getMaxValue();
		double minValue = rule.getMinValue();
		String maxValueStr = String.valueOf(rule.getMaxValue());
		String minValueStr = String.valueOf(rule.getMinValue());
		boolean passedNumberTypesValidate = true; // 判断是否符合模板中指定的验证数据类型的要求
		
		// 如果规则要求是数字，验证是否为数字格式
		if("Number".equalsIgnoreCase(rule.getValidateType()) || "Double".equalsIgnoreCase(rule.getValidateType()) || "Percent".equalsIgnoreCase(rule.getValidateType())){
			if(cellValue != null && ! "".equals(cellValue.trim())){
				if("Percent".equalsIgnoreCase(rule.getValidateType())){ //百分比剔除%， add by zhuhongjiang
					cellValue = cellValue.replaceAll("%", "");
				}
				if(!ExcelUtil.isNumber(cellValue)){
					result = false;
					//this.addComment(cell, "该栏必须输入数字！", rule.getRowId()); //用于在单元格的批注中写入出错信息，但由于POI的功能问题，暂时无法实现
//					detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"一栏必须输入数字！";
//					batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"一栏必须输入数字！";
					detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u5fc5\u987b\u8f93\u5165\u6570\u5b57\uff01";
					batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u5fc5\u987b\u8f93\u5165\u6570\u5b57\uff01";
					this.addCellErrorMessage(rule, batchErrorMsg, detailErrorMsg);
				}
			}
		}
		// 如果规则要求是日期，验证是否为日期格式
		else if("Date".equalsIgnoreCase(rule.getValidateType())){
			if(cellValue != null && ! "".equals(cellValue.trim()) && ! ExcelUtil.isStandardDate(cellValue)){
				result = false;
				//this.addComment(cell, "该栏所输入的日期不存在或格式不正确（标准格式为：2007-03-05)", rule.getRowId()); // 用于在单元格的批注中写入出错信息，但由于POI的功能问题，暂时无法实现
//				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"一栏所输入的日期不存在或格式不正确（标准格式为：2007-03-05)！";
//				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"一栏所输入的日期不存在或格式不正确（标准格式为：2007-03-05)！";
				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u6240\u8f93\u5165\u7684\u65e5\u671f\u4e0d\u5b58\u5728\u6216\u683c\u5f0f\u4e0d\u6b63\u786e\uff08\u6807\u51c6\u683c\u5f0f\u4e3a\uff1a2007-03-05)\uff01";
				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u6240\u8f93\u5165\u7684\u65e5\u671f\u4e0d\u5b58\u5728\u6216\u683c\u5f0f\u4e0d\u6b63\u786e\uff08\u6807\u51c6\u683c\u5f0f\u4e3a\uff1a2007-03-05)\uff01";
				this.addCellErrorMessage(rule, batchErrorMsg, detailErrorMsg);
			}
		}
		// 如果规则要求是字符串，验证单元格是否为文本格式
		else if("String".equalsIgnoreCase(rule.getValidateType())){
			if(false){//取消对字符串的验证
				result = false;
				this.existStringCellWithNumberFormat = true;
				//this.addComment(cell, "该单元格应该为文本格式，却设置为了数值格式，可能导致数据导入出错！", rule.getRowId()); // 用于在单元格的批注中写入出错信息，但由于POI的功能问题，暂时无法实现
//				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"一栏的单元格应该为文本格式，却设置为了数值格式，可能导致数据导入出错！";
//				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"一栏的单元格应该为文本格式，却设置为了数值格式，可能导致数据导入出错！";
				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u7684\u5355\u5143\u683c\u5e94\u8be5\u4e3a\u6587\u672c\u683c\u5f0f\uff0c\u5374\u8bbe\u7f6e\u4e3a\u4e86\u6570\u503c\u683c\u5f0f\uff0c\u53ef\u80fd\u5bfc\u81f4\u6570\u636e\u5bfc\u5165\u51fa\u9519\uff01";
				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u7684\u5355\u5143\u683c\u5e94\u8be5\u4e3a\u6587\u672c\u683c\u5f0f\uff0c\u5374\u8bbe\u7f6e\u4e3a\u4e86\u6570\u503c\u683c\u5f0f\uff0c\u53ef\u80fd\u5bfc\u81f4\u6570\u636e\u5bfc\u5165\u51fa\u9519\uff01";
				this.addCellErrorMessage(rule, batchErrorMsg, detailErrorMsg);
			}
		}
		// 如果规则要求是Integer类型，验证数据格式是否正确
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
				if(doubleObject == null){ // 说明输入的不是数字
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
//				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"一栏必须输入整数！";
//				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"一栏必须输入整数！";
				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u5fc5\u987b\u8f93\u5165\u6574\u6570\uff01";
				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u5fc5\u987b\u8f93\u5165\u6574\u6570\uff01";
				this.addCellErrorMessage(rule, batchErrorMsg, detailErrorMsg);
			}
		}
		// 如果规则要求是Long类型，验证数据格式是否正确
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
//				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"一栏必须输入整数！";
//				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"一栏必须输入整数！";
				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u5fc5\u987b\u8f93\u5165\u6574\u6570\uff01";
				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u5fc5\u987b\u8f93\u5165\u6574\u6570\uff01";
				this.addCellErrorMessage(rule, batchErrorMsg, detailErrorMsg);
			}
		}
		// 如果规则要求是Short类型，验证数据格式是否正确
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
//				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"一栏必须输入整数！";
//				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"一栏必须输入整数！";
				detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u5fc5\u987b\u8f93\u5165\u6574\u6570\uff01";
				batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u5fc5\u987b\u8f93\u5165\u6574\u6570\uff01";
				this.addCellErrorMessage(rule, batchErrorMsg, detailErrorMsg);
			}
		}
		
		
		// 如果规则要求是各种数值类型，验证是否符合最大值与最小值要求
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
//					detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"一栏的值不能大于" + maxValueStr + "！";
//					batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"一栏的值不能大于" + maxValueStr + "！";
					detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u7684\u503c\u4e0d\u80fd\u5927\u4e8e" + maxValueStr + "\uff01";
					batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u7684\u503c\u4e0d\u80fd\u5927\u4e8e" + maxValueStr + "\uff01";
					this.addCellErrorMessage(rule, batchErrorMsg, detailErrorMsg);
				}
				
				if(minValue != Double.MIN_VALUE && doubleObject != null && doubleObject.doubleValue() < minValue){
					result = false;
					if(minValueStr != null && minValueStr.endsWith(".0")){
						minValueStr = minValueStr.substring(0, minValueStr.indexOf(".0"));
					}
//					detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"一栏的值不能小于" + minValueStr + "！";
//					batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"一栏的值不能小于" + minValueStr + "！";
					detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u7684\u503c\u4e0d\u80fd\u5c0f\u4e8e" + minValueStr + "\uff01";
					batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u7684\u503c\u4e0d\u80fd\u5c0f\u4e8e" + minValueStr + "\uff01";
					this.addCellErrorMessage(rule, batchErrorMsg, detailErrorMsg);
				}
			}
		}
		
		// 如果有最大长度限制，验证长度是否超过限制, -999代表没有长度限制
		int maxLength = rule.getMaxLength();
		if(maxLength != -999 && ExcelUtil.isExceedCellLengthLimit(cell, maxLength)){
			result = false;
			//this.addComment(cell, "该栏的长度不能超过" + limit + "个字符（或" + limit/3 + "个汉字）！", rule.getRowId()); //用于在单元格的批注中写入出错信息，但由于POI的功能问题，暂时无法实现
			String chineseLimitMsg = "";
			if(maxLength >= 3){
//				chineseLimitMsg = "（或" + maxLength/3 + "个汉字）";
				chineseLimitMsg = "\uff08\u6216" + maxLength/3 + "\u4e2a\u6c49\u5b57\uff09";
			}
//			detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"一栏的长度不能超过" + maxLength + "个字符" + chineseLimitMsg + "！";
//			batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"一栏的长度不能超过" + maxLength + "个字符" + chineseLimitMsg + "！";
			detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u7684\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc7" + maxLength + "\u4e2a\u5b57\u7b26" + chineseLimitMsg + "\uff01";
			batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"\u4e00\u680f\u7684\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc7" + maxLength + "\u4e2a\u5b57\u7b26" + chineseLimitMsg + "\uff01";
			this.addCellErrorMessage(rule, batchErrorMsg, detailErrorMsg);
		}
		
		// 如果有最小长度要求，验证长度是否小于最小长度
		int minLength = rule.getMinLength();
		if(minLength > 0 && ExcelUtil.isLessThanCellLengthRequirement(cell, minLength)){
			result = false;
			//this.addComment(cell, "该栏的长度不能少于" + limit + "个字符（或" + limit/3 + "个汉字）！", rule.getRowId()); //用于在单元格的批注中写入出错信息，但由于POI的功能问题，暂时无法实现
			String chineseLimitMsg = "";
			//if(minLength >= 3){
//				chineseLimitMsg = "（或" + ((minLength + 2)/3) + "个汉字）";
			chineseLimitMsg = "\uff08\u6216" + ((minLength + 2)/3) + "\u4e2a\u6c49\u5b57\uff09";
			//}
//			detailErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"一栏的长度不能少于" + minLength + "个字符" + chineseLimitMsg + "！";
//			batchErrorMsg = errorMsgHead + "\"" + rule.getCellTitle() + "\"一栏的长度不能少于" + minLength + "个字符" + chineseLimitMsg + "！";
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
	 * 往errorList中添加出错提示信息
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
			addRowErrorMessage(rule,detailErrorMsg);	//以行的方式存储信息
		}
//		this.errorList.add(detailErrorMsg);
	}

	private void addRowErrorMessage(CellValidateRule rule, String detailErrorMsg){
		Integer rowId = this.currentRowId ;
		Object obj = errorRowMap.get(rowId) ;
		if( null != obj ){
			errorRowMap.put(rowId, obj.toString() + "；"+ detailErrorMsg );
		}else{
			errorRowMap.put(rowId,  detailErrorMsg );
		}
	}
	/**
	 * 读取给定的规则对象所对应的单元格的内容
	 * 根据其cellValidateRule中的配置返回相应类型的对象
	 * update by jerry 
	 */
	private Object getCellValue(CellValidateRule rule, Cell cell){
		String cellValueString = ExcelUtil.getStringCellValue(cell);
		
		//System.out.println("------------>>" + cellValueString);
		
		String returnType = rule.getReturnType();
		if(returnType == null || "".equals(returnType) || "String".equalsIgnoreCase(returnType)){
			if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
				if(cellValueString.endsWith(".0")){//去掉当字符串为数值型的时候给加上的'.0',所以如果字符串以'.0'结尾又不想被截掉的话请将单元格设置为文本型 by zwj
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
	 * 得到某个头信息单元格在EXCEL文件中所处的行号（注意：值从0开始）
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
	 * 得到某个详细信息单元格在EXCEL文件中所处的行号（注意：值从0开始）
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
	 * 得到某个头信息单元格在EXCEL文件中所处的列号（注意：值从0开始）
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
	 * 得到某个详细信息单元格在EXCEL文件中所处的列号（注意：值从0开始）
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
