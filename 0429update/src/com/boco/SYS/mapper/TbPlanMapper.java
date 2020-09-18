package com.boco.SYS.mapper;

import com.boco.SYS.base.GenericMapper;
import com.boco.SYS.entity.TbPlan;
import com.boco.SYS.entity.TbPlanDetail;
import com.boco.TONY.biz.loanplan.exception.TBPlanException;
import com.boco.TONY.biz.loanreq.POJO.DO.TbReqPlanInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tony
 * @describe LoanPlanMapper
 * @date 2019-09-29
 */
public interface TbPlanMapper extends GenericMapper<TbPlan, String> {

    /**
     * 插入信贷计划
     *
     * @param tbPlanDO 信贷计划信息
     */
    void insertLoanPlanInfo(TbPlan tbPlanDO);

    /**
     * 更新信贷计划
     *
     * @param tbPlanDO 信贷计划信息
     */
    void updateLoanPlanInfo(TbPlan tbPlanDO);

    /**
     * 删除信贷计划
     *
     * @param planId 信贷计划标识
     */
    void deleteLoanPlanInfo(String planId);

    /**
     * 通过信贷计划ID查询信贷计划
     *
     * @param planId 信贷计划标识
     * @return TbPlanDO
     */
    TbPlan selectLoanPlanByPlanId(String planId);
    /**
     * 通过信贷计划Month查询信贷计划
     *
     * @param planMonth 信贷计划Month
     * @return TbPlanDO
     */
    List<TbPlan>  selectLoanPlanByPlanMonth(String planMonth);

    /**
     * 查询所有信贷计划
     *
     * @return List<TbPlanDO>
     * @throws TBPlanException 异常
     */
    List<TbPlan> selectAllLoanPlanInfo(TbPlan tbPlanDO) throws TBPlanException;

    /**
     * 查询信贷,针对需求制定计划
     *
     * @return List<TbReqPlanInfo>
     */
    List<TbReqPlanInfo> selectReqPlanInfo();

    /**
     * 更新信贷计划状态
     *
     * @param tbPlanDO 信贷计划标识
     */
    void updateLoanPlanState(TbPlan tbPlanDO);
    /**
     * 更新信贷计划状态
     *
     * @param tbPlanDO 信贷计划标识
     */
    void updateLoanPlanAdjustmentState(TbPlan tbPlanDO);

    /**
     * 通过柜员查询已静制定并且已经审批完成的信贷计划
     *
     * @param planOper 信贷计划柜员
     * @return List<TbPlanDO>
     */
    List<TbPlan> selectLoanPlanByPlanOper(String planOper);


    /**
     * 联想输入-计划编号
     *
     * @param planId 计划编号
     * @return
     */
    List<String> selectByPlanId(String planId);

    /**
     * 联想输入-计划月份
     *
     * @param planMonth 计划月份
     * @return
     */
    List<String> selectByPlanMonth(String planMonth);

    /**
     * @Description 查询信贷计划详情
     * @Author liujinbo
     * @Date 2019/11/19
     * @param map
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getAuditRecordHist(Map<String, Object> map);

    /**
     * @Description 查询待审批的信贷计划
     * @Author liujinbo
     * @Date 2019/11/19
     * @param map
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getPendingAppReq(Map<String, Object> map);

    /**
     * @Description 查询已审批的信贷计划
     * @Author liujinbo
     * @Date 2019/11/20
     * @param map
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getApprovedRecord(Map<String, Object> map);


    /**
     * @Description 查询信贷计划回显  录入回显
     * @Author liujinbo
     * @Date 2019/11/20
     * @param queryMap
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getFdOrganPlanInfoListNotPlanId(HashMap<String, String> queryMap);

    /**
     * @Description 查询信贷计划列表界面
     * @Author liujinbo
     * @Date 2019/11/21
     * @param tbPlanDO
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> selectTbPlanAndTradeParam(Map<String, Object> map);

    /**
     * @Description 获取计划表的列名称
     * @Author liujinbo
     * @Date 2019/11/23
     * @param
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getPlanCol();


    /**
     * 分页查询录入列表页
     * @Author zhuhongjiang
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> selectListByPage(Map<String, Object> paramMap);

    //查询所有信贷计划
    List<Map<String, Object>> selectAll();

    /**
     * @Description 更新计划详情
     * @Author liujinbo
     * @Date 2019/12/12
     * @param tbPlanDetail
     * @Return void
     */
    void updatePlanDetail(TbPlanDetail tbPlanDetail);

    List<TbPlan> selectByMonth(TbPlan tbPlan);

    void updatePlan(TbPlan tbPlan);

    /**
     * @Description 查询分行计划净增量
     * @Author liujinbo
     * @Date 2020/1/6
     * @param planQueryMap
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> selectPlanOrganIncreament(HashMap<String, Object> planQueryMap);

    /**
     * @Description 查询该机构的上级机构给该机构制定的计划的总和
     * @Author liujinbo
     * @Date 2020/4/2
     * @param paramMap
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> selectUporganPlan(Map<String, Object> paramMap);

    /**
     * 分行查看上级机构给自己制定的计划详情
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> selectLowOrganIncrement(Map<String, Object> paramMap);
}
