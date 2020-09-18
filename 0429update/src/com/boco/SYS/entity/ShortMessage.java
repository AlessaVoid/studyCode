package com.boco.SYS.entity;

import com.boco.SYS.util.BocoUtils;
import com.boco.TONY.utils.IDGeneratorUtils;

import java.io.Serializable;

/**
 * ����ƽ̨����ʵ��
 */
public class ShortMessage implements Serializable {
    /*ϵͳ׷�ٺ� �Ǳ���*/
    private String SYS_TRACE_NO;
    /*����ϵͳ���� �Ǳ���*/
    private String SENDINST;
    /*�ṩ��ϵͳ���� �Ǳ���*/
    private String destInst;
    /*���ѷ�ϵͳ���� �Ǳ���*/
    private String transInst;
    /*��ˮ�� �Ǳ���*/
    private String cltSeqNo;
    /*GMSЭ��*/
    private String bTpUdhi;
    /*GMSЭ��*/
    private String bTpPid;
    /*��ǰ��Ϣ�ǵڼ���*/
    private String bPKNumber;
    /*����Ϣ��������*/
    private String bContentType;
    /*�Ƿ��ǳ�����*/
    private String bLongSMS;
    /*��Ϣ������*/
    private String bPKTotal;
    /*��������*/
    private String strBusinessKind;
    /** ����ʱ��
     * ��ʽ��YYYY-MM-DD HH:MI:SS
     * �����ʽ����ȷ����Ϊ���ַ�������ô��ʾ���̷���
     */
    private String strSendTime;
    /*ý������*/
    private String bMediaType;
    /*��������*/
    private String strContent;
    /*�Ʒ��ֻ��� ��Ŀ���ֻ��ű���һ��*/
    private String strDesMsisdn;
    /*Ŀ���ֻ���*/
    private String strFeeMsisdn;


    public ShortMessage() {
        // this.SYS_TRACE_NO = SYS_TRACE_NO;
        this.SENDINST = "99711080000";
        this.destInst = "99100100000";
        this.transInst = "99711080000";
        // this.cltSeqNo = "";
        this.bTpUdhi = "0";
        this.bTpPid = "0";
        this.bPKNumber = "1";
        this.bContentType = "15";
        this.bLongSMS = "1";
        this.bPKTotal = "1";
        this.strBusinessKind = "250_XEGLXT_001";
        // this.strSendTime = strSendTime;
        this.bMediaType = "1";
    }

    public String getSYS_TRACE_NO() {
        return SYS_TRACE_NO;
    }

    public void setSYS_TRACE_NO(String SYS_TRACE_NO) {
        this.SYS_TRACE_NO = SYS_TRACE_NO;
    }

    public String getSENDINST() {
        return SENDINST;
    }

    public void setSENDINST(String SENDINST) {
        this.SENDINST = SENDINST;
    }

    public String getDestInst() {
        return destInst;
    }

    public void setDestInst(String destInst) {
        this.destInst = destInst;
    }

    public String getTransInst() {
        return transInst;
    }

    public void setTransInst(String transInst) {
        this.transInst = transInst;
    }

    public String getCltSeqNo() {
        return cltSeqNo;
    }

    public void setCltSeqNo(String cltSeqNo) {
        this.cltSeqNo = cltSeqNo;
    }

    public String getbTpUdhi() {
        return bTpUdhi;
    }

    public void setbTpUdhi(String bTpUdhi) {
        this.bTpUdhi = bTpUdhi;
    }

    public String getbTpPid() {
        return bTpPid;
    }

    public void setbTpPid(String bTpPid) {
        this.bTpPid = bTpPid;
    }

    public String getbPKNumber() {
        return bPKNumber;
    }

    public void setbPKNumber(String bPKNumber) {
        this.bPKNumber = bPKNumber;
    }

    public String getbContentType() {
        return bContentType;
    }

    public void setbContentType(String bContentType) {
        this.bContentType = bContentType;
    }

    public String getbLongSMS() {
        return bLongSMS;
    }

    public void setbLongSMS(String bLongSMS) {
        this.bLongSMS = bLongSMS;
    }

    public String getbPKTotal() {
        return bPKTotal;
    }

    public void setbPKTotal(String bPKTotal) {
        this.bPKTotal = bPKTotal;
    }

    public String getStrBusinessKind() {
        return strBusinessKind;
    }

    public void setStrBusinessKind(String strBusinessKind) {
        this.strBusinessKind = strBusinessKind;
    }

    public String getStrSendTime() {
        return strSendTime;
    }

    public void setStrSendTime(String strSendTime) {
        this.strSendTime = strSendTime;
    }

    public String getbMediaType() {
        return bMediaType;
    }

    public void setbMediaType(String bMediaType) {
        this.bMediaType = bMediaType;
    }

    public String getStrContent() {
        return strContent;
    }

    public void setStrContent(String strContent) {
        this.strContent = strContent;
    }

    public String getStrDesMsisdn() {
        return strDesMsisdn;
    }

    public void setStrDesMsisdn(String strDesMsisdn) {
        this.strDesMsisdn = strDesMsisdn;
    }

    public String getStrFeeMsisdn() {
        return strFeeMsisdn;
    }

    public void setStrFeeMsisdn(String strFeeMsisdn) {
        this.strFeeMsisdn = strFeeMsisdn;
    }
}
