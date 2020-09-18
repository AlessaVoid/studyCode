package com.boco.PUB.service.lineReqReset;

import com.boco.SYS.entity.WebOperInfo;
import com.boco.SYS.util.Pager;
import com.boco.TONY.common.PlainResult;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ILoanReqAppService
 * @Description 机构需求
 * @Author daice
 * @Date 2019/11/14 下午8:16
 * @Version 2.0
 **/
public interface ILineReqResetAppService {

    /**
     * 启动信贷需求审批流程
     *
     * @param reqId     需求批次标识
     * @param organCode 机构号
     * @param operCode  柜员号
     * @param operName  柜员名称
     * @param assignee  签收人
     * @return PlainResult
     */
    PlainResult<String> startLoanReqAuditProcess(int reqId, String organCode, String operCode, String operName, String assignee,String comment) throws Exception;

    /**
     * @Author daice
     * @Description //查询已提交信贷需求
     * @Date 下午9:32 2019/11/14
     * @Param [operCode, auditStatus, reqMonth]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    List<Map<String,Object>> getAuditRecordHistRecord(WebOperInfo sessionOperInfo, String auditStatus) throws Exception;

    /**
     * @Author daice
     * @Description //查询待审批的信贷需求
     * @Date 下午2:17 2019/11/16
     * @Param [operCode, reqMonth]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String,Object>> getPendingAppReq(String organLevel, WebOperInfo sessionOperInfo, String auditStatus, Pager pager) throws Exception;

     Map<String, Object> findIsNotAgreeInfo(Task task, String custType, Map<String, Object> variables)	throws Exception;


    ProcessInstance completeTaskAudit(String taskId, String comment, Map<String, Object> varMap, Map msgMap) throws Exception;
    void completeTask(ProcessInstance  processInstance, Map<String, Object> varMap, Map msgMap) throws Exception;

    /**
     * @Author daice
     * @Description //查询已审批的信贷需求
     * @Date 下午6:41 2019/11/16
     * @Param [operCode, auditStatus, reqMonth]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<Map<String, Object>> getApprovedRecord(WebOperInfo sessionOperInfo, String auditStatus) throws Exception;
}
