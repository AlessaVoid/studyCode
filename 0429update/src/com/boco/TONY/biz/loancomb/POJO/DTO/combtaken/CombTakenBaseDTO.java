package com.boco.TONY.biz.loancomb.POJO.DTO.combtaken;

import java.io.Serializable;

/**
 * 贷种组合占用DTO
 *
 * @author tony
 * @describe CombTakenBaseDO
 * @date 2019-11-04
 */
public class CombTakenBaseDTO implements Serializable {
    private static final long serialVersionUID = -997553482968941950L;
    private String parentComb;
    //贷种组合级别
    private int comb_level;
    //1-部分占用 0-不占用 2-随意占用
    private int type;

    public String getParentComb() {
        return parentComb;
    }

    public void setParentComb(String parentComb) {
        this.parentComb = parentComb;
    }

    public int getComb_level() {
        return comb_level;
    }

    public void setComb_level(int comb_level) {
        this.comb_level = comb_level;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CombTakenBaseDO{" +
                "parentComb='" + parentComb + '\'' +
                ", comb_level=" + comb_level +
                ", type=" + type +
                '}';
    }
}
