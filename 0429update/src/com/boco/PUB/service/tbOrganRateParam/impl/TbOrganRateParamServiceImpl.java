package com.boco.PUB.service.tbOrganRateParam.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boco.PUB.POJO.DO.OrganRateParamElementType;
import com.boco.PUB.service.tbOrganRateParam.TbOrganRateParamService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbOrganRateParam;
import com.boco.SYS.mapper.TbOrganRateParamMapper;
import com.boco.SYS.util.BocoUtils;
import com.boco.TONY.common.PlainResult;
import com.boco.TONY.utils.IDGeneratorUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 机构评分参数表业务服务层实现类(父类已实现增、删、改、查操作，只有特殊业务时需要自定义方法)
 *
 * <pre>
 * 修改日期            修改人      修改原因
 * 2014-10-29      杨滔      批量新建
 * </pre>
 */
@Service
public class TbOrganRateParamServiceImpl extends GenericService<TbOrganRateParam, String> implements TbOrganRateParamService {

    //有自定义方法时使用
    @Autowired
    private TbOrganRateParamMapper tbOrganRateParamMapper;

    /*评分参数列表*/
    @Override
    public List<Map<String, Object>> selectByType() {
        List<Map<String, Object>> list = tbOrganRateParamMapper.selectByType();
        return list;
    }

    @Override
    public PlainResult<String> updateOrganRateParam(HttpServletRequest request, String operCode) {
        PlainResult<String> result = new PlainResult<>();

        try {
            String paramStr = request.getParameter("paramStr");
            String elementType = request.getParameter("elementType");

            //校验特殊字段非空
            if(StringUtils.isEmpty(paramStr) || StringUtils.isEmpty(elementType)){
                return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update rateSorceParam param not to be null");
            }


            //删除评分参数明细
            tbOrganRateParamMapper.deleteByWhere("element_type = \'" + elementType + "\'");
            //构建评分参数明细
            List<TbOrganRateParam> TbOrganRateParamList = buildTbOrganRateParam(paramStr, operCode);
            //插入评分参数明细
            tbOrganRateParamMapper.insertBatch(TbOrganRateParamList);



        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "add rateSorceParam filed.");
        }
        return result.success("add", "add rateSorceParam success.");
    }

    /*构建评分参数*/
    private List<TbOrganRateParam> buildTbOrganRateParam(String paramStr, String operCode) {
        JSONArray jsonArray = JSONArray.parseArray(paramStr);
        List<TbOrganRateParam> tbOrganRateParamList = new ArrayList<>();
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            TbOrganRateParam tbOrganRateParam = new TbOrganRateParam();
            tbOrganRateParam.setId(IDGeneratorUtils.getSequence());

            String elementType = jsonObject.getString("elementType");
            tbOrganRateParam.setElementType(elementType);

            String elementTypeName = jsonObject.getString("elementTypeName");
            tbOrganRateParam.setElementTypeName(elementTypeName);

            String orderRate = getParamString(jsonObject.getString("orderRate"));
            tbOrganRateParam.setOrderRate(Integer.valueOf(orderRate));

            String targetScore = getParamString(jsonObject.getString("targetScore"));
            tbOrganRateParam.setTargetScore(new BigDecimal(targetScore));

            String minTargetScore = getParamString(jsonObject.getString("minTargetScore"));
            tbOrganRateParam.setMinTargetScore(new BigDecimal(minTargetScore));

            String maxTargetScore = getParamString(jsonObject.getString("maxTargetScore"));
            tbOrganRateParam.setMaxTargetScore(new BigDecimal(maxTargetScore));

            String low = getParamString(jsonObject.getString("low"));
            tbOrganRateParam.setLow(new BigDecimal(low));

            String high = getParamString(jsonObject.getString("high"));
            tbOrganRateParam.setHigh(new BigDecimal(high));

            String lowHighVar = getParamString(jsonObject.getString("lowHighVar"));
            tbOrganRateParam.setLowHighVar(new BigDecimal(lowHighVar));

            String varScore = getParamString(jsonObject.getString("varScore"));
            tbOrganRateParam.setVarScore(new BigDecimal(varScore));

            String directScore = getParamString(jsonObject.getString("directScore"));
            tbOrganRateParam.setDirectScore(new BigDecimal(directScore));

            String adjCount = getParamString(jsonObject.getString("adjCount"));
            tbOrganRateParam.setAdjCount(new BigDecimal(adjCount));

            String adjCountThree = getParamString(jsonObject.getString("adjCountThree"));
            tbOrganRateParam.setAdjCountThree(new BigDecimal(adjCountThree));

            String adjCountTwo = getParamString(jsonObject.getString("adjCountTwo"));
            tbOrganRateParam.setAdjCountTwo(new BigDecimal(adjCountTwo));

            String scaleThree = getParamString(jsonObject.getString("scaleThree"));
            tbOrganRateParam.setScaleThree(new BigDecimal(scaleThree));

            String scaleTwo = getParamString(jsonObject.getString("scaleTwo"));
            tbOrganRateParam.setScaleTwo(new BigDecimal(scaleTwo));

            String scoreOne = getParamString(jsonObject.getString("scoreOne"));
            tbOrganRateParam.setScoreOne(new BigDecimal(scoreOne));

            String scoreTwo = getParamString(jsonObject.getString("scoreTwo"));
            tbOrganRateParam.setScoreTwo(new BigDecimal(scoreTwo));

            String scoreThree = getParamString(jsonObject.getString("scoreThree"));
            tbOrganRateParam.setScoreThree(new BigDecimal(scoreThree));


            tbOrganRateParam.setCreateTime(BocoUtils.getTime());
            tbOrganRateParam.setUpdateTime(BocoUtils.getTime());
            tbOrganRateParam.setUpdateOper(operCode);

            tbOrganRateParamList.add(tbOrganRateParam);
        }

        return tbOrganRateParamList;
    }

    private String getParamString(String paramString) {
        if (paramString == null || "".equals(paramString.trim())) {
            return "0";
        }
        return paramString;
    }


    @Override
    public void add() {
        // 季度评分权重

        TbOrganRateParam organRateParam = new TbOrganRateParam();
        organRateParam.setId(IDGeneratorUtils.getSequence());
        organRateParam.setElementType(OrganRateParamElementType.QUARTER_WEIGHT);
        organRateParam.setElementTypeName("季度评分权重");

        organRateParam.setTargetScore(new BigDecimal("0"));
        organRateParam.setScoreOne(new BigDecimal("30"));
        organRateParam.setScoreTwo(new BigDecimal("30"));
        organRateParam.setScoreThree(new BigDecimal("40"));

        organRateParam.setCreateTime(BocoUtils.getTime());
        organRateParam.setUpdateTime(BocoUtils.getTime());


        tbOrganRateParamMapper.insertEntity(organRateParam);


    }

    //@Override
    // public void add() {
    //     // 季度评级  4条规则
    //
    //     //4分  （1--4）
    //     // 0--15%          1
    //     // 15%--50%		2
    //     // -50%--85%		3
    //     // -85%-100%		4
    //
    //
    //
    //
    //     // 0--15%          1
    //     TbOrganRateParam organRateParam = new TbOrganRateParam();
    //     organRateParam.setId(IDGeneratorUtils.getSequence());
    //     organRateParam.setElementType(OrganRateParamElementType.QUARTER_GRADE);
    //
    //     organRateParam.setTargetScore(new BigDecimal("4"));
    //     organRateParam.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam.setMaxTargetScore(new BigDecimal("4"));
    //
    //     organRateParam.setLow(new BigDecimal("0"));
    //     organRateParam.setHigh(new BigDecimal("0.15"));
    //     organRateParam.setLowHighVar(new BigDecimal("0"));
    //     organRateParam.setVarScore(new BigDecimal("0"));
    //      organRateParam.setDirectScore(new BigDecimal("1"));
    //
    //     organRateParam.setCreateTime(BocoUtils.getTime());
    //     organRateParam.setUpdateTime(BocoUtils.getTime());
    //
    //     // 15%--50%		2
    //     TbOrganRateParam organRateParam2 = new TbOrganRateParam();
    //     organRateParam2.setId(IDGeneratorUtils.getSequence());
    //     organRateParam2.setElementType(OrganRateParamElementType.QUARTER_GRADE);
    //
    //     organRateParam2.setTargetScore(new BigDecimal("4"));
    //     organRateParam2.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam2.setMaxTargetScore(new BigDecimal("4"));
    //
    //     organRateParam2.setLow(new BigDecimal("0.15"));
    //     organRateParam2.setHigh(new BigDecimal("0.5"));
    //     organRateParam2.setLowHighVar(new BigDecimal("0"));
    //     organRateParam2.setVarScore(new BigDecimal("0"));
    //     organRateParam2.setDirectScore(new BigDecimal("2"));
    //
    //     organRateParam2.setCreateTime(BocoUtils.getTime());
    //     organRateParam2.setUpdateTime(BocoUtils.getTime());
    //
    //     // -50%--85%		3
    //     TbOrganRateParam organRateParam3 = new TbOrganRateParam();
    //     organRateParam3.setId(IDGeneratorUtils.getSequence());
    //     organRateParam3.setElementType(OrganRateParamElementType.QUARTER_GRADE);
    //
    //     organRateParam3.setTargetScore(new BigDecimal("4"));
    //     organRateParam3.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam3.setMaxTargetScore(new BigDecimal("4"));
    //
    //     organRateParam3.setLow(new BigDecimal("0.5"));
    //     organRateParam3.setHigh(new BigDecimal("0.85"));
    //     organRateParam3.setLowHighVar(new BigDecimal("0"));
    //     organRateParam3.setVarScore(new BigDecimal("0"));
    //     organRateParam3.setDirectScore(new BigDecimal("3"));
    //
    //     organRateParam3.setCreateTime(BocoUtils.getTime());
    //     organRateParam3.setUpdateTime(BocoUtils.getTime());
    //
    //     // -85%-100%		4
    //
    //     TbOrganRateParam organRateParam4 = new TbOrganRateParam();
    //     organRateParam4.setId(IDGeneratorUtils.getSequence());
    //     organRateParam4.setElementType(OrganRateParamElementType.QUARTER_GRADE);
    //
    //     organRateParam4.setTargetScore(new BigDecimal("4"));
    //     organRateParam4.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam4.setMaxTargetScore(new BigDecimal("4"));
    //
    //     organRateParam4.setLow(new BigDecimal("0.85"));
    //     organRateParam4.setHigh(new BigDecimal("1"));
    //     organRateParam4.setLowHighVar(new BigDecimal("0"));
    //     organRateParam4.setVarScore(new BigDecimal("0"));
    //     organRateParam4.setDirectScore(new BigDecimal("4"));
    //
    //     organRateParam4.setCreateTime(BocoUtils.getTime());
    //     organRateParam4.setUpdateTime(BocoUtils.getTime());
    //
    //
    //     tbOrganRateParamMapper.insertEntity(organRateParam);
    //     tbOrganRateParamMapper.insertEntity(organRateParam2);
    //     tbOrganRateParamMapper.insertEntity(organRateParam3);
    //     tbOrganRateParamMapper.insertEntity(organRateParam4);
    //
    //
    //
    // }




    //@Override
    //public void add() {
    //    // 季度评分 - 均未产生超规模占用费和规模闲置费和调整频次未扣分 规则参数  1条规则
    //
    //
    //    // 考评季度内均未产生超规模占用费和规模闲置费的，加15分；
    //    // 考评季度内有任意两个月未产生超规模占用费和规模闲置费的，加6分；
    //
    //    // 考评季度内“调整频次”指标均未扣分的，加12分。
    //    // 考评季度内有任意两个月“调整频次”指标未扣分的，加3分。
    //
    //
    //
    //
    //    TbOrganRateParam organRateParam = new TbOrganRateParam();
    //    organRateParam.setId(IDGeneratorUtils.getSequence());
    //    organRateParam.setElementType(OrganRateParamElementType.SCALE_AMOUNT_AND_ADJ_COUNT);
    //
    //    organRateParam.setScaleThree(new BigDecimal("15"));
    //    organRateParam.setScaleTwo(new BigDecimal("6"));
    //    organRateParam.setAdjCountThree(new BigDecimal("12"));
    //    organRateParam.setAdjCountTwo(new BigDecimal("3"));
    //
    //    organRateParam.setCreateTime(BocoUtils.getTime());
    //    organRateParam.setUpdateTime(BocoUtils.getTime());
    //
    //
    //    // -30%--20%	1%		-0.5
    //
    //
    //    tbOrganRateParamMapper.insertEntity(organRateParam);
    //
    //
    //
    //
    //}


    // @Override
    // public void add() {
    //     // 调整频次 规则参数  1条规则
    //
    //     //15分  （0--15）
    //     // 在月中统一动态调整计划通知分行后，分行每额外申请一次计划调整扣5分。最低得分为0分
    //
    //
    //
    //     // -1000%--30%	1%		-0.7
    //     TbOrganRateParam organRateParam = new TbOrganRateParam();
    //     organRateParam.setId(IDGeneratorUtils.getSequence());
    //     organRateParam.setElementType(OrganRateParamElementType.ADJ_COUNT);
    //
    //     organRateParam.setTargetScore(new BigDecimal("15"));
    //     organRateParam.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam.setMaxTargetScore(new BigDecimal("15"));
    //
    //     // organRateParam.setLow(new BigDecimal("-10"));
    //     // organRateParam.setHigh(new BigDecimal("-0.3"));
    //     // organRateParam.setLowHighVar(new BigDecimal("0.01"));
    //     organRateParam.setVarScore(new BigDecimal("-5"));
    //     // organRateParam.setDirectScore(new BigDecimal("10"));
    //
    //     organRateParam.setCreateTime(BocoUtils.getTime());
    //     organRateParam.setUpdateTime(BocoUtils.getTime());
    //
    //
    //     // -30%--20%	1%		-0.5
    //
    //
    //     tbOrganRateParamMapper.insertEntity(organRateParam);
    //
    //
    //
    //
    // }

    // @Override
    // public void add() {
    //     // 计划执行偏离度-月末月初偏离度 规则参数  7条规则
    //
    //     //15分  （0--15）
    //     // -1000%--30%	1%		-0.7
    //     // -30%--20%	1%		-0.5
    //     // -20%--10%	1%		-0.3
    //     // -10%-0%		0%		0		15
    //     // 0%-10%		1%		-0.3
    //     // 10%-20%		1%		-0.5
    //     // 20%-1000%	1%		-0.7
    //
    //
    //
    //     // -1000%--30%	1%		-0.7
    //     TbOrganRateParam organRateParam = new TbOrganRateParam();
    //     organRateParam.setId(IDGeneratorUtils.getSequence());
    //     organRateParam.setElementType(OrganRateParamElementType.PLAN_EXECUTE_DEVIATION_MONTH_INIT);
    //
    //     organRateParam.setTargetScore(new BigDecimal("15"));
    //     organRateParam.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam.setMaxTargetScore(new BigDecimal("15"));
    //
    //     organRateParam.setLow(new BigDecimal("-10"));
    //     organRateParam.setHigh(new BigDecimal("-0.3"));
    //     organRateParam.setLowHighVar(new BigDecimal("0.01"));
    //     organRateParam.setVarScore(new BigDecimal("-0.7"));
    //     // organRateParam.setDirectScore(new BigDecimal("10"));
    //
    //     organRateParam.setCreateTime(BocoUtils.getTime());
    //     organRateParam.setUpdateTime(BocoUtils.getTime());
    //
    //
    //     // -30%--20%	1%		-0.5
    //     TbOrganRateParam organRateParam2 = new TbOrganRateParam();
    //     organRateParam2.setId(IDGeneratorUtils.getSequence());
    //     organRateParam2.setElementType(OrganRateParamElementType.PLAN_EXECUTE_DEVIATION_MONTH_INIT);
    //
    //     organRateParam2.setTargetScore(new BigDecimal("15"));
    //     organRateParam2.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam2.setMaxTargetScore(new BigDecimal("15"));
    //
    //     organRateParam2.setLow(new BigDecimal("-0.3"));
    //     organRateParam2.setHigh(new BigDecimal("-0.2"));
    //     organRateParam2.setLowHighVar(new BigDecimal("0.01"));
    //     organRateParam2.setVarScore(new BigDecimal("-0.5"));
    //     // organRateParam.setDirectScore(new BigDecimal("10"));
    //
    //     organRateParam2.setCreateTime(BocoUtils.getTime());
    //     organRateParam2.setUpdateTime(BocoUtils.getTime());
    //
    //     // -20%--10%	1%		-0.3
    //     TbOrganRateParam organRateParam3 = new TbOrganRateParam();
    //     organRateParam3.setId(IDGeneratorUtils.getSequence());
    //     organRateParam3.setElementType(OrganRateParamElementType.PLAN_EXECUTE_DEVIATION_MONTH_INIT);
    //
    //     organRateParam3.setTargetScore(new BigDecimal("15"));
    //     organRateParam3.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam3.setMaxTargetScore(new BigDecimal("15"));
    //
    //     organRateParam3.setLow(new BigDecimal("-0.2"));
    //     organRateParam3.setHigh(new BigDecimal("-0.1"));
    //     organRateParam3.setLowHighVar(new BigDecimal("0.01"));
    //     organRateParam3.setVarScore(new BigDecimal("-0.3"));
    //     // organRateParam.setDirectScore(new BigDecimal("10"));
    //
    //     organRateParam3.setCreateTime(BocoUtils.getTime());
    //     organRateParam3.setUpdateTime(BocoUtils.getTime());
    //
    //     // -10%-0%		0%		0		15
    //     TbOrganRateParam organRateParam4 = new TbOrganRateParam();
    //     organRateParam4.setId(IDGeneratorUtils.getSequence());
    //     organRateParam4.setElementType(OrganRateParamElementType.PLAN_EXECUTE_DEVIATION_MONTH_INIT);
    //
    //     organRateParam4.setTargetScore(new BigDecimal("15"));
    //     organRateParam4.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam4.setMaxTargetScore(new BigDecimal("15"));
    //
    //     organRateParam4.setLow(new BigDecimal("-0.1"));
    //     organRateParam4.setHigh(new BigDecimal("0"));
    //     organRateParam4.setLowHighVar(new BigDecimal("0"));
    //     organRateParam4.setVarScore(new BigDecimal("0"));
    //     organRateParam4.setDirectScore(new BigDecimal("15"));
    //
    //     organRateParam4.setCreateTime(BocoUtils.getTime());
    //     organRateParam4.setUpdateTime(BocoUtils.getTime());
    //
    //     // 0%-10%		1%		-0.3
    //     TbOrganRateParam organRateParam5 = new TbOrganRateParam();
    //     organRateParam5.setId(IDGeneratorUtils.getSequence());
    //     organRateParam5.setElementType(OrganRateParamElementType.PLAN_EXECUTE_DEVIATION_MONTH_INIT);
    //
    //     organRateParam5.setTargetScore(new BigDecimal("15"));
    //     organRateParam5.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam5.setMaxTargetScore(new BigDecimal("15"));
    //
    //     organRateParam5.setLow(new BigDecimal("0"));
    //     organRateParam5.setHigh(new BigDecimal("0.1"));
    //     organRateParam5.setLowHighVar(new BigDecimal("0.01"));
    //     organRateParam5.setVarScore(new BigDecimal("-0.3"));
    //     // organRateParam5.setDirectScore(new BigDecimal("15"));
    //
    //     organRateParam5.setCreateTime(BocoUtils.getTime());
    //     organRateParam5.setUpdateTime(BocoUtils.getTime());
    //
    //     // 10%-20%		1%		-0.5
    //     TbOrganRateParam organRateParam6 = new TbOrganRateParam();
    //     organRateParam6.setId(IDGeneratorUtils.getSequence());
    //     organRateParam6.setElementType(OrganRateParamElementType.PLAN_EXECUTE_DEVIATION_MONTH_INIT);
    //
    //     organRateParam6.setTargetScore(new BigDecimal("15"));
    //     organRateParam6.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam6.setMaxTargetScore(new BigDecimal("15"));
    //
    //     organRateParam6.setLow(new BigDecimal("0.1"));
    //     organRateParam6.setHigh(new BigDecimal("0.2"));
    //     organRateParam6.setLowHighVar(new BigDecimal("0.01"));
    //     organRateParam6.setVarScore(new BigDecimal("-0.5"));
    //     // organRateParam5.setDirectScore(new BigDecimal("15"));
    //
    //     organRateParam6.setCreateTime(BocoUtils.getTime());
    //     organRateParam6.setUpdateTime(BocoUtils.getTime());
    //
    //     // 20%-1000%	1%		-0.7
    //     TbOrganRateParam organRateParam7 = new TbOrganRateParam();
    //     organRateParam7.setId(IDGeneratorUtils.getSequence());
    //     organRateParam7.setElementType(OrganRateParamElementType.PLAN_EXECUTE_DEVIATION_MONTH_INIT);
    //
    //     organRateParam7.setTargetScore(new BigDecimal("15"));
    //     organRateParam7.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam7.setMaxTargetScore(new BigDecimal("15"));
    //
    //     organRateParam7.setLow(new BigDecimal("0.2"));
    //     organRateParam7.setHigh(new BigDecimal("10"));
    //     organRateParam7.setLowHighVar(new BigDecimal("0.01"));
    //     organRateParam7.setVarScore(new BigDecimal("-0.7"));
    //     // organRateParam5.setDirectScore(new BigDecimal("15"));
    //
    //     organRateParam7.setCreateTime(BocoUtils.getTime());
    //     organRateParam7.setUpdateTime(BocoUtils.getTime());
    //
    //
    //
    //     tbOrganRateParamMapper.insertEntity(organRateParam);
    //     tbOrganRateParamMapper.insertEntity(organRateParam2);
    //     tbOrganRateParamMapper.insertEntity(organRateParam3);
    //     tbOrganRateParamMapper.insertEntity(organRateParam4);
    //     tbOrganRateParamMapper.insertEntity(organRateParam5);
    //     tbOrganRateParamMapper.insertEntity(organRateParam6);
    //     tbOrganRateParamMapper.insertEntity(organRateParam7);
    //
    //
    //
    // }


    // @Override
    // public void add() {
    //     // 计划执行偏离度-月末月中偏离度 规则参数  7条规则
    //
    //     //10分 （0--10）
    //     // -1000%--7%	0.5%	-1
    //     // -7%--5%		0.5%	-0.75
    //     // -5%--3%		0.5%	-0.5
    //     // -3%-0%		0%		0		10
    //     // 0%-2%		0.5%	-0.5
    //     // 2%-4%		0.5%	-0.75
    //     // 4%-1000%		0.5%	-1
    //
    //
    //
    //     // -1000%--7%	0.5%	-1
    //     TbOrganRateParam organRateParam = new TbOrganRateParam();
    //     organRateParam.setId(IDGeneratorUtils.getSequence());
    //     organRateParam.setElementType(OrganRateParamElementType.PLAN_EXECUTE_DEVIATION_MONTH_MID);
    //
    //     organRateParam.setTargetScore(new BigDecimal("10"));
    //     organRateParam.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam.setMaxTargetScore(new BigDecimal("10"));
    //
    //     organRateParam.setLow(new BigDecimal("-10"));
    //     organRateParam.setHigh(new BigDecimal("-0.07"));
    //     organRateParam.setLowHighVar(new BigDecimal("0.005"));
    //     organRateParam.setVarScore(new BigDecimal("-1"));
    //     // organRateParam.setDirectScore(new BigDecimal("10"));
    //
    //     organRateParam.setCreateTime(BocoUtils.getTime());
    //     organRateParam.setUpdateTime(BocoUtils.getTime());
    //
    //
    //     // -7%--5%		0.5%	-0.75
    //     TbOrganRateParam organRateParam2 = new TbOrganRateParam();
    //     organRateParam2.setId(IDGeneratorUtils.getSequence());
    //     organRateParam2.setElementType(OrganRateParamElementType.PLAN_EXECUTE_DEVIATION_MONTH_MID);
    //
    //     organRateParam2.setTargetScore(new BigDecimal("10"));
    //     organRateParam2.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam2.setMaxTargetScore(new BigDecimal("10"));
    //
    //     organRateParam2.setLow(new BigDecimal("-0.07"));
    //     organRateParam2.setHigh(new BigDecimal("-0.05"));
    //     organRateParam2.setLowHighVar(new BigDecimal("0.005"));
    //     organRateParam2.setVarScore(new BigDecimal("-0.75"));
    //     // organRateParam.setDirectScore(new BigDecimal("10"));
    //
    //     organRateParam2.setCreateTime(BocoUtils.getTime());
    //     organRateParam2.setUpdateTime(BocoUtils.getTime());
    //
    //     // -5%--3%		0.5%	-0.5
    //     TbOrganRateParam organRateParam3 = new TbOrganRateParam();
    //     organRateParam3.setId(IDGeneratorUtils.getSequence());
    //     organRateParam3.setElementType(OrganRateParamElementType.PLAN_EXECUTE_DEVIATION_MONTH_MID);
    //
    //     organRateParam3.setTargetScore(new BigDecimal("10"));
    //     organRateParam3.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam3.setMaxTargetScore(new BigDecimal("10"));
    //
    //     organRateParam3.setLow(new BigDecimal("-0.05"));
    //     organRateParam3.setHigh(new BigDecimal("-0.03"));
    //     organRateParam3.setLowHighVar(new BigDecimal("0.005"));
    //     organRateParam3.setVarScore(new BigDecimal("-0.5"));
    //     // organRateParam.setDirectScore(new BigDecimal("10"));
    //
    //     organRateParam3.setCreateTime(BocoUtils.getTime());
    //     organRateParam3.setUpdateTime(BocoUtils.getTime());
    //
    //     // -3%-0%		0%		0		10
    //     TbOrganRateParam organRateParam4 = new TbOrganRateParam();
    //     organRateParam4.setId(IDGeneratorUtils.getSequence());
    //     organRateParam4.setElementType(OrganRateParamElementType.PLAN_EXECUTE_DEVIATION_MONTH_MID);
    //
    //     organRateParam4.setTargetScore(new BigDecimal("10"));
    //     organRateParam4.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam4.setMaxTargetScore(new BigDecimal("10"));
    //
    //     organRateParam4.setLow(new BigDecimal("-0.03"));
    //     organRateParam4.setHigh(new BigDecimal("0"));
    //     organRateParam4.setLowHighVar(new BigDecimal("0"));
    //     organRateParam4.setVarScore(new BigDecimal("0"));
    //     organRateParam4.setDirectScore(new BigDecimal("10"));
    //
    //     organRateParam4.setCreateTime(BocoUtils.getTime());
    //     organRateParam4.setUpdateTime(BocoUtils.getTime());
    //
    //     // 0%-2%		0.5%	-0.5
    //     TbOrganRateParam organRateParam5 = new TbOrganRateParam();
    //     organRateParam5.setId(IDGeneratorUtils.getSequence());
    //     organRateParam5.setElementType(OrganRateParamElementType.PLAN_EXECUTE_DEVIATION_MONTH_MID);
    //
    //     organRateParam5.setTargetScore(new BigDecimal("10"));
    //     organRateParam5.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam5.setMaxTargetScore(new BigDecimal("10"));
    //
    //     organRateParam5.setLow(new BigDecimal("0"));
    //     organRateParam5.setHigh(new BigDecimal("0.02"));
    //     organRateParam5.setLowHighVar(new BigDecimal("0.005"));
    //     organRateParam5.setVarScore(new BigDecimal("-0.5"));
    //     // organRateParam5.setDirectScore(new BigDecimal("10"));
    //
    //     organRateParam5.setCreateTime(BocoUtils.getTime());
    //     organRateParam5.setUpdateTime(BocoUtils.getTime());
    //
    //
    //     // 2%-4%		0.5%	-0.75
    //     TbOrganRateParam organRateParam6 = new TbOrganRateParam();
    //     organRateParam6.setId(IDGeneratorUtils.getSequence());
    //     organRateParam6.setElementType(OrganRateParamElementType.PLAN_EXECUTE_DEVIATION_MONTH_MID);
    //
    //     organRateParam6.setTargetScore(new BigDecimal("10"));
    //     organRateParam6.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam6.setMaxTargetScore(new BigDecimal("10"));
    //
    //     organRateParam6.setLow(new BigDecimal("0.02"));
    //     organRateParam6.setHigh(new BigDecimal("0.04"));
    //     organRateParam6.setLowHighVar(new BigDecimal("0.005"));
    //     organRateParam6.setVarScore(new BigDecimal("-0.75"));
    //     // organRateParam5.setDirectScore(new BigDecimal("10"));
    //
    //     organRateParam6.setCreateTime(BocoUtils.getTime());
    //     organRateParam6.setUpdateTime(BocoUtils.getTime());
    //
    //     // 4%-1000%		0.5%	-1
    //     TbOrganRateParam organRateParam7 = new TbOrganRateParam();
    //     organRateParam7.setId(IDGeneratorUtils.getSequence());
    //     organRateParam7.setElementType(OrganRateParamElementType.PLAN_EXECUTE_DEVIATION_MONTH_MID);
    //
    //     organRateParam7.setTargetScore(new BigDecimal("10"));
    //     organRateParam7.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam7.setMaxTargetScore(new BigDecimal("10"));
    //
    //     organRateParam7.setLow(new BigDecimal("0.04"));
    //     organRateParam7.setHigh(new BigDecimal("10"));
    //     organRateParam7.setLowHighVar(new BigDecimal("0.005"));
    //     organRateParam7.setVarScore(new BigDecimal("-1"));
    //     // organRateParam5.setDirectScore(new BigDecimal("10"));
    //
    //     organRateParam7.setCreateTime(BocoUtils.getTime());
    //     organRateParam7.setUpdateTime(BocoUtils.getTime());
    //
    //
    //
    //
    //
    //     tbOrganRateParamMapper.insertEntity(organRateParam);
    //     tbOrganRateParamMapper.insertEntity(organRateParam2);
    //     tbOrganRateParamMapper.insertEntity(organRateParam3);
    //     tbOrganRateParamMapper.insertEntity(organRateParam4);
    //     tbOrganRateParamMapper.insertEntity(organRateParam5);
    //     tbOrganRateParamMapper.insertEntity(organRateParam6);
    //     tbOrganRateParamMapper.insertEntity(organRateParam7);
    //
    //
    //
    // }


    // @Override
    // public void add() {
    //     // 计划报送偏离度费规则参数  8条规则
    //
    //     //10分 （0--10）
    //     // -100%-40%	1%		-0.5
    //     // -40%--30%	1%		-0.3
    //     // -30%--20%	1%		-0.2
    //     // -20%-0%		0%		0		10
    //     // 0%-20%		0%		0		10
    //     // 20%-30%		1%		-0.2
    //     // 30%-40%		1%		-0.3
    //     // 40%-100%		1%		-0.5
    //
    //
    //
    //
    //     // -100%-40%	1%		-0.5
    //     TbOrganRateParam organRateParam = new TbOrganRateParam();
    //     organRateParam.setId(IDGeneratorUtils.getSequence());
    //     organRateParam.setElementType(OrganRateParamElementType.PLAN_SUBMIT_DEVIATION);
    //
    //     organRateParam.setTargetScore(new BigDecimal("10"));
    //     organRateParam.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam.setMaxTargetScore(new BigDecimal("10"));
    //
    //     organRateParam.setLow(new BigDecimal("-1"));
    //     organRateParam.setHigh(new BigDecimal("-0.4"));
    //     organRateParam.setLowHighVar(new BigDecimal("0.01"));
    //     organRateParam.setVarScore(new BigDecimal("-0.5"));
    //     // organRateParam.setDirectScore(new BigDecimal("10"));
    //
    //     organRateParam.setCreateTime(BocoUtils.getTime());
    //     organRateParam.setUpdateTime(BocoUtils.getTime());
    //
    //
    //     // -40%--30%	1%		-0.3
    //     TbOrganRateParam organRateParam2 = new TbOrganRateParam();
    //     organRateParam2.setId(IDGeneratorUtils.getSequence());
    //     organRateParam2.setElementType(OrganRateParamElementType.PLAN_SUBMIT_DEVIATION);
    //
    //     organRateParam2.setTargetScore(new BigDecimal("10"));
    //     organRateParam2.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam2.setMaxTargetScore(new BigDecimal("10"));
    //
    //     organRateParam2.setLow(new BigDecimal("-0.4"));
    //     organRateParam2.setHigh(new BigDecimal("-0.3"));
    //     organRateParam2.setLowHighVar(new BigDecimal("0.01"));
    //     organRateParam2.setVarScore(new BigDecimal("-0.3"));
    //     // organRateParam.setDirectScore(new BigDecimal("10"));
    //
    //     organRateParam2.setCreateTime(BocoUtils.getTime());
    //     organRateParam2.setUpdateTime(BocoUtils.getTime());
    //
    //     // -30%--20%	1%		-0.2
    //     TbOrganRateParam organRateParam3 = new TbOrganRateParam();
    //     organRateParam3.setId(IDGeneratorUtils.getSequence());
    //     organRateParam3.setElementType(OrganRateParamElementType.PLAN_SUBMIT_DEVIATION);
    //
    //     organRateParam3.setTargetScore(new BigDecimal("10"));
    //     organRateParam3.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam3.setMaxTargetScore(new BigDecimal("10"));
    //
    //     organRateParam3.setLow(new BigDecimal("-0.3"));
    //     organRateParam3.setHigh(new BigDecimal("-0.2"));
    //     organRateParam3.setLowHighVar(new BigDecimal("0.01"));
    //     organRateParam3.setVarScore(new BigDecimal("-0.2"));
    //     // organRateParam.setDirectScore(new BigDecimal("10"));
    //
    //     organRateParam3.setCreateTime(BocoUtils.getTime());
    //     organRateParam3.setUpdateTime(BocoUtils.getTime());
    //
    //     // -20%-0%		0%		0		10
    //     TbOrganRateParam organRateParam4 = new TbOrganRateParam();
    //     organRateParam4.setId(IDGeneratorUtils.getSequence());
    //     organRateParam4.setElementType(OrganRateParamElementType.PLAN_SUBMIT_DEVIATION);
    //
    //     organRateParam4.setTargetScore(new BigDecimal("10"));
    //     organRateParam4.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam4.setMaxTargetScore(new BigDecimal("10"));
    //
    //     organRateParam4.setLow(new BigDecimal("-0.2"));
    //     organRateParam4.setHigh(new BigDecimal("0"));
    //     organRateParam4.setLowHighVar(new BigDecimal("0"));
    //     organRateParam4.setVarScore(new BigDecimal("0"));
    //     organRateParam4.setDirectScore(new BigDecimal("10"));
    //
    //     organRateParam4.setCreateTime(BocoUtils.getTime());
    //     organRateParam4.setUpdateTime(BocoUtils.getTime());
    //
    //     // 0%-20%		0%		0		10
    //     TbOrganRateParam organRateParam5 = new TbOrganRateParam();
    //     organRateParam5.setId(IDGeneratorUtils.getSequence());
    //     organRateParam5.setElementType(OrganRateParamElementType.PLAN_SUBMIT_DEVIATION);
    //
    //     organRateParam5.setTargetScore(new BigDecimal("10"));
    //     organRateParam5.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam5.setMaxTargetScore(new BigDecimal("10"));
    //
    //     organRateParam5.setLow(new BigDecimal("0"));
    //     organRateParam5.setHigh(new BigDecimal("0.2"));
    //     organRateParam5.setLowHighVar(new BigDecimal("0"));
    //     organRateParam5.setVarScore(new BigDecimal("0"));
    //     organRateParam5.setDirectScore(new BigDecimal("10"));
    //
    //     organRateParam5.setCreateTime(BocoUtils.getTime());
    //     organRateParam5.setUpdateTime(BocoUtils.getTime());
    //
    //
    //     // 20%-30%		1%		-0.2
    //     TbOrganRateParam organRateParam6 = new TbOrganRateParam();
    //     organRateParam6.setId(IDGeneratorUtils.getSequence());
    //     organRateParam6.setElementType(OrganRateParamElementType.PLAN_SUBMIT_DEVIATION);
    //
    //     organRateParam6.setTargetScore(new BigDecimal("10"));
    //     organRateParam6.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam6.setMaxTargetScore(new BigDecimal("10"));
    //
    //     organRateParam6.setLow(new BigDecimal("0.2"));
    //     organRateParam6.setHigh(new BigDecimal("0.3"));
    //     organRateParam6.setLowHighVar(new BigDecimal("0.01"));
    //     organRateParam6.setVarScore(new BigDecimal("-0.2"));
    //     // organRateParam.setDirectScore(new BigDecimal("10"));
    //
    //     organRateParam6.setCreateTime(BocoUtils.getTime());
    //     organRateParam6.setUpdateTime(BocoUtils.getTime());
    //
    //     // 30%-40%		1%		-0.3
    //     TbOrganRateParam organRateParam7 = new TbOrganRateParam();
    //     organRateParam7.setId(IDGeneratorUtils.getSequence());
    //     organRateParam7.setElementType(OrganRateParamElementType.PLAN_SUBMIT_DEVIATION);
    //
    //     organRateParam7.setTargetScore(new BigDecimal("10"));
    //     organRateParam7.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam7.setMaxTargetScore(new BigDecimal("10"));
    //
    //     organRateParam7.setLow(new BigDecimal("0.3"));
    //     organRateParam7.setHigh(new BigDecimal("0.4"));
    //     organRateParam7.setLowHighVar(new BigDecimal("0.01"));
    //     organRateParam7.setVarScore(new BigDecimal("-0.3"));
    //     // organRateParam.setDirectScore(new BigDecimal("10"));
    //
    //     organRateParam7.setCreateTime(BocoUtils.getTime());
    //     organRateParam7.setUpdateTime(BocoUtils.getTime());
    //
    //     // 40%-100%		1%		-0.5
    //     TbOrganRateParam organRateParam8 = new TbOrganRateParam();
    //     organRateParam8.setId(IDGeneratorUtils.getSequence());
    //     organRateParam8.setElementType(OrganRateParamElementType.PLAN_SUBMIT_DEVIATION);
    //
    //     organRateParam8.setTargetScore(new BigDecimal("10"));
    //     organRateParam8.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam8.setMaxTargetScore(new BigDecimal("10"));
    //
    //     organRateParam8.setLow(new BigDecimal("0.4"));
    //     organRateParam8.setHigh(new BigDecimal("1"));
    //     organRateParam8.setLowHighVar(new BigDecimal("0.01"));
    //     organRateParam8.setVarScore(new BigDecimal("-0.5"));
    //     // organRateParam.setDirectScore(new BigDecimal("10"));
    //
    //     organRateParam8.setCreateTime(BocoUtils.getTime());
    //     organRateParam8.setUpdateTime(BocoUtils.getTime());
    //
    //
    //
    //
    //     tbOrganRateParamMapper.insertEntity(organRateParam);
    //     tbOrganRateParamMapper.insertEntity(organRateParam2);
    //     tbOrganRateParamMapper.insertEntity(organRateParam3);
    //     tbOrganRateParamMapper.insertEntity(organRateParam4);
    //     tbOrganRateParamMapper.insertEntity(organRateParam5);
    //     tbOrganRateParamMapper.insertEntity(organRateParam6);
    //     tbOrganRateParamMapper.insertEntity(organRateParam7);
    //     tbOrganRateParamMapper.insertEntity(organRateParam8);
    //
    //
    //
    // }


    // @Override
    // public void add() {
    //     // 超规模占用费和规模闲置费规则参数  5条规则
    //
    //     //10分 （0--10）
    //     // 0-0		0%		0		10
    //     // 0-5%		1%		-0.4
    //     // 5%-10%	1%		-0.6
    //     // 10%-15%	1%		-1
    //     // 15%-100%	0%		0		0
    //
    //
    //
    //     // 0-0		0%		0		10
    //     TbOrganRateParam organRateParam = new TbOrganRateParam();
    //     organRateParam.setId(IDGeneratorUtils.getSequence());
    //     organRateParam.setElementType(OrganRateParamElementType.SCALE_AMOUNT);
    //
    //     organRateParam.setTargetScore(new BigDecimal("10"));
    //     organRateParam.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam.setMaxTargetScore(new BigDecimal("10"));
    //
    //     organRateParam.setLow(new BigDecimal("0"));
    //     organRateParam.setHigh(new BigDecimal("0"));
    //     organRateParam.setLowHighVar(new BigDecimal("0"));
    //     organRateParam.setVarScore(new BigDecimal("0"));
    //     organRateParam.setDirectScore(new BigDecimal("10"));
    //
    //     organRateParam.setCreateTime(BocoUtils.getTime());
    //     organRateParam.setUpdateTime(BocoUtils.getTime());
    //
    //
    //     // 0-5%		1%		-0.4
    //     TbOrganRateParam organRateParam2 = new TbOrganRateParam();
    //     organRateParam2.setId(IDGeneratorUtils.getSequence());
    //     organRateParam2.setElementType(OrganRateParamElementType.SCALE_AMOUNT);
    //
    //     organRateParam2.setTargetScore(new BigDecimal("10"));
    //     organRateParam2.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam2.setMaxTargetScore(new BigDecimal("10"));
    //
    //     organRateParam2.setLow(new BigDecimal("0"));
    //     organRateParam2.setHigh(new BigDecimal("0.05"));
    //     organRateParam2.setLowHighVar(new BigDecimal("0.01"));
    //     organRateParam2.setVarScore(new BigDecimal("-0.4"));
    //     // organRateParam2.setDirectScore(new BigDecimal("10"));
    //
    //     organRateParam2.setCreateTime(BocoUtils.getTime());
    //     organRateParam2.setUpdateTime(BocoUtils.getTime());
    //
    //     // 5%-10%	1%		-0.6
    //     TbOrganRateParam organRateParam3 = new TbOrganRateParam();
    //     organRateParam3.setId(IDGeneratorUtils.getSequence());
    //     organRateParam3.setElementType(OrganRateParamElementType.SCALE_AMOUNT);
    //
    //     organRateParam3.setTargetScore(new BigDecimal("10"));
    //     organRateParam3.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam3.setMaxTargetScore(new BigDecimal("10"));
    //
    //     organRateParam3.setLow(new BigDecimal("0.05"));
    //     organRateParam3.setHigh(new BigDecimal("0.1"));
    //     organRateParam3.setLowHighVar(new BigDecimal("0.01"));
    //     organRateParam3.setVarScore(new BigDecimal("-0.6"));
    //     // organRateParam2.setDirectScore(new BigDecimal("10"));
    //
    //     organRateParam3.setCreateTime(BocoUtils.getTime());
    //     organRateParam3.setUpdateTime(BocoUtils.getTime());
    //
    //
    //     // 10%-15%	1%		-1
    //     TbOrganRateParam organRateParam4 = new TbOrganRateParam();
    //     organRateParam4.setId(IDGeneratorUtils.getSequence());
    //     organRateParam4.setElementType(OrganRateParamElementType.SCALE_AMOUNT);
    //
    //     organRateParam4.setTargetScore(new BigDecimal("10"));
    //     organRateParam4.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam4.setMaxTargetScore(new BigDecimal("10"));
    //
    //     organRateParam4.setLow(new BigDecimal("0.1"));
    //     organRateParam4.setHigh(new BigDecimal("0.15"));
    //     organRateParam4.setLowHighVar(new BigDecimal("0.01"));
    //     organRateParam4.setVarScore(new BigDecimal("-1"));
    //     // organRateParam2.setDirectScore(new BigDecimal("10"));
    //
    //     organRateParam4.setCreateTime(BocoUtils.getTime());
    //     organRateParam4.setUpdateTime(BocoUtils.getTime());
    //
    //
    //     // 15%-100%	0%		0		0
    //     TbOrganRateParam organRateParam5 = new TbOrganRateParam();
    //     organRateParam5.setId(IDGeneratorUtils.getSequence());
    //     organRateParam5.setElementType(OrganRateParamElementType.SCALE_AMOUNT);
    //
    //     organRateParam5.setTargetScore(new BigDecimal("10"));
    //     organRateParam5.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam5.setMaxTargetScore(new BigDecimal("10"));
    //
    //     organRateParam5.setLow(new BigDecimal("0.15"));
    //     organRateParam5.setHigh(new BigDecimal("1"));
    //     organRateParam5.setLowHighVar(new BigDecimal("0"));
    //     organRateParam5.setVarScore(new BigDecimal("0"));
    //     organRateParam5.setDirectScore(new BigDecimal("0"));
    //
    //     organRateParam5.setCreateTime(BocoUtils.getTime());
    //     organRateParam5.setUpdateTime(BocoUtils.getTime());
    //
    //
    //
    //
    //     tbOrganRateParamMapper.insertEntity(organRateParam);
    //     tbOrganRateParamMapper.insertEntity(organRateParam2);
    //     tbOrganRateParamMapper.insertEntity(organRateParam3);
    //     tbOrganRateParamMapper.insertEntity(organRateParam4);
    //     tbOrganRateParamMapper.insertEntity(organRateParam5);
    //
    //
    // }

    // @Override
    // public void add() {
    //     // 贷款结构规则参数  2条规则
    //
    //     //10分 （0--10）
    //     // -100%-0	0.5	%	-1
    //     // 0-100%	0%		0		10
    //
    //
    //     // -100%-0	0.5	%	-1
    //     TbOrganRateParam organRateParam = new TbOrganRateParam();
    //     organRateParam.setId(IDGeneratorUtils.getSequence());
    //     organRateParam.setElementType(OrganRateParamElementType.LOAN_STRUCT);
    //
    //     organRateParam.setTargetScore(new BigDecimal("10"));
    //     organRateParam.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam.setMaxTargetScore(new BigDecimal("10"));
    //
    //     organRateParam.setLow(new BigDecimal("-1"));
    //     organRateParam.setHigh(new BigDecimal("0"));
    //     organRateParam.setLowHighVar(new BigDecimal("0.005"));
    //     organRateParam.setVarScore(new BigDecimal("-1"));
    //     // organRateParam.setDirectScore(new BigDecimal("0"));
    //
    //     organRateParam.setCreateTime(BocoUtils.getTime());
    //     organRateParam.setUpdateTime(BocoUtils.getTime());
    //
    //
    //     // 0-100%	0%		0		10
    //     TbOrganRateParam organRateParam2 = new TbOrganRateParam();
    //     organRateParam2.setId(IDGeneratorUtils.getSequence());
    //     organRateParam2.setElementType(OrganRateParamElementType.LOAN_STRUCT);
    //
    //     organRateParam2.setTargetScore(new BigDecimal("10"));
    //     organRateParam2.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam2.setMaxTargetScore(new BigDecimal("10"));
    //
    //     organRateParam2.setLow(new BigDecimal("0"));
    //     organRateParam2.setHigh(new BigDecimal("1"));
    //     organRateParam2.setLowHighVar(new BigDecimal("0"));
    //     organRateParam2.setVarScore(new BigDecimal("0"));
    //     organRateParam2.setDirectScore(new BigDecimal("10"));
    //
    //     organRateParam2.setCreateTime(BocoUtils.getTime());
    //     organRateParam2.setUpdateTime(BocoUtils.getTime());
    //
    //
    //
    //     tbOrganRateParamMapper.insertEntity(organRateParam);
    //     tbOrganRateParamMapper.insertEntity(organRateParam2);
    //
    //
    // }


    // @Override
    // public void add() {
    //     // 新发生贷款利率规则参数  2条规则
    //
    //     //10分  （0--20）
    //     // -100%-0	0.01%	-0.1
    //     // 0-100%	0.01%	0.15
    //
    //
    //     // -100%-0	0.01%	-0.1
    //     TbOrganRateParam organRateParam = new TbOrganRateParam();
    //     organRateParam.setId(IDGeneratorUtils.getSequence());
    //     organRateParam.setElementType(OrganRateParamElementType.NEW_LOAN_RATIO);
    //
    //     organRateParam.setTargetScore(new BigDecimal("10"));
    //     organRateParam.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam.setMaxTargetScore(new BigDecimal("20"));
    //
    //     organRateParam.setLow(new BigDecimal("-1"));
    //     organRateParam.setHigh(new BigDecimal("0"));
    //     // organRateParam.setDirectScore(new BigDecimal("0"));
    //     organRateParam.setLowHighVar(new BigDecimal("0.0001"));
    //     organRateParam.setVarScore(new BigDecimal("-0.1"));
    //
    //     organRateParam.setCreateTime(BocoUtils.getTime());
    //     organRateParam.setUpdateTime(BocoUtils.getTime());
    //
    //
    //     // 0-100%	0.01%	0.15
    //     TbOrganRateParam organRateParam2 = new TbOrganRateParam();
    //     organRateParam2.setId(IDGeneratorUtils.getSequence());
    //     organRateParam2.setElementType(OrganRateParamElementType.NEW_LOAN_RATIO);
    //
    //     organRateParam2.setTargetScore(new BigDecimal("10"));
    //     organRateParam2.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam2.setMaxTargetScore(new BigDecimal("20"));
    //
    //     organRateParam2.setLow(new BigDecimal("0"));
    //     organRateParam2.setHigh(new BigDecimal("1"));
    //     // organRateParam.setDirectScore(new BigDecimal("0"));
    //     organRateParam2.setLowHighVar(new BigDecimal("0.0001"));
    //     organRateParam2.setVarScore(new BigDecimal("0.15"));
    //
    //     organRateParam2.setCreateTime(BocoUtils.getTime());
    //     organRateParam2.setUpdateOper(BocoUtils.getTime());
    //
    //
    //
    //     tbOrganRateParamMapper.insertEntity(organRateParam);
    //     tbOrganRateParamMapper.insertEntity(organRateParam2);
    //
    //
    // }


    // @Override
    // public void add() {
    //     // 添加自营新增存贷比规则参数  6条规则
    //
    //     //10分  （0--15）
    //     // -1000%-0		0
    //     // 0-100%		15
    //     // 100%-200%	11
    //     // 200%-300%	7
    //     // 300%-400%	3
    //     // 400%-1000%	0
    //
    //
    //     // -1000%-0		0
    //     TbOrganRateParam organRateParam = new TbOrganRateParam();
    //     organRateParam.setId(IDGeneratorUtils.getSequence());
    //     organRateParam.setElementType(OrganRateParamElementType.DEPOSIT_LOAN_RATIO);
    //
    //     organRateParam.setTargetScore(new BigDecimal("10"));
    //     organRateParam.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam.setMaxTargetScore(new BigDecimal("15"));
    //
    //     organRateParam.setLow(new BigDecimal("-10"));
    //     organRateParam.setHigh(new BigDecimal("0"));
    //     organRateParam.setDirectScore(new BigDecimal("0"));
    //
    //     organRateParam.setCreateTime(BocoUtils.getTime());
    //     organRateParam.setUpdateOper(BocoUtils.getTime());
    //
    //
    //     // 0-100%		15
    //     TbOrganRateParam organRateParam2 = new TbOrganRateParam();
    //     organRateParam2.setId(IDGeneratorUtils.getSequence());
    //     organRateParam2.setElementType(OrganRateParamElementType.DEPOSIT_LOAN_RATIO);
    //
    //     organRateParam2.setTargetScore(new BigDecimal("10"));
    //     organRateParam2.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam2.setMaxTargetScore(new BigDecimal("15"));
    //
    //     organRateParam2.setLow(new BigDecimal("0"));
    //     organRateParam2.setHigh(new BigDecimal("1"));
    //     organRateParam2.setDirectScore(new BigDecimal("15"));
    //
    //     organRateParam2.setCreateTime(BocoUtils.getTime());
    //     organRateParam2.setUpdateOper(BocoUtils.getTime());
    //
    //
    //     // 100%-200%	11
    //     TbOrganRateParam organRateParam3 = new TbOrganRateParam();
    //     organRateParam3.setId(IDGeneratorUtils.getSequence());
    //     organRateParam3.setElementType(OrganRateParamElementType.DEPOSIT_LOAN_RATIO);
    //
    //     organRateParam3.setTargetScore(new BigDecimal("10"));
    //     organRateParam3.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam3.setMaxTargetScore(new BigDecimal("15"));
    //
    //     organRateParam3.setLow(new BigDecimal("1"));
    //     organRateParam3.setHigh(new BigDecimal("2"));
    //     organRateParam3.setDirectScore(new BigDecimal("11"));
    //
    //     organRateParam3.setCreateTime(BocoUtils.getTime());
    //     organRateParam3.setUpdateOper(BocoUtils.getTime());
    //
    //     // 200%-300%	7
    //     TbOrganRateParam organRateParam4 = new TbOrganRateParam();
    //     organRateParam4.setId(IDGeneratorUtils.getSequence());
    //     organRateParam4.setElementType(OrganRateParamElementType.DEPOSIT_LOAN_RATIO);
    //
    //     organRateParam4.setTargetScore(new BigDecimal("10"));
    //     organRateParam4.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam4.setMaxTargetScore(new BigDecimal("15"));
    //
    //     organRateParam4.setLow(new BigDecimal("2"));
    //     organRateParam4.setHigh(new BigDecimal("3"));
    //     organRateParam4.setDirectScore(new BigDecimal("7"));
    //
    //     organRateParam4.setCreateTime(BocoUtils.getTime());
    //     organRateParam4.setUpdateOper(BocoUtils.getTime());
    //
    //
    //     // 300%-400%	3
    //     TbOrganRateParam organRateParam5 = new TbOrganRateParam();
    //     organRateParam5.setId(IDGeneratorUtils.getSequence());
    //     organRateParam5.setElementType(OrganRateParamElementType.DEPOSIT_LOAN_RATIO);
    //
    //     organRateParam5.setTargetScore(new BigDecimal("10"));
    //     organRateParam5.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam5.setMaxTargetScore(new BigDecimal("15"));
    //
    //     organRateParam5.setLow(new BigDecimal("3"));
    //     organRateParam5.setHigh(new BigDecimal("4"));
    //     organRateParam5.setDirectScore(new BigDecimal("3"));
    //
    //     organRateParam5.setCreateTime(BocoUtils.getTime());
    //     organRateParam5.setUpdateOper(BocoUtils.getTime());
    //
    //
    //     // 400%-1000%	0
    //     TbOrganRateParam organRateParam6 = new TbOrganRateParam();
    //     organRateParam6.setId(IDGeneratorUtils.getSequence());
    //     organRateParam6.setElementType(OrganRateParamElementType.DEPOSIT_LOAN_RATIO);
    //
    //     organRateParam6.setTargetScore(new BigDecimal("10"));
    //     organRateParam6.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam6.setMaxTargetScore(new BigDecimal("15"));
    //
    //     organRateParam6.setLow(new BigDecimal("4"));
    //     organRateParam6.setHigh(new BigDecimal("10"));
    //     organRateParam6.setDirectScore(new BigDecimal("0"));
    //
    //     organRateParam6.setCreateTime(BocoUtils.getTime());
    //     organRateParam6.setUpdateOper(BocoUtils.getTime());
    //
    //
    //
    //     tbOrganRateParamMapper.insertEntity(organRateParam);
    //     tbOrganRateParamMapper.insertEntity(organRateParam2);
    //     tbOrganRateParamMapper.insertEntity(organRateParam3);
    //     tbOrganRateParamMapper.insertEntity(organRateParam4);
    //     tbOrganRateParamMapper.insertEntity(organRateParam5);
    //     tbOrganRateParamMapper.insertEntity(organRateParam6);
    //
    // }

    // @Override
    // public void add() {
    //     // 添加不良率规则参数  2条规则
    //
    //     //10分   （0--20）
    //     // -100%-0 		0.01% 	0.4
    //     // 0-100%		0.01%	-0.4
    //
    //
    //     // -100%-0 		0.01% 	0.4
    //     TbOrganRateParam organRateParam = new TbOrganRateParam();
    //     organRateParam.setId(IDGeneratorUtils.getSequence());
    //     organRateParam.setElementType(OrganRateParamElementType.BAD_CONDITION);
    //     //目标值--最大值--最小值
    //     organRateParam.setTargetScore(new BigDecimal("10"));
    //     organRateParam.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam.setMaxTargetScore(new BigDecimal("20"));
    //
    //     //区间  区间变量  区间变量得分
    //     organRateParam.setLow(new BigDecimal("-1"));
    //     organRateParam.setHigh(new BigDecimal("0"));
    //     organRateParam.setLowHighVar(new BigDecimal("0.0001"));
    //     organRateParam.setVarScore(new BigDecimal("0.4"));
    //
    //     organRateParam.setCreateTime(BocoUtils.getTime());
    //     organRateParam.setUpdateOper(BocoUtils.getTime());
    //
    //
    //
    //     // 0-100%		0.01%	-0.4
    //     TbOrganRateParam organRateParam2 = new TbOrganRateParam();
    //     organRateParam2.setId(IDGeneratorUtils.getSequence());
    //     organRateParam2.setElementType(OrganRateParamElementType.BAD_CONDITION);
    //     //目标值--最大值--最小值
    //     organRateParam2.setTargetScore(new BigDecimal("10"));
    //     organRateParam2.setMinTargetScore(new BigDecimal("0"));
    //     organRateParam2.setMaxTargetScore(new BigDecimal("20"));
    //
    //     //区间  区间变量  区间变量得分
    //     organRateParam2.setLow(new BigDecimal("0"));
    //     organRateParam2.setHigh(new BigDecimal("1"));
    //     organRateParam2.setLowHighVar(new BigDecimal("0.0001"));
    //     organRateParam2.setVarScore(new BigDecimal("-0.4"));
    //
    //     organRateParam2.setCreateTime(BocoUtils.getTime());
    //     organRateParam2.setUpdateOper(BocoUtils.getTime());
    //
    //
    //     tbOrganRateParamMapper.insertEntity(organRateParam);
    //     tbOrganRateParamMapper.insertEntity(organRateParam2);
    //
    // }
}