package com.boco.PUB.POJO.DTO;

import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * �Ŵ�����¼����ϸ��ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbReqDetailDTO extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ����id */
		@EntityParaAnno(zhName="����id")
	private Integer reqdId;
	/** ��������id */
		@EntityParaAnno(zhName="��������id")
	private Integer reqdRefId;
	/** �����id */
		@EntityParaAnno(zhName="�����id")
	private String reqdOrgan;
	/** ��λ */
		@EntityParaAnno(zhName="��λ")
	private Integer reqdUnit;
	/** �ϱ��� */
		@EntityParaAnno(zhName="�ϱ���")
	private String reqdOper;
	/** ��Ŀ״̬ */
		@EntityParaAnno(zhName="��Ŀ״̬")
	private Integer reqdState;
	/** С����������� */
		@EntityParaAnno(zhName="С�����������")
	private String reqdSmallamountLoanReq;
	/** С�����Ԥ�Ƶ����� */
		@EntityParaAnno(zhName="С�����Ԥ�Ƶ�����")
	private String reqdSmallamountLoanExp;
	/** �������������� */
		@EntityParaAnno(zhName="��������������")
	private String reqdOtherLoanReq;
	/** ��������Ԥ�Ƶ����� */
		@EntityParaAnno(zhName="��������Ԥ�Ƶ�����")
	private String reqdOtherLoanExp;
	/** ���̴��������� */
		@EntityParaAnno(zhName="���̴���������")
	private String reqdPerbusineLoanReq;
	/** ���̴���Ԥ�Ƶ����� */
		@EntityParaAnno(zhName="���̴���Ԥ�Ƶ�����")
	private String reqdPerbusineLoanExp;
	/** С��ҵ������ */
		@EntityParaAnno(zhName="С��ҵ������")
	private String reqdSmallbusineReq;
	/** С��ҵԤ�Ƶ����� */
		@EntityParaAnno(zhName="С��ҵԤ�Ƶ�����")
	private String reqdSmallbusineExp;
	/** С��ҵ���������� */
		@EntityParaAnno(zhName="С��ҵ����������")
	private String reqdSmallbusinefactorReq;
	/** С��ҵ����Ԥ�Ƶ����� */
		@EntityParaAnno(zhName="С��ҵ����Ԥ�Ƶ�����")
	private String reqdSmallbusinefactorExp;
	/** ס�����Ҵ��������� */
		@EntityParaAnno(zhName="ס�����Ҵ���������")
	private String reqdHousemortLoanReq;
	/** ס�����Ҵ���Ԥ�Ƶ����� */
		@EntityParaAnno(zhName="ס�����Ҵ���Ԥ�Ƶ�����")
	private String reqdHousemortLoanExp;
	/** �������Ѵ��������� */
		@EntityParaAnno(zhName="�������Ѵ���������")
	private String reqdOtherconsumLoanReq;
	/** �������Ѵ���Ԥ�Ƶ����� */
		@EntityParaAnno(zhName="�������Ѵ���Ԥ�Ƶ�����")
	private String reqdOtherconsumLoanExp;
	/** ��Ӧ�������� */
		@EntityParaAnno(zhName="��Ӧ��������")
	private String reqdSupplylineReq;
	/** ��Ӧ��Ԥ�Ƶ����� */
		@EntityParaAnno(zhName="��Ӧ��Ԥ�Ƶ�����")
	private String reqdSupplylineExp;
	/** ����ó������������ */
		@EntityParaAnno(zhName="����ó������������")
	private String reqdDomestictradefinanceReq;
	/** ����ó������Ԥ�Ƶ����� */
		@EntityParaAnno(zhName="����ó������Ԥ�Ƶ�����")
	private String reqdDomestictradefinanceExp;
	/** ����ó������������ */
		@EntityParaAnno(zhName="����ó������������")
	private String reqdIntertradefinanceReq;
	/** ����ó������Ԥ�Ƶ����� */
		@EntityParaAnno(zhName="����ó������Ԥ�Ƶ�����")
	private String reqdIntertradefinanceExp;
	/** ������˾���������� */
		@EntityParaAnno(zhName="������˾����������")
	private String reqdOthercorporateLoanReq;
	/** ������˾����Ԥ�Ƶ����� */
		@EntityParaAnno(zhName="������˾����Ԥ�Ƶ�����")
	private String reqdOthercorporateLoanExp;
	/** ��ũ��˾���������� */
		@EntityParaAnno(zhName="��ũ��˾����������")
	private String reqdSancompanyLoanReq;
	/** ��ũ��˾������� */
		@EntityParaAnno(zhName="��ũ��˾�������")
	private String reqdSancompanyLoanExp;
	/** �������������� */
		@EntityParaAnno(zhName="��������������")
	private String reqdMergerLoanReq;
	/** ��������Ԥ�Ƶ����� */
		@EntityParaAnno(zhName="��������Ԥ�Ƶ�����")
	private String reqdMergerLoanExp;
	/** ������������ */
		@EntityParaAnno(zhName="������������")
	private String reqdAlladvanceLoanReq;
	/** ������Ԥ�Ƶ����� */
		@EntityParaAnno(zhName="������Ԥ�Ƶ�����")
	private String reqdAlladvanceLoanExp;
	/** ��λ͸֧������ */
		@EntityParaAnno(zhName="��λ͸֧������")
	private String reqdUnitoverdraftReq;
	/** ��λ͸֧Ԥ�Ƶ����� */
		@EntityParaAnno(zhName="��λ͸֧Ԥ�Ƶ�����")
	private String reqdUnitoverdraftExp;
	/** ת�������� */
		@EntityParaAnno(zhName="ת��������")
	private String reqdRepostReq;
	/** ת��Ԥ�Ƶ����� */
		@EntityParaAnno(zhName="ת��Ԥ�Ƶ�����")
	private String reqdRepostExp;
	/** ֱ�������� */
		@EntityParaAnno(zhName="ֱ��������")
	private String reqdStraightReq;
	/** ֱ��Ԥ�Ƶ����� */
		@EntityParaAnno(zhName="ֱ��Ԥ�Ƶ�����")
	private String reqdStraightExp;
	/** -���븣��͢������ң������� */
		@EntityParaAnno(zhName="-���븣��͢������ң�������")
	private String reqdBuyfftrmbReq;
	/** ���븣��͢������ң�Ԥ�Ƶ����� */
		@EntityParaAnno(zhName="���븣��͢������ң�Ԥ�Ƶ�����")
	private String reqdBuyfftrmbExp;
	/** ���ʹ�˾���������� */
		@EntityParaAnno(zhName="���ʹ�˾����������")
	private String reqdIntercorporateLoanReq;
	/** ���ʹ�˾����Ԥ�Ƶ����� */
		@EntityParaAnno(zhName="���ʹ�˾����Ԥ�Ƶ�����")
	private String reqdIntercorporateLoanExp;
	/** ר������������ */
		@EntityParaAnno(zhName="ר������������")
	private String reqdSpecialfinaceReq;
	/** ר������Ԥ�Ƶ����� */
		@EntityParaAnno(zhName="ר������Ԥ�Ƶ�����")
	private String reqdSpecialfinaceExp;
	/** ����͸֧������ */
		@EntityParaAnno(zhName="����͸֧������")
	private String reqdPersonoverdraftReq;
	/** ����͸֧Ԥ�Ƶ����� */
		@EntityParaAnno(zhName="����͸֧Ԥ�Ƶ�����")
	private String reqdPersonoverdraftExp;
	/** ��Ҵ��������� */
		@EntityParaAnno(zhName="��Ҵ���������")
	private String reqdForeigncurrencyLoanReq;
	/** ��Ҵ���Ԥ�Ƶ����� */
		@EntityParaAnno(zhName="��Ҵ���Ԥ�Ƶ�����")
	private String reqdForeigncurrencyLoanExp;
	/** ���븣��͢����ң������� */
		@EntityParaAnno(zhName="���븣��͢����ң�������")
	private String reqdBuyfftforeigncurReq;
	/** ���븣��͢����ң�Ԥ�Ƶ����� */
		@EntityParaAnno(zhName="���븣��͢����ң�Ԥ�Ƶ�����")
	private String reqdBuyfftforeigncurExp;
	/** ���������� */
		@EntityParaAnno(zhName="����������")
	private String reqdOtherReq;
	/** ����Ԥ�Ƶ����� */
		@EntityParaAnno(zhName="����Ԥ�Ƶ�����")
	private String reqdOtherExp;
	/** ����ʱ�� */
		@EntityParaAnno(zhName="����ʱ��")
	private String reqdCreateTime;
	/** ����ʱ�� */
		@EntityParaAnno(zhName="����ʱ��")
	private String reqdUpdateTime;
	/** �����˻�͸֧������ */
		@EntityParaAnno(zhName="�����˻�͸֧������")
	private String reqdCorporateaccoverdraftLoanReq;
	/** �����˻�͸֧Ԥ�Ƶ����� */
		@EntityParaAnno(zhName="�����˻�͸֧Ԥ�Ƶ�����")
	private String reqdCorporateaccoverdraftLoanExp;
	/** ��ŷ��������� */
		@EntityParaAnno(zhName="��ŷ���������")
	private String reqdDisassemblyReq;
	/** ��ŷ��������� */
		@EntityParaAnno(zhName="��ŷ���������")
	private String reqdDisassemblyExp;
	
	/** setter\getter���� */
	/** ����id */
	public void setReqdId(Integer reqdId) {
		this.reqdId = reqdId;
	}
	public Integer getReqdId() {
		return this.reqdId;
	}
	/** ��������id */
	public void setReqdRefId(Integer reqdRefId) {
		this.reqdRefId = reqdRefId;
	}
	public Integer getReqdRefId() {
		return this.reqdRefId;
	}
	/** �����id */
	public void setReqdOrgan(String reqdOrgan) {
		this.reqdOrgan = reqdOrgan == null ? null : reqdOrgan.trim();
	}
	public String getReqdOrgan() {
		return this.reqdOrgan;
	}
	/** ��λ */
	public void setReqdUnit(Integer reqdUnit) {
		this.reqdUnit = reqdUnit;
	}
	public Integer getReqdUnit() {
		return this.reqdUnit;
	}
	/** �ϱ��� */
	public void setReqdOper(String reqdOper) {
		this.reqdOper = reqdOper == null ? null : reqdOper.trim();
	}
	public String getReqdOper() {
		return this.reqdOper;
	}
	/** ��Ŀ״̬ */
	public void setReqdState(Integer reqdState) {
		this.reqdState = reqdState;
	}
	public Integer getReqdState() {
		return this.reqdState;
	}
	/** С����������� */
	public void setReqdSmallamountLoanReq(String reqdSmallamountLoanReq) {
		this.reqdSmallamountLoanReq = reqdSmallamountLoanReq == null ? null : reqdSmallamountLoanReq.trim();
	}
	public String getReqdSmallamountLoanReq() {
		return this.reqdSmallamountLoanReq;
	}
	/** С�����Ԥ�Ƶ����� */
	public void setReqdSmallamountLoanExp(String reqdSmallamountLoanExp) {
		this.reqdSmallamountLoanExp = reqdSmallamountLoanExp == null ? null : reqdSmallamountLoanExp.trim();
	}
	public String getReqdSmallamountLoanExp() {
		return this.reqdSmallamountLoanExp;
	}
	/** �������������� */
	public void setReqdOtherLoanReq(String reqdOtherLoanReq) {
		this.reqdOtherLoanReq = reqdOtherLoanReq == null ? null : reqdOtherLoanReq.trim();
	}
	public String getReqdOtherLoanReq() {
		return this.reqdOtherLoanReq;
	}
	/** ��������Ԥ�Ƶ����� */
	public void setReqdOtherLoanExp(String reqdOtherLoanExp) {
		this.reqdOtherLoanExp = reqdOtherLoanExp == null ? null : reqdOtherLoanExp.trim();
	}
	public String getReqdOtherLoanExp() {
		return this.reqdOtherLoanExp;
	}
	/** ���̴��������� */
	public void setReqdPerbusineLoanReq(String reqdPerbusineLoanReq) {
		this.reqdPerbusineLoanReq = reqdPerbusineLoanReq == null ? null : reqdPerbusineLoanReq.trim();
	}
	public String getReqdPerbusineLoanReq() {
		return this.reqdPerbusineLoanReq;
	}
	/** ���̴���Ԥ�Ƶ����� */
	public void setReqdPerbusineLoanExp(String reqdPerbusineLoanExp) {
		this.reqdPerbusineLoanExp = reqdPerbusineLoanExp == null ? null : reqdPerbusineLoanExp.trim();
	}
	public String getReqdPerbusineLoanExp() {
		return this.reqdPerbusineLoanExp;
	}
	/** С��ҵ������ */
	public void setReqdSmallbusineReq(String reqdSmallbusineReq) {
		this.reqdSmallbusineReq = reqdSmallbusineReq == null ? null : reqdSmallbusineReq.trim();
	}
	public String getReqdSmallbusineReq() {
		return this.reqdSmallbusineReq;
	}
	/** С��ҵԤ�Ƶ����� */
	public void setReqdSmallbusineExp(String reqdSmallbusineExp) {
		this.reqdSmallbusineExp = reqdSmallbusineExp == null ? null : reqdSmallbusineExp.trim();
	}
	public String getReqdSmallbusineExp() {
		return this.reqdSmallbusineExp;
	}
	/** С��ҵ���������� */
	public void setReqdSmallbusinefactorReq(String reqdSmallbusinefactorReq) {
		this.reqdSmallbusinefactorReq = reqdSmallbusinefactorReq == null ? null : reqdSmallbusinefactorReq.trim();
	}
	public String getReqdSmallbusinefactorReq() {
		return this.reqdSmallbusinefactorReq;
	}
	/** С��ҵ����Ԥ�Ƶ����� */
	public void setReqdSmallbusinefactorExp(String reqdSmallbusinefactorExp) {
		this.reqdSmallbusinefactorExp = reqdSmallbusinefactorExp == null ? null : reqdSmallbusinefactorExp.trim();
	}
	public String getReqdSmallbusinefactorExp() {
		return this.reqdSmallbusinefactorExp;
	}
	/** ס�����Ҵ��������� */
	public void setReqdHousemortLoanReq(String reqdHousemortLoanReq) {
		this.reqdHousemortLoanReq = reqdHousemortLoanReq == null ? null : reqdHousemortLoanReq.trim();
	}
	public String getReqdHousemortLoanReq() {
		return this.reqdHousemortLoanReq;
	}
	/** ס�����Ҵ���Ԥ�Ƶ����� */
	public void setReqdHousemortLoanExp(String reqdHousemortLoanExp) {
		this.reqdHousemortLoanExp = reqdHousemortLoanExp == null ? null : reqdHousemortLoanExp.trim();
	}
	public String getReqdHousemortLoanExp() {
		return this.reqdHousemortLoanExp;
	}
	/** �������Ѵ��������� */
	public void setReqdOtherconsumLoanReq(String reqdOtherconsumLoanReq) {
		this.reqdOtherconsumLoanReq = reqdOtherconsumLoanReq == null ? null : reqdOtherconsumLoanReq.trim();
	}
	public String getReqdOtherconsumLoanReq() {
		return this.reqdOtherconsumLoanReq;
	}
	/** �������Ѵ���Ԥ�Ƶ����� */
	public void setReqdOtherconsumLoanExp(String reqdOtherconsumLoanExp) {
		this.reqdOtherconsumLoanExp = reqdOtherconsumLoanExp == null ? null : reqdOtherconsumLoanExp.trim();
	}
	public String getReqdOtherconsumLoanExp() {
		return this.reqdOtherconsumLoanExp;
	}
	/** ��Ӧ�������� */
	public void setReqdSupplylineReq(String reqdSupplylineReq) {
		this.reqdSupplylineReq = reqdSupplylineReq == null ? null : reqdSupplylineReq.trim();
	}
	public String getReqdSupplylineReq() {
		return this.reqdSupplylineReq;
	}
	/** ��Ӧ��Ԥ�Ƶ����� */
	public void setReqdSupplylineExp(String reqdSupplylineExp) {
		this.reqdSupplylineExp = reqdSupplylineExp == null ? null : reqdSupplylineExp.trim();
	}
	public String getReqdSupplylineExp() {
		return this.reqdSupplylineExp;
	}
	/** ����ó������������ */
	public void setReqdDomestictradefinanceReq(String reqdDomestictradefinanceReq) {
		this.reqdDomestictradefinanceReq = reqdDomestictradefinanceReq == null ? null : reqdDomestictradefinanceReq.trim();
	}
	public String getReqdDomestictradefinanceReq() {
		return this.reqdDomestictradefinanceReq;
	}
	/** ����ó������Ԥ�Ƶ����� */
	public void setReqdDomestictradefinanceExp(String reqdDomestictradefinanceExp) {
		this.reqdDomestictradefinanceExp = reqdDomestictradefinanceExp == null ? null : reqdDomestictradefinanceExp.trim();
	}
	public String getReqdDomestictradefinanceExp() {
		return this.reqdDomestictradefinanceExp;
	}
	/** ����ó������������ */
	public void setReqdIntertradefinanceReq(String reqdIntertradefinanceReq) {
		this.reqdIntertradefinanceReq = reqdIntertradefinanceReq == null ? null : reqdIntertradefinanceReq.trim();
	}
	public String getReqdIntertradefinanceReq() {
		return this.reqdIntertradefinanceReq;
	}
	/** ����ó������Ԥ�Ƶ����� */
	public void setReqdIntertradefinanceExp(String reqdIntertradefinanceExp) {
		this.reqdIntertradefinanceExp = reqdIntertradefinanceExp == null ? null : reqdIntertradefinanceExp.trim();
	}
	public String getReqdIntertradefinanceExp() {
		return this.reqdIntertradefinanceExp;
	}
	/** ������˾���������� */
	public void setReqdOthercorporateLoanReq(String reqdOthercorporateLoanReq) {
		this.reqdOthercorporateLoanReq = reqdOthercorporateLoanReq == null ? null : reqdOthercorporateLoanReq.trim();
	}
	public String getReqdOthercorporateLoanReq() {
		return this.reqdOthercorporateLoanReq;
	}
	/** ������˾����Ԥ�Ƶ����� */
	public void setReqdOthercorporateLoanExp(String reqdOthercorporateLoanExp) {
		this.reqdOthercorporateLoanExp = reqdOthercorporateLoanExp == null ? null : reqdOthercorporateLoanExp.trim();
	}
	public String getReqdOthercorporateLoanExp() {
		return this.reqdOthercorporateLoanExp;
	}
	/** ��ũ��˾���������� */
	public void setReqdSancompanyLoanReq(String reqdSancompanyLoanReq) {
		this.reqdSancompanyLoanReq = reqdSancompanyLoanReq == null ? null : reqdSancompanyLoanReq.trim();
	}
	public String getReqdSancompanyLoanReq() {
		return this.reqdSancompanyLoanReq;
	}
	/** ��ũ��˾������� */
	public void setReqdSancompanyLoanExp(String reqdSancompanyLoanExp) {
		this.reqdSancompanyLoanExp = reqdSancompanyLoanExp == null ? null : reqdSancompanyLoanExp.trim();
	}
	public String getReqdSancompanyLoanExp() {
		return this.reqdSancompanyLoanExp;
	}
	/** �������������� */
	public void setReqdMergerLoanReq(String reqdMergerLoanReq) {
		this.reqdMergerLoanReq = reqdMergerLoanReq == null ? null : reqdMergerLoanReq.trim();
	}
	public String getReqdMergerLoanReq() {
		return this.reqdMergerLoanReq;
	}
	/** ��������Ԥ�Ƶ����� */
	public void setReqdMergerLoanExp(String reqdMergerLoanExp) {
		this.reqdMergerLoanExp = reqdMergerLoanExp == null ? null : reqdMergerLoanExp.trim();
	}
	public String getReqdMergerLoanExp() {
		return this.reqdMergerLoanExp;
	}
	/** ������������ */
	public void setReqdAlladvanceLoanReq(String reqdAlladvanceLoanReq) {
		this.reqdAlladvanceLoanReq = reqdAlladvanceLoanReq == null ? null : reqdAlladvanceLoanReq.trim();
	}
	public String getReqdAlladvanceLoanReq() {
		return this.reqdAlladvanceLoanReq;
	}
	/** ������Ԥ�Ƶ����� */
	public void setReqdAlladvanceLoanExp(String reqdAlladvanceLoanExp) {
		this.reqdAlladvanceLoanExp = reqdAlladvanceLoanExp == null ? null : reqdAlladvanceLoanExp.trim();
	}
	public String getReqdAlladvanceLoanExp() {
		return this.reqdAlladvanceLoanExp;
	}
	/** ��λ͸֧������ */
	public void setReqdUnitoverdraftReq(String reqdUnitoverdraftReq) {
		this.reqdUnitoverdraftReq = reqdUnitoverdraftReq == null ? null : reqdUnitoverdraftReq.trim();
	}
	public String getReqdUnitoverdraftReq() {
		return this.reqdUnitoverdraftReq;
	}
	/** ��λ͸֧Ԥ�Ƶ����� */
	public void setReqdUnitoverdraftExp(String reqdUnitoverdraftExp) {
		this.reqdUnitoverdraftExp = reqdUnitoverdraftExp == null ? null : reqdUnitoverdraftExp.trim();
	}
	public String getReqdUnitoverdraftExp() {
		return this.reqdUnitoverdraftExp;
	}
	/** ת�������� */
	public void setReqdRepostReq(String reqdRepostReq) {
		this.reqdRepostReq = reqdRepostReq == null ? null : reqdRepostReq.trim();
	}
	public String getReqdRepostReq() {
		return this.reqdRepostReq;
	}
	/** ת��Ԥ�Ƶ����� */
	public void setReqdRepostExp(String reqdRepostExp) {
		this.reqdRepostExp = reqdRepostExp == null ? null : reqdRepostExp.trim();
	}
	public String getReqdRepostExp() {
		return this.reqdRepostExp;
	}
	/** ֱ�������� */
	public void setReqdStraightReq(String reqdStraightReq) {
		this.reqdStraightReq = reqdStraightReq == null ? null : reqdStraightReq.trim();
	}
	public String getReqdStraightReq() {
		return this.reqdStraightReq;
	}
	/** ֱ��Ԥ�Ƶ����� */
	public void setReqdStraightExp(String reqdStraightExp) {
		this.reqdStraightExp = reqdStraightExp == null ? null : reqdStraightExp.trim();
	}
	public String getReqdStraightExp() {
		return this.reqdStraightExp;
	}
	/** -���븣��͢������ң������� */
	public void setReqdBuyfftrmbReq(String reqdBuyfftrmbReq) {
		this.reqdBuyfftrmbReq = reqdBuyfftrmbReq == null ? null : reqdBuyfftrmbReq.trim();
	}
	public String getReqdBuyfftrmbReq() {
		return this.reqdBuyfftrmbReq;
	}
	/** ���븣��͢������ң�Ԥ�Ƶ����� */
	public void setReqdBuyfftrmbExp(String reqdBuyfftrmbExp) {
		this.reqdBuyfftrmbExp = reqdBuyfftrmbExp == null ? null : reqdBuyfftrmbExp.trim();
	}
	public String getReqdBuyfftrmbExp() {
		return this.reqdBuyfftrmbExp;
	}
	/** ���ʹ�˾���������� */
	public void setReqdIntercorporateLoanReq(String reqdIntercorporateLoanReq) {
		this.reqdIntercorporateLoanReq = reqdIntercorporateLoanReq == null ? null : reqdIntercorporateLoanReq.trim();
	}
	public String getReqdIntercorporateLoanReq() {
		return this.reqdIntercorporateLoanReq;
	}
	/** ���ʹ�˾����Ԥ�Ƶ����� */
	public void setReqdIntercorporateLoanExp(String reqdIntercorporateLoanExp) {
		this.reqdIntercorporateLoanExp = reqdIntercorporateLoanExp == null ? null : reqdIntercorporateLoanExp.trim();
	}
	public String getReqdIntercorporateLoanExp() {
		return this.reqdIntercorporateLoanExp;
	}
	/** ר������������ */
	public void setReqdSpecialfinaceReq(String reqdSpecialfinaceReq) {
		this.reqdSpecialfinaceReq = reqdSpecialfinaceReq == null ? null : reqdSpecialfinaceReq.trim();
	}
	public String getReqdSpecialfinaceReq() {
		return this.reqdSpecialfinaceReq;
	}
	/** ר������Ԥ�Ƶ����� */
	public void setReqdSpecialfinaceExp(String reqdSpecialfinaceExp) {
		this.reqdSpecialfinaceExp = reqdSpecialfinaceExp == null ? null : reqdSpecialfinaceExp.trim();
	}
	public String getReqdSpecialfinaceExp() {
		return this.reqdSpecialfinaceExp;
	}
	/** ����͸֧������ */
	public void setReqdPersonoverdraftReq(String reqdPersonoverdraftReq) {
		this.reqdPersonoverdraftReq = reqdPersonoverdraftReq == null ? null : reqdPersonoverdraftReq.trim();
	}
	public String getReqdPersonoverdraftReq() {
		return this.reqdPersonoverdraftReq;
	}
	/** ����͸֧Ԥ�Ƶ����� */
	public void setReqdPersonoverdraftExp(String reqdPersonoverdraftExp) {
		this.reqdPersonoverdraftExp = reqdPersonoverdraftExp == null ? null : reqdPersonoverdraftExp.trim();
	}
	public String getReqdPersonoverdraftExp() {
		return this.reqdPersonoverdraftExp;
	}
	/** ��Ҵ��������� */
	public void setReqdForeigncurrencyLoanReq(String reqdForeigncurrencyLoanReq) {
		this.reqdForeigncurrencyLoanReq = reqdForeigncurrencyLoanReq == null ? null : reqdForeigncurrencyLoanReq.trim();
	}
	public String getReqdForeigncurrencyLoanReq() {
		return this.reqdForeigncurrencyLoanReq;
	}
	/** ��Ҵ���Ԥ�Ƶ����� */
	public void setReqdForeigncurrencyLoanExp(String reqdForeigncurrencyLoanExp) {
		this.reqdForeigncurrencyLoanExp = reqdForeigncurrencyLoanExp == null ? null : reqdForeigncurrencyLoanExp.trim();
	}
	public String getReqdForeigncurrencyLoanExp() {
		return this.reqdForeigncurrencyLoanExp;
	}
	/** ���븣��͢����ң������� */
	public void setReqdBuyfftforeigncurReq(String reqdBuyfftforeigncurReq) {
		this.reqdBuyfftforeigncurReq = reqdBuyfftforeigncurReq == null ? null : reqdBuyfftforeigncurReq.trim();
	}
	public String getReqdBuyfftforeigncurReq() {
		return this.reqdBuyfftforeigncurReq;
	}
	/** ���븣��͢����ң�Ԥ�Ƶ����� */
	public void setReqdBuyfftforeigncurExp(String reqdBuyfftforeigncurExp) {
		this.reqdBuyfftforeigncurExp = reqdBuyfftforeigncurExp == null ? null : reqdBuyfftforeigncurExp.trim();
	}
	public String getReqdBuyfftforeigncurExp() {
		return this.reqdBuyfftforeigncurExp;
	}
	/** ���������� */
	public void setReqdOtherReq(String reqdOtherReq) {
		this.reqdOtherReq = reqdOtherReq == null ? null : reqdOtherReq.trim();
	}
	public String getReqdOtherReq() {
		return this.reqdOtherReq;
	}
	/** ����Ԥ�Ƶ����� */
	public void setReqdOtherExp(String reqdOtherExp) {
		this.reqdOtherExp = reqdOtherExp == null ? null : reqdOtherExp.trim();
	}
	public String getReqdOtherExp() {
		return this.reqdOtherExp;
	}
	/** ����ʱ�� */
	public void setReqdCreateTime(String reqdCreateTime) {
		this.reqdCreateTime = reqdCreateTime == null ? null : reqdCreateTime.trim();
	}
	public String getReqdCreateTime() {
		return this.reqdCreateTime;
	}
	/** ����ʱ�� */
	public void setReqdUpdateTime(String reqdUpdateTime) {
		this.reqdUpdateTime = reqdUpdateTime == null ? null : reqdUpdateTime.trim();
	}
	public String getReqdUpdateTime() {
		return this.reqdUpdateTime;
	}
	/** �����˻�͸֧������ */
	public void setReqdCorporateaccoverdraftLoanReq(String reqdCorporateaccoverdraftLoanReq) {
		this.reqdCorporateaccoverdraftLoanReq = reqdCorporateaccoverdraftLoanReq == null ? null : reqdCorporateaccoverdraftLoanReq.trim();
	}
	public String getReqdCorporateaccoverdraftLoanReq() {
		return this.reqdCorporateaccoverdraftLoanReq;
	}
	/** �����˻�͸֧Ԥ�Ƶ����� */
	public void setReqdCorporateaccoverdraftLoanExp(String reqdCorporateaccoverdraftLoanExp) {
		this.reqdCorporateaccoverdraftLoanExp = reqdCorporateaccoverdraftLoanExp == null ? null : reqdCorporateaccoverdraftLoanExp.trim();
	}
	public String getReqdCorporateaccoverdraftLoanExp() {
		return this.reqdCorporateaccoverdraftLoanExp;
	}
	/** ��ŷ��������� */
	public void setReqdDisassemblyReq(String reqdDisassemblyReq) {
		this.reqdDisassemblyReq = reqdDisassemblyReq == null ? null : reqdDisassemblyReq.trim();
	}
	public String getReqdDisassemblyReq() {
		return this.reqdDisassemblyReq;
	}
	/** ��ŷ��������� */
	public void setReqdDisassemblyExp(String reqdDisassemblyExp) {
		this.reqdDisassemblyExp = reqdDisassemblyExp == null ? null : reqdDisassemblyExp.trim();
	}
	public String getReqdDisassemblyExp() {
		return this.reqdDisassemblyExp;
	}
}