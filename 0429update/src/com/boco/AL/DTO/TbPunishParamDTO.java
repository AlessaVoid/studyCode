package com.boco.AL.DTO;

import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

/**
 * ��Ϣ������ʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20160315 ������      �����½�
 * </pre>
 */
public class TbPunishParamDTO extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** ���� */
    /**
     * id
     */
    @EntityParaAnno(zhName = "id")
    private Integer ppId;
    /**
     * ��Ϣ����
     */
    @EntityParaAnno(zhName = "��Ϣ����")
    private String ppName;
    /**
     * ������
     */
    @EntityParaAnno(zhName = "������")
    private String ppOrgan;
    /**
     * ��Ϣ���ͣ�1 ��ĩ����,2��ĩ��ĩ,4�Ǽ�ĩ����,8�Ǽ�ĩ��ĩ
     */
    @EntityParaAnno(zhName = "��Ϣ���ͣ�1 ��ĩ����,2��ĩ��ĩ,4�Ǽ�ĩ����,8�Ǽ�ĩ��ĩ")
    private Integer type;
    /**
     * ������Сֵ
     */
    @EntityParaAnno(zhName = "������Сֵ")
    private Long minnum;
    /**
     * �������ֵ
     */
    @EntityParaAnno(zhName = "�������ֵ")
    private Long maxnum;
    /**
     * �����Ӧ��Ϣ
     */
    @EntityParaAnno(zhName = "�����Ӧ��Ϣ")
    private Long interest;
    /**
     * ״̬
     */
    @EntityParaAnno(zhName = "״̬")
    private Integer state;
    /**
     * ����ʱ��
     */
    @EntityParaAnno(zhName = "����ʱ��")
    private String createtime;
    /**
     * ����ʱ��
     */
    @EntityParaAnno(zhName = "����ʱ��")
    private String updatetime;
    /**
     * �����û�id
     */
    @EntityParaAnno(zhName = "�����û�id")
    private String createuserid;
    /**
     * �����û�id
     */
    @EntityParaAnno(zhName = "�����û�id")
    private String updateuserid;
    /**
     * ��ȡ����
     */
    @EntityParaAnno(zhName = "��ȡ����")
    private java.lang.Integer collecttype;
    /**
     * ��Ϣ����
     */
    @EntityParaAnno(zhName = "��Ϣ����")
    private java.lang.Integer ppType;

    /** setter\getter���� */
    /**
     * id
     */
    public void setPpId(Integer ppId) {
        this.ppId = ppId;
    }

    public Integer getPpId() {
        return this.ppId;
    }

    /**
     * ��Ϣ����
     */
    public void setPpName(String ppName) {
        this.ppName = ppName == null ? null : ppName.trim();
    }

    public String getPpName() {
        return this.ppName;
    }

    /**
     * ������
     */
    public void setPpOrgan(String ppOrgan) {
        this.ppOrgan = ppOrgan == null ? null : ppOrgan.trim();
    }

    public String getPpOrgan() {
        return this.ppOrgan;
    }

    /**
     * ��Ϣ���ͣ�1 ��ĩ����,2��ĩ��ĩ,4�Ǽ�ĩ����,8�Ǽ�ĩ��ĩ
     */
    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return this.type;
    }

    /**
     * ������Сֵ
     */
    public void setMinnum(Long minnum) {
        this.minnum = minnum;
    }

    public Long getMinnum() {
        return this.minnum;
    }

    /**
     * �������ֵ
     */
    public void setMaxnum(Long maxnum) {
        this.maxnum = maxnum;
    }

    public Long getMaxnum() {
        return this.maxnum;
    }

    /**
     * �����Ӧ��Ϣ
     */
    public void setInterest(Long interest) {
        this.interest = interest;
    }

    public Long getInterest() {
        return this.interest;
    }

    /**
     * ״̬
     */
    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return this.state;
    }

    /**
     * ����ʱ��
     */
    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getCreatetime() {
        return this.createtime;
    }

    /**
     * ����ʱ��
     */
    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime == null ? null : updatetime.trim();
    }

    public String getUpdatetime() {
        return this.updatetime;
    }

    /**
     * �����û�id
     */
    public void setCreateuserid(String createuserid) {
        this.createuserid = createuserid == null ? null : createuserid.trim();
    }

    public String getCreateuserid() {
        return this.createuserid;
    }

    /**
     * �����û�id
     */
    public void setUpdateuserid(String updateuserid) {
        this.updateuserid = updateuserid == null ? null : updateuserid.trim();
    }

    public String getUpdateuserid() {
        return this.updateuserid;
    }

    /**
     * ��ȡ����
     */
    public void setCollecttype(java.lang.Integer collecttype) {
        this.collecttype = collecttype;
    }

    public java.lang.Integer getCollecttype() {
        return this.collecttype;
    }

    /**
     * ��Ϣ����
     */
    public void setPpType(java.lang.Integer ppType) {
        this.ppType = ppType;
    }

    public java.lang.Integer getPpType() {
        return this.ppType;
    }
}