package com.boco.TONY.biz.loanreq.POJO.DO;

import com.boco.SYS.entity.TbReqList;

/**
 * �Ŵ�����
 * @author tony
 * @describe �Ŵ�������ʷ��
 * @date 2019-09-28
 */
public class TbHistoryReqListDO implements java.io.Serializable {
    private static final long serialVersionUID = -455937311673980017L;

    /**�Ŵ���ʷ�����*/
    private TbReqList tbReqList;

    /**�Ŵ���ʷ��¼�����汾*/
    private int version;

    public TbReqList getTbReqList() {
        return tbReqList;
    }

    public TbHistoryReqListDO setTbReqList(TbReqList tbReqList) {
        this.tbReqList = tbReqList;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public TbHistoryReqListDO setVersion(int version) {
        this.version = version;
        return this;
    }
}