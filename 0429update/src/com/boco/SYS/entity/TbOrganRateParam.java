package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * 
 * 
 * �������ֲ�����ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20160315 ������      �����½�
 * </pre>
 */
public class TbOrganRateParam extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ����ID */
		@EntityParaAnno(zhName="����ID")
	private String id;
	/** �÷������� bad_condition  ������� deposit_loan_ratio  ��Ӫ��������� new_deposit_ratio �·����������� deposit_struct ����ṹ scale ����ģռ�÷Ѻ͹�ģ���÷� plan_submit_deviation  �ƻ�����ƫ��� plan_execute_deviation_month_mid �ƻ�ִ��ƫ���-��ĩ����ƫ��� plan_execute_deviation_month_init �ƻ�ִ��ƫ���-��ĩ�³�ƫ��� adj_count ����Ƶ�� scale_and_adj_count δ��������ģռ�÷Ѻ͹�ģ���÷Ѻ͵���Ƶ�� */
		@EntityParaAnno(zhName="�÷������� bad_condition  ������� deposit_loan_ratio  ��Ӫ��������� new_deposit_ratio �·����������� deposit_struct ����ṹ scale ����ģռ�÷Ѻ͹�ģ���÷� plan_submit_deviation  �ƻ�����ƫ��� plan_execute_deviation_month_mid �ƻ�ִ��ƫ���-��ĩ����ƫ��� plan_execute_deviation_month_init �ƻ�ִ��ƫ���-��ĩ�³�ƫ��� adj_count ����Ƶ�� scale_and_adj_count δ��������ģռ�÷Ѻ͹�ģ���÷Ѻ͵���Ƶ��")
	private String elementType;
	/** Ŀ���ֵ */
		@EntityParaAnno(zhName="Ŀ���ֵ")
	private BigDecimal targetScore;
	/** ��С�÷�ֵ */
		@EntityParaAnno(zhName="��С�÷�ֵ")
	private BigDecimal minTargetScore;
	/** ���÷�ֵ */
		@EntityParaAnno(zhName="���÷�ֵ")
	private BigDecimal maxTargetScore;
	/** �������� */
		@EntityParaAnno(zhName="��������")
	private BigDecimal low;
	/** �������� */
		@EntityParaAnno(zhName="��������")
	private BigDecimal high;
	/** ��������ֱ�ӵ÷� */
		@EntityParaAnno(zhName="��������ֱ�ӵ÷�")
	private BigDecimal directScore;
	/** ����Ƶ�δ��� */
		@EntityParaAnno(zhName="����Ƶ�δ���")
	private BigDecimal adjCount;
	/** ÿlow_high_var��varscore�� */
		@EntityParaAnno(zhName="ÿlow_high_var��varscore��")
	private BigDecimal lowHighVar;
	/** ÿlow_high_var��varscore�� */
		@EntityParaAnno(zhName="ÿlow_high_var��varscore��")
	private BigDecimal varScore;
	/** ����Ƶ��3��δ�۷ֵ÷� */
		@EntityParaAnno(zhName="����Ƶ��3��δ�۷ֵ÷�")
	private BigDecimal adjCountThree;
	/** ����Ƶ��2��δ�۷ֵ÷� */
		@EntityParaAnno(zhName="����Ƶ��2��δ�۷ֵ÷�")
	private BigDecimal adjCountTwo;
	/** ����ģռ�÷Ѻ͹�ģ���÷�3��δ�۷ֵ÷� */
		@EntityParaAnno(zhName="����ģռ�÷Ѻ͹�ģ���÷�3��δ�۷ֵ÷�")
	private BigDecimal scaleThree;
	/** ����ģռ�÷Ѻ͹�ģ���÷�2��δ�۷ֵ÷� */
		@EntityParaAnno(zhName="����ģռ�÷Ѻ͹�ģ���÷�2��δ�۷ֵ÷�")
	private BigDecimal scaleTwo;
	/** ����ʱ�� */
		@EntityParaAnno(zhName="����ʱ��")
	private String createTime;
	/** ����ʱ�� */
		@EntityParaAnno(zhName="����ʱ��")
	private String updateTime;
	/** ������Ա */
		@EntityParaAnno(zhName="������Ա")
	private String updateOper;
	/** �÷��������� */
	@EntityParaAnno(zhName="�÷���������")
	private String elementTypeName;
	/** �������� */
	@EntityParaAnno(zhName="��������")
	private Integer orderRate;
	/** �������ֵ�һ����Ȩ�� */
	@EntityParaAnno(zhName="�������ֵ�һ����Ȩ��")
	private BigDecimal scoreOne;
	/** �������ֵڶ�����Ȩ�� */
	@EntityParaAnno(zhName="�������ֵڶ�����Ȩ��")
	private BigDecimal scoreTwo;
	/** �������ֵ�������Ȩ�� */
	@EntityParaAnno(zhName="�������ֵ�������Ȩ��")
	private BigDecimal scoreThree;
	
	/** setter\getter���� */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getElementType() {
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

	public BigDecimal getTargetScore() {
		return targetScore;
	}

	public void setTargetScore(BigDecimal targetScore) {
		this.targetScore = targetScore;
	}

	public BigDecimal getMinTargetScore() {
		return minTargetScore;
	}

	public void setMinTargetScore(BigDecimal minTargetScore) {
		this.minTargetScore = minTargetScore;
	}

	public BigDecimal getMaxTargetScore() {
		return maxTargetScore;
	}

	public void setMaxTargetScore(BigDecimal maxTargetScore) {
		this.maxTargetScore = maxTargetScore;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getDirectScore() {
		return directScore;
	}

	public void setDirectScore(BigDecimal directScore) {
		this.directScore = directScore;
	}

	public BigDecimal getAdjCount() {
		return adjCount;
	}

	public void setAdjCount(BigDecimal adjCount) {
		this.adjCount = adjCount;
	}

	public BigDecimal getLowHighVar() {
		return lowHighVar;
	}

	public void setLowHighVar(BigDecimal lowHighVar) {
		this.lowHighVar = lowHighVar;
	}

	public BigDecimal getVarScore() {
		return varScore;
	}

	public void setVarScore(BigDecimal varScore) {
		this.varScore = varScore;
	}

	public BigDecimal getAdjCountThree() {
		return adjCountThree;
	}

	public void setAdjCountThree(BigDecimal adjCountThree) {
		this.adjCountThree = adjCountThree;
	}

	public BigDecimal getAdjCountTwo() {
		return adjCountTwo;
	}

	public void setAdjCountTwo(BigDecimal adjCountTwo) {
		this.adjCountTwo = adjCountTwo;
	}

	public BigDecimal getScaleThree() {
		return scaleThree;
	}

	public void setScaleThree(BigDecimal scaleThree) {
		this.scaleThree = scaleThree;
	}

	public BigDecimal getScaleTwo() {
		return scaleTwo;
	}

	public void setScaleTwo(BigDecimal scaleTwo) {
		this.scaleTwo = scaleTwo;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateOper() {
		return updateOper;
	}

	public void setUpdateOper(String updateOper) {
		this.updateOper = updateOper;
	}

	public String getElementTypeName() {
		return elementTypeName;
	}

	public void setElementTypeName(String elementTypeName) {
		this.elementTypeName = elementTypeName;
	}

	public Integer getOrderRate() {
		return orderRate;
	}

	public void setOrderRate(Integer orderRate) {
		this.orderRate = orderRate;
	}

	public BigDecimal getScoreOne() {
		return scoreOne;
	}

	public void setScoreOne(BigDecimal scoreOne) {
		this.scoreOne = scoreOne;
	}

	public BigDecimal getScoreTwo() {
		return scoreTwo;
	}

	public void setScoreTwo(BigDecimal scoreTwo) {
		this.scoreTwo = scoreTwo;
	}

	public BigDecimal getScoreThree() {
		return scoreThree;
	}

	public void setScoreThree(BigDecimal scoreThree) {
		this.scoreThree = scoreThree;
	}
}