package com.boco.RE.util;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * ������
 * @Author zhuhongjiang
 * @Date 2020/1/9 ����6:12
 **/
public class Constant {

    public static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * �������� - ȫ��
     */
    public static final String ORGAN_CODE_QG = "1";
    /**
     * �������� - ����
     */
    public static final String ORGAN_CODE_ZH = "11005293";
    /**
     * �������� - �Ϻ�����
     */
    public static final String ORGAN_CODE_SHFH = "31000017";


    //<<<<<<<<<<<<<<<<<<<<<<<<<-------- �ϴ����� ------->>>>>>>>>>>>>>>>>>>>>>>>>>
    /**
     * �ϴ����� - ����ҵ�ֵ�������
     */
    public static final String UPLOAD_TYPE_1 = "1";
    /**
     * �ϴ����� - ����ҵ�ֻ�������
     */
    public static final String UPLOAD_TYPE_2 = "2";
    /**
     * �ϴ����� - ����Ҵ����ձ����ܵ���
     */
    public static final String UPLOAD_TYPE_3 = "3";
    /**
     * �ϴ����� - ����Ҵ����ձ����� - �������
     */
    public static final String UPLOAD_TYPE_4 = "4";
    /**
     * �ϴ����� - ����Ҵ����ձ����� - ���˴���
     */
    public static final String UPLOAD_TYPE_5 = "5";
    /**
     * �ϴ����� - ����Ҵ����ձ����� - ��˾����
     */
    public static final String UPLOAD_TYPE_6 = "6";


    //<<<<<<<<<<<<<<<<<<<<<<<<<--------  �����ļ�·�� ------->>>>>>>>>>>>>>>>>>>>>>>>>>
    /**
     * �����ļ�·��
     */
    public static final String RULE_PATH = "libs" + File.separator + "excel" + File.separator + "rule" + File.separator;
    /**
     * �����ļ�·�� - ����ҵ�ֵ�������
     */
    public static final String RULE_PATH_PSBC_RMB_BUSI = RULE_PATH + "RulePsbcRmbBusi.xlsx";
    /**
     * �����ļ�·�� - ����ҵ�ֻ�������
     */
    public static final String RULE_PATH_BANK_RMB_BUSI = RULE_PATH + "RuleBankRmbBusi.xlsx";
    /**
     * �����ļ�·�� - ����Ҵ����ձ����ܵ���
     */
    public static final String RULE_PATH_PSBC_RMB_LOAN_SUM = RULE_PATH + "RulePsbcRmbLoanSum.xlsx";
    /**
     * �����ļ�·�� - ����Ҵ����ձ����� - �������
     */
    public static final String RULE_PATH_PSBC_RMB_LOANDAY = RULE_PATH + "RulePsbcRmbLoanDay.xlsx";
    /**
     * �����ļ�·�� - ����Ҵ����ձ����� - ���˴���
     */
    public static final String RULE_PATH_PSBC_RMB_LOANDAY_GR = RULE_PATH + "RulePsbcRmbLoanDayGR.xlsx";
    /**
     * �����ļ�·�� - ����Ҵ����ձ����� - ��˾����
     */
    public static final String RULE_PATH_PSBC_RMB_LOANDAY_GS = RULE_PATH + "RulePsbcRmbLoanDayGS.xlsx";

    /**
     * �ϴ�����-���⴦��
     * �������-���˴���-��������-ס�����Ҵ���
     */
    public static String GXDK_GRDK_XFDK_ZFAJDK = "gxdk_grdk_grxf_zfajdk";

    /**
     * �ϴ�����-���⴦��
     * �������-���˴���-��������
     */
    public static String GXDK_GRDK_XFDK = "gxdk_grdk_grxf";
}
