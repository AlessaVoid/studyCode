package com.boco.TONY.biz.loancomb.POJO.DO.combbase;

import java.io.Serializable;

/**
 * �������״̬����DO
 *
 * @author tony
 * @describe LineCombStatusDO
 * @date 2019-09-19
 */
public class LineCombStatusDO implements Serializable {
    private static final long serialVersionUID = -47841232271772797L;
    /***������ϱ�ʶ��*/
    private String combCode;

    /***�������״̬ -1-��ɾ�� 0-��ʾ��������*/
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
