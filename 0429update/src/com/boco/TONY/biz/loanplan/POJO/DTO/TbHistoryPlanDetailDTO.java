package com.boco.TONY.biz.loanplan.POJO.DTO;

/**
 * 信贷计划详情历史DTO
 *
 * @author tony
 * @describe TbHistoryPlanDetailDTO
 * @date 2019-10-10
 */
public class TbHistoryPlanDetailDTO extends TbPlanDetailDTO {
    private static final long serialVersionUID = -6458203246670656354L;
    private long id;
    private int version;
    private String historyMaker;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getHistoryMaker() {
        return historyMaker;
    }

    public void setHistoryMaker(String historyMaker) {
        this.historyMaker = historyMaker;
    }


}
