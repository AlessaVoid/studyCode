package com.boco.TONY.biz.loanreq.POJO.DO;

import com.boco.SYS.entity.TbReqDetail;

/**
 * �Ŵ�����������ʷ��¼��
 *
 * <pre>
 * �޸�����            �޸���      �޸�ԭ��
 * 20190915 tony      �����½�
 * </pre>
 */
public class TbHistoryReqDetailDO implements java.io.Serializable {
    private static final long serialVersionUID = 4701103320165098840L;

    private int version;

    private String processInstanceId;

    private String taskId;

    private String businessId;


    /**
     * ������ϸ��
     */
    private TbReqDetail tbReqDetail;

    public TbReqDetail getTbReqDetail() {
        return tbReqDetail;
    }

    public TbHistoryReqDetailDO setTbReqDetail(TbReqDetail tbReqDetail) {
        this.tbReqDetail = tbReqDetail;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public TbHistoryReqDetailDO setVersion(int version) {
        this.version = version;
        return this;
    }
}