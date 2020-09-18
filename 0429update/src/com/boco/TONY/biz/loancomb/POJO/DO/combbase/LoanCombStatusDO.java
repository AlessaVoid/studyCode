package com.boco.TONY.biz.loancomb.POJO.DO.combbase;

import java.io.Serializable;

/**
 * 贷种组合状态更新DO
 *
 * @author tony
 * @describe LoanCombStatusDO
 * @date 2019-09-19
 */
public class LoanCombStatusDO implements Serializable {
    private static final long serialVersionUID = -47841232271772797L;
    /***贷种组合标识码*/
    private String combCode;

    /***贷种组合状态 -1-已删除 1-表示机构可用 2-机构被占用 3-表示条线可用 4-表示条线被占用*/
    private int combStatus;

    public String getCombCode() {
        return combCode;
    }

    public LoanCombStatusDO setCombCode(String combCode) {
        this.combCode = combCode;
        return this;
    }

    public int getCombStatus() {
        return combStatus;
    }

    public LoanCombStatusDO setCombStatus(int combStatus) {
        this.combStatus = combStatus;
        return this;
    }
}
