package com.boco.RE.entity;

import java.math.BigDecimal;

/**
 * 报表常量类
 */
public class ReportConstant {


    /*金额换算，百分比*/
    /**
     * 金额换算单位  元-->万元
     */
    public static final BigDecimal MONEY_NUM = new BigDecimal("10000");
    /**
     * 百分比换算
     */
    public static final BigDecimal RATIO_NUM = new BigDecimal("100");

    /*-------------贷种相关------------*/
    /**
     * 贷种编码--拆放非银 2,3级
     */
    public static final String COMB_CODE_CFFY2 = "cffy2,cffy-z,cffz-1,cffz-2,tyjk2";
    /**
     * 贷种编码--其他实体贷款
     */
    public static final String COMB_CODE_QTSTDK1 = "qtstdk1";




    /*---------树形结构的parentId相关-------*/
    /**
     * 重点行parentId
     */
    public static final String KEY_ORGAN = "keyOrgan";
    /**
     * 区域parentId
     */
    public static final String AREA = "area";
    /**
     * 行业ParentId
     */
    public static final String INDUSTRY = "industry";
    /**
     * 同业ParentId
     */
    public static final String BANKINDUSTRY = "bankindustry";
    /**
     * 地区ParentId
     */
    public static final String AREAINDUSTRY = "areaindustry";



    /*----------树形结构level--------*/
    /**
     * 树形结构级别 1
     */
    public static final int TREE_LEVEL_ONE = 1;
    /**
     * 树形结构级别 2
     */
    public static final int TREE_LEVEL_TWO = 2;


    /*--------报表周期- 1-年 2-季 3-月 4-日---*/
    /**
     * 报表周期- 1-年
     */
    public static final String CYCEL_YEAR = "1";
    /**
     * 报表周期-  2-季
     */
    public static final String CYCEL_SEASON = "2";
    /**
     * 报表周期- 3-月
     */
    public static final String CYCEL_MONTH = "3";
    /**
     * 报表周期- 4-日
     */
    public static final String CYCEL_DAY = "4";



    /*--------贷种组合类型 1 全部；2少拆放；4 实体---*/
    /**
     * 贷种组合类型 1 全部
     */
    public static final String combType_1 = "1";
    /**
     * 贷种组合类型 2少拆放
     */
    public static final String combType_2 = "2";
    /**
     * 贷种组合类型 4 实体
     */
    public static final String combType_4 = "4";


    /*--------汇总维度 1-区域-地理区域划分 2-区域-银行同业划分 3-区域-财务考核划分 8-分行 16-重点城市行---*/
    /**
     * 汇总维度 1-区域-地理区域划分
     */
    public static final String DIMENSION_AREA_1 = "1";
    /**
     * 汇总维度 2-区域-银行同业划分
     */
    public static final String DIMENSION_AREA_2 = "2";
    /**
     * 汇总维度 3-区域-财务考核划分
     */
    public static final String DIMENSION_AREA_3 = "3";
    /**
     * 汇总维度  8-分行
     */
    public static final String DIMENSION_ORGAN = "8";
    /**
     * 汇总维度 16-重点城市行
     */
    public static final String DIMENSION_KEY_ORGAN = "16";


    /*--------- 流量表 贷种----------------*/

    /**
     * 1.1个人经营性贷款
     */
    public static final String DATA_FLOW_COMB_11 = "grjyxdk";
    /**
     * 1.2小企业
     */
    public static final String DATA_FLOW_COMB_12 = "xqy";
    /**
     * 2.1住房按揭贷款
     */
    public static final String DATA_FLOW_COMB_21 = "zfdk";
    /**
     * 2.2其他消费贷款
     */
    public static final String DATA_FLOW_COMB_22 = "qitaxf2";
    /**
     * 2.3供应链与贸易融资
     */
    public static final String DATA_FLOW_COMB_23 = "gylmyrz2";
    /**
     * 2.4公司贷款
     */
    public static final String DATA_FLOW_COMB_24 = "gsdk2";
    /**
     * 3.1转贴
     */
    public static final String DATA_FLOW_COMB_31 = "zt2";
    /**
     * 3.2直贴
     */
    public static final String DATA_FLOW_COMB_32 = "zhit2";
    /**
     * 3.3福费廷
     */
    public static final String DATA_FLOW_COMB_33 = "pjfft2";
    /**
     * 4.1个人信用卡透支
     */
    public static final String DATA_FLOW_COMB_41 = "grxyktz2";
    /**
     * 4.2拆放非银
     */
    public static final String DATA_FLOW_COMB_42 = "cffy2";


    /*------------同业 、 地区表-------------*/
    //各项贷款
    public static String GXDK = "gxdk";
    //各项贷款-境内个人贷款
    public static String GXDK_GRDK = "gxdk_grdk";
    //各项贷款-境内个人贷款-消费贷款
    public static String GXDK_GRDK_GRXF = "gxdk_grdk_grxf";
    //各项贷款-境内公司贷款
    public static String GXDK_GSDK = "gxdk_gsdk";
    //各项贷款-境内公司贷款-并购贷款
    public static String GXDK_GSDK_BGDK = "gxdk_gsdk_bgdk";
    //各项贷款-票据融资
    public static String GXDK_PJRZ = "gxdk_pjrz";
    //各项贷款-非存款类金融机构贷款
    public static String GXDK_FCKLJRJGDK = "gxdk_fckljrjgdk";
    //各项贷款-境外贷款
    public static String GXDK_JWDK = "gxdk_jwdk";
    //各项贷款-各项垫款
    public static String GXDK_GXDK = "gxdk_gxdk";


    /*------------单位-------------*/
    /**
     * 1-万元
     */
    public static String UNIT_1 = "1";
    /**
     * 2-亿元
     */
    public static String UNIT_2 = "2";






}
