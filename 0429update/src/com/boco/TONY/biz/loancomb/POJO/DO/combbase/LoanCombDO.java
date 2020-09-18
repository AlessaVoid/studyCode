package com.boco.TONY.biz.loancomb.POJO.DO.combbase;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 贷种组合DO
 *
 * @author tony
 * @describe LoanCombDO
 * @date 2019-09-17
 */
public class LoanCombDO implements Serializable {
    private static final long serialVersionUID = -3457316703323580927L;

    /***贷种组合ID*/
    private String combId;

    /***贷种编码*/
    private String combCode;

    /***贷种名称*/
    private String combName;

    /***贷种级别*/
    private int combLevel;

    /***贷种的状态 [0表示不可用]/[1表示可用]/[2表示被占用]*/
    private int combStatus;

    /***贷种创建者*/
    private String combCreator;

    /***贷种记录的创建时间*/
    private LocalDateTime combCreateTime;

    /***贷种更新者*/
    private String combUpdater;

    /***贷种记录的更新时间*/
    private LocalDateTime combUpdateTime;

    public String getCombId() {
        return combId;
    }

    public LoanCombDO setCombId(String combId) {
        this.combId = combId;
        return this;
    }

    public String getCombCode() {
        return combCode;
    }

    public LoanCombDO setCombCode(String combCode) {
        this.combCode = combCode;
        return this;
    }

    public String getCombName() {
        return combName;
    }

    public LoanCombDO setCombName(String combName) {
        this.combName = combName;
        return this;
    }

    public int getCombLevel() {
        return combLevel;
    }

    public LoanCombDO setCombLevel(int combLevel) {
        this.combLevel = combLevel;
        return this;
    }

    public int getCombStatus() {
        return combStatus;
    }

    public LoanCombDO setCombStatus(int combStatus) {
        this.combStatus = combStatus;
        return this;
    }

    public String getCombCreator() {
        return combCreator;
    }

    public LoanCombDO setCombCreator(String combCreator) {
        this.combCreator = combCreator;
        return this;
    }

    public LocalDateTime getCombCreateTime() {
        return combCreateTime;
    }

    public LoanCombDO setCombCreateTime(LocalDateTime combCreateTime) {
        this.combCreateTime = combCreateTime;
        return this;
    }

    public LoanCombDO setCombUpdateTime(LocalDateTime combUpdateTime) {
        this.combUpdateTime = combUpdateTime;
        return this;
    }

    public LocalDateTime getCombUpdateTime() {
        return combUpdateTime;
    }

    public String getCombUpdater() {
        return combUpdater;
    }

    public LoanCombDO setCombUpdater(String combUpdater) {
        this.combUpdater = combUpdater;
        return this;
    }
}
