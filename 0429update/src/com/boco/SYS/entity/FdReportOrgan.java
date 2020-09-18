package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

/**
 * FdReportOrgan实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class FdReportOrgan extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** 属性 */
    /**
     * 机构号
     */
    @EntityParaAnno(zhName = "机构号")
    private String organcode;
    /**
     * 机构名称
     */
    @EntityParaAnno(zhName = "机构名称")
    private String organname;
    /**
     * 第一种区域划分(1-总行 2-华北地区 3-东北地区 4-华东地区 5-华中地区 6-华南地区 7-西南地区 8-西北地区)
     */
    @EntityParaAnno(zhName = "第一种区域划分(1-总行 2-华北地区 3-东北地区 4-华东地区 5-华中地区 6-华南地区 7-西南地区 8-西北地区)")
    private Integer type1;
    /**
     * 第二种区域划分(1-总行 2-长三角 3-珠三角 4-环渤海 5-中部地区 6-西部地区 7-东北地区)
     */
    @EntityParaAnno(zhName = "第二种区域划分(1-总行 2-长三角 3-珠三角 4-环渤海 5-中部地区 6-西部地区 7-东北地区)")
    private Integer type2;
    /**
     * 第三种区域划分(1-总行 2-第一组 3-第二组 4-第三组 5-第四组)
     */
    @EntityParaAnno(zhName = "第三种区域划分(1-总行 2-第一组 3-第二组 4-第三组 5-第四组)")
    private Integer type3;
    /**
     * remark
     */
    @EntityParaAnno(zhName = "remark")
    private Integer type4;
    /**
     * remark
     */
    @EntityParaAnno(zhName = "remark")
    private Integer type5;
    /**
     * 排序
     */
    @EntityParaAnno(zhName = "排序")
    private Integer organorder;

    /** setter\getter方法 */
    /**
     * 机构号
     */
    public void setOrgancode(String organcode) {
        this.organcode = organcode == null ? null : organcode.trim();
    }

    public String getOrgancode() {
        return this.organcode;
    }

    /**
     * 机构名称
     */
    public void setOrganname(String organname) {
        this.organname = organname == null ? null : organname.trim();
    }

    public String getOrganname() {
        return this.organname;
    }

    /**
     * 第一种区域划分(1-总行 2-华北地区 3-东北地区 4-华东地区 5-华中地区 6-华南地区 7-西南地区 8-西北地区)
     */
    public void setType1(Integer type1) {
        this.type1 = type1;
    }

    public Integer getType1() {
        return this.type1;
    }

    /**
     * 第二种区域划分(1-总行 2-长三角 3-珠三角 4-环渤海 5-中部地区 6-西部地区 7-东北地区)
     */
    public void setType2(Integer type2) {
        this.type2 = type2;
    }

    public Integer getType2() {
        return this.type2;
    }

    /**
     * 第三种区域划分(1-总行 2-第一组 3-第二组 4-第三组 5-第四组)
     */
    public void setType3(Integer type3) {
        this.type3 = type3;
    }

    public Integer getType3() {
        return this.type3;
    }

    /**
     * remark
     */
    public void setType4(Integer type4) {
        this.type4 = type4;
    }

    public Integer getType4() {
        return this.type4;
    }

    /**
     * remark
     */
    public void setType5(Integer type5) {
        this.type5 = type5;
    }

    public Integer getType5() {
        return this.type5;
    }

    /**
     * 排序
     */
    public void setOrganorder(Integer organorder) {
        this.organorder = organorder;
    }

    public Integer getOrganorder() {
        return this.organorder;
    }
}