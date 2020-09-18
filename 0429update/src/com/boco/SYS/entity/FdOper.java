package com.boco.SYS.entity;

import com.boco.SYS.base.BaseEntity;

/**
 * 
 * 
 * FdOper实体类
 * 
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29  杨滔      批量新建
 * </pre>
 */
public class FdOper extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */
	/** 机构代号  */
	private java.lang.String organcode;
	/** 操作员代码 */
	private java.lang.String opercode;
	/** 操作员姓名     */
	private java.lang.String opername;
	/** 操作员密码    */
	private java.lang.String operpassword;
	/** 操作员状态    0-签到 1-签退 2-注销  */
	private java.lang.String operstate;
	/** 操作员身份（级别）1-柜员,2-综合柜员,3-所主任（支局长）,4-二级出纳,5-出纳,6-会计员,7-凭证管理员,8-业务主管   */
	private java.lang.String operdegree;
	/** 注册终端号       */
	private java.lang.String ttyno;
	/** 虚拟操作员标志  0-代理网点 1-电话银行 2-ATM  3-POS  4-网上银行  */
	private java.lang.String virtualflag;
	/** 凭证套号(会计分录流水的一个流水号) */
	private java.lang.Long voucherserial;
	/** 当前流水号  */
	private java.lang.Long acctserial;
	/** 日志流水号   */
	private java.lang.Long logserial;
	/** 修改时间(对该表的内容修改时保留痕迹)  */
	private java.lang.String modifydate;
	/** 修改机构(对该表的内容修改时保留痕迹) */
	private java.lang.String modifyorgan;
	/** 修改柜员(对该表的内容修改时保留痕迹)  */
	private java.lang.String modifyoper;
	/** */
	private java.lang.String certno;
	/** 角色集，用于后面查询使用 */          
	private java.lang.String operdegrees;
	
	/** setter\getter方法 */
	/** 机构代号   */
	public void setOrgancode(java.lang.String organcode) {
		this.organcode = organcode == null ? null : organcode.trim();
	}
	public java.lang.String getOrgancode() {
		return this.organcode;
	}
	/** 操作员代码                                                                                                  */
	public void setOpercode(java.lang.String opercode) {
		this.opercode = opercode == null ? null : opercode.trim();
	}
	public java.lang.String getOpercode() {
		return this.opercode;
	}
	/** 操作员姓名                                                                                                  */
	public void setOpername(java.lang.String opername) {
		this.opername = opername == null ? null : opername.trim();
	}
	public java.lang.String getOpername() {
		return this.opername;
	}
	/** 操作员密码                                                                                                  */
	public void setOperpassword(java.lang.String operpassword) {
		this.operpassword = operpassword == null ? null : operpassword.trim();
	}
	public java.lang.String getOperpassword() {
		return this.operpassword;
	}
	/** 操作员状态    0-签到 1-签退 2-注销                                                                          */
	public void setOperstate(java.lang.String operstate) {
		this.operstate = operstate == null ? null : operstate.trim();
	}
	public java.lang.String getOperstate() {
		return this.operstate;
	}
	/** 操作员身份（级别）1-柜员,2-综合柜员,3-所主任（支局长）,4-二级出纳,5-出纳,6-会计员,7-凭证管理员,8-业务主管   */
	public void setOperdegree(java.lang.String operdegree) {
		this.operdegree = operdegree == null ? null : operdegree.trim();
	}
	public java.lang.String getOperdegree() {
		return this.operdegree;
	}
	/** 注册终端号                                                                                                  */
	public void setTtyno(java.lang.String ttyno) {
		this.ttyno = ttyno == null ? null : ttyno.trim();
	}
	public java.lang.String getTtyno() {
		return this.ttyno;
	}
	/** 虚拟操作员标志  0-代理网点 1-电话银行 2-ATM  3-POS  4-网上银行                                              */
	public void setVirtualflag(java.lang.String virtualflag) {
		this.virtualflag = virtualflag == null ? null : virtualflag.trim();
	}
	public java.lang.String getVirtualflag() {
		return this.virtualflag;
	}
	/** 凭证套号(会计分录流水的一个流水号)                                                                          */
	public void setVoucherserial(java.lang.Long voucherserial) {
		this.voucherserial = voucherserial;
	}
	public java.lang.Long getVoucherserial() {
		return this.voucherserial;
	}
	/** 当前流水号                                                                                                  */
	public void setAcctserial(java.lang.Long acctserial) {
		this.acctserial = acctserial;
	}
	public java.lang.Long getAcctserial() {
		return this.acctserial;
	}
	/** 日志流水号                                                                                                  */
	public void setLogserial(java.lang.Long logserial) {
		this.logserial = logserial;
	}
	public java.lang.Long getLogserial() {
		return this.logserial;
	}
	/** 修改时间(对该表的内容修改时保留痕迹)                                                                        */
	public void setModifydate(java.lang.String modifydate) {
		this.modifydate = modifydate == null ? null : modifydate.trim();
	}
	public java.lang.String getModifydate() {
		return this.modifydate;
	}
	/** 修改机构(对该表的内容修改时保留痕迹)                                                                        */
	public void setModifyorgan(java.lang.String modifyorgan) {
		this.modifyorgan = modifyorgan == null ? null : modifyorgan.trim();
	}
	public java.lang.String getModifyorgan() {
		return this.modifyorgan;
	}
	/** 修改柜员(对该表的内容修改时保留痕迹)                                                                        */
	public void setModifyoper(java.lang.String modifyoper) {
		this.modifyoper = modifyoper == null ? null : modifyoper.trim();
	}
	public java.lang.String getModifyoper() {
		return this.modifyoper;
	}
	/**                                                                                                              */
	public void setCertno(java.lang.String certno) {
		this.certno = certno == null ? null : certno.trim();
	}
	public java.lang.String getCertno() {
		return this.certno;
	}
	/** 角色集，用于后面查询使用 */   
	public java.lang.String getOperdegrees() {
		return operdegrees;
	}
	/** 角色集，用于后面查询使用 */   
	public void setOperdegrees(java.lang.String operdegrees) {
		this.operdegrees = operdegrees;
	}
	
}