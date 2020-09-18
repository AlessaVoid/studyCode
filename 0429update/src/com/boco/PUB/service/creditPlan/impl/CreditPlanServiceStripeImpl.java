package com.boco.PUB.service.creditPlan.impl;

import com.boco.PUB.service.creditPlan.CreditPlanServiceStripe;
import com.boco.PUB.service.loanPlan.TbPlanService;
import com.boco.SYS.base.GenericService;
import com.boco.SYS.entity.TbPlan;
import com.boco.SYS.entity.TbPlanDetail;
import com.boco.SYS.entity.TbReqDetail;
import com.boco.SYS.mapper.LoanPlanDetailMapper;
import com.boco.SYS.mapper.TbPlanDetailMapper;
import com.boco.SYS.mapper.TbPlanMapper;
import com.boco.SYS.util.BocoUtils;
import com.boco.TONY.biz.loancomb.service.IWebLoanCombService;
import com.boco.TONY.common.PlainResult;
import com.boco.TONY.enums.LoanStateEnums;
import com.boco.TONY.utils.IDGeneratorUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 信贷计划Service
 * @Author zhuhongjiang
 * @Date 2019/11/29 下午2:23
 **/
@Service
public class CreditPlanServiceStripeImpl extends GenericService<TbPlan, String> implements CreditPlanServiceStripe {

    public static Logger logger = Logger.getLogger(CreditPlanServiceStripeImpl.class);

    @Autowired
    private TbPlanMapper tbPlanMapper;
    @Autowired
    private TbPlanDetailMapper tbPlanDetailMapper;
    @Autowired
    private LoanPlanDetailMapper loanPlanDetailMapper;
    @Autowired
    private IWebLoanCombService loanCombService;
    @Autowired
    private TbPlanService tbPlanService;

    /**
     * 录入信贷计划-分页查询列表页数据
     * @Author zhuhongjiang
     * @param paramMap
     * @return
     */
    @Override
    public List<Map<String, Object>> selectListByPage(Map<String, Object> paramMap) {

        List<Map<String, Object>> maps = tbPlanMapper.selectListByPage(paramMap);
        for (Map<String, Object> map : maps) {
            map.put("planincrement", new BigDecimal(map.get("planincrement").toString()).divide(new BigDecimal("10000")));
            map.put("planrealincrement", new BigDecimal(map.get("planrealincrement").toString()).divide(new BigDecimal("10000")));
        }

        return maps;
    }

    /**
     * 删除信贷计划
     * @Author zhuhongjiang
     * @param planId
     * @return
     */
    @Override
    public PlainResult<String> deleteCreditPlan(String planId) {
        PlainResult<String> result = new PlainResult<>();
        try {
            tbPlanMapper.deleteLoanPlanInfo(planId);
            loanPlanDetailMapper.deleteLoanPlanDetail(planId);
        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "delete credit plan.");
        }
        return result.success("delete", "delete credit plan success.");
    }



    /**
     * 新增信贷计划
     * @param request
     * @param operCode
     * @param organCode
     * @param organlevel
     * @param uporgan
     * @return
     */
    @Override
    public PlainResult<String> addCreditPlan(HttpServletRequest request, String operCode, String organCode, Integer planType, String organlevel, String uporgan) {
        PlainResult<String> result = new PlainResult<>();

        try {
            String planDetailStr = request.getParameter("planDetail");
            String planMonth = request.getParameter("planMonth");
            String planUnit = request.getParameter("planUnit");
            String increment = request.getParameter("increment");
            String amountCount = request.getParameter("amountCount");

            //校验特殊字段非空
            if(StringUtils.isEmpty(planMonth) || StringUtils.isEmpty(planUnit) || StringUtils.isEmpty(planDetailStr)|| StringUtils.isEmpty(amountCount)){
                return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "新增失败，参数不能为空");
            }

            //校验唯一性
            TbPlan tbPlanParam = new TbPlan();
            tbPlanParam.setPlanMonth(planMonth);
            tbPlanParam.setPlanOrgan(organCode);
            tbPlanParam.setPlanType(planType);
            List<TbPlan> tbPlanListCheck = tbPlanMapper.selectByAttr(tbPlanParam);
            if(CollectionUtils.isNotEmpty(tbPlanListCheck)){
                return result.error(HttpServletResponse.SC_FORBIDDEN, "所属月份条线计划已存在");
            }
            //分行 条线和计划只能制定一个
            if ("1".equals(organlevel)) {
                TbPlan tbPlanParam2 = new TbPlan();
                tbPlanParam2.setPlanMonth(planMonth);
                tbPlanParam2.setPlanOrgan(organCode);
                tbPlanParam2.setPlanType(TbPlan.PLAN);
                List<TbPlan> tbPlanListCheck2 = tbPlanMapper.selectByAttr(tbPlanParam2);
                if(CollectionUtils.isNotEmpty(tbPlanListCheck2)){
                    return result.error(HttpServletResponse.SC_FORBIDDEN, "所属月份已经制定机构计划，不能再制定条线计划");
                }
            }

            //构建计划
            String planId = IDGeneratorUtils.getSequence();
            TbPlan tbPlan = new TbPlan();
            tbPlan.setPlanId(planId);
            tbPlan.setPlanOrgan(organCode);
            tbPlan.setPlanMonth(planMonth);
            tbPlan.setPlanOper(operCode);
            tbPlan.setPlanStatus(LoanStateEnums.STATE_NEW.status);
            BigDecimal planIncrement = new BigDecimal(StringUtils.isEmpty(increment) ? "0" : increment);
            planIncrement = planIncrement.multiply(new BigDecimal("10000"));
            BigDecimal planRealIncrement = new BigDecimal(StringUtils.isEmpty(amountCount) ? "0" : amountCount);
            if ("2".equals(planUnit)) {
                planRealIncrement = planRealIncrement.multiply(new BigDecimal("10000"));
            }
            tbPlan.setPlanIncrement(planIncrement);
            tbPlan.setPlanRealIncrement(planRealIncrement);
            tbPlan.setPlanCreateTime(BocoUtils.getTime());
            tbPlan.setPlanUnit(Integer.valueOf(planUnit));
            tbPlan.setPlanUpdater(operCode);
            tbPlan.setPlanUpdateTime(BocoUtils.getTime());
            tbPlan.setPlanType(TbPlan.STRIPE);
            tbPlan.setCombLevel(TbPlan.COMB_TWO);

            //构建计划明细
            List<TbPlanDetail> tbPlanDetailList = buildTbPlanDetail(planDetailStr, tbPlan);

            //一分给二分制定计划，制定量不等于计划净增量不让制定
            if ("1".equals(organlevel)) {

                // 一分贷种级别
                int combLevelOne = tbPlan.getCombLevel();
                //总行贷种级别
                TbPlan planParam = new TbPlan();
                planParam.setPlanMonth(tbPlan.getPlanMonth());
                planParam.setPlanOrgan(uporgan);
                planParam.setPlanType(TbPlan.PLAN);
                TbPlan upPlan = tbPlanMapper.selectByAttr(planParam).get(0);
                int combLevelTotal = upPlan.getCombLevel();

                // 获取当前贷种map  相同贷种直接比较，不同贷种转换为一级贷种比较
                Map<String, BigDecimal> realUpcombIncrementMap = null;
                //总行给该机构指定的计划量   相同贷种直接比较，不同贷种转换为一级贷种比较
                Map<String, BigDecimal> upcombIncrementMap = null;

                if (combLevelTotal == 1 && combLevelOne ==1) {
                    // 总行->一级贷种  一分->一级贷种
                    realUpcombIncrementMap = tbPlanService.getPlanCombMap(tbPlanDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organCode);

                } else if (combLevelTotal == 1 && combLevelOne ==2) {
                    // 总行->一级贷种  一分->二级贷种
                    realUpcombIncrementMap = tbPlanService.getPlanCombMapAndTransCombLevel(tbPlanDetailList, combLevelOne);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organCode);

                } else if (combLevelTotal == 2 && combLevelOne ==1) {
                    // 总行->二级贷种  一分->一级贷种
                    realUpcombIncrementMap = tbPlanService.getPlanCombMap(tbPlanDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMapAndTransCombLevel(planMonth, uporgan, organCode,combLevelTotal);

                } else if (combLevelTotal == 2 && combLevelOne ==2) {
                    // 总行->二级贷种  一分->二级贷种
                    realUpcombIncrementMap = tbPlanService.getPlanCombMap(tbPlanDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organCode);

                }

                //比较计划和制定的净增量
                for (String upcombCode : upcombIncrementMap.keySet()) {
                    //如果实际制定量不等于计划净增量，插入失败
                    if (getSafeCount(realUpcombIncrementMap.get(upcombCode)).compareTo(getSafeCount(upcombIncrementMap.get(upcombCode))) != 0) {
                        HashMap<String, Object> querymap = new HashMap<>();
                        querymap.put("combcode", upcombCode);
                        List<Map<String, Object>> maps = loanCombService.selectCombBycombcode(querymap);
                        String combname = maps.get(0).get("combname").toString();
                        return result.error(HttpServletResponse.SC_REQUEST_TIMEOUT,
                                combname + "制定净增量为" + realUpcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000")) + "亿元，计划净增量为"
                                        + upcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000")) + "亿元，请调整");
                    }
                }


            }
            tbPlanMapper.insertEntity(tbPlan);

            tbPlanDetailMapper.insertBatch(tbPlanDetailList);

        } catch (Exception e) {
            e.printStackTrace();
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "新增失败");
        }
        return result.success("add", "新增成功");
    }

    /**
     * 修改信贷计划
     * @param request
     * @param operCode
     * @param organCode
     * @param organlevel
     * @param uporgan
     * @return
     */
    @Override
    public PlainResult<String> updateCreditPlan(HttpServletRequest request, String operCode, String organCode, String organlevel, String uporgan) {
        PlainResult<String> result = new PlainResult<>();

        try {
            String planDetailStr = request.getParameter("planDetail");
            String planId = request.getParameter("planId");
            String planMonth = request.getParameter("planMonth");
            String planUnit = request.getParameter("planUnit");
            String increment = request.getParameter("increment");
            String amountCount = request.getParameter("amountCount");

            //校验特殊字段非空
            if(StringUtils.isEmpty(amountCount)||StringUtils.isEmpty(planId) || StringUtils.isEmpty(planMonth) || StringUtils.isEmpty(planUnit) || StringUtils.isEmpty(planDetailStr)){
                return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "update credit plan param not to be null");
            }

            //校验唯一性(排除自身)
            TbPlan tbPlanParam = new TbPlan();
            tbPlanParam.setPlanMonth(planMonth);
            tbPlanParam.setPlanOrgan(organCode);
            tbPlanParam.setPlanType(TbPlan.STRIPE);
            List<TbPlan> tbPlanListCheck = tbPlanMapper.selectByAttr(tbPlanParam);
            if(CollectionUtils.isNotEmpty(tbPlanListCheck)){
                TbPlan tbPlanCheck = tbPlanListCheck.get(0);
                if(!planId.equals(tbPlanCheck.getPlanId())){
                    return result.error(HttpServletResponse.SC_FORBIDDEN, "update credit plan month is existed.");
                }
            }

            //构建计划
            TbPlan tbPlan = new TbPlan();
            tbPlan.setPlanId(planId);
            tbPlan.setPlanUpdater(operCode);
            tbPlan.setPlanUpdateTime(BocoUtils.getTime());
            tbPlan.setPlanCreateTime(BocoUtils.getTime());
            tbPlan.setPlanUnit(Integer.parseInt(planUnit));
            tbPlan.setPlanMonth(planMonth);
            BigDecimal planIncrement = new BigDecimal(StringUtils.isEmpty(increment) ? "0" : increment);
            planIncrement = planIncrement.multiply(new BigDecimal("10000"));
            BigDecimal planRealIncrement = new BigDecimal(StringUtils.isEmpty(amountCount) ? "0" : amountCount);
            if ("2".equals(planUnit)) {
                planRealIncrement = planRealIncrement.multiply(new BigDecimal("10000"));
            }
            tbPlan.setPlanRealIncrement(planRealIncrement);
            tbPlan.setCombLevel(TbPlan.COMB_TWO);



            //构建计划明细
            List<TbPlanDetail> tbPlanDetailList = buildTbPlanDetail(planDetailStr, tbPlan);

            //一分给二分制定计划，制定量不等于计划净增量不让制定
            if ("1".equals(organlevel)) {

                // 一分贷种级别
                int combLevelOne = tbPlan.getCombLevel();
                //总行贷种级别
                TbPlan planParam = new TbPlan();
                planParam.setPlanMonth(tbPlan.getPlanMonth());
                planParam.setPlanOrgan(uporgan);
                planParam.setPlanType(TbPlan.PLAN);
                TbPlan upPlan = tbPlanMapper.selectByAttr(planParam).get(0);
                int combLevelTotal = upPlan.getCombLevel();

                // 获取当前贷种map  相同贷种直接比较，不同贷种转换为一级贷种比较
                Map<String, BigDecimal> realUpcombIncrementMap = null;
                //总行给该机构指定的计划量   相同贷种直接比较，不同贷种转换为一级贷种比较
                Map<String, BigDecimal> upcombIncrementMap = null;

                if (combLevelTotal == 1 && combLevelOne ==1) {
                    // 总行->一级贷种  一分->一级贷种
                    realUpcombIncrementMap = tbPlanService.getPlanCombMap(tbPlanDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organCode);

                } else if (combLevelTotal == 1 && combLevelOne ==2) {
                    // 总行->一级贷种  一分->二级贷种
                    realUpcombIncrementMap = tbPlanService.getPlanCombMapAndTransCombLevel(tbPlanDetailList, combLevelOne);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organCode);

                } else if (combLevelTotal == 2 && combLevelOne ==1) {
                    // 总行->二级贷种  一分->一级贷种
                    realUpcombIncrementMap = tbPlanService.getPlanCombMap(tbPlanDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMapAndTransCombLevel(planMonth, uporgan, organCode,combLevelTotal);

                } else if (combLevelTotal == 2 && combLevelOne ==2) {
                    // 总行->二级贷种  一分->二级贷种
                    realUpcombIncrementMap = tbPlanService.getPlanCombMap(tbPlanDetailList);
                    upcombIncrementMap = tbPlanService.getUporganCombMap(planMonth, uporgan, organCode);

                }

                //比较计划和制定的净增量
                for (String upcombCode : upcombIncrementMap.keySet()) {
                    //如果实际制定量不等于计划净增量，插入失败
                    if (getSafeCount(realUpcombIncrementMap.get(upcombCode)).compareTo(getSafeCount(upcombIncrementMap.get(upcombCode))) != 0) {
                        HashMap<String, Object> querymap = new HashMap<>();
                        querymap.put("combcode", upcombCode);
                        List<Map<String, Object>> maps = loanCombService.selectCombBycombcode(querymap);
                        String combname = maps.get(0).get("combname").toString();
                        return result.error(HttpServletResponse.SC_REQUEST_TIMEOUT,
                                combname + "制定净增量为" + realUpcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000")) + "亿元，计划净增量为"
                                        + upcombIncrementMap.get(upcombCode).divide(new BigDecimal("10000")) + "亿元，请调整");
                    }
                }
            }

            tbPlanMapper.updateByPK(tbPlan);

            //更新所有明细
            tbPlanDetailMapper.deleteLoanPlanDetail(planId);
            tbPlanDetailMapper.insertBatch(tbPlanDetailList);

        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "add credit plan.");
        }
        return result.success("add", "add credit plan success.");
    }

    /*判断该月份是否存在计划*/
    @Override
    public PlainResult<String> creditPlanJudgeMonth(String organlevel, HttpServletRequest request, String organCode) {
        PlainResult<String> result = new PlainResult<>();

        try {
            String planMonth = request.getParameter("planMonth");
            //校验特殊字段非空
            if(StringUtils.isEmpty(planMonth) ){
                return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "请选择月份");
            }

            TbPlan tbPlanParam = new TbPlan();
            tbPlanParam.setPlanMonth(planMonth);
            tbPlanParam.setPlanOrgan(organCode);
            tbPlanParam.setPlanType(TbPlan.STRIPE);
            List<TbPlan> tbPlanListCheck = tbPlanMapper.selectByAttr(tbPlanParam);
            if(CollectionUtils.isNotEmpty(tbPlanListCheck)){
                return result.error(HttpServletResponse.SC_FORBIDDEN, "所属月份条线计划已存在");
            }

            if ("1".equals(organlevel)) {
                TbPlan tbPlanParam2 = new TbPlan();
                tbPlanParam2.setPlanMonth(planMonth);
                tbPlanParam2.setPlanOrgan(organCode);
                tbPlanParam2.setPlanType(TbPlan.PLAN);
                List<TbPlan> tbPlanListCheck2 = tbPlanMapper.selectByAttr(tbPlanParam2);
                if(CollectionUtils.isNotEmpty(tbPlanListCheck2)){
                    return result.error(HttpServletResponse.SC_FORBIDDEN, "所属月份已经制定机构计划，不能再制定条线计划");
                }
            }

        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "add credit plan.");
        }
        return result.success("success", "add credit plan success.");

    }

    /*判断该月份是否存在计划 -- 导入页面*/
    @Override
    public PlainResult<String> creditPlanEnterJudgeMonth(HttpServletRequest request,String organCode) {
        PlainResult<String> result = new PlainResult<>();

        try {
            String planMonth = request.getParameter("planMonth");
            //校验特殊字段非空
            if(StringUtils.isEmpty(planMonth) ){
                return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "请选择月份");
            }

            TbPlan tbPlanParam = new TbPlan();
            tbPlanParam.setPlanMonth(planMonth);
            tbPlanParam.setPlanOrgan(organCode);
            tbPlanParam.setPlanType(TbPlan.STRIPE);
            List<TbPlan> tbPlanListCheck = tbPlanMapper.selectByAttr(tbPlanParam);
            if(CollectionUtils.isNotEmpty(tbPlanListCheck)){
                if (TbReqDetail.STATE_DISMISSED == tbPlanListCheck.get(0).getPlanStatus().intValue()) {
                    return result.error(HttpServletResponse.SC_FORBIDDEN, "所属月份计划已被驳回，可以导入");
                } else if (TbReqDetail.STATE_NEW == tbPlanListCheck.get(0).getPlanStatus().intValue()) {
                    return result.error(HttpServletResponse.SC_FORBIDDEN, "所属月份计划已经填写，可以导入");
                } else {
                    return result.error(HttpServletResponse.SC_FORBIDDEN, "所属月份计划正在审批中，不能导入");
                }
            }

        } catch (Exception e) {
            return result.error(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "add credit plan.");
        }
        return result.success("success", "add credit plan success.");

    }

    /**
     * 构建计划明细对象
     * @param planDetailStr
     * @param tbPlan
     * @return
     */
    public List<TbPlanDetail>  buildTbPlanDetail(String planDetailStr, TbPlan tbPlan){
        List<TbPlanDetail> tbPlanDetailList = new ArrayList<>();
        String[] planDetailArr = planDetailStr.split("&");
        for(int i = 0; i < planDetailArr.length; i++){
            String[] planDetailArr2 = planDetailArr[i].split("=");
            String[] planDetailArr3 = planDetailArr2[0].split("_");

            //eg: 11005293_x001=123 (机构_贷种=金额&机构_贷种=金额)
            String pdAmount = planDetailArr2[1];
            String pdOrgan = planDetailArr3[0];
            String pdLoanType = planDetailArr3[1];

           String planDetailId = IDGeneratorUtils.getSequence();
            TbPlanDetail tbPlanDetail = new TbPlanDetail();
            tbPlanDetail.setPdId(planDetailId);
            tbPlanDetail.setPdRefId(tbPlan.getPlanId());
            tbPlanDetail.setPdOrgan(pdOrgan);
            tbPlanDetail.setPdMonth(tbPlan.getPlanMonth());
            tbPlanDetail.setPdLoanType(pdLoanType);
            BigDecimal amount = new BigDecimal(pdAmount);
            //亿元转化为万元
            if (tbPlan.getPlanUnit() == 2) {
                amount = amount.multiply(new BigDecimal("10000"));
            }
            tbPlanDetail.setPdAmount(amount);
            tbPlanDetail.setPdUnit(tbPlan.getPlanUnit());
            tbPlanDetail.setPdCreateTime(BocoUtils.getTime());
            tbPlanDetail.setPdUpdateTime(BocoUtils.getTime());
            tbPlanDetailList.add(tbPlanDetail);
        }
        return tbPlanDetailList;
    }

    private BigDecimal getSafeCount(BigDecimal b1) {
        if (b1 == null) {
            return BigDecimal.ZERO;
        }
        return b1;
    }

    private BigDecimal getSafeCount(Object b1) {
        if (b1 == null || "".equals(b1.toString())) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(b1.toString());
    }
}
