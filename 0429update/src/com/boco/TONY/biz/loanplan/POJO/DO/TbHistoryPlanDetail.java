package com.boco.TONY.biz.loanplan.POJO.DO;

import com.boco.SYS.entity.TbPlanDetail;

/**
 * �Ŵ��ƻ�������ʷ
 *
 * @author tony
 * @describe TbHistoryPlanDetailDO
 * @date 2019-10-10
 */
public class TbHistoryPlanDetail extends TbPlanDetail {
    private static final long serialVersionUID = 5539119504972506305L;
    /**
     * �Ŵ��ƻ�������ʷΨһ��ʶ
     */
    private long id;
    /**
     * �Ŵ��ƻ�������ʷ�汾��
     */
    private int version;
    /**
     * �Ŵ��ƻ�������ʷ������
     */
    private String historyMaker;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHistoryMaker() {
        return historyMaker;
    }

    public void setHistoryMaker(String historyMaker) {
        this.historyMaker = historyMaker;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
