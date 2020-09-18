package com.boco.TONY.biz.loancomb.POJO.DO.combbase;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 贷种组合DTO
 *
 * @author tony
 * @describe LoanCombDTO
 * @date 2019-09-17
 */
public class LoanCombDTO implements Serializable {
    private static final long serialVersionUID = -6827539778783669905L;
    /***贷种唯一标识*/
    private String combId;

    /***贷种组合编码*/
    private String combCode;

    /***贷种组合名称*/
    private String combName;

    /***贷种组合级别 1-一级组合 2-二级组合 3-三级组合*/
    private int combLevel;

    /***贷种组合状态 0-可组合 1-可组合 2表示已被组合*/
    private int combStatus;

    /***贷种创建者*/
    private String combCreator;

    /***贷种组合创建时间*/
    private LocalDateTime combCreateTime;

    /***贷种更新者*/
    private String combUpdater;

    /***贷种组合更新时间*/
    private LocalDateTime combUpdateTime;

    public String getCombId() {
        return combId;
    }

    public LoanCombDTO setCombId(String combId) {
        this.combId = combId;
        return this;
    }

    public String getCombCode() {
        return combCode;
    }

    public LoanCombDTO setCombCode(String combCode) {
        this.combCode = combCode;
        return this;
    }

    public String getCombName() {
        return combName;
    }

    public LoanCombDTO setCombName(String combName) {
        this.combName = combName;
        return this;
    }

    public int getCombLevel() {
        return combLevel;
    }

    public LoanCombDTO setCombLevel(int combLevel) {
        this.combLevel = combLevel;
        return this;
    }

    public int getCombStatus() {
        return combStatus;
    }

    public LoanCombDTO setCombStatus(int combStatus) {
        this.combStatus = combStatus;
        return this;
    }

    public String getCombCreator() {
        return combCreator;
    }

    public LoanCombDTO setCombCreator(String combCreator) {
        this.combCreator = combCreator;
        return this;
    }

    public LocalDateTime getCombCreateTime() {
        return combCreateTime;
    }

    public LoanCombDTO setCombCreateTime(LocalDateTime combCreateTime) {
        this.combCreateTime = combCreateTime;
        return this;
    }

    public LocalDateTime getCombUpdateTime() {
        return combUpdateTime;
    }

    public LoanCombDTO setCombUpdateTime(LocalDateTime combUpdateTime) {
        this.combUpdateTime = combUpdateTime;
        return this;
    }

    public String getCombUpdater() {
        return combUpdater;
    }

    public LoanCombDTO setCombUpdater(String combUpdater) {
        this.combUpdater = combUpdater;
        return this;
    }

    @Override
    public String toString() {
        return "LoanCombDTO{" +
                "combId='" + combId + '\'' +
                ", combCode='" + combCode + '\'' +
                ", combName='" + combName + '\'' +
                ", combLevel=" + combLevel +
                ", combStatus=" + combStatus +
                ", combCreator='" + combCreator + '\'' +
                ", combCreateTime=" + combCreateTime +
                ", combUpdater='" + combUpdater + '\'' +
                ", combUpdateTime=" + combUpdateTime +
                '}';
    }
}
