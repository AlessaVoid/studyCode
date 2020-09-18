package com.boco.TONY.biz.loancomb.POJO.DTO.combbase;

import java.io.Serializable;

/**
 * 贷种组合DTO
 *
 * @author tony
 * @describe LoanCombDTOV2
 * @date 2019-09-22
 */
public class LoanCombDTOV2 implements Serializable {
    private static final long serialVersionUID = -6827539778783669905L;
    /***贷种唯一标识*/
    private String combId;

    /***贷种组合编码*/
    private String combCode;

    /***贷种组合名称*/
    private String combName;

    /***贷种组合级别 1-一级组合 2-二级组合 3-三级组合*/
    private int combLevel;

    /***贷种组合状态 0-不可组合 1-可组合 2表示已被组合*/
    private int combStatus;
    /***贷种组合状态 0-不可组合 1-可组合 2表示已被组合*/
    private String combChildren;
    /***贷种创建者*/
    private String combCreator;

    /***贷种组合创建时间*/
    private String combCreateTime;

    /***贷种更新者*/
    private String combUpdater;

    /***贷种组合更新时间*/
    private String combUpdateTime;

    public String getCombId() {
        return combId;
    }

    public LoanCombDTOV2 setCombId(String combId) {
        this.combId = combId;
        return this;
    }

    public String getCombCode() {
        return combCode;
    }

    public LoanCombDTOV2 setCombCode(String combCode) {
        this.combCode = combCode;
        return this;
    }

    public String getCombName() {
        return combName;
    }

    public LoanCombDTOV2 setCombName(String combName) {
        this.combName = combName;
        return this;
    }

    public int getCombLevel() {
        return combLevel;
    }

    public LoanCombDTOV2 setCombLevel(int combLevel) {
        this.combLevel = combLevel;
        return this;
    }

    public int getCombStatus() {
        return combStatus;
    }

    public LoanCombDTOV2 setCombStatus(int combStatus) {
        this.combStatus = combStatus;
        return this;
    }

    public String getCombCreator() {
        return combCreator;
    }

    public LoanCombDTOV2 setCombCreator(String combCreator) {
        this.combCreator = combCreator;
        return this;
    }

    public String getCombCreateTime() {
        return combCreateTime;
    }

    public LoanCombDTOV2 setCombCreateTime(String combCreateTime) {
        this.combCreateTime = combCreateTime;
        return this;
    }

    public String getCombUpdateTime() {
        return combUpdateTime;
    }

    public LoanCombDTOV2 setCombUpdateTime(String combUpdateTime) {
        this.combUpdateTime = combUpdateTime;
        return this;
    }

    public String getCombUpdater() {
        return combUpdater;
    }

    public LoanCombDTOV2 setCombUpdater(String combUpdater) {
        this.combUpdater = combUpdater;
        return this;
    }

    public String getCombChildren() {
        return combChildren;
    }

    public void setCombChildren(String combChildren) {
        this.combChildren = combChildren;
    }

    @Override
    public String toString() {
        return "LoanCombDTOV2{" +
                "combId='" + combId + '\'' +
                ", combCode='" + combCode + '\'' +
                ", combName='" + combName + '\'' +
                ", combLevel=" + combLevel +
                ", combStatus=" + combStatus +
                ", combCreator='" + combCreator + '\'' +
                ", combCreateTime='" + combCreateTime + '\'' +
                ", combUpdater='" + combUpdater + '\'' +
                ", combUpdateTime='" + combUpdateTime + '\'' +
                '}';
    }
}
