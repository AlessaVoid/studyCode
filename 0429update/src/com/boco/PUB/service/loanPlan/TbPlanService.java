package com.boco.PUB.service.loanPlan;

import com.boco.SYS.base.IGenericService;
import com.boco.SYS.entity.TbPlan;
import com.boco.SYS.entity.TbPlanDetail;
import com.boco.SYS.entity.TbPlanadjDetail;
import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.util.Pager;
import com.boco.TONY.common.PlainResult;
import org.activiti.engine.task.Task;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: liujinbo
 * @Description:  信贷计划审批
 * @Date: 2019/11/18
 * @Version: 1.0
 */
public interface TbPlanService extends IGenericService<TbPlan, String> {

    /**
     * @Description 查询已提交的信贷计划
     * @Author liujinbo
     * @Date 2019/11/18
     * @param operCode
     * @param auditStatus
     * @param reqMonth
     * @param sessionOperInfo
     * @param sort
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String,Object>> getAuditRecordHistRecord(String operCode, String auditStatus, String reqMonth, WebOperInfo sessionOperInfo, Integer planType, String sort) throws Exception;

    /**
     * @Description 查询已审批的信贷计划
     * @Author liujinbo
     * @Date 2019/11/19
     * @param sort
     * @param operCode
     * @param auditStatus
     * @param reqMonth
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getApprovedRecord(String sort, String operCode, String auditStatus, String reqMonth, Integer planType) throws Exception;

    /**
     * @Description 启动信贷计划审批流程
     * @Author liujinbo
     * @Date 2019/11/19
     * @param planId
     * @param organCode
     * @param operCode
     * @param operName
     * @param assignee
     * @param comment
     * @Return com.boco.TONY.common.PlainResult<java.lang.String>
     */
    PlainResult<String> startLoanPlanAuditProcess(String planId, String organCode, String operCode, String operName, String assignee, String comment) throws Exception;
    /**
     * @Description 启动信贷条线审批流程
     * @Author liujinbo
     * @Date 2019/11/19
     * @param planId
     * @param organCode
     * @param operCode
     * @param operName
     * @param assignee
     * @param comment
     * @Return com.boco.TONY.common.PlainResult<java.lang.String>
     */
    PlainResult<String> startLoanplanStripeAuditProcess(String planId, String organCode, String operCode, String operName, String assignee, String comment) throws Exception;



    /**
     * @Description 查询待审批的信贷计划
     * @Author liujinbo
     * @Date 2019/11/19
     * @param sort
     * @param operCode
     * @param reqMonth
     * @param auditStatus
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getPendingAppReq(String sort, String operCode, String reqMonth, String auditStatus, Pager pager, Integer planType) throws Exception;
    /**
     * @Description 查询待审批的条线计划
     * @Author liujinbo
     * @Date 2019/11/19
     * @param sort
     * @param operCode
     * @param reqMonth
     * @param auditStatus
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getPendingPlanStripe(String sort, String operCode, String reqMonth, String auditStatus, Pager pager, Integer planType) throws Exception;


    Map<String, Object> findIsNotAgreeInfo(Task task, String custType, Map<String, Object> variables) throws Exception;

    void completeTaskAudit(String taskId, String comment, Map<String, Object> varMap, Map msgMap) throws Exception;

    void completeTaskAuditPlanStripe(String taskId, String comment, Map<String, Object> varMap, Map msgMap) throws Exception;



    /**
     * @Description 录入计划文件
     * @Author liujinbo
     * @Date 2019/12/13
     * @param file
     * @param operCode
     * @param organCode
     * @param request
     * @param organlevel
     * @param uporgan
     * @Return void
     */
    Map<String,String> enterReportPlanByMonth(MultipartFile file, String operCode, String organCode, HttpServletRequest request, String organlevel, String uporgan) throws Exception;

    /**
     * @Description 录入条线文件
     * @Author liujinbo
     * @Date 2019/12/13
     * @param file
     * @param operCode
     * @param organCode
     * @param request
     * @param organlevel
     * @param uporgan
     * @Return void
     */
    Map<String,String>  enterReportPlanStripeByMonth(MultipartFile file, String operCode, String organCode, HttpServletRequest request, String organlevel, String uporgan) throws Exception;

    /**
     * @Description 下载计划模板
     * @Author liujinbo
     * @Date 2019/12/26
     * @param request
     * @param type
     * @param response
     * @param organlevel
     * @Return javax.servlet.http.HttpServletResponse
     */
    void downloadPlanTemplate(HttpServletRequest request, String type, HttpServletResponse response, String organlevel) throws Exception;

    /**
     * @Description 维护时间计划后，更新信贷计划的计划净增量
     * @Author liujinbo
     * @Date 2020/1/2
     * @param plan  机构号planOrgan，月份planMonth,净增量 planIncrement
     * @Return void
     */
    void updatePlanAndPlanadj(TbPlan plan);

    /**
     * @Description 维护时间计划后，更新条线计划的计划净增量
     * @Author liujinbo
     * @Date 2020/1/2
     * @param plan 机构号planOrgan，月份planMonth,净增量 planIncrement
     * @Return void
     */
    void updatePlanStripeAndPlanadjStripe(TbPlan plan);

    /*超限额上传下载*/
     String uploadFile(MultipartFile file);
     HttpServletResponse downloadFile(String filePath, HttpServletResponse response);

    /**
     * 下载机构计划
     * @param response
     * @param request
     * @param organlevel
     */
    void downloadPlan(HttpServletResponse response, HttpServletRequest request, String organlevel) throws Exception;

    /**
     * 下载条线计划
     * @param response
     * @param request
     * @param organlevel
     */
    void downloadPlanStripe(HttpServletResponse response, HttpServletRequest request, String organlevel) throws Exception;

    /**
     * 获取计划贷种map  对于二级贷种处理为一级贷种   <upcombCode,amount>
     * @param tbPlanDetailList 计划详情list
     * @param combLevel list的贷种级别
     * @return <upcomb,amount>
     */
    Map<String, BigDecimal> getPlanCombMapAndTransCombLevel(List<TbPlanDetail> tbPlanDetailList, int combLevel);

    /**
     * 获取计划贷种map    list--> <upcombCode,amount>
     * @param tbPlanDetailList 计划详情list
     * @return <upcomb,amount>
     */
    Map<String, BigDecimal> getPlanCombMap(List<TbPlanDetail> tbPlanDetailList);

    /**
     * 获取总行给本机构制定的计划  <combCode,amount>  对于贷种级别不处理
     *
     * @param planMonth
     * @param upOrganCode
     * @param organCode
     * @return
     */
    Map<String, BigDecimal> getUporganCombMap(String planMonth, String upOrganCode, String organCode);

    /**
     * 获取总行给本机构制定的计划 对于二级贷种处理为一级贷种 <combCode,amount>
     *
     * @param planMonth
     * @param upOrganCode
     * @param organCode
     * @return
     */
    Map<String, BigDecimal> getUporganCombMapAndTransCombLevel(String planMonth, String upOrganCode, String organCode, int combLevel);


    /**
     * 获取计划调整贷种map  对于二级贷种处理为一级贷种   <upcombCode,amount>
     * @param tbPlanadjDetailList 计划调整详情list
     * @param combLevel list的贷种级别
     * @return <upcomb,amount>
     */
    Map<String, BigDecimal> getPlanadjCombMapAndTransCombLevel(List<TbPlanadjDetail> tbPlanadjDetailList, int combLevel);

    /**
     * 获取计划调整贷种map    list--> <upcombCode,amount>
     * @param tbPlanadjDetailList 计划调整详情list
     * @return <upcomb,amount>
     */
    Map<String, BigDecimal> getPlanadjCombMap(List<TbPlanadjDetail> tbPlanadjDetailList);

    /**
     * 分行查看上级机构给自己制定的计划详情
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> selectLowOrganIncrement(Map<String, Object> paramMap);
}
