package com.boco.RE.excel.importutil;

import com.boco.RE.excel.util.ExcelUtil;

/**
 * 导入规则的验证条件Bean
 * @Author zhuhongjiang
 * @Date 2020/1/7 下午2:57
 **/
public class TemplateValidateRule {
	private boolean isValidateDataEnabled = true; //验证数据是否必填
	private boolean isValidateSheetNameEnabled = false; //验证SHEET名称是否必填
	private boolean isValidateHeadTitleEnabled = true; //验证SHEET 头信息标题是否必填
	private boolean isValidateDetailTitleEnabled = true; //验证SHEET 行信息标题是否必填
	private boolean isMultiSheet = false; // 1.0	//验证是否允许多个SHEET
	private boolean isDetailMustExsit = true; //验证是否允许1个SHEET 多个行信息
	private boolean isBlankDetailLineAllowed = true; //是否允许行信息为空
	private boolean hasHeadTitle = true;		//存在头信息标题
	private boolean hasDetailTitle = true;		//存在行信息标题
	private boolean hasHead = true;				//存在头信息
	private boolean hasDetail = true;			//存在行信息
	private String headReturnType = "Map";		//返回Map类型
	private String headReturnMapKey = "cell-name"; //返回Map的名称
	private static final String[] supportedHeadReturnMapKeys = new String[]{"cell-name", "row-id", "column-id", "row-column"};
	private String headBeanClass;
	private String detailLayoutType = "row";
	private int detailStartIndex = 0;
	private String detailReturnType = "List";
	private String detailReturnMapKey = "cell-name";
	private static final String[] supportedDetailReturnMapKeys = new String[]{"cell-name", "row-id", "column-id", "row-column"};
	private String detailBeanClass;
	private String totalReturnType = "Bean";
	private String totalReturnMapKey = "sheet-id";
	private static final String[] supportedTotalReturnMapKeys = new String[]{"sheet-id", "sheet-name"};
	/*
	 * 是否允许配置多个SHEET规则,导入模板对应的SHEET会校验相应的SHEET规则 .
	 * 配置该参数每个规则数据只能放在一个SHEET中
	 */
	private boolean isMultiSheetRule = false ;


	public TemplateValidateRule(){

	}
	
	public String getDetailBeanClass() {
		return detailBeanClass;
	}
	public void setDetailBeanClass(String detailBeanClass) {
		this.detailBeanClass = detailBeanClass;
	}
	public String getDetailReturnMapKey() {
		return detailReturnMapKey;
	}
	public void setDetailReturnMapKey(String detailReturnMapKey) {
		this.detailReturnMapKey = detailReturnMapKey;
	}
	public String getDetailReturnType() {
		return detailReturnType;
	}
	public void setDetailReturnType(String detailReturnType) {
		this.detailReturnType = detailReturnType;
	}
	public int getDetailStartIndex() {
		return detailStartIndex;
	}
	public void setDetailStartIndex(int detailStartIndex) {
		this.detailStartIndex = detailStartIndex;
	}
	public String getDetailLayoutType() {
		return detailLayoutType;
	}
	public void setDetailLayoutType(String detailLayoutType) {
		this.detailLayoutType = detailLayoutType;
	}
	public boolean hasDetail() {
		return hasDetail;
	}
	public void setHasDetail(boolean hasDetail) {
		this.hasDetail = hasDetail;
	}
	public boolean hasDetailTitle() {
		return hasDetailTitle;
	}
	public void setHasDetailTitle(boolean hasDetailTitle) {
		this.hasDetailTitle = hasDetailTitle;
	}
	public boolean hasHead() {
		return hasHead;
	}
	public void setHasHead(boolean hasHead) {
		this.hasHead = hasHead;
	}
	public boolean hasHeadTitle() {
		return hasHeadTitle;
	}
	public void setHasHeadTitle(boolean hasHeadTitle) {
		this.hasHeadTitle = hasHeadTitle;
	}
	public String getHeadBeanClass() {
		return headBeanClass;
	}
	public void setHeadBeanClass(String headBeanClass) {
		this.headBeanClass = headBeanClass;
	}
	public String getHeadReturnMapKey() {
		return headReturnMapKey;
	}
	public void setHeadReturnMapKey(String headReturnMapKey) {
		this.headReturnMapKey = headReturnMapKey;
	}
	public String getHeadReturnType() {
		return headReturnType;
	}
	public void setHeadReturnType(String headReturnType) {
		this.headReturnType = headReturnType;
	}
	public boolean isMultiSheet() {
		return isMultiSheet;
	}
	public void setMultiSheet(boolean isMultiSheet) {
		this.isMultiSheet = isMultiSheet;
	}
	public boolean isValidateDataEnabled() {
		return isValidateDataEnabled;
	}
	public void setValidateDataEnabled(boolean isValidateDataEnabled) {
		this.isValidateDataEnabled = isValidateDataEnabled;
	}
	public boolean isValidateDetailTitleEnabled() {
		return isValidateDetailTitleEnabled;
	}
	public void setValidateDetailTitleEnabled(boolean isValidateDetailTitleEnabled) {
		this.isValidateDetailTitleEnabled = isValidateDetailTitleEnabled;
	}
	public boolean isValidateHeadTitleEnabled() {
		return isValidateHeadTitleEnabled;
	}
	public void setValidateHeadTitleEnabled(boolean isValidateHeadTitleEnabled) {
		this.isValidateHeadTitleEnabled = isValidateHeadTitleEnabled;
	}
	public boolean isValidateSheetNameEnabled() {
		return isValidateSheetNameEnabled;
	}
	public void setValidateSheetNameEnabled(boolean isValidateSheetNameEnabled) {
		this.isValidateSheetNameEnabled = isValidateSheetNameEnabled;
	}
	public boolean isBlankDetailLineAllowed() {
		return isBlankDetailLineAllowed;
	}
	public void setBlankDetailLineAllowed(boolean isBlankDetailLineAllowed) {
		this.isBlankDetailLineAllowed = isBlankDetailLineAllowed;
	}
	public String getTotalReturnMapKey() {
		return totalReturnMapKey;
	}
	public void setTotalReturnMapKey(String totalReturnMapKey) {
		this.totalReturnMapKey = totalReturnMapKey;
	}
	public String getTotalReturnType() {
		return totalReturnType;
	}
	public void setTotalReturnType(String totalReturnType) {
		this.totalReturnType = totalReturnType;
	}
	
	public static boolean isSupportedHeadReturnMapKey(String key){
		return ExcelUtil.isValueInOptions(key, supportedHeadReturnMapKeys);
	}

	public static boolean isSupportedDetailReturnMapKey(String key){
		return ExcelUtil.isValueInOptions(key, supportedDetailReturnMapKeys);
	}
	
	public static boolean isSupportedTotalReturnMapKey(String key){
		return ExcelUtil.isValueInOptions(key, supportedTotalReturnMapKeys);
	}
	
	public boolean isDetailMustExsit() {
		return isDetailMustExsit;
	}
	public void setDetailMustExsit(boolean isDetailMustExsit) {
		this.isDetailMustExsit = isDetailMustExsit;
	}
	public static String[] getSupportedDetailReturnMapKeys() {
		return supportedDetailReturnMapKeys;
	}
	public static String[] getSupportedHeadReturnMapKeys() {
		return supportedHeadReturnMapKeys;
	}
	public static String[] getSupportedTotalReturnMapKeys() {
		return supportedTotalReturnMapKeys;
	}
	public boolean isMultiSheetRule() {
		return isMultiSheetRule;
	}
	public void setMultiSheetRule(boolean isMultiSheetRule) {
		this.isMultiSheetRule = isMultiSheetRule;
	}
	
}
