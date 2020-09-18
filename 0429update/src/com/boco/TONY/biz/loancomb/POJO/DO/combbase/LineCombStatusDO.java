package com.boco.TONY.biz.loancomb.POJO.DO.combbase;

import java.io.Serializable;

/**
 * 贷种组合状态更新DO
 *
 * @author tony
 * @describe LineCombStatusDO
 * @date 2019-09-19
 */
public class LineCombStatusDO implements Serializable {
    private static final long serialVersionUID = -47841232271772797L;
    /***贷种组合标识码*/
    private String combCode;

    /***贷种组合状态 -1-已删除 0-表示机构可用*/
    private int lineStatus;

    public String getCombCode() {
        return combCode;
    }

    public LineCombStatusDO setCombCode(String combCode) {
        this.combCode = combCode;
        return this;
    }

    public int getLineStatus() {
        return lineStatus;
    }

    public LineCombStatusDO setLineStatus(int lineStatus) {
        this.lineStatus = lineStatus;
        return this;
    }
}
