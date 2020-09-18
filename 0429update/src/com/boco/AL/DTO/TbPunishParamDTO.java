package com.boco.AL.DTO;

import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

/**
 * 罚息参数表实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 20160315 谷立羊      批量新建
 * </pre>
 */
public class TbPunishParamDTO extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** 属性 */
    /**
     * id
     */
    @EntityParaAnno(zhName = "id")
    private Integer ppId;
    /**
     * 罚息名称
     */
    @EntityParaAnno(zhName = "罚息名称")
    private String ppName;
    /**
     * 机构号
     */
    @EntityParaAnno(zhName = "机构号")
    private String ppOrgan;
    /**
     * 罚息类型：1 季末月中,2季末月末,4非季末月中,8非季末月末
     */
    @EntityParaAnno(zhName = "罚息类型：1 季末月中,2季末月末,4非季末月中,8非季末月末")
    private Integer type;
    /**
     * 区间最小值
     */
    @EntityParaAnno(zhName = "区间最小值")
    private Long minnum;
    /**
     * 区间最大值
     */
    @EntityParaAnno(zhName = "区间最大值")
    private Long maxnum;
    /**
     * 区间对应利息
     */
    @EntityParaAnno(zhName = "区间对应利息")
    private Long interest;
    /**
     * 状态
     */
    @EntityParaAnno(zhName = "状态")
    private Integer state;
    /**
     * 创建时间
     */
    @EntityParaAnno(zhName = "创建时间")
    private String createtime;
    /**
     * 更新时间
     */
    @EntityParaAnno(zhName = "更新时间")
    private String updatetime;
    /**
     * 创建用户id
     */
    @EntityParaAnno(zhName = "创建用户id")
    private String createuserid;
    /**
     * 更新用户id
     */
    @EntityParaAnno(zhName = "更新用户id")
    private String updateuserid;
    /**
     * 收取类型
     */
    @EntityParaAnno(zhName = "收取类型")
    private java.lang.Integer collecttype;
    /**
     * 罚息类型
     */
    @EntityParaAnno(zhName = "罚息类型")
    private java.lang.Integer ppType;

    /** setter\getter方法 */
    /**
     * id
     */
    public void setPpId(Integer ppId) {
        this.ppId = ppId;
    }

    public Integer getPpId() {
        return this.ppId;
    }

    /**
     * 罚息名称
     */
    public void setPpName(String ppName) {
        this.ppName = ppName == null ? null : ppName.trim();
    }

    public String getPpName() {
        return this.ppName;
    }

    /**
     * 机构号
     */
    public void setPpOrgan(String ppOrgan) {
        this.ppOrgan = ppOrgan == null ? null : ppOrgan.trim();
    }

    public String getPpOrgan() {
        return this.ppOrgan;
    }

    /**
     * 罚息类型：1 季末月中,2季末月末,4非季末月中,8非季末月末
     */
    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return this.type;
    }

    /**
     * 区间最小值
     */
    public void setMinnum(Long minnum) {
        this.minnum = minnum;
    }

    public Long getMinnum() {
        return this.minnum;
    }

    /**
     * 区间最大值
     */
    public void setMaxnum(Long maxnum) {
        this.maxnum = maxnum;
    }

    public Long getMaxnum() {
        return this.maxnum;
    }

    /**
     * 区间对应利息
     */
    public void setInterest(Long interest) {
        this.interest = interest;
    }

    public Long getInterest() {
        return this.interest;
    }

    /**
     * 状态
     */
    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return this.state;
    }

    /**
     * 创建时间
     */
    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getCreatetime() {
        return this.createtime;
    }

    /**
     * 更新时间
     */
    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime == null ? null : updatetime.trim();
    }

    public String getUpdatetime() {
        return this.updatetime;
    }

    /**
     * 创建用户id
     */
    public void setCreateuserid(String createuserid) {
        this.createuserid = createuserid == null ? null : createuserid.trim();
    }

    public String getCreateuserid() {
        return this.createuserid;
    }

    /**
     * 更新用户id
     */
    public void setUpdateuserid(String updateuserid) {
        this.updateuserid = updateuserid == null ? null : updateuserid.trim();
    }

    public String getUpdateuserid() {
        return this.updateuserid;
    }

    /**
     * 收取类型
     */
    public void setCollecttype(java.lang.Integer collecttype) {
        this.collecttype = collecttype;
    }

    public java.lang.Integer getCollecttype() {
        return this.collecttype;
    }

    /**
     * 罚息类型
     */
    public void setPpType(java.lang.Integer ppType) {
        this.ppType = ppType;
    }

    public java.lang.Integer getPpType() {
        return this.ppType;
    }
}