package com.boco.RE.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 同业表
 */
public class BankIndustryReport implements Serializable {

    private String id;
    private String parentId;
    private int level;
    private List<BankIndustryReport> children;
    private boolean open;
    private String name;
    private Integer order;

    /*一.各项贷款*/
    //余额
    private BigDecimal gxdkBalance;
    //周期净增
    private BigDecimal gxdkIncrement;
    //净增增速
    private BigDecimal gxdkIncrementGrowthRatio;
    //本行、机构 余额占比
    private BigDecimal gxdkSelfBalanceRaito;
    //本行、机构 余额占比较期初
    private BigDecimal gxdkSelfBalanceRaitoCompareDateBegin;
    //本行、机构 净增占比
    private BigDecimal gxdkSelfIncrementRatio;
    //银行业、总行 余额占比
    private BigDecimal gxdkAllBalanceRaito;
    //银行业、总行 余额占比较期初
    private BigDecimal gxdkAllBalanceRaitoCompareDateBegin;
    //银行业、总行 净增占比
    private BigDecimal gxdkAllIncrementRatio;

    /*实体贷款*/
    private BigDecimal stdkBalance;
    private BigDecimal stdkIncrement;
    private BigDecimal stdkIncrementGrowthRatio;
    private BigDecimal stdkSelfBalanceRaito;
    private BigDecimal stdkSelfBalanceRaitoCompareDateBegin;
    private BigDecimal stdkSelfIncrementRatio;
    private BigDecimal stdkAllBalanceRaito;
    private BigDecimal stdkAllBalanceRaitoCompareDateBegin;
    private BigDecimal stdkAllIncrementRatio;


    /*1.境内个人贷款*/
    private BigDecimal grdkBalance;
    private BigDecimal grdkIncrement;
    private BigDecimal grdkIncrementGrowthRatio;
    private BigDecimal grdkSelfBalanceRaito;
    private BigDecimal grdkSelfBalanceRaitoCompareDateBegin;
    private BigDecimal grdkSelfIncrementRatio;
    private BigDecimal grdkAllBalanceRaito;
    private BigDecimal grdkAllBalanceRaitoCompareDateBegin;
    private BigDecimal grdkAllIncrementRatio;

    /*其中：消费贷款*/
    private BigDecimal xfdkBalance;
    private BigDecimal xfdkIncrement;
    private BigDecimal xfdkIncrementGrowthRatio;
    private BigDecimal xfdkSelfBalanceRaito;
    private BigDecimal xfdkSelfBalanceRaitoCompareDateBegin;
    private BigDecimal xfdkSelfIncrementRatio;
    private BigDecimal xfdkAllBalanceRaito;
    private BigDecimal xfdkAllBalanceRaitoCompareDateBegin;
    private BigDecimal xfdkAllIncrementRatio;



    /*2.境内公司贷款*/
    private BigDecimal gsdkBalance;
    private BigDecimal gsdkIncrement;
    private BigDecimal gsdkIncrementGrowthRatio;
    private BigDecimal gsdkSelfBalanceRaito;
    private BigDecimal gsdkSelfBalanceRaitoCompareDateBegin;
    private BigDecimal gsdkSelfIncrementRatio;
    private BigDecimal gsdkAllBalanceRaito;
    private BigDecimal gsdkAllBalanceRaitoCompareDateBegin;
    private BigDecimal gsdkAllIncrementRatio;

    /*其中：并购贷款*/
    private BigDecimal bgdkBalance;
    private BigDecimal bgdkIncrement;
    private BigDecimal bgdkIncrementGrowthRatio;
    private BigDecimal bgdkSelfBalanceRaito;
    private BigDecimal bgdkSelfBalanceRaitoCompareDateBegin;
    private BigDecimal bgdkSelfIncrementRatio;
    private BigDecimal bgdkAllBalanceRaito;
    private BigDecimal bgdkAllBalanceRaitoCompareDateBegin;
    private BigDecimal bgdkAllIncrementRatio;

    /*3.票据融资*/
    private BigDecimal pjrzBalance;
    private BigDecimal pjrzIncrement;
    private BigDecimal pjrzIncrementGrowthRatio;
    private BigDecimal pjrzSelfBalanceRaito;
    private BigDecimal pjrzSelfBalanceRaitoCompareDateBegin;
    private BigDecimal pjrzSelfIncrementRatio;
    private BigDecimal pjrzAllBalanceRaito;
    private BigDecimal pjrzAllBalanceRaitoCompareDateBegin;
    private BigDecimal pjrzAllIncrementRatio;

    /*4.非存款类金融机构贷款*/
    private BigDecimal fcdkBalance;
    private BigDecimal fcdkIncrement;
    private BigDecimal fcdkIncrementGrowthRatio;
    private BigDecimal fcdkSelfBalanceRaito;
    private BigDecimal fcdkSelfBalanceRaitoCompareDateBegin;
    private BigDecimal fcdkSelfIncrementRatio;
    private BigDecimal fcdkAllBalanceRaito;
    private BigDecimal fcdkAllBalanceRaitoCompareDateBegin;
    private BigDecimal fcdkAllIncrementRatio;

    /*5.境外贷款*/
    private BigDecimal jwdkBalance;
    private BigDecimal jwdkIncrement;
    private BigDecimal jwdkIncrementGrowthRatio;
    private BigDecimal jwdkSelfBalanceRaito;
    private BigDecimal jwdkSelfBalanceRaitoCompareDateBegin;
    private BigDecimal jwdkSelfIncrementRatio;
    private BigDecimal jwdkAllBalanceRaito;
    private BigDecimal jwdkAllBalanceRaitoCompareDateBegin;
    private BigDecimal jwdkAllIncrementRatio;

    /*6.各项垫款*/
    private BigDecimal gxdk2Balance;
    private BigDecimal gxdk2Increment;
    private BigDecimal gxdk2IncrementGrowthRatio;
    private BigDecimal gxdk2SelfBalanceRaito;
    private BigDecimal gxdk2SelfBalanceRaitoCompareDateBegin;
    private BigDecimal gxdk2SelfIncrementRatio;
    private BigDecimal gxdk2AllBalanceRaito;
    private BigDecimal gxdk2AllBalanceRaitoCompareDateBegin;
    private BigDecimal gxdk2AllIncrementRatio;

    public BankIndustryReport() {
        this.open = false;
        this.order = 0;
        this.gxdkBalance = BigDecimal.ZERO;
        this.gxdkIncrement = BigDecimal.ZERO;
        this.gxdkIncrementGrowthRatio = BigDecimal.ZERO;
        this.gxdkSelfBalanceRaito = BigDecimal.ZERO;
        this.gxdkSelfBalanceRaitoCompareDateBegin = BigDecimal.ZERO;
        this.gxdkSelfIncrementRatio = BigDecimal.ZERO;
        this.gxdkAllBalanceRaito = BigDecimal.ZERO;
        this.gxdkAllBalanceRaitoCompareDateBegin = BigDecimal.ZERO;
        this.gxdkAllIncrementRatio = BigDecimal.ZERO;
        this.stdkBalance= BigDecimal.ZERO;
        this.stdkIncrement= BigDecimal.ZERO;
        this.stdkIncrementGrowthRatio= BigDecimal.ZERO;
        this.stdkSelfBalanceRaito= BigDecimal.ZERO;
        this.stdkSelfBalanceRaitoCompareDateBegin= BigDecimal.ZERO;
        this.stdkSelfIncrementRatio= BigDecimal.ZERO;
        this.stdkAllBalanceRaito= BigDecimal.ZERO;
        this.stdkAllBalanceRaitoCompareDateBegin= BigDecimal.ZERO;
        this.stdkAllIncrementRatio= BigDecimal.ZERO;
        this.grdkBalance = BigDecimal.ZERO;
        this.grdkIncrement = BigDecimal.ZERO;
        this.grdkIncrementGrowthRatio = BigDecimal.ZERO;
        this.grdkSelfBalanceRaito = BigDecimal.ZERO;
        this.grdkSelfBalanceRaitoCompareDateBegin = BigDecimal.ZERO;
        this.grdkSelfIncrementRatio = BigDecimal.ZERO;
        this.grdkAllBalanceRaito = BigDecimal.ZERO;
        this.grdkAllBalanceRaitoCompareDateBegin = BigDecimal.ZERO;
        this.grdkAllIncrementRatio = BigDecimal.ZERO;
        this.xfdkBalance = BigDecimal.ZERO;
        this.xfdkIncrement = BigDecimal.ZERO;
        this.xfdkIncrementGrowthRatio = BigDecimal.ZERO;
        this.xfdkSelfBalanceRaito = BigDecimal.ZERO;
        this.xfdkSelfBalanceRaitoCompareDateBegin = BigDecimal.ZERO;
        this.xfdkSelfIncrementRatio = BigDecimal.ZERO;
        this.xfdkAllBalanceRaito = BigDecimal.ZERO;
        this.xfdkAllBalanceRaitoCompareDateBegin = BigDecimal.ZERO;
        this.xfdkAllIncrementRatio = BigDecimal.ZERO;
        this.gsdkBalance = BigDecimal.ZERO;
        this.gsdkIncrement = BigDecimal.ZERO;
        this.gsdkIncrementGrowthRatio = BigDecimal.ZERO;
        this.gsdkSelfBalanceRaito = BigDecimal.ZERO;
        this.gsdkSelfBalanceRaitoCompareDateBegin = BigDecimal.ZERO;
        this.gsdkSelfIncrementRatio = BigDecimal.ZERO;
        this.gsdkAllBalanceRaito = BigDecimal.ZERO;
        this.gsdkAllBalanceRaitoCompareDateBegin = BigDecimal.ZERO;
        this.gsdkAllIncrementRatio = BigDecimal.ZERO;
        this.bgdkBalance = BigDecimal.ZERO;
        this.bgdkIncrement = BigDecimal.ZERO;
        this.bgdkIncrementGrowthRatio = BigDecimal.ZERO;
        this.bgdkSelfBalanceRaito = BigDecimal.ZERO;
        this.bgdkSelfBalanceRaitoCompareDateBegin = BigDecimal.ZERO;
        this.bgdkSelfIncrementRatio = BigDecimal.ZERO;
        this.bgdkAllBalanceRaito = BigDecimal.ZERO;
        this.bgdkAllBalanceRaitoCompareDateBegin = BigDecimal.ZERO;
        this.bgdkAllIncrementRatio = BigDecimal.ZERO;
        this.pjrzBalance = BigDecimal.ZERO;
        this.pjrzIncrement = BigDecimal.ZERO;
        this.pjrzIncrementGrowthRatio = BigDecimal.ZERO;
        this.pjrzSelfBalanceRaito = BigDecimal.ZERO;
        this.pjrzSelfBalanceRaitoCompareDateBegin = BigDecimal.ZERO;
        this.pjrzSelfIncrementRatio = BigDecimal.ZERO;
        this.pjrzAllBalanceRaito = BigDecimal.ZERO;
        this.pjrzAllBalanceRaitoCompareDateBegin = BigDecimal.ZERO;
        this.pjrzAllIncrementRatio = BigDecimal.ZERO;
        this.fcdkBalance = BigDecimal.ZERO;
        this.fcdkIncrement = BigDecimal.ZERO;
        this.fcdkIncrementGrowthRatio = BigDecimal.ZERO;
        this.fcdkSelfBalanceRaito = BigDecimal.ZERO;
        this.fcdkSelfBalanceRaitoCompareDateBegin = BigDecimal.ZERO;
        this.fcdkSelfIncrementRatio = BigDecimal.ZERO;
        this.fcdkAllBalanceRaito = BigDecimal.ZERO;
        this.fcdkAllBalanceRaitoCompareDateBegin = BigDecimal.ZERO;
        this.fcdkAllIncrementRatio = BigDecimal.ZERO;
        this.jwdkBalance = BigDecimal.ZERO;
        this.jwdkIncrement = BigDecimal.ZERO;
        this.jwdkIncrementGrowthRatio = BigDecimal.ZERO;
        this.jwdkSelfBalanceRaito = BigDecimal.ZERO;
        this.jwdkSelfBalanceRaitoCompareDateBegin = BigDecimal.ZERO;
        this.jwdkSelfIncrementRatio = BigDecimal.ZERO;
        this.jwdkAllBalanceRaito = BigDecimal.ZERO;
        this.jwdkAllBalanceRaitoCompareDateBegin = BigDecimal.ZERO;
        this.jwdkAllIncrementRatio = BigDecimal.ZERO;
        this.gxdk2Balance = BigDecimal.ZERO;
        this.gxdk2Increment = BigDecimal.ZERO;
        this.gxdk2IncrementGrowthRatio = BigDecimal.ZERO;
        this.gxdk2SelfBalanceRaito = BigDecimal.ZERO;
        this.gxdk2SelfBalanceRaitoCompareDateBegin = BigDecimal.ZERO;
        this.gxdk2SelfIncrementRatio = BigDecimal.ZERO;
        this.gxdk2AllBalanceRaito = BigDecimal.ZERO;
        this.gxdk2AllBalanceRaitoCompareDateBegin = BigDecimal.ZERO;
        this.gxdk2AllIncrementRatio = BigDecimal.ZERO;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public BigDecimal getStdkBalance() {
        return stdkBalance;
    }

    public void setStdkBalance(BigDecimal stdkBalance) {
        this.stdkBalance = stdkBalance;
    }

    public BigDecimal getStdkIncrement() {
        return stdkIncrement;
    }

    public void setStdkIncrement(BigDecimal stdkIncrement) {
        this.stdkIncrement = stdkIncrement;
    }

    public BigDecimal getStdkIncrementGrowthRatio() {
        return stdkIncrementGrowthRatio;
    }

    public void setStdkIncrementGrowthRatio(BigDecimal stdkIncrementGrowthRatio) {
        this.stdkIncrementGrowthRatio = stdkIncrementGrowthRatio;
    }

    public BigDecimal getStdkSelfBalanceRaito() {
        return stdkSelfBalanceRaito;
    }

    public void setStdkSelfBalanceRaito(BigDecimal stdkSelfBalanceRaito) {
        this.stdkSelfBalanceRaito = stdkSelfBalanceRaito;
    }

    public BigDecimal getStdkSelfBalanceRaitoCompareDateBegin() {
        return stdkSelfBalanceRaitoCompareDateBegin;
    }

    public void setStdkSelfBalanceRaitoCompareDateBegin(BigDecimal stdkSelfBalanceRaitoCompareDateBegin) {
        this.stdkSelfBalanceRaitoCompareDateBegin = stdkSelfBalanceRaitoCompareDateBegin;
    }

    public BigDecimal getStdkSelfIncrementRatio() {
        return stdkSelfIncrementRatio;
    }

    public void setStdkSelfIncrementRatio(BigDecimal stdkSelfIncrementRatio) {
        this.stdkSelfIncrementRatio = stdkSelfIncrementRatio;
    }

    public BigDecimal getStdkAllBalanceRaito() {
        return stdkAllBalanceRaito;
    }

    public void setStdkAllBalanceRaito(BigDecimal stdkAllBalanceRaito) {
        this.stdkAllBalanceRaito = stdkAllBalanceRaito;
    }

    public BigDecimal getStdkAllBalanceRaitoCompareDateBegin() {
        return stdkAllBalanceRaitoCompareDateBegin;
    }

    public void setStdkAllBalanceRaitoCompareDateBegin(BigDecimal stdkAllBalanceRaitoCompareDateBegin) {
        this.stdkAllBalanceRaitoCompareDateBegin = stdkAllBalanceRaitoCompareDateBegin;
    }

    public BigDecimal getStdkAllIncrementRatio() {
        return stdkAllIncrementRatio;
    }

    public void setStdkAllIncrementRatio(BigDecimal stdkAllIncrementRatio) {
        this.stdkAllIncrementRatio = stdkAllIncrementRatio;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<BankIndustryReport> getChildren() {
        return children;
    }

    public void setChildren(List<BankIndustryReport> children) {
        this.children = children;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public BigDecimal getGxdkBalance() {
        return gxdkBalance;
    }

    public void setGxdkBalance(BigDecimal gxdkBalance) {
        this.gxdkBalance = gxdkBalance;
    }

    public BigDecimal getGxdkIncrement() {
        return gxdkIncrement;
    }

    public void setGxdkIncrement(BigDecimal gxdkIncrement) {
        this.gxdkIncrement = gxdkIncrement;
    }

    public BigDecimal getGxdkIncrementGrowthRatio() {
        return gxdkIncrementGrowthRatio;
    }

    public void setGxdkIncrementGrowthRatio(BigDecimal gxdkIncrementGrowthRatio) {
        this.gxdkIncrementGrowthRatio = gxdkIncrementGrowthRatio;
    }

    public BigDecimal getGxdkSelfBalanceRaito() {
        return gxdkSelfBalanceRaito;
    }

    public void setGxdkSelfBalanceRaito(BigDecimal gxdkSelfBalanceRaito) {
        this.gxdkSelfBalanceRaito = gxdkSelfBalanceRaito;
    }

    public BigDecimal getGxdkSelfBalanceRaitoCompareDateBegin() {
        return gxdkSelfBalanceRaitoCompareDateBegin;
    }

    public void setGxdkSelfBalanceRaitoCompareDateBegin(BigDecimal gxdkSelfBalanceRaitoCompareDateBegin) {
        this.gxdkSelfBalanceRaitoCompareDateBegin = gxdkSelfBalanceRaitoCompareDateBegin;
    }

    public BigDecimal getGxdkSelfIncrementRatio() {
        return gxdkSelfIncrementRatio;
    }

    public void setGxdkSelfIncrementRatio(BigDecimal gxdkSelfIncrementRatio) {
        this.gxdkSelfIncrementRatio = gxdkSelfIncrementRatio;
    }

    public BigDecimal getGxdkAllBalanceRaito() {
        return gxdkAllBalanceRaito;
    }

    public void setGxdkAllBalanceRaito(BigDecimal gxdkAllBalanceRaito) {
        this.gxdkAllBalanceRaito = gxdkAllBalanceRaito;
    }

    public BigDecimal getGxdkAllBalanceRaitoCompareDateBegin() {
        return gxdkAllBalanceRaitoCompareDateBegin;
    }

    public void setGxdkAllBalanceRaitoCompareDateBegin(BigDecimal gxdkAllBalanceRaitoCompareDateBegin) {
        this.gxdkAllBalanceRaitoCompareDateBegin = gxdkAllBalanceRaitoCompareDateBegin;
    }

    public BigDecimal getGxdkAllIncrementRatio() {
        return gxdkAllIncrementRatio;
    }

    public void setGxdkAllIncrementRatio(BigDecimal gxdkAllIncrementRatio) {
        this.gxdkAllIncrementRatio = gxdkAllIncrementRatio;
    }

    public BigDecimal getGrdkBalance() {
        return grdkBalance;
    }

    public void setGrdkBalance(BigDecimal grdkBalance) {
        this.grdkBalance = grdkBalance;
    }

    public BigDecimal getGrdkIncrement() {
        return grdkIncrement;
    }

    public void setGrdkIncrement(BigDecimal grdkIncrement) {
        this.grdkIncrement = grdkIncrement;
    }

    public BigDecimal getGrdkIncrementGrowthRatio() {
        return grdkIncrementGrowthRatio;
    }

    public void setGrdkIncrementGrowthRatio(BigDecimal grdkIncrementGrowthRatio) {
        this.grdkIncrementGrowthRatio = grdkIncrementGrowthRatio;
    }

    public BigDecimal getGrdkSelfBalanceRaito() {
        return grdkSelfBalanceRaito;
    }

    public void setGrdkSelfBalanceRaito(BigDecimal grdkSelfBalanceRaito) {
        this.grdkSelfBalanceRaito = grdkSelfBalanceRaito;
    }

    public BigDecimal getGrdkSelfBalanceRaitoCompareDateBegin() {
        return grdkSelfBalanceRaitoCompareDateBegin;
    }

    public void setGrdkSelfBalanceRaitoCompareDateBegin(BigDecimal grdkSelfBalanceRaitoCompareDateBegin) {
        this.grdkSelfBalanceRaitoCompareDateBegin = grdkSelfBalanceRaitoCompareDateBegin;
    }

    public BigDecimal getGrdkSelfIncrementRatio() {
        return grdkSelfIncrementRatio;
    }

    public void setGrdkSelfIncrementRatio(BigDecimal grdkSelfIncrementRatio) {
        this.grdkSelfIncrementRatio = grdkSelfIncrementRatio;
    }

    public BigDecimal getGrdkAllBalanceRaito() {
        return grdkAllBalanceRaito;
    }

    public void setGrdkAllBalanceRaito(BigDecimal grdkAllBalanceRaito) {
        this.grdkAllBalanceRaito = grdkAllBalanceRaito;
    }

    public BigDecimal getGrdkAllBalanceRaitoCompareDateBegin() {
        return grdkAllBalanceRaitoCompareDateBegin;
    }

    public void setGrdkAllBalanceRaitoCompareDateBegin(BigDecimal grdkAllBalanceRaitoCompareDateBegin) {
        this.grdkAllBalanceRaitoCompareDateBegin = grdkAllBalanceRaitoCompareDateBegin;
    }

    public BigDecimal getGrdkAllIncrementRatio() {
        return grdkAllIncrementRatio;
    }

    public void setGrdkAllIncrementRatio(BigDecimal grdkAllIncrementRatio) {
        this.grdkAllIncrementRatio = grdkAllIncrementRatio;
    }

    public BigDecimal getXfdkBalance() {
        return xfdkBalance;
    }

    public void setXfdkBalance(BigDecimal xfdkBalance) {
        this.xfdkBalance = xfdkBalance;
    }

    public BigDecimal getXfdkIncrement() {
        return xfdkIncrement;
    }

    public void setXfdkIncrement(BigDecimal xfdkIncrement) {
        this.xfdkIncrement = xfdkIncrement;
    }

    public BigDecimal getXfdkIncrementGrowthRatio() {
        return xfdkIncrementGrowthRatio;
    }

    public void setXfdkIncrementGrowthRatio(BigDecimal xfdkIncrementGrowthRatio) {
        this.xfdkIncrementGrowthRatio = xfdkIncrementGrowthRatio;
    }

    public BigDecimal getXfdkSelfBalanceRaito() {
        return xfdkSelfBalanceRaito;
    }

    public void setXfdkSelfBalanceRaito(BigDecimal xfdkSelfBalanceRaito) {
        this.xfdkSelfBalanceRaito = xfdkSelfBalanceRaito;
    }

    public BigDecimal getXfdkSelfBalanceRaitoCompareDateBegin() {
        return xfdkSelfBalanceRaitoCompareDateBegin;
    }

    public void setXfdkSelfBalanceRaitoCompareDateBegin(BigDecimal xfdkSelfBalanceRaitoCompareDateBegin) {
        this.xfdkSelfBalanceRaitoCompareDateBegin = xfdkSelfBalanceRaitoCompareDateBegin;
    }

    public BigDecimal getXfdkSelfIncrementRatio() {
        return xfdkSelfIncrementRatio;
    }

    public void setXfdkSelfIncrementRatio(BigDecimal xfdkSelfIncrementRatio) {
        this.xfdkSelfIncrementRatio = xfdkSelfIncrementRatio;
    }

    public BigDecimal getXfdkAllBalanceRaito() {
        return xfdkAllBalanceRaito;
    }

    public void setXfdkAllBalanceRaito(BigDecimal xfdkAllBalanceRaito) {
        this.xfdkAllBalanceRaito = xfdkAllBalanceRaito;
    }

    public BigDecimal getXfdkAllBalanceRaitoCompareDateBegin() {
        return xfdkAllBalanceRaitoCompareDateBegin;
    }

    public void setXfdkAllBalanceRaitoCompareDateBegin(BigDecimal xfdkAllBalanceRaitoCompareDateBegin) {
        this.xfdkAllBalanceRaitoCompareDateBegin = xfdkAllBalanceRaitoCompareDateBegin;
    }

    public BigDecimal getXfdkAllIncrementRatio() {
        return xfdkAllIncrementRatio;
    }

    public void setXfdkAllIncrementRatio(BigDecimal xfdkAllIncrementRatio) {
        this.xfdkAllIncrementRatio = xfdkAllIncrementRatio;
    }

    public BigDecimal getGsdkBalance() {
        return gsdkBalance;
    }

    public void setGsdkBalance(BigDecimal gsdkBalance) {
        this.gsdkBalance = gsdkBalance;
    }

    public BigDecimal getGsdkIncrement() {
        return gsdkIncrement;
    }

    public void setGsdkIncrement(BigDecimal gsdkIncrement) {
        this.gsdkIncrement = gsdkIncrement;
    }

    public BigDecimal getGsdkIncrementGrowthRatio() {
        return gsdkIncrementGrowthRatio;
    }

    public void setGsdkIncrementGrowthRatio(BigDecimal gsdkIncrementGrowthRatio) {
        this.gsdkIncrementGrowthRatio = gsdkIncrementGrowthRatio;
    }

    public BigDecimal getGsdkSelfBalanceRaito() {
        return gsdkSelfBalanceRaito;
    }

    public void setGsdkSelfBalanceRaito(BigDecimal gsdkSelfBalanceRaito) {
        this.gsdkSelfBalanceRaito = gsdkSelfBalanceRaito;
    }

    public BigDecimal getGsdkSelfBalanceRaitoCompareDateBegin() {
        return gsdkSelfBalanceRaitoCompareDateBegin;
    }

    public void setGsdkSelfBalanceRaitoCompareDateBegin(BigDecimal gsdkSelfBalanceRaitoCompareDateBegin) {
        this.gsdkSelfBalanceRaitoCompareDateBegin = gsdkSelfBalanceRaitoCompareDateBegin;
    }

    public BigDecimal getGsdkSelfIncrementRatio() {
        return gsdkSelfIncrementRatio;
    }

    public void setGsdkSelfIncrementRatio(BigDecimal gsdkSelfIncrementRatio) {
        this.gsdkSelfIncrementRatio = gsdkSelfIncrementRatio;
    }

    public BigDecimal getGsdkAllBalanceRaito() {
        return gsdkAllBalanceRaito;
    }

    public void setGsdkAllBalanceRaito(BigDecimal gsdkAllBalanceRaito) {
        this.gsdkAllBalanceRaito = gsdkAllBalanceRaito;
    }

    public BigDecimal getGsdkAllBalanceRaitoCompareDateBegin() {
        return gsdkAllBalanceRaitoCompareDateBegin;
    }

    public void setGsdkAllBalanceRaitoCompareDateBegin(BigDecimal gsdkAllBalanceRaitoCompareDateBegin) {
        this.gsdkAllBalanceRaitoCompareDateBegin = gsdkAllBalanceRaitoCompareDateBegin;
    }

    public BigDecimal getGsdkAllIncrementRatio() {
        return gsdkAllIncrementRatio;
    }

    public void setGsdkAllIncrementRatio(BigDecimal gsdkAllIncrementRatio) {
        this.gsdkAllIncrementRatio = gsdkAllIncrementRatio;
    }

    public BigDecimal getBgdkBalance() {
        return bgdkBalance;
    }

    public void setBgdkBalance(BigDecimal bgdkBalance) {
        this.bgdkBalance = bgdkBalance;
    }

    public BigDecimal getBgdkIncrement() {
        return bgdkIncrement;
    }

    public void setBgdkIncrement(BigDecimal bgdkIncrement) {
        this.bgdkIncrement = bgdkIncrement;
    }

    public BigDecimal getBgdkIncrementGrowthRatio() {
        return bgdkIncrementGrowthRatio;
    }

    public void setBgdkIncrementGrowthRatio(BigDecimal bgdkIncrementGrowthRatio) {
        this.bgdkIncrementGrowthRatio = bgdkIncrementGrowthRatio;
    }

    public BigDecimal getBgdkSelfBalanceRaito() {
        return bgdkSelfBalanceRaito;
    }

    public void setBgdkSelfBalanceRaito(BigDecimal bgdkSelfBalanceRaito) {
        this.bgdkSelfBalanceRaito = bgdkSelfBalanceRaito;
    }

    public BigDecimal getBgdkSelfBalanceRaitoCompareDateBegin() {
        return bgdkSelfBalanceRaitoCompareDateBegin;
    }

    public void setBgdkSelfBalanceRaitoCompareDateBegin(BigDecimal bgdkSelfBalanceRaitoCompareDateBegin) {
        this.bgdkSelfBalanceRaitoCompareDateBegin = bgdkSelfBalanceRaitoCompareDateBegin;
    }

    public BigDecimal getBgdkSelfIncrementRatio() {
        return bgdkSelfIncrementRatio;
    }

    public void setBgdkSelfIncrementRatio(BigDecimal bgdkSelfIncrementRatio) {
        this.bgdkSelfIncrementRatio = bgdkSelfIncrementRatio;
    }

    public BigDecimal getBgdkAllBalanceRaito() {
        return bgdkAllBalanceRaito;
    }

    public void setBgdkAllBalanceRaito(BigDecimal bgdkAllBalanceRaito) {
        this.bgdkAllBalanceRaito = bgdkAllBalanceRaito;
    }

    public BigDecimal getBgdkAllBalanceRaitoCompareDateBegin() {
        return bgdkAllBalanceRaitoCompareDateBegin;
    }

    public void setBgdkAllBalanceRaitoCompareDateBegin(BigDecimal bgdkAllBalanceRaitoCompareDateBegin) {
        this.bgdkAllBalanceRaitoCompareDateBegin = bgdkAllBalanceRaitoCompareDateBegin;
    }

    public BigDecimal getBgdkAllIncrementRatio() {
        return bgdkAllIncrementRatio;
    }

    public void setBgdkAllIncrementRatio(BigDecimal bgdkAllIncrementRatio) {
        this.bgdkAllIncrementRatio = bgdkAllIncrementRatio;
    }

    public BigDecimal getPjrzBalance() {
        return pjrzBalance;
    }

    public void setPjrzBalance(BigDecimal pjrzBalance) {
        this.pjrzBalance = pjrzBalance;
    }

    public BigDecimal getPjrzIncrement() {
        return pjrzIncrement;
    }

    public void setPjrzIncrement(BigDecimal pjrzIncrement) {
        this.pjrzIncrement = pjrzIncrement;
    }

    public BigDecimal getPjrzIncrementGrowthRatio() {
        return pjrzIncrementGrowthRatio;
    }

    public void setPjrzIncrementGrowthRatio(BigDecimal pjrzIncrementGrowthRatio) {
        this.pjrzIncrementGrowthRatio = pjrzIncrementGrowthRatio;
    }

    public BigDecimal getPjrzSelfBalanceRaito() {
        return pjrzSelfBalanceRaito;
    }

    public void setPjrzSelfBalanceRaito(BigDecimal pjrzSelfBalanceRaito) {
        this.pjrzSelfBalanceRaito = pjrzSelfBalanceRaito;
    }

    public BigDecimal getPjrzSelfBalanceRaitoCompareDateBegin() {
        return pjrzSelfBalanceRaitoCompareDateBegin;
    }

    public void setPjrzSelfBalanceRaitoCompareDateBegin(BigDecimal pjrzSelfBalanceRaitoCompareDateBegin) {
        this.pjrzSelfBalanceRaitoCompareDateBegin = pjrzSelfBalanceRaitoCompareDateBegin;
    }

    public BigDecimal getPjrzSelfIncrementRatio() {
        return pjrzSelfIncrementRatio;
    }

    public void setPjrzSelfIncrementRatio(BigDecimal pjrzSelfIncrementRatio) {
        this.pjrzSelfIncrementRatio = pjrzSelfIncrementRatio;
    }

    public BigDecimal getPjrzAllBalanceRaito() {
        return pjrzAllBalanceRaito;
    }

    public void setPjrzAllBalanceRaito(BigDecimal pjrzAllBalanceRaito) {
        this.pjrzAllBalanceRaito = pjrzAllBalanceRaito;
    }

    public BigDecimal getPjrzAllBalanceRaitoCompareDateBegin() {
        return pjrzAllBalanceRaitoCompareDateBegin;
    }

    public void setPjrzAllBalanceRaitoCompareDateBegin(BigDecimal pjrzAllBalanceRaitoCompareDateBegin) {
        this.pjrzAllBalanceRaitoCompareDateBegin = pjrzAllBalanceRaitoCompareDateBegin;
    }

    public BigDecimal getPjrzAllIncrementRatio() {
        return pjrzAllIncrementRatio;
    }

    public void setPjrzAllIncrementRatio(BigDecimal pjrzAllIncrementRatio) {
        this.pjrzAllIncrementRatio = pjrzAllIncrementRatio;
    }

    public BigDecimal getFcdkBalance() {
        return fcdkBalance;
    }

    public void setFcdkBalance(BigDecimal fcdkBalance) {
        this.fcdkBalance = fcdkBalance;
    }

    public BigDecimal getFcdkIncrement() {
        return fcdkIncrement;
    }

    public void setFcdkIncrement(BigDecimal fcdkIncrement) {
        this.fcdkIncrement = fcdkIncrement;
    }

    public BigDecimal getFcdkIncrementGrowthRatio() {
        return fcdkIncrementGrowthRatio;
    }

    public void setFcdkIncrementGrowthRatio(BigDecimal fcdkIncrementGrowthRatio) {
        this.fcdkIncrementGrowthRatio = fcdkIncrementGrowthRatio;
    }

    public BigDecimal getFcdkSelfBalanceRaito() {
        return fcdkSelfBalanceRaito;
    }

    public void setFcdkSelfBalanceRaito(BigDecimal fcdkSelfBalanceRaito) {
        this.fcdkSelfBalanceRaito = fcdkSelfBalanceRaito;
    }

    public BigDecimal getFcdkSelfBalanceRaitoCompareDateBegin() {
        return fcdkSelfBalanceRaitoCompareDateBegin;
    }

    public void setFcdkSelfBalanceRaitoCompareDateBegin(BigDecimal fcdkSelfBalanceRaitoCompareDateBegin) {
        this.fcdkSelfBalanceRaitoCompareDateBegin = fcdkSelfBalanceRaitoCompareDateBegin;
    }

    public BigDecimal getFcdkSelfIncrementRatio() {
        return fcdkSelfIncrementRatio;
    }

    public void setFcdkSelfIncrementRatio(BigDecimal fcdkSelfIncrementRatio) {
        this.fcdkSelfIncrementRatio = fcdkSelfIncrementRatio;
    }

    public BigDecimal getFcdkAllBalanceRaito() {
        return fcdkAllBalanceRaito;
    }

    public void setFcdkAllBalanceRaito(BigDecimal fcdkAllBalanceRaito) {
        this.fcdkAllBalanceRaito = fcdkAllBalanceRaito;
    }

    public BigDecimal getFcdkAllBalanceRaitoCompareDateBegin() {
        return fcdkAllBalanceRaitoCompareDateBegin;
    }

    public void setFcdkAllBalanceRaitoCompareDateBegin(BigDecimal fcdkAllBalanceRaitoCompareDateBegin) {
        this.fcdkAllBalanceRaitoCompareDateBegin = fcdkAllBalanceRaitoCompareDateBegin;
    }

    public BigDecimal getFcdkAllIncrementRatio() {
        return fcdkAllIncrementRatio;
    }

    public void setFcdkAllIncrementRatio(BigDecimal fcdkAllIncrementRatio) {
        this.fcdkAllIncrementRatio = fcdkAllIncrementRatio;
    }

    public BigDecimal getJwdkBalance() {
        return jwdkBalance;
    }

    public void setJwdkBalance(BigDecimal jwdkBalance) {
        this.jwdkBalance = jwdkBalance;
    }

    public BigDecimal getJwdkIncrement() {
        return jwdkIncrement;
    }

    public void setJwdkIncrement(BigDecimal jwdkIncrement) {
        this.jwdkIncrement = jwdkIncrement;
    }

    public BigDecimal getJwdkIncrementGrowthRatio() {
        return jwdkIncrementGrowthRatio;
    }

    public void setJwdkIncrementGrowthRatio(BigDecimal jwdkIncrementGrowthRatio) {
        this.jwdkIncrementGrowthRatio = jwdkIncrementGrowthRatio;
    }

    public BigDecimal getJwdkSelfBalanceRaito() {
        return jwdkSelfBalanceRaito;
    }

    public void setJwdkSelfBalanceRaito(BigDecimal jwdkSelfBalanceRaito) {
        this.jwdkSelfBalanceRaito = jwdkSelfBalanceRaito;
    }

    public BigDecimal getJwdkSelfBalanceRaitoCompareDateBegin() {
        return jwdkSelfBalanceRaitoCompareDateBegin;
    }

    public void setJwdkSelfBalanceRaitoCompareDateBegin(BigDecimal jwdkSelfBalanceRaitoCompareDateBegin) {
        this.jwdkSelfBalanceRaitoCompareDateBegin = jwdkSelfBalanceRaitoCompareDateBegin;
    }

    public BigDecimal getJwdkSelfIncrementRatio() {
        return jwdkSelfIncrementRatio;
    }

    public void setJwdkSelfIncrementRatio(BigDecimal jwdkSelfIncrementRatio) {
        this.jwdkSelfIncrementRatio = jwdkSelfIncrementRatio;
    }

    public BigDecimal getJwdkAllBalanceRaito() {
        return jwdkAllBalanceRaito;
    }

    public void setJwdkAllBalanceRaito(BigDecimal jwdkAllBalanceRaito) {
        this.jwdkAllBalanceRaito = jwdkAllBalanceRaito;
    }

    public BigDecimal getJwdkAllBalanceRaitoCompareDateBegin() {
        return jwdkAllBalanceRaitoCompareDateBegin;
    }

    public void setJwdkAllBalanceRaitoCompareDateBegin(BigDecimal jwdkAllBalanceRaitoCompareDateBegin) {
        this.jwdkAllBalanceRaitoCompareDateBegin = jwdkAllBalanceRaitoCompareDateBegin;
    }

    public BigDecimal getJwdkAllIncrementRatio() {
        return jwdkAllIncrementRatio;
    }

    public void setJwdkAllIncrementRatio(BigDecimal jwdkAllIncrementRatio) {
        this.jwdkAllIncrementRatio = jwdkAllIncrementRatio;
    }

    public BigDecimal getGxdk2Balance() {
        return gxdk2Balance;
    }

    public void setGxdk2Balance(BigDecimal gxdk2Balance) {
        this.gxdk2Balance = gxdk2Balance;
    }

    public BigDecimal getGxdk2Increment() {
        return gxdk2Increment;
    }

    public void setGxdk2Increment(BigDecimal gxdk2Increment) {
        this.gxdk2Increment = gxdk2Increment;
    }

    public BigDecimal getGxdk2IncrementGrowthRatio() {
        return gxdk2IncrementGrowthRatio;
    }

    public void setGxdk2IncrementGrowthRatio(BigDecimal gxdk2IncrementGrowthRatio) {
        this.gxdk2IncrementGrowthRatio = gxdk2IncrementGrowthRatio;
    }

    public BigDecimal getGxdk2SelfBalanceRaito() {
        return gxdk2SelfBalanceRaito;
    }

    public void setGxdk2SelfBalanceRaito(BigDecimal gxdk2SelfBalanceRaito) {
        this.gxdk2SelfBalanceRaito = gxdk2SelfBalanceRaito;
    }

    public BigDecimal getGxdk2SelfBalanceRaitoCompareDateBegin() {
        return gxdk2SelfBalanceRaitoCompareDateBegin;
    }

    public void setGxdk2SelfBalanceRaitoCompareDateBegin(BigDecimal gxdk2SelfBalanceRaitoCompareDateBegin) {
        this.gxdk2SelfBalanceRaitoCompareDateBegin = gxdk2SelfBalanceRaitoCompareDateBegin;
    }

    public BigDecimal getGxdk2SelfIncrementRatio() {
        return gxdk2SelfIncrementRatio;
    }

    public void setGxdk2SelfIncrementRatio(BigDecimal gxdk2SelfIncrementRatio) {
        this.gxdk2SelfIncrementRatio = gxdk2SelfIncrementRatio;
    }

    public BigDecimal getGxdk2AllBalanceRaito() {
        return gxdk2AllBalanceRaito;
    }

    public void setGxdk2AllBalanceRaito(BigDecimal gxdk2AllBalanceRaito) {
        this.gxdk2AllBalanceRaito = gxdk2AllBalanceRaito;
    }

    public BigDecimal getGxdk2AllBalanceRaitoCompareDateBegin() {
        return gxdk2AllBalanceRaitoCompareDateBegin;
    }

    public void setGxdk2AllBalanceRaitoCompareDateBegin(BigDecimal gxdk2AllBalanceRaitoCompareDateBegin) {
        this.gxdk2AllBalanceRaitoCompareDateBegin = gxdk2AllBalanceRaitoCompareDateBegin;
    }

    public BigDecimal getGxdk2AllIncrementRatio() {
        return gxdk2AllIncrementRatio;
    }

    public void setGxdk2AllIncrementRatio(BigDecimal gxdk2AllIncrementRatio) {
        this.gxdk2AllIncrementRatio = gxdk2AllIncrementRatio;
    }

    /**
     * 把list转换为树形list
     *
     * @param list       需要有id parentId
     * @param maxLevel   树形层级
     * @param upParentId 顶层parentId
     * @return
     */
    public static List<BankIndustryReport> transToTree(List<BankIndustryReport> list, int maxLevel, String upParentId, int startLevel) {
        List<Map<String, List<BankIndustryReport>>> totalList = new ArrayList<>();
        for (int i = 0; i < maxLevel + 1; i++) {
            Map<String, List<BankIndustryReport>> map = new HashMap<>();
            totalList.add(map);
        }
        if (list != null && list.size() > 0) {
            for (int i = maxLevel; i > 0; i--) {
                Map<String, List<BankIndustryReport>> map = totalList.get(i);
                for (BankIndustryReport tree : list) {
                    Integer level = tree.getLevel();
                    if (level == i) {
                        String parentId = tree.getParentId();
                        String id = tree.getId();
                        if (i < maxLevel) {
                            Map<String, List<BankIndustryReport>> lowMap = totalList.get(i + 1);
                            if (lowMap != null) {
                                List<BankIndustryReport> lowList = lowMap.get(id);
                                if (lowList != null && lowList.size() > 0) {
                                    tree.setChildren(lowList);
                                }
                            }
                        }
                        List<BankIndustryReport> tempList = map.get(parentId);
                        if (tempList == null) {
                            tempList = new ArrayList<>();
                        }
                        tempList.add(tree);
                        map.put(parentId, tempList);
                    }
                }
            }
        }
        Map<String, List<BankIndustryReport>> map = totalList.get(startLevel);
        List<BankIndustryReport> resultList = map.get(upParentId);

        return resultList;
    }

    /**
     * 树形list转换为正常list
     * @param dataList 树形list
     * @param list     正常list
     * @param level    树形层级level 大于level的层级不会被转换
     * @return
     */
    public static List<BankIndustryReport> treeTransTo(List<BankIndustryReport> dataList, List<BankIndustryReport> list, Integer level) {
        for (BankIndustryReport report : dataList) {
            if (report.level <= level) {
                list.add(report);
                if (report.getChildren() != null && report.getChildren().size() > 0) {
                    treeTransTo(report.getChildren(), list, level);
                }
            }
        }
        return list;
    }
}
