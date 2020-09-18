package com.boco.SYS.entity;

import com.boco.SYS.annotation.EntityParaAnno;
import com.boco.SYS.base.BaseEntity;

/**
 * FdOrgan实体类
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29  杨滔      批量新建
 * </pre>
 */
public class FdOrgan extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    /** 属性 */
    /**
     * 本机构代号
     */
    private java.lang.String thiscode;
    /**
     * 基金全国中心代号
     */
    private java.lang.String countrycode;
    /**
     * 省局代号
     */
    private java.lang.String provincecode;
    /**
     * 地区代码
     */
    private java.lang.String areacode;
    /**
     * 市,县局代号
     */
    private java.lang.String citycode;
    /**
     * 支局机构代号
     */
    private java.lang.String branchcode;
    /**
     * 机构级别   0-全国中心 1-省级 2-地区3-市,县级 4-支局所
     */
    private java.lang.String organlevel;
    /**
     * 核算单位标志  1-核算 0-不核算
     */
    private java.lang.String checkflag;
    /**
     * 本机构名称
     */
    private java.lang.String organname;
    /**
     * 本机构地址
     */
    private java.lang.String organaddr;
    /**
     * 本机构状态  0签到 1签退 2撤销
     */
    private java.lang.String organstate;
    /**
     * IP地址
     */
    private java.lang.String organipaddr;
    /**
     * 端口号
     */
    private java.lang.String organport;
    /**
     * 维护人员姓名
     */
    private java.lang.String maintainname;
    /**
     * 维护人员联系电话
     */
    private java.lang.String maintainphone;
    /**
     * 传输密钥
     */
    private java.lang.String trankey;
    /**
     * MAC密钥
     */
    private java.lang.String mackey;
    /**
     * 次主密钥
     */
    private java.lang.String tutrankey;
    /**
     * 所属二级出纳
     */
    private java.lang.String cashiercode;
    /**
     * 二级出纳所在的机构
     */
    private java.lang.String cashorgcode;
    /**
     * 交款单编号顺序号(交款单编号17位：9位机构号+6位日期+2位顺序号
     */
    private java.lang.String cashtobankserial;
    /**
     * 当日累计下拨金额限额
     */
    private java.lang.String totalquota;
    /**
     * 中间业务交易账号顺序号
     */
    private java.lang.Long customerserial;
    /**
     * 当日累计下拨金额
     */
    private java.lang.String totalgrant;
    /**
     * 当日单笔下拨金额限额
     */
    private java.lang.String singlequota;
    /**
     * 拨款单顺序号
     */
    private java.lang.Long deliverserial;
    /**
     * 缴款单编号顺序号
     */
    private java.lang.String cashtocenterserial;
    /**
     * 凭证申请单顺序号
     */
    private java.lang.Long applyserial;
    /**
     * 凭证上缴单顺序号
     */
    private java.lang.Long turninserial;
    /**
     * 凭证发放单顺序号
     */
    private java.lang.Long grantserial;
    /**
     * 挂失申请书顺序号
     */
    private java.lang.Long lostserial;
    /**
     * 本机构收款凭证号
     */
    private java.lang.Long recserial;
    /**
     * 本机构付款凭证号
     */
    private java.lang.Long payserial;
    /**
     * 本机构转帐凭证号
     */
    private java.lang.Long turserial;
    /**
     * 合同号
     */
    private java.lang.Long impawnserial;
    /**
     * 修改时间
     */
    private java.lang.String modifydate;
    /**
     * 修改机构
     */
    private java.lang.String modifyorgan;
    /**
     * 修改柜员
     */
    private java.lang.String modifyoper;
    /**
     * 本机构的清算机构
     */
    private java.lang.String liquiorgancode;
    /**
     * 本机构清算代码
     */
    private java.lang.String liquicode;
    /**
     * 外部支票号码序号
     */
    private java.lang.String outsidechequeserial;
    /**
     * 机构属性1
     */
    private java.lang.String organflag;
    /**
     * 机构职能(01-既有管理职能又有营业职能,02-仅有管理职能,03-仅有营业职能)
     */
    private java.lang.String organfunctions;
    /**
     * 地区属性
     */
    private java.lang.String areatype;
    /**
     * 上级机构
     */
    private java.lang.String uporgan;

    /** 最后修改时间戳||最后修改时间戳 */
    @EntityParaAnno(zhName="最后修改时间戳")
    private java.lang.String lastModStamp;
    /** 一级机构排序 */
    @EntityParaAnno(zhName="一级机构排序")
    private java.lang.Integer leveloneorder;

    /** setter\getter方法 */
    /**
     * 本机构代号
     */
    public void setThiscode(java.lang.String thiscode) {
        this.thiscode = thiscode == null ? null : thiscode.trim();
    }

    public java.lang.String getThiscode() {
        return this.thiscode;
    }

    /**
     * 基金全国中心代号
     */
    public void setCountrycode(java.lang.String countrycode) {
        this.countrycode = countrycode == null ? null : countrycode.trim();
    }

    public java.lang.String getCountrycode() {
        return this.countrycode;
    }

    /**
     * 省局代号
     */
    public void setProvincecode(java.lang.String provincecode) {
        this.provincecode = provincecode == null ? null : provincecode.trim();
    }

    public java.lang.String getProvincecode() {
        return this.provincecode;
    }

    /**
     * 地区代码
     */
    public void setAreacode(java.lang.String areacode) {
        this.areacode = areacode == null ? null : areacode.trim();
    }

    public java.lang.String getAreacode() {
        return this.areacode;
    }

    /**
     * 市,县局代号
     */
    public void setCitycode(java.lang.String citycode) {
        this.citycode = citycode == null ? null : citycode.trim();
    }

    public java.lang.String getCitycode() {
        return this.citycode;
    }

    /**
     * 支局机构代号
     */
    public void setBranchcode(java.lang.String branchcode) {
        this.branchcode = branchcode == null ? null : branchcode.trim();
    }

    public java.lang.String getBranchcode() {
        return this.branchcode;
    }

    /**
     * 机构级别   0-全国中心 1-省级 2-地区3-市,县级 4-支局所
     */
    public void setOrganlevel(java.lang.String organlevel) {
        this.organlevel = organlevel == null ? null : organlevel.trim();
    }

    public java.lang.String getOrganlevel() {
        return this.organlevel;
    }

    /**
     * 核算单位标志  1-核算 0-不核算
     */
    public void setCheckflag(java.lang.String checkflag) {
        this.checkflag = checkflag == null ? null : checkflag.trim();
    }

    public java.lang.String getCheckflag() {
        return this.checkflag;
    }

    /**
     * 本机构名称
     */
    public void setOrganname(java.lang.String organname) {
        this.organname = organname == null ? null : organname.trim();
    }

    public java.lang.String getOrganname() {
        return this.organname;
    }

    /**
     * 本机构地址
     */
    public void setOrganaddr(java.lang.String organaddr) {
        this.organaddr = organaddr == null ? null : organaddr.trim();
    }

    public java.lang.String getOrganaddr() {
        return this.organaddr;
    }

    /**
     * 本机构状态  0签到 1签退 2撤销
     */
    public void setOrganstate(java.lang.String organstate) {
        this.organstate = organstate == null ? null : organstate.trim();
    }

    public java.lang.String getOrganstate() {
        return this.organstate;
    }

    /**
     * IP地址
     */
    public void setOrganipaddr(java.lang.String organipaddr) {
        this.organipaddr = organipaddr == null ? null : organipaddr.trim();
    }

    public java.lang.String getOrganipaddr() {
        return this.organipaddr;
    }

    /**
     * 端口号
     */
    public void setOrganport(java.lang.String organport) {
        this.organport = organport == null ? null : organport.trim();
    }

    public java.lang.String getOrganport() {
        return this.organport;
    }

    /**
     * 维护人员姓名
     */
    public void setMaintainname(java.lang.String maintainname) {
        this.maintainname = maintainname == null ? null : maintainname.trim();
    }

    public java.lang.String getMaintainname() {
        return this.maintainname;
    }

    /**
     * 维护人员联系电话
     */
    public void setMaintainphone(java.lang.String maintainphone) {
        this.maintainphone = maintainphone == null ? null : maintainphone.trim();
    }

    public java.lang.String getMaintainphone() {
        return this.maintainphone;
    }

    /**
     * 传输密钥
     */
    public void setTrankey(java.lang.String trankey) {
        this.trankey = trankey == null ? null : trankey.trim();
    }

    public java.lang.String getTrankey() {
        return this.trankey;
    }

    /**
     * MAC密钥
     */
    public void setMackey(java.lang.String mackey) {
        this.mackey = mackey == null ? null : mackey.trim();
    }

    public java.lang.String getMackey() {
        return this.mackey;
    }

    /**
     * 次主密钥
     */
    public void setTutrankey(java.lang.String tutrankey) {
        this.tutrankey = tutrankey == null ? null : tutrankey.trim();
    }

    public java.lang.String getTutrankey() {
        return this.tutrankey;
    }

    /**
     * 所属二级出纳
     */
    public void setCashiercode(java.lang.String cashiercode) {
        this.cashiercode = cashiercode == null ? null : cashiercode.trim();
    }

    public java.lang.String getCashiercode() {
        return this.cashiercode;
    }

    /**
     * 二级出纳所在的机构
     */
    public void setCashorgcode(java.lang.String cashorgcode) {
        this.cashorgcode = cashorgcode == null ? null : cashorgcode.trim();
    }

    public java.lang.String getCashorgcode() {
        return this.cashorgcode;
    }

    /**
     * 交款单编号顺序号(交款单编号17位：9位机构号+6位日期+2位顺序号
     */
    public void setCashtobankserial(java.lang.String cashtobankserial) {
        this.cashtobankserial = cashtobankserial == null ? null : cashtobankserial.trim();
    }

    public java.lang.String getCashtobankserial() {
        return this.cashtobankserial;
    }

    /**
     * 当日累计下拨金额限额
     */
    public void setTotalquota(java.lang.String totalquota) {
        this.totalquota = totalquota == null ? null : totalquota.trim();
    }

    public java.lang.String getTotalquota() {
        return this.totalquota;
    }

    /**
     * 中间业务交易账号顺序号
     */
    public void setCustomerserial(java.lang.Long customerserial) {
        this.customerserial = customerserial;
    }

    public java.lang.Long getCustomerserial() {
        return this.customerserial;
    }

    /**
     * 当日累计下拨金额
     */
    public void setTotalgrant(java.lang.String totalgrant) {
        this.totalgrant = totalgrant == null ? null : totalgrant.trim();
    }

    public java.lang.String getTotalgrant() {
        return this.totalgrant;
    }

    /**
     * 当日单笔下拨金额限额
     */
    public void setSinglequota(java.lang.String singlequota) {
        this.singlequota = singlequota == null ? null : singlequota.trim();
    }

    public java.lang.String getSinglequota() {
        return this.singlequota;
    }

    /**
     * 拨款单顺序号
     */
    public void setDeliverserial(java.lang.Long deliverserial) {
        this.deliverserial = deliverserial;
    }

    public java.lang.Long getDeliverserial() {
        return this.deliverserial;
    }

    /**
     * 缴款单编号顺序号
     */
    public void setCashtocenterserial(java.lang.String cashtocenterserial) {
        this.cashtocenterserial = cashtocenterserial == null ? null : cashtocenterserial.trim();
    }

    public java.lang.String getCashtocenterserial() {
        return this.cashtocenterserial;
    }

    /**
     * 凭证申请单顺序号
     */
    public void setApplyserial(java.lang.Long applyserial) {
        this.applyserial = applyserial;
    }

    public java.lang.Long getApplyserial() {
        return this.applyserial;
    }

    /**
     * 凭证上缴单顺序号
     */
    public void setTurninserial(java.lang.Long turninserial) {
        this.turninserial = turninserial;
    }

    public java.lang.Long getTurninserial() {
        return this.turninserial;
    }

    /**
     * 凭证发放单顺序号
     */
    public void setGrantserial(java.lang.Long grantserial) {
        this.grantserial = grantserial;
    }

    public java.lang.Long getGrantserial() {
        return this.grantserial;
    }

    /**
     * 挂失申请书顺序号
     */
    public void setLostserial(java.lang.Long lostserial) {
        this.lostserial = lostserial;
    }

    public java.lang.Long getLostserial() {
        return this.lostserial;
    }

    /**
     * 本机构收款凭证号
     */
    public void setRecserial(java.lang.Long recserial) {
        this.recserial = recserial;
    }

    public java.lang.Long getRecserial() {
        return this.recserial;
    }

    /**
     * 本机构付款凭证号
     */
    public void setPayserial(java.lang.Long payserial) {
        this.payserial = payserial;
    }

    public java.lang.Long getPayserial() {
        return this.payserial;
    }

    /**
     * 本机构转帐凭证号
     */
    public void setTurserial(java.lang.Long turserial) {
        this.turserial = turserial;
    }

    public java.lang.Long getTurserial() {
        return this.turserial;
    }

    /**
     * 合同号
     */
    public void setImpawnserial(java.lang.Long impawnserial) {
        this.impawnserial = impawnserial;
    }

    public java.lang.Long getImpawnserial() {
        return this.impawnserial;
    }

    /**
     * 修改时间
     */
    public void setModifydate(java.lang.String modifydate) {
        this.modifydate = modifydate == null ? null : modifydate.trim();
    }

    public java.lang.String getModifydate() {
        return this.modifydate;
    }

    /**
     * 修改机构
     */
    public void setModifyorgan(java.lang.String modifyorgan) {
        this.modifyorgan = modifyorgan == null ? null : modifyorgan.trim();
    }

    public java.lang.String getModifyorgan() {
        return this.modifyorgan;
    }

    /**
     * 修改柜员
     */
    public void setModifyoper(java.lang.String modifyoper) {
        this.modifyoper = modifyoper == null ? null : modifyoper.trim();
    }

    public java.lang.String getModifyoper() {
        return this.modifyoper;
    }

    /**
     * 本机构的清算机构
     */
    public void setLiquiorgancode(java.lang.String liquiorgancode) {
        this.liquiorgancode = liquiorgancode == null ? null : liquiorgancode.trim();
    }

    public java.lang.String getLiquiorgancode() {
        return this.liquiorgancode;
    }

    /**
     * 本机构清算代码
     */
    public void setLiquicode(java.lang.String liquicode) {
        this.liquicode = liquicode == null ? null : liquicode.trim();
    }

    public java.lang.String getLiquicode() {
        return this.liquicode;
    }

    /**
     * 外部支票号码序号
     */
    public void setOutsidechequeserial(java.lang.String outsidechequeserial) {
        this.outsidechequeserial = outsidechequeserial == null ? null : outsidechequeserial.trim();
    }

    public java.lang.String getOutsidechequeserial() {
        return this.outsidechequeserial;
    }

    /**
     * 机构属性1
     */
    public void setOrganflag(java.lang.String organflag) {
        this.organflag = organflag == null ? null : organflag.trim();
    }

    public java.lang.String getOrganflag() {
        return this.organflag;
    }

    /**
     * 机构职能(01-既有管理职能又有营业职能,02-仅有管理职能,03-仅有营业职能)
     */
    public void setOrganfunctions(java.lang.String organfunctions) {
        this.organfunctions = organfunctions == null ? null : organfunctions.trim();
    }

    public java.lang.String getOrganfunctions() {
        return this.organfunctions;
    }

    /**
     * 地区属性
     */
    public void setAreatype(java.lang.String areatype) {
        this.areatype = areatype == null ? null : areatype.trim();
    }

    public java.lang.String getAreatype() {
        return this.areatype;
    }

    /**
     * 上级机构
     */
    public void setUporgan(java.lang.String uporgan) {
        this.uporgan = uporgan == null ? null : uporgan.trim();
    }

    public java.lang.String getUporgan() {
        return this.uporgan;
    }


    public String getLastModStamp() {
        return lastModStamp;
    }

    public void setLastModStamp(String lastModStamp) {
        this.lastModStamp = lastModStamp;
    }

    public Integer getLeveloneorder() {
        return leveloneorder;
    }

    public void setLeveloneorder(Integer leveloneorder) {
        this.leveloneorder = leveloneorder;
    }
}