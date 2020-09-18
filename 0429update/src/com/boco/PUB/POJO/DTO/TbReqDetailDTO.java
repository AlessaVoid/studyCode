package com.boco.PUB.POJO.DTO;

import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * 信贷需求录入详细表实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbReqDetailDTO extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 编码id */
		@EntityParaAnno(zhName="编码id")
	private Integer reqdId;
	/** 所属批次id */
		@EntityParaAnno(zhName="所属批次id")
	private Integer reqdRefId;
	/** 填报机构id */
		@EntityParaAnno(zhName="填报机构id")
	private String reqdOrgan;
	/** 单位 */
		@EntityParaAnno(zhName="单位")
	private Integer reqdUnit;
	/** 上报人 */
		@EntityParaAnno(zhName="上报人")
	private String reqdOper;
	/** 条目状态 */
		@EntityParaAnno(zhName="条目状态")
	private Integer reqdState;
	/** 小额贷款需求量 */
		@EntityParaAnno(zhName="小额贷款需求量")
	private String reqdSmallamountLoanReq;
	/** 小额贷款预计到期量 */
		@EntityParaAnno(zhName="小额贷款预计到期量")
	private String reqdSmallamountLoanExp;
	/** 其他贷款需求量 */
		@EntityParaAnno(zhName="其他贷款需求量")
	private String reqdOtherLoanReq;
	/** 其他贷款预计到期量 */
		@EntityParaAnno(zhName="其他贷款预计到期量")
	private String reqdOtherLoanExp;
	/** 个商贷款需求量 */
		@EntityParaAnno(zhName="个商贷款需求量")
	private String reqdPerbusineLoanReq;
	/** 个商贷款预计到期量 */
		@EntityParaAnno(zhName="个商贷款预计到期量")
	private String reqdPerbusineLoanExp;
	/** 小企业需求量 */
		@EntityParaAnno(zhName="小企业需求量")
	private String reqdSmallbusineReq;
	/** 小企业预计到期量 */
		@EntityParaAnno(zhName="小企业预计到期量")
	private String reqdSmallbusineExp;
	/** 小企业保理需求量 */
		@EntityParaAnno(zhName="小企业保理需求量")
	private String reqdSmallbusinefactorReq;
	/** 小企业保理预计到期量 */
		@EntityParaAnno(zhName="小企业保理预计到期量")
	private String reqdSmallbusinefactorExp;
	/** 住房按揭贷款需求量 */
		@EntityParaAnno(zhName="住房按揭贷款需求量")
	private String reqdHousemortLoanReq;
	/** 住房按揭贷款预计到期量 */
		@EntityParaAnno(zhName="住房按揭贷款预计到期量")
	private String reqdHousemortLoanExp;
	/** 其他消费贷款需求量 */
		@EntityParaAnno(zhName="其他消费贷款需求量")
	private String reqdOtherconsumLoanReq;
	/** 其他消费贷款预计到期量 */
		@EntityParaAnno(zhName="其他消费贷款预计到期量")
	private String reqdOtherconsumLoanExp;
	/** 供应链需求量 */
		@EntityParaAnno(zhName="供应链需求量")
	private String reqdSupplylineReq;
	/** 供应链预计到期量 */
		@EntityParaAnno(zhName="供应链预计到期量")
	private String reqdSupplylineExp;
	/** 国内贸易融资需求量 */
		@EntityParaAnno(zhName="国内贸易融资需求量")
	private String reqdDomestictradefinanceReq;
	/** 国内贸易融资预计到期量 */
		@EntityParaAnno(zhName="国内贸易融资预计到期量")
	private String reqdDomestictradefinanceExp;
	/** 国际贸易融资需求量 */
		@EntityParaAnno(zhName="国际贸易融资需求量")
	private String reqdIntertradefinanceReq;
	/** 国际贸易融资预计到期量 */
		@EntityParaAnno(zhName="国际贸易融资预计到期量")
	private String reqdIntertradefinanceExp;
	/** 其他公司贷款需求量 */
		@EntityParaAnno(zhName="其他公司贷款需求量")
	private String reqdOthercorporateLoanReq;
	/** 其他公司贷款预计到期量 */
		@EntityParaAnno(zhName="其他公司贷款预计到期量")
	private String reqdOthercorporateLoanExp;
	/** 三农公司贷款需求量 */
		@EntityParaAnno(zhName="三农公司贷款需求量")
	private String reqdSancompanyLoanReq;
	/** 三农公司贷款到期量 */
		@EntityParaAnno(zhName="三农公司贷款到期量")
	private String reqdSancompanyLoanExp;
	/** 并购贷款需求量 */
		@EntityParaAnno(zhName="并购贷款需求量")
	private String reqdMergerLoanReq;
	/** 并购贷款预计到期量 */
		@EntityParaAnno(zhName="并购贷款预计到期量")
	private String reqdMergerLoanExp;
	/** 各项垫款需求量 */
		@EntityParaAnno(zhName="各项垫款需求量")
	private String reqdAlladvanceLoanReq;
	/** 各项垫款预计到期量 */
		@EntityParaAnno(zhName="各项垫款预计到期量")
	private String reqdAlladvanceLoanExp;
	/** 单位透支需求量 */
		@EntityParaAnno(zhName="单位透支需求量")
	private String reqdUnitoverdraftReq;
	/** 单位透支预计到期量 */
		@EntityParaAnno(zhName="单位透支预计到期量")
	private String reqdUnitoverdraftExp;
	/** 转贴需求量 */
		@EntityParaAnno(zhName="转贴需求量")
	private String reqdRepostReq;
	/** 转贴预计到期量 */
		@EntityParaAnno(zhName="转贴预计到期量")
	private String reqdRepostExp;
	/** 直贴需求量 */
		@EntityParaAnno(zhName="直贴需求量")
	private String reqdStraightReq;
	/** 直贴预计到期量 */
		@EntityParaAnno(zhName="直贴预计到期量")
	private String reqdStraightExp;
	/** -买入福费廷（人民币）需求量 */
		@EntityParaAnno(zhName="-买入福费廷（人民币）需求量")
	private String reqdBuyfftrmbReq;
	/** 买入福费廷（人民币）预计到期量 */
		@EntityParaAnno(zhName="买入福费廷（人民币）预计到期量")
	private String reqdBuyfftrmbExp;
	/** 国际公司贷款需求量 */
		@EntityParaAnno(zhName="国际公司贷款需求量")
	private String reqdIntercorporateLoanReq;
	/** 国际公司贷款预计到期量 */
		@EntityParaAnno(zhName="国际公司贷款预计到期量")
	private String reqdIntercorporateLoanExp;
	/** 专项融资需求量 */
		@EntityParaAnno(zhName="专项融资需求量")
	private String reqdSpecialfinaceReq;
	/** 专项融资预计到期量 */
		@EntityParaAnno(zhName="专项融资预计到期量")
	private String reqdSpecialfinaceExp;
	/** 个人透支需求量 */
		@EntityParaAnno(zhName="个人透支需求量")
	private String reqdPersonoverdraftReq;
	/** 个人透支预计到期量 */
		@EntityParaAnno(zhName="个人透支预计到期量")
	private String reqdPersonoverdraftExp;
	/** 外币贷款需求量 */
		@EntityParaAnno(zhName="外币贷款需求量")
	private String reqdForeigncurrencyLoanReq;
	/** 外币贷款预计到期量 */
		@EntityParaAnno(zhName="外币贷款预计到期量")
	private String reqdForeigncurrencyLoanExp;
	/** 买入福费廷（外币）需求量 */
		@EntityParaAnno(zhName="买入福费廷（外币）需求量")
	private String reqdBuyfftforeigncurReq;
	/** 买入福费廷（外币）预计到期量 */
		@EntityParaAnno(zhName="买入福费廷（外币）预计到期量")
	private String reqdBuyfftforeigncurExp;
	/** 其他需求量 */
		@EntityParaAnno(zhName="其他需求量")
	private String reqdOtherReq;
	/** 其他预计到期量 */
		@EntityParaAnno(zhName="其他预计到期量")
	private String reqdOtherExp;
	/** 创建时间 */
		@EntityParaAnno(zhName="创建时间")
	private String reqdCreateTime;
	/** 更新时间 */
		@EntityParaAnno(zhName="更新时间")
	private String reqdUpdateTime;
	/** 法人账户透支需求量 */
		@EntityParaAnno(zhName="法人账户透支需求量")
	private String reqdCorporateaccoverdraftLoanReq;
	/** 法人账户透支预计到期量 */
		@EntityParaAnno(zhName="法人账户透支预计到期量")
	private String reqdCorporateaccoverdraftLoanExp;
	/** 拆放非银需求量 */
		@EntityParaAnno(zhName="拆放非银需求量")
	private String reqdDisassemblyReq;
	/** 拆放非银到期量 */
		@EntityParaAnno(zhName="拆放非银到期量")
	private String reqdDisassemblyExp;
	
	/** setter\getter方法 */
	/** 编码id */
	public void setReqdId(Integer reqdId) {
		this.reqdId = reqdId;
	}
	public Integer getReqdId() {
		return this.reqdId;
	}
	/** 所属批次id */
	public void setReqdRefId(Integer reqdRefId) {
		this.reqdRefId = reqdRefId;
	}
	public Integer getReqdRefId() {
		return this.reqdRefId;
	}
	/** 填报机构id */
	public void setReqdOrgan(String reqdOrgan) {
		this.reqdOrgan = reqdOrgan == null ? null : reqdOrgan.trim();
	}
	public String getReqdOrgan() {
		return this.reqdOrgan;
	}
	/** 单位 */
	public void setReqdUnit(Integer reqdUnit) {
		this.reqdUnit = reqdUnit;
	}
	public Integer getReqdUnit() {
		return this.reqdUnit;
	}
	/** 上报人 */
	public void setReqdOper(String reqdOper) {
		this.reqdOper = reqdOper == null ? null : reqdOper.trim();
	}
	public String getReqdOper() {
		return this.reqdOper;
	}
	/** 条目状态 */
	public void setReqdState(Integer reqdState) {
		this.reqdState = reqdState;
	}
	public Integer getReqdState() {
		return this.reqdState;
	}
	/** 小额贷款需求量 */
	public void setReqdSmallamountLoanReq(String reqdSmallamountLoanReq) {
		this.reqdSmallamountLoanReq = reqdSmallamountLoanReq == null ? null : reqdSmallamountLoanReq.trim();
	}
	public String getReqdSmallamountLoanReq() {
		return this.reqdSmallamountLoanReq;
	}
	/** 小额贷款预计到期量 */
	public void setReqdSmallamountLoanExp(String reqdSmallamountLoanExp) {
		this.reqdSmallamountLoanExp = reqdSmallamountLoanExp == null ? null : reqdSmallamountLoanExp.trim();
	}
	public String getReqdSmallamountLoanExp() {
		return this.reqdSmallamountLoanExp;
	}
	/** 其他贷款需求量 */
	public void setReqdOtherLoanReq(String reqdOtherLoanReq) {
		this.reqdOtherLoanReq = reqdOtherLoanReq == null ? null : reqdOtherLoanReq.trim();
	}
	public String getReqdOtherLoanReq() {
		return this.reqdOtherLoanReq;
	}
	/** 其他贷款预计到期量 */
	public void setReqdOtherLoanExp(String reqdOtherLoanExp) {
		this.reqdOtherLoanExp = reqdOtherLoanExp == null ? null : reqdOtherLoanExp.trim();
	}
	public String getReqdOtherLoanExp() {
		return this.reqdOtherLoanExp;
	}
	/** 个商贷款需求量 */
	public void setReqdPerbusineLoanReq(String reqdPerbusineLoanReq) {
		this.reqdPerbusineLoanReq = reqdPerbusineLoanReq == null ? null : reqdPerbusineLoanReq.trim();
	}
	public String getReqdPerbusineLoanReq() {
		return this.reqdPerbusineLoanReq;
	}
	/** 个商贷款预计到期量 */
	public void setReqdPerbusineLoanExp(String reqdPerbusineLoanExp) {
		this.reqdPerbusineLoanExp = reqdPerbusineLoanExp == null ? null : reqdPerbusineLoanExp.trim();
	}
	public String getReqdPerbusineLoanExp() {
		return this.reqdPerbusineLoanExp;
	}
	/** 小企业需求量 */
	public void setReqdSmallbusineReq(String reqdSmallbusineReq) {
		this.reqdSmallbusineReq = reqdSmallbusineReq == null ? null : reqdSmallbusineReq.trim();
	}
	public String getReqdSmallbusineReq() {
		return this.reqdSmallbusineReq;
	}
	/** 小企业预计到期量 */
	public void setReqdSmallbusineExp(String reqdSmallbusineExp) {
		this.reqdSmallbusineExp = reqdSmallbusineExp == null ? null : reqdSmallbusineExp.trim();
	}
	public String getReqdSmallbusineExp() {
		return this.reqdSmallbusineExp;
	}
	/** 小企业保理需求量 */
	public void setReqdSmallbusinefactorReq(String reqdSmallbusinefactorReq) {
		this.reqdSmallbusinefactorReq = reqdSmallbusinefactorReq == null ? null : reqdSmallbusinefactorReq.trim();
	}
	public String getReqdSmallbusinefactorReq() {
		return this.reqdSmallbusinefactorReq;
	}
	/** 小企业保理预计到期量 */
	public void setReqdSmallbusinefactorExp(String reqdSmallbusinefactorExp) {
		this.reqdSmallbusinefactorExp = reqdSmallbusinefactorExp == null ? null : reqdSmallbusinefactorExp.trim();
	}
	public String getReqdSmallbusinefactorExp() {
		return this.reqdSmallbusinefactorExp;
	}
	/** 住房按揭贷款需求量 */
	public void setReqdHousemortLoanReq(String reqdHousemortLoanReq) {
		this.reqdHousemortLoanReq = reqdHousemortLoanReq == null ? null : reqdHousemortLoanReq.trim();
	}
	public String getReqdHousemortLoanReq() {
		return this.reqdHousemortLoanReq;
	}
	/** 住房按揭贷款预计到期量 */
	public void setReqdHousemortLoanExp(String reqdHousemortLoanExp) {
		this.reqdHousemortLoanExp = reqdHousemortLoanExp == null ? null : reqdHousemortLoanExp.trim();
	}
	public String getReqdHousemortLoanExp() {
		return this.reqdHousemortLoanExp;
	}
	/** 其他消费贷款需求量 */
	public void setReqdOtherconsumLoanReq(String reqdOtherconsumLoanReq) {
		this.reqdOtherconsumLoanReq = reqdOtherconsumLoanReq == null ? null : reqdOtherconsumLoanReq.trim();
	}
	public String getReqdOtherconsumLoanReq() {
		return this.reqdOtherconsumLoanReq;
	}
	/** 其他消费贷款预计到期量 */
	public void setReqdOtherconsumLoanExp(String reqdOtherconsumLoanExp) {
		this.reqdOtherconsumLoanExp = reqdOtherconsumLoanExp == null ? null : reqdOtherconsumLoanExp.trim();
	}
	public String getReqdOtherconsumLoanExp() {
		return this.reqdOtherconsumLoanExp;
	}
	/** 供应链需求量 */
	public void setReqdSupplylineReq(String reqdSupplylineReq) {
		this.reqdSupplylineReq = reqdSupplylineReq == null ? null : reqdSupplylineReq.trim();
	}
	public String getReqdSupplylineReq() {
		return this.reqdSupplylineReq;
	}
	/** 供应链预计到期量 */
	public void setReqdSupplylineExp(String reqdSupplylineExp) {
		this.reqdSupplylineExp = reqdSupplylineExp == null ? null : reqdSupplylineExp.trim();
	}
	public String getReqdSupplylineExp() {
		return this.reqdSupplylineExp;
	}
	/** 国内贸易融资需求量 */
	public void setReqdDomestictradefinanceReq(String reqdDomestictradefinanceReq) {
		this.reqdDomestictradefinanceReq = reqdDomestictradefinanceReq == null ? null : reqdDomestictradefinanceReq.trim();
	}
	public String getReqdDomestictradefinanceReq() {
		return this.reqdDomestictradefinanceReq;
	}
	/** 国内贸易融资预计到期量 */
	public void setReqdDomestictradefinanceExp(String reqdDomestictradefinanceExp) {
		this.reqdDomestictradefinanceExp = reqdDomestictradefinanceExp == null ? null : reqdDomestictradefinanceExp.trim();
	}
	public String getReqdDomestictradefinanceExp() {
		return this.reqdDomestictradefinanceExp;
	}
	/** 国际贸易融资需求量 */
	public void setReqdIntertradefinanceReq(String reqdIntertradefinanceReq) {
		this.reqdIntertradefinanceReq = reqdIntertradefinanceReq == null ? null : reqdIntertradefinanceReq.trim();
	}
	public String getReqdIntertradefinanceReq() {
		return this.reqdIntertradefinanceReq;
	}
	/** 国际贸易融资预计到期量 */
	public void setReqdIntertradefinanceExp(String reqdIntertradefinanceExp) {
		this.reqdIntertradefinanceExp = reqdIntertradefinanceExp == null ? null : reqdIntertradefinanceExp.trim();
	}
	public String getReqdIntertradefinanceExp() {
		return this.reqdIntertradefinanceExp;
	}
	/** 其他公司贷款需求量 */
	public void setReqdOthercorporateLoanReq(String reqdOthercorporateLoanReq) {
		this.reqdOthercorporateLoanReq = reqdOthercorporateLoanReq == null ? null : reqdOthercorporateLoanReq.trim();
	}
	public String getReqdOthercorporateLoanReq() {
		return this.reqdOthercorporateLoanReq;
	}
	/** 其他公司贷款预计到期量 */
	public void setReqdOthercorporateLoanExp(String reqdOthercorporateLoanExp) {
		this.reqdOthercorporateLoanExp = reqdOthercorporateLoanExp == null ? null : reqdOthercorporateLoanExp.trim();
	}
	public String getReqdOthercorporateLoanExp() {
		return this.reqdOthercorporateLoanExp;
	}
	/** 三农公司贷款需求量 */
	public void setReqdSancompanyLoanReq(String reqdSancompanyLoanReq) {
		this.reqdSancompanyLoanReq = reqdSancompanyLoanReq == null ? null : reqdSancompanyLoanReq.trim();
	}
	public String getReqdSancompanyLoanReq() {
		return this.reqdSancompanyLoanReq;
	}
	/** 三农公司贷款到期量 */
	public void setReqdSancompanyLoanExp(String reqdSancompanyLoanExp) {
		this.reqdSancompanyLoanExp = reqdSancompanyLoanExp == null ? null : reqdSancompanyLoanExp.trim();
	}
	public String getReqdSancompanyLoanExp() {
		return this.reqdSancompanyLoanExp;
	}
	/** 并购贷款需求量 */
	public void setReqdMergerLoanReq(String reqdMergerLoanReq) {
		this.reqdMergerLoanReq = reqdMergerLoanReq == null ? null : reqdMergerLoanReq.trim();
	}
	public String getReqdMergerLoanReq() {
		return this.reqdMergerLoanReq;
	}
	/** 并购贷款预计到期量 */
	public void setReqdMergerLoanExp(String reqdMergerLoanExp) {
		this.reqdMergerLoanExp = reqdMergerLoanExp == null ? null : reqdMergerLoanExp.trim();
	}
	public String getReqdMergerLoanExp() {
		return this.reqdMergerLoanExp;
	}
	/** 各项垫款需求量 */
	public void setReqdAlladvanceLoanReq(String reqdAlladvanceLoanReq) {
		this.reqdAlladvanceLoanReq = reqdAlladvanceLoanReq == null ? null : reqdAlladvanceLoanReq.trim();
	}
	public String getReqdAlladvanceLoanReq() {
		return this.reqdAlladvanceLoanReq;
	}
	/** 各项垫款预计到期量 */
	public void setReqdAlladvanceLoanExp(String reqdAlladvanceLoanExp) {
		this.reqdAlladvanceLoanExp = reqdAlladvanceLoanExp == null ? null : reqdAlladvanceLoanExp.trim();
	}
	public String getReqdAlladvanceLoanExp() {
		return this.reqdAlladvanceLoanExp;
	}
	/** 单位透支需求量 */
	public void setReqdUnitoverdraftReq(String reqdUnitoverdraftReq) {
		this.reqdUnitoverdraftReq = reqdUnitoverdraftReq == null ? null : reqdUnitoverdraftReq.trim();
	}
	public String getReqdUnitoverdraftReq() {
		return this.reqdUnitoverdraftReq;
	}
	/** 单位透支预计到期量 */
	public void setReqdUnitoverdraftExp(String reqdUnitoverdraftExp) {
		this.reqdUnitoverdraftExp = reqdUnitoverdraftExp == null ? null : reqdUnitoverdraftExp.trim();
	}
	public String getReqdUnitoverdraftExp() {
		return this.reqdUnitoverdraftExp;
	}
	/** 转贴需求量 */
	public void setReqdRepostReq(String reqdRepostReq) {
		this.reqdRepostReq = reqdRepostReq == null ? null : reqdRepostReq.trim();
	}
	public String getReqdRepostReq() {
		return this.reqdRepostReq;
	}
	/** 转贴预计到期量 */
	public void setReqdRepostExp(String reqdRepostExp) {
		this.reqdRepostExp = reqdRepostExp == null ? null : reqdRepostExp.trim();
	}
	public String getReqdRepostExp() {
		return this.reqdRepostExp;
	}
	/** 直贴需求量 */
	public void setReqdStraightReq(String reqdStraightReq) {
		this.reqdStraightReq = reqdStraightReq == null ? null : reqdStraightReq.trim();
	}
	public String getReqdStraightReq() {
		return this.reqdStraightReq;
	}
	/** 直贴预计到期量 */
	public void setReqdStraightExp(String reqdStraightExp) {
		this.reqdStraightExp = reqdStraightExp == null ? null : reqdStraightExp.trim();
	}
	public String getReqdStraightExp() {
		return this.reqdStraightExp;
	}
	/** -买入福费廷（人民币）需求量 */
	public void setReqdBuyfftrmbReq(String reqdBuyfftrmbReq) {
		this.reqdBuyfftrmbReq = reqdBuyfftrmbReq == null ? null : reqdBuyfftrmbReq.trim();
	}
	public String getReqdBuyfftrmbReq() {
		return this.reqdBuyfftrmbReq;
	}
	/** 买入福费廷（人民币）预计到期量 */
	public void setReqdBuyfftrmbExp(String reqdBuyfftrmbExp) {
		this.reqdBuyfftrmbExp = reqdBuyfftrmbExp == null ? null : reqdBuyfftrmbExp.trim();
	}
	public String getReqdBuyfftrmbExp() {
		return this.reqdBuyfftrmbExp;
	}
	/** 国际公司贷款需求量 */
	public void setReqdIntercorporateLoanReq(String reqdIntercorporateLoanReq) {
		this.reqdIntercorporateLoanReq = reqdIntercorporateLoanReq == null ? null : reqdIntercorporateLoanReq.trim();
	}
	public String getReqdIntercorporateLoanReq() {
		return this.reqdIntercorporateLoanReq;
	}
	/** 国际公司贷款预计到期量 */
	public void setReqdIntercorporateLoanExp(String reqdIntercorporateLoanExp) {
		this.reqdIntercorporateLoanExp = reqdIntercorporateLoanExp == null ? null : reqdIntercorporateLoanExp.trim();
	}
	public String getReqdIntercorporateLoanExp() {
		return this.reqdIntercorporateLoanExp;
	}
	/** 专项融资需求量 */
	public void setReqdSpecialfinaceReq(String reqdSpecialfinaceReq) {
		this.reqdSpecialfinaceReq = reqdSpecialfinaceReq == null ? null : reqdSpecialfinaceReq.trim();
	}
	public String getReqdSpecialfinaceReq() {
		return this.reqdSpecialfinaceReq;
	}
	/** 专项融资预计到期量 */
	public void setReqdSpecialfinaceExp(String reqdSpecialfinaceExp) {
		this.reqdSpecialfinaceExp = reqdSpecialfinaceExp == null ? null : reqdSpecialfinaceExp.trim();
	}
	public String getReqdSpecialfinaceExp() {
		return this.reqdSpecialfinaceExp;
	}
	/** 个人透支需求量 */
	public void setReqdPersonoverdraftReq(String reqdPersonoverdraftReq) {
		this.reqdPersonoverdraftReq = reqdPersonoverdraftReq == null ? null : reqdPersonoverdraftReq.trim();
	}
	public String getReqdPersonoverdraftReq() {
		return this.reqdPersonoverdraftReq;
	}
	/** 个人透支预计到期量 */
	public void setReqdPersonoverdraftExp(String reqdPersonoverdraftExp) {
		this.reqdPersonoverdraftExp = reqdPersonoverdraftExp == null ? null : reqdPersonoverdraftExp.trim();
	}
	public String getReqdPersonoverdraftExp() {
		return this.reqdPersonoverdraftExp;
	}
	/** 外币贷款需求量 */
	public void setReqdForeigncurrencyLoanReq(String reqdForeigncurrencyLoanReq) {
		this.reqdForeigncurrencyLoanReq = reqdForeigncurrencyLoanReq == null ? null : reqdForeigncurrencyLoanReq.trim();
	}
	public String getReqdForeigncurrencyLoanReq() {
		return this.reqdForeigncurrencyLoanReq;
	}
	/** 外币贷款预计到期量 */
	public void setReqdForeigncurrencyLoanExp(String reqdForeigncurrencyLoanExp) {
		this.reqdForeigncurrencyLoanExp = reqdForeigncurrencyLoanExp == null ? null : reqdForeigncurrencyLoanExp.trim();
	}
	public String getReqdForeigncurrencyLoanExp() {
		return this.reqdForeigncurrencyLoanExp;
	}
	/** 买入福费廷（外币）需求量 */
	public void setReqdBuyfftforeigncurReq(String reqdBuyfftforeigncurReq) {
		this.reqdBuyfftforeigncurReq = reqdBuyfftforeigncurReq == null ? null : reqdBuyfftforeigncurReq.trim();
	}
	public String getReqdBuyfftforeigncurReq() {
		return this.reqdBuyfftforeigncurReq;
	}
	/** 买入福费廷（外币）预计到期量 */
	public void setReqdBuyfftforeigncurExp(String reqdBuyfftforeigncurExp) {
		this.reqdBuyfftforeigncurExp = reqdBuyfftforeigncurExp == null ? null : reqdBuyfftforeigncurExp.trim();
	}
	public String getReqdBuyfftforeigncurExp() {
		return this.reqdBuyfftforeigncurExp;
	}
	/** 其他需求量 */
	public void setReqdOtherReq(String reqdOtherReq) {
		this.reqdOtherReq = reqdOtherReq == null ? null : reqdOtherReq.trim();
	}
	public String getReqdOtherReq() {
		return this.reqdOtherReq;
	}
	/** 其他预计到期量 */
	public void setReqdOtherExp(String reqdOtherExp) {
		this.reqdOtherExp = reqdOtherExp == null ? null : reqdOtherExp.trim();
	}
	public String getReqdOtherExp() {
		return this.reqdOtherExp;
	}
	/** 创建时间 */
	public void setReqdCreateTime(String reqdCreateTime) {
		this.reqdCreateTime = reqdCreateTime == null ? null : reqdCreateTime.trim();
	}
	public String getReqdCreateTime() {
		return this.reqdCreateTime;
	}
	/** 更新时间 */
	public void setReqdUpdateTime(String reqdUpdateTime) {
		this.reqdUpdateTime = reqdUpdateTime == null ? null : reqdUpdateTime.trim();
	}
	public String getReqdUpdateTime() {
		return this.reqdUpdateTime;
	}
	/** 法人账户透支需求量 */
	public void setReqdCorporateaccoverdraftLoanReq(String reqdCorporateaccoverdraftLoanReq) {
		this.reqdCorporateaccoverdraftLoanReq = reqdCorporateaccoverdraftLoanReq == null ? null : reqdCorporateaccoverdraftLoanReq.trim();
	}
	public String getReqdCorporateaccoverdraftLoanReq() {
		return this.reqdCorporateaccoverdraftLoanReq;
	}
	/** 法人账户透支预计到期量 */
	public void setReqdCorporateaccoverdraftLoanExp(String reqdCorporateaccoverdraftLoanExp) {
		this.reqdCorporateaccoverdraftLoanExp = reqdCorporateaccoverdraftLoanExp == null ? null : reqdCorporateaccoverdraftLoanExp.trim();
	}
	public String getReqdCorporateaccoverdraftLoanExp() {
		return this.reqdCorporateaccoverdraftLoanExp;
	}
	/** 拆放非银需求量 */
	public void setReqdDisassemblyReq(String reqdDisassemblyReq) {
		this.reqdDisassemblyReq = reqdDisassemblyReq == null ? null : reqdDisassemblyReq.trim();
	}
	public String getReqdDisassemblyReq() {
		return this.reqdDisassemblyReq;
	}
	/** 拆放非银到期量 */
	public void setReqdDisassemblyExp(String reqdDisassemblyExp) {
		this.reqdDisassemblyExp = reqdDisassemblyExp == null ? null : reqdDisassemblyExp.trim();
	}
	public String getReqdDisassemblyExp() {
		return this.reqdDisassemblyExp;
	}
}