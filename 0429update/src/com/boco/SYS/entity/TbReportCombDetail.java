package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * 
 * 
 * ���������ϸ��ʵ����
 * 
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbReportCombDetail extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	/** ���� */
	/** ������ϸ������ʶ */
		@EntityParaAnno(zhName="������ϸ������ʶ")
	private Long combDetailId;
	/** ���ֱ��� */
		@EntityParaAnno(zhName="���ֱ���")
	private String combCode;
	/** ���ֲ�Ʒ���� */
		@EntityParaAnno(zhName="���ֲ�Ʒ����")
	private String prodCode;
	/** ������ϸ״̬ -1-��ʾ��ǰ�����Ѿ���ɾ�� 1-��ʾ��ǰ������ϲ����ô��ֲ�Ʒ 2-��ʾ��ǰ������Ͽ��ô��ֲ�Ʒ */
		@EntityParaAnno(zhName="������ϸ״̬ -1-��ʾ��ǰ�����Ѿ���ɾ�� 1-��ʾ��ǰ������ϲ����ô��ֲ�Ʒ 2-��ʾ��ǰ������Ͽ��ô��ֲ�Ʒ")
	private Integer status;
	/** createTime */
		@EntityParaAnno(zhName="createTime")
	private LocalDateTime createTime;
	/** ��Ʒ����ϵͳid */
		@EntityParaAnno(zhName="��Ʒ����ϵͳid")
	private String prodSysId;
	
	/** setter\getter���� */
	/** ������ϸ������ʶ */
	public void setCombDetailId(Long combDetailId) {
		this.combDetailId = combDetailId;
	}
	public Long getCombDetailId() {
		return this.combDetailId;
	}
	/** ���ֱ��� */
	public void setCombCode(String combCode) {
		this.combCode = combCode == null ? null : combCode.trim();
	}
	public String getCombCode() {
		return this.combCode;
	}
	/** ���ֲ�Ʒ���� */
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode == null ? null : prodCode.trim();
	}
	public String getProdCode() {
		return this.prodCode;
	}
	/** ������ϸ״̬ -1-��ʾ��ǰ�����Ѿ���ɾ�� 1-��ʾ��ǰ������ϲ����ô��ֲ�Ʒ 2-��ʾ��ǰ������Ͽ��ô��ֲ�Ʒ */
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getStatus() {
		return this.status;
	}
//	/** createTime
//	 * @param createTime*/
//	public void setCreateTime(LocalDateTime createTime) {
//		this.createTime = createTime == null ? null : createTime.trim();
//	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getCreateTime() {
		return this.createTime;
	}
	/** ��Ʒ����ϵͳid */
	public void setProdSysId(String prodSysId) {
		this.prodSysId = prodSysId == null ? null : prodSysId.trim();
	}
	public String getProdSysId() {
		return this.prodSysId;
	}
}