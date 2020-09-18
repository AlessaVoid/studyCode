package com.boco.RE.util;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * 常量类
 * @Author zhuhongjiang
 * @Date 2020/1/9 下午6:12
 **/
public class Constant {

    public static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * 机构编码 - 全国
     */
    public static final String ORGAN_CODE_QG = "1";
    /**
     * 机构编码 - 总行
     */
    public static final String ORGAN_CODE_ZH = "11005293";
    /**
     * 机构编码 - 上海分行
     */
    public static final String ORGAN_CODE_SHFH = "31000017";


    //<<<<<<<<<<<<<<<<<<<<<<<<<-------- 上传类型 ------->>>>>>>>>>>>>>>>>>>>>>>>>>
    /**
     * 上传类型 - 银行业分地区导入
     */
    public static final String UPLOAD_TYPE_1 = "1";
    /**
     * 上传类型 - 银行业分机构导入
     */
    public static final String UPLOAD_TYPE_2 = "2";
    /**
     * 上传类型 - 人民币贷款日报汇总导入
     */
    public static final String UPLOAD_TYPE_3 = "3";
    /**
     * 上传类型 - 人民币贷款日报导入 - 各项贷款
     */
    public static final String UPLOAD_TYPE_4 = "4";
    /**
     * 上传类型 - 人民币贷款日报导入 - 个人贷款
     */
    public static final String UPLOAD_TYPE_5 = "5";
    /**
     * 上传类型 - 人民币贷款日报导入 - 公司贷款
     */
    public static final String UPLOAD_TYPE_6 = "6";


    //<<<<<<<<<<<<<<<<<<<<<<<<<--------  规则文件路径 ------->>>>>>>>>>>>>>>>>>>>>>>>>>
    /**
     * 规则文件路径
     */
    public static final String RULE_PATH = "libs" + File.separator + "excel" + File.separator + "rule" + File.separator;
    /**
     * 规则文件路径 - 银行业分地区导入
     */
    public static final String RULE_PATH_PSBC_RMB_BUSI = RULE_PATH + "RulePsbcRmbBusi.xlsx";
    /**
     * 规则文件路径 - 银行业分机构导入
     */
    public static final String RULE_PATH_BANK_RMB_BUSI = RULE_PATH + "RuleBankRmbBusi.xlsx";
    /**
     * 规则文件路径 - 人民币贷款日报汇总导入
     */
    public static final String RULE_PATH_PSBC_RMB_LOAN_SUM = RULE_PATH + "RulePsbcRmbLoanSum.xlsx";
    /**
     * 规则文件路径 - 人民币贷款日报导入 - 各项贷款
     */
    public static final String RULE_PATH_PSBC_RMB_LOANDAY = RULE_PATH + "RulePsbcRmbLoanDay.xlsx";
    /**
     * 规则文件路径 - 人民币贷款日报导入 - 个人贷款
     */
    public static final String RULE_PATH_PSBC_RMB_LOANDAY_GR = RULE_PATH + "RulePsbcRmbLoanDayGR.xlsx";
    /**
     * 规则文件路径 - 人民币贷款日报导入 - 公司贷款
     */
    public static final String RULE_PATH_PSBC_RMB_LOANDAY_GS = RULE_PATH + "RulePsbcRmbLoanDayGS.xlsx";

    /**
     * 上传导入-特殊处理
     * 各项贷款-个人贷款-个人消费-住房按揭贷款
     */
    public static String GXDK_GRDK_XFDK_ZFAJDK = "gxdk_grdk_grxf_zfajdk";

    /**
     * 上传导入-特殊处理
     * 各项贷款-个人贷款-个人消费
     */
    public static String GXDK_GRDK_XFDK = "gxdk_grdk_grxf";
}
