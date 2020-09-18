package com.boco.TONY.biz.loanplan.POJO.DO;

import com.boco.SYS.entity.TbPlan;

/**
 * @author tony
 * @describe TbHistoryPlanDO
 * @date 2019-10-11
 */
public class TbHistoryPlanDO extends TbPlan {
    private static final long serialVersionUID = -2039071488025967253L;
    private int version;
    private int id;
    private String historyMaker;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHistoryMaker() {
        return historyMaker;
    }

    public void setHistoryMaker(String historyMaker) {
        this.historyMaker = historyMaker;
    }

    @Override
    public String toString() {
        return "TbHistoryPlanDO{" +
                "version=" + version +
                ", id=" + id +
                ", historyMaker='" + historyMaker + '\'' +
                '}';
    }
}
