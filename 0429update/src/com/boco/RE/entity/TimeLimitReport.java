package com.boco.RE.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 期限表
 */
public class TimeLimitReport implements Serializable {

    private String id;
    private String parentId;
    private int level;
    private List<TimeLimitReport> children;
    private boolean open;
    private String name;
    private Integer order;

    /**
     * 1月以内余额
     */
    private BigDecimal month1Balance;
    /**
     * 笔数
     */
    private Integer month1Count;
    /**
     * 1-3月余额
     */
    private BigDecimal month13Balance;
    /**
     * 笔数
     */
    private Integer month13Count;
    /**
     * 3-6月余额
     */
    private BigDecimal month36Balance;
    /**
     * 笔数
     */
    private Integer month36Count;
    /**
     * 6-12月余额
     */
    private BigDecimal month612Balance;
    /**
     * 笔数
     */
    private Integer month612Count;
    /**
     * 短期余额合计
     */
    private BigDecimal monthBalanceTotal;
    /**
     * 短期笔数合计
     */
    private Integer monthCountTotal;
    /**
     * 1-2年余额
     */
    private BigDecimal year12Balance;
    /**
     * 笔数
     */
    private Integer year12Count;
    /**
     * 2-3年余额
     */
    private BigDecimal year23Balance;
    /**
     * 笔数
     */
    private Integer year23Count;
    /**
     * 3-5年余额
     */
    private BigDecimal year35Balance;
    /**
     * 笔数
     */
    private Integer year35Count;
    /**
     * 5-10年余额
     */
    private BigDecimal year510Balance;
    /**
     * 笔数
     */
    private Integer year510Count;
    /**
     * 10年以上余额
     */
    private BigDecimal year10Balance;
    /**
     * 笔数
     */
    private Integer year10Count;
    /**
     * 中长期余额合计
     */
    private BigDecimal yearBalanceTotal;
    /**
     * 中长期笔数合计
     */
    private Integer yearCountTotal;
    /**
     * 余额总计
     */
    private BigDecimal balanceTotal;
    /**
     * 笔数总计
     */
    private Integer countTotal;

    public TimeLimitReport() {

        this.open = false;
        this.order = 0;
        this.month1Balance = BigDecimal.ZERO;
        this.month1Count = 0;
        this.month13Balance = BigDecimal.ZERO;
        this.month13Count = 0;
        this.month36Balance = BigDecimal.ZERO;
        this.month36Count = 0;
        this.month612Balance = BigDecimal.ZERO;
        this.month612Count = 0;
        this.monthBalanceTotal = BigDecimal.ZERO;
        this.monthCountTotal = 0;
        this.year12Balance = BigDecimal.ZERO;
        this.year12Count = 0;
        this.year23Balance = BigDecimal.ZERO;
        this.year23Count = 0;
        this.year35Balance = BigDecimal.ZERO;
        this.year35Count = 0;
        this.year510Balance = BigDecimal.ZERO;
        this.year510Count = 0;
        this.year10Balance = BigDecimal.ZERO;
        this.year10Count = 0;
        this.yearBalanceTotal = BigDecimal.ZERO;
        this.yearCountTotal = 0;
        this.balanceTotal = BigDecimal.ZERO;
        this.countTotal = 0;
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

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<TimeLimitReport> getChildren() {
        return children;
    }

    public void setChildren(List<TimeLimitReport> children) {
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

    public BigDecimal getMonth1Balance() {
        return month1Balance;
    }

    public void setMonth1Balance(BigDecimal month1Balance) {
        this.month1Balance = month1Balance;
    }

    public Integer getMonth1Count() {
        return month1Count;
    }

    public void setMonth1Count(Integer month1Count) {
        this.month1Count = month1Count;
    }

    public BigDecimal getMonth13Balance() {
        return month13Balance;
    }

    public void setMonth13Balance(BigDecimal month13Balance) {
        this.month13Balance = month13Balance;
    }

    public Integer getMonth13Count() {
        return month13Count;
    }

    public void setMonth13Count(Integer month13Count) {
        this.month13Count = month13Count;
    }

    public BigDecimal getMonth36Balance() {
        return month36Balance;
    }

    public void setMonth36Balance(BigDecimal month36Balance) {
        this.month36Balance = month36Balance;
    }

    public Integer getMonth36Count() {
        return month36Count;
    }

    public void setMonth36Count(Integer month36Count) {
        this.month36Count = month36Count;
    }

    public BigDecimal getMonth612Balance() {
        return month612Balance;
    }

    public void setMonth612Balance(BigDecimal month612Balance) {
        this.month612Balance = month612Balance;
    }

    public Integer getMonth612Count() {
        return month612Count;
    }

    public void setMonth612Count(Integer month612Count) {
        this.month612Count = month612Count;
    }

    public BigDecimal getMonthBalanceTotal() {
        return monthBalanceTotal;
    }

    public void setMonthBalanceTotal(BigDecimal monthBalanceTotal) {
        this.monthBalanceTotal = monthBalanceTotal;
    }

    public Integer getMonthCountTotal() {
        return monthCountTotal;
    }

    public void setMonthCountTotal(Integer monthCountTotal) {
        this.monthCountTotal = monthCountTotal;
    }

    public BigDecimal getYear12Balance() {
        return year12Balance;
    }

    public void setYear12Balance(BigDecimal year12Balance) {
        this.year12Balance = year12Balance;
    }

    public Integer getYear12Count() {
        return year12Count;
    }

    public void setYear12Count(Integer year12Count) {
        this.year12Count = year12Count;
    }

    public BigDecimal getYear23Balance() {
        return year23Balance;
    }

    public void setYear23Balance(BigDecimal year23Balance) {
        this.year23Balance = year23Balance;
    }

    public Integer getYear23Count() {
        return year23Count;
    }

    public void setYear23Count(Integer year23Count) {
        this.year23Count = year23Count;
    }

    public BigDecimal getYear35Balance() {
        return year35Balance;
    }

    public void setYear35Balance(BigDecimal year35Balance) {
        this.year35Balance = year35Balance;
    }

    public Integer getYear35Count() {
        return year35Count;
    }

    public void setYear35Count(Integer year35Count) {
        this.year35Count = year35Count;
    }

    public BigDecimal getYear510Balance() {
        return year510Balance;
    }

    public void setYear510Balance(BigDecimal year510Balance) {
        this.year510Balance = year510Balance;
    }

    public Integer getYear510Count() {
        return year510Count;
    }

    public void setYear510Count(Integer year510Count) {
        this.year510Count = year510Count;
    }

    public BigDecimal getYear10Balance() {
        return year10Balance;
    }

    public void setYear10Balance(BigDecimal year10Balance) {
        this.year10Balance = year10Balance;
    }

    public Integer getYear10Count() {
        return year10Count;
    }

    public void setYear10Count(Integer year10Count) {
        this.year10Count = year10Count;
    }

    public BigDecimal getYearBalanceTotal() {
        return yearBalanceTotal;
    }

    public void setYearBalanceTotal(BigDecimal yearBalanceTotal) {
        this.yearBalanceTotal = yearBalanceTotal;
    }

    public Integer getYearCountTotal() {
        return yearCountTotal;
    }

    public void setYearCountTotal(Integer yearCountTotal) {
        this.yearCountTotal = yearCountTotal;
    }

    public BigDecimal getBalanceTotal() {
        return balanceTotal;
    }

    public void setBalanceTotal(BigDecimal balanceTotal) {
        this.balanceTotal = balanceTotal;
    }

    public Integer getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(Integer countTotal) {
        this.countTotal = countTotal;
    }

    /**
     * 把list转换为树形list
     *
     * @param list       需要有id parentId
     * @param maxLevel   树形层级
     * @param upParentId 顶层parentId
     * @return
     */
    public static List<TimeLimitReport> transToTree(List<TimeLimitReport> list, int maxLevel, String upParentId, int startLevel) {
        List<Map<String, List<TimeLimitReport>>> totalList = new ArrayList<>();
        for (int i = 0; i < maxLevel + 1; i++) {
            Map<String, List<TimeLimitReport>> map = new HashMap<>();
            totalList.add(map);
        }
        if (list != null && list.size() > 0) {
            for (int i = maxLevel; i > 0; i--) {
                Map<String, List<TimeLimitReport>> map = totalList.get(i);
                for (TimeLimitReport tree : list) {
                    Integer level = tree.getLevel();
                    if (level == i) {
                        String parentId = tree.getParentId();
                        String id = tree.getId();
                        if (i < maxLevel) {
                            Map<String, List<TimeLimitReport>> lowMap = totalList.get(i + 1);
                            if (lowMap != null) {
                                List<TimeLimitReport> lowList = lowMap.get(id);
                                if (lowList != null && lowList.size() > 0) {
                                    tree.setChildren(lowList);
                                }
                            }
                        }
                        List<TimeLimitReport> tempList = map.get(parentId);
                        if (tempList == null) {
                            tempList = new ArrayList<>();
                        }
                        tempList.add(tree);
                        map.put(parentId, tempList);
                    }
                }
            }
        }
        Map<String, List<TimeLimitReport>> map = totalList.get(startLevel);
        List<TimeLimitReport> resultList = map.get(upParentId);

        return resultList;
    }

    /**
     * 树形list转换为正常list
     * @param dataList 树形list
     * @param list     正常list
     * @param level    树形层级level 大于level的层级不会被转换
     * @return
     */
    public static List<TimeLimitReport> treeTransTo(List<TimeLimitReport> dataList, List<TimeLimitReport> list, Integer level) {
        for (TimeLimitReport report : dataList) {
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
