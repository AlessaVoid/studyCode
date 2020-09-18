package com.boco.TONY.biz.loancomb.POJO.DO.combbase;

import java.io.Serializable;

/**
 * �������״̬����DO
 *
 * @author tony
 * @describe LoanCombStatusDO
 * @date 2019-09-19
 */
public class LoanCombStatusDO implements Serializable {
    private static final long serialVersionUID = -47841232271772797L;
    /***������ϱ�ʶ��*/
    private String combCode;

    /***�������״̬ -1-��ɾ�� 1-��ʾ�������� 2-������ռ�� 3-��ʾ���߿��� 4-��ʾ���߱�ռ��*/
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
