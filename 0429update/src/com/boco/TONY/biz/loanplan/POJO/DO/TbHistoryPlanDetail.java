package com.boco.TONY.biz.loanplan.POJO.DO;

import com.boco.SYS.entity.TbPlanDetail;

/**
 * 信贷计划详情历史
 *
 * @author tony
 * @describe TbHistoryPlanDetailDO
 * @date 2019-10-10
 */
public class TbHistoryPlanDetail extends TbPlanDetail {
    private static final long serialVersionUID = 5539119504972506305L;
    /**
     * 信贷计划详情历史唯一标识
     */
    private long id;
    /**
     * 信贷计划详情历史版本号
     */
    private int version;
    /**
     * 信贷计划详情历史创建人
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
