package com.boco.TONY.biz.line.POJO.DO;

/**
 * 机构条线持久层DO
 *
 * @author tony
 * @describe ProductLineCombDO
 * @date 2019-10-29
 */
public class ProductLineCombDO {
    //机构号
    private String organCode;
    //贷种组合级别
    private int combLevel;

    public String getOrganCode() {
        return organCode;
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode;
    }

    public int getCombLevel() {
        return combLevel;
    }

    public void setCombLevel(int combLevel) {
        this.combLevel = combLevel;
    }

    @Override
    public String toString() {
        return "ProductLineCombDO{" +
                "organCode='" + organCode + '\'' +
                ", combLevel=" + combLevel +
                '}';
    }
}
