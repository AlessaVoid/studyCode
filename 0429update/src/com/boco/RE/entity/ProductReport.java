package com.boco.RE.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * ��Ʒ��  ������   �����ص��б�  ��ͬ���id ��parentId ��ͬ
 */
public class ProductReport implements Serializable {

    //combcode
    private String id;
    //upcombcode
    private String parentId;
    //���ּ���
    private int level;
    private List<ProductReport> children;
    private boolean open;
    //��������
    private String name;
    //���������ֶ�
    private Integer combOrder;


    /**
     * ���
     */
    private BigDecimal balance;
    /**
     * ����
     */
    private Integer balanceRank;
    /**
     * ���ռ��
     */
    private BigDecimal balanceRatio;
    /**
     * ���ռ�Ƚ��ڳ�
     */
    private BigDecimal balanceRatioCompareDateBegin;
    /**
     * ���ھ�������(����)
     */
    private BigDecimal current_increase_req_line;

    /**
     * ���ھ�������(����)
     */
    private BigDecimal current_increase_req_organ;
    /**
     * ���ھ�������(����)����
     */
    private Integer current_increase_req_rank;
    /**
     * ���ڼƻ�
     */
    private BigDecimal planAmount;
    /**
     * ���վ���
     */
    private BigDecimal day_increase_num;
    /**
     * ���ھ���
     */
    private BigDecimal current_increase_num;
    /**
     * ���ھ�������
     */
    private Integer current_increase_num_rank;
    /**
     * ���ڳ��ƻ�
     */
    private BigDecimal current_over_plan_num;
    /**
     * ���ھ���ͬ��
     */
    private BigDecimal current_increase_num_yoy;
    /**
     * ���ھ���ռ��
     */
    private BigDecimal current_increase_num_proportion;
    /**
     * ���ڼƻ������
     */
    private BigDecimal current_plan_completion_rate;
    /**
     * ���ڼƻ����������
     */
    private Integer current_plan_completion_rate_rank;
    /**
     * ���ڼƻ������ͬ��
     */
    private BigDecimal current_plan_completion_rate_yoy;
    /**
     * ���ھ�������
     */
    private BigDecimal current_increase_num_growth_rate;
    /**
     * ���ھ�����������
     */
    private Integer current_increase_num_growth_rate_rank;
    /**
     * ���ھ�������ͬ��
     */
    private BigDecimal current_increase_num_growth_rate_yoy;
    /**
     * ���ڷ�����
     */
    private BigDecimal occ;
    /**
     * ����
     */
    private Integer occRank;
    /**
     * ͬ��
     */
    private BigDecimal occYoy;
    /**
     * ����Ԥ�Ƶ�����
     */
    private BigDecimal expireEstimate;
    /**
     * ����
     */
    private Integer expireEstimateRank;
    /**
     * ͬ��
     */
    private BigDecimal expireEstimateYoy;
    /**
     * �����ѵ�����
     */
    private BigDecimal expire;
    /**
     * ����
     */
    private Integer expireRank;
    /**
     * ͬ��
     */
    private BigDecimal expireYoy;
    /**
     * ����ʣ��Ԥ�Ƶ�����
     */
    private BigDecimal expireEstimateLeft;
    /**
     * ����
     */
    private Integer expireEstimateLeftRank;
    /**
     * ͬ��
     */
    private BigDecimal expireEstimateLeftYoy;


    public ProductReport() {
        this.open = false;
        this.combOrder = 0;
        this.balance = BigDecimal.ZERO;
        this.balanceRank = 0;
        this.balanceRatio = BigDecimal.ZERO;
        this.balanceRatioCompareDateBegin = BigDecimal.ZERO;
        this.current_increase_req_line = BigDecimal.ZERO;
        this.current_increase_req_organ = BigDecimal.ZERO;
        this.current_increase_req_rank = 0;
        this.planAmount = BigDecimal.ZERO;
        this.day_increase_num = BigDecimal.ZERO;
        this.current_increase_num = BigDecimal.ZERO;
        this.current_increase_num_rank = 0;
        this.current_over_plan_num = BigDecimal.ZERO;
        this.current_increase_num_yoy = BigDecimal.ZERO;
        this.current_increase_num_proportion = BigDecimal.ZERO;
        this.current_plan_completion_rate = BigDecimal.ZERO;
        this.current_plan_completion_rate_rank = 0;
        this.current_plan_completion_rate_yoy = BigDecimal.ZERO;
        this.current_increase_num_growth_rate = BigDecimal.ZERO;
        this.current_increase_num_growth_rate_rank = 0;
        this.current_increase_num_growth_rate_yoy = BigDecimal.ZERO;
        this.occ = BigDecimal.ZERO;
        this.occRank = 0;
        this.occYoy = BigDecimal.ZERO;
        this.expireEstimate = BigDecimal.ZERO;
        this.expireEstimateRank = 0;
        this.expireEstimateYoy = BigDecimal.ZERO;
        this.expire = BigDecimal.ZERO;
        this.expireRank = 0;
        this.expireYoy = BigDecimal.ZERO;
        this.expireEstimateLeft = BigDecimal.ZERO;
        this.expireEstimateLeftRank = 0;
        this.expireEstimateLeftYoy = BigDecimal.ZERO;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductReport> getChildren() {
        return children;
    }

    public void setChildren(List<ProductReport> children) {
        this.children = children;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public BigDecimal getPlanAmount() {
        return planAmount;
    }

    public void setPlanAmount(BigDecimal planAmount) {
        this.planAmount = planAmount;
    }

    public BigDecimal getCurrent_plan_completion_rate() {
        return current_plan_completion_rate;
    }

    public void setCurrent_plan_completion_rate(BigDecimal current_plan_completion_rate) {
        this.current_plan_completion_rate = current_plan_completion_rate;
    }

    public Integer getCurrent_plan_completion_rate_rank() {
        return current_plan_completion_rate_rank;
    }

    public void setCurrent_plan_completion_rate_rank(Integer current_plan_completion_rate_rank) {
        this.current_plan_completion_rate_rank = current_plan_completion_rate_rank;
    }

    public BigDecimal getCurrent_plan_completion_rate_yoy() {
        return current_plan_completion_rate_yoy;
    }

    public void setCurrent_plan_completion_rate_yoy(BigDecimal current_plan_completion_rate_yoy) {
        this.current_plan_completion_rate_yoy = current_plan_completion_rate_yoy;
    }

    public BigDecimal getCurrent_increase_num_growth_rate() {
        return current_increase_num_growth_rate;
    }

    public void setCurrent_increase_num_growth_rate(BigDecimal current_increase_num_growth_rate) {
        this.current_increase_num_growth_rate = current_increase_num_growth_rate;
    }

    public Integer getCurrent_increase_num_growth_rate_rank() {
        return current_increase_num_growth_rate_rank;
    }

    public void setCurrent_increase_num_growth_rate_rank(Integer current_increase_num_growth_rate_rank) {
        this.current_increase_num_growth_rate_rank = current_increase_num_growth_rate_rank;
    }

    public BigDecimal getCurrent_increase_num_growth_rate_yoy() {
        return current_increase_num_growth_rate_yoy;
    }

    public void setCurrent_increase_num_growth_rate_yoy(BigDecimal current_increase_num_growth_rate_yoy) {
        this.current_increase_num_growth_rate_yoy = current_increase_num_growth_rate_yoy;
    }

    public BigDecimal getCurrent_over_plan_num() {
        return current_over_plan_num;
    }

    public void setCurrent_over_plan_num(BigDecimal current_over_plan_num) {
        this.current_over_plan_num = current_over_plan_num;
    }

    public BigDecimal getCurrent_increase_num_yoy() {
        return current_increase_num_yoy;
    }

    public void setCurrent_increase_num_yoy(BigDecimal current_increase_num_yoy) {
        this.current_increase_num_yoy = current_increase_num_yoy;
    }

    public BigDecimal getCurrent_increase_num_proportion() {
        return current_increase_num_proportion;
    }

    public void setCurrent_increase_num_proportion(BigDecimal current_increase_num_proportion) {
        this.current_increase_num_proportion = current_increase_num_proportion;
    }

    public BigDecimal getCurrent_increase_req_line() {
        return current_increase_req_line;
    }

    public void setCurrent_increase_req_line(BigDecimal current_increase_req_line) {
        this.current_increase_req_line = current_increase_req_line;
    }

    public BigDecimal getCurrent_increase_req_organ() {
        return current_increase_req_organ;
    }

    public void setCurrent_increase_req_organ(BigDecimal current_increase_req_organ) {
        this.current_increase_req_organ = current_increase_req_organ;
    }

    public BigDecimal getDay_increase_num() {
        return day_increase_num;
    }

    public void setDay_increase_num(BigDecimal day_increase_num) {
        this.day_increase_num = day_increase_num;
    }

    public BigDecimal getCurrent_increase_num() {
        return current_increase_num;
    }

    public void setCurrent_increase_num(BigDecimal current_increase_num) {
        this.current_increase_num = current_increase_num;
    }

    public Integer getCurrent_increase_num_rank() {
        return current_increase_num_rank;
    }

    public void setCurrent_increase_num_rank(Integer current_increase_num_rank) {
        this.current_increase_num_rank = current_increase_num_rank;
    }

    public Integer getCurrent_increase_req_rank() {
        return current_increase_req_rank;
    }

    public void setCurrent_increase_req_rank(Integer current_increase_req_rank) {
        this.current_increase_req_rank = current_increase_req_rank;
    }

    public Integer getCombOrder() {
        return combOrder;
    }

    public void setCombOrder(Integer combOrder) {
        this.combOrder = combOrder;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getBalanceRank() {
        return balanceRank;
    }

    public void setBalanceRank(Integer balanceRank) {
        this.balanceRank = balanceRank;
    }

    public BigDecimal getBalanceRatio() {
        return balanceRatio;
    }

    public void setBalanceRatio(BigDecimal balanceRatio) {
        this.balanceRatio = balanceRatio;
    }

    public BigDecimal getBalanceRatioCompareDateBegin() {
        return balanceRatioCompareDateBegin;
    }

    public void setBalanceRatioCompareDateBegin(BigDecimal balanceRatioCompareDateBegin) {
        this.balanceRatioCompareDateBegin = balanceRatioCompareDateBegin;
    }

    public BigDecimal getOcc() {
        return occ;
    }

    public void setOcc(BigDecimal occ) {
        this.occ = occ;
    }

    public Integer getOccRank() {
        return occRank;
    }

    public void setOccRank(Integer occRank) {
        this.occRank = occRank;
    }

    public BigDecimal getOccYoy() {
        return occYoy;
    }

    public void setOccYoy(BigDecimal occYoy) {
        this.occYoy = occYoy;
    }

    public BigDecimal getExpireEstimate() {
        return expireEstimate;
    }

    public void setExpireEstimate(BigDecimal expireEstimate) {
        this.expireEstimate = expireEstimate;
    }

    public Integer getExpireEstimateRank() {
        return expireEstimateRank;
    }

    public void setExpireEstimateRank(Integer expireEstimateRank) {
        this.expireEstimateRank = expireEstimateRank;
    }

    public BigDecimal getExpireEstimateYoy() {
        return expireEstimateYoy;
    }

    public void setExpireEstimateYoy(BigDecimal expireEstimateYoy) {
        this.expireEstimateYoy = expireEstimateYoy;
    }

    public BigDecimal getExpire() {
        return expire;
    }

    public void setExpire(BigDecimal expire) {
        this.expire = expire;
    }

    public Integer getExpireRank() {
        return expireRank;
    }

    public void setExpireRank(Integer expireRank) {
        this.expireRank = expireRank;
    }

    public BigDecimal getExpireYoy() {
        return expireYoy;
    }

    public void setExpireYoy(BigDecimal expireYoy) {
        this.expireYoy = expireYoy;
    }

    public BigDecimal getExpireEstimateLeft() {
        return expireEstimateLeft;
    }

    public void setExpireEstimateLeft(BigDecimal expireEstimateLeft) {
        this.expireEstimateLeft = expireEstimateLeft;
    }

    public Integer getExpireEstimateLeftRank() {
        return expireEstimateLeftRank;
    }

    public void setExpireEstimateLeftRank(Integer expireEstimateLeftRank) {
        this.expireEstimateLeftRank = expireEstimateLeftRank;
    }

    public BigDecimal getExpireEstimateLeftYoy() {
        return expireEstimateLeftYoy;
    }

    public void setExpireEstimateLeftYoy(BigDecimal expireEstimateLeftYoy) {
        this.expireEstimateLeftYoy = expireEstimateLeftYoy;
    }


    /**
     * ��listת��Ϊ����list
     *
     * @param list       ��Ҫ��id parentId
     * @param maxLevel   ���β㼶
     * @param upParentId ����parentId
     * @return
     */
    public static List<ProductReport> transToTree(List<ProductReport> list, int maxLevel, String upParentId, int startLevel) {
        List<Map<String, List<ProductReport>>> totalList = new ArrayList<>();
        for (int i = 0; i < maxLevel + 1; i++) {
            Map<String, List<ProductReport>> map = new HashMap<>();
            totalList.add(map);
        }
        if (list != null && list.size() > 0) {
            for (int i = maxLevel; i > 0; i--) {
                Map<String, List<ProductReport>> map = totalList.get(i);
                for (ProductReport tree : list) {
                    Integer level = tree.getLevel();
                    if (level == i) {
                        String parentId = tree.getParentId();
                        String id = tree.getId();
                        if (i < maxLevel) {
                            Map<String, List<ProductReport>> lowMap = totalList.get(i + 1);
                            if (lowMap != null) {
                                List<ProductReport> lowList = lowMap.get(id);
                                if (lowList != null && lowList.size() > 0) {
                                    tree.setChildren(lowList);
                                }
                            }
                        }
                        List<ProductReport> tempList = map.get(parentId);
                        if (tempList == null) {
                            tempList = new ArrayList<>();
                        }
                        tempList.add(tree);
                        map.put(parentId, tempList);
                    }
                }
            }
        }
        Map<String, List<ProductReport>> map = totalList.get(startLevel);
        List<ProductReport> resultList = map.get(upParentId);

        return resultList;
    }

    /**
     * ����listת��Ϊ����list
     *
     * @param dataList ����list
     * @param list     ����list
     * @param level    ���β㼶level ����level�Ĳ㼶���ᱻת��
     * @return
     */
    public static List<ProductReport> treeTransTo(List<ProductReport> dataList, List<ProductReport> list, Integer level) {
        for (ProductReport report : dataList) {
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

