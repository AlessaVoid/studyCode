package com.boco.RE.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流量表
 */
public class DataFlowReport implements Serializable {

    private String id;
    private String parentId;
    private int level;
    private List<DataFlowReport> children;
    private boolean open;
    private String name;
    private Integer order;

    /*1.1个人经营性贷款*/
    /**
     * 新发放
     */
    private BigDecimal occ_11;
    /**
     * 还款合计
     */
    private BigDecimal expire_11;
    /**
     * 合同到期还款
     */
    private BigDecimal expireNormal_11;
    /**
     * 合同提前还款
     */
    private BigDecimal expireAdv_11;

    /*1.2小企业*/
    private BigDecimal occ_12;
    private BigDecimal expire_12;
    private BigDecimal expireNormal_12;
    private BigDecimal expireAdv_12;
    /*2.1住房按揭贷款*/
    private BigDecimal occ_21;
    private BigDecimal expire_21;
    private BigDecimal expireNormal_21;
    private BigDecimal expireAdv_21;
    /*2.2其他消费贷款*/
    private BigDecimal occ_22;
    private BigDecimal expire_22;
    private BigDecimal expireNormal_22;
    private BigDecimal expireAdv_22;
    /*2.3供应链与贸易融资*/
    private BigDecimal occ_23;
    private BigDecimal expire_23;
    private BigDecimal expireNormal_23;
    private BigDecimal expireAdv_23;
    /*2.4公司贷款*/
    private BigDecimal occ_24;
    private BigDecimal expire_24;
    private BigDecimal expireNormal_24;
    private BigDecimal expireAdv_24;
    /*3.1转贴*/
    private BigDecimal occ_31;
    private BigDecimal expire_31;
    private BigDecimal expireNormal_31;
    private BigDecimal expireAdv_31;
    /*3.2直贴*/
    private BigDecimal occ_32;
    private BigDecimal expire_32;
    private BigDecimal expireNormal_32;
    private BigDecimal expireAdv_32;
    /*3.3福费廷*/
    private BigDecimal occ_33;
    private BigDecimal expire_33;
    private BigDecimal expireNormal_33;
    private BigDecimal expireAdv_33;
    /*4.1个人信用卡透支*/
    private BigDecimal occ_41;
    private BigDecimal expire_41;
    private BigDecimal expireNormal_41;
    private BigDecimal expireAdv_41;
    /*4.2拆放非银*/
    private BigDecimal occ_42;
    private BigDecimal expire_42;
    private BigDecimal expireNormal_42;
    private BigDecimal expireAdv_42;


    public DataFlowReport() {
        this.open = false;
        this.order = 0;
        this.occ_11 = BigDecimal.ZERO;
        this.expire_11 = BigDecimal.ZERO;
        this.expireNormal_11 = BigDecimal.ZERO;
        this.expireAdv_11 = BigDecimal.ZERO;
        this.occ_12 = BigDecimal.ZERO;
        this.expire_12 = BigDecimal.ZERO;
        this.expireNormal_12 = BigDecimal.ZERO;
        this.expireAdv_12 = BigDecimal.ZERO;
        this.occ_21 = BigDecimal.ZERO;
        this.expire_21 = BigDecimal.ZERO;
        this.expireNormal_21 = BigDecimal.ZERO;
        this.expireAdv_21 = BigDecimal.ZERO;
        this.occ_22 = BigDecimal.ZERO;
        this.expire_22 = BigDecimal.ZERO;
        this.expireNormal_22 = BigDecimal.ZERO;
        this.expireAdv_22 = BigDecimal.ZERO;
        this.occ_23 = BigDecimal.ZERO;
        this.expire_23 = BigDecimal.ZERO;
        this.expireNormal_23 = BigDecimal.ZERO;
        this.expireAdv_23 = BigDecimal.ZERO;
        this.occ_24 = BigDecimal.ZERO;
        this.expire_24 = BigDecimal.ZERO;
        this.expireNormal_24 = BigDecimal.ZERO;
        this.expireAdv_24 = BigDecimal.ZERO;
        this.occ_31 = BigDecimal.ZERO;
        this.expire_31 = BigDecimal.ZERO;
        this.expireNormal_31 = BigDecimal.ZERO;
        this.expireAdv_31 = BigDecimal.ZERO;
        this.occ_32 = BigDecimal.ZERO;
        this.expire_32 = BigDecimal.ZERO;
        this.expireNormal_32 = BigDecimal.ZERO;
        this.expireAdv_32 = BigDecimal.ZERO;
        this.occ_33 = BigDecimal.ZERO;
        this.expire_33 = BigDecimal.ZERO;
        this.expireNormal_33 = BigDecimal.ZERO;
        this.expireAdv_33 = BigDecimal.ZERO;
        this.occ_41 = BigDecimal.ZERO;
        this.expire_41 = BigDecimal.ZERO;
        this.expireNormal_41 = BigDecimal.ZERO;
        this.expireAdv_41 = BigDecimal.ZERO;
        this.occ_42 = BigDecimal.ZERO;
        this.expire_42 = BigDecimal.ZERO;
        this.expireNormal_42 = BigDecimal.ZERO;
        this.expireAdv_42 = BigDecimal.ZERO;
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

    public List<DataFlowReport> getChildren() {
        return children;
    }

    public void setChildren(List<DataFlowReport> children) {
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

    public BigDecimal getOcc_11() {
        return occ_11;
    }

    public void setOcc_11(BigDecimal occ_11) {
        this.occ_11 = occ_11;
    }

    public BigDecimal getExpire_11() {
        return expire_11;
    }

    public void setExpire_11(BigDecimal expire_11) {
        this.expire_11 = expire_11;
    }

    public BigDecimal getExpireNormal_11() {
        return expireNormal_11;
    }

    public void setExpireNormal_11(BigDecimal expireNormal_11) {
        this.expireNormal_11 = expireNormal_11;
    }

    public BigDecimal getExpireAdv_11() {
        return expireAdv_11;
    }

    public void setExpireAdv_11(BigDecimal expireAdv_11) {
        this.expireAdv_11 = expireAdv_11;
    }

    public BigDecimal getOcc_12() {
        return occ_12;
    }

    public void setOcc_12(BigDecimal occ_12) {
        this.occ_12 = occ_12;
    }

    public BigDecimal getExpire_12() {
        return expire_12;
    }

    public void setExpire_12(BigDecimal expire_12) {
        this.expire_12 = expire_12;
    }

    public BigDecimal getExpireNormal_12() {
        return expireNormal_12;
    }

    public void setExpireNormal_12(BigDecimal expireNormal_12) {
        this.expireNormal_12 = expireNormal_12;
    }

    public BigDecimal getExpireAdv_12() {
        return expireAdv_12;
    }

    public void setExpireAdv_12(BigDecimal expireAdv_12) {
        this.expireAdv_12 = expireAdv_12;
    }

    public BigDecimal getOcc_21() {
        return occ_21;
    }

    public void setOcc_21(BigDecimal occ_21) {
        this.occ_21 = occ_21;
    }

    public BigDecimal getExpire_21() {
        return expire_21;
    }

    public void setExpire_21(BigDecimal expire_21) {
        this.expire_21 = expire_21;
    }

    public BigDecimal getExpireNormal_21() {
        return expireNormal_21;
    }

    public void setExpireNormal_21(BigDecimal expireNormal_21) {
        this.expireNormal_21 = expireNormal_21;
    }

    public BigDecimal getExpireAdv_21() {
        return expireAdv_21;
    }

    public void setExpireAdv_21(BigDecimal expireAdv_21) {
        this.expireAdv_21 = expireAdv_21;
    }

    public BigDecimal getOcc_22() {
        return occ_22;
    }

    public void setOcc_22(BigDecimal occ_22) {
        this.occ_22 = occ_22;
    }

    public BigDecimal getExpire_22() {
        return expire_22;
    }

    public void setExpire_22(BigDecimal expire_22) {
        this.expire_22 = expire_22;
    }

    public BigDecimal getExpireNormal_22() {
        return expireNormal_22;
    }

    public void setExpireNormal_22(BigDecimal expireNormal_22) {
        this.expireNormal_22 = expireNormal_22;
    }

    public BigDecimal getExpireAdv_22() {
        return expireAdv_22;
    }

    public void setExpireAdv_22(BigDecimal expireAdv_22) {
        this.expireAdv_22 = expireAdv_22;
    }

    public BigDecimal getOcc_23() {
        return occ_23;
    }

    public void setOcc_23(BigDecimal occ_23) {
        this.occ_23 = occ_23;
    }

    public BigDecimal getExpire_23() {
        return expire_23;
    }

    public void setExpire_23(BigDecimal expire_23) {
        this.expire_23 = expire_23;
    }

    public BigDecimal getExpireNormal_23() {
        return expireNormal_23;
    }

    public void setExpireNormal_23(BigDecimal expireNormal_23) {
        this.expireNormal_23 = expireNormal_23;
    }

    public BigDecimal getExpireAdv_23() {
        return expireAdv_23;
    }

    public void setExpireAdv_23(BigDecimal expireAdv_23) {
        this.expireAdv_23 = expireAdv_23;
    }

    public BigDecimal getOcc_24() {
        return occ_24;
    }

    public void setOcc_24(BigDecimal occ_24) {
        this.occ_24 = occ_24;
    }

    public BigDecimal getExpire_24() {
        return expire_24;
    }

    public void setExpire_24(BigDecimal expire_24) {
        this.expire_24 = expire_24;
    }

    public BigDecimal getExpireNormal_24() {
        return expireNormal_24;
    }

    public void setExpireNormal_24(BigDecimal expireNormal_24) {
        this.expireNormal_24 = expireNormal_24;
    }

    public BigDecimal getExpireAdv_24() {
        return expireAdv_24;
    }

    public void setExpireAdv_24(BigDecimal expireAdv_24) {
        this.expireAdv_24 = expireAdv_24;
    }

    public BigDecimal getOcc_31() {
        return occ_31;
    }

    public void setOcc_31(BigDecimal occ_31) {
        this.occ_31 = occ_31;
    }

    public BigDecimal getExpire_31() {
        return expire_31;
    }

    public void setExpire_31(BigDecimal expire_31) {
        this.expire_31 = expire_31;
    }

    public BigDecimal getExpireNormal_31() {
        return expireNormal_31;
    }

    public void setExpireNormal_31(BigDecimal expireNormal_31) {
        this.expireNormal_31 = expireNormal_31;
    }

    public BigDecimal getExpireAdv_31() {
        return expireAdv_31;
    }

    public void setExpireAdv_31(BigDecimal expireAdv_31) {
        this.expireAdv_31 = expireAdv_31;
    }

    public BigDecimal getOcc_32() {
        return occ_32;
    }

    public void setOcc_32(BigDecimal occ_32) {
        this.occ_32 = occ_32;
    }

    public BigDecimal getExpire_32() {
        return expire_32;
    }

    public void setExpire_32(BigDecimal expire_32) {
        this.expire_32 = expire_32;
    }

    public BigDecimal getExpireNormal_32() {
        return expireNormal_32;
    }

    public void setExpireNormal_32(BigDecimal expireNormal_32) {
        this.expireNormal_32 = expireNormal_32;
    }

    public BigDecimal getExpireAdv_32() {
        return expireAdv_32;
    }

    public void setExpireAdv_32(BigDecimal expireAdv_32) {
        this.expireAdv_32 = expireAdv_32;
    }

    public BigDecimal getOcc_33() {
        return occ_33;
    }

    public void setOcc_33(BigDecimal occ_33) {
        this.occ_33 = occ_33;
    }

    public BigDecimal getExpire_33() {
        return expire_33;
    }

    public void setExpire_33(BigDecimal expire_33) {
        this.expire_33 = expire_33;
    }

    public BigDecimal getExpireNormal_33() {
        return expireNormal_33;
    }

    public void setExpireNormal_33(BigDecimal expireNormal_33) {
        this.expireNormal_33 = expireNormal_33;
    }

    public BigDecimal getExpireAdv_33() {
        return expireAdv_33;
    }

    public void setExpireAdv_33(BigDecimal expireAdv_33) {
        this.expireAdv_33 = expireAdv_33;
    }

    public BigDecimal getOcc_41() {
        return occ_41;
    }

    public void setOcc_41(BigDecimal occ_41) {
        this.occ_41 = occ_41;
    }

    public BigDecimal getExpire_41() {
        return expire_41;
    }

    public void setExpire_41(BigDecimal expire_41) {
        this.expire_41 = expire_41;
    }

    public BigDecimal getExpireNormal_41() {
        return expireNormal_41;
    }

    public void setExpireNormal_41(BigDecimal expireNormal_41) {
        this.expireNormal_41 = expireNormal_41;
    }

    public BigDecimal getExpireAdv_41() {
        return expireAdv_41;
    }

    public void setExpireAdv_41(BigDecimal expireAdv_41) {
        this.expireAdv_41 = expireAdv_41;
    }

    public BigDecimal getOcc_42() {
        return occ_42;
    }

    public void setOcc_42(BigDecimal occ_42) {
        this.occ_42 = occ_42;
    }

    public BigDecimal getExpire_42() {
        return expire_42;
    }

    public void setExpire_42(BigDecimal expire_42) {
        this.expire_42 = expire_42;
    }

    public BigDecimal getExpireNormal_42() {
        return expireNormal_42;
    }

    public void setExpireNormal_42(BigDecimal expireNormal_42) {
        this.expireNormal_42 = expireNormal_42;
    }

    public BigDecimal getExpireAdv_42() {
        return expireAdv_42;
    }

    public void setExpireAdv_42(BigDecimal expireAdv_42) {
        this.expireAdv_42 = expireAdv_42;
    }

    /**
     * 把list转换为树形list
     *
     * @param list       需要有id parentId
     * @param maxLevel   树形层级
     * @param upParentId 顶层parentId
     * @return
     */
    public static List<DataFlowReport> transToTree(List<DataFlowReport> list, int maxLevel, String upParentId, int startLevel) {
        List<Map<String, List<DataFlowReport>>> totalList = new ArrayList<>();
        for (int i = 0; i < maxLevel + 1; i++) {
            Map<String, List<DataFlowReport>> map = new HashMap<>();
            totalList.add(map);
        }
        if (list != null && list.size() > 0) {
            for (int i = maxLevel; i > 0; i--) {
                Map<String, List<DataFlowReport>> map = totalList.get(i);
                for (DataFlowReport tree : list) {
                    Integer level = tree.getLevel();
                    if (level == i) {
                        String parentId = tree.getParentId();
                        String id = tree.getId();
                        if (i < maxLevel) {
                            Map<String, List<DataFlowReport>> lowMap = totalList.get(i + 1);
                            if (lowMap != null) {
                                List<DataFlowReport> lowList = lowMap.get(id);
                                if (lowList != null && lowList.size() > 0) {
                                    tree.setChildren(lowList);
                                }
                            }
                        }
                        List<DataFlowReport> tempList = map.get(parentId);
                        if (tempList == null) {
                            tempList = new ArrayList<>();
                        }
                        tempList.add(tree);
                        map.put(parentId, tempList);
                    }
                }
            }
        }
        Map<String, List<DataFlowReport>> map = totalList.get(startLevel);
        List<DataFlowReport> resultList = map.get(upParentId);

        return resultList;
    }

    /**
     * 树形list转换为正常list
     *
     * @param dataList 树形list
     * @param list     正常list
     * @param level    树形层级level 大于level的层级不会被转换
     * @return
     */
    public static List<DataFlowReport> treeTransTo(List<DataFlowReport> dataList, List<DataFlowReport> list, Integer level) {
        for (DataFlowReport report : dataList) {
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
