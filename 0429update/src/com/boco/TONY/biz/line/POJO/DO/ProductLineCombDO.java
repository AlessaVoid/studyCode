package com.boco.TONY.biz.line.POJO.DO;

/**
 * �������߳־ò�DO
 *
 * @author tony
 * @describe ProductLineCombDO
 * @date 2019-10-29
 */
public class ProductLineCombDO {
    //������
    private String organCode;
    //������ϼ���
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
