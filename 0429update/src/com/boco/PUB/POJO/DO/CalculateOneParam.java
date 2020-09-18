package com.boco.PUB.POJO.DO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: Liujinbo
 * @Date: 2020/2/27
 * @Description: 测算 模式一 计算类
 */
public class CalculateOneParam implements Serializable {

    /*月度测算*/
    public static final BigDecimal CALCULATE_MONTH = new BigDecimal("1");
    /*年度测算*/
    public static final BigDecimal CALCULATE_YEAR = new BigDecimal("2");


    /*主指标*/
    public static final BigDecimal MAJOR_INDEX = new BigDecimal("1");
    /*副指标*/
    public static final BigDecimal SECOND_INDEX = new BigDecimal("2");
    /*无用指标*/
    public static final BigDecimal OTHER_INDEX = new BigDecimal("4");


    /*存贷联动类*/
    public static final BigDecimal DEPOSIT = new BigDecimal("1");
    /*结构优化类*/
    public static final BigDecimal STRUCT = new BigDecimal("2");
    /*市场竞争类*/
    public static final BigDecimal MARKET = new BigDecimal("4");
    /*价值提升类*/
    public static final BigDecimal BENEFIT = new BigDecimal("8");
    /*测算二*/
    public static final BigDecimal CSTWO = new BigDecimal("16");




}
