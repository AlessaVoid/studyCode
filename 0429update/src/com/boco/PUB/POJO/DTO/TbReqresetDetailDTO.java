package com.boco.PUB.POJO.DTO;

import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * 调整申请报送要求录入详细表实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbReqresetDetailDTO extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 编码id */
		@EntityParaAnno(zhName="编码id")
	private Integer reqdresetId;
	/** 所属批次id */
		@EntityParaAnno(zhName="所属批次id")
	private Integer reqdresetRefId;
	/** 填报机构id */
		@EntityParaAnno(zhName="填报机构id")
	private String reqdresetOrgan;
	/** 单位 */
		@EntityParaAnno(zhName="单位")
	private Integer reqdresetUnit;
	/** 上报人 */
		@EntityParaAnno(zhName="上报人")
	private String reqdresetOper;
	/** 条目状态 */
		@EntityParaAnno(zhName="条目状态")
	private Integer reqdresetState;
	/** 小额贷款额度调整方向 */
		@EntityParaAnno(zhName="小额贷款额度调整方向")
	private Integer reqdresetSmallamountLoanDir;
	/** 小额贷款预计调整额度 */
		@EntityParaAnno(zhName="小额贷款预计调整额度")
	private String reqdresetSmallamountLoanAmt;
	/** 其他贷款额度调整方向 */
		@EntityParaAnno(zhName="其他贷款额度调整方向")
	private Integer reqdresetOtherLoanDir;
	/** 其他贷款预计调整额度 */
		@EntityParaAnno(zhName="其他贷款预计调整额度")
	private String reqdresetOtherLoanAmt;
	/** 个商贷款额度调整方向 */
		@EntityParaAnno(zhName="个商贷款额度调整方向")
	private Integer reqdresetPerbusineLoanDir;
	/** 个商贷款预计调整额度 */
		@EntityParaAnno(zhName="个商贷款预计调整额度")
	private String reqdresetPerbusineLoanAmt;
	/** 小企业额度调整方向 */
		@EntityParaAnno(zhName="小企业额度调整方向")
	private Integer reqdresetSmallbusineDir;
	/** 小企业预计调整额度 */
		@EntityParaAnno(zhName="小企业预计调整额度")
	private String reqdresetSmallbusineAmt;
	/** 小企业保理额度调整方向 */
		@EntityParaAnno(zhName="小企业保理额度调整方向")
	private Integer reqdresetSmallbusinefactorDir;
	/** 小企业保理预计调整额度 */
		@EntityParaAnno(zhName="小企业保理预计调整额度")
	private String reqdresetSmallbusinefactorAmt;
	/** 住房按揭贷款额度调整方向 */
		@EntityParaAnno(zhName="住房按揭贷款额度调整方向")
	private Integer reqdresetHousemortLoanDir;
	/** 住房按揭贷款预计调整额度 */
		@EntityParaAnno(zhName="住房按揭贷款预计调整额度")
	private String reqdresetHousemortLoanAmt;
	/** 其他消费贷款额度调整方向 */
		@EntityParaAnno(zhName="其他消费贷款额度调整方向")
	private Integer reqdresetOtherconsumLoanDir;
	/** 其他消费贷款预计调整额度 */
		@EntityParaAnno(zhName="其他消费贷款预计调整额度")
	private String reqdresetOtherconsumLoanAmt;
	/** 供应链额度调整方向 */
		@EntityParaAnno(zhName="供应链额度调整方向")
	private Integer reqdresetSupplylineDir;
	/** 供应链预计调整额度 */
		@EntityParaAnno(zhName="供应链预计调整额度")
	private String reqdresetSupplylineAmt;
	/** 国内贸易融资额度调整方向 */
		@EntityParaAnno(zhName="国内贸易融资额度调整方向")
	private Integer reqdresetDomestictradefinanceDir;
	/** 国内贸易融资预计调整额度 */
		@EntityParaAnno(zhName="国内贸易融资预计调整额度")
	private String reqdresetDomestictradefinanceAmt;
	/** 国际贸易融资额度调整方向 */
		@EntityParaAnno(zhName="国际贸易融资额度调整方向")
	private Integer reqdresetIntertradefinanceDir;
	/** 国际贸易融资预计调整额度 */
		@EntityParaAnno(zhName="国际贸易融资预计调整额度")
	private String reqdresetIntertradefinanceAmt;
	/** 其他公司贷款额度调整方向 */
		@EntityParaAnno(zhName="其他公司贷款额度调整方向")
	private Integer reqdresetOthercorporateLoanDir;
	/** 其他公司贷款预计调整额度 */
		@EntityParaAnno(zhName="其他公司贷款预计调整额度")
	private String reqdresetOthercorporateLoanAmt;
	/** 法人账户透支额度调整方向 */
		@EntityParaAnno(zhName="法人账户透支额度调整方向")
	private Integer reqdresetCorporateaccoverdraftLoanDir;
	/** 法人账户透支预计调整额度 */
		@EntityParaAnno(zhName="法人账户透支预计调整额度")
	private String reqdresetCorporateaccoverdraftLoanAmt;
	/** 三农公司贷款额度调整方向 */
		@EntityParaAnno(zhName="三农公司贷款额度调整方向")
	private Integer reqdresetSancompanyLoanDir;
	/** 三农公司贷款预计调整额度 */
		@EntityParaAnno(zhName="三农公司贷款预计调整额度")
	private String reqdresetSancompanyLoanAmt;
	/** 并购贷款额度调整方向 */
		@EntityParaAnno(zhName="并购贷款额度调整方向")
	private Integer reqdresetMergerLoanDir;
	/** 并购贷款预计调整额度 */
		@EntityParaAnno(zhName="并购贷款预计调整额度")
	private String reqdresetMergerLoanAmt;
	/** 各项垫款额度调整方向 */
		@EntityParaAnno(zhName="各项垫款额度调整方向")
	private Integer reqdresetAlladvanceLoanDir;
	/** 各项垫款预计调整额度 */
		@EntityParaAnno(zhName="各项垫款预计调整额度")
	private String reqdresetAlladvanceLoanAmt;
	/** 单位透支额度调整方向 */
		@EntityParaAnno(zhName="单位透支额度调整方向")
	private Integer reqdresetUnitoverdraftDir;
	/** 单位透支预计调整额度 */
		@EntityParaAnno(zhName="单位透支预计调整额度")
	private String reqdresetUnitoverdraftAmt;
	/** 转贴额度调整方向 */
		@EntityParaAnno(zhName="转贴额度调整方向")
	private Integer reqdresetRepostDir;
	/** 转贴预计调整额度 */
		@EntityParaAnno(zhName="转贴预计调整额度")
	private String reqdresetRepostAmt;
	/** 直贴额度调整方向 */
		@EntityParaAnno(zhName="直贴额度调整方向")
	private Integer reqdresetStraightDir;
	/** 直贴预计调整额度 */
		@EntityParaAnno(zhName="直贴预计调整额度")
	private String reqdresetStraightAmt;
	/** 买入福费廷（人民币）额度调整方向 */
		@EntityParaAnno(zhName="买入福费廷（人民币）额度调整方向")
	private Integer reqdresetBuyfftrmbDir;
	/** 买入福费廷（人民币）预计调整额度 */
		@EntityParaAnno(zhName="买入福费廷（人民币）预计调整额度")
	private String reqdresetBuyfftrmbAmt;
	/** 国际公司贷款额度调整方向 */
		@EntityParaAnno(zhName="国际公司贷款额度调整方向")
	private Integer reqdresetIntercorporateLoanDir;
	/** 国际公司贷款预计调整额度 */
		@EntityParaAnno(zhName="国际公司贷款预计调整额度")
	private String reqdresetIntercorporateLoanAmt;
	/** 专项融资额度调整方向 */
		@EntityParaAnno(zhName="专项融资额度调整方向")
	private Integer reqdresetSpecialfinaceDir;
	/** 专项融资预计调整额度 */
		@EntityParaAnno(zhName="专项融资预计调整额度")
	private String reqdresetSpecialfinaceAmt;
	/** 个人透支额度调整方向 */
		@EntityParaAnno(zhName="个人透支额度调整方向")
	private Integer reqdresetPersonoverdraftDir;
	/** 个人透支预计调整额度 */
		@EntityParaAnno(zhName="个人透支预计调整额度")
	private String reqdresetPersonoverdraftAmt;
	/** 外币贷款额度调整方向 */
		@EntityParaAnno(zhName="外币贷款额度调整方向")
	private Integer reqdresetForeigncurrencyLoanDir;
	/** 外币贷款预计调整额度 */
		@EntityParaAnno(zhName="外币贷款预计调整额度")
	private String reqdresetForeigncurrencyLoanAmt;
	/** 买入福费廷（外币）额度调整方向 */
		@EntityParaAnno(zhName="买入福费廷（外币）额度调整方向")
	private Integer reqdresetBuyfftforeigncurDir;
	/** 买入福费廷（外币）预计调整额度 */
		@EntityParaAnno(zhName="买入福费廷（外币）预计调整额度")
	private String reqdresetBuyfftforeigncurAmt;
	/** 其他额度调整方向 */
		@EntityParaAnno(zhName="其他额度调整方向")
	private Integer reqdresetOtherDir;
	/** 其他预计调整额度 */
		@EntityParaAnno(zhName="其他预计调整额度")
	private String reqdresetOtherAmt;
	/** 创建时间 */
		@EntityParaAnno(zhName="创建时间")
	private String reqdresetCreatetime;
	/** 更新时间 */
		@EntityParaAnno(zhName="更新时间")
	private String reqdresetUpdatetime;
	/** 操作人员 */
		@EntityParaAnno(zhName="操作人员")
	private String reqdresetUpdateoper;
	/** 拆放非银额度调整方向 */
		@EntityParaAnno(zhName="拆放非银额度调整方向")
	private Integer reqdresetDisassemblyDir;
	/** 拆放非银预计调整额度 */
		@EntityParaAnno(zhName="拆放非银预计调整额度")
	private String reqdresetDisassemblyAmt;
	
	/** setter\getter方法 */
	/** 编码id */
	public void setReqdresetId(Integer reqdresetId) {
		this.reqdresetId = reqdresetId;
	}
	public Integer getReqdresetId() {
		return this.reqdresetId;
	}
	/** 所属批次id */
	public void setReqdresetRefId(Integer reqdresetRefId) {
		this.reqdresetRefId = reqdresetRefId;
	}
	public Integer getReqdresetRefId() {
		return this.reqdresetRefId;
	}
	/** 填报机构id */
	public void setReqdresetOrgan(String reqdresetOrgan) {
		this.reqdresetOrgan = reqdresetOrgan == null ? null : reqdresetOrgan.trim();
	}
	public String getReqdresetOrgan() {
		return this.reqdresetOrgan;
	}
	/** 单位 */
	public void setReqdresetUnit(Integer reqdresetUnit) {
		this.reqdresetUnit = reqdresetUnit;
	}
	public Integer getReqdresetUnit() {
		return this.reqdresetUnit;
	}
	/** 上报人 */
	public void setReqdresetOper(String reqdresetOper) {
		this.reqdresetOper = reqdresetOper == null ? null : reqdresetOper.trim();
	}
	public String getReqdresetOper() {
		return this.reqdresetOper;
	}
	/** 条目状态 */
	public void setReqdresetState(Integer reqdresetState) {
		this.reqdresetState = reqdresetState;
	}
	public Integer getReqdresetState() {
		return this.reqdresetState;
	}
	/** 小额贷款额度调整方向 */
	public void setReqdresetSmallamountLoanDir(Integer reqdresetSmallamountLoanDir) {
		this.reqdresetSmallamountLoanDir = reqdresetSmallamountLoanDir;
	}
	public Integer getReqdresetSmallamountLoanDir() {
		return this.reqdresetSmallamountLoanDir;
	}
	/** 小额贷款预计调整额度 */
	public void setReqdresetSmallamountLoanAmt(String reqdresetSmallamountLoanAmt) {
		this.reqdresetSmallamountLoanAmt = reqdresetSmallamountLoanAmt == null ? null : reqdresetSmallamountLoanAmt.trim();
	}
	public String getReqdresetSmallamountLoanAmt() {
		return this.reqdresetSmallamountLoanAmt;
	}
	/** 其他贷款额度调整方向 */
	public void setReqdresetOtherLoanDir(Integer reqdresetOtherLoanDir) {
		this.reqdresetOtherLoanDir = reqdresetOtherLoanDir;
	}
	public Integer getReqdresetOtherLoanDir() {
		return this.reqdresetOtherLoanDir;
	}
	/** 其他贷款预计调整额度 */
	public void setReqdresetOtherLoanAmt(String reqdresetOtherLoanAmt) {
		this.reqdresetOtherLoanAmt = reqdresetOtherLoanAmt == null ? null : reqdresetOtherLoanAmt.trim();
	}
	public String getReqdresetOtherLoanAmt() {
		return this.reqdresetOtherLoanAmt;
	}
	/** 个商贷款额度调整方向 */
	public void setReqdresetPerbusineLoanDir(Integer reqdresetPerbusineLoanDir) {
		this.reqdresetPerbusineLoanDir = reqdresetPerbusineLoanDir;
	}
	public Integer getReqdresetPerbusineLoanDir() {
		return this.reqdresetPerbusineLoanDir;
	}
	/** 个商贷款预计调整额度 */
	public void setReqdresetPerbusineLoanAmt(String reqdresetPerbusineLoanAmt) {
		this.reqdresetPerbusineLoanAmt = reqdresetPerbusineLoanAmt == null ? null : reqdresetPerbusineLoanAmt.trim();
	}
	public String getReqdresetPerbusineLoanAmt() {
		return this.reqdresetPerbusineLoanAmt;
	}
	/** 小企业额度调整方向 */
	public void setReqdresetSmallbusineDir(Integer reqdresetSmallbusineDir) {
		this.reqdresetSmallbusineDir = reqdresetSmallbusineDir;
	}
	public Integer getReqdresetSmallbusineDir() {
		return this.reqdresetSmallbusineDir;
	}
	/** 小企业预计调整额度 */
	public void setReqdresetSmallbusineAmt(String reqdresetSmallbusineAmt) {
		this.reqdresetSmallbusineAmt = reqdresetSmallbusineAmt == null ? null : reqdresetSmallbusineAmt.trim();
	}
	public String getReqdresetSmallbusineAmt() {
		return this.reqdresetSmallbusineAmt;
	}
	/** 小企业保理额度调整方向 */
	public void setReqdresetSmallbusinefactorDir(Integer reqdresetSmallbusinefactorDir) {
		this.reqdresetSmallbusinefactorDir = reqdresetSmallbusinefactorDir;
	}
	public Integer getReqdresetSmallbusinefactorDir() {
		return this.reqdresetSmallbusinefactorDir;
	}
	/** 小企业保理预计调整额度 */
	public void setReqdresetSmallbusinefactorAmt(String reqdresetSmallbusinefactorAmt) {
		this.reqdresetSmallbusinefactorAmt = reqdresetSmallbusinefactorAmt == null ? null : reqdresetSmallbusinefactorAmt.trim();
	}
	public String getReqdresetSmallbusinefactorAmt() {
		return this.reqdresetSmallbusinefactorAmt;
	}
	/** 住房按揭贷款额度调整方向 */
	public void setReqdresetHousemortLoanDir(Integer reqdresetHousemortLoanDir) {
		this.reqdresetHousemortLoanDir = reqdresetHousemortLoanDir;
	}
	public Integer getReqdresetHousemortLoanDir() {
		return this.reqdresetHousemortLoanDir;
	}
	/** 住房按揭贷款预计调整额度 */
	public void setReqdresetHousemortLoanAmt(String reqdresetHousemortLoanAmt) {
		this.reqdresetHousemortLoanAmt = reqdresetHousemortLoanAmt == null ? null : reqdresetHousemortLoanAmt.trim();
	}
	public String getReqdresetHousemortLoanAmt() {
		return this.reqdresetHousemortLoanAmt;
	}
	/** 其他消费贷款额度调整方向 */
	public void setReqdresetOtherconsumLoanDir(Integer reqdresetOtherconsumLoanDir) {
		this.reqdresetOtherconsumLoanDir = reqdresetOtherconsumLoanDir;
	}
	public Integer getReqdresetOtherconsumLoanDir() {
		return this.reqdresetOtherconsumLoanDir;
	}
	/** 其他消费贷款预计调整额度 */
	public void setReqdresetOtherconsumLoanAmt(String reqdresetOtherconsumLoanAmt) {
		this.reqdresetOtherconsumLoanAmt = reqdresetOtherconsumLoanAmt == null ? null : reqdresetOtherconsumLoanAmt.trim();
	}
	public String getReqdresetOtherconsumLoanAmt() {
		return this.reqdresetOtherconsumLoanAmt;
	}
	/** 供应链额度调整方向 */
	public void setReqdresetSupplylineDir(Integer reqdresetSupplylineDir) {
		this.reqdresetSupplylineDir = reqdresetSupplylineDir;
	}
	public Integer getReqdresetSupplylineDir() {
		return this.reqdresetSupplylineDir;
	}
	/** 供应链预计调整额度 */
	public void setReqdresetSupplylineAmt(String reqdresetSupplylineAmt) {
		this.reqdresetSupplylineAmt = reqdresetSupplylineAmt == null ? null : reqdresetSupplylineAmt.trim();
	}
	public String getReqdresetSupplylineAmt() {
		return this.reqdresetSupplylineAmt;
	}
	/** 国内贸易融资额度调整方向 */
	public void setReqdresetDomestictradefinanceDir(Integer reqdresetDomestictradefinanceDir) {
		this.reqdresetDomestictradefinanceDir = reqdresetDomestictradefinanceDir;
	}
	public Integer getReqdresetDomestictradefinanceDir() {
		return this.reqdresetDomestictradefinanceDir;
	}
	/** 国内贸易融资预计调整额度 */
	public void setReqdresetDomestictradefinanceAmt(String reqdresetDomestictradefinanceAmt) {
		this.reqdresetDomestictradefinanceAmt = reqdresetDomestictradefinanceAmt == null ? null : reqdresetDomestictradefinanceAmt.trim();
	}
	public String getReqdresetDomestictradefinanceAmt() {
		return this.reqdresetDomestictradefinanceAmt;
	}
	/** 国际贸易融资额度调整方向 */
	public void setReqdresetIntertradefinanceDir(Integer reqdresetIntertradefinanceDir) {
		this.reqdresetIntertradefinanceDir = reqdresetIntertradefinanceDir;
	}
	public Integer getReqdresetIntertradefinanceDir() {
		return this.reqdresetIntertradefinanceDir;
	}
	/** 国际贸易融资预计调整额度 */
	public void setReqdresetIntertradefinanceAmt(String reqdresetIntertradefinanceAmt) {
		this.reqdresetIntertradefinanceAmt = reqdresetIntertradefinanceAmt == null ? null : reqdresetIntertradefinanceAmt.trim();
	}
	public String getReqdresetIntertradefinanceAmt() {
		return this.reqdresetIntertradefinanceAmt;
	}
	/** 其他公司贷款额度调整方向 */
	public void setReqdresetOthercorporateLoanDir(Integer reqdresetOthercorporateLoanDir) {
		this.reqdresetOthercorporateLoanDir = reqdresetOthercorporateLoanDir;
	}
	public Integer getReqdresetOthercorporateLoanDir() {
		return this.reqdresetOthercorporateLoanDir;
	}
	/** 其他公司贷款预计调整额度 */
	public void setReqdresetOthercorporateLoanAmt(String reqdresetOthercorporateLoanAmt) {
		this.reqdresetOthercorporateLoanAmt = reqdresetOthercorporateLoanAmt == null ? null : reqdresetOthercorporateLoanAmt.trim();
	}
	public String getReqdresetOthercorporateLoanAmt() {
		return this.reqdresetOthercorporateLoanAmt;
	}
	/** 法人账户透支额度调整方向 */
	public void setReqdresetCorporateaccoverdraftLoanDir(Integer reqdresetCorporateaccoverdraftLoanDir) {
		this.reqdresetCorporateaccoverdraftLoanDir = reqdresetCorporateaccoverdraftLoanDir;
	}
	public Integer getReqdresetCorporateaccoverdraftLoanDir() {
		return this.reqdresetCorporateaccoverdraftLoanDir;
	}
	/** 法人账户透支预计调整额度 */
	public void setReqdresetCorporateaccoverdraftLoanAmt(String reqdresetCorporateaccoverdraftLoanAmt) {
		this.reqdresetCorporateaccoverdraftLoanAmt = reqdresetCorporateaccoverdraftLoanAmt == null ? null : reqdresetCorporateaccoverdraftLoanAmt.trim();
	}
	public String getReqdresetCorporateaccoverdraftLoanAmt() {
		return this.reqdresetCorporateaccoverdraftLoanAmt;
	}
	/** 三农公司贷款额度调整方向 */
	public void setReqdresetSancompanyLoanDir(Integer reqdresetSancompanyLoanDir) {
		this.reqdresetSancompanyLoanDir = reqdresetSancompanyLoanDir;
	}
	public Integer getReqdresetSancompanyLoanDir() {
		return this.reqdresetSancompanyLoanDir;
	}
	/** 三农公司贷款预计调整额度 */
	public void setReqdresetSancompanyLoanAmt(String reqdresetSancompanyLoanAmt) {
		this.reqdresetSancompanyLoanAmt = reqdresetSancompanyLoanAmt == null ? null : reqdresetSancompanyLoanAmt.trim();
	}
	public String getReqdresetSancompanyLoanAmt() {
		return this.reqdresetSancompanyLoanAmt;
	}
	/** 并购贷款额度调整方向 */
	public void setReqdresetMergerLoanDir(Integer reqdresetMergerLoanDir) {
		this.reqdresetMergerLoanDir = reqdresetMergerLoanDir;
	}
	public Integer getReqdresetMergerLoanDir() {
		return this.reqdresetMergerLoanDir;
	}
	/** 并购贷款预计调整额度 */
	public void setReqdresetMergerLoanAmt(String reqdresetMergerLoanAmt) {
		this.reqdresetMergerLoanAmt = reqdresetMergerLoanAmt == null ? null : reqdresetMergerLoanAmt.trim();
	}
	public String getReqdresetMergerLoanAmt() {
		return this.reqdresetMergerLoanAmt;
	}
	/** 各项垫款额度调整方向 */
	public void setReqdresetAlladvanceLoanDir(Integer reqdresetAlladvanceLoanDir) {
		this.reqdresetAlladvanceLoanDir = reqdresetAlladvanceLoanDir;
	}
	public Integer getReqdresetAlladvanceLoanDir() {
		return this.reqdresetAlladvanceLoanDir;
	}
	/** 各项垫款预计调整额度 */
	public void setReqdresetAlladvanceLoanAmt(String reqdresetAlladvanceLoanAmt) {
		this.reqdresetAlladvanceLoanAmt = reqdresetAlladvanceLoanAmt == null ? null : reqdresetAlladvanceLoanAmt.trim();
	}
	public String getReqdresetAlladvanceLoanAmt() {
		return this.reqdresetAlladvanceLoanAmt;
	}
	/** 单位透支额度调整方向 */
	public void setReqdresetUnitoverdraftDir(Integer reqdresetUnitoverdraftDir) {
		this.reqdresetUnitoverdraftDir = reqdresetUnitoverdraftDir;
	}
	public Integer getReqdresetUnitoverdraftDir() {
		return this.reqdresetUnitoverdraftDir;
	}
	/** 单位透支预计调整额度 */
	public void setReqdresetUnitoverdraftAmt(String reqdresetUnitoverdraftAmt) {
		this.reqdresetUnitoverdraftAmt = reqdresetUnitoverdraftAmt == null ? null : reqdresetUnitoverdraftAmt.trim();
	}
	public String getReqdresetUnitoverdraftAmt() {
		return this.reqdresetUnitoverdraftAmt;
	}
	/** 转贴额度调整方向 */
	public void setReqdresetRepostDir(Integer reqdresetRepostDir) {
		this.reqdresetRepostDir = reqdresetRepostDir;
	}
	public Integer getReqdresetRepostDir() {
		return this.reqdresetRepostDir;
	}
	/** 转贴预计调整额度 */
	public void setReqdresetRepostAmt(String reqdresetRepostAmt) {
		this.reqdresetRepostAmt = reqdresetRepostAmt == null ? null : reqdresetRepostAmt.trim();
	}
	public String getReqdresetRepostAmt() {
		return this.reqdresetRepostAmt;
	}
	/** 直贴额度调整方向 */
	public void setReqdresetStraightDir(Integer reqdresetStraightDir) {
		this.reqdresetStraightDir = reqdresetStraightDir;
	}
	public Integer getReqdresetStraightDir() {
		return this.reqdresetStraightDir;
	}
	/** 直贴预计调整额度 */
	public void setReqdresetStraightAmt(String reqdresetStraightAmt) {
		this.reqdresetStraightAmt = reqdresetStraightAmt == null ? null : reqdresetStraightAmt.trim();
	}
	public String getReqdresetStraightAmt() {
		return this.reqdresetStraightAmt;
	}
	/** 买入福费廷（人民币）额度调整方向 */
	public void setReqdresetBuyfftrmbDir(Integer reqdresetBuyfftrmbDir) {
		this.reqdresetBuyfftrmbDir = reqdresetBuyfftrmbDir;
	}
	public Integer getReqdresetBuyfftrmbDir() {
		return this.reqdresetBuyfftrmbDir;
	}
	/** 买入福费廷（人民币）预计调整额度 */
	public void setReqdresetBuyfftrmbAmt(String reqdresetBuyfftrmbAmt) {
		this.reqdresetBuyfftrmbAmt = reqdresetBuyfftrmbAmt == null ? null : reqdresetBuyfftrmbAmt.trim();
	}
	public String getReqdresetBuyfftrmbAmt() {
		return this.reqdresetBuyfftrmbAmt;
	}
	/** 国际公司贷款额度调整方向 */
	public void setReqdresetIntercorporateLoanDir(Integer reqdresetIntercorporateLoanDir) {
		this.reqdresetIntercorporateLoanDir = reqdresetIntercorporateLoanDir;
	}
	public Integer getReqdresetIntercorporateLoanDir() {
		return this.reqdresetIntercorporateLoanDir;
	}
	/** 国际公司贷款预计调整额度 */
	public void setReqdresetIntercorporateLoanAmt(String reqdresetIntercorporateLoanAmt) {
		this.reqdresetIntercorporateLoanAmt = reqdresetIntercorporateLoanAmt == null ? null : reqdresetIntercorporateLoanAmt.trim();
	}
	public String getReqdresetIntercorporateLoanAmt() {
		return this.reqdresetIntercorporateLoanAmt;
	}
	/** 专项融资额度调整方向 */
	public void setReqdresetSpecialfinaceDir(Integer reqdresetSpecialfinaceDir) {
		this.reqdresetSpecialfinaceDir = reqdresetSpecialfinaceDir;
	}
	public Integer getReqdresetSpecialfinaceDir() {
		return this.reqdresetSpecialfinaceDir;
	}
	/** 专项融资预计调整额度 */
	public void setReqdresetSpecialfinaceAmt(String reqdresetSpecialfinaceAmt) {
		this.reqdresetSpecialfinaceAmt = reqdresetSpecialfinaceAmt == null ? null : reqdresetSpecialfinaceAmt.trim();
	}
	public String getReqdresetSpecialfinaceAmt() {
		return this.reqdresetSpecialfinaceAmt;
	}
	/** 个人透支额度调整方向 */
	public void setReqdresetPersonoverdraftDir(Integer reqdresetPersonoverdraftDir) {
		this.reqdresetPersonoverdraftDir = reqdresetPersonoverdraftDir;
	}
	public Integer getReqdresetPersonoverdraftDir() {
		return this.reqdresetPersonoverdraftDir;
	}
	/** 个人透支预计调整额度 */
	public void setReqdresetPersonoverdraftAmt(String reqdresetPersonoverdraftAmt) {
		this.reqdresetPersonoverdraftAmt = reqdresetPersonoverdraftAmt == null ? null : reqdresetPersonoverdraftAmt.trim();
	}
	public String getReqdresetPersonoverdraftAmt() {
		return this.reqdresetPersonoverdraftAmt;
	}
	/** 外币贷款额度调整方向 */
	public void setReqdresetForeigncurrencyLoanDir(Integer reqdresetForeigncurrencyLoanDir) {
		this.reqdresetForeigncurrencyLoanDir = reqdresetForeigncurrencyLoanDir;
	}
	public Integer getReqdresetForeigncurrencyLoanDir() {
		return this.reqdresetForeigncurrencyLoanDir;
	}
	/** 外币贷款预计调整额度 */
	public void setReqdresetForeigncurrencyLoanAmt(String reqdresetForeigncurrencyLoanAmt) {
		this.reqdresetForeigncurrencyLoanAmt = reqdresetForeigncurrencyLoanAmt == null ? null : reqdresetForeigncurrencyLoanAmt.trim();
	}
	public String getReqdresetForeigncurrencyLoanAmt() {
		return this.reqdresetForeigncurrencyLoanAmt;
	}
	/** 买入福费廷（外币）额度调整方向 */
	public void setReqdresetBuyfftforeigncurDir(Integer reqdresetBuyfftforeigncurDir) {
		this.reqdresetBuyfftforeigncurDir = reqdresetBuyfftforeigncurDir;
	}
	public Integer getReqdresetBuyfftforeigncurDir() {
		return this.reqdresetBuyfftforeigncurDir;
	}
	/** 买入福费廷（外币）预计调整额度 */
	public void setReqdresetBuyfftforeigncurAmt(String reqdresetBuyfftforeigncurAmt) {
		this.reqdresetBuyfftforeigncurAmt = reqdresetBuyfftforeigncurAmt == null ? null : reqdresetBuyfftforeigncurAmt.trim();
	}
	public String getReqdresetBuyfftforeigncurAmt() {
		return this.reqdresetBuyfftforeigncurAmt;
	}
	/** 其他额度调整方向 */
	public void setReqdresetOtherDir(Integer reqdresetOtherDir) {
		this.reqdresetOtherDir = reqdresetOtherDir;
	}
	public Integer getReqdresetOtherDir() {
		return this.reqdresetOtherDir;
	}
	/** 其他预计调整额度 */
	public void setReqdresetOtherAmt(String reqdresetOtherAmt) {
		this.reqdresetOtherAmt = reqdresetOtherAmt == null ? null : reqdresetOtherAmt.trim();
	}
	public String getReqdresetOtherAmt() {
		return this.reqdresetOtherAmt;
	}
	/** 创建时间 */
	public void setReqdresetCreatetime(String reqdresetCreatetime) {
		this.reqdresetCreatetime = reqdresetCreatetime == null ? null : reqdresetCreatetime.trim();
	}
	public String getReqdresetCreatetime() {
		return this.reqdresetCreatetime;
	}
	/** 更新时间 */
	public void setReqdresetUpdatetime(String reqdresetUpdatetime) {
		this.reqdresetUpdatetime = reqdresetUpdatetime == null ? null : reqdresetUpdatetime.trim();
	}
	public String getReqdresetUpdatetime() {
		return this.reqdresetUpdatetime;
	}
	/** 操作人员 */
	public void setReqdresetUpdateoper(String reqdresetUpdateoper) {
		this.reqdresetUpdateoper = reqdresetUpdateoper == null ? null : reqdresetUpdateoper.trim();
	}
	public String getReqdresetUpdateoper() {
		return this.reqdresetUpdateoper;
	}
	/** 拆放非银额度调整方向 */
	public void setReqdresetDisassemblyDir(Integer reqdresetDisassemblyDir) {
		this.reqdresetDisassemblyDir = reqdresetDisassemblyDir;
	}
	public Integer getReqdresetDisassemblyDir() {
		return this.reqdresetDisassemblyDir;
	}
	/** 拆放非银预计调整额度 */
	public void setReqdresetDisassemblyAmt(String reqdresetDisassemblyAmt) {
		this.reqdresetDisassemblyAmt = reqdresetDisassemblyAmt == null ? null : reqdresetDisassemblyAmt.trim();
	}
	public String getReqdresetDisassemblyAmt() {
		return this.reqdresetDisassemblyAmt;
	}
}