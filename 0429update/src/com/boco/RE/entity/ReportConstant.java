package com.boco.RE.entity;

import java.math.BigDecimal;

/**
 * ��������
 */
public class ReportConstant {


    /*���㣬�ٷֱ�*/
    /**
     * ���㵥λ  Ԫ-->��Ԫ
     */
    public static final BigDecimal MONEY_NUM = new BigDecimal("10000");
    /**
     * �ٷֱȻ���
     */
    public static final BigDecimal RATIO_NUM = new BigDecimal("100");

    /*-------------�������------------*/
    /**
     * ���ֱ���--��ŷ��� 2,3��
     */
    public static final String COMB_CODE_CFFY2 = "cffy2,cffy-z,cffz-1,cffz-2,tyjk2";
    /**
     * ���ֱ���--����ʵ�����
     */
    public static final String COMB_CODE_QTSTDK1 = "qtstdk1";




    /*---------���νṹ��parentId���-------*/
    /**
     * �ص���parentId
     */
    public static final String KEY_ORGAN = "keyOrgan";
    /**
     * ����parentId
     */
    public static final String AREA = "area";
    /**
     * ��ҵParentId
     */
    public static final String INDUSTRY = "industry";
    /**
     * ͬҵParentId
     */
    public static final String BANKINDUSTRY = "bankindustry";
    /**
     * ����ParentId
     */
    public static final String AREAINDUSTRY = "areaindustry";



    /*----------���νṹlevel--------*/
    /**
     * ���νṹ���� 1
     */
    public static final int TREE_LEVEL_ONE = 1;
    /**
     * ���νṹ���� 2
     */
    public static final int TREE_LEVEL_TWO = 2;


    /*--------��������- 1-�� 2-�� 3-�� 4-��---*/
    /**
     * ��������- 1-��
     */
    public static final String CYCEL_YEAR = "1";
    /**
     * ��������-  2-��
     */
    public static final String CYCEL_SEASON = "2";
    /**
     * ��������- 3-��
     */
    public static final String CYCEL_MONTH = "3";
    /**
     * ��������- 4-��
     */
    public static final String CYCEL_DAY = "4";



    /*--------����������� 1 ȫ����2�ٲ�ţ�4 ʵ��---*/
    /**
     * ����������� 1 ȫ��
     */
    public static final String combType_1 = "1";
    /**
     * ����������� 2�ٲ��
     */
    public static final String combType_2 = "2";
    /**
     * ����������� 4 ʵ��
     */
    public static final String combType_4 = "4";


    /*--------����ά�� 1-����-�������򻮷� 2-����-����ͬҵ���� 3-����-���񿼺˻��� 8-���� 16-�ص������---*/
    /**
     * ����ά�� 1-����-�������򻮷�
     */
    public static final String DIMENSION_AREA_1 = "1";
    /**
     * ����ά�� 2-����-����ͬҵ����
     */
    public static final String DIMENSION_AREA_2 = "2";
    /**
     * ����ά�� 3-����-���񿼺˻���
     */
    public static final String DIMENSION_AREA_3 = "3";
    /**
     * ����ά��  8-����
     */
    public static final String DIMENSION_ORGAN = "8";
    /**
     * ����ά�� 16-�ص������
     */
    public static final String DIMENSION_KEY_ORGAN = "16";


    /*--------- ������ ����----------------*/

    /**
     * 1.1���˾�Ӫ�Դ���
     */
    public static final String DATA_FLOW_COMB_11 = "grjyxdk";
    /**
     * 1.2С��ҵ
     */
    public static final String DATA_FLOW_COMB_12 = "xqy";
    /**
     * 2.1ס�����Ҵ���
     */
    public static final String DATA_FLOW_COMB_21 = "zfdk";
    /**
     * 2.2�������Ѵ���
     */
    public static final String DATA_FLOW_COMB_22 = "qitaxf2";
    /**
     * 2.3��Ӧ����ó������
     */
    public static final String DATA_FLOW_COMB_23 = "gylmyrz2";
    /**
     * 2.4��˾����
     */
    public static final String DATA_FLOW_COMB_24 = "gsdk2";
    /**
     * 3.1ת��
     */
    public static final String DATA_FLOW_COMB_31 = "zt2";
    /**
     * 3.2ֱ��
     */
    public static final String DATA_FLOW_COMB_32 = "zhit2";
    /**
     * 3.3����͢
     */
    public static final String DATA_FLOW_COMB_33 = "pjfft2";
    /**
     * 4.1�������ÿ�͸֧
     */
    public static final String DATA_FLOW_COMB_41 = "grxyktz2";
    /**
     * 4.2��ŷ���
     */
    public static final String DATA_FLOW_COMB_42 = "cffy2";


    /*------------ͬҵ �� ������-------------*/
    //�������
    public static String GXDK = "gxdk";
    //�������-���ڸ��˴���
    public static String GXDK_GRDK = "gxdk_grdk";
    //�������-���ڸ��˴���-���Ѵ���
    public static String GXDK_GRDK_GRXF = "gxdk_grdk_grxf";
    //�������-���ڹ�˾����
    public static String GXDK_GSDK = "gxdk_gsdk";
    //�������-���ڹ�˾����-��������
    public static String GXDK_GSDK_BGDK = "gxdk_gsdk_bgdk";
    //�������-Ʊ������
    public static String GXDK_PJRZ = "gxdk_pjrz";
    //�������-�Ǵ������ڻ�������
    public static String GXDK_FCKLJRJGDK = "gxdk_fckljrjgdk";
    //�������-�������
    public static String GXDK_JWDK = "gxdk_jwdk";
    //�������-������
    public static String GXDK_GXDK = "gxdk_gxdk";


    /*------------��λ-------------*/
    /**
     * 1-��Ԫ
     */
    public static String UNIT_1 = "1";
    /**
     * 2-��Ԫ
     */
    public static String UNIT_2 = "2";






}
