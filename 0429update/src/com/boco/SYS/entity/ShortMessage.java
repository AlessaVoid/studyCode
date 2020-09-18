package com.boco.SYS.entity;

import com.boco.SYS.util.BocoUtils;
import com.boco.TONY.utils.IDGeneratorUtils;

import java.io.Serializable;

/**
 * 短信平台短信实体
 */
public class ShortMessage implements Serializable {
    /*系统追踪号 非必输*/
    private String SYS_TRACE_NO;
    /*请求方系统代码 非必输*/
    private String SENDINST;
    /*提供方系统代码 非必输*/
    private String destInst;
    /*消费方系统代码 非必输*/
    private String transInst;
    /*流水号 非必输*/
    private String cltSeqNo;
    /*GMS协议*/
    private String bTpUdhi;
    /*GMS协议*/
    private String bTpPid;
    /*当前消息是第几条*/
    private String bPKNumber;
    /*短消息内容类型*/
    private String bContentType;
    /*是否是长短信*/
    private String bLongSMS;
    /*消息总条数*/
    private String bPKTotal;
    /*短信种类*/
    private String strBusinessKind;
    /** 发送时间
     * 格式：YYYY-MM-DD HH:MI:SS
     * 如果格式不正确或者为空字符串，那么表示立刻发送
     */
    private String strSendTime;
    /*媒体类型*/
    private String bMediaType;
    /*短信内容*/
    private String strContent;
    /*计费手机号 和目的手机号保持一致*/
    private String strDesMsisdn;
    /*目的手机号*/
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
