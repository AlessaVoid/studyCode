package com.boco.TONY.biz.loancomb.POJO.DO.combtaken;

import java.io.Serializable;

/**
 * �������ռ��DTO
 *
 * @author tony
 * @describe CombTakenBaseDO
 * @date 2019-11-04
 */
public class CombTakenBaseDO implements Serializable {
    private static final long serialVersionUID = -997553482968941950L;
    private String parentComb;
    //1-����ռ�� 0-��ռ�� 2-����ռ��
    private int takenType;

    public String getParentComb() {
        return parentComb;
    }

    public CombTakenBaseDO setParentComb(String parentComb) {
        this.parentComb = parentComb;
        return this;
    }

    public int getTakenType() {
        return takenType;
    }

    public CombTakenBaseDO setTakenType(int takenType) {
        this.takenType = takenType;
        return this;
    }

    @Override
    public String toString() {
        return "CombTakenBaseDO{" +
                "parentComb='" + parentComb + '\'' +
                '}';
    }
}
