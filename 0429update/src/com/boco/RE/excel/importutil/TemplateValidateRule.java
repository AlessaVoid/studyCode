package com.boco.RE.excel.importutil;

import com.boco.RE.excel.util.ExcelUtil;

/**
 * ����������֤����Bean
 * @Author zhuhongjiang
 * @Date 2020/1/7 ����2:57
 **/
public class TemplateValidateRule {
	private boolean isValidateDataEnabled = true; //��֤�����Ƿ����
	private boolean isValidateSheetNameEnabled = false; //��֤SHEET�����Ƿ����
	private boolean isValidateHeadTitleEnabled = true; //��֤SHEET ͷ��Ϣ�����Ƿ����
	private boolean isValidateDetailTitleEnabled = true; //��֤SHEET ����Ϣ�����Ƿ����
	private boolean isMultiSheet = false; // 1.0	//��֤�Ƿ�������SHEET
	private boolean isDetailMustExsit = true; //��֤�Ƿ�����1��SHEET �������Ϣ
	private boolean isBlankDetailLineAllowed = true; //�Ƿ���������ϢΪ��
	private boolean hasHeadTitle = true;		//����ͷ��Ϣ����
	private boolean hasDetailTitle = true;		//��������Ϣ����
	private boolean hasHead = true;				//����ͷ��Ϣ
	private boolean hasDetail = true;			//��������Ϣ
	private String headReturnType = "Map";		//����Map����
	private String headReturnMapKey = "cell-name"; //����Map������
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
	 * �Ƿ��������ö��SHEET����,����ģ���Ӧ��SHEET��У����Ӧ��SHEET���� .
	 * ���øò���ÿ����������ֻ�ܷ���һ��SHEET��
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
