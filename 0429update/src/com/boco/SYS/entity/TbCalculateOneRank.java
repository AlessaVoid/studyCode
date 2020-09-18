package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;

import com.boco.SYS.base.BaseEntity;

import java.math.BigDecimal;

/**
 * TbCalculateOneRank实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20190915 txn      批量新建
 * </pre>
 */
public class TbCalculateOneRank extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** 属性 */
    /**
     * id
     */
    @EntityParaAnno(zhName = "id")
    private String id;
    /**
     * 1：存款类 2：贷款 4：需求 8：效益类
     */
    @EntityParaAnno(zhName = "1：存款类 2：贷款 4：需求 8：效益类")
    private Integer type;
    /**
     * rank1
     */
    @EntityParaAnno(zhName = "rank1")
    private BigDecimal rank1;
    /**
     * rank2
     */
    @EntityParaAnno(zhName = "rank2")
    private BigDecimal rank2;
    /**
     * rank3
     */
    @EntityParaAnno(zhName = "rank3")
    private BigDecimal rank3;
    /**
     * rank4
     */
    @EntityParaAnno(zhName = "rank4")
    private BigDecimal rank4;
    /**
     * rank5
     */
    @EntityParaAnno(zhName = "rank5")
    private BigDecimal rank5;
    /**
     * rank6
     */
    @EntityParaAnno(zhName = "rank6")
    private BigDecimal rank6;
    /**
     * rank7
     */
    @EntityParaAnno(zhName = "rank7")
    private BigDecimal rank7;
    /**
     * rank8
     */
    @EntityParaAnno(zhName = "rank8")
    private BigDecimal rank8;
    /**
     * rank9
     */
    @EntityParaAnno(zhName = "rank9")
    private BigDecimal rank9;
    /**
     * rank10
     */
    @EntityParaAnno(zhName = "rank10")
    private BigDecimal rank10;
    /**
     * rank11
     */
    @EntityParaAnno(zhName = "rank11")
    private BigDecimal rank11;
    /**
     * rank12
     */
    @EntityParaAnno(zhName = "rank12")
    private BigDecimal rank12;
    /**
     * rank13
     */
    @EntityParaAnno(zhName = "rank13")
    private BigDecimal rank13;
    /**
     * rank14
     */
    @EntityParaAnno(zhName = "rank14")
    private BigDecimal rank14;
    /**
     * rank15
     */
    @EntityParaAnno(zhName = "rank15")
    private BigDecimal rank15;
    /**
     * rank16
     */
    @EntityParaAnno(zhName = "rank16")
    private BigDecimal rank16;
    /**
     * rank17
     */
    @EntityParaAnno(zhName = "rank17")
    private BigDecimal rank17;
    /**
     * rank18
     */
    @EntityParaAnno(zhName = "rank18")
    private BigDecimal rank18;
    /**
     * rank19
     */
    @EntityParaAnno(zhName = "rank19")
    private BigDecimal rank19;
    /**
     * rank20
     */
    @EntityParaAnno(zhName = "rank20")
    private BigDecimal rank20;
    /**
     * rank21
     */
    @EntityParaAnno(zhName = "rank21")
    private BigDecimal rank21;
    /**
     * rank22
     */
    @EntityParaAnno(zhName = "rank22")
    private BigDecimal rank22;
    /**
     * rank23
     */
    @EntityParaAnno(zhName = "rank23")
    private BigDecimal rank23;
    /**
     * rank24
     */
    @EntityParaAnno(zhName = "rank24")
    private BigDecimal rank24;
    /**
     * rank25
     */
    @EntityParaAnno(zhName = "rank25")
    private BigDecimal rank25;
    /**
     * rank26
     */
    @EntityParaAnno(zhName = "rank26")
    private BigDecimal rank26;
    /**
     * rank27
     */
    @EntityParaAnno(zhName = "rank27")
    private BigDecimal rank27;
    /**
     * rank28
     */
    @EntityParaAnno(zhName = "rank28")
    private BigDecimal rank28;
    /**
     * rank29
     */
    @EntityParaAnno(zhName = "rank29")
    private BigDecimal rank29;
    /**
     * rank30
     */
    @EntityParaAnno(zhName = "rank30")
    private BigDecimal rank30;
    /**
     * rank31
     */
    @EntityParaAnno(zhName = "rank31")
    private BigDecimal rank31;
    /**
     * rank32
     */
    @EntityParaAnno(zhName = "rank32")
    private BigDecimal rank32;
    /**
     * rank33
     */
    @EntityParaAnno(zhName = "rank33")
    private BigDecimal rank33;
    /**
     * rank34
     */
    @EntityParaAnno(zhName = "rank34")
    private BigDecimal rank34;
    /**
     * rank35
     */
    @EntityParaAnno(zhName = "rank35")
    private BigDecimal rank35;
    /**
     * rank36
     */
    @EntityParaAnno(zhName = "rank36")
    private BigDecimal rank36;
    /**
     * createTime
     */
    @EntityParaAnno(zhName = "createTime")
    private String createTime;
    /**
     * updateTime
     */
    @EntityParaAnno(zhName = "updateTime")
    private String updateTime;
    /**
     * createOper
     */
    @EntityParaAnno(zhName = "createOper")
    private String createOper;
    /**
     * updateOper
     */
    @EntityParaAnno(zhName = "updateOper")
    private String updateOper;

    /** 排名系数模板名称 */
    @EntityParaAnno(zhName="排名系数模板名称")
    private String name;
    /** 启用状态：1启用 2停用 */
    @EntityParaAnno(zhName="启用状态：1启用 2停用")
    private Integer status;

    /** setter\getter方法 */
    /**
     * id
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    /**
     * 1：存款类 2：贷款 4：需求 8：效益类
     */
    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return this.type;
    }

    /**
     * rank1
     */
    public void setRank1(BigDecimal rank1) {
        this.rank1 = rank1;
    }

    public BigDecimal getRank1() {
        return this.rank1;
    }

    /**
     * rank2
     */
    public void setRank2(BigDecimal rank2) {
        this.rank2 = rank2;
    }

    public BigDecimal getRank2() {
        return this.rank2;
    }

    /**
     * rank3
     */
    public void setRank3(BigDecimal rank3) {
        this.rank3 = rank3;
    }

    public BigDecimal getRank3() {
        return this.rank3;
    }

    /**
     * rank4
     */
    public void setRank4(BigDecimal rank4) {
        this.rank4 = rank4;
    }

    public BigDecimal getRank4() {
        return this.rank4;
    }

    /**
     * rank5
     */
    public void setRank5(BigDecimal rank5) {
        this.rank5 = rank5;
    }

    public BigDecimal getRank5() {
        return this.rank5;
    }

    /**
     * rank6
     */
    public void setRank6(BigDecimal rank6) {
        this.rank6 = rank6;
    }

    public BigDecimal getRank6() {
        return this.rank6;
    }

    /**
     * rank7
     */
    public void setRank7(BigDecimal rank7) {
        this.rank7 = rank7;
    }

    public BigDecimal getRank7() {
        return this.rank7;
    }

    /**
     * rank8
     */
    public void setRank8(BigDecimal rank8) {
        this.rank8 = rank8;
    }

    public BigDecimal getRank8() {
        return this.rank8;
    }

    /**
     * rank9
     */
    public void setRank9(BigDecimal rank9) {
        this.rank9 = rank9;
    }

    public BigDecimal getRank9() {
        return this.rank9;
    }

    /**
     * rank10
     */
    public void setRank10(BigDecimal rank10) {
        this.rank10 = rank10;
    }

    public BigDecimal getRank10() {
        return this.rank10;
    }

    /**
     * rank11
     */
    public void setRank11(BigDecimal rank11) {
        this.rank11 = rank11;
    }

    public BigDecimal getRank11() {
        return this.rank11;
    }

    /**
     * rank12
     */
    public void setRank12(BigDecimal rank12) {
        this.rank12 = rank12;
    }

    public BigDecimal getRank12() {
        return this.rank12;
    }

    /**
     * rank13
     */
    public void setRank13(BigDecimal rank13) {
        this.rank13 = rank13;
    }

    public BigDecimal getRank13() {
        return this.rank13;
    }

    /**
     * rank14
     */
    public void setRank14(BigDecimal rank14) {
        this.rank14 = rank14;
    }

    public BigDecimal getRank14() {
        return this.rank14;
    }

    /**
     * rank15
     */
    public void setRank15(BigDecimal rank15) {
        this.rank15 = rank15;
    }

    public BigDecimal getRank15() {
        return this.rank15;
    }

    /**
     * rank16
     */
    public void setRank16(BigDecimal rank16) {
        this.rank16 = rank16;
    }

    public BigDecimal getRank16() {
        return this.rank16;
    }

    /**
     * rank17
     */
    public void setRank17(BigDecimal rank17) {
        this.rank17 = rank17;
    }

    public BigDecimal getRank17() {
        return this.rank17;
    }

    /**
     * rank18
     */
    public void setRank18(BigDecimal rank18) {
        this.rank18 = rank18;
    }

    public BigDecimal getRank18() {
        return this.rank18;
    }

    /**
     * rank19
     */
    public void setRank19(BigDecimal rank19) {
        this.rank19 = rank19;
    }

    public BigDecimal getRank19() {
        return this.rank19;
    }

    /**
     * rank20
     */
    public void setRank20(BigDecimal rank20) {
        this.rank20 = rank20;
    }

    public BigDecimal getRank20() {
        return this.rank20;
    }

    /**
     * rank21
     */
    public void setRank21(BigDecimal rank21) {
        this.rank21 = rank21;
    }

    public BigDecimal getRank21() {
        return this.rank21;
    }

    /**
     * rank22
     */
    public void setRank22(BigDecimal rank22) {
        this.rank22 = rank22;
    }

    public BigDecimal getRank22() {
        return this.rank22;
    }

    /**
     * rank23
     */
    public void setRank23(BigDecimal rank23) {
        this.rank23 = rank23;
    }

    public BigDecimal getRank23() {
        return this.rank23;
    }

    /**
     * rank24
     */
    public void setRank24(BigDecimal rank24) {
        this.rank24 = rank24;
    }

    public BigDecimal getRank24() {
        return this.rank24;
    }

    /**
     * rank25
     */
    public void setRank25(BigDecimal rank25) {
        this.rank25 = rank25;
    }

    public BigDecimal getRank25() {
        return this.rank25;
    }

    /**
     * rank26
     */
    public void setRank26(BigDecimal rank26) {
        this.rank26 = rank26;
    }

    public BigDecimal getRank26() {
        return this.rank26;
    }

    /**
     * rank27
     */
    public void setRank27(BigDecimal rank27) {
        this.rank27 = rank27;
    }

    public BigDecimal getRank27() {
        return this.rank27;
    }

    /**
     * rank28
     */
    public void setRank28(BigDecimal rank28) {
        this.rank28 = rank28;
    }

    public BigDecimal getRank28() {
        return this.rank28;
    }

    /**
     * rank29
     */
    public void setRank29(BigDecimal rank29) {
        this.rank29 = rank29;
    }

    public BigDecimal getRank29() {
        return this.rank29;
    }

    /**
     * rank30
     */
    public void setRank30(BigDecimal rank30) {
        this.rank30 = rank30;
    }

    public BigDecimal getRank30() {
        return this.rank30;
    }

    /**
     * rank31
     */
    public void setRank31(BigDecimal rank31) {
        this.rank31 = rank31;
    }

    public BigDecimal getRank31() {
        return this.rank31;
    }

    /**
     * rank32
     */
    public void setRank32(BigDecimal rank32) {
        this.rank32 = rank32;
    }

    public BigDecimal getRank32() {
        return this.rank32;
    }

    /**
     * rank33
     */
    public void setRank33(BigDecimal rank33) {
        this.rank33 = rank33;
    }

    public BigDecimal getRank33() {
        return this.rank33;
    }

    /**
     * rank34
     */
    public void setRank34(BigDecimal rank34) {
        this.rank34 = rank34;
    }

    public BigDecimal getRank34() {
        return this.rank34;
    }

    /**
     * rank35
     */
    public void setRank35(BigDecimal rank35) {
        this.rank35 = rank35;
    }

    public BigDecimal getRank35() {
        return this.rank35;
    }

    /**
     * rank36
     */
    public void setRank36(BigDecimal rank36) {
        this.rank36 = rank36;
    }

    public BigDecimal getRank36() {
        return this.rank36;
    }

    /**
     * createTime
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getCreateTime() {
        return this.createTime;
    }

    /**
     * updateTime
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    /**
     * createOper
     */
    public void setCreateOper(String createOper) {
        this.createOper = createOper == null ? null : createOper.trim();
    }

    public String getCreateOper() {
        return this.createOper;
    }

    /**
     * updateOper
     */
    public void setUpdateOper(String updateOper) {
        this.updateOper = updateOper == null ? null : updateOper.trim();
    }

    public String getUpdateOper() {
        return this.updateOper;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}