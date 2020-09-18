package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

/**
 * FdOrganʵ����
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 2014-10-29  ����      �����½�
 * </pre>
 */
public class FdOrgan extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** ���� */
    /**
     * ����������
     */
    private java.lang.String thiscode;
    /**
     * ����ȫ�����Ĵ���
     */
    private java.lang.String countrycode;
    /**
     * ʡ�ִ���
     */
    private java.lang.String provincecode;
    /**
     * ��������
     */
    private java.lang.String areacode;
    /**
     * ��,�ؾִ���
     */
    private java.lang.String citycode;
    /**
     * ֧�ֻ�������
     */
    private java.lang.String branchcode;
    /**
     * ��������   0-ȫ������ 1-ʡ�� 2-����3-��,�ؼ� 4-֧����
     */
    private java.lang.String organlevel;
    /**
     * ���㵥λ��־  1-���� 0-������
     */
    private java.lang.String checkflag;
    /**
     * ����������
     */
    private java.lang.String organname;
    /**
     * ��������ַ
     */
    private java.lang.String organaddr;
    /**
     * ������״̬  0ǩ�� 1ǩ�� 2����
     */
    private java.lang.String organstate;
    /**
     * IP��ַ
     */
    private java.lang.String organipaddr;
    /**
     * �˿ں�
     */
    private java.lang.String organport;
    /**
     * ά����Ա����
     */
    private java.lang.String maintainname;
    /**
     * ά����Ա��ϵ�绰
     */
    private java.lang.String maintainphone;
    /**
     * ������Կ
     */
    private java.lang.String trankey;
    /**
     * MAC��Կ
     */
    private java.lang.String mackey;
    /**
     * ������Կ
     */
    private java.lang.String tutrankey;
    /**
     * ������������
     */
    private java.lang.String cashiercode;
    /**
     * �����������ڵĻ���
     */
    private java.lang.String cashorgcode;
    /**
     * ������˳���(������17λ��9λ������+6λ����+2λ˳���
     */
    private java.lang.String cashtobankserial;
    /**
     * �����ۼ��²�����޶�
     */
    private java.lang.String totalquota;
    /**
     * �м�ҵ�����˺�˳���
     */
    private java.lang.Long customerserial;
    /**
     * �����ۼ��²����
     */
    private java.lang.String totalgrant;
    /**
     * ���յ����²�����޶�
     */
    private java.lang.String singlequota;
    /**
     * ���˳���
     */
    private java.lang.Long deliverserial;
    /**
     * �ɿ���˳���
     */
    private java.lang.String cashtocenterserial;
    /**
     * ƾ֤���뵥˳���
     */
    private java.lang.Long applyserial;
    /**
     * ƾ֤�Ͻɵ�˳���
     */
    private java.lang.Long turninserial;
    /**
     * ƾ֤���ŵ�˳���
     */
    private java.lang.Long grantserial;
    /**
     * ��ʧ������˳���
     */
    private java.lang.Long lostserial;
    /**
     * �������տ�ƾ֤��
     */
    private java.lang.Long recserial;
    /**
     * ����������ƾ֤��
     */
    private java.lang.Long payserial;
    /**
     * ������ת��ƾ֤��
     */
    private java.lang.Long turserial;
    /**
     * ��ͬ��
     */
    private java.lang.Long impawnserial;
    /**
     * �޸�ʱ��
     */
    private java.lang.String modifydate;
    /**
     * �޸Ļ���
     */
    private java.lang.String modifyorgan;
    /**
     * �޸Ĺ�Ա
     */
    private java.lang.String modifyoper;
    /**
     * ���������������
     */
    private java.lang.String liquiorgancode;
    /**
     * �������������
     */
    private java.lang.String liquicode;
    /**
     * �ⲿ֧Ʊ�������
     */
    private java.lang.String outsidechequeserial;
    /**
     * ��������1
     */
    private java.lang.String organflag;
    /**
     * ����ְ��(01-���й���ְ������Ӫҵְ��,02-���й���ְ��,03-����Ӫҵְ��)
     */
    private java.lang.String organfunctions;
    /**
     * ��������
     */
    private java.lang.String areatype;
    /**
     * �ϼ�����
     */
    private java.lang.String uporgan;

    /** ����޸�ʱ���||����޸�ʱ��� */
    @EntityParaAnno(zhName="����޸�ʱ���")
    private java.lang.String lastModStamp;
    /** һ���������� */
    @EntityParaAnno(zhName="һ����������")
    private java.lang.Integer leveloneorder;

    /** setter\getter���� */
    /**
     * ����������
     */
    public void setThiscode(java.lang.String thiscode) {
        this.thiscode = thiscode == null ? null : thiscode.trim();
    }

    public java.lang.String getThiscode() {
        return this.thiscode;
    }

    /**
     * ����ȫ�����Ĵ���
     */
    public void setCountrycode(java.lang.String countrycode) {
        this.countrycode = countrycode == null ? null : countrycode.trim();
    }

    public java.lang.String getCountrycode() {
        return this.countrycode;
    }

    /**
     * ʡ�ִ���
     */
    public void setProvincecode(java.lang.String provincecode) {
        this.provincecode = provincecode == null ? null : provincecode.trim();
    }

    public java.lang.String getProvincecode() {
        return this.provincecode;
    }

    /**
     * ��������
     */
    public void setAreacode(java.lang.String areacode) {
        this.areacode = areacode == null ? null : areacode.trim();
    }

    public java.lang.String getAreacode() {
        return this.areacode;
    }

    /**
     * ��,�ؾִ���
     */
    public void setCitycode(java.lang.String citycode) {
        this.citycode = citycode == null ? null : citycode.trim();
    }

    public java.lang.String getCitycode() {
        return this.citycode;
    }

    /**
     * ֧�ֻ�������
     */
    public void setBranchcode(java.lang.String branchcode) {
        this.branchcode = branchcode == null ? null : branchcode.trim();
    }

    public java.lang.String getBranchcode() {
        return this.branchcode;
    }

    /**
     * ��������   0-ȫ������ 1-ʡ�� 2-����3-��,�ؼ� 4-֧����
     */
    public void setOrganlevel(java.lang.String organlevel) {
        this.organlevel = organlevel == null ? null : organlevel.trim();
    }

    public java.lang.String getOrganlevel() {
        return this.organlevel;
    }

    /**
     * ���㵥λ��־  1-���� 0-������
     */
    public void setCheckflag(java.lang.String checkflag) {
        this.checkflag = checkflag == null ? null : checkflag.trim();
    }

    public java.lang.String getCheckflag() {
        return this.checkflag;
    }

    /**
     * ����������
     */
    public void setOrganname(java.lang.String organname) {
        this.organname = organname == null ? null : organname.trim();
    }

    public java.lang.String getOrganname() {
        return this.organname;
    }

    /**
     * ��������ַ
     */
    public void setOrganaddr(java.lang.String organaddr) {
        this.organaddr = organaddr == null ? null : organaddr.trim();
    }

    public java.lang.String getOrganaddr() {
        return this.organaddr;
    }

    /**
     * ������״̬  0ǩ�� 1ǩ�� 2����
     */
    public void setOrganstate(java.lang.String organstate) {
        this.organstate = organstate == null ? null : organstate.trim();
    }

    public java.lang.String getOrganstate() {
        return this.organstate;
    }

    /**
     * IP��ַ
     */
    public void setOrganipaddr(java.lang.String organipaddr) {
        this.organipaddr = organipaddr == null ? null : organipaddr.trim();
    }

    public java.lang.String getOrganipaddr() {
        return this.organipaddr;
    }

    /**
     * �˿ں�
     */
    public void setOrganport(java.lang.String organport) {
        this.organport = organport == null ? null : organport.trim();
    }

    public java.lang.String getOrganport() {
        return this.organport;
    }

    /**
     * ά����Ա����
     */
    public void setMaintainname(java.lang.String maintainname) {
        this.maintainname = maintainname == null ? null : maintainname.trim();
    }

    public java.lang.String getMaintainname() {
        return this.maintainname;
    }

    /**
     * ά����Ա��ϵ�绰
     */
    public void setMaintainphone(java.lang.String maintainphone) {
        this.maintainphone = maintainphone == null ? null : maintainphone.trim();
    }

    public java.lang.String getMaintainphone() {
        return this.maintainphone;
    }

    /**
     * ������Կ
     */
    public void setTrankey(java.lang.String trankey) {
        this.trankey = trankey == null ? null : trankey.trim();
    }

    public java.lang.String getTrankey() {
        return this.trankey;
    }

    /**
     * MAC��Կ
     */
    public void setMackey(java.lang.String mackey) {
        this.mackey = mackey == null ? null : mackey.trim();
    }

    public java.lang.String getMackey() {
        return this.mackey;
    }

    /**
     * ������Կ
     */
    public void setTutrankey(java.lang.String tutrankey) {
        this.tutrankey = tutrankey == null ? null : tutrankey.trim();
    }

    public java.lang.String getTutrankey() {
        return this.tutrankey;
    }

    /**
     * ������������
     */
    public void setCashiercode(java.lang.String cashiercode) {
        this.cashiercode = cashiercode == null ? null : cashiercode.trim();
    }

    public java.lang.String getCashiercode() {
        return this.cashiercode;
    }

    /**
     * �����������ڵĻ���
     */
    public void setCashorgcode(java.lang.String cashorgcode) {
        this.cashorgcode = cashorgcode == null ? null : cashorgcode.trim();
    }

    public java.lang.String getCashorgcode() {
        return this.cashorgcode;
    }

    /**
     * ������˳���(������17λ��9λ������+6λ����+2λ˳���
     */
    public void setCashtobankserial(java.lang.String cashtobankserial) {
        this.cashtobankserial = cashtobankserial == null ? null : cashtobankserial.trim();
    }

    public java.lang.String getCashtobankserial() {
        return this.cashtobankserial;
    }

    /**
     * �����ۼ��²�����޶�
     */
    public void setTotalquota(java.lang.String totalquota) {
        this.totalquota = totalquota == null ? null : totalquota.trim();
    }

    public java.lang.String getTotalquota() {
        return this.totalquota;
    }

    /**
     * �м�ҵ�����˺�˳���
     */
    public void setCustomerserial(java.lang.Long customerserial) {
        this.customerserial = customerserial;
    }

    public java.lang.Long getCustomerserial() {
        return this.customerserial;
    }

    /**
     * �����ۼ��²����
     */
    public void setTotalgrant(java.lang.String totalgrant) {
        this.totalgrant = totalgrant == null ? null : totalgrant.trim();
    }

    public java.lang.String getTotalgrant() {
        return this.totalgrant;
    }

    /**
     * ���յ����²�����޶�
     */
    public void setSinglequota(java.lang.String singlequota) {
        this.singlequota = singlequota == null ? null : singlequota.trim();
    }

    public java.lang.String getSinglequota() {
        return this.singlequota;
    }

    /**
     * ���˳���
     */
    public void setDeliverserial(java.lang.Long deliverserial) {
        this.deliverserial = deliverserial;
    }

    public java.lang.Long getDeliverserial() {
        return this.deliverserial;
    }

    /**
     * �ɿ���˳���
     */
    public void setCashtocenterserial(java.lang.String cashtocenterserial) {
        this.cashtocenterserial = cashtocenterserial == null ? null : cashtocenterserial.trim();
    }

    public java.lang.String getCashtocenterserial() {
        return this.cashtocenterserial;
    }

    /**
     * ƾ֤���뵥˳���
     */
    public void setApplyserial(java.lang.Long applyserial) {
        this.applyserial = applyserial;
    }

    public java.lang.Long getApplyserial() {
        return this.applyserial;
    }

    /**
     * ƾ֤�Ͻɵ�˳���
     */
    public void setTurninserial(java.lang.Long turninserial) {
        this.turninserial = turninserial;
    }

    public java.lang.Long getTurninserial() {
        return this.turninserial;
    }

    /**
     * ƾ֤���ŵ�˳���
     */
    public void setGrantserial(java.lang.Long grantserial) {
        this.grantserial = grantserial;
    }

    public java.lang.Long getGrantserial() {
        return this.grantserial;
    }

    /**
     * ��ʧ������˳���
     */
    public void setLostserial(java.lang.Long lostserial) {
        this.lostserial = lostserial;
    }

    public java.lang.Long getLostserial() {
        return this.lostserial;
    }

    /**
     * �������տ�ƾ֤��
     */
    public void setRecserial(java.lang.Long recserial) {
        this.recserial = recserial;
    }

    public java.lang.Long getRecserial() {
        return this.recserial;
    }

    /**
     * ����������ƾ֤��
     */
    public void setPayserial(java.lang.Long payserial) {
        this.payserial = payserial;
    }

    public java.lang.Long getPayserial() {
        return this.payserial;
    }

    /**
     * ������ת��ƾ֤��
     */
    public void setTurserial(java.lang.Long turserial) {
        this.turserial = turserial;
    }

    public java.lang.Long getTurserial() {
        return this.turserial;
    }

    /**
     * ��ͬ��
     */
    public void setImpawnserial(java.lang.Long impawnserial) {
        this.impawnserial = impawnserial;
    }

    public java.lang.Long getImpawnserial() {
        return this.impawnserial;
    }

    /**
     * �޸�ʱ��
     */
    public void setModifydate(java.lang.String modifydate) {
        this.modifydate = modifydate == null ? null : modifydate.trim();
    }

    public java.lang.String getModifydate() {
        return this.modifydate;
    }

    /**
     * �޸Ļ���
     */
    public void setModifyorgan(java.lang.String modifyorgan) {
        this.modifyorgan = modifyorgan == null ? null : modifyorgan.trim();
    }

    public java.lang.String getModifyorgan() {
        return this.modifyorgan;
    }

    /**
     * �޸Ĺ�Ա
     */
    public void setModifyoper(java.lang.String modifyoper) {
        this.modifyoper = modifyoper == null ? null : modifyoper.trim();
    }

    public java.lang.String getModifyoper() {
        return this.modifyoper;
    }

    /**
     * ���������������
     */
    public void setLiquiorgancode(java.lang.String liquiorgancode) {
        this.liquiorgancode = liquiorgancode == null ? null : liquiorgancode.trim();
    }

    public java.lang.String getLiquiorgancode() {
        return this.liquiorgancode;
    }

    /**
     * �������������
     */
    public void setLiquicode(java.lang.String liquicode) {
        this.liquicode = liquicode == null ? null : liquicode.trim();
    }

    public java.lang.String getLiquicode() {
        return this.liquicode;
    }

    /**
     * �ⲿ֧Ʊ�������
     */
    public void setOutsidechequeserial(java.lang.String outsidechequeserial) {
        this.outsidechequeserial = outsidechequeserial == null ? null : outsidechequeserial.trim();
    }

    public java.lang.String getOutsidechequeserial() {
        return this.outsidechequeserial;
    }

    /**
     * ��������1
     */
    public void setOrganflag(java.lang.String organflag) {
        this.organflag = organflag == null ? null : organflag.trim();
    }

    public java.lang.String getOrganflag() {
        return this.organflag;
    }

    /**
     * ����ְ��(01-���й���ְ������Ӫҵְ��,02-���й���ְ��,03-����Ӫҵְ��)
     */
    public void setOrganfunctions(java.lang.String organfunctions) {
        this.organfunctions = organfunctions == null ? null : organfunctions.trim();
    }

    public java.lang.String getOrganfunctions() {
        return this.organfunctions;
    }

    /**
     * ��������
     */
    public void setAreatype(java.lang.String areatype) {
        this.areatype = areatype == null ? null : areatype.trim();
    }

    public java.lang.String getAreatype() {
        return this.areatype;
    }

    /**
     * �ϼ�����
     */
    public void setUporgan(java.lang.String uporgan) {
        this.uporgan = uporgan == null ? null : uporgan.trim();
    }

    public java.lang.String getUporgan() {
        return this.uporgan;
    }


    public String getLastModStamp() {
        return lastModStamp;
    }

    public void setLastModStamp(String lastModStamp) {
        this.lastModStamp = lastModStamp;
    }

    public Integer getLeveloneorder() {
        return leveloneorder;
    }

    public void setLeveloneorder(Integer leveloneorder) {
        this.leveloneorder = leveloneorder;
    }
}