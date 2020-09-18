package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * ��ȷ����ʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20160315 ������      �����½�
 * </pre>
 */
public class TbQuotaAllocate extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** ���� */
    /**
     * ����
     */
    @EntityParaAnno(zhName = "����")
    private String paId;
    /**
     * �����·�
     */
    @EntityParaAnno(zhName = "�����·�")
    private String paMonth;
    /**
     * ����
     */
    @EntityParaAnno(zhName = "����")
    private String paOrgan;
    /**
     * ����
     */
    @EntityParaAnno(zhName = "����")
    private String paProdCode;
    /**
     * Ŀǰ���ö��
     */
    @EntityParaAnno(zhName = "Ŀǰ���ö��")
    private BigDecimal paQuotaAvail;
    /**
     * �����ܼƻ�
     */
    @EntityParaAnno(zhName = "�����ܼƻ�")
    private BigDecimal paPlan;
    /**
     * �������1����2����
     */
    @EntityParaAnno(zhName = "�������1����2����")
    private Integer quotaType;
    /**
     * ��ȼ��� 0�����ƶ��ļƻ����1һ���ƶ��ļƻ����
     */
    @EntityParaAnno(zhName = "��ȼ��� 0�����ƶ��ļƻ����1һ���ƶ��ļƻ����")
    private Integer quotaLevel;

    /** setter\getter���� */
	public String getPaId() {
		return paId;
	}

	public void setPaId(String paId) {
		this.paId = paId;
	}

	public String getPaMonth() {
		return paMonth;
	}

	public void setPaMonth(String paMonth) {
		this.paMonth = paMonth;
	}

	public String getPaOrgan() {
		return paOrgan;
	}

	public void setPaOrgan(String paOrgan) {
		this.paOrgan = paOrgan;
	}

	public String getPaProdCode() {
		return paProdCode;
	}

	public void setPaProdCode(String paProdCode) {
		this.paProdCode = paProdCode;
	}

	public BigDecimal getPaQuotaAvail() {
		return paQuotaAvail;
	}

	public void setPaQuotaAvail(BigDecimal paQuotaAvail) {
		this.paQuotaAvail = paQuotaAvail;
	}

	public BigDecimal getPaPlan() {
		return paPlan;
	}

	public void setPaPlan(BigDecimal paPlan) {
		this.paPlan = paPlan;
	}

	public Integer getQuotaType() {
		return quotaType;
	}

	public void setQuotaType(Integer quotaType) {
		this.quotaType = quotaType;
	}

	public Integer getQuotaLevel() {
		return quotaLevel;
	}

	public void setQuotaLevel(Integer quotaLevel) {
		this.quotaLevel = quotaLevel;
	}
}