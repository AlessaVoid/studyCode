package com.boco.RE.excel.importutil;



import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.RE.excel.util.ExcelFactory;
import com.boco.RE.excel.util.ExcelUtil;
import com.boco.RE.excel.util.CommonUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 验证规则EXCEL加载类
 * @Author zhuhongjiang
 * @Date 2020/1/7 下午2:58
 **/
public class ValidateRuleExcelLoader implements ValidateRuleLoader {
	private Workbook workbook = null;
	private List errorList = new ArrayList();
	private Map headTitleRuleMap = new HashMap();
	private List<Map> headTitleRuleList = new ArrayList<Map>();
	private Map headDataRuleMap = new HashMap();
	private List<Map> headDataRuleList = new ArrayList<Map>();
	private Map detailTitleRuleMap = new HashMap();
	private List<Map> detailTitleRuleList = new ArrayList<Map>();
	private Map detailDataRuleMap = new HashMap();
	private List<Map> detailDataRuleList = new ArrayList<Map>();
	private TemplateValidateRule templateValidateRule = new TemplateValidateRule();
	
	public ValidateRuleExcelLoader(){
		
	}
	
	public boolean loadValidateRules(String ruleFilePath) {
		File ruleFile = null;
		try{
			ruleFile = new File(ruleFilePath);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		if(ruleFile != null){
			return loadValidateRules(ruleFile);
		}
		else{
			return false;
		}
	}

	/**
	 * 方法说明: 验证并加载模板中规则
	 * @param ruleFile
	 * @return
	 */
	public boolean loadValidateRules(File ruleFile) {
		if(ruleFile == null){
			return false;
		}
		//1：工厂模式将模板文件生成WORKBOOK对象
		this.workbook = new ExcelFactory(ruleFile).getWorkbook();
		//2: 验证模板是否正确
		if(this.workbook == null){
			this.errorList.add("The template file \"" + ruleFile.getAbsolutePath() + "\" is not Excel file or contains unreadable format!");
			return false;
		}
		
		int sheetCount = this.workbook.getNumberOfSheets();
		if(sheetCount <= 1){
			//如果模板SHEET小于1,说明规则不全则报错
			this.errorList.add("There is no enough sheets in Excel template file!");
			return false;
		}
		
		boolean result = true;
		//3.1 ：验证并加载全局配置
		if(this.loadTemplateValidateRules() == false){
			result = false;
		}
		
		//3.2：验证并加载分配表导入单元格配置参数
		if(this.loadCellValidateRules() == false){
			result = false;
		}
		
		return result;
	}
	
	/**
	 * 方法说明:验证并加载全局配置
	 * @return 是否验证
	 */
	private boolean loadTemplateValidateRules(){
		int sheetCount = this.workbook.getNumberOfSheets();
		
		Sheet sheet = this.workbook.getSheetAt(sheetCount - 1); //获取第倒数SHEET的全局配置
		System.out.println("全局配置SHEET名称:"+sheet.getSheetName());
		Row row = null;
		Cell cell = null;
		boolean result = true;
		
		for(int i = 0; i <= sheet.getLastRowNum(); i++){
			String ruleString = null;
			String key = null;
			String value = null;
			
			row = sheet.getRow(i);
			if(row == null){
				continue;
			}
			
			cell = row.getCell((short)0); //
			if(cell == null){
				continue;
			}
			
			if(cell.getCellType() == Cell.CELL_TYPE_STRING){
				if(cell.getRichStringCellValue() != null){
					ruleString = cell.getRichStringCellValue().getString();
				}
				
				if(ruleString != null){
					key = CommonUtil.getPropertiesKey(ruleString);
					value = CommonUtil.getPropertiesValue(ruleString);
				}
				
				if(key != null && !"".equals(key.trim()) && value != null && !"".equals(value.trim())){
					if(this.assignTemplateValidateRule(key, value) == false){
						result = false;
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 * 方法说明: 验证全局配置里面的参数是否非法并存储
	 * @param key 键
	 * @param value 值
	 * @return 验证结果
	 */
	private boolean assignTemplateValidateRule(String key, String value){
		if(key == null || "".equals(key.trim())){
			return false;
		}
		
		boolean result = true;
		boolean hasUnsupportedValue = false;
		
		if("validate-data-enabled".equalsIgnoreCase(key)){
			if("true".equalsIgnoreCase(value)){
				this.templateValidateRule.setValidateDataEnabled(true);
			}
			else if("false".equalsIgnoreCase(value)){
				this.templateValidateRule.setValidateDataEnabled(false);
			}
			else{
				hasUnsupportedValue = true;
			}
		}else if("validate-sheet-name-enabled".equalsIgnoreCase(key)){
			if("true".equalsIgnoreCase(value)){
				this.templateValidateRule.setValidateSheetNameEnabled(true);
			}
			else if("false".equalsIgnoreCase(value)){
				this.templateValidateRule.setValidateSheetNameEnabled(false);
			}
			else{
				hasUnsupportedValue = true;
			}
		}else if("validate-head-title-enabled".equalsIgnoreCase(key)){
			if("true".equalsIgnoreCase(value)){
				this.templateValidateRule.setValidateHeadTitleEnabled(true);
			}
			else if("false".equalsIgnoreCase(value)){
				this.templateValidateRule.setValidateHeadTitleEnabled(false);
			}
			else{
				hasUnsupportedValue = true;
			}
		}else if("validate-detail-title-enabled".equalsIgnoreCase(key)){
			if("true".equalsIgnoreCase(value)){
				this.templateValidateRule.setValidateDetailTitleEnabled(true);
			}
			else if("false".equalsIgnoreCase(value)){
				this.templateValidateRule.setValidateDetailTitleEnabled(false);
			}
			else{
				hasUnsupportedValue = true;
			}
		}else if("is-multi-sheet".equalsIgnoreCase(key)){
			if("true".equalsIgnoreCase(value)){
				this.templateValidateRule.setMultiSheet(true);
			}
			else if("false".equalsIgnoreCase(value)){
				this.templateValidateRule.setMultiSheet(false);
			}
			else{
				hasUnsupportedValue = true;
			}
		}else if("detail-must-exist".equalsIgnoreCase(key)){
			if("true".equalsIgnoreCase(value)){
				this.templateValidateRule.setDetailMustExsit(true);
			}
			else if("false".equalsIgnoreCase(value)){
				this.templateValidateRule.setDetailMustExsit(false);
			}
			else{
				hasUnsupportedValue = true;
			}
		}else if("blank-detail-line-allowed".equalsIgnoreCase(key)){
			if("true".equalsIgnoreCase(value)){
				this.templateValidateRule.setBlankDetailLineAllowed(true);
			}
			else if("false".equalsIgnoreCase(value)){
				this.templateValidateRule.setBlankDetailLineAllowed(false);
			}
			else{
				hasUnsupportedValue = true;
			}
		}else if("has-head-title".equalsIgnoreCase(key)){
			if("true".equalsIgnoreCase(value)){
				this.templateValidateRule.setHasHeadTitle(true);
			}
			else if("false".equalsIgnoreCase(value)){
				this.templateValidateRule.setHasHeadTitle(false);
			}
			else{
				hasUnsupportedValue = true;
			}
		}else if("has-detail-title".equalsIgnoreCase(key)){
			if("true".equalsIgnoreCase(value)){
				this.templateValidateRule.setHasDetailTitle(true);
			}
			else if("false".equalsIgnoreCase(value)){
				this.templateValidateRule.setHasDetailTitle(false);
			}
			else{
				hasUnsupportedValue = true;
			}
		}else if("has-head".equalsIgnoreCase(key)){
			if("true".equalsIgnoreCase(value)){
				this.templateValidateRule.setHasHead(true);
			}
			else if("false".equalsIgnoreCase(value)){
				this.templateValidateRule.setHasHead(false);
			}
			else{
				hasUnsupportedValue = true;
			}
		}else if("has-detail".equalsIgnoreCase(key)){
			if("true".equalsIgnoreCase(value)){
				this.templateValidateRule.setHasDetail(true);
			}
			else if("false".equalsIgnoreCase(value)){
				this.templateValidateRule.setHasDetail(false);
			}
			else{
				hasUnsupportedValue = true;
			}
		}else if("head-return-type".equalsIgnoreCase(key)){
			this.templateValidateRule.setHeadReturnType(value);
		}else if("head-return-map-key".equalsIgnoreCase(key)){
			if(TemplateValidateRule.isSupportedHeadReturnMapKey(value)){
				this.templateValidateRule.setHeadReturnMapKey(value);
			}
			else{
				hasUnsupportedValue = true;
			}
		}
		else if("head-bean-class".equalsIgnoreCase(key)){
			this.templateValidateRule.setHeadBeanClass(value);
		}else if("detail-layout-type".equalsIgnoreCase(key)){
			if("row".equalsIgnoreCase(value) || "column".equalsIgnoreCase(value)){
				this.templateValidateRule.setDetailLayoutType(value);
			}
			else{
				hasUnsupportedValue = true;
			}
		}else if("detail-start-index".equalsIgnoreCase(key)){
			try{
				int startIndex = Integer.parseInt(value);
				this.templateValidateRule.setDetailStartIndex(startIndex);
			}
			catch(Exception e){
				hasUnsupportedValue = true;
			}
		}else if("detail-return-type".equalsIgnoreCase(key)){
			this.templateValidateRule.setDetailReturnType(value);
		}else if("detail-return-map-key".equalsIgnoreCase(key)){
			if(TemplateValidateRule.isSupportedDetailReturnMapKey(value)){
				this.templateValidateRule.setDetailReturnMapKey(value);
			}
			else{
				hasUnsupportedValue = true;
			}
		}else if("detail-bean-class".equalsIgnoreCase(key)){
			this.templateValidateRule.setDetailBeanClass(value);
		}else if("total-return-type".equalsIgnoreCase(key)){
			this.templateValidateRule.setTotalReturnType(value);
		}else if("total-return-map-key".equalsIgnoreCase(key)){
			if(TemplateValidateRule.isSupportedTotalReturnMapKey(value)){
				this.templateValidateRule.setTotalReturnMapKey(value);
			}
			else{
				hasUnsupportedValue = true;
			}
		}else if("is-multi-sheet-rule".equalsIgnoreCase(key)){
			if("true".equalsIgnoreCase(value)){
				this.templateValidateRule.setMultiSheetRule(true);
			}
			else if("false".equalsIgnoreCase(value)){
				this.templateValidateRule.setMultiSheetRule(false);
			}
			else{
				hasUnsupportedValue = false;
			}
		}else{
			this.errorList.add("Unsupported rule key \"" + key + "\" in Excel template file!");
			result = false;
		}
		
		if(hasUnsupportedValue){
			this.errorList.add("Unsupported rule value \"" + value + "\" for rule key \"" + key + "\"");
			result = false;
		}

		return result;
	}
	
	/**
	 * 方法说明: 验证并加载分配表导入单元格配置参数
	 * @return
	 */
	private boolean loadCellValidateRules(){
		boolean result = true;
		int ruleSheetNum = this.workbook.getNumberOfSheets();
		if(ruleSheetNum >1){
			ruleSheetNum = ruleSheetNum -1 ; //获取规则模板中的所有的规则SHEET
		}
		Map firstHeadTitleRule = new HashMap() ; 
		Map firstHeadDataRule = new HashMap() ;
		Map firstDetailTitleRule = new HashMap() ;
		Map firstDetailDataRule = new HashMap() ;
		Map headTitle , headData , detailTitle, detailData ;
		
		//循环遍历并加载规则表SHEET中的规则
		for(int ruleIndex = 0 ; ruleIndex < ruleSheetNum ; ruleIndex ++ ){
			Sheet sheet = this.workbook.getSheetAt(ruleIndex); //获取模板中的每个SHEET规则配置
			result = loadSheetValidateRule(sheet);
			//保存第一个SHEET的规则
			if(ruleIndex == 0){ 
				firstHeadTitleRule.putAll(this.headTitleRuleMap) ; 
				firstHeadDataRule.putAll(this.headDataRuleMap );
				firstDetailTitleRule.putAll(this.detailTitleRuleMap );
				firstDetailDataRule.putAll(this.detailDataRuleMap ); 
				
			}
			//往集合放置校验各种类型Map
			headTitle = new HashMap(); headTitle.putAll(this.headTitleRuleMap);
			headData = new HashMap();  headData.putAll(this.headDataRuleMap);
			detailTitle = new HashMap();  detailTitle.putAll(this.detailTitleRuleMap);
			detailData = new HashMap();  detailData.putAll(this.detailDataRuleMap);
			
			this.headTitleRuleList.add(headTitle);
			this.headDataRuleList.add(headData);
			this.detailTitleRuleList.add(detailTitle);
			this.detailDataRuleList.add(detailData);
			
			this.headTitleRuleMap.clear();
			this.headDataRuleMap.clear();
			this.detailTitleRuleMap.clear();
			this.detailDataRuleMap.clear();
		}
		if(null != firstHeadDataRule){
			this.headDataRuleMap.putAll(firstHeadDataRule) ;
		}
		if(null != firstHeadTitleRule){
			this.headTitleRuleMap.putAll(firstHeadTitleRule);
		}
		if(null != firstDetailTitleRule){
			this.detailTitleRuleMap.putAll(firstDetailTitleRule ); 
		}
		if(null != firstDetailDataRule){
			 this.detailDataRuleMap.putAll(firstDetailDataRule) ;
		}
		
		return result;
	}
	
	/**
	 * 方法说明: 循环遍历规则SHEET
	 * @param sheet
	 * @return
	 */
	private boolean loadSheetValidateRule(Sheet sheet){
		boolean result = true;
		Row row = null;
		Cell cell = null;
		
		//循环遍历行获取规则
		for(int rowId = 0; rowId <= sheet.getLastRowNum(); rowId++){
			//System.out.println("\n====================== reading cell rule from row:" + rowId);
			row = sheet.getRow(rowId);
			if(row == null){
				//System.out.println("row:" + rowId + " is null");
				continue;
			}
			
			//获取行的每列中规则
			for(short columnId = 0; columnId < row.getLastCellNum(); columnId++){
				//System.out.println("reading cell rule from row:" + rowId + " column:" + columnId);
				cell = row.getCell(columnId);
				if(cell == null){
					//System.out.println("cell:" + columnId + " of row:" + rowId + " is null");
					continue;
				}
				//加载模板列中规则备注
				if(this.loadCellValidateRule(cell, rowId, columnId) == false){
					result = false;
					//System.out.println("loadCellValidateRule failed"); // (*)
				}
			}
		}
		return result ;
	}
	
	/**
	 * 方法说明:
	 * @param cell 每列
	 * @param rowId 行号
	 * @param columnId 列号
	 * @return
	 */
	private boolean loadCellValidateRule(Cell cell, int rowId, short columnId){
		//获取内容
		Comment comment = cell.getCellComment();
		if(comment == null){
			return true;
		}
		
		String ruleParagraph = comment.getString().getString();
		
		if(ruleParagraph == null || "".equals(ruleParagraph.trim())){
			return true; //内容为空
		}
		
		boolean result = true;
		
		String ruleString = null;
		String key = null;
		String value = null;
		
		String[] ruleLines = ruleParagraph.split("\n");
		CellValidateRule cellValidateRule = new CellValidateRule();
		
		for(int lineId = 0; lineId < ruleLines.length; lineId++){
			ruleString = ruleLines[lineId];
			if(ruleString != null){
				key = CommonUtil.getPropertiesKey(ruleString);
				value = CommonUtil.getPropertiesValue(ruleString);
			}
			
			if(key != null && !"".equals(key.trim()) && value != null && !"".equals(value.trim())){
				cellValidateRule = this.assignCellValidateRule(cellValidateRule, key, value);
				if(cellValidateRule == null){
					break;
				}
			}
		}
		
		//如果模板中规则为空
		if(cellValidateRule == null){
			result = false;
		}
		else{
			cellValidateRule.setRowId(rowId);
			cellValidateRule.setColumnId(columnId);
			
			String cellType = cellValidateRule.getCellType();
			if(cellType != null && ! "".equals(cellType)){
				if(CellValidateRule.CELL_TYPE_OF_HEAD_TITLE.equals(cellType)){
					cellValidateRule.setCellTitle(ExcelUtil.getStringCellValue(cell)); //获取头信息CELL的格式并保存在Map中
					this.headTitleRuleMap.put(cellValidateRule.getCellName(), cellValidateRule);
				}
				else if(CellValidateRule.CELL_TYPE_OF_HEAD_DATA.equals(cellType)){
					this.headDataRuleMap.put(cellValidateRule.getCellName(), cellValidateRule);
				}
				else if(CellValidateRule.CELL_TYPE_OF_DETAIL_TITLE.equals(cellType)){
					cellValidateRule.setCellTitle(ExcelUtil.getStringCellValue(cell)); //获取行信息CELL的格式并保存在Map中
					this.detailTitleRuleMap.put(cellValidateRule.getCellName(), cellValidateRule);
				}
				else if(CellValidateRule.CELL_TYPE_OF_DETAIL_DATA.equals(cellType)){
					this.detailDataRuleMap.put(cellValidateRule.getCellName(), cellValidateRule);
				}
			}
		}
		
		return result;
	}
	

	/**
	 * 方法说明: 校验并存储模板中第1个SHEET中的备注信息
	 * @param cellValidateRule 存储备注对象
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	private CellValidateRule assignCellValidateRule(CellValidateRule cellValidateRule, String key, String value){
		if(key == null || "".equals(key.trim())){
			return cellValidateRule;
		}
		
		boolean hasUnsupportedValue = false;
		
		if("name".equalsIgnoreCase(key)){
			if("".equals(value.trim())){
				this.errorList.add("Cell name can not be empty!");
				return null;
			}
			else{
				cellValidateRule.setCellName(value);
			}
		}
		else if("title".equalsIgnoreCase(key)){
			cellValidateRule.setCellTitle(value);
		}
		else if("row-id".equalsIgnoreCase(key)){
			try{
				int rowId = Integer.parseInt(value);
				if(rowId < 0 || rowId > ExcelUtil.MAX_ROW_ID_IN_SHEET){
					hasUnsupportedValue = true;
				}
				else{
					cellValidateRule.setRowId(rowId);
				}
			}
			catch(Exception e){
				hasUnsupportedValue = true;
			}
		}
		else if("column-id".equalsIgnoreCase(key)){
			try{
				short columnId = Short.parseShort(value);
				if(columnId < 0 || columnId > ExcelUtil.MAX_COLUMN_ID_IN_SHEET){
					hasUnsupportedValue = true;
				}
				else{
					cellValidateRule.setColumnId(columnId);
				}
			}
			catch(Exception e){
				hasUnsupportedValue = true;
			}
		}
		else if("cell-type".equalsIgnoreCase(key)){
			if(CellValidateRule.isSupportedCellType(value)){
				cellValidateRule.setCellType(value);
			}
			else{
				hasUnsupportedValue = true;
			}
		}
		else if("validate-type".equalsIgnoreCase(key)){
			// isSupported ??? (*)
			cellValidateRule.setValidateType(value);
		}
		else if("return-type".equalsIgnoreCase(key)){
			// isSupported ??? (*)
			cellValidateRule.setReturnType(value);
		}
		else if("valid-range".equalsIgnoreCase(key)){
			// isSupported ??? (*)
			cellValidateRule.setValidRange(value);
		}
		else if("genexp".equalsIgnoreCase(key)){
			// isSupported ??? (*)
			cellValidateRule.setGenexp(value);
		}
		else if("genext-error-message".equalsIgnoreCase(key)){
			cellValidateRule.setGenexpErrorMessage(value);
		}
		else if("max-value".equalsIgnoreCase(key)){
			try{
				double maxValue = Double.parseDouble(value);
				cellValidateRule.setMaxValue(maxValue);
			}
			catch(Exception e){
				hasUnsupportedValue = true;
			}
		}
		else if("min-value".equalsIgnoreCase(key)){
			try{
				double minValue = Double.parseDouble(value);
				cellValidateRule.setMinValue(minValue);
			}
			catch(Exception e){
				hasUnsupportedValue = true;
			}
		}
		else if("max-length".equalsIgnoreCase(key)){
			try{
				int maxLength = Integer.parseInt(value);
				cellValidateRule.setMaxLength(maxLength);
			}
			catch(Exception e){
				hasUnsupportedValue = true;
			}
		}
		else if("min-length".equalsIgnoreCase(key)){
			try{
				int minLength = Integer.parseInt(value);
				cellValidateRule.setMinLength(minLength);
			}
			catch(Exception e){
				hasUnsupportedValue = true;
			}
		}
		else if("required".equalsIgnoreCase(key)){
			if("true".equalsIgnoreCase(value)){
				cellValidateRule.setRequired(true);
			}
			else if("false".equalsIgnoreCase(value)){
				cellValidateRule.setRequired(false);
			}
			else{
				hasUnsupportedValue = true;
			}
		}
		else if("detail-error-msg-in-error-list".equalsIgnoreCase(key)){
			if("true".equalsIgnoreCase(value)){
				cellValidateRule.setDetailErrorMsgInErrorList(true);
			}
			else if("false".equalsIgnoreCase(value)){
				cellValidateRule.setDetailErrorMsgInErrorList(false);
			}
			else{
				hasUnsupportedValue = true;
			}
		}
		else if("batch-error-msg-in-error-list".equalsIgnoreCase(key)){
			if("true".equalsIgnoreCase(value)){
				cellValidateRule.setBatchErrorMsgInErrorList(true);
			}
			else if("false".equalsIgnoreCase(value)){
				cellValidateRule.setBatchErrorMsgInErrorList(false);
			}
			else{
				hasUnsupportedValue = true;
			}
		}
		else{
			this.errorList.add("Unsupported rule key \"" + key + "\" in Excel template file!");
			cellValidateRule = null;
		}
		
		if(hasUnsupportedValue){
			this.errorList.add("Unsupported rule value \"" + value + "\" for rule key \"" + key + "\"");
			cellValidateRule = null;
		}
		
		/*
		if(cellValidateRule != null){
			System.out.println(key + " = " + value); // (*)
		}
		*/
		
		return cellValidateRule;
	}

	/**
	 * 
	 * 方法说明:
	 * @return
	 */
	public Map getHeadDataRuleMap() {
		return this.headDataRuleMap;
	}
	
	/**
	 * 
	 * 方法说明:
	 * @return
	 */
	public Map getHeadTitleRuleMap() {
		return this.headTitleRuleMap;
	}
	
	/**
	 * 
	 * 方法说明:
	 * @return
	 */
	public Map getDetailTitleRuleMap() {
		return this.detailTitleRuleMap;
	}
	
	/**
	 * 
	 * 方法说明:
	 * @return
	 */
	public Map getDetailDataRuleMap() {
		return this.detailDataRuleMap;
	}

	/**
	 * 
	 * 方法说明:
	 * @return
	 */
	public TemplateValidateRule getTemplateValidateRule() {
		return this.templateValidateRule;
	}

	/**
	 * 
	 * 方法说明:
	 * @return
	 */
	public List getErrorList() {
		return this.errorList;
	}
	
	/**
	 * 
	 * 方法说明:
	 * @return
	 */
	public Workbook getWorkBook(){
		return this.workbook;
	}

	public List<Map> getHeadTitleRuleList() {
		return headTitleRuleList;
	}

	public List<Map> getHeadDataRuleList() {
		return headDataRuleList;
	}

	public List<Map> getDetailTitleRuleList() {
		return detailTitleRuleList;
	}

	public List<Map> getDetailDataRuleList() {
		return detailDataRuleList;
	}
	
}
