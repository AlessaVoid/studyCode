package com.boco.RE.excel.importutil;

import com.boco.RE.excel.util.ExcelUtil;

/**
 * 导入模板中备注实体Bean
 * @Author zhuhongjiang
 * @Date 2020/1/7 下午2:42
 **/
public class CellValidateRule {

	public static final String CELL_TYPE_OF_HEAD_TITLE = "head-title";
	public static final String CELL_TYPE_OF_HEAD_DATA = "head-data";
	public static final String CELL_TYPE_OF_DETAIL_TITLE = "detail-title";
	public static final String CELL_TYPE_OF_DETAIL_DATA = "detail-data";
	private static final String[] supportedCellTypes = new String[]{CELL_TYPE_OF_HEAD_TITLE, CELL_TYPE_OF_HEAD_DATA, CELL_TYPE_OF_DETAIL_TITLE, CELL_TYPE_OF_DETAIL_DATA};

	private String cellName; 							// cell英文名称
	private String cellTitle; 							// cell中文名称
	private int rowId; 									// 行ID
	private short columnId; 							// 列ID
	private boolean required = false; 					// 是否必填项
	private double maxValue = Double.MAX_VALUE;
	private double minValue = Double.MIN_VALUE;
	private int maxLength = -999;
	private int minLength = 0;

	private String cellType = CELL_TYPE_OF_HEAD_DATA; 	// head-title/detail-title/head-data/detail-data
	private String validateType = "String"; 			// 验证类型String/Date/Email/Integer/Double......
	private String returnType = "String"; 				// 返回类型 String/Date/Integer/Double......
	private String validRange = "this"; 				// this/row+n/row+all/column+n/column+all/row-n/row-all/column-n/column-all/all
	private String genexp;
	private String genexpErrorMessage;

	private boolean detailErrorMsgInErrorList = true;
	private boolean batchErrorMsgInErrorList = true;


	public CellValidateRule(){

	}

	public boolean hasBatchErrorMsgInErrorList() {
		return batchErrorMsgInErrorList;
	}

	public void setBatchErrorMsgInErrorList(boolean batchErrorMsgInErrorList) {
		this.batchErrorMsgInErrorList = batchErrorMsgInErrorList;
	}

	public String getCellType() {
		return cellType;
	}

	public void setCellType(String cellType) {
		this.cellType = cellType;
	}

	public short getColumnId() {
		return columnId;
	}

	public void setColumnId(short columnId) {
		this.columnId = columnId;
	}

	public boolean hasDetailErrorMsgInErrorList() {
		return detailErrorMsgInErrorList;
	}

	public void setDetailErrorMsgInErrorList(boolean detailErrorMsgInErrorList) {
		this.detailErrorMsgInErrorList = detailErrorMsgInErrorList;
	}

	public String getGenexp() {
		return genexp;
	}

	public void setGenexp(String genexp) {
		this.genexp = genexp;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public double getMinValue() {
		return minValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	public String getCellName() {
		return cellName;
	}

	public void setCellName(String cellName) {
		this.cellName = cellName;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public String getCellTitle() {
		return cellTitle;
	}

	public void setCellTitle(String cellTitle) {
		this.cellTitle = cellTitle;
	}

	public String getValidateType() {
		return validateType;
	}

	public void setValidateType(String validateType) {
		this.validateType = validateType;
	}

	public String getValidRange() {
		return validRange;
	}

	public void setValidRange(String validRange) {
		this.validRange = validRange;
	}

	public String getGenexpErrorMessage() {
		return genexpErrorMessage;
	}

	public void setGenexpErrorMessage(String genexpErrorMessage) {
		this.genexpErrorMessage = genexpErrorMessage;
	}

	public static boolean isSupportedCellType(String type){
		return ExcelUtil.isValueInOptions(type, supportedCellTypes);
	}

}
