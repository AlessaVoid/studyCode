package com.boco.PUB.POJO.DTO;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * 下发信贷需求报送要求表实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbReqListDTO extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** 属性 */

	/** 编号id */
	@EntityParaAnno(zhName="编号id")
	private java.lang.Integer reqId;
	/** 需求所属月份 */
	@EntityParaAnno(zhName="需求所属月份")
	private java.lang.String reqMonth;
	/** 需求发布机构 */
	@EntityParaAnno(zhName="需求发布机构")
	private java.lang.String reqOrgan;
	/** 需求类型 */
	@EntityParaAnno(zhName="需求类型")
	private java.lang.Integer reqType;
	/** 需求状态 */
	@EntityParaAnno(zhName="需求状态")
	private java.lang.Integer reqState;
	/** 需求填报开始时间 */
	@EntityParaAnno(zhName="需求填报开始时间")
	private java.lang.String reqDateStart;
	/** 需求填报结束时间 */
	@EntityParaAnno(zhName="需求填报结束时间")
	private java.lang.String reqDateEnd;
	/** 填报附件id */
	@EntityParaAnno(zhName="填报附件id")
	private java.lang.Integer reqAttachmentId;
	/** 填报说明 */
	@EntityParaAnno(zhName="填报说明")
	private java.lang.String reqNote;
	/** 下发给机构 */
	@EntityParaAnno(zhName="下发给机构")
	private java.lang.Integer reqTo;
	/** 创建人员id */
	@EntityParaAnno(zhName="创建人员id")
	private java.lang.String reqCreateOper;
	/** 更新人员id */
	@EntityParaAnno(zhName="更新人员id")
	private java.lang.String reqUpdateOper;
	/** 创建时间 */
	@EntityParaAnno(zhName="创建时间")
	private java.lang.String reqCreatetime;
	/** 更新时间 */
	@EntityParaAnno(zhName="更新时间")
	private java.lang.String reqUpdatetime;

	/** setter\getter方法 */
	/** 编号id */
	public void setReqId(java.lang.Integer reqId) {
		this.reqId = reqId;
	}
	public java.lang.Integer getReqId() {
		return this.reqId;
	}
	/** 需求所属月份 */
	public void setReqMonth(java.lang.String reqMonth) {
		this.reqMonth = reqMonth == null ? null : reqMonth.trim();
	}
	public java.lang.String getReqMonth() {
		return this.reqMonth;
	}
	/** 需求发布机构 */
	public void setReqOrgan(java.lang.String reqOrgan) {
		this.reqOrgan = reqOrgan == null ? null : reqOrgan.trim();
	}
	public java.lang.String getReqOrgan() {
		return this.reqOrgan;
	}
	/** 需求类型 */
	public void setReqType(java.lang.Integer reqType) {
		this.reqType = reqType;
	}
	public java.lang.Integer getReqType() {
		return this.reqType;
	}
	/** 需求状态 */
	public void setReqState(java.lang.Integer reqState) {
		this.reqState = reqState;
	}
	public java.lang.Integer getReqState() {
		return this.reqState;
	}
	/** 需求填报开始时间 */
	public void setReqDateStart(java.lang.String reqDateStart) {
		this.reqDateStart = reqDateStart == null ? null : reqDateStart.trim();
	}
	public java.lang.String getReqDateStart() {
		return this.reqDateStart;
	}
	/** 需求填报结束时间 */
	public void setReqDateEnd(java.lang.String reqDateEnd) {
		this.reqDateEnd = reqDateEnd == null ? null : reqDateEnd.trim();
	}
	public java.lang.String getReqDateEnd() {
		return this.reqDateEnd;
	}
	/** 填报附件id */
	public void setReqAttachmentId(java.lang.Integer reqAttachmentId) {
		this.reqAttachmentId = reqAttachmentId;
	}
	public java.lang.Integer getReqAttachmentId() {
		return this.reqAttachmentId;
	}
	/** 填报说明 */
	public void setReqNote(java.lang.String reqNote) {
		this.reqNote = reqNote == null ? null : reqNote.trim();
	}
	public java.lang.String getReqNote() {
		return this.reqNote;
	}
	/** 下发给机构 */
	public void setReqTo(java.lang.Integer reqTo) {
		this.reqTo = reqTo;
	}
	public java.lang.Integer getReqTo() {
		return this.reqTo;
	}
	/** 创建人员id */
	public void setReqCreateOper(java.lang.String reqCreateOper) {
		this.reqCreateOper = reqCreateOper == null ? null : reqCreateOper.trim();
	}
	public java.lang.String getReqCreateOper() {
		return this.reqCreateOper;
	}
	/** 更新人员id */
	public void setReqUpdateOper(java.lang.String reqUpdateOper) {
		this.reqUpdateOper = reqUpdateOper == null ? null : reqUpdateOper.trim();
	}
	public java.lang.String getReqUpdateOper() {
		return this.reqUpdateOper;
	}
	/** 创建时间 */
	public void setReqCreatetime(java.lang.String reqCreatetime) {
		this.reqCreatetime = reqCreatetime == null ? null : reqCreatetime.trim();
	}
	public java.lang.String getReqCreatetime() {
		return this.reqCreatetime;
	}
	/** 更新时间 */
	public void setReqUpdatetime(java.lang.String reqUpdatetime) {
		this.reqUpdatetime = reqUpdatetime == null ? null : reqUpdatetime.trim();
	}
	public java.lang.String getReqUpdatetime() {
		return this.reqUpdatetime;
	}
}