package com.boco.TONY.biz.loanplan.service;

import com.boco.SYS.entity.FdOrgan;
import com.boco.TONY.biz.loanplan.POJO.DO.FdOrganPlanInfo;
import com.boco.SYS.entity.TbPlan;
import com.boco.TONY.biz.loanplan.POJO.DTO.TbPlanDetailDTO;
import com.boco.TONY.biz.loanplan.exception.TBPlanException;
import com.boco.TONY.biz.loanreq.POJO.DO.TbReqPlanInfo;
import com.boco.TONY.biz.planadjust.POJO.DO.TbPlanAdjustDetailDO;
import com.boco.TONY.common.ListResult;
import com.boco.TONY.common.PlainResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 信贷计划制定业务逻辑层接口
 *
 * @author tony
 * @describe ILoanPlanService
 * @date 2019-09-29
 */
public interface ILoanPlanService {
    /**
     * 插入信贷计划
     *
     * @param request HTTP REQUEST
     * @return PlainResult
     * @throws TBPlanException 信贷需求计划异常
     */
    PlainResult<String> insertLoanPlan(HttpServletRequest request, String operCode, String organCode) throws TBPlanException;

    /**
     * 获取需求批次信息
     *
     * @return 批次信息
     */
    ListResult<TbReqPlanInfo> getAllTbReqSpInfo();

    /**
     * 更新信贷计划
     *
     * @param request HTTP
     * @return PlainResult
     * @throws TBPlanException 信贷计划异常
     */
    PlainResult<String> updateLoanPlanInfo(HttpServletRequest request, String operCode) throws TBPlanException;

    /**
     * 删除信贷计划
     *
     * @param planId planId
     * @return PlainResult
     * @throws TBPlanException 信贷计划异常
     */
    PlainResult<String> deleteLoanPlanInfo(String planId) throws TBPlanException;

    /**
     * 通过ID查询信贷计划
     *
     * @param planId 信贷Id
     * @return 查询信贷计划
     * @throws TBPlanException 信贷异常
     */
    PlainResult<TbPlan> selectLoanPlanByPlanId(String planId) throws TBPlanException;

    /**
     * 通过月份查询信贷计划
     *
     * @param planMonth 信贷Id
     * @return 查询信贷计划
     * @throws TBPlanException 信贷异常
     */
    ListResult<TbPlan> selectLoanPlanByPlanMonth(String planMonth) throws TBPlanException;

    /**
     * 查询所有信贷计划
     *
     * @return 查询信贷计划
     * @throws TBPlanException 信贷异常
     */
    Map<String, Object> selectAllLoanPlanInfo(String organCode, int pageNo, int pageSize) throws TBPlanException;

    /**
     * 查询信贷计划明细信息
     *
     * @param planId 信贷计划批次ID
     * @return 信贷计划明细
     */
    PlainResult<TbPlanDetailDTO> selectPlanDetailByPlanId(String planId);

    /**
     * 回显信贷计划详情
     *
     * @param planId 信贷计划id
     * @return 更新结果
     */
    PlainResult<String> getAdjustPlanDetailInfoByPlanId(String planId);

    /**
     * 调整信贷计划详情
     *
     * @param tbPlanAdjustDetail 信贷计划详情VO
     * @return 更新结果
     */
    PlainResult<String> adjustPlanDetailInfo(TbPlanAdjustDetailDO tbPlanAdjustDetail);

    /**
     * 初始化信贷计划
     *
     * @param planId      计划ID
     * @param fdOrganList 机构信息列表
     * @return List
     */
    List<FdOrganPlanInfo> initPlanDetailOrganInfo(String planId, List<FdOrgan> fdOrganList);

    /**
     * 初始化信贷计划调整
     *
     * @param planId      计划ID
     * @param fdOrganList 机构信息列表
     * @return List
     */
    List<FdOrganPlanInfo> initPlanDetailAdjustOrganInfo(String planId, List<FdOrgan> fdOrganList);

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
     * 根据计划id查询计划调整详情
     *
     * @param planId 计划详情
     * @return
     */
    List<TbPlanAdjustDetailDO> selectLoanPlanAdjustmentInfoById(String planId);


    /**
     * @Description 更新tbplandetail
     * @Author liujinbo
     * @Date 2019/11/20
     * @param request
     * @param operCode
     * @param thiscode
     * @Return com.boco.TONY.common.PlainResult<java.lang.String>
     */
    PlainResult<String> updateLoanPlan(HttpServletRequest request, String operCode, String organCode);

    /**
     * @Description 获取信贷计划页面回显值
     * @Author liujinbo
     * @Date 2019/11/20
     * @param planId
     * @param thiscode
     * @Return java.util.List<com.boco.TONY.biz.loanplan.POJO.DO.FdOrganPlanInfo>
     */
    List<Map<String,Object>> getFdOrganPlanInfoList(String planId, String thiscode);

    /**
     * @Description 获取信贷计划页面回显值 录入信贷计划
     * @Author liujinbo
     * @Date 2019/11/20
     * @param thiscode
     * @Return java.util.List<com.boco.TONY.biz.loanplan.POJO.DO.FdOrganPlanInfo>
     */
    List<Map<String,Object>> getFdOrganPlanInfoListNotPlanId(String thiscode);

/**
 * @Description  查询列表界面数据
 * @Author liujinbo
 * @Date 2019/11/21
 * @param organCode
 * @param pageNo
 * @param pageSize
 * @Return java.util.Map<java.lang.String,java.lang.Object>
 */
    Map<String, Object> selectTbPlanAndTradeParam(String organCode, int pageNo, int pageSize) ;
}
