package com.boco.TONY.biz.loancomb.POJO.DO.combbase;

/**
 * �������ռ��DO
 *
 * @author tony
 * @describe LoanCombTakenTypeDO
 * @date 2019-10-28
 */
public class LoanCombTakenTypeDO {
    private String combCode;
    private int combTakenType;

    public String getCombCode() {
        return combCode;
    }

    public LoanCombTakenTypeDO setCombCode(String combCode) {
        this.combCode = combCode;
        return this;
    }

    public int getCombTakenType() {
        return combTakenType;
    }

    public LoanCombTakenTypeDO setCombTakenType(int combTakenType) {
        this.combTakenType = combTakenType;
        return this;
    }
}
