package com.boco.PUB.POJO.DTO;

import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * �������뱨��Ҫ��¼����ϸ��ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbReqresetDetailDTO extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ����id */
		@EntityParaAnno(zhName="����id")
	private Integer reqdresetId;
	/** ��������id */
		@EntityParaAnno(zhName="��������id")
	private Integer reqdresetRefId;
	/** �����id */
		@EntityParaAnno(zhName="�����id")
	private String reqdresetOrgan;
	/** ��λ */
		@EntityParaAnno(zhName="��λ")
	private Integer reqdresetUnit;
	/** �ϱ��� */
		@EntityParaAnno(zhName="�ϱ���")
	private String reqdresetOper;
	/** ��Ŀ״̬ */
		@EntityParaAnno(zhName="��Ŀ״̬")
	private Integer reqdresetState;
	/** С������ȵ������� */
		@EntityParaAnno(zhName="С������ȵ�������")
	private Integer reqdresetSmallamountLoanDir;
	/** С�����Ԥ�Ƶ������ */
		@EntityParaAnno(zhName="С�����Ԥ�Ƶ������")
	private String reqdresetSmallamountLoanAmt;
	/** ���������ȵ������� */
		@EntityParaAnno(zhName="���������ȵ�������")
	private Integer reqdresetOtherLoanDir;
	/** ��������Ԥ�Ƶ������ */
		@EntityParaAnno(zhName="��������Ԥ�Ƶ������")
	private String reqdresetOtherLoanAmt;
	/** ���̴����ȵ������� */
		@EntityParaAnno(zhName="���̴����ȵ�������")
	private Integer reqdresetPerbusineLoanDir;
	/** ���̴���Ԥ�Ƶ������ */
		@EntityParaAnno(zhName="���̴���Ԥ�Ƶ������")
	private String reqdresetPerbusineLoanAmt;
	/** С��ҵ��ȵ������� */
		@EntityParaAnno(zhName="С��ҵ��ȵ�������")
	private Integer reqdresetSmallbusineDir;
	/** С��ҵԤ�Ƶ������ */
		@EntityParaAnno(zhName="С��ҵԤ�Ƶ������")
	private String reqdresetSmallbusineAmt;
	/** С��ҵ�����ȵ������� */
		@EntityParaAnno(zhName="С��ҵ�����ȵ�������")
	private Integer reqdresetSmallbusinefactorDir;
	/** С��ҵ����Ԥ�Ƶ������ */
		@EntityParaAnno(zhName="С��ҵ����Ԥ�Ƶ������")
	private String reqdresetSmallbusinefactorAmt;
	/** ס�����Ҵ����ȵ������� */
		@EntityParaAnno(zhName="ס�����Ҵ����ȵ�������")
	private Integer reqdresetHousemortLoanDir;
	/** ס�����Ҵ���Ԥ�Ƶ������ */
		@EntityParaAnno(zhName="ס�����Ҵ���Ԥ�Ƶ������")
	private String reqdresetHousemortLoanAmt;
	/** �������Ѵ����ȵ������� */
		@EntityParaAnno(zhName="�������Ѵ����ȵ�������")
	private Integer reqdresetOtherconsumLoanDir;
	/** �������Ѵ���Ԥ�Ƶ������ */
		@EntityParaAnno(zhName="�������Ѵ���Ԥ�Ƶ������")
	private String reqdresetOtherconsumLoanAmt;
	/** ��Ӧ����ȵ������� */
		@EntityParaAnno(zhName="��Ӧ����ȵ�������")
	private Integer reqdresetSupplylineDir;
	/** ��Ӧ��Ԥ�Ƶ������ */
		@EntityParaAnno(zhName="��Ӧ��Ԥ�Ƶ������")
	private String reqdresetSupplylineAmt;
	/** ����ó�����ʶ�ȵ������� */
		@EntityParaAnno(zhName="����ó�����ʶ�ȵ�������")
	private Integer reqdresetDomestictradefinanceDir;
	/** ����ó������Ԥ�Ƶ������ */
		@EntityParaAnno(zhName="����ó������Ԥ�Ƶ������")
	private String reqdresetDomestictradefinanceAmt;
	/** ����ó�����ʶ�ȵ������� */
		@EntityParaAnno(zhName="����ó�����ʶ�ȵ�������")
	private Integer reqdresetIntertradefinanceDir;
	/** ����ó������Ԥ�Ƶ������ */
		@EntityParaAnno(zhName="����ó������Ԥ�Ƶ������")
	private String reqdresetIntertradefinanceAmt;
	/** ������˾�����ȵ������� */
		@EntityParaAnno(zhName="������˾�����ȵ�������")
	private Integer reqdresetOthercorporateLoanDir;
	/** ������˾����Ԥ�Ƶ������ */
		@EntityParaAnno(zhName="������˾����Ԥ�Ƶ������")
	private String reqdresetOthercorporateLoanAmt;
	/** �����˻�͸֧��ȵ������� */
		@EntityParaAnno(zhName="�����˻�͸֧��ȵ�������")
	private Integer reqdresetCorporateaccoverdraftLoanDir;
	/** �����˻�͸֧Ԥ�Ƶ������ */
		@EntityParaAnno(zhName="�����˻�͸֧Ԥ�Ƶ������")
	private String reqdresetCorporateaccoverdraftLoanAmt;
	/** ��ũ��˾�����ȵ������� */
		@EntityParaAnno(zhName="��ũ��˾�����ȵ�������")
	private Integer reqdresetSancompanyLoanDir;
	/** ��ũ��˾����Ԥ�Ƶ������ */
		@EntityParaAnno(zhName="��ũ��˾����Ԥ�Ƶ������")
	private String reqdresetSancompanyLoanAmt;
	/** ���������ȵ������� */
		@EntityParaAnno(zhName="���������ȵ�������")
	private Integer reqdresetMergerLoanDir;
	/** ��������Ԥ�Ƶ������ */
		@EntityParaAnno(zhName="��������Ԥ�Ƶ������")
	private String reqdresetMergerLoanAmt;
	/** �������ȵ������� */
		@EntityParaAnno(zhName="�������ȵ�������")
	private Integer reqdresetAlladvanceLoanDir;
	/** ������Ԥ�Ƶ������ */
		@EntityParaAnno(zhName="������Ԥ�Ƶ������")
	private String reqdresetAlladvanceLoanAmt;
	/** ��λ͸֧��ȵ������� */
		@EntityParaAnno(zhName="��λ͸֧��ȵ�������")
	private Integer reqdresetUnitoverdraftDir;
	/** ��λ͸֧Ԥ�Ƶ������ */
		@EntityParaAnno(zhName="��λ͸֧Ԥ�Ƶ������")
	private String reqdresetUnitoverdraftAmt;
	/** ת����ȵ������� */
		@EntityParaAnno(zhName="ת����ȵ�������")
	private Integer reqdresetRepostDir;
	/** ת��Ԥ�Ƶ������ */
		@EntityParaAnno(zhName="ת��Ԥ�Ƶ������")
	private String reqdresetRepostAmt;
	/** ֱ����ȵ������� */
		@EntityParaAnno(zhName="ֱ����ȵ�������")
	private Integer reqdresetStraightDir;
	/** ֱ��Ԥ�Ƶ������ */
		@EntityParaAnno(zhName="ֱ��Ԥ�Ƶ������")
	private String reqdresetStraightAmt;
	/** ���븣��͢������ң���ȵ������� */
		@EntityParaAnno(zhName="���븣��͢������ң���ȵ�������")
	private Integer reqdresetBuyfftrmbDir;
	/** ���븣��͢������ң�Ԥ�Ƶ������ */
		@EntityParaAnno(zhName="���븣��͢������ң�Ԥ�Ƶ������")
	private String reqdresetBuyfftrmbAmt;
	/** ���ʹ�˾�����ȵ������� */
		@EntityParaAnno(zhName="���ʹ�˾�����ȵ�������")
	private Integer reqdresetIntercorporateLoanDir;
	/** ���ʹ�˾����Ԥ�Ƶ������ */
		@EntityParaAnno(zhName="���ʹ�˾����Ԥ�Ƶ������")
	private String reqdresetIntercorporateLoanAmt;
	/** ר�����ʶ�ȵ������� */
		@EntityParaAnno(zhName="ר�����ʶ�ȵ�������")
	private Integer reqdresetSpecialfinaceDir;
	/** ר������Ԥ�Ƶ������ */
		@EntityParaAnno(zhName="ר������Ԥ�Ƶ������")
	private String reqdresetSpecialfinaceAmt;
	/** ����͸֧��ȵ������� */
		@EntityParaAnno(zhName="����͸֧��ȵ�������")
	private Integer reqdresetPersonoverdraftDir;
	/** ����͸֧Ԥ�Ƶ������ */
		@EntityParaAnno(zhName="����͸֧Ԥ�Ƶ������")
	private String reqdresetPersonoverdraftAmt;
	/** ��Ҵ����ȵ������� */
		@EntityParaAnno(zhName="��Ҵ����ȵ�������")
	private Integer reqdresetForeigncurrencyLoanDir;
	/** ��Ҵ���Ԥ�Ƶ������ */
		@EntityParaAnno(zhName="��Ҵ���Ԥ�Ƶ������")
	private String reqdresetForeigncurrencyLoanAmt;
	/** ���븣��͢����ң���ȵ������� */
		@EntityParaAnno(zhName="���븣��͢����ң���ȵ�������")
	private Integer reqdresetBuyfftforeigncurDir;
	/** ���븣��͢����ң�Ԥ�Ƶ������ */
		@EntityParaAnno(zhName="���븣��͢����ң�Ԥ�Ƶ������")
	private String reqdresetBuyfftforeigncurAmt;
	/** ������ȵ������� */
		@EntityParaAnno(zhName="������ȵ�������")
	private Integer reqdresetOtherDir;
	/** ����Ԥ�Ƶ������ */
		@EntityParaAnno(zhName="����Ԥ�Ƶ������")
	private String reqdresetOtherAmt;
	/** ����ʱ�� */
		@EntityParaAnno(zhName="����ʱ��")
	private String reqdresetCreatetime;
	/** ����ʱ�� */
		@EntityParaAnno(zhName="����ʱ��")
	private String reqdresetUpdatetime;
	/** ������Ա */
		@EntityParaAnno(zhName="������Ա")
	private String reqdresetUpdateoper;
	/** ��ŷ�����ȵ������� */
		@EntityParaAnno(zhName="��ŷ�����ȵ�������")
	private Integer reqdresetDisassemblyDir;
	/** ��ŷ���Ԥ�Ƶ������ */
		@EntityParaAnno(zhName="��ŷ���Ԥ�Ƶ������")
	private String reqdresetDisassemblyAmt;
	
	/** setter\getter���� */
	/** ����id */
	public void setReqdresetId(Integer reqdresetId) {
		this.reqdresetId = reqdresetId;
	}
	public Integer getReqdresetId() {
		return this.reqdresetId;
	}
	/** ��������id */
	public void setReqdresetRefId(Integer reqdresetRefId) {
		this.reqdresetRefId = reqdresetRefId;
	}
	public Integer getReqdresetRefId() {
		return this.reqdresetRefId;
	}
	/** �����id */
	public void setReqdresetOrgan(String reqdresetOrgan) {
		this.reqdresetOrgan = reqdresetOrgan == null ? null : reqdresetOrgan.trim();
	}
	public String getReqdresetOrgan() {
		return this.reqdresetOrgan;
	}
	/** ��λ */
	public void setReqdresetUnit(Integer reqdresetUnit) {
		this.reqdresetUnit = reqdresetUnit;
	}
	public Integer getReqdresetUnit() {
		return this.reqdresetUnit;
	}
	/** �ϱ��� */
	public void setReqdresetOper(String reqdresetOper) {
		this.reqdresetOper = reqdresetOper == null ? null : reqdresetOper.trim();
	}
	public String getReqdresetOper() {
		return this.reqdresetOper;
	}
	/** ��Ŀ״̬ */
	public void setReqdresetState(Integer reqdresetState) {
		this.reqdresetState = reqdresetState;
	}
	public Integer getReqdresetState() {
		return this.reqdresetState;
	}
	/** С������ȵ������� */
	public void setReqdresetSmallamountLoanDir(Integer reqdresetSmallamountLoanDir) {
		this.reqdresetSmallamountLoanDir = reqdresetSmallamountLoanDir;
	}
	public Integer getReqdresetSmallamountLoanDir() {
		return this.reqdresetSmallamountLoanDir;
	}
	/** С�����Ԥ�Ƶ������ */
	public void setReqdresetSmallamountLoanAmt(String reqdresetSmallamountLoanAmt) {
		this.reqdresetSmallamountLoanAmt = reqdresetSmallamountLoanAmt == null ? null : reqdresetSmallamountLoanAmt.trim();
	}
	public String getReqdresetSmallamountLoanAmt() {
		return this.reqdresetSmallamountLoanAmt;
	}
	/** ���������ȵ������� */
	public void setReqdresetOtherLoanDir(Integer reqdresetOtherLoanDir) {
		this.reqdresetOtherLoanDir = reqdresetOtherLoanDir;
	}
	public Integer getReqdresetOtherLoanDir() {
		return this.reqdresetOtherLoanDir;
	}
	/** ��������Ԥ�Ƶ������ */
	public void setReqdresetOtherLoanAmt(String reqdresetOtherLoanAmt) {
		this.reqdresetOtherLoanAmt = reqdresetOtherLoanAmt == null ? null : reqdresetOtherLoanAmt.trim();
	}
	public String getReqdresetOtherLoanAmt() {
		return this.reqdresetOtherLoanAmt;
	}
	/** ���̴����ȵ������� */
	public void setReqdresetPerbusineLoanDir(Integer reqdresetPerbusineLoanDir) {
		this.reqdresetPerbusineLoanDir = reqdresetPerbusineLoanDir;
	}
	public Integer getReqdresetPerbusineLoanDir() {
		return this.reqdresetPerbusineLoanDir;
	}
	/** ���̴���Ԥ�Ƶ������ */
	public void setReqdresetPerbusineLoanAmt(String reqdresetPerbusineLoanAmt) {
		this.reqdresetPerbusineLoanAmt = reqdresetPerbusineLoanAmt == null ? null : reqdresetPerbusineLoanAmt.trim();
	}
	public String getReqdresetPerbusineLoanAmt() {
		return this.reqdresetPerbusineLoanAmt;
	}
	/** С��ҵ��ȵ������� */
	public void setReqdresetSmallbusineDir(Integer reqdresetSmallbusineDir) {
		this.reqdresetSmallbusineDir = reqdresetSmallbusineDir;
	}
	public Integer getReqdresetSmallbusineDir() {
		return this.reqdresetSmallbusineDir;
	}
	/** С��ҵԤ�Ƶ������ */
	public void setReqdresetSmallbusineAmt(String reqdresetSmallbusineAmt) {
		this.reqdresetSmallbusineAmt = reqdresetSmallbusineAmt == null ? null : reqdresetSmallbusineAmt.trim();
	}
	public String getReqdresetSmallbusineAmt() {
		return this.reqdresetSmallbusineAmt;
	}
	/** С��ҵ�����ȵ������� */
	public void setReqdresetSmallbusinefactorDir(Integer reqdresetSmallbusinefactorDir) {
		this.reqdresetSmallbusinefactorDir = reqdresetSmallbusinefactorDir;
	}
	public Integer getReqdresetSmallbusinefactorDir() {
		return this.reqdresetSmallbusinefactorDir;
	}
	/** С��ҵ����Ԥ�Ƶ������ */
	public void setReqdresetSmallbusinefactorAmt(String reqdresetSmallbusinefactorAmt) {
		this.reqdresetSmallbusinefactorAmt = reqdresetSmallbusinefactorAmt == null ? null : reqdresetSmallbusinefactorAmt.trim();
	}
	public String getReqdresetSmallbusinefactorAmt() {
		return this.reqdresetSmallbusinefactorAmt;
	}
	/** ס�����Ҵ����ȵ������� */
	public void setReqdresetHousemortLoanDir(Integer reqdresetHousemortLoanDir) {
		this.reqdresetHousemortLoanDir = reqdresetHousemortLoanDir;
	}
	public Integer getReqdresetHousemortLoanDir() {
		return this.reqdresetHousemortLoanDir;
	}
	/** ס�����Ҵ���Ԥ�Ƶ������ */
	public void setReqdresetHousemortLoanAmt(String reqdresetHousemortLoanAmt) {
		this.reqdresetHousemortLoanAmt = reqdresetHousemortLoanAmt == null ? null : reqdresetHousemortLoanAmt.trim();
	}
	public String getReqdresetHousemortLoanAmt() {
		return this.reqdresetHousemortLoanAmt;
	}
	/** �������Ѵ����ȵ������� */
	public void setReqdresetOtherconsumLoanDir(Integer reqdresetOtherconsumLoanDir) {
		this.reqdresetOtherconsumLoanDir = reqdresetOtherconsumLoanDir;
	}
	public Integer getReqdresetOtherconsumLoanDir() {
		return this.reqdresetOtherconsumLoanDir;
	}
	/** �������Ѵ���Ԥ�Ƶ������ */
	public void setReqdresetOtherconsumLoanAmt(String reqdresetOtherconsumLoanAmt) {
		this.reqdresetOtherconsumLoanAmt = reqdresetOtherconsumLoanAmt == null ? null : reqdresetOtherconsumLoanAmt.trim();
	}
	public String getReqdresetOtherconsumLoanAmt() {
		return this.reqdresetOtherconsumLoanAmt;
	}
	/** ��Ӧ����ȵ������� */
	public void setReqdresetSupplylineDir(Integer reqdresetSupplylineDir) {
		this.reqdresetSupplylineDir = reqdresetSupplylineDir;
	}
	public Integer getReqdresetSupplylineDir() {
		return this.reqdresetSupplylineDir;
	}
	/** ��Ӧ��Ԥ�Ƶ������ */
	public void setReqdresetSupplylineAmt(String reqdresetSupplylineAmt) {
		this.reqdresetSupplylineAmt = reqdresetSupplylineAmt == null ? null : reqdresetSupplylineAmt.trim();
	}
	public String getReqdresetSupplylineAmt() {
		return this.reqdresetSupplylineAmt;
	}
	/** ����ó�����ʶ�ȵ������� */
	public void setReqdresetDomestictradefinanceDir(Integer reqdresetDomestictradefinanceDir) {
		this.reqdresetDomestictradefinanceDir = reqdresetDomestictradefinanceDir;
	}
	public Integer getReqdresetDomestictradefinanceDir() {
		return this.reqdresetDomestictradefinanceDir;
	}
	/** ����ó������Ԥ�Ƶ������ */
	public void setReqdresetDomestictradefinanceAmt(String reqdresetDomestictradefinanceAmt) {
		this.reqdresetDomestictradefinanceAmt = reqdresetDomestictradefinanceAmt == null ? null : reqdresetDomestictradefinanceAmt.trim();
	}
	public String getReqdresetDomestictradefinanceAmt() {
		return this.reqdresetDomestictradefinanceAmt;
	}
	/** ����ó�����ʶ�ȵ������� */
	public void setReqdresetIntertradefinanceDir(Integer reqdresetIntertradefinanceDir) {
		this.reqdresetIntertradefinanceDir = reqdresetIntertradefinanceDir;
	}
	public Integer getReqdresetIntertradefinanceDir() {
		return this.reqdresetIntertradefinanceDir;
	}
	/** ����ó������Ԥ�Ƶ������ */
	public void setReqdresetIntertradefinanceAmt(String reqdresetIntertradefinanceAmt) {
		this.reqdresetIntertradefinanceAmt = reqdresetIntertradefinanceAmt == null ? null : reqdresetIntertradefinanceAmt.trim();
	}
	public String getReqdresetIntertradefinanceAmt() {
		return this.reqdresetIntertradefinanceAmt;
	}
	/** ������˾�����ȵ������� */
	public void setReqdresetOthercorporateLoanDir(Integer reqdresetOthercorporateLoanDir) {
		this.reqdresetOthercorporateLoanDir = reqdresetOthercorporateLoanDir;
	}
	public Integer getReqdresetOthercorporateLoanDir() {
		return this.reqdresetOthercorporateLoanDir;
	}
	/** ������˾����Ԥ�Ƶ������ */
	public void setReqdresetOthercorporateLoanAmt(String reqdresetOthercorporateLoanAmt) {
		this.reqdresetOthercorporateLoanAmt = reqdresetOthercorporateLoanAmt == null ? null : reqdresetOthercorporateLoanAmt.trim();
	}
	public String getReqdresetOthercorporateLoanAmt() {
		return this.reqdresetOthercorporateLoanAmt;
	}
	/** �����˻�͸֧��ȵ������� */
	public void setReqdresetCorporateaccoverdraftLoanDir(Integer reqdresetCorporateaccoverdraftLoanDir) {
		this.reqdresetCorporateaccoverdraftLoanDir = reqdresetCorporateaccoverdraftLoanDir;
	}
	public Integer getReqdresetCorporateaccoverdraftLoanDir() {
		return this.reqdresetCorporateaccoverdraftLoanDir;
	}
	/** �����˻�͸֧Ԥ�Ƶ������ */
	public void setReqdresetCorporateaccoverdraftLoanAmt(String reqdresetCorporateaccoverdraftLoanAmt) {
		this.reqdresetCorporateaccoverdraftLoanAmt = reqdresetCorporateaccoverdraftLoanAmt == null ? null : reqdresetCorporateaccoverdraftLoanAmt.trim();
	}
	public String getReqdresetCorporateaccoverdraftLoanAmt() {
		return this.reqdresetCorporateaccoverdraftLoanAmt;
	}
	/** ��ũ��˾�����ȵ������� */
	public void setReqdresetSancompanyLoanDir(Integer reqdresetSancompanyLoanDir) {
		this.reqdresetSancompanyLoanDir = reqdresetSancompanyLoanDir;
	}
	public Integer getReqdresetSancompanyLoanDir() {
		return this.reqdresetSancompanyLoanDir;
	}
	/** ��ũ��˾����Ԥ�Ƶ������ */
	public void setReqdresetSancompanyLoanAmt(String reqdresetSancompanyLoanAmt) {
		this.reqdresetSancompanyLoanAmt = reqdresetSancompanyLoanAmt == null ? null : reqdresetSancompanyLoanAmt.trim();
	}
	public String getReqdresetSancompanyLoanAmt() {
		return this.reqdresetSancompanyLoanAmt;
	}
	/** ���������ȵ������� */
	public void setReqdresetMergerLoanDir(Integer reqdresetMergerLoanDir) {
		this.reqdresetMergerLoanDir = reqdresetMergerLoanDir;
	}
	public Integer getReqdresetMergerLoanDir() {
		return this.reqdresetMergerLoanDir;
	}
	/** ��������Ԥ�Ƶ������ */
	public void setReqdresetMergerLoanAmt(String reqdresetMergerLoanAmt) {
		this.reqdresetMergerLoanAmt = reqdresetMergerLoanAmt == null ? null : reqdresetMergerLoanAmt.trim();
	}
	public String getReqdresetMergerLoanAmt() {
		return this.reqdresetMergerLoanAmt;
	}
	/** �������ȵ������� */
	public void setReqdresetAlladvanceLoanDir(Integer reqdresetAlladvanceLoanDir) {
		this.reqdresetAlladvanceLoanDir = reqdresetAlladvanceLoanDir;
	}
	public Integer getReqdresetAlladvanceLoanDir() {
		return this.reqdresetAlladvanceLoanDir;
	}
	/** ������Ԥ�Ƶ������ */
	public void setReqdresetAlladvanceLoanAmt(String reqdresetAlladvanceLoanAmt) {
		this.reqdresetAlladvanceLoanAmt = reqdresetAlladvanceLoanAmt == null ? null : reqdresetAlladvanceLoanAmt.trim();
	}
	public String getReqdresetAlladvanceLoanAmt() {
		return this.reqdresetAlladvanceLoanAmt;
	}
	/** ��λ͸֧��ȵ������� */
	public void setReqdresetUnitoverdraftDir(Integer reqdresetUnitoverdraftDir) {
		this.reqdresetUnitoverdraftDir = reqdresetUnitoverdraftDir;
	}
	public Integer getReqdresetUnitoverdraftDir() {
		return this.reqdresetUnitoverdraftDir;
	}
	/** ��λ͸֧Ԥ�Ƶ������ */
	public void setReqdresetUnitoverdraftAmt(String reqdresetUnitoverdraftAmt) {
		this.reqdresetUnitoverdraftAmt = reqdresetUnitoverdraftAmt == null ? null : reqdresetUnitoverdraftAmt.trim();
	}
	public String getReqdresetUnitoverdraftAmt() {
		return this.reqdresetUnitoverdraftAmt;
	}
	/** ת����ȵ������� */
	public void setReqdresetRepostDir(Integer reqdresetRepostDir) {
		this.reqdresetRepostDir = reqdresetRepostDir;
	}
	public Integer getReqdresetRepostDir() {
		return this.reqdresetRepostDir;
	}
	/** ת��Ԥ�Ƶ������ */
	public void setReqdresetRepostAmt(String reqdresetRepostAmt) {
		this.reqdresetRepostAmt = reqdresetRepostAmt == null ? null : reqdresetRepostAmt.trim();
	}
	public String getReqdresetRepostAmt() {
		return this.reqdresetRepostAmt;
	}
	/** ֱ����ȵ������� */
	public void setReqdresetStraightDir(Integer reqdresetStraightDir) {
		this.reqdresetStraightDir = reqdresetStraightDir;
	}
	public Integer getReqdresetStraightDir() {
		return this.reqdresetStraightDir;
	}
	/** ֱ��Ԥ�Ƶ������ */
	public void setReqdresetStraightAmt(String reqdresetStraightAmt) {
		this.reqdresetStraightAmt = reqdresetStraightAmt == null ? null : reqdresetStraightAmt.trim();
	}
	public String getReqdresetStraightAmt() {
		return this.reqdresetStraightAmt;
	}
	/** ���븣��͢������ң���ȵ������� */
	public void setReqdresetBuyfftrmbDir(Integer reqdresetBuyfftrmbDir) {
		this.reqdresetBuyfftrmbDir = reqdresetBuyfftrmbDir;
	}
	public Integer getReqdresetBuyfftrmbDir() {
		return this.reqdresetBuyfftrmbDir;
	}
	/** ���븣��͢������ң�Ԥ�Ƶ������ */
	public void setReqdresetBuyfftrmbAmt(String reqdresetBuyfftrmbAmt) {
		this.reqdresetBuyfftrmbAmt = reqdresetBuyfftrmbAmt == null ? null : reqdresetBuyfftrmbAmt.trim();
	}
	public String getReqdresetBuyfftrmbAmt() {
		return this.reqdresetBuyfftrmbAmt;
	}
	/** ���ʹ�˾�����ȵ������� */
	public void setReqdresetIntercorporateLoanDir(Integer reqdresetIntercorporateLoanDir) {
		this.reqdresetIntercorporateLoanDir = reqdresetIntercorporateLoanDir;
	}
	public Integer getReqdresetIntercorporateLoanDir() {
		return this.reqdresetIntercorporateLoanDir;
	}
	/** ���ʹ�˾����Ԥ�Ƶ������ */
	public void setReqdresetIntercorporateLoanAmt(String reqdresetIntercorporateLoanAmt) {
		this.reqdresetIntercorporateLoanAmt = reqdresetIntercorporateLoanAmt == null ? null : reqdresetIntercorporateLoanAmt.trim();
	}
	public String getReqdresetIntercorporateLoanAmt() {
		return this.reqdresetIntercorporateLoanAmt;
	}
	/** ר�����ʶ�ȵ������� */
	public void setReqdresetSpecialfinaceDir(Integer reqdresetSpecialfinaceDir) {
		this.reqdresetSpecialfinaceDir = reqdresetSpecialfinaceDir;
	}
	public Integer getReqdresetSpecialfinaceDir() {
		return this.reqdresetSpecialfinaceDir;
	}
	/** ר������Ԥ�Ƶ������ */
	public void setReqdresetSpecialfinaceAmt(String reqdresetSpecialfinaceAmt) {
		this.reqdresetSpecialfinaceAmt = reqdresetSpecialfinaceAmt == null ? null : reqdresetSpecialfinaceAmt.trim();
	}
	public String getReqdresetSpecialfinaceAmt() {
		return this.reqdresetSpecialfinaceAmt;
	}
	/** ����͸֧��ȵ������� */
	public void setReqdresetPersonoverdraftDir(Integer reqdresetPersonoverdraftDir) {
		this.reqdresetPersonoverdraftDir = reqdresetPersonoverdraftDir;
	}
	public Integer getReqdresetPersonoverdraftDir() {
		return this.reqdresetPersonoverdraftDir;
	}
	/** ����͸֧Ԥ�Ƶ������ */
	public void setReqdresetPersonoverdraftAmt(String reqdresetPersonoverdraftAmt) {
		this.reqdresetPersonoverdraftAmt = reqdresetPersonoverdraftAmt == null ? null : reqdresetPersonoverdraftAmt.trim();
	}
	public String getReqdresetPersonoverdraftAmt() {
		return this.reqdresetPersonoverdraftAmt;
	}
	/** ��Ҵ����ȵ������� */
	public void setReqdresetForeigncurrencyLoanDir(Integer reqdresetForeigncurrencyLoanDir) {
		this.reqdresetForeigncurrencyLoanDir = reqdresetForeigncurrencyLoanDir;
	}
	public Integer getReqdresetForeigncurrencyLoanDir() {
		return this.reqdresetForeigncurrencyLoanDir;
	}
	/** ��Ҵ���Ԥ�Ƶ������ */
	public void setReqdresetForeigncurrencyLoanAmt(String reqdresetForeigncurrencyLoanAmt) {
		this.reqdresetForeigncurrencyLoanAmt = reqdresetForeigncurrencyLoanAmt == null ? null : reqdresetForeigncurrencyLoanAmt.trim();
	}
	public String getReqdresetForeigncurrencyLoanAmt() {
		return this.reqdresetForeigncurrencyLoanAmt;
	}
	/** ���븣��͢����ң���ȵ������� */
	public void setReqdresetBuyfftforeigncurDir(Integer reqdresetBuyfftforeigncurDir) {
		this.reqdresetBuyfftforeigncurDir = reqdresetBuyfftforeigncurDir;
	}
	public Integer getReqdresetBuyfftforeigncurDir() {
		return this.reqdresetBuyfftforeigncurDir;
	}
	/** ���븣��͢����ң�Ԥ�Ƶ������ */
	public void setReqdresetBuyfftforeigncurAmt(String reqdresetBuyfftforeigncurAmt) {
		this.reqdresetBuyfftforeigncurAmt = reqdresetBuyfftforeigncurAmt == null ? null : reqdresetBuyfftforeigncurAmt.trim();
	}
	public String getReqdresetBuyfftforeigncurAmt() {
		return this.reqdresetBuyfftforeigncurAmt;
	}
	/** ������ȵ������� */
	public void setReqdresetOtherDir(Integer reqdresetOtherDir) {
		this.reqdresetOtherDir = reqdresetOtherDir;
	}
	public Integer getReqdresetOtherDir() {
		return this.reqdresetOtherDir;
	}
	/** ����Ԥ�Ƶ������ */
	public void setReqdresetOtherAmt(String reqdresetOtherAmt) {
		this.reqdresetOtherAmt = reqdresetOtherAmt == null ? null : reqdresetOtherAmt.trim();
	}
	public String getReqdresetOtherAmt() {
		return this.reqdresetOtherAmt;
	}
	/** ����ʱ�� */
	public void setReqdresetCreatetime(String reqdresetCreatetime) {
		this.reqdresetCreatetime = reqdresetCreatetime == null ? null : reqdresetCreatetime.trim();
	}
	public String getReqdresetCreatetime() {
		return this.reqdresetCreatetime;
	}
	/** ����ʱ�� */
	public void setReqdresetUpdatetime(String reqdresetUpdatetime) {
		this.reqdresetUpdatetime = reqdresetUpdatetime == null ? null : reqdresetUpdatetime.trim();
	}
	public String getReqdresetUpdatetime() {
		return this.reqdresetUpdatetime;
	}
	/** ������Ա */
	public void setReqdresetUpdateoper(String reqdresetUpdateoper) {
		this.reqdresetUpdateoper = reqdresetUpdateoper == null ? null : reqdresetUpdateoper.trim();
	}
	public String getReqdresetUpdateoper() {
		return this.reqdresetUpdateoper;
	}
	/** ��ŷ�����ȵ������� */
	public void setReqdresetDisassemblyDir(Integer reqdresetDisassemblyDir) {
		this.reqdresetDisassemblyDir = reqdresetDisassemblyDir;
	}
	public Integer getReqdresetDisassemblyDir() {
		return this.reqdresetDisassemblyDir;
	}
	/** ��ŷ���Ԥ�Ƶ������ */
	public void setReqdresetDisassemblyAmt(String reqdresetDisassemblyAmt) {
		this.reqdresetDisassemblyAmt = reqdresetDisassemblyAmt == null ? null : reqdresetDisassemblyAmt.trim();
	}
	public String getReqdresetDisassemblyAmt() {
		return this.reqdresetDisassemblyAmt;
	}
}