package com.boco.PUB.service.creditPlan;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbPlan;
import com.boco.SYS.entity.TbPlanDetail;
import com.boco.TONY.common.PlainResult;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 信贷计划Service
 * @Author zhuhongjiang
 * @Date  下午2:18
 **/
public interface CreditPlanService extends IGenericService<TbPlan, String> {

    /**
     * 录入信贷计划-分页查询列表页数据
     * @Author zhuhongjiang
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> selectListByPage(Map<String, Object> paramMap) ;

    /**
     * 删除信贷计划
     * @param planId
     * @return
     */
    PlainResult<String> deleteCreditPlan(String planId);

    /**
     * 新增信贷计划
     * @param request
     * @param operCode
     * @param organCode
     * @param s
     * @param organlevel
     * @return
     */
    PlainResult<String> addCreditPlan(HttpServletRequest request, String operCode, String organCode, Integer planType, String organlevel, String upOrgan);

    /**
     * 修改信贷计划
     * @param request
     * @param operCode
     * @param organCode
     * @param organlevel
     * @param upOrgan
     * @return
     */
    PlainResult<String> updateCreditPlan(HttpServletRequest request, String operCode, String organCode, String organlevel, String upOrgan);

    /**
     * @Description判断该月份是否存在计划
     * @Author liujinbo
     * @Date 2019/12/13
     * @param organlevel
     * @param request
     * @Return com.boco.TONY.common.PlainResult<java.lang.String>
     */
    PlainResult<String> creditPlanJudgeMonth(String organlevel, HttpServletRequest request, String organCode);

        /**
     * @Description判断该月份是否存在计划--导入页面
     * @Author liujinbo
     * @Date 2019/12/13
     * @param request
     * @Return com.boco.TONY.common.PlainResult<java.lang.String>
     */
    PlainResult<String> creditPlanEnterJudgeMonth(HttpServletRequest request, String organCode);
}
