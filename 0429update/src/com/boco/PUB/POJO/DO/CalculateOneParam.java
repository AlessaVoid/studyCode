package com.boco.PUB.POJO.DO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: Liujinbo
 * @Date: 2020/2/27
 * @Description: ���� ģʽһ ������
 */
public class CalculateOneParam implements Serializable {

    /*�¶Ȳ���*/
    public static final BigDecimal CALCULATE_MONTH = new BigDecimal("1");
    /*��Ȳ���*/
    public static final BigDecimal CALCULATE_YEAR = new BigDecimal("2");


    /*��ָ��*/
    public static final BigDecimal MAJOR_INDEX = new BigDecimal("1");
    /*��ָ��*/
    public static final BigDecimal SECOND_INDEX = new BigDecimal("2");
    /*����ָ��*/
    public static final BigDecimal OTHER_INDEX = new BigDecimal("4");


    /*���������*/
    public static final BigDecimal DEPOSIT = new BigDecimal("1");
    /*�ṹ�Ż���*/
    public static final BigDecimal STRUCT = new BigDecimal("2");
    /*�г�������*/
    public static final BigDecimal MARKET = new BigDecimal("4");
    /*��ֵ������*/
    public static final BigDecimal BENEFIT = new BigDecimal("8");
    /*�����*/
    public static final BigDecimal CSTWO = new BigDecimal("16");




}
