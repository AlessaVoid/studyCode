package com.boco.PUB.POJO.DO;

import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * �Ŵ�����¼����ϸ�� ҳ�����ݴ������
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 txn      �����½�
 * </pre>
 */
public class TbReqDetailDO extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5439696657692723291L;


    /** ���� */
    /**
     * ����id
     */
    @EntityParaAnno(zhName = "����id")
    private Integer reqdId;
    /**
     * ��������id
     */
    @EntityParaAnno(zhName = "��������id")
    private Integer reqdRefId;
    /**
     * �����id
     */
    @EntityParaAnno(zhName = "�����id")
    private String reqdOrgan;
    /**
     * �ϱ�����
     */
    @EntityParaAnno(zhName = "�ϱ�����")
    private Integer reqdProdCode;
    /**
     * �ϱ����ּ���
     */
    @EntityParaAnno(zhName = "�ϱ����ּ���")
    private Integer reqdProdLevel;
    /**
     * ������
     */
    @EntityParaAnno(zhName = "������")
    private BigDecimal reqdReqAmount;
    /**
     * ��λ
     */
    @EntityParaAnno(zhName = "��λ")
    private Integer reqdUnit;
    /**
     * �ϱ�����
     */
    @EntityParaAnno(zhName = "�ϱ�����")
    private String reqdAmt;
    /**
     * �ϱ���
     */
    @EntityParaAnno(zhName = "�ϱ���")
    private Integer reqdOper;
    /**
     * ��Ŀ״̬
     */
    @EntityParaAnno(zhName = "��Ŀ״̬")
    private Integer reqdState;
    /**
     * ����ʱ��
     */
    @EntityParaAnno(zhName = "����ʱ��")
    private String reqdCreatetime;
    /**
     * ����ʱ��
     */
    @EntityParaAnno(zhName = "����ʱ��")
    private String reqdUpdatetime;

    /** setter\getter���� */
    /**
     * ����id
     */
    public void setReqdId(Integer reqdId) {
        this.reqdId = reqdId;
    }

    public Integer getReqdId() {
        return this.reqdId;
    }

    /**
     * ��������id
     */
    public void setReqdRefId(Integer reqdRefId) {
        this.reqdRefId = reqdRefId;
    }

    public Integer getReqdRefId() {
        return this.reqdRefId;
    }

    /**
     * �����id
     */
    public void setReqdOrgan(String reqdOrgan) {
        this.reqdOrgan = reqdOrgan;
    }

    public String getReqdOrgan() {
        return this.reqdOrgan;
    }

    /**
     * �ϱ�����
     */
    public void setReqdProdCode(Integer reqdProdCode) {
        this.reqdProdCode = reqdProdCode;
    }

    public Integer getReqdProdCode() {
        return this.reqdProdCode;
    }

    /**
     * �ϱ����ּ���
     */
    public void setReqdProdLevel(Integer reqdProdLevel) {
        this.reqdProdLevel = reqdProdLevel;
    }

    public Integer getReqdProdLevel() {
        return this.reqdProdLevel;
    }

    /**
     * ������
     */
    public void setReqdReqAmount(BigDecimal reqdReqAmount) {
        this.reqdReqAmount = reqdReqAmount;
    }

    public BigDecimal getReqdReqAmount() {
        return this.reqdReqAmount;
    }

    /**
     * ��λ
     */
    public void setReqdUnit(Integer reqdUnit) {
        this.reqdUnit = reqdUnit;
    }

    public Integer getReqdUnit() {
        return this.reqdUnit;
    }

    /**
     * �ϱ�����
     */
    public void setReqdAmt(String reqdAmt) {
        this.reqdAmt = reqdAmt == null ? null : reqdAmt.trim();
    }

    public String getReqdAmt() {
        return this.reqdAmt;
    }

    /**
     * �ϱ���
     */
    public void setReqdOper(Integer reqdOper) {
        this.reqdOper = reqdOper;
    }

    public Integer getReqdOper() {
        return this.reqdOper;
    }

    /**
     * ��Ŀ״̬
     */
    public void setReqdState(Integer reqdState) {
        this.reqdState = reqdState;
    }

    public Integer getReqdState() {
        return this.reqdState;
    }

    /**
     * ����ʱ��
     */
    public void setReqdCreatetime(String reqdCreatetime) {
        this.reqdCreatetime = reqdCreatetime == null ? null : reqdCreatetime.trim();
    }

    public String getReqdCreatetime() {
        return this.reqdCreatetime;
    }

    /**
     * ����ʱ��
     */
    public void setReqdUpdatetime(String reqdUpdatetime) {
        this.reqdUpdatetime = reqdUpdatetime == null ? null : reqdUpdatetime.trim();
    }

    public String getReqdUpdatetime() {
        return this.reqdUpdatetime;
    }
}